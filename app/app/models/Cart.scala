package models

case class CartItem(
  id: Int,
  productId: Int,
  productName: String,
  quantity: Int,
  price: Double
)

case class CartItemForm(
  productId: Int,
  quantity: Int
)

case class Cart(
  id: Int,
  userId: Int,
  items: List[CartItem]
) {
  def totalValue: Double = items.map(item => item.price * item.quantity).sum
  def itemCount: Int = items.map(_.quantity).sum
}

case class CartForm(
  userId: Int
)