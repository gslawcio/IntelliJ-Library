package pl.moja.biblioteczka.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.moja.biblioteczka.DateBase.models.ListBooksModel;
import pl.moja.biblioteczka.Utils.DialogsUtils;
import pl.moja.biblioteczka.Utils.FxmlUtils;
import pl.moja.biblioteczka.Utils.exceptions.ApplicationException;
import pl.moja.biblioteczka.modelFx.AuthorFx;
import pl.moja.biblioteczka.modelFx.BookFx;
import pl.moja.biblioteczka.modelFx.CategoryFx;

import java.io.IOException;
import java.time.LocalDate;

public class ListBooksController {
    @FXML
    private TableColumn<BookFx, BookFx> editColumn;
    @FXML
    private TableColumn<BookFx, BookFx> deleteColunm;
    @FXML
    private ComboBox authorComboBox;
    @FXML
    private ComboBox categoryComboBox;
    @FXML
    private TableColumn<BookFx, LocalDate> releaseColumn;
    @FXML
    private TableColumn<BookFx, String> isbnColumn;
    @FXML
    private TableColumn<BookFx, Number> ratingColumn;
    @FXML
    private TableColumn<BookFx, CategoryFx> categoryColumn;
    @FXML
    private TableColumn<BookFx, AuthorFx> authorColumn;
    @FXML
    private TableColumn<BookFx, String> descColumn;
    @FXML
    private TableColumn<BookFx, String> titleColumn;
    @FXML
    private TableView<BookFx> booksTableView;


    private ListBooksModel listBooksModel;


    @FXML
    public void initialize(){
        //instancja tego modelu
        this.listBooksModel = new ListBooksModel();
        try {
            this.listBooksModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
                //inicjalizacja ComboBoxów (wypełnienie) danymi
            this.categoryComboBox.setItems(this.listBooksModel.getCategoryFxObservableList());
            this.authorComboBox.setItems(this.listBooksModel.getAuthorFxObservableList());

            //powiązujemy bindujemy categoryFxObjectProperty(categoryFxObjectPropertyProperty) z aktualnie wybraną pozycją w ComboBoxie
            this.listBooksModel.categoryFxObjectPropertyProperty().bind(this.categoryComboBox.valueProperty());
            this.listBooksModel.authorFxObjectPropertyProperty().bind(this.authorComboBox.valueProperty());


        //do BooksTableView podłączyliśmy listę wszystkich elementów które się znajdują w listBooksModel
        this.booksTableView.setItems(this.listBooksModel.getObservableList());
        //na każdej kolumnie ustawiamy setCellValueFactory - bindujemy
        this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        this.descColumn.setCellValueFactory(cellDesc -> cellDesc.getValue().descriptionProperty());
        this.authorColumn.setCellValueFactory(cellAuthor -> cellAuthor.getValue().authorFxProperty());
        this.categoryColumn.setCellValueFactory(cellCategory -> cellCategory.getValue().categoryFxProperty());
        this.ratingColumn.setCellValueFactory(cellRating -> cellRating.getValue().ratingProperty());
        this.isbnColumn.setCellValueFactory(cellISBN -> cellISBN.getValue().isbnProperty());
        this.releaseColumn.setCellValueFactory(cellRelease -> cellRelease.getValue().releaseDateProperty());

        //odwołanie do kolumny delete. pracuje na BookFX nie jest opakowana wiec jak opakowaliśmy w SimpleObjectProperty
        this.deleteColunm.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        //Metoda   tworzymy nowy obiekt TablleCell który pracuje na BookFx i BookFx. Budujemy komórki w naszych kolumnach
        //każda komórka pracuje na BookFx
        this.deleteColunm.setCellFactory(param -> new TableCell<BookFx , BookFx>(){
            Button button = createButton("/icons/delete.png");            //przycisk mamy gotowy- budujemy
            @Override       //nadpisujemy metodę, któa zajmuje się tworzeniem komórki
            protected void updateItem(BookFx item, boolean empty) {
                super.updateItem(item, empty);
                        // po usunięciu by nie pokazywały się ikony delete
                if(empty){
                    setGraphic(null);
                    return;
                }

                //tu możemy budować komórkę naszej kolumny
                if(!empty){ //gdy nie jest pusty ustawiamy przycisk
                setGraphic(button);
                //ustawiamy akcję
                button.setOnAction(event -> {
                    try {
                        listBooksModel.deleteBook(item);
                    } catch (ApplicationException e) {
                        DialogsUtils.errorDialog(e.getMessage());
                    }
                });
                }
            }
        });
        //bindujemy editColumn
        this.editColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        //tworzenie ikonki w każdym wierszu colimny
        this.editColumn.setCellFactory(param -> new TableCell<BookFx , BookFx>(){
            Button button = createButton("/icons/edit.png");            //przycisk mamy gotowy- budujemy
            @Override       //nadpisujemy metodę, któa zajmuje się tworzeniem komórki
            protected void updateItem(BookFx item, boolean empty) {
                super.updateItem(item, empty);
                // po usunięciu by nie pokazywały się ikony delete
                if(empty){
                    setGraphic(null);
                    return;
                }

                //tu możemy budować komórkę naszej kolumny
                if(!empty){ //gdy nie jest pusty ustawiamy przycisk
                    setGraphic(button);
                    //ustawiamy akcję
                    button.setOnAction(event -> {
                        FXMLLoader loader = FxmlUtils.getLoader("/fxml/AddBook.fxml");
                        Scene scene=null;
                        try {
                            scene = new Scene(loader.load());
                        } catch (IOException e) {
                            DialogsUtils.errorDialog(e.getMessage());
                        }

                        BookController controller = loader.getController();
                        controller.getBookModel().setBookFxObjectProperty(item);
                        controller.bindings();


                        Stage stage = new Stage();
                        stage.setScene(scene);
                        //sprawi że nasze okno modalne będzie nad aplikacją i dopuki nie zamkniemy go nie będzie dospępu do innych modyfikacji
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    });
                }
            }
        });
    }

    //przyciski
    private Button createButton(String path){
        Button button = new Button();
        Image image = new Image(this.getClass().getResource(path).toString());
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        return button;
    }


    public void filterOnActionComboBox() {
        this.listBooksModel.filterBooksList();
        System.out.println(this.listBooksModel.categoryFxObjectPropertyProperty().get());

    }

    public void clearCategoryComboBox() {
        this.categoryComboBox.getSelectionModel().clearSelection();
    }

    public void clearAuthorComboBox() {
        this.authorComboBox.getSelectionModel().clearSelection();
    }
}
