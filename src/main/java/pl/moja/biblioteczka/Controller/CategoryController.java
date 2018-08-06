package pl.moja.biblioteczka.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.moja.biblioteczka.Utils.DialogsUtils;
import pl.moja.biblioteczka.Utils.exceptions.ApplicationException;
import pl.moja.biblioteczka.modelFx.CategoryFx;
import pl.moja.biblioteczka.modelFx.CategoryModel;

import java.sql.SQLException;

public class CategoryController {
    @FXML
    private Button deleteCategoryButton;
    @FXML
    private Button editCategoryButton;
    @FXML
    private TextField categoryTextField;
    @FXML
    private Button addCategoryButton;
    @FXML
    private TreeView<String> categoryTreeView;   //wyświetlanie listy w kategoriach (dodaj kategorię)
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;  // obsługuje CategoryFx

    private CategoryModel categoryModel;        //powołujemy CategoryModel

    @FXML
    public void initialize() {           // zawsze wywołuje się po konstruktorze
        this.categoryModel = new CategoryModel();
        try {
            this.categoryModel.init();                  //inicjalizacja listy Category
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        this.categoryComboBox.setItems(this.categoryModel.getCategoryList());
        this.categoryTreeView.setRoot(this.categoryModel.getRoot());
        initBindings();
    }

    private void initBindings() {       //włączenie wyłączenie przycisku dodaj kategorię
       this.addCategoryButton.disableProperty().bind(categoryTextField.textProperty().isEmpty());       //wyłączenie przycisku jeśli nic nie zostało wpisane
       this.deleteCategoryButton.disableProperty().bind(this.categoryModel.categoryProperty().isNull());    // wyłączenie przycisku Usuń jeśłi nic nie zostało wybrane
       this.editCategoryButton.disableProperty().bind(this.categoryModel.categoryProperty().isNull());    // wyłączenie przycisku Edit jeśłi nic nie zostało wybrane
    }


    @FXML
    public void addCategoryOnAction() {
        System.out.println(categoryTextField.getText());
        try {
            categoryModel.saveCategoryInDataBase(categoryTextField.getText());
        }catch (ApplicationException e){
            DialogsUtils.errorDialog(e.getMessage());       //wiadomość z naszego okna dialogowego
        }
        categoryTextField.clear();
    }

    //prycisk usunięcia z bazy kategorii
    public void deleteCategoryOnAction() {
        try {
            this.categoryModel.deleteCategoryId();
        } catch (ApplicationException | SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }
    //Metoda na ComboBoxie
    //to to by jak coś wybierzemy żęby pojawiła się w ObjectProperty  - categoey
    public void onActionComboBox() {
        this.categoryModel.setCategory( this.categoryComboBox.getSelectionModel().getSelectedItem());
       //ustawiamy w categoryModel to z ComboBoxa (pobieramy to co zostało w ComboBoxie)
        //każdy wybór w ComboBoxie ustawia w CategoryModel w ObjectProperty<CategoryFx> category
    }

    public void onActionEditCategory()  {
        //nowa zmienna przechowująca nową wartość kategorii = powołujemy z dialogUtils metodę editDialog(wysyłamy starą wartość)
        String newCategoryName = DialogsUtils.editDialog(this.categoryModel.getCategory().getName());
        if(newCategoryName!=null){      //jeżeli coś zotało naciśnięte- sprawdzamy czy to jest null
            this.categoryModel.getCategory().setName(newCategoryName);
            try {
                this.categoryModel.updataCategoryInDataBase();              //metoda update w bazie danych
            } catch (ApplicationException e) {
                DialogsUtils.errorDialog(e.getMessage());       //wiadomość z naszego okna dialogowego
            }
        }
    }
}
