package com.phoenix.akka.http.auth.jwt.models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.unmarshalling.Unmarshaller
import spray.json._

/**
  * Created by bipulk on 4/26/17.
  */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val loginRequestFormat = jsonFormat3(LoginRequest)
  implicit val loginResponseFormat = jsonFormat1(LoginResponse)
  implicit val jwtModelFormat = jsonFormat5(JwtModel)


  implicit val boxListUnmarshaller: Unmarshaller[JsValue, JwtModel] =
    Unmarshaller.strict(jsValue => jwtModelFormat.read(jsValue))

  //implicit val securedResponseFormat = jsonFormat2(SecuredResponse.apply)

}
