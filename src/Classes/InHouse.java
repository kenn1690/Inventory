//InHouse
package Classes;

import javafx.collections.ObservableList;

/** This class helps construct all parts that are made in house and has
 the special modifier of Machine ID that is an Integer.
 */
public class InHouse extends Part {
    private int machineID;
    public static int id = 0;
    public InHouse(int id, String name, double price, int stock, int min, int max,int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }
    /** This method returns a machine ID that is an Integer.
     @return machineID returns the machine ID the part was made from
     */
    public int getMachineID() {
        return machineID;
    }
    /** This method sets a machine ID that is an Integer.
     * @param machineID machineID to set */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
