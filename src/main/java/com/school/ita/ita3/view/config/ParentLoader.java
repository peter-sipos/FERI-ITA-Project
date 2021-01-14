package com.school.ita.ita3.view.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

// a wrapper object to wrap the parent together with its loader
// used to return both the parent and the loader from the load method
public class ParentLoader {
    public final Parent parent;
    public final FXMLLoader loader;

    public ParentLoader(Parent parent, FXMLLoader loader) {
        this.parent = parent;
        this.loader = loader;
    }

}
