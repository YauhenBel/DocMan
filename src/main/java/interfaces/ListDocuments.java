package interfaces;


import javafx.collections.ObservableList;
import objects.Documents;

public interface ListDocuments {

    void add(Documents documents, String id_user);



     void update(Documents documents);

     void delete(Documents documents);




    void fillData(String id_user);

    ObservableList<Documents> getDocList();
}
