package com.school.ita.ita3.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Purchase> findByPurchaseCustomerId(long id){
        return purchaseRepository.findByPurchaseCustomerId(id);
    }

    public void save(Purchase purchase) {
        purchaseRepository.save(purchase);
    }
}
