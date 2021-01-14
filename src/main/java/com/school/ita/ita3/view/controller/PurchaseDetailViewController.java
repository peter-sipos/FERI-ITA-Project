package com.school.ita.ita3.view.controller;

import com.school.ita.ita3.customer.Customer;
import com.school.ita.ita3.product.Product;
import com.school.ita.ita3.purchase.Purchase;
import com.school.ita.ita3.purchase.PurchaseItem;
import com.school.ita.ita3.view.FxmlView;
import com.school.ita.ita3.view.config.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class PurchaseDetailViewController extends AbstractController implements Initializable {

    private Customer loggedInCustomer = null;
    private ObservableList<PurchaseItem> purchaseItemsDisplay = FXCollections.observableArrayList();
    private Purchase selectedPurchase;

    @FXML
    private Button customerViewButton;

    @FXML
    private Button productsViewButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField orderIdField;

    @FXML
    private TextField totalPriceField;

    @FXML
    private TableView<PurchaseItem> purchaseItemsTableView;

    @FXML
    private TableColumn<PurchaseItem, String> productNameColumn;

    @FXML
    private TableColumn<PurchaseItem, Integer> quantityColumn;

    @FXML
    private TableColumn<PurchaseItem, Integer> priceColumn;

    @FXML
    private MenuItem productDetails;

    @FXML
    void showCustomerView(ActionEvent event) {
        stageManager.switchScene(FxmlView.CUSTOMER_VIEW, loggedInCustomer);
    }


    @FXML
    void showProductDetails(ActionEvent event) {
        Product selectedProduct = purchaseItemsTableView.getSelectionModel().getSelectedItem().getProduct();
        stageManager.switchScene(FxmlView.PRODUCT_DETAIL_VIEW, loggedInCustomer, selectedProduct);
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

    @FXML
    void showPurchasesView(ActionEvent event) {
        stageManager.switchScene(FxmlView.PURCHASES_VIEW, loggedInCustomer);
    }

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Override
    public <T, E> void initData(T x, E y) {
        purchaseItemsDisplay.clear();
        loggedInCustomer = (Customer) x;
        selectedPurchase = (Purchase) y;
        purchaseItemsDisplay.addAll(selectedPurchase.getPurchasedItems());

        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantityPurchased"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        purchaseItemsTableView.setItems(purchaseItemsDisplay);

        orderIdField.setText("PURCHASE ID: " + Long.toString(selectedPurchase.getId()));
        totalPriceField.setText(Integer.toString(selectedPurchase.getTotalPrice()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
