package Order.Modal.model;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Setter
@Getter
public class ModelProduct {

    public ModelProduct(Icon icon, String name, String category) {
        this.icon = icon;
        this.name = name;
        this.category = category;
    }
    private Icon icon;
    private String name;
    private String category;
}
