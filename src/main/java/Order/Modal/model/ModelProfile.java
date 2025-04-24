package Order.Modal.model;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Setter
@Getter
public class ModelProfile {

    public ModelProfile(Icon icon, String name, String location) {
        this.icon = icon;
        this.name = name;
        this.location = location;
    }
    private Icon icon;
    private String name;
    private String location;
}
