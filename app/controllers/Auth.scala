package controllers

import api.ApiError._
import api.JsonCombinators._
import models._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.Play.current
import play.api.libs.concurrent.Akka
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Inject
import play.api.i18n.{ MessagesApi }

class Auth @Inject() (val messagesApi: MessagesApi) extends api.ApiController {

  implicit val loginINfoFmt = Json.format[LoginInfo]

  def signIn = ApiActionWithBody { implicit request =>
    readFromRequest[LoginInfo] {
      case LoginInfo(mobileNum, pwd) =>
        TestUser.findByEmail(mobileNum).flatMap {
          case None => errorUserNotFound
          case Some(user) => {
            if (user.password != pwd) errorUserNotFound
            else if (!user.emailConfirmed) errorUserEmailUnconfirmed
            else if (!user.active) errorUserInactive
            else ApiToken.create(request.apiKeyOpt.get, user.id).flatMap { token =>
              ok(Json.obj(
                "token" -> token,
                "minutes" -> 10
              ))
            }
          }
        }
    }
  }

  def signOut = SecuredApiAction { implicit request =>
    ApiToken.delete(request.token).flatMap { _ =>
      noContent()
    }
  }


  implicit val singUpInfoFmt = Json.format[SignUpInfo]

  def signUp = ApiActionWithBody { implicit request =>
    readFromRequest[SignUpInfo] {
      case SignUpInfo(mobileNum, password, securityCode, area) =>
        if (securityCodeIsValid(securityCode)) {
          TestUser.findByEmail(mobileNum).flatMap {
            case Some(anotherUser) => errorCustom("api.error.signup.email.exists")
            case None => TestUser.insert(mobileNum, password, area + securityCode).flatMap {
              case (id, user) =>

                // Send confirmation email. You will have to catch the link and confirm the email and activate the user.
                // But meanwhile...
                Akka.system.scheduler.scheduleOnce(30 seconds) {
                  TestUser.confirmEmail(id)
                }

                ok(user)
            }
          }
        } else errorCustom("security code is invalid")
    }
  }

  /**
   * TODO 短信验证码暂忽略
   * @param sc
   * @return
   */
  private def securityCodeIsValid(sc: Int) = true

}