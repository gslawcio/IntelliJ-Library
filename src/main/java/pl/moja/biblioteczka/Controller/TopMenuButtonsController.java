package pl.moja.biblioteczka.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class TopMenuButtonsController {

    public static final String LIBRARY_FXML = "/fxml/Library.fxml";
    public static final String LIST_BOOKS_FXML = "/fxml/ListBooks.fxml";
    public static final String STATISTICS_FXML = "/fxml/Statistics.fxml";
    public static final String ADD_BOOK_FXML = "/fxml/AddBook.fxml";
    public static final String ADD_CATEGORY_FXML = "/fxml/AddCategory.fxml";
    public static final String ADD_AUTHOR_FXML = "/fxml/AddAuthor.fxml";



    private MainController mainController; //pole main controllera i tworzymy seter

    @FXML
    private ToggleGroup toggleButtons;

    @FXML
    public void openLibrary() {
        mainController.setCenter(LIBRARY_FXML);    }
    @FXML
    public void openListBooks() {
        mainController.setCenter(LIST_BOOKS_FXML);
    }
    @FXML
    public void openStatistics() {
        mainController.setCenter(STATISTICS_FXML);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    @FXML
    public void addBook() {
        ResetToggleButton();
        mainController.setCenter(ADD_BOOK_FXML);
    }


    @FXML
    public void addCategory() {
        ResetToggleButton();
        mainController.setCenter(ADD_CATEGORY_FXML);
    }
    @FXML
    public void addAuthor() {
        ResetToggleButton();
        mainController.setCenter(ADD_AUTHOR_FXML);

    }

    private void ResetToggleButton() {          // metoda resetująca przyciski, aby był tylko jeden użyty
        if(toggleButtons.getSelectedToggle()!=null){
            toggleButtons.getSelectedToggle().setSelected(false);
        }
    }



}
