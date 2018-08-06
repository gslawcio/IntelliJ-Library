package pl.moja.biblioteczka.DateBase.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.moja.biblioteczka.DateBase.dao.AuthorDao;
import pl.moja.biblioteczka.DateBase.dao.BookDao;
import pl.moja.biblioteczka.DateBase.dao.CategoryDao;
import pl.moja.biblioteczka.Utils.Conventers.ConventerAuthor;
import pl.moja.biblioteczka.Utils.Conventers.ConventerBook;
import pl.moja.biblioteczka.Utils.Conventers.ConventerCategory;
import pl.moja.biblioteczka.Utils.exceptions.ApplicationException;
import pl.moja.biblioteczka.modelFx.AuthorFx;
import pl.moja.biblioteczka.modelFx.BookFx;
import pl.moja.biblioteczka.modelFx.CategoryFx;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListBooksModel {

    //model do kontrolera ListBooksController

    private ObservableList<BookFx> observableList = FXCollections.observableArrayList();
    //pola obserwalable List dla autora i categorii
    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();

    //ustawiemy dwa obiekty które będą przechowywały wybrane elementy w comboBoxie
    private ObjectProperty<AuthorFx> authorFxObjectProperty = new SimpleObjectProperty<>();
    private ObjectProperty<CategoryFx> categoryFxObjectProperty = new SimpleObjectProperty<>();

    private List<BookFx> bookFxList = new ArrayList<>();


    public void init() throws ApplicationException {
        BookDao bookDao = new BookDao();
        List<Book> books = bookDao.queryForAll(Book.class);
        bookFxList.clear();
        books.forEach(book -> {
            this.bookFxList.add(ConventerBook.convertToBookFX(book));
        } );
        this.observableList.setAll(bookFxList);
        //inicjalizacja ComboBoksów
        initAuthors();
        initCategory();

    }
    private void initAuthors() throws ApplicationException {

        AuthorDao authorDao = new AuthorDao();
        List<Author> authorList = authorDao.queryForAll(Author.class);
        this.authorFxObservableList.clear();
        authorList.forEach(author -> {
           AuthorFx authorFX = ConventerAuthor.convertToAuthorFx(author);
            this.authorFxObservableList.add(authorFX);
        } );
    }
    private void initCategory() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.queryForAll(Category.class);
        this.categoryFxObservableList.clear();
        categoryList.forEach(category ->{
            CategoryFx categoryFx = ConventerCategory.convertToCategoryFx(category);
            this.categoryFxObservableList.add(categoryFx);

        });
    }

    //metoda filtrująca dane z bazy danych Category i author    Warunki filtrowania
    private Predicate<BookFx> predicateCategory(){
        Predicate<BookFx> predicate = bookFx -> bookFx.getCategoryFx().getId() == getCategoryFxObjectProperty().getId();
        return predicate;
        // lub return bookFx -> bookFx.getCategoryFx().getId() == getCategoryFxObjectProperty().getId();
    }
    private Predicate<BookFx> predicateAuthor(){
        return bookFx -> bookFx.getAuthorFx().getId() == getAuthorFxObjectProperty().getId();
    }

    //metoda któa działa na predicat-ach  które stworzyliśmy
    private void  filterPredicate (Predicate<BookFx> predicate){
        //tworzy nam listę ksiązęk
        List<BookFx> newList = bookFxList.stream().filter(predicate).collect(Collectors.toList());
        this.observableList.setAll(newList);

    }

    public void filterBooksList(){
        if(getAuthorFxObjectProperty() != null && getCategoryFxObjectProperty() != null){
            filterPredicate(predicateAuthor().and(predicateCategory()));
        }else if(getCategoryFxObjectProperty() != null){
            filterPredicate(predicateCategory());
        }else if(getAuthorFxObjectProperty() != null){
            filterPredicate(predicateAuthor());
        }else {
               this.observableList.setAll(this.bookFxList);
        }

    }

    public void deleteBook(BookFx bookFx) throws ApplicationException {
        BookDao bookDao = new BookDao();
        bookDao.deleteById(Book.class, bookFx.getId());
        init();
    }

    public ObservableList<BookFx> getObservableList() {

        return observableList;
    }

    public void setObservableList(ObservableList<BookFx> observableList) {
        this.observableList = observableList;
    }

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public void setAuthorFxObservableList(ObservableList<AuthorFx> authorFxObservableList) {
        this.authorFxObservableList = authorFxObservableList;
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

    public AuthorFx getAuthorFxObjectProperty() {
        return authorFxObjectProperty.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyProperty() {
        return authorFxObjectProperty;
    }

    public void setAuthorFxObjectProperty(AuthorFx authorFxObjectProperty) {
        this.authorFxObjectProperty.set(authorFxObjectProperty);
    }

    public CategoryFx getCategoryFxObjectProperty() {
        return categoryFxObjectProperty.get();
    }

    public ObjectProperty<CategoryFx> categoryFxObjectPropertyProperty() {
        return categoryFxObjectProperty;
    }

    public void setCategoryFxObjectProperty(CategoryFx categoryFxObjectProperty) {
        this.categoryFxObjectProperty.set(categoryFxObjectProperty);
    }
}
