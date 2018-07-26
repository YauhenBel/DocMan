package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Documents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CollectionDocuments implements ListDocuments {

    private ObservableList<Documents> docList =
            FXCollections.observableArrayList();
    @Override
    public void add(Documents documents, String id_user) {
        //docList.add(documents);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/docman?useUnicode=true&characterEncoding=utf-8";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                Integer typeWork = 0;
                String sql_query = "INSERT INTO `documents` (`id_doc`, `name_doc`, `id_user`) VALUES(NULL,"
                        + "'" + documents.getName_doc() + "', "
                        + "'" + id_user + "')";
                System.out.println(sql_query);
                int rs = stmt.executeUpdate(sql_query);
                System.out.println(rs);
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Documents documents) {


    }

    @Override
    public void delete(Documents documents) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/docman?useUnicode=true&characterEncoding=utf-8";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);

            try {
                Statement stmt = con.createStatement();
                Integer typeWork = 0;
                String sql_query = "DELETE FROM `documents`"
                        + " WHERE `id_doc` = " + documents.getId_doc();
                System.out.println(sql_query);
                int rs = stmt.executeUpdate(sql_query);
                System.out.println(rs);
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        docList.remove(documents);
    }


    @Override
    public void fillData(String id_user) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/docman";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "SELECT * FROM `documents` WHERE `id_user` = '" + id_user + "'";
                System.out.println(sql_query);
                ResultSet rs = stmt.executeQuery(sql_query);

                while (rs.next()) {
                    String str = "{\nid_doc = " + rs.getString("id_doc")
                            + ",\nname_doc = " + rs.getString("name_doc")
                            + ",\nid_user = " + rs.getString("id_user") + ";\n}";
                    System.out.println("info: " + str);
                    Integer xId_doc = Integer.parseInt(rs.getString("id_doc"));
                    String xNameDoc =rs.getString("name_doc");
                    Integer xId_user = Integer.parseInt(rs.getString("id_user"));

                    docList.add(new Documents(xId_doc, xNameDoc, xId_user));
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
    public ObservableList<Documents> getDocList() {
        return docList;
    }

    public void addNewSelect(Documents documents){
        //добавляем новую запись

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/docman";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "SELECT * FROM `documents` WHERE `name_doc` = '" + documents.getName_doc() + "'";
                System.out.println(sql_query);
                ResultSet rs = stmt.executeQuery(sql_query);

                while (rs.next()) {
                    String str = "{\nid_doc = " + rs.getString("id_doc")
                            + ",\nname_doc = " + rs.getString("name_doc")
                            + ",\nid_user = " + rs.getString("id_user") + ";\n}";
                    System.out.println("info: " + str);
                    Integer xId_doc = Integer.parseInt(rs.getString("id_doc"));
                    String xNameDoc =rs.getString("name_doc");
                    Integer xId_user = Integer.parseInt(rs.getString("id_user"));

                    docList.add(new Documents(xId_doc, xNameDoc, xId_user));
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
}
