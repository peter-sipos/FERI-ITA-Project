// based on https://github.com/RamAlapure/JavaFXSpringBootApp.git

package com.school.ita.ita3.view;

import java.util.ResourceBundle;

public enum FxmlView {

    LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("Login.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Login.fxml";
        }
    },
    CUSTOMER_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("customerView.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/CustomerView.fxml";
        }
    },
    PURCHASES_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("purchasesView.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/PurchasesView.fxml";
        }
    },
    PURCHASE_DETAILS_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("purchaseDetailView.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/PurchaseDetailView.fxml";
        }
    },
    PRODUCT_DETAIL_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("productDetailView.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/ProductDetailView.fxml";
        }
    },
    PRODUCTS_VIEW {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("productsView.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/ProductsView.fxml";
        }
    };
    
    public abstract String getTitle();
    public abstract String getFxmlFile();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
