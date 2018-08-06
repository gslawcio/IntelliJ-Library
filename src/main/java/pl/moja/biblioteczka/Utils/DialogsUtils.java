package pl.moja.biblioteczka.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.ResourceBundle;

public class DialogsUtils {
        // odwołujemy się do żródeł dialogów - tam gdzie mamy zmianę języków
        static ResourceBundle bundle = FxmlUtils.getResourceBundle();

        //metoda oka dialogowego o aplikacji
    public static void dialogAboutApplication(){
        // kożystamy z klasa Alert która rozszeża klasę Dialog
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("about.title"));            //tytuł ustawiamy
        informationAlert.setHeaderText(bundle.getString("about.header"));
        informationAlert.setContentText(bundle.getString("about.content"));
        informationAlert.showAndWait();                     //wywołąnie okna.
    }

    //metoda przy zamykaniu aplikacji
    public static Optional<ButtonType> confirmationDialog(){

        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(bundle.getString("exit.title"));            //tytuł ustawiamy
        confirmationDialog.setHeaderText(bundle.getString("exit.header"));
        confirmationDialog.showAndWait();                                        //wywołąnie okna.
        Optional<ButtonType> result = confirmationDialog.showAndWait();         // przypisanie do result co zostało kliknięte
        return result;
    }

    //metoda wyświetlanąca okno nie wgranego fxml-a. Obsłużenie błędu w FxmlUtils - fmxlloader
    public static void errorDialog(String errorDialog){

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("exit.titleError"));
        errorAlert.setHeaderText(bundle.getString("exit.headerErrror"));

        TextArea textArea = new TextArea(errorDialog);
        errorAlert.getDialogPane().setContent(textArea);

        errorAlert.showAndWait();
    }

    //poppap - okno w którym będziemy edytować wpisaną kategorię
    public static String editDialog( String value){

        TextInputDialog dialog = new TextInputDialog(value);
        dialog.setTitle(bundle.getString("edit.title"));
        dialog.setHeaderText(bundle.getString("edit.header"));
        dialog.setContentText(bundle.getString("edit.content"));
        Optional<String> result = dialog.showAndWait();             // za pomocą OPtional pobieramy wartość któa będzie zmienona w polu tekstowym
        if(result.isPresent()) {                        //sprawdzamy czy wartośc jest pobrana
            return result.get();                //jeśli jest to ja pobieramy i zwracamy
        }
        return null;            //jeśłi nie ma to zwracamy null

    }
}
