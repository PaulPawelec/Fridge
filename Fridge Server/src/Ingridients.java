import java.util.LinkedList;
import java.util.List;

public class Ingridients {
    public Ingridients(Recipe recipe, List<Product> products) {
        this.recipe = recipe;
        this.products = products;
    }
    Recipe recipe;
    List<Product> products = new LinkedList<Product>();
}
