//Main
package Main;

import Classes.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static Classes.Inventory.incrementPartID;
/** The main class launches the main screen the first time and loads
 *  some initial data that was used for testing purposes.*/
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root, 1085, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {



        //Inserted test data
        InHouse screw = new InHouse(incrementPartID(), "Screw", .99, 10, 1, 15, 991);
        InHouse nail = new InHouse(incrementPartID(), "Nail", .85, 10, 1, 15, 9913);
        InHouse tap = new InHouse(incrementPartID(), "tap", 2.99, 10, 1, 15, 9914);
        Outsourced nailGun = new Outsourced(incrementPartID(),"Nail Gun", 139.99,5, 1, 7,"Dewalt");
        Inventory.addPart(screw);
        Inventory.addPart(nail);
        Inventory.addPart(tap);
        Inventory.addPart((nailGun));



        Product house = new Product(incrementPartID(), "House", 10000, 1, 1, 5);

        house.addAssociatedPart(screw);
        house.addAssociatedPart(nail);
        Inventory.addProduct(house);






        launch(args);
    }
}
