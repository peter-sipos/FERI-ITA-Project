package com.school.ita.ita3.view.controller;

import com.school.ita.ita3.customer.Customer;
import com.school.ita.ita3.product.Product;
import com.school.ita.ita3.product.ProductService;
import com.school.ita.ita3.purchase.Purchase;
import com.school.ita.ita3.purchase.PurchaseItem;
import com.school.ita.ita3.purchase.PurchaseService;
import com.school.ita.ita3.view.FxmlView;
import com.school.ita.ita3.view.config.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class ProductDetailViewController extends AbstractController implements Initializable {

    private Customer loggedInCustomer = null;
    private Product viewedProduct = null;

    @FXML
    private Button customerViewButton;

    @FXML
    private Button productsViewButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Button buyButton;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private ProductService productService;

    @Autowired
    private PurchaseService purchaseService;

    @FXML
    void buyProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Order confirmation");
        alert.setHeaderText("Do you really want to buy this item?");
        alert.setContentText("Once you do, you have to pay for it.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            PurchaseItem purchaseItem = new PurchaseItem(viewedProduct, quantitySpinner.getValue());

            Purchase newPurchase = new Purchase(loggedInCustomer);
            newPurchase.getPurchasedItems().add(purchaseItem);
            newPurchase.calculateTotalPrice();
            purchaseService.save(newPurchase);

            viewedProduct.updateStockQuantity(quantitySpinner.getValue());
            productService.save(viewedProduct);

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText("New purchase");
            alert1.setContentText("Your purchase has been noticed");
            alert1.showAndWait();
        }


    }

    @FXML
    void showCustomerView(ActionEvent event) {
        stageManager.switchScene(FxmlView.CUSTOMER_VIEW, loggedInCustomer);
    }


    @FXML
    void showProductsView(ActionEvent event) {
        stageManager.switchScene(FxmlView.PRODUCTS_VIEW, loggedInCustomer);
    }

    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are signing out, OK?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            stageManager.switchScene(FxmlView.LOGIN);
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public <T, E> void initData(T x, E y) {
        loggedInCustomer = (Customer) x;
        viewedProduct = (Product) y;

        productNameField.setText(viewedProduct.getName());
        descriptionArea.setText(viewedProduct.getDescription());
        productPriceField.setText(Integer.toString(viewedProduct.getPrice()));

        SpinnerValueFactory<Integer> integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,viewedProduct.getQuantityInStock(),1);
        quantitySpinner.setValueFactory(integerSpinnerValueFactory);
    }
}
