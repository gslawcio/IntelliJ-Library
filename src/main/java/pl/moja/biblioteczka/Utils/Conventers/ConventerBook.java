package pl.moja.biblioteczka.Utils.Conventers;

import pl.moja.biblioteczka.DateBase.models.Book;
import pl.moja.biblioteczka.Utils.Utils;
import pl.moja.biblioteczka.modelFx.BookFx;

public class ConventerBook {

    public static Book convertToBook(BookFx bookFx){

        Book book = new Book();
        book.setId(bookFx.getId());
        book.setTitle(bookFx.getTitle());
        book.setDescription(bookFx.getDescription());
        book.setRating(bookFx.getRating());
        book.setIsbn(bookFx.getIsbn());
        book.setReleaseDate(Utils.convertToDate(bookFx.getReleaseDate()));
        book.setAddedDate(Utils.convertToDate(bookFx.getAddedDate()));

        return book;


    }

    public static BookFx convertToBookFX(Book book) {
        BookFx bookFx = new BookFx();
        bookFx.setId(book.getId());
        bookFx.setTitle(book.getTitle());
        bookFx.setDescription(book.getDescription());
        bookFx.setRating(book.getRating());
        bookFx.setIsbn(book.getIsbn());
        bookFx.setReleaseDate(Utils.convertToLocalDate(book.getReleaseDate()));
        bookFx.setAuthorFx(ConventerAuthor.convertToAuthorFx(book.getAuthor()));
        bookFx.setCategoryFx(ConventerCategory.convertToCategoryFx(book.getCategory()));
        return bookFx;
    }
}
