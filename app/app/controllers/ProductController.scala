package controllers

import javax.inject._
import play.api.i18n.{MessagesApi, I18nSupport, Messages, MessagesProvider}
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._ 
import play.api.data.validation.Constraints._
import models.{Product, ProductForm, Category}

@Singleton
class ProductController @Inject()(val controllerComponents: ControllerComponents, 
                                  val categoryController: CategoryController,
                                  override val messagesApi: MessagesApi) extends BaseController with I18nSupport {
    
    var products = List(
        Product(1, "Hp Gaming Laptop", "Laptopy", 2137.0),
        Product(2, "Lenovo Office Laptop", "Laptopy", 1000.0),
        Product(3, "Apple Expensive Laptop", "Laptopy", 6000.0)
    )

    def show_products = Action { implicit request: Request[AnyContent] =>
        Ok(views.html.products.show_products(products))
    }

    def show_single_product(id: Int) = Action { implicit request: Request[AnyContent] =>

        val productOption = products.find(_.id == id)
        Ok(views.html.products.show_single_product(productOption))
    }

    val productForm: Form[ProductForm] = Form(
        mapping(
            "name" -> nonEmptyText,
            "category" -> nonEmptyText,
            "price" -> of[Double].verifying("Cena musi być dodatnia", price => price >= 0.0)
        )(ProductForm.apply)(ProductForm.unapply)
    )

    def product_forms = Action { implicit request: Request[AnyContent] =>
        val categories = categoryController.categories
        Ok(views.html.products.product_form(productForm, categories))
    }

    def add_products = Action { implicit request: Request[AnyContent] =>
        val categories = categoryController.categories
        productForm.bindFromRequest().fold(
            formWithErrors => {
                BadRequest(views.html.products.product_form(formWithErrors, categories))
            },
            productData => {
                val newId = if (products.isEmpty) 1 else products.map(_.id).max + 1
                products = products :+ Product(newId, productData.name, productData.category, productData.price)
                Redirect(routes.ProductController.show_products).flashing("success" -> "Produkt został dodany pomyślnie")
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

    def edit_product(id: Int) = Action { implicit request =>
        val categories = categoryController.categories
        products.find(_.id == id) match {
            case Some(product) =>
                val filledForm = productForm.fill(ProductForm(product.name, product.category, product.price))
                Ok(views.html.products.edit_product(id, filledForm, categories))
            case None => NotFound("Produkt nie został znaleziony")
        }
    }

    def update_product(id: Int) = Action { implicit request =>
        val categories = categoryController.categories
        productForm.bindFromRequest().fold(
            formWithErrors => {
                BadRequest(views.html.products.edit_product(id, formWithErrors, categories))
            },
            productData => {
                val productIndex = products.indexWhere(_.id == id)
                
                if (productIndex >= 0) {
                    val updatedProduct = Product(id, productData.name, productData.category, productData.price)
                    products = products.updated(productIndex, updatedProduct)
                    Redirect(routes.ProductController.show_products)
                        .flashing("success" -> "Produkt został zaktualizowany")
                } else {
                    NotFound("Produkt nie został znaleziony")
                }
            }
        )
    }
}