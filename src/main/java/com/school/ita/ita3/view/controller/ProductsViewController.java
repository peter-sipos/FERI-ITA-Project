package com.school.ita.ita3.view.controller;

import com.school.ita.ita3.customer.Customer;
import com.school.ita.ita3.product.Product;
import com.school.ita.ita3.product.ProductService;
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
public class ProductsViewController extends AbstractController implements Initializable {

    private Customer loggedInCustomer = null;
    private List<Product> products;
    private ObservableList<Product> productsDisplay = FXCollections.observableArrayList();

    @FXML
    private Button customerViewButton;

    @FXML
    private Button productsViewButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TableColumn<Product, String> quantityInStockColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> priceColumn;

    @FXML
    private MenuItem productDetails;

    @FXML
    void showCustomerView(ActionEvent event) {
        stageManager.switchScene(FxmlView.CUSTOMER_VIEW, loggedInCustomer);
    }


    @FXML
    void showProductDetails(ActionEvent event) {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
// TO PROVIDE SOME DATA IN DB
//        Book book = new Book("Gorge R.R. Martin", "Unknow", "fantasy");
//        book.setDescription("Lorem ipsum Martin GOT");
//        book.setName("Game of Thrones");
//        book.setQuantityInStock(20);
//        book.setPrice(15);
//
//        Book book1 = new Book("Christopher Paoilini", "Unknown", "fantasy");
//        book1.setDescription("Lorem ipsom Paolini");
//        book1.setName("Eragon");
//        book1.setQuantityInStock(5);
//        book1.setPrice(20);
//
//        Movie movie = new Movie("Stephen Spielberg", "Gourge Lucas", "Lucas Films", "sci-fi");
//        movie.setDescription("Lorem ipsum Star Movie");
//        movie.setName("Star Movie");
//        movie.setPrice(10);
//        movie.setQuantityInStock(12);
//
//        productService.save(book);
//        productService.save(book1);
//        productService.save(movie);
    }

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private ProductService productService;

    @Override
    public <T> void initData(T x) {
        productsDisplay.clear();
        loggedInCustomer = (Customer) x;
        products = productService.findAll();
        productsDisplay.addAll(products);

        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityInStockColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));

        productsTableView.setItems(productsDisplay);
    }
}
