package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, AnyContent, BaseController, ControllerComponents, Request}

class HomeController  @Inject()(  cc: ControllerComponents) extends AbstractController(cc)  {

  def index() = Action { implicit request: Request[AnyContent] =>
    val message:String = "version 1.0.0"
    Ok(views.html.index(message))
  }
}
