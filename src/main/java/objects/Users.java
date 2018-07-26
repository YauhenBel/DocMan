package objects;

import javafx.beans.property.SimpleStringProperty;

public class Users {
    private SimpleStringProperty id_user = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();

    public Users(){}

    public Users(String id, String _name){
        this.id_user = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(_name);
    }

    public void setId_user(String id_user) {
        this.id_user.set(id_user);
    }

    public String getId_user() {
        return id_user.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return name.get();
    }
}
