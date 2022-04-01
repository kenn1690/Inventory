//ModifyProductController
package Controllers;

import Classes.InHouse;
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

//import static Classes.Inventory.incrementPartID;
/** The modify product controller class will take user input from the
 *  modifyProductView under the View folder to modify an existing product.*/
public class ModifyProductController implements Initializable {
    @FXML
    private TextField searchFieldModifyProduct;
    @FXML
    private TableView modifyAssociatedPartsTableView;
    @FXML
    private TableColumn pulledPartToProductIdCol;
    @FXML
    private TableColumn pulledPartToProductNameCol;
    @FXML
    private TableColumn pulledPartToProductPriceCol;
    @FXML
    private TableColumn pulledPartToProductStockCol;
    @FXML
    private TableView modifyPartIDTableView;
    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn priceCol;
    @FXML
    private TableColumn stockCol;
    @FXML
    private TextField productIDTxt;
    @FXML
    private TextField modifiedProductNameTxt;
    @FXML
    private TextField modifiedProductPriceDbl;
    @FXML
    private TextField modifiedProductStockInt;
    @FXML
    private TextField modifiedProductMinInt;
    @FXML
    private TextField modifiedProductMaxInt;

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private Product modifiedProduct = null;
    /** This takes the selected product from main screen and loads its information
     *  into the respected fields and displays its associated parts in the associated parts table.
     *  These fields are editable and the associtated parts can be removed/added too to update/modify the product.
     *  @param productToModify The product selected from the main screen to modify. */
    public void receiveProductInfo(Product productToModify) {
        modifiedProduct = productToModify;
        productIDTxt.setText(String.valueOf(productToModify.getProductID()));
        modifiedProductNameTxt.setText(productToModify.getProductName());
        modifiedProductPriceDbl.setText(String.valueOf(productToModify.getProductPrice()));
        modifiedProductStockInt.setText(String.valueOf(productToModify.getProductStock()));
        modifiedProductMinInt.setText(String.valueOf(productToModify.getMinProducts()));
        modifiedProductMaxInt.setText(String.valueOf(productToModify.getMaxProducts()));
        associatedParts = modifiedProduct.getAssociatedParts();
        modifyAssociatedPartsTableView.setItems(productToModify.getAssociatedParts());
        System.out.println(productToModify.getProductPrice());
        System.out.println(productToModify.getAssociatedParts());


    }
    /** When this view initializes, it will load the part table with all of the part data and a
     *  associated parts table that displays all of the parts associated to the product.
     *  It also has labels and user text fields to modify the current information. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("To Modify Product");
        modifyPartIDTableView.setItems(Inventory.getPartList());
        modifyPartIDTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


        pulledPartToProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        pulledPartToProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        pulledPartToProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        pulledPartToProductStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
    /** This method will take the user's back to the main Screen.
     * @param actionEvent is controlled by a button action event. */
    public void toFirst(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1085, 400);
        stage.setScene(scene);
        stage.show();
    }
    /** This method will attempt to save what was entered into the ModifyProductView view form.
     *  It will be pre-populated with the information from the recievedProductInfo method above.
     *  Also, the associated parts table will be pre-populated with all the parts associated to that product.
     *  It goes through various validations to confirm the fields are filled out correctly.
     *  Example, confirms max is a number, is larger or equal to min and stock is less or equal to the max.
     *  @param actionEvent is controlled by a button action event. */
    public void onActionUpdateProduct(ActionEvent actionEvent) throws IOException{
        try{
            boolean isValid = true;
            int count = 0;
            int id = Integer.parseInt(productIDTxt.getText());
            String name = modifiedProductNameTxt.getText();
            double price = Double.parseDouble(modifiedProductPriceDbl.getText());
            int stock = Integer.parseInt(modifiedProductStockInt.getText());
            int min = Integer.parseInt(modifiedProductMinInt.getText());
            int max = Integer.parseInt(modifiedProductMaxInt.getText());
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
                Inventory.updateProduct(id, productToSave);
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
    /** This takes the data from the pre-populated part table and adds it to the to the associated parts table that initializes with the associated parts
     *  from the product that is brought in through the receiveProductInfo class above.
     *  The associated parts table holds the parts that will be updated for that product.
     *  @param actionEvent is controlled by a button action event.*/
    public void onActionAddPartToProduct(ActionEvent actionEvent) {
        Part partToBeAdded = (Part)modifyPartIDTableView.getSelectionModel().getSelectedItem();
        if(partToBeAdded != null) {
            associatedParts.add(partToBeAdded);
            modifyAssociatedPartsTableView.setItems(associatedParts);
        }
        else{
            alert.setHeaderText("Error");
            alert.setContentText("Please make a valid selection");
            alert.showAndWait();
        }
    }
    /** This takes the part that was added/pre-populated to the associated parts table and removes it.
     * @param actionEvent is controlled by a button action event. */
    public void onActionDeletePartFromProduct(ActionEvent actionEvent) {
        Part partToBeDeleted = (Part) modifyAssociatedPartsTableView.getSelectionModel().getSelectedItem();
        if (partToBeDeleted != null){
            alertConfirm.setTitle("Confirmation Dialog");
            alertConfirm.setHeaderText("Look, a Confirmation Dialog");
            alertConfirm.setContentText("Remove part from product list?");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK){
                modifiedProduct.deleteAssociatedPart(partToBeDeleted);
                modifyAssociatedPartsTableView.setItems(modifiedProduct.getAssociatedParts());
            }
            else if (result.get() == ButtonType.CANCEL) {
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
    /** This class will search the part table view. It uses the Inventory class to first do a partial
     *  name search before searching by ID, which is also found in the inventory class.
     *  The text that is searched is whatever the user input into
     *  the search text field. It uses an array list to hold the found products to then present in the parts
     *  view table.
     *  @param actionEvent is controlled by a text field action event. */
    public void onActionSearchPartsInModifyView(ActionEvent actionEvent) {
        String searched = searchFieldModifyProduct.getText();
        ObservableList<Part> searchedParts = Inventory.lookUpPartialPart(searched);
        if(searchedParts.size()== 0){
            try {
                int partID = Integer.parseInt(searched);
                Part part = Inventory.lookUpPart(partID);
                if (part != null) {
                    searchedParts.add(part);
                }
            }
            catch (NumberFormatException e){ }
        }
        modifyPartIDTableView.setItems(searchedParts);
        if(searchedParts.isEmpty()){
            modifyPartIDTableView.setItems(Inventory.getPartList());
        }
    }
}
