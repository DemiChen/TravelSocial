package models

import play.api.libs.json.Json

/**
 * Login info
 * @param mobileNum
 * @param password
 */
case class LoginInfo(mobileNum: String, password: String)

object LoginInfo {
  implicit val loginInfoFmt = Json.format[LoginInfo]
}