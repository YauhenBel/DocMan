package controllers;

import interfaces.CollectionDocuments;
import interfaces.CollectionMessDocs;
import interfaces.CollectionUsers;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.Documents;
import objects.MessDocs;
import objects.Users;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class MainWindow implements Initializable {
    static final String DOWNLOAD_URL = "http://localhost:8080/download";
    static final String OPEN_URL = "http://localhost:8080/open";
    static final String DEL_URL = "http://localhost:8080/delete";
    static final int BUFFER_SIZE = 4096;
    public String faleName = "";
    public Label labelName;
    public Button btnCreate;
    public Button btnOpen;
    public Button btnDownload;
    public Button btnDelete;
    public TableView documentsTable;
    public TableColumn<Documents, String> columnsdoc;
    public Label labelCount;
    public ChoiceBox choiceUser;
    public Button btnSend;
    public TableView outTable;
    public TableColumn outWhoSend;
    public TableColumn outDocument;
    public TableColumn outListen;
    public TableColumn outAccept;
    public TableColumn outFinish;
    public TableColumn outClose;
    public Label outName;
    public Button outBtnOpen;
    public Button outBtnFinish;
    public Button outBtnClose;
    public Button outBtnDel;
    public Label outCount;
    public Button outAccepts;
    public TableView inTable;
    public TableColumn inColFrom;
    public TableColumn inColDoc;
    public TableColumn inColListen;
    public TableColumn inColAccept;
    public TableColumn inColFinish;
    public TableColumn inColClose;
    public Label inName;
    public Button inbtnOpen;
    public Button inbtnAccept;
    public Button inbtnFinish;
    public Button inbtnRefresh;
    public Label inCount;
    public Button outBtnRefresh;
    CollectionDocuments collectionDocuments = new CollectionDocuments();
    CollectionUsers collectionUsers = new CollectionUsers();
    CollectionMessDocs collectionMessDocs = new CollectionMessDocs();
    CollectionMessDocs collectionMessDocsInput = new CollectionMessDocs();
    private CreateDocument createDocument;
    private static String FXMLSection = "../layouts/CreateDocument.fxml";
    private Stage primaryStage;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Parent fxmlNewDoc;
    private Stage mainStage;
    BufferedReader reader;
    URL url;
    private String id_user;
    private String name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize");
        columnsdoc.setCellValueFactory(new PropertyValueFactory<Documents, String>("name_doc"));

        outWhoSend.setCellValueFactory(new PropertyValueFactory<MessDocs, String>("userName"));
        outDocument.setCellValueFactory(new PropertyValueFactory<MessDocs, String>("docName"));
        outListen.setCellValueFactory(new PropertyValueFactory<MessDocs, String>("listen"));
        outAccept.setCellValueFactory(new PropertyValueFactory<MessDocs, String>("start"));
        outFinish.setCellValueFactory(new PropertyValueFactory<MessDocs, String>("finish"));
        outClose.setCellValueFactory(new PropertyValueFactory<MessDocs, String>("close"));

        inColFrom.setCellValueFactory(new PropertyValueFactory<MessDocs, String>("userName"));
        inColDoc.setCellValueFactory(new PropertyValueFactory<MessDocs, String>("docName"));
        inColListen.setCellValueFactory(new PropertyValueFactory<MessDocs, String>("listen"));
        inColAccept.setCellValueFactory(new PropertyValueFactory<MessDocs, String>("start"));
        inColFinish.setCellValueFactory(new PropertyValueFactory<MessDocs, String>("finish"));
        inColClose.setCellValueFactory(new PropertyValueFactory<MessDocs, String>("close"));

        initListeners();
        initLoader();
        documentsTable.setEditable(true);
        collectionUsers.fillData();
        collectionUsers.print();
        choiceUser.setItems(collectionUsers.getUserList());

    }

    public void setInfo(String id, String name) throws SQLException {
        this.id_user = id;
        this.name = name;
        labelName.setText(name);
        outName.setText(name);
        inName.setText(name);
        fillData(id_user);
    }

    public void fillData(String id_user) throws SQLException {
        collectionDocuments.fillData(id_user);
        collectionMessDocs.upload(id_user);
        collectionMessDocsInput.uploadinput(id_user);

        documentsTable.setItems(collectionDocuments.getDocList());

        outTable.setItems(collectionMessDocs.getMessDocList());

        inTable.setItems(collectionMessDocsInput.getMessDocList());
    }

    public void initListeners(){
        collectionDocuments.getDocList().addListener(new ListChangeListener<Documents>() {
            @Override
            public void onChanged(Change<? extends Documents> c) {
                updateCountLabel();
            }
        });

        collectionMessDocs.getMessDocList().addListener(new ListChangeListener<MessDocs>() {
            @Override
            public void onChanged(Change<? extends MessDocs> c) {updateCountLabel();}
        });

        collectionMessDocsInput.getMessDocList().addListener(new ListChangeListener<MessDocs>() {
            @Override
            public void onChanged(Change<? extends MessDocs> c) {updateCountLabel();}
        });

        //здесь команда открытия файла по двойному клику
    }

    private void updateCountLabel() {
        labelCount.setText("Количество записей: "
                + collectionDocuments.getDocList().size());

        outCount.setText("Количество записей: "
                + collectionMessDocs.getMessDocList().size());

        inCount.setText("Количество записей: "
                + collectionMessDocsInput.getMessDocList().size());
    }


    public void tfSearchAct(KeyEvent keyEvent) {
    }

    public void tfSearchActMouse(MouseEvent mouseEvent) {
    }


    public void ActionButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
        Object object = actionEvent.getSource();

        Documents selectDocument = (Documents) documentsTable.getSelectionModel().getSelectedItem();

        Users users1 = (Users) choiceUser.getSelectionModel().getSelectedItem();

        MessDocs selectMessDoc = (MessDocs) outTable.getSelectionModel().getSelectedItem();

        MessDocs selecteMessDocIn = (MessDocs) inTable.getSelectionModel().getSelectedItem();


        if (!(object instanceof Button)){
            return;
        }

        Button button = (Button) object;

        switch (button.getId()){
            case "btnCreate":
                createDocument.setDocuments(null);
                createGui();
                collectionDocuments.add(createDocument.getDocuments(), id_user);
                collectionDocuments.addNewSelect(createDocument.getDocuments());
                System.out.println("btnCreate");
                break;
            case "btnOpen":
                System.out.println("btnOpen");
                OpenFile(selectDocument.getName_doc());
                break;
            case "btnDownload":
                DownloadDocument(selectDocument.getName_doc());
                System.out.println("btnDownload");
                break;
            case "btnDelete":
                DelDoc(selectDocument.getName_doc());
                collectionDocuments.delete(selectDocument);
                System.out.println("btnDelete");
                break;
            case "btnSend":
                System.out.println("Кому = " + users1.getName());
                System.out.println("Что = " + selectDocument.getName_doc());
                senDoc(id_user, users1.getId_user(), String.valueOf(selectDocument.getId_doc()));
                collectionMessDocs.clear();
                collectionMessDocs.upload(id_user);
                System.out.println("btnSend");
                break;
            case "outBtnOpen":
                OpenFile(selectMessDoc.getDocName());
                selectMessDoc.setListen("1");
                collectionMessDocs.listen(selectMessDoc.getIdMess());
                outTable.refresh();

                System.out.println(selectMessDoc.getListen());
                System.out.println("outBtnOpen");
                break;
            case "outAccepts":
                selectMessDoc.setStart("1");
                collectionMessDocs.start(selectMessDoc.getIdMess());
                outTable.refresh();

                System.out.println("outBtnOpen");
                break;
            case "outBtnFinish":
                selectMessDoc.setFinish("1");
                collectionMessDocs.finish(selectMessDoc.getIdMess());
                outTable.refresh();
                System.out.println("outBtnFinish");
                break;
            case "outBtnClose":
                selectMessDoc.setClose("1");
                collectionMessDocs.close(selectMessDoc.getIdMess());
                outTable.refresh();
                System.out.println("outBtnClose");
                break;
            case "outBtnRefresh":
                collectionMessDocs.clear();
                collectionMessDocs.upload(id_user);
                System.out.println("outBtnRefresh");
                break;
            case "outBtnDel":
                collectionMessDocs.delete(selectMessDoc);
                System.out.println("outBtnDel");
                break;

            case "inbtnOpen":
                OpenFile(selecteMessDocIn.getDocName());
                selecteMessDocIn.setListen("1");
                collectionMessDocsInput.listen(selecteMessDocIn.getIdMess());
                inTable.refresh();
                System.out.println("inbtnOpen");
                break;
            case "inbtnAccept":
                System.out.println("inbtnAccept");
                selecteMessDocIn.setStart("1");
                collectionMessDocsInput.start(selecteMessDocIn.getIdMess());
                inTable.refresh();
                break;
            case "inbtnFinish":
                selecteMessDocIn.setFinish("1");
                collectionMessDocsInput.finish(selecteMessDocIn.getIdMess());
                inTable.refresh();

                System.out.println("inbtnFinish");
                break;
            case "inbtnRefresh":
                collectionMessDocsInput.clear();
                collectionMessDocsInput.uploadinput(id_user);
                System.out.println("inbtnRefresh");
                break;
        }
    }

    public void OpenFile(String fileName) throws IOException {
        url = new URL(OPEN_URL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        // sets file name as a HTTP header
        httpConn.setRequestProperty("fileName", java.lang.String.valueOf(fileName));

        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // reads server's response
            reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            java.lang.String response = reader.readLine();
            System.out.println("Server's response: " + response);
        } else {
            System.out.println("Server returned non-OK code: " + responseCode);
        }
    }

    public void DownloadDocument(String faleName) throws IOException {
        //Данный метод скачивает файл с сервера
        //File uploadFile = new File(filePath);

        System.out.println("Download File Name: " + faleName);

        // creates a HTTP connection
        url = new URL(DOWNLOAD_URL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("GET");
        // sets file name as a HTTP header
        httpConn.setRequestProperty("fileName", faleName);

        // always check HTTP response code from server

        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream in = httpConn.getInputStream();
                 FileOutputStream out = new FileOutputStream(faleName))
            {
                byte[] b = new byte[BUFFER_SIZE];
                int count;
                while ((count = in.read(b)) > 0)
                {
                    out.write(b, 0, count);
                }
                reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                String response = reader.readLine();
                System.out.println("Файл загружен.");
            }
        } else {
            System.out.println("Ошибка загрузки файла.");
        }
    }

    public void DelDoc(String fileName) throws IOException {
        url = new URL(DEL_URL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        // sets file name as a HTTP header
        httpConn.setRequestProperty("fileName", fileName);

        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // reads server's response
            reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String response = reader.readLine();
            System.out.println("Server's response: " + response);
        } else {
            System.out.println("Server returned non-OK code: " + responseCode);
        }
    }

    private void createGui() throws IOException {
        if (primaryStage == null){
            primaryStage = new Stage();
            primaryStage.setTitle("Новый док");
            primaryStage.setScene(new Scene(fxmlNewDoc));
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.initOwner(mainStage);
            primaryStage.setMaxWidth(1020);
            primaryStage.setMaxHeight(660);
            primaryStage.setMinHeight(primaryStage.getHeight());
            primaryStage.setMinWidth(primaryStage.getWidth());

        }
        primaryStage.showAndWait();
    }
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    private void initLoader(){
        fxmlLoader.setLocation(getClass().getResource(FXMLSection));
        try {
            fxmlNewDoc = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        createDocument = fxmlLoader.getController();

    }

    private void senDoc(String userSend, String userArriv, String idDoc){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/docman?useUnicode=true&characterEncoding=utf-8";
            String login = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, login, password);
            try {
                Statement stmt = con.createStatement();
                String sql_query = "INSERT INTO `mess_doc` (`id_mess`, `user_send`, `user_arriv`, " +
                        "`id_doc`, `listen`, `start`, `finish`, `close`) " +
                        "VALUES (NULL,"
                        + "'" + userSend + "', "
                        + "'" + userArriv + "', "
                        + "'" + idDoc + "',"
                        + "'0', '0', '0', '0')";
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
}
