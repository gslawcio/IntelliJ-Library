package pl.moja.biblioteczka;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.moja.biblioteczka.DateBase.dbuitls.DbManager;
import pl.moja.biblioteczka.Utils.FillDatabase;
import pl.moja.biblioteczka.Utils.FxmlUtils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {


    public static final String BORDER_PAIN_MAINE_FXML = "/fxml/BorderPainMaine.fxml";

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) throws Exception {
       // Locale.setDefault(new Locale("en"));    //zmiana języka wyświetlania.
        Pane borderPane = FxmlUtils.fxmlLoader(BORDER_PAIN_MAINE_FXML);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("title.application"));
        primaryStage.show();

        DbManager.initDatabase();   //inicjalizowanie bazy danych, po tym jak wystartuje aplikacja
        FillDatabase.fillDatabase();
    }
}
