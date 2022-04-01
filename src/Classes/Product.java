//Product
package Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** This class helps construct all products that are made.
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String productName;
    private double productPrice;
    private int productStock;
    private int minProducts;
    private int maxProducts;

    /** Constructor class for the product.
     * @param productID Product unique identifier.
     * @param productName Products name
     * @param productPrice Products price as a double
     * @param productStock Products set amount in inventory
     * @param maxProducts The maximum amount of products that can be in inventory
     * @param minProducts The minimum amount of products that can be in inventory
     * */
    public Product(int productID, String productName, double productPrice, int productStock, int minProducts, int maxProducts) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.minProducts = minProducts;
        this.maxProducts = maxProducts;
    }
    /** Adds an associated part to the product. This list is kept in
     *  an observable array list.
     *  @param addedPart this is the part to be added*/
    public void addAssociatedPart(Part addedPart){
        this.associatedParts.add(addedPart);
    }
    /** Deletes an associated part from the product. This list is kept in
     *  an observable array list.
     *  @param deletedPart  Part to be removed from the associated parts list*/
    public void deleteAssociatedPart(Part deletedPart){this.associatedParts.remove(deletedPart);}
    /** Gets the associated part list
     *  @return associatedParts this is where all of the parts that get added the to product are kept. */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    /** Gets product ID.
     * @return Returns the product ID. */
    public int getProductID() {
        return productID;
    }
    /** Sets product ID. */
    public void setProductID(int productID) {
        this.productID = productID;
    }
    /** Gets product name.
     * @return Returns the product name. */
    public String getProductName() {
        return productName;
    }
    /** Sets product name. */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    /** Gets product price.
     * @return Returns the product price. */
    public double getProductPrice() {
        return productPrice;
    }
    /** Sets product price. */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    /** Gets product stock.
     * @return Returns the product stock. */
    public int getProductStock() {
        return productStock;
    }
    /** Sets product stock. */
    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }
    /** Gets product minimum amount of products.
     * @return Returns the product minimum amount of products. */
    public int getMinProducts() {
        return minProducts;
    }
    /** Sets product minimum amount of products. */
    public void setMinProducts(int minProducts) {
        this.minProducts = minProducts;
    }
    /** Gets product maximum amount of products.
     * @return Returns the product ID. */
    public int getMaxProducts() {
        return maxProducts;
    }
    /** Sets product minimum amount of products. */
    public void setMaxProducts(int maxProducts) {
        this.maxProducts = maxProducts;
    }
}
