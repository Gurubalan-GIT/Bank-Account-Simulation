package com.mycompany.atmmanagementsys;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BalancePageController implements Initializable {
    @FXML
    private TextField balanceinfo;
    @FXML
    private TextArea quotedisbp;
    @FXML
    private TextField withdrawamount;
    @FXML
    private TextField depositamount;
    String UserID;
    String BalanceS;
    @FXML
    private TextField receiverid;
    @FXML
    private TextField trnasamount;
    @FXML
    private PasswordField retypepass;
    @FXML
    private Label dipositconf;
    @FXML
    private Label withdrawinfo;
    @FXML
    private TextArea quotedisdp;
    @FXML
    private TextArea quotediswp;
    @FXML
    private Label transferconf;
    @FXML
    private TextArea quotedisbt;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void getUserID(String Id) {
        UserID = Id;
    }
    public void SetBalance(String Bal) {
        BalanceS = Bal;
        balanceinfo.setText(BalanceS);
        Quotes qt = new Quotes();
        String quote = qt.returnQuotes();
        quotedisbp.setText(quote);
    }
    public void StqDepositPage() {
        Quotes qt = new Quotes();
        String quote = qt.returnQuotes();
        quotedisdp.setText(quote);
    }
    public void StqWithdrawPage() {
        Quotes qt = new Quotes();
        String quote = qt.returnQuotes();
        quotediswp.setText(quote);
    }
    public void StqBalanceTransPage() {
        Quotes qt = new Quotes();
        String quote = qt.returnQuotes();
        quotedisbt.setText(quote);
    }
    @FXML
    public void WithdrawMoney(ActionEvent event) throws SQLException {
        try {
            if (withdrawamount.getText().isEmpty() || Integer.parseInt(withdrawamount.getText()) < 0) {
                withdrawinfo.setText("Please Enter A Valid Amount");
            } else {
                Connection con = DbConnection.Connection();
                PreparedStatement ps = null;
                ResultSet rs = null;
                int balance;
                ps = con.prepareStatement("SELECT * FROM users WHERE id = ?");
                ps.setString(1, UserID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int NewBalance = (rs.getInt("balance") - Integer.parseInt(withdrawamount.getText()));
                    if (NewBalance < 0) {
                        withdrawinfo.setText("Your Account Balance Is Low");
                    } else {
                        ps = con.prepareStatement("UPDATE users SET balance =? WHERE id = '" + UserID + "'");
                        ps.setInt(1, NewBalance);
                        ps.executeUpdate();
                        withdrawinfo.setText("Transaction Successfull");
                    }
                }
                ps.close();
                rs.close();
                con.close();
            }
        } catch (NumberFormatException | SQLException e) {
            withdrawinfo.setText("Invalid Database Or Number Format");
        }
        withdrawamount.setText("");
    }
    @FXML
    public void Deposit(ActionEvent event) throws SQLException {
        try {
            if (depositamount.getText().isEmpty() || Integer.parseInt(depositamount.getText()) < 0) {
                dipositconf.setText("Please Enter A Valid Amount");
            } else {
                Connection con = DbConnection.Connection();
                PreparedStatement ps = null;
                ResultSet rs = null;
                ps = con.prepareStatement("SELECT * FROM users WHERE id = ?");
                ps.setString(1, UserID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int NewBalance = (rs.getInt("balance") + Integer.parseInt(depositamount.getText()));
                    ps = con.prepareStatement("UPDATE users SET balance =? WHERE id = '" + UserID + "'");
                    ps.setInt(1, NewBalance);
                    ps.executeUpdate();
                }
                dipositconf.setText("Cash Has Been Deposited");
                ps.close();
                rs.close();
                con.close();
            }
        } catch (NumberFormatException | SQLException e) {
            dipositconf.setText("Invalid Database Or Number Format");
        }
        depositamount.setText("");
    }
    @FXML
    public void TransferMoney(ActionEvent event) throws SQLException {
        try {
            if (trnasamount.getText().isEmpty() || Integer.parseInt(trnasamount.getText()) < 0 || retypepass.getText().isEmpty() || receiverid.getText().isEmpty()) {
                transferconf.setText("Please Fill Up Everything Correctly.");
            } else {
                Connection con = DbConnection.Connection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id = ? and password = ? ");
                receiverid.setStyle("-fx-border-color: #e65100;-fx-border-width:2;-fx-border-radius:20;-fx-background-radius:22;");
                ps.setString(1, UserID);
                ps.setString(2, retypepass.getText());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    retypepass.setStyle("-fx-border-color: #e65100;-fx-border-width:2;-fx-border-radius:20;-fx-background-radius:22;");
                    int newbalance = rs.getInt("balance") - Integer.parseInt(trnasamount.getText());
                    if (newbalance < 0) {
                        transferconf.setText("You Dont Have Enough Money To Transfer.");
                        trnasamount.setText("");
                        ps.close();
                        rs.close();
                    } else {
                        ps = con.prepareStatement("UPDATE users SET balance = ? WHERE id = '" + UserID + "' ");
                        ps.setInt(1, newbalance);
                        ps.executeUpdate();
                        ps.close();
                        rs.close();
                        PreparedStatement ps2 = con.prepareStatement("SELECT * FROM users WHERE id =?");
                        ps2.setString(1, receiverid.getText());
                        ResultSet rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            int receivernewbalance = rs2.getInt("balance") + Integer.parseInt(trnasamount.getText());
                            ps2 = con.prepareStatement("UPDATE users SET balance =? WHERE id = '" + receiverid.getText() + "' ");
                            ps2.setInt(1, receivernewbalance);
                            ps2.executeUpdate();
                            ps2.close();
                            rs2.close();
                            transferconf.setText("Transfer Successfull");
                            receiverid.setText("");
                            trnasamount.setText("");
                            retypepass.setText("");
                        } else {
                            transferconf.setText("UserID Invalid , Failed To Transfer.");
                            receiverid.setText("");
                            receiverid.setStyle("-fx-border-color:red;-fx-border-width:2;-fx-border-radius:20;-fx-background-radius:22;");
                            PreparedStatement pss = con.prepareStatement("SELECT * FROM users WHERE id =?");
                            pss.setString(1, UserID);
                            ResultSet rss = pss.executeQuery();
                            while (rss.next()) {
                                int addbalance = rss.getInt("Balance") + Integer.parseInt(trnasamount.getText());
                                pss = con.prepareStatement("UPDATE users SET balance = ? WHERE id = '" + UserID + "'");
                                pss.setInt(1, addbalance);
                                pss.executeUpdate();
                            }
                            pss.close();
                            rss.close();
                        }
                    }
                } else {
                    transferconf.setText("Wrong Password Transaction Failed.");
                    retypepass.setStyle("-fx-border-color:red;-fx-border-width:2;-fx-border-radius:20;-fx-background-radius:22;");
                }
                con.close();
            }
        } catch (NumberFormatException | SQLException e) {
            transferconf.setText("Invalid Database Or Number Format");
        }
    }
}
