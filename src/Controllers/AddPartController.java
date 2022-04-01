//add part controller
package Controllers;
import Classes.InHouse;
import Classes.Inventory;
import Classes.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Classes.Inventory.incrementPartID;

/** The add part controller class will take user input from the
 *  AddPartScreen under the View folder to create a new part.*/
public class AddPartController implements Initializable{
    @FXML
    private Label priceLbl;
    @FXML
    private Label minLbl;
    @FXML
    private Label stockLbl;
    @FXML
    private Label maxLbl;
    @FXML
    private Label nameLbl;
    @FXML
    private Label partIDLbl;
    @FXML
    private RadioButton inHouseRbtn;
    @FXML
    private RadioButton outSourcedRbtn;
    @FXML
    private TextField partIDTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField priceDbl;
    @FXML
    private TextField stockTxt;
    @FXML
    private TextField minInt;
    @FXML
    private TextField maxInt;
    @FXML
    private TextField partTypeTxt;
    @FXML
    private Label partTypeLbl;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("To Add Product");
    }
    /** This method will take the user's back to the main Screen.
     * @param actionEvent is controlled by a button action event.*/
    @FXML
    public void toFirst(ActionEvent actionEvent) throws IOException{
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1085, 400);
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){
            alert.setHeaderText("Error");
            alert.setContentText("Please Exit application and retry");
            alert.showAndWait();
        }
    }
    /** This method will attempt to save what was entered into the AddPartScreen view form. It
     *  goes through various validations to confirm the fields are filled out correctly.
     *  Example, confirms stock is a number and is between or equal to min and max.
     *  Also, it will check if the part is an in house or out sourced part via a radio
     *  button. If all forms are filled out correctly, it will add the part into
     *  inventory and display it on the main screen.
     *  RUNTIME ERROR was found here. If an alpha character was input to one of the fields
     *  that required an integer, a numberFormatException was thrown. I fixed this by including
     *  a try catch statement. The try has the code bits to save a new part and the catch block
     *  catches the numberFormatException error and prompts the user to correct the mistake.
     *  @param actionEvent is controlled by a button action event. */
    @FXML
    public void onActionSavePart(ActionEvent actionEvent) throws IOException{
        try{
            boolean isValid = true;
            int count = 0;
            boolean partTypeRbtn;
            int id = incrementPartID();
            String name = nameTxt.getText();
            double price = Double.parseDouble(priceDbl.getText());

            int stock =  Integer.parseInt(stockTxt.getText());
            int min = Integer.parseInt(minInt.getText());
            int max = Integer.parseInt(maxInt.getText());
            if (min < 0){
                count ++;
                alert.setHeaderText("Error");
                alert.setContentText("Please enter valid values for Min.\nMust be larger than zero but less than Max and Stock");
                alert.showAndWait();
            }
            if(max < min) {
                count++;
                alert.setHeaderText("Error");
                alert.setContentText("Please enter valid values for Max.\nMust be larger or equal to min");
                alert.showAndWait();
            }
            if (stock < 0 || stock < min || stock > max){
                count ++;
                alert.setHeaderText("Error");
                alert.setContentText("Please enter valid values for Stock.\nMust be between min and max values");
                alert.showAndWait();
            }
            String partType = partTypeTxt.getText();

            if(inHouseRbtn.isSelected()){
                partTypeRbtn = true;
            }
            else{
                partTypeRbtn = false;
            }
            if(count > 0){
                isValid = false;
            }
            if(partTypeRbtn && isValid) {
                int machineID = Integer.parseInt(partType);
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
                toFirst(actionEvent);
            }
            else if(partTypeRbtn == false && isValid){
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, partType));
                toFirst(actionEvent);
            }
        }

        catch(NumberFormatException e){
            System.out.println("Please Enter valid values in text field.");
            System.out.println("Exception: " + e);
            alert.setHeaderText("Error");
            alert.setContentText("Please Enter valid values in text field.\nAll fields must have entries\nID values can't be enterd\n" +
                    "Name and Company Name must be a string\nPrice may have a period\nStock, Min, Max, Machine iD must be a number");
            alert.showAndWait();
        }
    }
    /** This method will change what the viewer sees in the Part Type label field
     *  when a certain radio button is selected. The radio button has its own on
     *  action event. The viewer will now see/have to enter a Machine ID.
     *  @param actionEvent is controlled by a radio button action event.*/
    public void onActionChangeToInHouse(ActionEvent actionEvent) {
        partTypeLbl.setText("Machine iD");
    }
    /** This method will change what the viewer sees in the Part Type label field
     *  when a certain radio button is selected. The radio button has its own on
     *  action event. The viewer will now see/have to enter a Company Name.
     *  @param actionEvent is controlled by a radio button action event.*/
    public void onActionChangeToOutSourced(ActionEvent actionEvent) {
        partTypeLbl.setText("Company Name");
    }
}
