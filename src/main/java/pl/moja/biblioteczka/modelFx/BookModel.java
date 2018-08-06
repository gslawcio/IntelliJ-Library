package pl.moja.biblioteczka.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.moja.biblioteczka.DateBase.dao.AuthorDao;
import pl.moja.biblioteczka.DateBase.dao.BookDao;
import pl.moja.biblioteczka.DateBase.dao.CategoryDao;
import pl.moja.biblioteczka.DateBase.dbuitls.DbManager;
import pl.moja.biblioteczka.DateBase.models.Author;
import pl.moja.biblioteczka.DateBase.models.Book;
import pl.moja.biblioteczka.DateBase.models.Category;
import pl.moja.biblioteczka.Utils.Conventers.ConventerAuthor;
import pl.moja.biblioteczka.Utils.Conventers.ConventerBook;
import pl.moja.biblioteczka.Utils.Conventers.ConventerCategory;
import pl.moja.biblioteczka.Utils.exceptions.ApplicationException;

import java.util.List;

public class BookModel {

    //

    private ObjectProperty<BookFx> bookFxObjectProperty = new SimpleObjectProperty<>(new BookFx());

    //initializacja comboboxów
    //tworzymy dwie listy - obiekty przez któe dostaniemy się do autora i kategorii
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();
    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();

    public void init() throws ApplicationException {

        initAuthorList();
        initCategoryList();
    }

    private void initCategoryList() throws ApplicationException {
        //powołujemy categoryDao
        CategoryDao categoryDao = new CategoryDao();
      //  DbManager.getConnectionSource();
        //pobieramy wszystkie kategorie z bazy
        List<Category> categoryList = categoryDao.queryForAll(Category.class);
        categoryFxObservableList.clear();
        categoryList.forEach(c ->{
            CategoryFx categoryFx = ConventerCategory.convertToCategoryFx(c);
            categoryFxObservableList.add(categoryFx);
        });

      //  DbManager.closeConnectionSource();
    }

    private void initAuthorList() throws ApplicationException {
        //powołujemy categoryDao
        AuthorDao authorDao = new AuthorDao();
       // DbManager.getConnectionSource();
        //pobieramy wszystkie kategorie z bazy
        List<Author> authorList = authorDao.queryForAll(Author.class);
        authorFxObservableList.clear();
        authorList.forEach(c ->{
            AuthorFx authorFx = ConventerAuthor.convertToAuthorFx(c);
            authorFxObservableList.add(authorFx);
        });

       // DbManager.closeConnectionSource();

    }

    //metoda odpowiadająca za apis do bazy danych
    public void saveBookInDatabase() throws ApplicationException {

        Book book = ConventerBook.convertToBook(this.getBookFxObjectProperty());

        CategoryDao categoryDao = new CategoryDao();
        Category category = categoryDao.findById(Category.class, this.getBookFxObjectProperty().getCategoryFx().getId());

        book.setCategory(category);

        AuthorDao authorDao = new AuthorDao();
        Author author = authorDao.findById(Author.class , this.getBookFxObjectProperty().getAuthorFx().getId());

        book.setAuthor(author);

        BookDao bookDao = new BookDao();
        bookDao.createOrUpdate(book);

    }

    public BookFx getBookFxObjectProperty() {
        return bookFxObjectProperty.get();
    }

    public ObjectProperty<BookFx> bookFxObjectPropertyProperty() {
        return bookFxObjectProperty;
    }

    public void setBookFxObjectProperty(BookFx bookFxObjectProperty) {
        this.bookFxObjectProperty.set(bookFxObjectProperty);
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public void setAuthorFxObservableList(ObservableList<AuthorFx> authorFxObservableList) {
        this.authorFxObservableList = authorFxObservableList;
    }
}
