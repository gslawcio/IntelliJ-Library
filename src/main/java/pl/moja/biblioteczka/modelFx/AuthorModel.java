package pl.moja.biblioteczka.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.moja.biblioteczka.DateBase.dao.AuthorDao;
import pl.moja.biblioteczka.DateBase.dao.BookDao;
import pl.moja.biblioteczka.DateBase.dbuitls.DbManager;
import pl.moja.biblioteczka.DateBase.models.Author;
import pl.moja.biblioteczka.DateBase.models.Book;
import pl.moja.biblioteczka.Utils.Conventers.ConventerAuthor;
import pl.moja.biblioteczka.Utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.List;

public class AuthorModel {
        //tworzymy nowy obiekt, który będzie działa na AuthorFX. W prperty inicjalizujemy nowego authora
    private ObjectProperty<AuthorFx> authorFxObjectProperty = new SimpleObjectProperty<>(new AuthorFx());

    //tworzymy nowy obiekt działjacy AuthorFX aby zapisywał do bazy danych(edycja kolumn tabeli autor)
    private ObjectProperty<AuthorFx> authorFxObjectPropertyEdit = new SimpleObjectProperty<>(new AuthorFx());


    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();


    //inicjalizacja tabeli autorów
    public void init() throws ApplicationException {
       AuthorDao authorDao = new AuthorDao();
      //  DbManager.getConnectionSource();
            //tworymy listę authorList i pobieramy z klasy author
        List<Author> authorList = authorDao.queryForAll(Author.class);
        this.authorFxObservableList.clear();
        authorList.forEach(author ->{
            //przerabiamy każdego autora na autoraFx w klasie ConventerAuthor
            AuthorFx authorFx = ConventerAuthor.convertToAuthorFx(author);
            this.authorFxObservableList.add(authorFx);
        } );

     //   DbManager.closeConnectionSource();
    }
    //metoda zapisująca do bazy danych nowe dane po edycji w kolumnie autor
    public void saveAuthorEditInDatabase() throws ApplicationException {
        saveOrUpdate(this.getAuthorFxObjectPropertyEdit());
    }
    //metoda zapisująca do bazy danych nowego autora
    public void saveAuthorInDatabase() throws ApplicationException {
        saveOrUpdate(this.getAuthorFxObjectProperty());
    }


    //metoda zapisująca do bazy danych
    private void saveOrUpdate(AuthorFx authorFxObjectPropertyEdit) throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
      //  DbManager.getConnectionSource();
        Author author = ConventerAuthor.conventerToAuthor(authorFxObjectPropertyEdit);

        authorDao.createOrUpdate(author);

     //   DbManager.closeConnectionSource();
        this.init();  //ponowne załadowanie tabeli autorów
    }

    //metoda usuwająca autora z bazy danych
    public void deleteAuthorInDataBase() throws ApplicationException, SQLException {
        AuthorDao authorDao = new AuthorDao();
    //    DbManager.getConnectionSource();

        authorDao.deleteById(Author.class, this.getAuthorFxObjectPropertyEdit().getId());

        BookDao bookDao = new BookDao();
        bookDao.deleteByColumnName(Book.AUTHOR_ID , this.getAuthorFxObjectPropertyEdit().getId());

    //    DbManager.closeConnectionSource();
         this.init(); //ponowne załadowanie tabeli autorów

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

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public void setAuthorFxObservableList(ObservableList<AuthorFx> authorFxObservableList) {
        this.authorFxObservableList = authorFxObservableList;
    }

    public AuthorFx getAuthorFxObjectPropertyEdit() {
        return authorFxObjectPropertyEdit.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyEditProperty() {
        return authorFxObjectPropertyEdit;
    }

    public void setAuthorFxObjectPropertyEdit(AuthorFx authorFxObjectPropertyEdit) {
        this.authorFxObjectPropertyEdit.set(authorFxObjectPropertyEdit);
    }
}
