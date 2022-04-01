//Main controller
package Controllers;
import Classes.Inventory;
import Classes.Part;
import Classes.Product;
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
/** This controller audits the home/main screen the user sees.
 *  The user is able to manipulate and add the inventory data for parts
 *  and products from this screen.*/
public class MainController implements Initializable{
    @FXML
    private TableView productIDTableView;
    @FXML
    private TextField productSearchField;
    @FXML
    private TableColumn productIDCol;
    @FXML
    private TableColumn productNameCol;
    @FXML
    private TableColumn productPriceCol;
    @FXML
    private TableColumn productStockCol;
    @FXML
    private TextField searchField;
    @FXML
    private Label theLabel;
    @FXML
    private TableView<Part> partIDTableView;
    @FXML
    private TableColumn<Part, Integer> idCol;
    @FXML
    private TableColumn<Part, String> nameCol;
    @FXML
    private TableColumn<Part, Double> priceCol;
    @FXML
    private TableColumn<Part, Integer> stockCol;
    private Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
    private Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
    /** When this view initializes, it will load the part table with all of the part data and a
     *  product Table that displays all of the products. When changes are made, the table in the main
     *  screen reflects the modifications. This screen has several button functions for the user to
     *  manipulate the parts and products that are populated. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Program Start");
        partIDTableView.setItems(Inventory.getPartList());
        partIDTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productIDTableView.setItems(Inventory.getProductList());
        productIDTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("productStock"));


    }
    /** On clicking this button, it will take the user to the add part screen where the
     *  user can input a new part and new part data.
     *  @param actionEvent  is controlled by a button action event. */
    @FXML
    public void onActionAddPart(ActionEvent actionEvent) throws IOException{
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/View/AddPartScreen.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Add Part Screen");
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){
        }
    }
    /** On clicking this button, it will take the user to the add product screen where the
     *  user can input a new part and new part data.
     *  @param actionEvent  is controlled by a button action event. */
    public void onActionAddProduct(ActionEvent actionEvent) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/AddProductScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 812, 550);
            stage.setTitle("Add Product Screen");
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /** On clicking this button, it will ask the user to confirm if they would like to
     *  deleted this part. After confirming, the part will be removed from the respected
     *  table view.
     *  @param actionEvent  is controlled by a button action event. */
    @FXML
    public void onActionDeletePart(ActionEvent actionEvent) throws IOException{
        try {
            if(partIDTableView.getSelectionModel().getSelectedItem() != null) {
                alertConfirmation.setTitle("Warning");
                alertConfirmation.setHeaderText("Warning");
                alertConfirmation.setContentText("Are you ok with this?");
                Optional<ButtonType> result = alertConfirmation.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Inventory.deletePart(partIDTableView.getSelectionModel().getSelectedItem());
                }
                else if (result.get() == ButtonType.CANCEL) {
                    alertInformation.setTitle("Warning");
                    alertInformation.setHeaderText("Did not delete");
                    alertInformation.showAndWait();
                }
            }
            else{
                alertInformation.setHeaderText("Error");
                alertInformation.setContentText("Please make a valid selection");
                alertInformation.showAndWait();
            }
        }
        catch(NullPointerException npe){

        }
    }
    /** On clicking this button, it will ask the user to confirm if they would like to
     *  deleted this product. After confirming, the product will be removed from the respected
     *  table view.
     *  @param actionEvent  is controlled by a button action event. */
    public void onActionDeleteProduct(ActionEvent actionEvent) {
        Product checkingForAssociatedParts = (Product) productIDTableView.getSelectionModel().getSelectedItem();
        try {

            System.out.println(checkingForAssociatedParts.getAssociatedParts());
            if(checkingForAssociatedParts.getAssociatedParts().isEmpty()) {
                alertConfirmation.setTitle("Warning");
                alertConfirmation.setHeaderText("Warning");
                alertConfirmation.setContentText("Are you ok with this?");
                Optional<ButtonType> result = alertConfirmation.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(checkingForAssociatedParts);
                } else if (result.get() == ButtonType.CANCEL) {
                    alertInformation.setTitle("Warning");
                    alertInformation.setHeaderText("Did not delete");
                    alertInformation.showAndWait();
                }
            }
            else {
                alertInformation.setTitle("Warning");
                alertInformation.setContentText("Please delete all associated parts");
                alertInformation.showAndWait();
            }
        }
        catch(NullPointerException npe){
            alertInformation.setTitle("Warning");
            alertInformation.setContentText("Please select a part");
            alertInformation.showAndWait();
        }
    }
    /** When clicking this button, a part must be selected from the home screen.
     *  It then takes that part information and loads it in the ModifyPartScreen.
     *  From here the user can change the information that is already
     *  saved to that part. This also has an instance of the ModifyPartController class
     *  so it can pass data upon from the main screen to the modify part screen.
     *  @param actionEvent is controlled by a button action event. */
    @FXML
    public void onActionModifyPart(ActionEvent actionEvent) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyPartScreen.fxml"));
            loader.load();
            ModifyPartController MPCController = loader.getController();
            MPCController.receivePartInfo(partIDTableView.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Part Screen");
            stage.setScene(new Scene(scene));
        }
        catch(IOException | NullPointerException e){
            alertInformation.setTitle("Warning");
            alertInformation.setContentText("Please select a part");
            alertInformation.showAndWait();
        }
    }
    /** When clicking this button, a product must be selected from the home screen.
     *  It then takes that product information and loads it in the ModifyProductScreen.
     *  From here the user can change the information that is already
     *  saved to that product. This also has an instance of the ModifyProductController class
     *  so it can pass data from the main screen to the modify product screen.
     *  @param actionEvent is controlled by a button action event. */
    public void onActionModifyProduct(ActionEvent actionEvent) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyProductScreen.fxml"));
            loader.load();
            ModifyProductController MProductController = loader.getController();
            MProductController.receiveProductInfo((Product) productIDTableView.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Product Screen");
            stage.setScene(new Scene(scene));
        }
        catch(IOException |NullPointerException e){
            alertInformation.setTitle("Warning");
            alertInformation.setContentText("Please select a part");
            alertInformation.showAndWait();
        }
    }
    /** The text field takes in input and then first passes it to
     *  the lookUpPartialPart from the Inventory class to do a partial name search.
     *  If the partial name search returns zero, it will then do parse the input to
     *  an int and see if the int is a part ID. The search will return the full part
     *  list if no objects are found.
     *  @param actionEvent is controlled by a text field and user input. */
    public void onActionLookUpPart(ActionEvent actionEvent) {
        String searched = searchField.getText();
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
        partIDTableView.setItems(searchedParts);
        if(searchedParts.isEmpty()){
            partIDTableView.setItems(Inventory.getPartList());
        }
    }
    /** The text field takes in input and then first passes it to
     *  the lookUpPartialPart from the Inventory class to do a partial name search.
     *  If the partial name search returns zero, it will then do parse the input to
     *  an int and see if the int is a product ID. The search will return the full product
     *  list if no objects are found.
     *  @param actionEvent is controlled by a text field and user input.*/
    public void onActionLookUpProduct(ActionEvent actionEvent) {
        String searched = productSearchField.getText();
        ObservableList<Product> searchedProducts = Inventory.lookUpPartialProduct(searched);
        if(searchedProducts.size()== 0){
            try {
                int productID = Integer.parseInt(searched);
                Product product = Inventory.lookUpProduct(productID);
                if (product != null) {
                    searchedProducts.add(product);
                }
            }
            catch (NumberFormatException e){
            }
        }
        productIDTableView.setItems(searchedProducts);
        if(searchedProducts.isEmpty()){
            productIDTableView.setItems(Inventory.getProductList());
        }
    }

    /** Exits the application. */
    public void onActionExitProgram(ActionEvent actionEvent) {
        System.exit(0);
    }
}
