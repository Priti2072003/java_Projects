package ecommerce;

import java.util.UUID;

public class Product{
private String productId;
private String name;
private String description;
private double price;
private int stockQuantity;
public Product(String name,String description,double price,int stockQuantity){
this.productId=UUID.randomUUID().toString().substring(0, 8);
this.name=name;
this.description=description;
this.price=price;
this.stockQuantity=stockQuantity;
}
public String getProductId(){
    return productId;
}
public String getName(){
    return name;
}
public String getDescription(){
    return description;
}
public double getPrice(){
    return price;
}
public int getStockQuantity(){
    return stockQuantity;
}
public void setStockQuantity(int stockQuantity){
    if(stockQuantity>=0){
        this.stockQuantity=stockQuantity;
    }
    else{
        System.out.println("stock quantity can not negative.");
    }

}
public void displayProductDetails(){
    System.out.println("---------------------------");
    System.out.println("Producr Id:"+productId);
    System.out.println("Product name:"+name);
    System.out.println("Product description:"+description);
    System.out.println("Product Price:$"+price);
    System.out.println("Product Stock:"+stockQuantity);
    System.out.println("---------------------------");
}
@Override
    public String toString() {
        return "Product{" +
               "productId='" + productId + '\'' +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", stockQuantity=" + stockQuantity +
            '}';
}
}


