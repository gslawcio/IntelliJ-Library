package pl.moja.biblioteczka.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import pl.moja.biblioteczka.Utils.DialogsUtils;
import pl.moja.biblioteczka.Utils.exceptions.ApplicationException;
import pl.moja.biblioteczka.modelFx.AuthorFx;
import pl.moja.biblioteczka.modelFx.AuthorModel;

import java.sql.SQLException;

public class AddAuthorController {

    @FXML   //operuje na authorFx
    private TableView<AuthorFx> authorTableView;
    @FXML
    private TableColumn<AuthorFx, String> nameColumn;
    @FXML
    private TableColumn<AuthorFx, String> surnameColumn;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private Button addAuthorButton;
    @FXML
    private MenuItem deleteMenuItem;


    // najpierw wstrzykujemy autorModel
    private AuthorModel authorModel;



    public void initialize(){
        //nowa instacja tego obiektu
      this.authorModel = new AuthorModel();

      //odwołujemy się do metody init(), która pobierze autorów
        try {
            this.authorModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());       //wiadomość z naszego okna dialogowego
        }
        bindings();
        bindingsTableView();
    }

    private void bindingsTableView() {
        //podpinamy listę do TableView
        this.authorTableView.setItems(this.authorModel.getAuthorFxObservableList());
        //odwołąnie do każdej z kolumn
        this.nameColumn.setCellValueFactory(cellData-> cellData.getValue().nameProperty());
        this.surnameColumn.setCellValueFactory(cellData-> cellData.getValue().surnameProperty());

        //uaktywnienie komórek do edycji
        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        //podpinamy się do klikania w tabeli author. tzn podpinamy się w to co klikęliśmy i przesyłamy(wskakuje) nowa wartość do setAuthorFxObjectPropertyEdit
        this.authorTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
                this.authorModel.setAuthorFxObjectPropertyEdit(newValue);

                }
        );
    }

    private void bindings() {
        //bindujemy go z polem nameTextField
        this.authorModel.authorFxObjectPropertyProperty().get().nameProperty().bind(this.nameTextField.textProperty());
        // z polem surnameTextField
        this.authorModel.authorFxObjectPropertyProperty().get().surnameProperty().bind(this.surnameTextField.textProperty());
        //bindujemy przycisk by był włączony dopiero wtedy gdy coś wpiszemy
        this.addAuthorButton.disableProperty().bind(this.nameTextField.textProperty().isEmpty().or(this.surnameTextField.textProperty().isEmpty()));
        //blokowanie zaznaczenia Usuń na autorach gdy nie ma nic zaznaczonego
        this.deleteMenuItem.disableProperty().bind( this.authorTableView.getSelectionModel().selectedItemProperty().isNull());
    }


    public void addAuthorOnAction() {
        System.out.println(this.authorModel.getAuthorFxObjectProperty().getName());
        System.out.println(this.authorModel.getAuthorFxObjectProperty().getSurname());

        try {
            this.authorModel.saveAuthorInDatabase();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());       //wiadomość z naszego okna dialogowego
        }
        this.nameTextField.clear();
        this.surnameTextField.clear();
    }
    //metoda edycji nazwiska w tabeli autora. metoda otrzymuje każdą komórkę. metoda podpięta do kolumny imię
    public void onEditCommitName(TableColumn.CellEditEvent<AuthorFx,String> authorFxStringCellEditEvent) {

        // odwołujemy się do modelu. pobieramy obiekt, który jest kliknięty i ustawiamy name(pobieramy nową wartość)
        this.authorModel.getAuthorFxObjectPropertyEdit().setName(authorFxStringCellEditEvent.getNewValue());
        updateInDatabase();


    }
    //metoda edycji nazwiska w tabeli autora. metoda podpięta do kolumny nazwisko
    public void onEditCommitSurname(TableColumn.CellEditEvent<AuthorFx,String> authorFxStringCellEditEvent) {
        this.authorModel.getAuthorFxObjectPropertyEdit().setSurname(authorFxStringCellEditEvent.getNewValue());
        updateInDatabase();


    }
    //metoda aktualizująca dane w bazie danych - nazwisko, imię
    private void updateInDatabase() {
        try {
            this.authorModel.saveAuthorEditInDatabase();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());       //wiadomość z naszego okna dialogowego
        }
    }
    //metoda usuwająca autora(klikanie prawym przyciskiem)
    public void deleteAuthorOnAction() {

        try {
            this.authorModel.deleteAuthorInDataBase();
        } catch (ApplicationException | SQLException e){
            DialogsUtils.errorDialog(e.getMessage());       //wiadomość z naszego okna dialogowego
        }

    }



    public TextField getNameTextField() {
        return nameTextField;
    }

    public void setNameTextField(TextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    public TextField getSurnameTextField() {
        return surnameTextField;
    }

    public void setSurnameTextField(TextField surnameTextField) {
        this.surnameTextField = surnameTextField;
    }



}
