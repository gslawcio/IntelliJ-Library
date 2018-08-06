package pl.moja.biblioteczka.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.moja.biblioteczka.Utils.DialogsUtils;
import pl.moja.biblioteczka.Utils.exceptions.ApplicationException;
import pl.moja.biblioteczka.modelFx.AuthorFx;
import pl.moja.biblioteczka.modelFx.BookModel;
import pl.moja.biblioteczka.modelFx.CategoryFx;

public class BookController {
    @FXML
    private Button addButton;
    //pracuje na obiektach z CategoryFx
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    //pracuje na obiektach z AuthorFx
    @FXML
    private ComboBox<AuthorFx> authorComboBox;
    @FXML
    private TextArea descTextArea;
    @FXML
    private Slider ratingSlider;
    @FXML
    private TextField isbnTextField;
    @FXML
    private DatePicker releaseDataPicker;
    @FXML
    private TextField titleTextField;

    //żeby dostać się do BookModel dodajemy obiekt jego
    private BookModel bookModel;


    //taki konstruktor javyFx uruchamia się zaraz po uruchomieniu formatki - obsługa jej
    // bindujemy wszystkie nasze kontrolki z BookControler z prpertkami z BookFX
    @FXML
    public void initialize(){
    //inicjalizujemy obiekt BookModel
        this.bookModel = new BookModel();
      //teraz prze bookModel dostajemy się do naszego ObjectProperty w BookModel - bookFxObjectProperty, który obsługuje obiekty z BookFx
        // i przez ten obiekt dostajemy się do wszystkich obiektów z BookFX


        try {
            this.bookModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        bindings();
        validation();
        
    }

    private void validation() {
        //bindujemy przycisko by był wyłaczony gdy pola nie są wypełnione
        this.addButton.disableProperty().bind(this.authorComboBox.valueProperty().isNull()
                .or(this.categoryComboBox.valueProperty().isNull())
                .or(this.titleTextField.textProperty().isEmpty())
                .or( this.descTextArea.textProperty().isEmpty())
                .or(this.isbnTextField.textProperty().isEmpty())
                .or(this.releaseDataPicker.valueProperty().isNull()));
    }

    public void bindings() {
        //odwołujemy się do bookModel, pobieramy getBookFxObjectProperty i pobieramy categoryFxProperty() i bindujemy
        //do categoryComboBox.valueProperty

       // this.bookModel.getBookFxObjectProperty().categoryFxProperty().bind(this.categoryComboBox.valueProperty());
       // this.bookModel.getBookFxObjectProperty().authorFxProperty().bind(this.authorComboBox.valueProperty());
      //  this.bookModel.getBookFxObjectProperty().titleProperty().bind(this.titleTextField.textProperty());
       // this.bookModel.getBookFxObjectProperty().descriptionProperty().bind(this.descTextArea.textProperty());
       // this.bookModel.getBookFxObjectProperty().ratingProperty().bind(this.ratingSlider.valueProperty());
       // this.bookModel.getBookFxObjectProperty().isbnProperty().bind(this.isbnTextField.textProperty());
      //  this.bookModel.getBookFxObjectProperty().releaseDateProperty().bind(this.releaseDataPicker.valueProperty());

        this.releaseDataPicker.valueProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().releaseDateProperty());
        this.isbnTextField.textProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().isbnProperty());
        this.ratingSlider.valueProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().ratingProperty());
        this.descTextArea.textProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().descriptionProperty());
        this.titleTextField.textProperty().bindBidirectional( this.bookModel.getBookFxObjectProperty().titleProperty());
        this.authorComboBox.valueProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().authorFxProperty());
        this.categoryComboBox.valueProperty().bindBidirectional( this.bookModel.getBookFxObjectProperty().categoryFxProperty());

        //powiąanie comboboxów po inicjalizacji z listami które są w BookModel
        this.categoryComboBox.setItems(this.bookModel.getCategoryFxObservableList());
        this.authorComboBox.setItems(this.bookModel.getAuthorFxObservableList());
    }

    public void addBookOnAction() {

        System.out.println(this.bookModel.getBookFxObjectProperty().toString());
        try {
            this.bookModel.saveBookInDatabase();
            clearFields();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    private void clearFields() {
        this.categoryComboBox.getSelectionModel().clearSelection();
        this.authorComboBox.getSelectionModel().clearSelection();
        this.titleTextField.clear();
        this.descTextArea.clear();
        this.ratingSlider.setValue(1);
        this.isbnTextField.clear();
        this.releaseDataPicker.getEditor().clear();
    }

    public BookModel getBookModel() {
        return bookModel;
    }
}
