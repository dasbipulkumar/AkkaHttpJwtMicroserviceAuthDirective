package com.phoenix.akka.http.auth.jwt.customdirectives

/**
  * Created by bipulk on 4/27/17.
  */

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server._
import com.phoenix.akka.http.auth.jwt.{JwtUtils, SymetricKeyJwtService}
import com.phoenix.akka.http.auth.jwt.models.JwtModel
import spray.json.{JsObject, JsString}

object Authentication extends Directives with SprayJsonSupport {

  def authenticate(secretKey: String): Directive1[JwtModel] = {
    optionalHeaderValueByName("Authorization-jwt-token") flatMap {
      case Some(authHeader) => {

        //println("Header::   " + authHeader)

        val accessToken = authHeader.split(",").last.trim

        val decoded = JwtUtils.decode(SymetricKeyJwtService)(accessToken, secretKey)

        if (decoded._1) {

          if (decoded._2.expireOn < System.currentTimeMillis()) {

            complete(Unauthorized, JsObject(Map("status" -> JsString("Jwt Authorization Token expired'"))))

          } else {

            provide(decoded._2)
          }

        } else {

          complete(Unauthorized, JsObject(Map("status" -> JsString("Invalid Jwt Authorization Token'"))))

        }
      }
      case _ => complete(Unauthorized, JsObject(Map("status" -> JsString("Missing Jwt Authorization header named '[Authorization-jwt-token]'"))))
    }
  }

  def extractUserInfo(secretKey: String): Directive1[JwtModel] = {
    authenticate(secretKey).flatMap { user =>

      provide(user)

    }
  }

  def updateJwtToken(timeout: Int, secretKey: String): Directive1[String] = {

    extractUserInfo(secretKey).flatMap { user =>

      provide(JwtUtils.encode(SymetricKeyJwtService)(user.copy(expireOn = System.currentTimeMillis() + (timeout * 1000)), secretKey))

    }

  }

}
