package controllers

import javax.inject._
import play.api.i18n.{MessagesApi, I18nSupport, Messages, MessagesProvider}
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import models.{Cart, CartItem, CartItemForm, CartForm, Product}

@Singleton
class CartController @Inject()(val controllerComponents: ControllerComponents,
                               val productController: ProductController,
                               override val messagesApi: MessagesApi) extends BaseController with I18nSupport {
  
  var carts = List(
    Cart(1, 1, List(
      CartItem(1, 1, "Hp Gaming Laptop", 1, 2137.0),
      CartItem(2, 3, "Apple Expensive Laptop", 1, 6000.0)
    ))
  )
  
  val cartForm: Form[CartForm] = Form(
    mapping(
      "userId" -> number
    )(CartForm.apply)(CartForm.unapply)
  )
  
  val cartItemForm: Form[CartItemForm] = Form(
    mapping(
      "productId" -> number,
      "quantity" -> number.verifying("Ilość musi być dodatnia", quantity => quantity > 0)
    )(CartItemForm.apply)(CartItemForm.unapply)
  )
  
  def showCarts = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.carts.showCarts(carts))
  }
  
  def showCart(id: Int) = Action { implicit request: Request[AnyContent] =>
    carts.find(_.id == id) match {
      case Some(cart) => Ok(views.html.carts.showCart(cart))
      case None => NotFound("Koszyk nie został znaleziony")
    }
  }
  
  def newCart = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.carts.cartForm(cartForm))
  }
  
  def createCart = Action { implicit request =>
    cartForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest(views.html.carts.cartForm(formWithErrors))
      },
      cartData => {
        val newId = if (carts.isEmpty) 1 else carts.map(_.id).max + 1
        carts = carts :+ Cart(newId, cartData.userId, List())
        Redirect(routes.CartController.showCarts).flashing("success" -> "Koszyk został utworzony")
      }
    )
  }
  
  def addItemForm(cartId: Int) = Action { implicit request: Request[AnyContent] =>
    val products = productController.products
    carts.find(_.id == cartId) match {
      case Some(cart) => Ok(views.html.carts.addItemForm(cartId, cartItemForm, products))
      case None => NotFound("Koszyk nie został znaleziony")
    }
  }
  
  def addItem(cartId: Int) = Action { implicit request =>
    val products = productController.products
    
    cartItemForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest(views.html.carts.addItemForm(cartId, formWithErrors, products))
      },
      itemData => {
        val cartIndex = carts.indexWhere(_.id == cartId)
        
        if (cartIndex >= 0) {
          val cart = carts(cartIndex)
          
    
          products.find(_.id == itemData.productId) match {
            case Some(product) => 
        
              val existingItemIndex = cart.items.indexWhere(_.productId == itemData.productId)
              
              val updatedItems = if (existingItemIndex >= 0) {
            
                val existingItem = cart.items(existingItemIndex)
                val updatedItem = CartItem(
                  existingItem.id, 
                  existingItem.productId, 
                  existingItem.productName, 
                  existingItem.quantity + itemData.quantity, 
                  existingItem.price
                )
                cart.items.updated(existingItemIndex, updatedItem)
              } else {
            
                val newItemId = if (cart.items.isEmpty) 1 else cart.items.map(_.id).max + 1
                cart.items :+ CartItem(
                  newItemId, 
                  product.id, 
                  product.name, 
                  itemData.quantity, 
                  product.price
                )
              }
              
              val updatedCart = Cart(cart.id, cart.userId, updatedItems)
              carts = carts.updated(cartIndex, updatedCart)
              
              Redirect(routes.CartController.showCart(cartId))
                .flashing("success" -> "Produkt został dodany do koszyka")
                
            case None => 
              BadRequest(views.html.carts.addItemForm(cartId, cartItemForm.withError("productId", "Produkt nie istnieje"), products))
          }
        } else {
          NotFound("Koszyk nie został znaleziony")
        }
      }
    )
  }
  
  def removeItem(cartId: Int, itemId: Int) = Action { implicit request =>
    val cartIndex = carts.indexWhere(_.id == cartId)
    
    if (cartIndex >= 0) {
      val cart = carts(cartIndex)
      val updatedItems = cart.items.filterNot(_.id == itemId)
      val updatedCart = Cart(cart.id, cart.userId, updatedItems)
      
      carts = carts.updated(cartIndex, updatedCart)
      Redirect(routes.CartController.showCart(cartId))
        .flashing("success" -> "Produkt został usunięty z koszyka")
    } else {
      NotFound("Koszyk nie został znaleziony")
    }
  }
  
  def updateItemForm(cartId: Int, itemId: Int) = Action { implicit request =>
    carts.find(_.id == cartId) match {
      case Some(cart) =>
        cart.items.find(_.id == itemId) match {
          case Some(item) =>
            val filledForm = cartItemForm.fill(CartItemForm(item.productId, item.quantity))
            Ok(views.html.carts.updateItemForm(cartId, itemId, filledForm))
          case None => NotFound("Element koszyka nie został znaleziony")
        }
      case None => NotFound("Koszyk nie został znaleziony")
    }
  }
  
  def updateItem(cartId: Int, itemId: Int) = Action { implicit request =>
    cartItemForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest(views.html.carts.updateItemForm(cartId, itemId, formWithErrors))
      },
      itemData => {
        val cartIndex = carts.indexWhere(_.id == cartId)
        
        if (cartIndex >= 0) {
          val cart = carts(cartIndex)
          val itemIndex = cart.items.indexWhere(_.id == itemId)
          
          if (itemIndex >= 0) {
            val item = cart.items(itemIndex)
            val updatedItem = CartItem(
              item.id, 
              item.productId, 
              item.productName, 
              itemData.quantity, 
              item.price
            )
            
            val updatedItems = cart.items.updated(itemIndex, updatedItem)
            val updatedCart = Cart(cart.id, cart.userId, updatedItems)
            
            carts = carts.updated(cartIndex, updatedCart)
            Redirect(routes.CartController.showCart(cartId))
              .flashing("success" -> "Ilość produktu została zaktualizowana")
          } else {
            NotFound("Element koszyka nie został znaleziony")
          }
        } else {
          NotFound("Koszyk nie został znaleziony")
        }
      }
    )
  }
  
  def deleteCart(id: Int) = Action { implicit request =>
    val cartIndex = carts.indexWhere(_.id == id)
    
    if (cartIndex >= 0) {
      carts = carts.patch(cartIndex, Nil, 1)
      Redirect(routes.CartController.showCarts)
        .flashing("success" -> "Koszyk został usunięty")
    } else {
      NotFound("Koszyk nie został znaleziony")
    }
  }
  
  def clearCart(id: Int) = Action { implicit request =>
    val cartIndex = carts.indexWhere(_.id == id)
    
    if (cartIndex >= 0) {
      val cart = carts(cartIndex)
      val updatedCart = Cart(cart.id, cart.userId, List())
      
      carts = carts.updated(cartIndex, updatedCart)
      Redirect(routes.CartController.showCart(id))
        .flashing("success" -> "Koszyk został wyczyszczony")
    } else {
      NotFound("Koszyk nie został znaleziony")
    }
  }
}