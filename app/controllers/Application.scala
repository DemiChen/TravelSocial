package controllers

import java.util.UUID

import play.api.mvc._
import javax.inject.Inject
import play.api.i18n.{ MessagesApi }

class Application @Inject() (val messagesApi: MessagesApi) extends api.ApiController {

  def test = ApiAction { implicit request =>
    ok("The API is ready")
  }

  // Auxiliar to check the FakeDB information. It's only for testing purpose. You should remove it.
  def fakeDB = Action { implicit request =>
    Ok(views.html.fakeDB())
  }

  def upload = Action(parse.temporaryFile) { request =>
    request.body.moveTo(new java.io.File("/tutorial/"))
    println(request.body.file.getName)
    Ok("File uploaded")
  }

  /*def upload2 = Action(parse.file(to = new java.io.File("toturial/"))) { request =>
    println(request.body.getAbsolutePath)
    Ok("File uploaded" + request.body.getName)
  }*/

}
