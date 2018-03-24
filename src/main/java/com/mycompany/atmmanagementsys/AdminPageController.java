package com.mycompany.atmmanagementsys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AdminPageController implements Initializable {
    @FXML
    private Button loadcusinfo;
     @FXML
    private Image image;
     @FXML
    private TextField num;
     @FXML
    private TableView<CustomerData> customertable;
    @FXML
    private TableColumn<CustomerData,Integer> cusid;
    @FXML
    private TableColumn<CustomerData,String> cusname;
    @FXML
    private TableColumn<CustomerData,String> cusaddress;
    @FXML
    private TableColumn<CustomerData,String> cusemail;
    @FXML
    private TableColumn<CustomerData,String> cusphone;
    @FXML
    private TableColumn<CustomerData,Integer> cusbalance;
    private ObservableList<CustomerData>data;
    @FXML
    private Label welcome;
    String AdminID;
    @FXML
    private ImageView adminimage;
    @FXML
    private Label adminname;
    @FXML
    private Label adminid;
    public void GetAdminID(String id) throws SQLException, FileNotFoundException, IOException{
        AdminID = id;
        Connection con = DbConnection.Connection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM admins WHERE id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            adminname.setText(rs.getString("name"));
            adminid.setText(rs.getString("id"));
            InputStream is = rs.getBinaryStream("image");
            OutputStream os = new FileOutputStream(new File("adminimage.jpeg"));
            byte[] content = new byte[1024];
            int s = 0;
            while((s= is.read(content))!= -1){
            os.write(content, 0, s);
            }
            Image image = new Image("file:adminimage.jpeg");
            adminimage.setImage(image);
            adminimage.setFitWidth(248);
            adminimage.setFitHeight(186);
            Circle clip = new Circle(93,93,93);
            adminimage.setClip(clip);
        }
        ps.close();
        rs.close();
        con.close();
    }
    @FXML
    public void LoadCustomerData(ActionEvent event) throws SQLException{
    Connection con = DbConnection.Connection();
    data = FXCollections.observableArrayList();
    PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    data.add(new CustomerData(rs.getInt("id"),rs.getString("name"),rs.getString("address"),rs.getString("email"),rs.getString("Phone"),rs.getInt("balance")));
    }
            cusid.setCellValueFactory(new PropertyValueFactory<CustomerData,Integer>("CustomerId"));
            cusname.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerName"));
            cusaddress.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerAddress"));
            cusemail.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerEmail"));
            cusphone.setCellValueFactory(new PropertyValueFactory<CustomerData,String>("CustomerPhone"));
            cusbalance.setCellValueFactory(new PropertyValueFactory<CustomerData,Integer>("CustomerBalance"));
            customertable.setItems(null);
            customertable.setItems(data);
            ps.close();
            rs.close();
            con.close();
    }
     @FXML
    public void AddCustomerData(ActionEvent event) throws IOException{
    Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AddCustomer.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/AddCustomer.css");
        Image icon = new Image("/icons/adduser.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Add Customer Page");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void EditCustomerData(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/EditCustomer.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/EditCustomer.css");
        Image icon = new Image("/icons/edituser.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Edit Customer Page");
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void DeleteCustomerData(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/DeleteCustomer.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/DeleteCustomer.css");
        Image icon = new Image("/icons/deleteuser.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Delete Customer Page");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void ResetPassword(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/PasswordReset.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/PasswordReset.css");
        Image icon = new Image("/icons/Password.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setTitle("Passwor Reset Page");
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
