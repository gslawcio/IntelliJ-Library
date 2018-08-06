package pl.moja.biblioteczka.Controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.moja.biblioteczka.Utils.DialogsUtils;
import pl.moja.biblioteczka.Utils.FxmlUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController {
    @FXML
    private BorderPane borderPane;      //wstrzykiwanie pola z ekranu głównego by zmieniać go

    @FXML
    private TopMenuButtonsController  topMenuButtonsController; //wstrzykiwanie kontrolera dotyczący TopMenuButtonsController
                                                                //odbywa się poprzez topMenuButtons

    @FXML
    public void initialize(){
        topMenuButtonsController.setMainController(this);


    }

    public void setCenter(String fxmlPath){  //metoda przyjmująca Stringa ze ścieżką do fxml

        borderPane.setCenter(FxmlUtils.fxmlLoader(fxmlPath));
    }

    public void closeApplication() {     // zamykanie aplikacji
        Optional<ButtonType> results = DialogsUtils.confirmationDialog();   //
                                //sprawdzamy jaki przycisk został kliknięty
        if(results.get() == ButtonType.OK) {        //jeśłi kliknięto OK . Przycisk cancek nie opisujemy
            Platform.exit();
            System.exit(0);     //zamyka maszynę wirtualną
        }
    }

    public void setCaspian() {       //ustawienie stylu CASPIAN - gotowy styl z javyFX
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    }

    public void setModerna() {      //ustawienie stylu MODENA - gotowy styl z javyFX
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    }

    public void setAlwaysOnTop(ActionEvent actionEvent) {
      Stage stage = (Stage) borderPane.getScene().getWindow();
        Boolean value = ((CheckMenuItem) actionEvent.getSource()).selectedProperty().getValue();   //rzutujemy actionEvent (zdarzenie) na przycisk CheckBox. Pobieramy z niego wartość boolean
                                                                                                    //czy jest zaznaczony czy nie.Wszystko przypisujemy pod wartość boolean
      stage.setAlwaysOnTop(value);
    }

    public void about() {
        DialogsUtils.dialogAboutApplication();
    }
}
