package com.phoenix.akka.http.auth.jwt

import com.phoenix.akka.http.auth.jwt.models.{JsonSupport, JwtModel}
import org.json4s.{Serialization, jackson}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}
import spray.json.{DefaultJsonProtocol, pimpAny, pimpString}

/**
  * Created by bipulk on 4/27/17.
  */
object JwtUtils {

  def encode(jwtService: JwtService)(jwtModel: JwtModel, secretKey: String): String = jwtService.encode(jwtModel, secretKey)

  def decode(jwtService: JwtService)(token: String, secretKey: String): (Boolean, JwtModel) = jwtService.decode(token, secretKey)

}

trait JwtService extends JsonSupport with DefaultJsonProtocol {

  def encode(jwtModel: JwtModel, secretKey: String): String

  def decode(token: String, secretKey: String): (Boolean, JwtModel)

}

object SymetricKeyJwtService extends JwtService {

  override def encode(jwtModel: JwtModel, secretKey: String): String = {

    Jwt.encode(JwtClaim(jwtModel.toJson.toString()), secretKey, JwtAlgorithm.HS512)

  }

  override def decode(token: String, secretKey: String): (Boolean, JwtModel) = {

    implicit val jacksonSerialization: Serialization = jackson.Serialization

    if (Jwt.isValid(token, secretKey, Seq(JwtAlgorithm.HS512))) {

      val decoded = Jwt.decode(token, secretKey, Seq(JwtAlgorithm.HS512))

      if (decoded.isSuccess) {

        val jwtModelJson = decoded.get.stripMargin.parseJson

        val jwtModel = jwtModelJson.asJsObject.convertTo[JwtModel]

        (true, jwtModel)

      } else {

        (false, null)
      }


    } else {

      (false, null)

    }

  }
}


object AsymetricKeyJwtService extends JwtService {

  override def encode(jwtModel: JwtModel, secretKey: String): String = ???

  override def decode(token: String, secretKey: String): (Boolean, JwtModel) = ???
}