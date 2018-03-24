package com.mycompany.atmmanagementsys;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class PasswordResetController implements Initializable {
    @FXML
    private TextField cusid;
    @FXML
    private TextField cusname;
    @FXML
    private TextArea confirmation;
    String Email, Password;
    @FXML
    public void PassReset(ActionEvent event) throws SQLException {
        Random rnd = new Random();
        int pass = 1000 + rnd.nextInt(9000);
        Password = "Your New PassWord Is " + Integer.toString(pass);
        if (cusid.getText().isEmpty()) {
            confirmation.setText("Please Fill Up The ID First");
        } else {
            try {
                Connection con = DbConnection.Connection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM users Where id = ?");
                ps.setInt(1, Integer.parseInt(cusid.getText()));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Email = rs.getString("email");
                    PreparedStatement pss = con.prepareStatement("UPDATE users SET password = ? WHERE id = '" + cusid.getText() + "'");
                    pss.setString(1, Integer.toString(pass));
                    int i = pss.executeUpdate();
                    if (i > 0) {
                        confirmation.setText("A New Random Password Is Set To This ID"+"\n");
                        final String username = "give your email address"; // you have to change your security level
                        final String password = "give your email password";
                        Properties props = new Properties();
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.starttls.enable", "true");
                        props.put("mail.smtp.host", "smtp.gmail.com");
                        props.put("mail.smtp.port", "587");
                        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
                        try {
                            Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress("give your email address"));
                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Email));
                            message.setSubject("Password Reset");
                            message.setText(Password);
                            Transport.send(message);
                            confirmation.appendText("This New Password Has Been Sent To Customer's Email");
                        } catch (MessagingException e) {
                            confirmation.setText("Could Not Send The Email , Please Contract With An Admin "+"\n"+ "New Password :"+ pass);
                        }
                    } else {
                        confirmation.setText("Failed To Change Password , Please Check Customer ID");
                    }
                } else {
                    confirmation.setText("Please Enter A Valid Customer ID");
                }
                ps.close();
                con.close();
                rs.close();
            } catch (NumberFormatException | SQLException e) {
                confirmation.setText("DataBase Eror Or Invalid Number Format");
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
