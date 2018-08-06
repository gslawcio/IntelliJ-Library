package pl.moja.biblioteczka.modelFx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CategoryFx {
    //klasa odzwiercająca klasę bazy danych DataBase/Category
    //pola danych będą jako propertisy



    private IntegerProperty id =new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    //implementacja metodu toString. w ComboBox żęby wyświetlało dobrze


    @Override
    public String toString() {
        return name.getValue();
    }
}
