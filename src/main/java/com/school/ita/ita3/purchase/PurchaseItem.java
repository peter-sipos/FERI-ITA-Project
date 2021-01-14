package com.school.ita.ita3.purchase;

import com.school.ita.ita3.product.Product;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Embeddable
public class PurchaseItem {

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;

    private int quantityPurchased;
    private int itemPrice;
    private String name;

    public PurchaseItem() {
    }

    public PurchaseItem(Product product, int quantityPurchased) {
        this.product = product;
        this.quantityPurchased = quantityPurchased;
        this.name = product.getName();
        calculateItemPrice();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(int quantityOrdered) {
        this.quantityPurchased = quantityOrdered;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void calculateItemPrice(){
        this.itemPrice = product.getPrice() * quantityPurchased;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
