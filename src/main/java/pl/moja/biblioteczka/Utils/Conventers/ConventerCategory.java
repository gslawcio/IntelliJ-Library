package pl.moja.biblioteczka.Utils.Conventers;

import pl.moja.biblioteczka.DateBase.models.Category;
import pl.moja.biblioteczka.modelFx.CategoryFx;

public class ConventerCategory {

    // odpowiedzila za konwerotanie z category na categoryFx i na odwrót
    public static CategoryFx convertToCategoryFx(Category category){
        CategoryFx categoryFx = new CategoryFx();
        categoryFx.setId(category.getId());
        categoryFx.setName(category.getName());
        return categoryFx;
    }

}
