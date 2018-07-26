package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.MessDocs;
import objects.Users;

import java.sql.*;

public class CollectionMessDocs implements ListMessDocs {

    private ObservableList<MessDocs> messDocsList =
            FXCollections.observableArrayList();
    String url = "jdbc:mysql://localhost:3306/docman";
    String login = "root";
    String password = "";

    @Override
    public void upload(String id_user) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "SELECT `mess_doc`.`id_mess`, `mess_doc`.`user_arriv`, `users`.`name`, `mess_doc`.`id_doc`, \n" +
                        "\n" +
                        "`documents`.`name_doc`, `mess_doc`.`listen`, `mess_doc`.`start`, `mess_doc`.`finish`, `mess_doc`.`close` \n" +
                        "\n" +
                        "FROM `users` JOIN `mess_doc` ON `users`.`id_user` = `mess_doc`.`user_arriv`  \n" +
                        "\n" +
                        "INNER JOIN `documents` ON `documents`.`id_doc` = `mess_doc`.`id_doc`\n" +
                        "\n" +
                        "WHERE `mess_doc`.`user_send` = '" + id_user + "'";
                System.out.println(sql_query);
                ResultSet rs = stmt.executeQuery(sql_query);

                while (rs.next()) {
                    String str = "{\nid_mess = " + rs.getString("id_mess")
                            + ",\nuser_arriv = " + rs.getString("user_arriv")
                            + ",\nname = " + rs.getString("name")
                            + ",\nid_doc = " + rs.getString("id_doc")
                            + ",\nname_doc = " + rs.getString("name_doc")
                            + ",\nlisten = " + rs.getString("listen")
                            + ",\nstart = " + rs.getString("start")
                            + ",\nfinish = " + rs.getString("finish")
                            + ",\nclose = " + rs.getString("close")
                            +";\n}";
                    System.out.println("info: " + str);
                    messDocsList.add(new MessDocs(rs.getString("id_mess"), rs.getString("user_arriv"), rs.getString("name"),
                            rs.getString("id_doc"), rs.getString("name_doc"), rs.getString("listen"), rs.getString("start"),
                            rs.getString("finish"), rs.getString("close")));
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
    public void uploadinput(String id_user) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "SELECT `mess_doc`.`id_mess`, `mess_doc`.`user_send`, `users`.`name`, `mess_doc`.`id_doc`, `documents`.`name_doc`,\n" +
                        "\n" +
                        "`mess_doc`.`listen`, `mess_doc`.`start`, `mess_doc`.`finish`, `mess_doc`.`close` FROM `users` JOIN `mess_doc` ON\n" +
                        "\n" +
                        "`users`.`id_user` = `mess_doc`.`user_send` INNER JOIN `documents` ON `documents`.`id_doc` = `mess_doc`.`id_doc` WHERE\n" +
                        "\n" +
                        "`mess_doc`.`user_arriv` = '" + id_user + "'";
                System.out.println(sql_query);
                ResultSet rs = stmt.executeQuery(sql_query);

                while (rs.next()) {
                    String str = "{\nid_mess = " + rs.getString("id_mess")
                            + ",\nuser_send = " + rs.getString("user_send")
                            + ",\nname = " + rs.getString("name")
                            + ",\nid_doc = " + rs.getString("id_doc")
                            + ",\nname_doc = " + rs.getString("name_doc")
                            + ",\nlisten = " + rs.getString("listen")
                            + ",\nstart = " + rs.getString("start")
                            + ",\nfinish = " + rs.getString("finish")
                            + ",\nclose = " + rs.getString("close")
                            +";\n}";
                    System.out.println("info: " + str);
                    messDocsList.add(new MessDocs(rs.getString("id_mess"), rs.getString("user_send"), rs.getString("name"),
                            rs.getString("id_doc"), rs.getString("name_doc"), rs.getString("listen"), rs.getString("start"),
                            rs.getString("finish"), rs.getString("close")));
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
    public ObservableList<MessDocs> getMessDocList() {
        return messDocsList;
    }

    @Override
    public void listen(String id_mess) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "UPDATE `docman`.`mess_doc` SET `listen` = '1' WHERE `mess_doc`.`id_mess` = '" + id_mess + "'";
                System.out.println(sql_query);
                stmt.executeUpdate(sql_query);
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(String id_mess) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "UPDATE `docman`.`mess_doc` SET `start` = '1' WHERE `mess_doc`.`id_mess` = '" + id_mess + "'";
                System.out.println(sql_query);
                stmt.executeUpdate(sql_query);
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finish(String id_mess) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "UPDATE `docman`.`mess_doc` SET `finish` = '1' WHERE `mess_doc`.`id_mess` = '" + id_mess + "'";
                System.out.println(sql_query);
                stmt.executeUpdate(sql_query);
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close(String id_mess) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "UPDATE `docman`.`mess_doc` SET `close` = '1' WHERE `mess_doc`.`id_mess` = '" + id_mess + "'";
                System.out.println(sql_query);
                stmt.executeUpdate(sql_query);
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(MessDocs messDocs) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "DELETE FROM `docman`.`mess_doc` WHERE `mess_doc`.`id_mess` = '" + messDocs.getIdMess() +  "'";
                System.out.println(sql_query);
                stmt.executeUpdate(sql_query);
                stmt.close();
            } finally {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        messDocsList.remove(messDocs);
    }

    @Override
    public void clear() {
        messDocsList.clear();
    }


}
