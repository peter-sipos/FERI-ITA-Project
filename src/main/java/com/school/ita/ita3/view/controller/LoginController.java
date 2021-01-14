package com.school.ita.ita3.view.controller;

import com.school.ita.ita3.customer.Customer;
import com.school.ita.ita3.customer.CustomerService;
import com.school.ita.ita3.view.FxmlView;
import com.school.ita.ita3.view.config.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LoginController extends AbstractController implements Initializable {

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private CustomerService customerService;


    @FXML
    void login(ActionEvent event) {
        Customer customer = customerService.authorize(emailField.getText(), passwordField.getText());
        if (customer != null){
            stageManager.switchScene(FxmlView.CUSTOMER_VIEW, customer);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unsuccessful login");
            alert.setHeaderText("Email and password do not match");
            alert.setContentText("Did you enter correct email and password? If you don't have an accout, hit Register.");

            alert.showAndWait();
        }
    }

    @FXML
    void register(ActionEvent event) {
        stageManager.switchScene(FxmlView.CUSTOMER_VIEW);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
