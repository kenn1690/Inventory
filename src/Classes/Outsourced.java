//Outsourced
package Classes;
/** This class helps construct all parts that are made in house and has
 the special modifier of Machine ID that is an Integer.
 */
public class Outsourced extends Part {
    private String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /** This method returns a machine ID that is an Integer.
     @return companyName returns the company name the part was acquired from
     */
    public String getCompanyName() {
        return companyName;
    }
    /** This method sets a company name that is a string.
     * @param companyName String to set company name. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
