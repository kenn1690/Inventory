//Inventory
package Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** This class handles the inventory for parts and products. Items can be removed,
 added, searched, and updated using this class.
 FUTURE ENHANCEMENT could be done by moving this class and it's fields into a
 database. This way, when the user closes out of the application, the data can
 be stored and kept somewhere for future use.
 */
public class Inventory {
    /** Creates an array list to store parts into. */
    private static  ObservableList<Part> partList = FXCollections.observableArrayList();
    /** Creates an array list to store products into. */
    private static ObservableList<Product> productList = FXCollections.observableArrayList();
    /** This is a static member so the ID can remain unique for each part or product created. */
    private static int partID = 1;
    /** Anytime this method is called through out the application, it will increment the
     unique part or product ID by one.
     */
    public static int incrementPartID(){
        return partID++;
    }
    /**Adds part to part list.
     * @param part This is the part to be added from the list. */
    public static void addPart(Part part){
        partList.add(part);
    }
    /**Adds product to product list.
     * @param product This is the product to be added from the list. */
    public static void addProduct(Product product){
        productList.add(product);
    }
    /** Deletes part to part list.
     *  @param deletedPart This is the part to be deleted from the list*/
    public static void deletePart(Part deletedPart){
        partList.remove(deletedPart);
    }
    /**Deletes product to product list.
     *  @param deletedProduct This is the product to be deleted from the list*/
    public static void deleteProduct(Product deletedProduct){
        productList.remove(deletedProduct);
    }
    /** Gets parts from part list.
     @return partList gets the list of parts
      * */
    public static ObservableList<Part> getPartList(){
        return partList;
    }
    /** Gets products from products list.
     @return productList gets the list of products
      * */
    public static ObservableList<Product> getProductList(){
        return productList;
    }
    /** Updates part in part list.
     * @param updatedPart New information to be passed into the selected part
     * @param index  This is the index of the part to be deleted. */
    public static void updatePart(int index, Part updatedPart){
        Part partToBeDeleted = Inventory.lookUpPart(index);
        Inventory.deletePart(partToBeDeleted);
        Inventory.addPart(updatedPart);
    }
    /** Updates product in product list.
     * @param updatedProduct New information to be passed into the selected product
     * @param index  This is the index of the part to be deleted. */
    public static void updateProduct(int index, Product updatedProduct){
        Product productToBeDeleted = Inventory.lookUpProduct(index);
        Inventory.deleteProduct(productToBeDeleted);
        Inventory.addProduct(updatedProduct);
    }
    /** Looks up parts from parts list by unique id.
     @return partList returns a part with the corresponding index
     @param index Index of part to return after searching through part list.
      * */
    public static Part lookUpPart(int index){
        for(Part part : partList){
            if(part.getId() == index) {
                return part;
            }
        }
        return null;
    }
    /** Looks up products from products list by unique id.
     @return productList returns a product with the corresponding index
     @param index Index of product to return after searching through product list.
      * */
    public static Product lookUpProduct(int index){
        for(Product product : productList){
            if(product.getProductID() == index) {
                return product;
            }
        }
        return null;
    }
    /** Looks up parts from parts list by partial name search.
     @return partList returns a list of parts with names that contain the searched term
     @param partialName The partial search term to look up.
      * */
    public static ObservableList<Part> lookUpPartialPart(String partialName){
        ObservableList<Part> partialPartList = FXCollections.observableArrayList();
        Inventory.getPartList();
        for(Part part : getPartList()){
            if(part.getName().contains(partialName)){
                partialPartList.add(part);
            }
        }

        return partialPartList;
    }
    /** Looks up products from products list by a partial name search.
     @return productList returns a list of products with names that contain the searched term
     @param partialName The partial search term to look up.
      * */
    public static ObservableList<Product> lookUpPartialProduct(String partialName){
        ObservableList<Product> partialProductList = FXCollections.observableArrayList();
        Inventory.getProductList();
        for(Product product : getProductList()){
            if(product.getProductName().contains(partialName)){
                partialProductList.add(product);
            }
        }
        return partialProductList;
    }


}
