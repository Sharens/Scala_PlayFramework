package models

case class ProductForm(
  name: String,
  category: String,
  price: Double
)

case class Product(
  id: Int,
  name: String,
  category: String,
  price: Double
)