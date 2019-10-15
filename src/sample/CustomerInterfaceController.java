package sample;

import ATM.CustomerATM;
import User.BankSystemUser;
import User.Customer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Map;


public class CustomerInterfaceController {

    @FXML
    TableView table;
    @FXML
    Button insertCardBtn;

    CustomerATM atm;

    public CustomerInterfaceController() {
        atm = new CustomerATM();
    }

    public void onInsertCardClick(Event event) {
        insertCardBtn.setDisable(true);

        TableColumn<String, Customer> column0 = new TableColumn<>("ID");
        column0.setCellValueFactory(new PropertyValueFactory<String, Customer>("ID"));

        TableColumn<String, Customer> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(new PropertyValueFactory<String, Customer>("firstName"));

        TableColumn<String, Customer> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(new PropertyValueFactory<String, Customer>("lastName"));

        TableColumn<String, Customer> column3 = new TableColumn<>("Card Number");
        column3.setCellValueFactory(new PropertyValueFactory<String, Customer>("cardNumber"));

        table.getColumns().add(column0);
        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);

        for(Map.Entry<String, BankSystemUser> entry : atm.getBankSystemUsers().entrySet()) {
            BankSystemUser bankSystemUser = entry.getValue();
            if(bankSystemUser instanceof  Customer) {
                bankSystemUser = (Customer)bankSystemUser;
                table.getItems().add(bankSystemUser);
            }
        }
    }
}
