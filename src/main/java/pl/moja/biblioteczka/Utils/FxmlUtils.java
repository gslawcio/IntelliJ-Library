package pl.moja.biblioteczka.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

public class FxmlUtils {
                            //odpowiada za ładowanie wszystkich fxml-ów

    public static Pane fxmlLoader(String fxmlPane){ //będzie zwracała Pane. będzie przyjmowała stringa- ścieżkę do fxml

        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPane));

        loader.setResources(getResourceBundle());
        try {
          return  loader.load();
        } catch (Exception e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
            return null;
    }
        //zwraca FXMLLoader okienko edycji książki w ListBookController
    public static FXMLLoader getLoader(String fxmlPane){ //będzie zwracała Pane. będzie przyjmowała stringa- ścieżkę do fxml

        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPane));

        loader.setResources(getResourceBundle());
                  return  loader;
            }

            // będzie zwracałą resorsesBundle
    public static ResourceBundle getResourceBundle(){

        return ResourceBundle.getBundle("bundles.messages");
    }

}
