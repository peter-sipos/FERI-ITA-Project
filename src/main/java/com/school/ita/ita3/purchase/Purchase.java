package com.school.ita.ita3.purchase;

import com.school.ita.ita3.customer.Customer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private Customer purchaseCustomer;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<PurchaseItem> purchasedItems = new ArrayList<>();

    private int totalPrice;

    @Version
    private long version;


    public Purchase() {
    }

    public Purchase(Customer customer){
        this.purchaseCustomer = customer;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getPurchaseCustomer() {
        return purchaseCustomer;
    }

    public void setPurchaseCustomer(Customer orderCustomer) {
        this.purchaseCustomer = orderCustomer;
    }

    public List<PurchaseItem> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<PurchaseItem> orderedItems) {
        this.purchasedItems = orderedItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void calculateTotalPrice(){
        int sum = 0;
        for (PurchaseItem item : purchasedItems){
            sum += item.getItemPrice();
        }
        this.totalPrice = sum;
    }

}
