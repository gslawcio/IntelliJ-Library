package pl.moja.biblioteczka.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import pl.moja.biblioteczka.DateBase.dao.BookDao;
import pl.moja.biblioteczka.DateBase.dao.CategoryDao;
import pl.moja.biblioteczka.DateBase.dbuitls.DbManager;
import pl.moja.biblioteczka.DateBase.models.Book;
import pl.moja.biblioteczka.DateBase.models.Category;
import pl.moja.biblioteczka.Utils.Conventers.ConventerCategory;
import pl.moja.biblioteczka.Utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.List;


public class CategoryModel {
    // obsługa logiki , któa będzie wywoływana w kontrolerze . rozdziela bazę damych od fx


    private ObservableList<CategoryFx> categoryList = FXCollections.observableArrayList();  //ta lista będzi podłączona do ComboBoxa
    private ObjectProperty<CategoryFx> category = new SimpleObjectProperty<>();             //przytrzymuje obecnie wybrany element w ComboBoxie
    private TreeItem<String> root = new TreeItem<>();                                    //główny katalog drzewA



    public void saveCategoryInDataBase(String name) throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao(); //????
     //   DbManager.getConnectionSource();            //???
        Category category = new Category();
        category.setName(name);
            categoryDao.createOrUpdate(category);
      //  DbManager.closeConnectionSource();
        init();                                 //przgląda bazę i wyświetlaną się w ComboBox odrazu po wpisaniu kategorie
    }
    // obsługuje zapis kategori do bazy danych, stworzenie kategorii i zapis


    //wypełnienie listy elementami z bazy danych. Inicjalizuje model danymi
    public void init() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao(); //????  Połącznie do bazy danych
      //  DbManager.getConnectionSource();
        List<Category> categories = categoryDao.queryForAll(Category.class);
        initCategoryList(categories);
        initRoot(categories);
      //  DbManager.closeConnectionSource();
    }

    private void initRoot(List<Category> categories) {  //initializacja root. Przesyłamy listę kategorii
        this.root.getChildren().clear();

        categories.forEach(c->{         //każa kategoria będzie jednym Item. w pętli pobieramy każdą kategorię
            TreeItem<String> categoryItem = new TreeItem<>(c.getName());    //tworzymy od początku nowy TreeItem , który inicjalizowany jest nawą kategorii c.getName()
            c.getBooks().forEach(b->{
                categoryItem.getChildren().add(new TreeItem<>(b.getTitle()));
            });
            root.getChildren().add(categoryItem);               //i dodajemy do naszego root-a categoryItem
        });
    }

    private void initCategoryList(List<Category> categories) {
        this.categoryList.clear();                  //czyścimy i nie duplikują się elementy
        categories.forEach(c-> {
            CategoryFx categoryFx = ConventerCategory.convertToCategoryFx(c);   //Wywołanie metody convertToCategoryFx
            this.categoryList.add(categoryFx);

        });
    }

    public  void deleteCategoryId() throws ApplicationException, SQLException {
        CategoryDao categoryDao = new CategoryDao(); //????  Połącznie do bazy danych
     //   DbManager.getConnectionSource();
        categoryDao.deleteById(Category.class, category.getValue().getId());
      //  DbManager.closeConnectionSource();
        BookDao bookDao = new BookDao();
        bookDao.deleteByColumnName(Book.CATEGORY_ID , category.getValue().getId());
        init();
    }
    //metoda update w bazie danych
    public void updataCategoryInDataBase() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao(); //????  Połącznie do bazy danych
     //   DbManager.getConnectionSource();
        Category tempCategory = categoryDao.findById(Category.class, getCategory().getId());
        tempCategory.setName(getCategory().getName());
            categoryDao.createOrUpdate(tempCategory);
      //  DbManager.closeConnectionSource();
        init();
    }



    public ObservableList<CategoryFx> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ObservableList<CategoryFx> categoryList) {
        this.categoryList = categoryList;
    }

    public CategoryFx getCategory() {
        return category.get();
    }

    public ObjectProperty<CategoryFx> categoryProperty() {
        return category;
    }

    public void setCategory(CategoryFx category) {
        this.category.set(category);
    }

    public TreeItem<String> getRoot() {
        return root;
    }

    public void setRoot(TreeItem<String> root) {
        this.root = root;
    }
}