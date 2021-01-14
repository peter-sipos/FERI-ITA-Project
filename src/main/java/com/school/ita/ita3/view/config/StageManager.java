// based on https://github.com/RamAlapure/JavaFXSpringBootApp.git

package com.school.ita.ita3.view.config;

import com.school.ita.ita3.view.FxmlView;
import com.school.ita.ita3.view.controller.AbstractController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {

    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    public void switchScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile()).parent;
        show(viewRootNodeHierarchy, view.getTitle());
    }

    public <T> void switchScene(final FxmlView view, T x) {
        ParentLoader parentLoader = loadViewNodeHierarchy(view.getFxmlFile());
        Parent viewRootNodeHierarchy = parentLoader.parent;
        FXMLLoader loader = parentLoader.loader;
        show(viewRootNodeHierarchy, view.getTitle(), loader, x);
    }

    public <T, E> void switchScene(final FxmlView view, T x, E y) {
        ParentLoader parentLoader = loadViewNodeHierarchy(view.getFxmlFile());
        Parent viewRootNodeHierarchy = parentLoader.parent;
        FXMLLoader loader = parentLoader.loader;
        show(viewRootNodeHierarchy, view.getTitle(), loader, x, y);
    }

    private void show(final Parent rootnode, String title) {
        Scene scene = prepareScene(rootnode);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }
    }

    private <T> void show(final Parent rootnode, String title, FXMLLoader loader, T x) {
        Scene scene = prepareScene(rootnode);
        AbstractController controller = loader.getController();
        controller.initData(x);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }
    }

    private <T, E> void show(final Parent rootnode, String title, FXMLLoader loader, T x, E y) {
        Scene scene = prepareScene(rootnode);
        AbstractController controller = loader.getController();
        controller.initData(x, y);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }
    }
    
    private Scene prepareScene(Parent rootnode){
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    private ParentLoader loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        FXMLLoader loader = null;
        ParentLoader parentLoader = null;
        try {
            parentLoader = springFXMLLoader.load(fxmlFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            rootNode = parentLoader.parent;
            loader = parentLoader.loader;
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
        }
        return new ParentLoader(rootNode, loader);
    }
    
    
    private void logAndExit(String errorMsg, Exception exception) {
        Platform.exit();
    }

}
