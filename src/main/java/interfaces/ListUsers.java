package interfaces;

import javafx.collections.ObservableList;
import objects.Users;

public interface ListUsers {
    void fillData();

    ObservableList<Users> getUserList();

}
