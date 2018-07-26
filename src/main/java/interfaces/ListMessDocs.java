package interfaces;

import javafx.collections.ObservableList;
import objects.MessDocs;
import java.sql.SQLException;

public interface ListMessDocs {

    void upload(String id_user) throws SQLException;

    void uploadinput(String id_user);

    ObservableList<MessDocs> getMessDocList();

    void listen(String id_mess);

    void start(String id_mess);

    void finish(String id_mess);

    void close(String id_mess);

    void delete(MessDocs messDocs);

    void clear();
}
