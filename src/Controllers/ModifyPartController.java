//modify part controller
package Controllers;
import Classes.InHouse;
import Classes.Inventory;
import Classes.Outsourced;
import Classes.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Classes.Inventory.incrementPartID;

/** The modify product controller class will take user input from the
 *  modifyPartView under the View folder to modify an existing part.*/
public class ModifyPartController implements Initializable{
    @FXML
    private Label modifyMaxLbl;
    @FXML
    private Label modifyPriceLbl;
    @FXML
    private Label modifyMinLbl;
    @FXML
    private Label modifyStockLbl;
    @FXML
    private Label modifySaxLbl;
    @FXML
    private Label modifyNameLbl;
    @FXML
    private Label modifyPartIDLbl;
    @FXML
    private RadioButton modifyInHouseRbtn;
    @FXML
    private RadioButton modifyOutSourcedRbtn;
    @FXML
    private TextField modifyPartIDTxt;
    @FXML
    private TextField modifyNameTxt;
    @FXML
    private TextField modifyPriceDbl;
    @FXML
    private TextField modifyStockTxt;
    @FXML
    private TextField modifyMinInt;
    @FXML
    private TextField modifyMaxInt;
    @FXML
    private TextField modifyPartTypeTxt;
    @FXML
    private Label modifyPartTypeLbl;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

    /** This takes the selected part from main screen and loads its information
     *  into the respected fields to be able to update/modify. It will recognize and change
     *  the radio button indicator based on what instanceof the part is, either in house
     *  or outsourced.
     *  @param partToModify The selected part to modify from the main screen. */
    public void receivePartInfo(Part partToModify) {
        modifyPartIDTxt.setText(String.valueOf(partToModify.getId()));
        modifyNameTxt.setText(partToModify.getName());
        modifyPriceDbl.setText(String.valueOf(partToModify.getPrice()));
        modifyStockTxt.setText(String.valueOf(partToModify.getStock()));
        modifyMinInt.setText(String.valueOf(partToModify.getMin()));
        modifyMaxInt.setText(String.valueOf(partToModify.getMax()));
        if (partToModify instanceof InHouse) {
            modifyInHouseRbtn.setSelected(true);
            modifyPartTypeTxt.setText(String.valueOf(((InHouse) partToModify).getMachineID()));
        }
        if (partToModify instanceof Outsourced) {
            modifyOutSourcedRbtn.setSelected(true);
            modifyPartTypeLbl.setText("Company Name");
            modifyPartTypeTxt.setText(((Outsourced) partToModify).getCompanyName());

        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("To Modify Part");
    }

    /** This method will take the user's back to the main Screen.
     * @param actionEvent is controlled by a button action event. */
    @FXML
    public void toFirst(ActionEvent actionEvent) throws IOException{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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
    /** This method will attempt to save what was entered into the ModifyPartView view form.
     *  It will be pre-populated with the information from the recievedPartInfo method above.
     *  It goes through various validations to confirm the fields are filled out correctly.
     *  Example, confirms max is a number, is larger or equal to min and stock is less or equal to the max.
     *  @param actionEvent is controlled by a button action event. */
    @FXML
    public void onActionUpdatePart(ActionEvent actionEvent) throws IOException{
        try{
            boolean isValid = true;
            int count = 0;
            boolean partTypeRbtn;
            int id = Integer.parseInt(modifyPartIDTxt.getText());
            String name = modifyNameTxt.getText();
            double price = Double.parseDouble(modifyPriceDbl.getText());
            int stock =  Integer.parseInt(modifyStockTxt.getText());
            int min = Integer.parseInt(modifyMinInt.getText());
            int max = Integer.parseInt(modifyMaxInt.getText());
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
            String partType = modifyPartTypeTxt.getText();
            if(modifyInHouseRbtn.isSelected()){
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
                InHouse partToUpdate = new InHouse(id, name, price, stock, min, max, machineID);
                Inventory.updatePart(id, partToUpdate);
                toFirst(actionEvent);
            }
            else if(partTypeRbtn == false && isValid){
                Outsourced partToUpdateOS = new Outsourced(id, name, price, stock, min, max, partType);
                Inventory.updatePart(id, partToUpdateOS);
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
     *  @param actionEvent is controlled by a radio button action event. */
    public void onActionChangeToInHouse(ActionEvent actionEvent) {
        modifyPartTypeLbl.setText("Machine iD");
    }

    /** This method will change what the viewer sees in the Part Type label field
     *  when a certain radio button is selected. The radio button has its own on
     *  action event. The viewer will now see/have to enter a Company Name.
     *  @param actionEvent is controlled by a button action event. */
    public void onActionChangeToOutSourced(ActionEvent actionEvent) {
        modifyPartTypeLbl.setText("Company Name");
    }
}
