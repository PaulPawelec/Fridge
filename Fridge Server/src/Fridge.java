import java.util.LinkedList;
import java.util.List;

public class Fridge {
    public class FridgeProduct extends Product{
        public FridgeProduct(int id, String name, String category, String unit, String picture, String description, int quantity) {
            super(id, name, category, unit, picture);
            this.description = description;
            this.quantity = quantity;
        }
        String description;
        int quantity;
    }
    int ownerId;
    List<FridgeProduct> products = new LinkedList<FridgeProduct>();
}
