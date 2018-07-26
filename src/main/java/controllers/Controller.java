package controllers;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML public CustomPasswordField TF_passw;
    @FXML public CustomTextField TF_login;
    @FXML public Label label_errors;
    private static String FXMLSection = "../layouts/MainWindow.fxml";
    private Stage primaryStage;
    //MainWindow mainWindow = new MainWindow();

    public void log_out(ActionEvent actionEvent) {
        label_errors.setAlignment(Pos.CENTER);

        if (!TF_login.getText().isEmpty() && !TF_passw.getText().isEmpty()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/docman";
                String login = "root";
                String password = "";
                Connection con = DriverManager.getConnection(url, login, password);
                try {
                    Statement stmt = con.createStatement();
                    String sql_query ="";
                    /*if (TF_login.getText().equals("root") || TF_login.getText().equals("admin")){
                        sql_query = "SELECT * FROM `root_admins` WHERE `login`='" + TF_login.getText()
                                + "' && `passw`='" + TF_passw.getText()+"'";
                    } else {*/
                        sql_query = "SELECT * FROM `users` WHERE `login`='" + TF_login.getText()
                                + "' && `passw`='" + TF_passw.getText()+"'";
                    //}
                    System.out.println(sql_query);
                    ResultSet rs = stmt.executeQuery(sql_query);

                    if (rs.first()) {
                        createGui(rs.getString("id_user"),
                                rs.getString("name"));
                        close_window(actionEvent);
                    }

                    if (!rs.first()) {
                        label_errors.setText("Неправильный логин или пароль!");
                    }

                    while (rs.next()) {
                        String str = "id_users = " + rs.getString("id_user") +
                                "; login = " + rs.getString("login") +
                                "; passw = " + rs.getString("passw") +
                                "; name = "+ rs.getString("name");
                        System.out.println("info: " + str);
                    }
                    rs.close();
                    stmt.close();
                } finally {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (TF_login.getText().isEmpty() && TF_passw.getText().isEmpty()) {
            label_errors.setText("Введите логин и пароль!");
        } else if (!TF_login.getText().isEmpty() && TF_passw.getText().isEmpty()) {
            label_errors.setText("Введите пароль!");
        } else {
            label_errors.setText("Введите логин!");
        }
    }

    private void close_window(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupClearButtonField(TF_login);
        setupClearButtonField(TF_passw);
    }

    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setupClearButtonField(CustomPasswordField customPasswordField){
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customPasswordField, customPasswordField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void createGui(String id, String name) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXMLSection));
        Parent root = fxmlLoader.load();
        MainWindow mainWindow = fxmlLoader.getController();
        mainWindow.setInfo(id, name);
        primaryStage = new Stage();
        mainWindow.setMainStage(primaryStage);

        primaryStage.setTitle("Докумениты");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(primaryStage.getHeight());
        primaryStage.setMaxHeight(primaryStage.getWidth());
    }
}
