package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Documents;
import objects.Users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CollectionUsers implements ListUsers {

    private ObservableList<Users> userList =
            FXCollections.observableArrayList();
    @Override
    public void fillData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/docman";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "SELECT `id_user`, `name` FROM `users`";
                System.out.println(sql_query);
                ResultSet rs = stmt.executeQuery(sql_query);

                while (rs.next()) {
                    String str = "{\nid_user = " + rs.getString("id_user")
                            + ",\nname = " + rs.getString("name") + ";\n}";
                    System.out.println("info: " + str);
                    String xId_user = rs.getString("id_user");
                    String xName =rs.getString("name");
                    userList.add(new Users(xId_user, xName));
                }
                rs.close();
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Users> getUserList() {
        return userList;
    }

    public void print(){
        for (Users users: userList){
            System.out.println(
                  "Print:\n"
                    + "id_user = " + users.getId_user() + ",\n"
                    + "name = " + users.getName() + "\n"
            );
        }
    }
}
