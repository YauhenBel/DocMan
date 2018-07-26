package objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MessDocs {
    private SimpleStringProperty idMess = new SimpleStringProperty();
    private SimpleStringProperty id_user = new SimpleStringProperty();
    private SimpleStringProperty userName = new SimpleStringProperty();
    private SimpleStringProperty idDoc = new SimpleStringProperty();
    private SimpleStringProperty docName = new SimpleStringProperty();
    private SimpleStringProperty listen = new SimpleStringProperty();
    private SimpleStringProperty start = new SimpleStringProperty();
    private SimpleStringProperty finish = new SimpleStringProperty();
    private SimpleStringProperty close = new SimpleStringProperty();

    public MessDocs(){

    }

    public MessDocs(String _idMess, String _id_user, String _userName,
                    String _idDoc, String _docName, String _listen, String _start,
                    String _finish, String _close){
        this.idMess = new SimpleStringProperty(_idMess);
        this.id_user = new SimpleStringProperty(_id_user);
        this.userName = new SimpleStringProperty(_userName);
        this.idDoc = new SimpleStringProperty(_idDoc);
        this.docName = new SimpleStringProperty(_docName);
        this.listen = new SimpleStringProperty(_listen);
        this.start = new SimpleStringProperty(_start);
        this.finish = new SimpleStringProperty(_finish);
        this.close = new SimpleStringProperty(_close);
    }

    public void setIdMess(String idMess) {
        this.idMess.set(idMess);
    }

    public String getIdMess() {
        return idMess.get();
    }

    public void setid_user(String userArriv) {
        this.id_user.set(userArriv);
    }

    public String getUserArriv() {
        return id_user.get();
    }

    public void setArrName(String arrName) {
        this.userName.set(arrName);
    }

    public String getArrName() {
        return userName.get();
    }

    public void setIdDoc(String idDoc) {
        this.idDoc.set(idDoc);
    }

    public String getIdDoc() {
        return idDoc.get();
    }

    public void setDocName(String docName) {
        this.docName.set(docName);
    }

    public String getDocName() {
        return docName.get();
    }

    public void setListen(String listen) {
        this.listen.set(listen);
    }

    public String getListen() {
        return listen.get();
    }

    public void setStart(String start) {
        this.start.set(start);
    }

    public String getStart() {
        return start.get();
    }

    public void setFinish(String finish) {
        this.finish.set(finish);
    }

    public String getFinish() {
        return finish.get();
    }

    public void setClose(String close) {
        this.close.set(close);
    }

    public String getClose() {
        return close.get();
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public SimpleStringProperty docNameProperty() {
        return docName;
    }

    public SimpleStringProperty listenProperty() {
        if (listen.get().equals("0")){
            return new SimpleStringProperty("Нет");
        } else {
            return new SimpleStringProperty("Да");
        }
        //return listen;
    }

    public SimpleStringProperty startProperty() {
        if (start.get().equals("0")){
            return new SimpleStringProperty("Нет");
        } else {
            return new SimpleStringProperty("Да");
        }
    }

    public SimpleStringProperty finishProperty() {
        if (finish.get().equals("0")){
            return new SimpleStringProperty("Нет");
        } else {
            return new SimpleStringProperty("Да");
        }
    }

    public SimpleStringProperty closeProperty() {
        if (close.get().equals("0")){
            return new SimpleStringProperty("Нет");
        } else {
            return new SimpleStringProperty("Да");
        }
    }

}
