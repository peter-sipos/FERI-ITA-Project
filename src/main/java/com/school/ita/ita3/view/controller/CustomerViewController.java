package com.school.ita.ita3.view.controller;

import com.school.ita.ita3.customer.Address;
import com.school.ita.ita3.customer.Customer;
import com.school.ita.ita3.customer.CustomerService;
import com.school.ita.ita3.view.FxmlView;
import com.school.ita.ita3.view.config.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class CustomerViewController extends AbstractController implements Initializable {

    private Customer loggedInCustomer = null;


    @FXML
    private Button productsViewButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField houseNunmberField;

    @FXML
    private TextField zipField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField countryField;

    @FXML
    private Button purchasesViewButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private CustomerService customerService;


    @FXML
    void delete(ActionEvent event) {
        if (loggedInCustomer != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete confirmation");
            alert.setHeaderText("You are about to delete your account");
            alert.setContentText("Are you sure you want to delete your account?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                customerService.deleteById(loggedInCustomer.getId());
                stageManager.switchScene(FxmlView.LOGIN);
            }
        }
    }

    @FXML
    void save(ActionEvent event) {
        if (emptyFields()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("All fields must be filled");
            alert.showAndWait();
        } else {
            Customer customer;
            if (loggedInCustomer == null) {
                customer = new Customer();
            } else {
                customer = loggedInCustomer;
            }
            customer.setName(nameField.getText());
            customer.setSurname(surnameField.getText());
            customer.setEmail(emailField.getText());
            customer.setPassword(passwordField.getText());
            Address address = new Address(
                    streetField.getText(),
                    Integer.parseInt(houseNunmberField.getText()),
                    Integer.parseInt(zipField.getText()),
                    cityField.getText(),
                    countryField.getText());
            customer.setAddress(address);

            customerService.save(customer);
            loggedInCustomer = customer;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Your data has been saved");

            alert.showAndWait();
        }
    }

    private boolean emptyFields() {
        return countryField.getText().isEmpty() ||
                cityField.getText().isEmpty() ||
                zipField.getText().isEmpty() ||
                houseNunmberField.getText().isEmpty() ||
                streetField.getText().isEmpty() ||
                passwordField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                surnameField.getText().isEmpty() ||
                nameField.getText().isEmpty();
    }


    @FXML
    void showProductsView(ActionEvent event) {
        if (loggedInCustomer != null) {
            stageManager.switchScene(FxmlView.PRODUCTS_VIEW, loggedInCustomer);
        }
    }

    @FXML
    void showPurchasesView(ActionEvent event) {
        if (loggedInCustomer != null){
            stageManager.switchScene(FxmlView.PURCHASES_VIEW, loggedInCustomer);
        }
    }

    @FXML
    void logout(ActionEvent event) {
        if (loggedInCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("You are signing out, OK?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stageManager.switchScene(FxmlView.LOGIN);
                loggedInCustomer = null;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    @Override
    public <T> void initData(T x) {
        loggedInCustomer = (Customer) x;
        nameField.setText(loggedInCustomer.getName());
        surnameField.setText(loggedInCustomer.getSurname());
        emailField.setText(loggedInCustomer.getEmail());
        passwordField.setText(loggedInCustomer.getPassword());
        streetField.setText(loggedInCustomer.getAddress().getStreet());
        houseNunmberField.setText(Integer.toString(loggedInCustomer.getAddress().getHouseNumber()));
        zipField.setText(Integer.toString(loggedInCustomer.getAddress().getZip()));
        cityField.setText(loggedInCustomer.getAddress().getCity());
        countryField.setText(loggedInCustomer.getAddress().getCountry());
    }
}
