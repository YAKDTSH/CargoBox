import java.util.ArrayList;

/**
 * A CargoBox holds zero or more Items and can provide information about the
 * Items. One can add Items to a CargoBox during its lifetime, empty the
 * CargoBox, create a copy which contains Items only up to a certain weight,
 * and make various queries to the CargoBox. (Thus, the number of Items that
 * will be stored by a CargoBox object is not yet known when the new object
 * is created, and it may grow and shrink over the lifetime of a CargoBox
 * object.)
 *
 * @author Daniel Tshilongo
 */
public class CargoBox {

    /**
     * The list of items currently stored in this CargoBox.
     * This list contains all non-null items that have been added.
     * Invariant: items is never null, and contains no null elements.
     * 
     * ArrayList is used to provide dynamic storage for items that can grow
     * and shrink over the lifetime of a CargoBox object.
     */
    private ArrayList<Item> items;

    /* Constructors */

    /**
     * Constructs a new CargoBox without any Items.
     */
    public CargoBox() {
        this.items = new ArrayList<>();
    }

    /**
     * Constructs a new CargoBox containing the non-null Items in items.
     * The items array may be modified by the caller afterwards without
     * affecting this CargoBox, and it will not be modified by this
     * constructor.
     *
     * @param items must not be null; non-null elements are added to the
     *              constructed CargoBox
     */
    public CargoBox(Item[] items) {
        this(); // Call the default constructor to initialize the ArrayList
        this.addAll(items);
    }

    /* Modifiers */

    /**
     * Adds an Item e to this CargoBox if e is not null; does not modify this
     * CargoBox otherwise. Returns true if e is not null, false otherwise.
     *
     * @param e an item to be added to this CargoBox
     * @return true if e is not null, false otherwise
     */
    public boolean add(Item e) {
        if (e != null) {
            this.items.add(e);
            return true;
        }
        return false;
    }

    /**
     * Adds all non-null Items in items to this CargoBox.
     *
     * @param items contains the Item objects to be added to
     *              this CargoBox; must not be null (but may contain null)
     * @return true if at least one element of items is non-null;
     *         false otherwise
     */
    public boolean addAll(Item[] items) {
        boolean addedAny = false;
        for (Item item : items) {
            if (this.add(item)) {
                addedAny = true;
            }
        }
        return addedAny;
    }

    /**
     * Empties this CargoBox to a CargoBox that contains 0 Items.
     */
    public void empty() {
        this.items = new ArrayList<>();
    }

    /**
     * Removes certain Items from this CargoBox. Exactly those Items are kept
     * whose weight in grammes is less than or equal to the specified maximum
     * weight in grammes.
     *
     * @param maxItemWeightInGrammes the maximum weight in grammes for the
     *                               Items that are kept
     */
    public void keepOnlyItemsWith(int maxItemWeightInGrammes) {
        ArrayList<Item> keptItems = new ArrayList<>();
        for (Item item : this.items) {
            if (item.getWeightInGrammes() <= maxItemWeightInGrammes) {
                keptItems.add(item);
            }
        }
        this.items = keptItems;
    }

    /* Accessors */

    /**
     * Returns the number of non-null Items in this CargoBox.
     *
     * @return the number of non-null Items in this CargoBox
     */
    public int numberOfItems() {
        return this.items.size();
    }

    /**
     * Returns the total weight of the Items in this CargoBox.
     *
     * @return the total weight of the Items in this CargoBox.
     */
    public int totalWeightInGrammes() {
        int total = 0;
        for (Item item : this.items) {
            total += item.getWeightInGrammes();
        }
        return total;
    }

    /**
     * Returns the average weight in grammes of the (non-null) Items
     * in this CargoBox. In case there is no Item in this CargoBox,
     * -1.0 is returned.
     *
     * For example, if this CargoBox has the contents
     * new Item("clock", 400)
     * and
     * new Item("textbook", 395),
     * the result is: 397.5
     *
     * @return the average length of the Items in this CargoBox,
     *         or -1.0 if there is no such Item.
     */
    public double averageWeightInGrammes() {
        if (this.numberOfItems() == 0) {
            return -1.0;
        }
        return (double) this.totalWeightInGrammes() / this.numberOfItems();
    }

