package controllers

import play.api.i18n.{MessagesApi, I18nSupport, Messages, MessagesProvider}
import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.{Category, CategoryForm}

@Singleton
class CategoryController @Inject()(val controllerComponents: ControllerComponents, override val messagesApi: MessagesApi) extends BaseController with I18nSupport {
    var categories = List(
        Category(1, "Laptopy", "Laptopy gejmingowe, prackowe, fajne"),
        Category(2, "Książki", "mądre, wielostronicowe itd.")
    )
    

    def list_categories = Action { implicit request: Request[AnyContent] =>
        Ok(views.html.categories.show_categories(categories))
    }


    val categoryForm: Form[CategoryForm] = Form(
        mapping(
            "name" -> nonEmptyText,
            "description" -> text
        )(CategoryForm.apply)(CategoryForm.unapply)
    )


    def new_category = Action { implicit request: Request[AnyContent] =>
        Ok(views.html.categories.category_form(categoryForm))
    }

    def create_category = Action { implicit request =>
        categoryForm.bindFromRequest().fold(
            formWithErrors => {
                BadRequest(views.html.categories.category_form(formWithErrors))
            },
            categoryData => {
                val newId = if (categories.isEmpty) 1 else categories.map(_.id).max + 1
                categories = categories :+ Category(newId, categoryData.name, categoryData.description)

                Redirect(routes.CategoryController.list_categories).flashing("success" -> "Kategoria została dodana pomyślnie")
            }
        )
    }


    def show_single_category(id: Int) = Action { implicit request: Request[AnyContent] =>
        categories.find(_.id == id) match {
            case Some(category) => 
                Ok(views.html.categories.show_single_category(category))
            case None => NotFound("Kategoria nie została znaleziona")
        }
    }


    def edit_category(id:Int) = Action { implicit request =>
        categories.find(_.id == id) match {
            case Some(category) =>
                val filledForm = categoryForm.fill(CategoryForm(category.name, category.description))
                Ok(views.html.categories.edit_category(id, filledForm))
            case None => NotFound("Kategoria nie została znaleziona")
        }
    }


  def update_category(id: Int) = Action { implicit request =>
    categoryForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest(views.html.categories.edit_category(id, formWithErrors))
      },
      categoryData => {
        val categoryIndex = categories.indexWhere(_.id == id)
        
        if (categoryIndex >= 0) {
          val updatedCategory = Category(id, categoryData.name, categoryData.description)
          categories = categories.updated(categoryIndex, updatedCategory)
          Redirect(routes.CategoryController.list_categories)
            .flashing("success" -> "Kategoria została zaktualizowana")
        } else {
          NotFound("Kategoria nie została znaleziona")
        }
      }
    )
  }

  def delete_category(id: Int) = Action { implicit request =>
    val categoryIndex = categories.indexWhere(_.id == id)
    
    if (categoryIndex >= 0) {
      categories = categories.patch(categoryIndex, Nil, 1)
      Redirect(routes.CategoryController.list_categories)
        .flashing("success" -> "Kategoria została usunięta")
    } else {
      NotFound("Kategoria nie została znaleziona")
    }
  }

}