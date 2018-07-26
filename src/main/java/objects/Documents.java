package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Documents {

    private SimpleIntegerProperty id_doc = new SimpleIntegerProperty();
    private SimpleStringProperty name_doc = new SimpleStringProperty();
    private SimpleIntegerProperty id_user = new SimpleIntegerProperty();


    public Documents(){

    }

    public Documents(Integer _id_doc, String _name_doc, Integer _id_user){
        this.id_doc = new SimpleIntegerProperty(_id_doc);
        this.name_doc = new SimpleStringProperty(_name_doc);
        this.id_user = new SimpleIntegerProperty(_id_user);
    }

    public void setId_doc(int id_doc) {
        this.id_doc.set(id_doc);
    }

    public int getId_doc() {
        return id_doc.get();
    }

    public void setName_doc(String name_doc) {
        this.name_doc.set(name_doc);
    }

    public String getName_doc() {
        return name_doc.get();
    }

    public void setId_user(int id_user) {
        this.id_user.set(id_user);
    }

    public int getId_user() {
        return id_user.get();
    }

    public SimpleStringProperty name_docProperty() {
        return name_doc;
    }
}
