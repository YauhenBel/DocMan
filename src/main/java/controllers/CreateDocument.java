package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.Documents;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;


public class CreateDocument implements Initializable {

    public Button btnOgNameFile;
    public TextField TfName;
    static final String UPLOAD_URL = "http://localhost:8080/upload";
    static final String CREATE_URL = "http://localhost:8080/create";
    static final int BUFFER_SIZE = 4096;
    public Pane paneprogres;
    private Documents documents;
    String textfromlabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneprogres.getStyleClass().add("alert-background-pane");
    }

    public void setDocuments(Documents documents){

        if(documents == null){
            documents = new Documents();
            this.documents = documents;
            return;
        }
        this.documents = documents;
    }

    private void UpdateDoc() throws InterruptedException {
        documents.setName_doc(textfromlabel);

    }

    public Documents getDocuments() {return  documents;}

    private void CreateDoc() throws IOException, InterruptedException {
        URL url = new URL(CREATE_URL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        // sets file name as a HTTP header
        httpConn.setRequestProperty("fileName", textfromlabel);

        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // reads server's response
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String response = reader.readLine();
            System.out.println("Server's response: " + response);
        } else {
            System.out.println("Server returned non-OK code: " + responseCode);
        }

        for (int i = 0; i <2; i++){
            System.out.println(i);
            Thread.sleep(1000);
        }
        paneprogres.setVisible(false);
    }

    private void UploadFile() throws IOException, InterruptedException {

        OutputStream output = new FileOutputStream(textfromlabel);
        output.flush();
        output.close();
        //Process process = Runtime.getRuntime().exec("cmd /c " + textfromlabel);
        //process.waitFor();
        // takes file path from first program's argument
        String filePath = textfromlabel;
        File uploadFile = new File(filePath);
        System.out.println("File to upload: " + filePath);
        // creates a HTTP connection
        URL url = new URL(UPLOAD_URL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        // sets file name as a HTTP header
        httpConn.setRequestProperty("fileName", uploadFile.getName());
        System.out.println("httpConn1: " + httpConn);
        // opens output stream of the HTTP connection for writing data
        OutputStream outputStream = httpConn.getOutputStream();
        // Opens input stream of the file for reading data
        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
        System.out.println("Start writing data...");
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        System.out.println("Data was written.");
        outputStream.close();
        inputStream.close();
        // always check HTTP response code from server
        System.out.println("httpConn: " + httpConn);
        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // reads server's response
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String response = reader.readLine();
            System.out.println("Server's response: " + response);
        } else {
            System.out.println("Server returned non-OK code: " + responseCode);
        }
        for (int i = 0; i <2; i++){
            System.out.println(i);
            Thread.sleep(1000);
        }
        paneprogres.setVisible(false);


    }

    public void btnOkAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        paneprogres.setVisible(true);
        textfromlabel = TfName.getText();
        textfromlabel +=".docx";
        Thread t = new Thread(() -> {
            try {
                CreateDoc();
                UpdateDoc();
                paneprogres.setVisible(false);


                Platform.runLater(new Runnable() {
                    public void run() {
                        close(actionEvent);
                    }
                });


            } catch (Exception e) {
                paneprogres.setVisible(false);
                e.printStackTrace();
            }

        });
        t.start();

        //метод close должен срабатывать после завершения потока


    }

    public void close(ActionEvent actionEvent){
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
