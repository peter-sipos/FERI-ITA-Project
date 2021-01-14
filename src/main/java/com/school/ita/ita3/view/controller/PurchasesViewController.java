package com.school.ita.ita3.view.controller;

import com.school.ita.ita3.customer.Customer;
import com.school.ita.ita3.purchase.Purchase;
import com.school.ita.ita3.purchase.PurchaseService;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class PurchasesViewController extends AbstractController implements Initializable {

    private Customer loggedInCustomer = null;
    private ObservableList<Purchase> purchasesDisplay = FXCollections.observableArrayList();
    private List<Purchase> customerPurchases;


    @FXML
    private TableView<Purchase> purchasesTableView;

    @FXML
    private TableColumn<Purchase, Long> purchaseIdColumn;

    @FXML
    private TableColumn<Purchase, Integer> totalPriceColumn;

    @FXML
    private MenuItem purchaseDetails;

    @FXML
    private Button customerViewButton;

    @FXML
    private Button productsViewButton;

    @FXML
    private Button logoutButton;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private PurchaseService purchaseService;


    @FXML
    void showCustomerView(ActionEvent event) {
        stageManager.switchScene(FxmlView.CUSTOMER_VIEW, loggedInCustomer);
    }

    @FXML
    void showProductsView(ActionEvent event) {
        stageManager.switchScene(FxmlView.PRODUCTS_VIEW, loggedInCustomer);
    }

    @FXML
    void showPurchaseDetails(ActionEvent event) {
        Purchase selectedPurchase = purchasesTableView.getSelectionModel().getSelectedItem();
        stageManager.switchScene(FxmlView.PURCHASE_DETAILS_VIEW, loggedInCustomer, selectedPurchase);
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
    public <T> void initData(T x) {
        purchasesDisplay.clear();
        loggedInCustomer = (Customer) x;

        // need to get the purchases from database because of Lazy load
        customerPurchases = purchaseService.findByPurchaseCustomerId(loggedInCustomer.getId());
        // customerPurchases = loggedInCustomer.getPurchases(); // -> throws LazyInitializationException

        purchasesDisplay.addAll(customerPurchases);

        purchaseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        purchasesTableView.setItems(purchasesDisplay);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
