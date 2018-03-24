package com.mycompany.atmmanagementsys;

import java.io.File;
import java.io.FileInputStream;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditCustomerController implements Initializable {
    @FXML
    private ImageView cusimage;
    @FXML
    private TextField cusid;
    @FXML
    private Button load;
    @FXML
    private TextField cusname;
    @FXML
    private TextArea cusaddress;
    @FXML
    private TextField cusemail;
    @FXML
    private TextField cusphone;
    @FXML
    private TextField cusbalance;
    private File file;
    private FileInputStream fis;
    @FXML
    private Button chosepic;
    int cp;
    @FXML
    private Label confirmation;
    @FXML
    public void ChoosePicture(ActionEvent event) {
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            cusimage.setImage(image);
            cp = 1;

        } else {
            cp = 0;
        }
    }
    @FXML
    public void LoadCusInfo(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        try {
            Connection con = DbConnection.Connection();
            PreparedStatement ps = con.prepareStatement("Select * from users where id = ?");
            ps.setInt(1, Integer.parseInt(cusid.getText()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cusname.setText(rs.getString("name"));
                cusaddress.setText(rs.getString("address"));
                cusemail.setText(rs.getString("email"));
                cusphone.setText(rs.getString("phone"));
                cusbalance.setText(Integer.toString(rs.getInt("balance")));
                InputStream img = rs.getBinaryStream("image");
                OutputStream os = new FileOutputStream(new File("userimage.jpg"));
                byte[] content = new byte[1024];
                int s = 0;
                while ((s = img.read(content)) != -1) {
                    os.write(content, 0, s);
                }
                Image image = new Image("file:userimage.jpg");
                cusimage.setImage(image);
            } else {
                confirmation.setText("Customer Not Found");
            }
            ps.close();
            rs.close();
            con.close();
        } catch (IOException | NumberFormatException | SQLException e) {
            confirmation.setText("Please Enter The ID Correctly");
        }
    }
    public void EditCusInfo(ActionEvent event) throws SQLException, FileNotFoundException {
        try {
            if (cusname.getText().isEmpty() || cusaddress.getText().isEmpty() || cusemail.getText().isEmpty() || cusphone.getText().isEmpty() || cusbalance.getText().isEmpty()) {
                confirmation.setText("Please Fill Up Everything");
            } else if (Integer.parseInt(cusbalance.getText()) < 0) {
                confirmation.setText("Please Enter The Balance Correctly");
            } else {
                if (cp == 1) {
                    Connection con = DbConnection.Connection();
                    PreparedStatement ps = con.prepareStatement("UPDATE users SET name = ? ,address = ? ,email = ? , phone = ?,balance = ? , image = ? WHERE id = '" + Integer.parseInt(cusid.getText()) + "'");
                    ps.setString(1, cusname.getText());
                    ps.setString(2, cusaddress.getText());
                    ps.setString(3, cusemail.getText());
                    ps.setString(4, cusphone.getText());
                    ps.setInt(5, Integer.parseInt(cusbalance.getText()));
                    fis = new FileInputStream(file);
                    ps.setBinaryStream(6, (InputStream) fis, (int) file.length());
                    int i = ps.executeUpdate();
                    if (i > 0) {
                        cusname.setText("");
                        cusaddress.setText("");
                        cusemail.setText("");
                        cusphone.setText("");
                        cusbalance.setText("");
                        Image image = new Image("/icons/usericon3.png");
                        cusimage.setImage(image);
                        confirmation.setText("Customer Info Updated Successfully");
                    } else {
                        confirmation.setText("Failed To Update Customer Info");
                    }
                    cp = 0;
                    ps.close();
                    con.close();
                } else if (cp != 1) {
                    Connection con = DbConnection.Connection();
                    PreparedStatement ps = con.prepareStatement("UPDATE users SET name = ? ,address = ? ,email = ? , phone = ?,balance = ? WHERE id = '" + Integer.parseInt(cusid.getText()) + "'");
                    ps.setString(1, cusname.getText());
                    ps.setString(2, cusaddress.getText());
                    ps.setString(3, cusemail.getText());
                    ps.setString(4, cusphone.getText());
                    ps.setInt(5, Integer.parseInt(cusbalance.getText()));
                    int j = ps.executeUpdate();
                    if (j > 0) {
                        cusname.setText("");
                        cusaddress.setText("");
                        cusemail.setText("");
                        cusphone.setText("");
                        cusbalance.setText("");
                        Image image = new Image("/icons/usericon3.png");
                        cusimage.setImage(image);
                        confirmation.setText("Customer Info Updated Successfully");
                    } else {
                        confirmation.setText("Failed To Update Customer Info");
                    }
                    ps.close();
                    con.close();
                }
            }
        } catch (FileNotFoundException | NumberFormatException | SQLException e) {
            confirmation.setText("Please Enter Everything Correctly");
        }
    }
    public void DeleteCusInfo(ActionEvent event) throws SQLException {
        try {
            Connection con = DbConnection.Connection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE id = ?");
            ps.setInt(1, Integer.parseInt(cusid.getText()));
            int k = ps.executeUpdate();
            if (k > 0) {
                cusname.setText("");
                cusaddress.setText("");
                cusemail.setText("");
                cusphone.setText("");
                cusbalance.setText("");
                Image image = new Image("/icons/usericon4.png");
                cusimage.setImage(image);
                confirmation.setText("Successfully Removed The Customer");
            } else {
                confirmation.setText("Failed To Find The Customer");
            }
            ps.close();
            con.close();
        } catch (NumberFormatException | SQLException e) {
            confirmation.setText("Please Enter The ID Correctly");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
