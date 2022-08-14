import java.util.LinkedList;
import java.util.List;

public class ShoppingList {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public List<ShoppingListProduct> getList() {
        return list;
    }

    public void setList(List<ShoppingListProduct> list) {
        this.list = list;
    }

    String name;
    int id;
    List<User> users = new LinkedList<User>();
    int creatorId;

    List<ShoppingListProduct> list = new LinkedList<ShoppingListProduct>();

    public void addToList(ShoppingListProduct product){
        list.add(product);
    }

}