    /**
     * Returns the greatest Item in this CargoBox according to the
     * natural ordering of Item given by its compareTo method;
     * null if this CargoBox does not contain any Item objects
     *
     * @return the greatest Item in this CargoBox according to the
     *         natural ordering of Item given by its compareTo method;
     *         null if this CargoBox does not contain any Item objects
     */
    public Item greatestItem() {
        if (this.items.isEmpty()) {
            return null;
        }

        Item greatest = this.items.get(0);
        for (Item item : this.items) {
            if (item.compareTo(greatest) > 0) {
                greatest = item;
            }
        }
        return greatest;
    }

    /**
     * Returns a new CargoBox with exactly those Items of this CargoBox
     * whose weight is less than or equal to the specified method parameter.
     * Does not modify this CargoBox.
     *
     * @param maxItemWeightInGrammes the maximum weight in grammes for the
     *                               Items in the new CargoBox
     * @return a new CargoBox with exactly those Items of this CargoBox
     *         whose weight is less than or equal to the specified method parameter
     */
    public CargoBox makeNewCargoBoxWith(int maxItemWeightInGrammes) {
        CargoBox newBox = new CargoBox();
        for (Item item : this.items) {
            if (item.getWeightInGrammes() <= maxItemWeightInGrammes) {
                newBox.add(item);
            }
        }
        return newBox;
    }

    /**
     * Returns a string representation of this CargoBox. The string
     * representation consists of a list of the CargoBox's contents,
     * enclosed in square brackets ("[]"). Adjacent Items are
     * separated by the characters ", " (comma and space). Items are
     * converted to strings as by their toString() method. The
     * representation does not mention any null references.
     *
     * So for
     *
     * Item i1 = new Item("Pen", 15);
     * Item i2 = new Item("Letter", 20);
     * Item i3 = null;
     * Item[] items = { i1, i2, i3, i1 };
     * CargoBox k = new CargoBox(items);
     *
     * the call k.toString() will return one of the three following Strings:
     *
     * "[(Pen, 15g), (Pen, 15g), (Letter, 20g)]"
     * "[(Pen, 15g), (Letter, 20g), (Pen, 15g)]"
     * "[(Letter, 20g), (Pen, 15g), (Pen, 15g)]"
     *
     * @return a String representation of this CargoBox
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (this.items.isEmpty()) {
            return "[]";
        }

        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < this.items.size(); i++) {
            result.append(this.items.get(i).toString());
            if (i < this.items.size() - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

    /* class methods */

    /**
     * Class method to return a CargoBox with the highest total weight from an
     * array of CargoBoxs. If we have an array with a CargoBox of 3000 grammes
     * and a CargoBox with 4000 grammes, the CargoBox with 4000 grammes is
     * returned.
     *
     * Entries of the array may be null, and your method should work also in
     * the presence of such entries. So if in the above example we had an
     * additional third array entry null, the result would be exactly the same.
     *
     * If there are several CargoBoxs with the same weight, it is up to the
     * method implementation to choose one of them as the result (i.e., the
     * choice is implementation-specific, and method users should not rely on
     * any particular behaviour).
     *
     * @param CargoBoxs must not be null, but may contain null
     * @return one of the CargoBoxs with the highest total weight among all
     *         CargoBoxs in the parameter array; null if there is no non-null
     *         reference in CargoBoxs
     */
    public static CargoBox heaviestCargoBox(CargoBox[] CargoBoxs) {
        CargoBox heaviest = null;
        int maxWeight = 0;

        for (CargoBox box : CargoBoxs) {
            if (box != null) {
                int weight = box.totalWeightInGrammes();
                if (heaviest == null || weight > maxWeight) {
                    heaviest = box;
                    maxWeight = weight;
                }
            }
        }
        return heaviest;
    }
}
