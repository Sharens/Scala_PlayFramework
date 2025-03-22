package controllers

import javax.inject._
import play.api.i18n.{MessagesApi, I18nSupport, Messages, MessagesProvider}
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._ 
import play.api.data.validation.Constraints._
import models.Product
import models.BasicForm

@Singleton
class ProductController @Inject()(val controllerComponents: ControllerComponents, override val messagesApi: MessagesApi) extends BaseController with I18nSupport {
    
    var products = List(
        Product(1, "Hp Gaming Laptop", "Laptopy", 2137.0),
        Product(2, "Lenovo Office Laptop", "Laptopy", 1000.0),
        Product(3, "Apple Expensive Laptop", "Laptopy", 6000.0)
    )

    def show_products = Action { implicit request: Request[AnyContent] =>
        Ok(views.html.products.show_products(products))
    }

    def show_single_product(id: Int) = Action { implicit request: Request[AnyContent] =>
        Ok(views.html.products.show_single_product(products.find(Product => Product.id == id)))
    }

    val productForm: Form[BasicForm] = Form(
        mapping(
            "id" -> default(number, 0),
            "name" -> nonEmptyText,
            "category" -> nonEmptyText,
            "price" -> of[Double].verifying("Cena musi być dodatnia", price => price >= 0.0)
        )(BasicForm.apply)(BasicForm.unapply)
    )

    def product_forms = Action { implicit request: Request[AnyContent] =>
        Ok(views.html.products.product_form(productForm))
    }

    def add_products = Action { implicit request: Request[AnyContent] =>
        val productData = productForm.bindFromRequest()
        productData.fold(
            formWithErrors => {
                BadRequest(views.html.products.product_form(formWithErrors))
            },
            basicForm => {
                val newId = if (products.isEmpty) 1 else products.map(_.id).max + 1
                products = products :+ Product(newId, basicForm.name, basicForm.category, basicForm.price)
                Redirect(routes.ProductController.show_products)
            }
        )
    }

    def delete_product(id: Int) = Action { implicit request: Request[AnyContent] =>

        val productIndex = products.indexWhere(_.id == id)

        if (productIndex >= 0) {
            products = products.patch(productIndex, Nil, 1)
            Redirect(routes.ProductController.show_products).flashing("success" -> "Produkt został usunięty")
        } else {
            NotFound("Produkt nie został znaleziony")
        }
    }
}