package com.phoenix.akka.http.auth.jwt.models

/**
  * Created by bipulk on 4/26/17.
  */

case class LoginRequest(username: String, password: String, usertype: String)

case class LoginResponse(token: String)

case class JwtModel(username: String, userType: String, createdOn: Long, expireOn: Long, roles: List[String])



