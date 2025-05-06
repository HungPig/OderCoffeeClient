package Order.Modal.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ModelEmployee {


    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModelProduct getProfile() {
        return profile;
    }

    public void setProfile(ModelProduct profile) {
        this.profile = profile;
    }

    public ModelEmployee( double salary, String position, String description, ModelProduct profile) {
        this.salary = salary;
        this.position = position;
        this.description = description;
        this.profile = profile;
    }

    private double salary;
    private String position;
    private String description;
    private ModelProduct profile;

    public Object[] toTableRowBasic(int row) {
        NumberFormat nf = new DecimalFormat("$ #,##0.##");
        return new Object[]{ row, profile.getName(), profile.getCategory(),  nf.format(salary), position, description};
    }

    public Object[] toTableRowCustom(int row) {
        NumberFormat nf = new DecimalFormat("$ #,##0.##");
        return new Object[]{false, row, profile,  nf.format(salary), position, description, profile};
    }
}
