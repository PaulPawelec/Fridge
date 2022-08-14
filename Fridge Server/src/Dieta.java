import java.util.LinkedList;
import java.util.List;

public class Dieta{
    int id;
    String name;
    String category;

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setRecipe(DietaRecipes recipe) {
        this.recipe = recipe;
    }

    String description;
    List<User> users = new LinkedList<User>();
    class DietaRecipes extends Recipe{
        public DietaRecipes(int id, String name, String category, int preparationTime, String description, String image, int weektime) {
            super(id, name, category, preparationTime, description, image);
            this.weektime = weektime;
        }
        int weektime;
    }
    DietaRecipes recipe;
}
