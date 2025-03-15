package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.Product

@Singleton
class ProductController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
    
    var products = List(
        Product(1, "Hp Gaming Laptop", "Laptopy", 2137.0),
        Product(2, "Lenovo Office Laptop", "Laptopy", 1000.0),
        Product(3, "Apple Expensive Laptop", "Laptopy", 6000.0)
    )

    def show_products() = Action { implicit request: Request[AnyContent] =>
        Ok(views.html.products.show_products(products))
    }
}