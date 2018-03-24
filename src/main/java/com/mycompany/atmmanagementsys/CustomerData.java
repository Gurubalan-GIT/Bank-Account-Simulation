
package com.mycompany.atmmanagementsys;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerData {
    private final IntegerProperty CustomerId;
    private final StringProperty CustomerName;
    private final StringProperty CustomerAddress;
    private final StringProperty CustomerEmail;
    private final StringProperty CustomerPhone;
    private final IntegerProperty CustomerBalance;
    public CustomerData(Integer CustomerId,String CustomerName,String CustomerAddress,String CustomerEmail,String CustomerPhone,Integer CustomerBalance) {
        this.CustomerId = new SimpleIntegerProperty(CustomerId);
        this.CustomerName = new SimpleStringProperty(CustomerName);
        this.CustomerAddress = new SimpleStringProperty(CustomerAddress);
        this.CustomerEmail = new SimpleStringProperty(CustomerEmail);
        this.CustomerPhone = new SimpleStringProperty(CustomerPhone);
        this.CustomerBalance = new SimpleIntegerProperty(CustomerBalance);
    }
     public Integer getCustomerId(){
    return CustomerId.get();
    }
    public String getCustomerName(){
    return CustomerName.get();
    }
    public String getCustomerAddress(){
    return CustomerAddress.get();
    }
    public String getCustomerEmail(){
    return CustomerEmail.get();
    }
    public String getCustomerPhone(){
    return CustomerPhone.get();
    }
     public Integer getCustomerBalance(){
    return CustomerBalance.get();
    }
}
