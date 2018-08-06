package pl.moja.biblioteczka.DateBase.dao;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import pl.moja.biblioteczka.DateBase.models.Book;
import pl.moja.biblioteczka.Utils.exceptions.ApplicationException;

import java.sql.SQLException;

/**
 * Created by ZacznijProgramowac.
 * https://www.youtube.com/zacznijprogramowac
 * http://zacznijprogramowac.net/
 */
public class BookDao extends CommonDao {

    public BookDao() {
        super();
    }

    public void deleteByColumnName(String columnName, int id) throws ApplicationException, SQLException {
        Dao<Book, Object> dao = getDao(Book.class);
        DeleteBuilder<Book, Object> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq(columnName, id);
        dao.delete(deleteBuilder.prepare());
    }

}
