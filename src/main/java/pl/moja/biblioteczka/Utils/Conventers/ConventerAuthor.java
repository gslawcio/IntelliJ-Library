package pl.moja.biblioteczka.Utils.Conventers;

import pl.moja.biblioteczka.DateBase.models.Author;
import pl.moja.biblioteczka.modelFx.AuthorFx;

public class ConventerAuthor {

    public static Author conventerToAuthor(AuthorFx authorFx){

        Author author = new Author();
        author.setId(authorFx.getId());
        author.setName(authorFx.getName());
        author.setSurname(authorFx.getSurname());
        return author;
    }
    //przerabiamy każdego autora na autoraFx
    public static AuthorFx convertToAuthorFx(Author author){

        AuthorFx authorFx = new AuthorFx();
        authorFx.setId(author.getId());
        authorFx.setName(author.getName());
        authorFx.setSurname(author.getSurname());
        return authorFx;
    }

}
