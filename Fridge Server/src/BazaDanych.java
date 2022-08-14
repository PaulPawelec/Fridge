import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BazaDanych {
    static BazaDanych instance = null;
    public static BazaDanych getInstance() {
        if (instance == null) {
            instance = new BazaDanych();
        }
        return instance;
    }

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:C:\\Users\\ppawe\\Desktop\\Fridge Server\\FridgeDB.db";
    private Connection conn;
    private Statement stat;

    public BazaDanych() {
        try {
            Class.forName(BazaDanych.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC ");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia ");
            e.printStackTrace();
        }
    }

    public List<User> selectUsers() {
        List<User> users = new LinkedList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM users");
            int id;
            String username, email, password;
            while(result.next()) {
                id = result.getInt("IDuser");
                username = result.getString("username");
                email = result.getString("email");
                password = result.getString("password");
                users.add(new User(id, username, email, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }

    public String insertUser(String username, String email, String password) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into users values (NULL, ?, ?, ?);");
            prepStmt.setString(1, username);
            prepStmt.setString(2, email);
            prepStmt.setString(3, password);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy rejestracji " + e);
            return "-1";
        }
        return "ok";
    }

    public String login(String username, String hashedPassword) {
        try {
            ResultSet result = stat.executeQuery(String.format("SELECT * FROM users WHERE username = '%s' AND password = '%s'", username, hashedPassword));
            if(result.next() && result.getString("username").equals(username)){
                String id = result.getString("IDuser");
                id = "0000000000".substring(id.length()) + id; //id usera jako 10 cyfrowa liczba poprzedzona zerami
                LocalDateTime dateTime = LocalDateTime.now();
                dateTime = dateTime.plusHours(6); //sesja trwa 6 godzin
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                LoginSessions loginSessions = new LoginSessions();
                loginSessions.add("ok"+dateTime.format(formatter)+id+username);
                System.out.println("TUTAJ: "+"ok"+dateTime.format(formatter)+id+username);
                return "ok"+dateTime.format(formatter)+id+username;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return "-1";
    }

    public List<Recipe> suggestRecipes() {
        List<Recipe> recipes = new LinkedList<>();
        Recipe recipe;
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM recipes");
            while(result.next()) {
                recipe = new Recipe();
                recipe.setId(result.getInt("IDrecipes"));
                recipe.setName(result.getString("namerecipes"));
                recipe.setCategory(result.getString("category"));
                recipe.setPreparationTime(result.getInt("preparationtime"));
                recipe.setDescription(result.getString("description"));
                recipe.setImage(result.getString("image"));
                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return recipes;
    }

    public Recipe getRecipe(int recipeId) {
        Recipe recipe = new Recipe();
        try {
            ResultSet result = stat.executeQuery(String.format("SELECT * FROM recipes WHERE IDrecipes = '%s'", recipeId));
            while(result.next()) {
                recipe.setId(result.getInt("IDrecipes"));
                recipe.setName(result.getString("namerecipes"));
                recipe.setCategory(result.getString("category"));
                recipe.setPreparationTime(result.getInt("preparationtime"));
                recipe.setDescription(result.getString("description"));
                recipe.setImage(result.getString("image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return recipe;
    }

    public List<Product> getIngredients(int recipeId) {
        List<Product> products = new LinkedList<>();
        try {
            ResultSet result = stat.executeQuery(String.format("SELECT productID FROM ingridients WHERE recipeID = '%d'", recipeId));
            List<Integer> ingredientsIDs = new LinkedList<>();
            while(result.next()) {
                ingredientsIDs.add(result.getInt("productID"));
            }
            for(int id : ingredientsIDs){
                Product product = new Product();
                result = stat.executeQuery(String.format("SELECT * FROM products WHERE IDproduct = '%d'", id));
                product.setId(result.getInt("IDproduct"));
                product.setName(result.getString("name"));
                product.setCategory(result.getString("category"));
                product.setUnit(result.getString("unit"));
                product.setPicture(result.getString("picture"));
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return products;
    }
    public List<Dieta> selectDiets(){
        List<Dieta> diety = new LinkedList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM diet");
            while(result.next()) {
                Dieta dieta = new Dieta();
                dieta.setId(result.getString("IDdiet"));
                dieta.setName(result.getString("namediet"));
                dieta.setCategory(result.getString("category"));
                diety.add(dieta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return diety;
    }
    public List<Dieta> getDietlist(int id) {
        List<Dieta> diety = new LinkedList<>();
        //Dieta dieta = new Dieta();
        try {
            //ResultSet result = stat.executeQuery("SELECT * FROM diet WHERE IDdiet IN (SELECT recipeID FROM diet_users WHERE userID = " + id + "\"");
            ResultSet result = stat.executeQuery(String.format("SELECT * FROM diet WHERE IDdiet IN (SELECT dietID FROM diet_users WHERE userID = '%d')", id));
            while(result.next()) {
                Dieta dieta = new Dieta();
                dieta.setId(result.getString("IDdiet"));
                dieta.setName(result.getString("namediet"));
                dieta.setCategory(result.getString("category"));
                diety.add(dieta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return diety;
    }

    public List<String> showDiet(int id) throws SQLException {
        List<String> dieta = new ArrayList<>();
        System.out.println("IDdiet:"+id);
        ResultSet result = stat.executeQuery(String.format("SELECT * FROM diet WHERE IDdiet = '%d'", id));
        while(result.next()) {
            dieta.add(result.getString("namediet"));
            //System.out.println("namediet:"+result.getString("namediet"));
            dieta.add(result.getString("category"));
            //System.out.println("category:"+result.getString("category"));
            dieta.add(result.getString("description"));
            //System.out.println("description:"+result.getString("description"));
        }
        for(int i = 1; i<=21;i++){
            result = stat.executeQuery(String.format("SELECT namerecipes FROM recipes WHERE IDrecipes IN (SELECT recipeID FROM diet_time WHERE dietID = '%d' AND weektime = '%d')", id, i));
            while(result.next()){
                dieta.add(result.getString("namerecipes"));
                System.out.println("namerecipes:"+result.getString("namerecipes"));
            }
        }

        return dieta;
    }
    public boolean updateDiet(int dietId, int weektime, int recipeID){
        try {
            ResultSet resultSet1 = stat.executeQuery(String.format("UPDATE diet_time SET recipeID = %d WHERE dietID = %d AND weektime = %d", recipeID, dietId, weektime));

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean addToFridge(int ownerId, int productId, int quantity) {
        try{
            PreparedStatement prepStmt = conn.prepareStatement("insert into fridge values (?, ?, ?, ?);");
            prepStmt.setString(1, String.valueOf(ownerId));
            prepStmt.setString(2, String.valueOf(productId));
            prepStmt.setString(3, "");
            prepStmt.setString(4, String.valueOf(quantity));
            prepStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean removeFromFridge(int ownerId, int productId) {
        try{
            ResultSet result = stat.executeQuery(String.format("DELETE FROM fridge WHERE ownerID = %d AND productID = %d", ownerId, productId));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean updateProduct(int userId, int productId, int quantity){
        try {
            ResultSet resultSet1 = stat.executeQuery(String.format("UPDATE fridge SET quantity = %d WHERE ownerID = %d AND productID = %d", quantity, userId, productId));

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean removedietuser(int userId, int dietId){
        try {
            ResultSet resultSet = stat.executeQuery(String.format("DELETE FROM diet_users WHERE dietID = %d AND userID = %d", dietId, userId));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean removeProductfromList(int userId, int productId){
        try {
            ResultSet resultSet = stat.executeQuery(String.format("DELETE FROM shopping_list WHERE listID = %d AND productID = %d", userId, productId));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean removeShoppingList(int ownerId, String name) {
        int idList = 0;
        try{
            ResultSet result = stat.executeQuery(String.format("SELECT IDlist FROM shopping_lists WHERE namelist = '%s'", name));
            while(result.next()) {
                idList = result.getInt("IDlist");
            }
            result = stat.executeQuery(String.format("DELETE FROM shopping_lists WHERE creatorID = %d AND IDlist = %d", ownerId, idList));
            result = stat.executeQuery(String.format("DELETE FROM shopping_connections WHERE listID = %d", ownerId, idList));
            result = stat.executeQuery(String.format("DELETE FROM shopping_list WHERE listID = %d", idList));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean addProductsToList(int userId, ShoppingListProduct product) {
        try{
            //ResultSet result = stat.executeQuery(String.format("SELECT IDlist FROM shopping_lists WHERE namelist = %s)", listName));
            //int listId = result.getInt("IDlist");
            PreparedStatement prepStmt = conn.prepareStatement("insert into shopping_list values (?, ?, ?, ?);");
            prepStmt.setString(1, String.valueOf(userId));
            prepStmt.setString(2, String.valueOf(product.getId()));
            prepStmt.setString(3, product.getDescription());
            prepStmt.setString(4, String.valueOf(product.getQuantity()));
            prepStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public List<Product> selectProducts() {
        List<Product> products = new LinkedList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT IDproduct, name FROM products");
            while (result.next()) {
                Product product = new Product();
                product.setName(result.getString("name"));
                product.setId(result.getInt("IDproduct"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return products;
    }
    public List<Product> showByCategory(String category){
        List<Product> products = new LinkedList<>();
        try {
            ResultSet result = stat.executeQuery(String.format("SELECT IDproduct, name FROM products WHERE category = '%s'", category));
            while (result.next()){
                Product product = new Product();
                product.setName(result.getString("name"));
                product.setId(result.getInt("IDproduct"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return products;
    }

    public boolean addShoppingListUser(int userId, int listId){
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert into shopping_connections values (?, ?);");
            prepStmt.setString(1, String.valueOf(listId));
            prepStmt.setString(2, String.valueOf(userId));
            prepStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void addDiet(int userId, String dietName, String category, String description, List<Integer> recipes){
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into diet values (NULL, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            prepStmt.setString(1, dietName);
            prepStmt.setString(2, category);
            prepStmt.setString(3, description);
            prepStmt.execute();
            int dietId = prepStmt.getGeneratedKeys().getInt(1);
            System.out.println("id właśnie dodanej diety = " + dietId);
            int time = 1;
            for (Integer recipe : recipes){
                prepStmt = conn.prepareStatement(
                        "insert into diet_time values (?, ?, ?);");
                prepStmt.setString(1, String.valueOf(dietId));
                prepStmt.setString(2, String.valueOf(time));
                time++;
                prepStmt.setString(3, String.valueOf(recipe));
                prepStmt.execute();
            }
            prepStmt = conn.prepareStatement(
                    "insert into diet_users values (?, ?);");
            prepStmt.setString(1, String.valueOf(dietId));
            prepStmt.setString(2, String.valueOf(userId));
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy rejestracji " + e);
        }
    }

    public String assignDiet(int dietID, int userID){
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into diet_users values (?, ?);");
            prepStmt.setString(1, String.valueOf(dietID));
            prepStmt.setString(2, String.valueOf(userID));
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy przypisaniu diety " + e);
            return "-1";
        }
        return "ok";
    }
    public boolean checkproduct(int userId, int productId) throws SQLException {
        PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM fridge WHERE ownerID = ? AND productID = ?");
        prepStmt.setString (1, String.valueOf(userId));
        prepStmt.setString (2, String.valueOf(productId));
        ResultSet rs = prepStmt.executeQuery();
        if (rs.next()) {
            return true;
        } else {
            return false;
        }
    }
    public List<Product> showFridge(int id) {
        List<Product> products = new LinkedList<>();
        try {
            ResultSet result = stat.executeQuery(String.format("SELECT * FROM products WHERE IDproduct IN (SELECT productID FROM fridge WHERE ownerID = %d)", id));
            while(result.next()) {
                Product product = new Product();
                product.setId(result.getInt("IDproduct"));
                product.setName(result.getString("name"));
                product.setCategory(result.getString("category"));
                product.setUnit(result.getString("unit"));
                product.setPicture(result.getString("picture"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return products;
    }
    public int setQuantity(int id, int productId){
        int x;
        try{
            ResultSet result = stat.executeQuery(String.format("SELECT * FROM fridge WHERE ownerID = %d AND productID = %d", id, productId));
            if(result.next()){
                x =  result.getInt("quantity");
            }else
                x = 0;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
        return x;
    }
    public List<Product> showShoppingList(int ownerId, String listName) {
        List<Product> products = new LinkedList<>();
        try {
            ResultSet result = stat.executeQuery(String.format("SELECT * FROM products WHERE IDproduct IN (SELECT productID FROM shopping_list WHERE listID = %d)", ownerId));
            while(result.next()) {
                Product product = new Product();
                product.setId(result.getInt("IDproduct"));
                product.setName(result.getString("name"));
                product.setCategory(result.getString("category"));
                product.setUnit(result.getString("unit"));
                product.setPicture(result.getString("picture"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return products;
        /*
        ShoppingList list = new ShoppingList();
        try {
            //ResultSet result1 = stat.executeQuery(String.format("SELECT name, IDproduct FROM products WHERE IDproduct IN (SELECT productID from shopping_list WHERE listID IN (SELECT IDlist FROM shopping_lists WHERE namelist = '%s'))", listName));
            ResultSet result1 = stat.executeQuery(String.format("SELECT * FROM products WHERE IDproduct IN (SELECT productID FROM shopping_list WHERE listID = %d)", ownerId));
            while(result1.next()) {
                ShoppingListProduct product = new ShoppingListProduct();
                product.setId(result1.getInt("IDproduct"));
                product.setName(result1.getString("name"));
                product.setCategory(result1.getString("category"));
                product.setUnit(result1.getString("unit"));
                product.setPicture(result1.getString("picture"));
                ResultSet result2 = stat.executeQuery(String.format("SELECT quantity FROM fridge WHERE ownerID = %d AND productID = %d", ownerId, product.getId()));
                if(result2.next()) {
                    product.setQuantity(result2.getInt("quantity"));
                } else product.setQuantity(0);
                list.addToList(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
         */
    }
    public int setQuantity2(int id, int productId){
        int x;
        try{
            ResultSet result = stat.executeQuery(String.format("SELECT * FROM shopping_list WHERE listID = %d AND productID = %d", id, productId));
            if(result.next()){
                x =  result.getInt("quantity");
            }else
                x = 0;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
        return x;
    }
    public List<String> selectShoppingLists(int userId){
        List<String> shoppingLists = new ArrayList<String>();
        try {
            ResultSet result = stat.executeQuery(String.format("SELECT IDlist, namelist FROM shopping_lists WHERE IDlist IN (SELECT listID FROM shopping_connections WHERE memberID = %d)", userId));
            while(result.next()) {
                shoppingLists.add(result.getString("IDlist"));
                shoppingLists.add(result.getString("namelist"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return shoppingLists;
    }
    public List<String> selectCategories(){
        List<String> categories = new ArrayList<String>();
        try {
            ResultSet result = stat.executeQuery("SELECT DISTINCT category FROM products");
            while (result.next()){
                categories.add(result.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return categories;
    }



    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
}
