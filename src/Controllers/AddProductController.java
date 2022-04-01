//add product controller
package Controllers;

import Classes.Inventory;
import Classes.Part;
import Classes.Product;
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
import java.util.Optional;
import java.util.ResourceBundle;

import static Classes.Inventory.incrementPartID;

/** The add product controller class will take user input from the
 *  AddProductView under the View folder to create a new product.*/
public class AddProductController implements Initializable {

    @FXML
    private TextField searchFieldAddProduct;
    @FXML
    private TableView addPartToProductTableView;
    @FXML
    private TableColumn addPartToProductIdCol;
    @FXML
    private TableColumn addPartToProductNameCol;
    @FXML
    private TableColumn addPartToProductPriceCol;
    @FXML
    private TableColumn addPartToProductStockCol;
    @FXML
    private TextField productIDTxt;
    @FXML
    private TextField productNameTxt;
    @FXML
    private TextField productPriceDbl;
    @FXML
    private TextField productStockInt;
    @FXML
    private TextField productMinInt;
    @FXML
    private TextField productMaxInt;
    @FXML
    private TableView addPartIDTableView;
    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn priceCol;
    @FXML
    private TableColumn stockCol;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /** When this view initializes, it will load the part table with all of the part data and a blank
     *  table that can be added to. It also has labels and user text fields for input. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("To Add Product");
        addPartIDTableView.setItems(Inventory.getPartList());
        addPartIDTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartToProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartToProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartToProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addPartToProductStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
    /** This method will take the user's back to the main Screen.
     * @param actionEvent is controlled by a button action event.*/
    public void toFirst(ActionEvent actionEvent) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1085, 400);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){

        }
    }
    /** This method will attempt to save what was entered into the AddProductView view form. It
     *  goes through various validations to confirm the fields are filled out correctly.
     *  Example, confirms max is a number, is larger or equal to min and stock is less or equal to the max.
     *  @param actionEvent is controlled by a button action event.*/
    public void onActionSaveProduct(ActionEvent actionEvent) throws IOException{
        try {
            boolean isValid = true;
            int count = 0;
            int id = incrementPartID();
            String name = productNameTxt.getText();
            double price = Double.parseDouble(productPriceDbl.getText());
            int stock = Integer.parseInt(productStockInt.getText());
            int min = Integer.parseInt(productMinInt.getText());
            int max = Integer.parseInt(productMaxInt.getText());

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


            Product productToSave = new Product(id, name, price, stock, min, max);
            for(Part associated : associatedParts){
                productToSave.addAssociatedPart(associated);
            }

            if(count > 0){
                isValid = false;
            }
            if(isValid) {
                Inventory.addProduct(productToSave);
                toFirst(actionEvent);
            }

        }
        catch(NumberFormatException | IOException e){
            System.out.println("Please Enter valid values in text field.");
            System.out.println("Exception: " + e);
            alert.setHeaderText("Error");
            alert.setContentText("Please Enter valid values in text field.\nAll fields must have entries\nID values can't be enterd\n" +
                    "Name and Company Name must be a string\nPrice may have a period\nStock, Min, Max, Machine iD must be a number");
            alert.showAndWait();
        }

    }
    /** This takes the data from the pre-populated part table and adds it to the to the associated parts table that initializes empty.
     *  The associated parts table holds the parts that can be attached to the product.
     *  @param actionEvent is controlled by a button action event.*/
    public void onActionAddPartToProduct(ActionEvent actionEvent) throws IOException{
        try {
            Part partToBeAdded = (Part) addPartIDTableView.getSelectionModel().getSelectedItem();
            if (partToBeAdded != null) {
                associatedParts.add(partToBeAdded);
                addPartToProductTableView.setItems(associatedParts);
                System.out.println(associatedParts);
            } else {
                alert.setHeaderText("Error");
                alert.setContentText("Please make a valid selection");
                alert.showAndWait();
            }
        }
        catch(NullPointerException npe){

        }

    }

    /** This takes the data that was added to the associated parts table and removes it.
     * @param actionEvent is controlled by a button action event. */
    public void onActionDeletePartFromProduct(ActionEvent actionEvent) throws IOException{
        try {
            Part partToBeDeleted = (Part) addPartToProductTableView.getSelectionModel().getSelectedItem();
            if (partToBeDeleted != null) {
                alertConfirm.setTitle("Confirmation Dialog");
                alertConfirm.setHeaderText("Look, a Confirmation Dialog");
                alertConfirm.setContentText("Remove part from product list?");
                Optional<ButtonType> result = alertConfirm.showAndWait();
                if (result.get() == ButtonType.OK) {
                    associatedParts.remove(partToBeDeleted);
                    addPartToProductTableView.setItems(associatedParts);
                } else if (result.get() == ButtonType.CANCEL) {

                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Did not delete");
                    alert.showAndWait();
                } else {
                    alert.setHeaderText("Error");
                    alert.setContentText("Please make a valid selection");
                    alert.showAndWait();
                }
            }
        }
        catch (NullPointerException npe){

        }

    }

    /** This class will search the part table view. It uses the Inventory class to first do a partial
     *  name search before searching by ID, which is also found in the inventory class.
     *  The text that is searched is whatever the user input into
     *  the search text field. It uses an array list to hold the found products to then present in the parts
     *  view table.
     *  @param actionEvent is controlled by a text field action event. */
    public void onActionSearchPartsInAddView(ActionEvent actionEvent) {
        String searched = searchFieldAddProduct.getText();
        ObservableList<Part> searchedParts = Inventory.lookUpPartialPart(searched);
        if(searchedParts.size()== 0){
            try {
                int partID = Integer.parseInt(searched);
                Part part = Inventory.lookUpPart(partID);
                if (part != null) {
                    searchedParts.add(part);
                }
            }
            catch (NumberFormatException e){
            }
        }
        addPartIDTableView.setItems(searchedParts);
        if(searchedParts.isEmpty()){
            addPartIDTableView.setItems(Inventory.getPartList());
        }
    }
}
