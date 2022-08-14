import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ServerThread extends Thread {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        Session session = new Session();
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            String text;
            BazaDanych fridgedb = BazaDanych.getInstance();

            do {
                text = reader.readLine();
                System.out.println("Klient: " + text);
                if(text.startsWith("session")) {
                    //String sesh = reader.readLine();
                    String sesh = text.substring(7);
                    //System.out.println("Sesja =" + sesh);
                    LoginSessions loginSession = new LoginSessions();
                    session = loginSession.add(sesh);
                    System.out.println("Sesja=" + session.id);
                }
                else if(text.startsWith("nosession")){
                    System.out.println("Client is not logged in");
                }
                else if(text.startsWith("00")) { //logowanie
                    String username = reader.readLine();
                    String hashedPassword = reader.readLine();
                    System.out.println(username + "\n" + hashedPassword);

                    writer.println(fridgedb.login(username,hashedPassword));
                } else if(text.startsWith("01")){
                    String username = reader.readLine();
                    String hashedPassword = reader.readLine();
                    String email = reader.readLine();
                    System.out.println(username + "\n" + hashedPassword + "\n" + email);
                    writer.println(fridgedb.insertUser(username,email,hashedPassword));
                } else if(text.startsWith("0404")) {
//                    writer.println("0404");
                    int ileProduktow = Integer.parseInt(reader.readLine());
                    boolean noErrors = true;
                    while(ileProduktow > 0 && noErrors){
                        ileProduktow--;
                        int idProduktu = Integer.parseInt(reader.readLine());
                        int ilosc = Integer.parseInt(reader.readLine());
                        noErrors = fridgedb.addToFridge(session.getId(), idProduktu, ilosc);
                    }
                    if(noErrors) writer.println("ok"); else writer.println("-1");
                } else if (text.startsWith("0302")) { //wykasuj produkty z listy zakupów i dodaj do lodówki
                    int ileProduktow = Integer.parseInt(reader.readLine());
                    boolean noErrors = true;
                    boolean noErrors2 = true;
                    while (ileProduktow > 0 && noErrors && noErrors2){
                        ileProduktow--;
                        int productID = Integer.parseInt(reader.readLine());
                        int quantity = Integer.parseInt(reader.readLine());
                        noErrors = fridgedb.removeProductfromList(session.getId(), productID);
                        noErrors2 = fridgedb.addToFridge(session.getId(), productID, quantity);
                    }
                    if (noErrors) writer.println("ok"); else writer.println("-1");
                }else if(text.startsWith("0405")) {
//                    writer.println("0405");
                    int ileProduktow = Integer.parseInt(reader.readLine());
                    boolean noErrors = true;
                    while(ileProduktow > 0 && noErrors){
                        ileProduktow--;
                        int productID = Integer.parseInt(reader.readLine());
                        noErrors = fridgedb.removeFromFridge(session.getId(), productID);
                    }
                    if(noErrors) writer.println("ok"); else writer.println("-1");
                }else if(text.startsWith("0303")) { //wyświetlanie listy zakupów
                    String listName = reader.readLine();
                    //System.out.println("Listname:"+listName);

                    List<Product> products = fridgedb.showShoppingList(session.getId(), listName);
                    writer.println("0303");
                    writer.println(products.size());
                    for(Product product : products){
                        writer.println(product.getId());
                        writer.println(product.getName());
                        int x = fridgedb.setQuantity2(session.getId(),product.getId());
                        writer.println(x);
                    }
                }else if(text.startsWith("0409")) { //wyświetlanie lodówki
                    List<Product> products = fridgedb.showFridge(session.getId());
                    writer.println("0409");
                    writer.println(products.size());
                    System.out.println("Size:"+products.size());
                    for(Product product : products){
                        writer.println(product.getId());
                        int x = fridgedb.setQuantity(session.getId(),product.getId());
                        writer.println(product.getName());
                        writer.println(x);
                    }
                }else if(text.startsWith("0909")){//wypisz wszystkie produkty
                    List<Product> products = fridgedb.selectProducts();
                    writer.println("0909");
                    writer.println(products.size());
                    for(Product product : products){
                        writer.println(product.getId());
                        writer.println(product.getName());
                        writer.println(product.getQuantity());
                    }
                }else if(text.startsWith("0803")){ //produkty z kategorii
                    String category = reader.readLine();
                    List<Product> products = fridgedb.showByCategory(category);
                    writer.println("0803");
                    writer.println(products.size());
                    for(Product product : products){
                        writer.println(product.getId());
                        writer.println(product.getName());
                        writer.println(product.getQuantity());
                    }
                }else if(text.startsWith("0509")) {
                    writer.println("0509");
                    List<Recipe> recipes = fridgedb.suggestRecipes();
                    writer.println(recipes.size());
                    for(Recipe recipe : recipes){
                        writer.println(recipe.id);
                        System.out.println("Test:"+recipe.id);
                        writer.println(recipe.name);
                        writer.println(recipe.category);
//                        writer.println(recipe.image);
//                        writer.println(recipe.preparationTime);
//                        writer.println(recipe.description);
                    }
                } else if(text.startsWith("0502")) {
                    int productId = Integer.parseInt(reader.readLine());

                    boolean noErrors = fridgedb.checkproduct(session.getId(), productId);

                    if (noErrors) writer.println("0502ok"); else writer.println("0502bad");

                }else if(text.startsWith("0503")) {
                    int recipeId = Integer.parseInt(reader.readLine());
                    Recipe recipe = fridgedb.getRecipe(recipeId);
                    writer.println("0503");
                    writer.println(recipe.name);
                    writer.println(recipe.category);
                    writer.println(recipe.preparationTime);
//                        writer.println(recipe.image);
                    writer.println(recipe.description);
                    //List<Product> ingredients = new LinkedList<Product>();
                    List<Product> ingredients = fridgedb.getIngredients(recipeId);
                    writer.println(ingredients.size());
                    //System.out.println(ingredients.size());
                    for (Product ingredient : ingredients){
                        writer.println(ingredient.name);
                        writer.println(ingredient.id);
                    }
                } else if(text.startsWith("0600")){
                    String dietName = reader.readLine();
                    String category = reader.readLine();
                    String description = reader.readLine();
                    List<Integer> recipes = new ArrayList<>();
                    for(int i =0; i<21; i++){
                        recipes.add(Integer.parseInt(reader.readLine()));
                    }
                    fridgedb.addDiet(session.getId(), dietName, category, description, recipes);
                    writer.println("0600");
                } else if(text.startsWith("0603")) { //wyswietlenie diety
                    int idDiety = Integer.parseInt(reader.readLine());
                    writer.println("0603");
                    List<String> dieta = fridgedb.showDiet(idDiety);
                    for(String d : dieta){
                        writer.println(d);
                    }
                } else if (text.startsWith("0602")) {
                    int dietId = Integer.parseInt(reader.readLine());
                    int weektime = Integer.parseInt(reader.readLine());
                    int recipeId = Integer.parseInt(reader.readLine());
                    boolean noErrors = fridgedb.updateDiet(dietId, weektime, recipeId);
                    if (noErrors) writer.println("ok"); else writer.println("-1");
                }else if(text.startsWith("0608")) {
                    writer.println("0608");
                    System.out.println("ID SESJI:"+session.getId());

                    List<Dieta> diety = fridgedb.getDietlist(session.getId());
                    writer.println(diety.size());
                    for(Dieta dieta : diety){
                        writer.println(dieta.id);
                        writer.println(dieta.name);
                        writer.println(dieta.category);
                    }
                } else if(text.startsWith("0609")) {
                    writer.println("0609");
                    List<Dieta> diety = fridgedb.selectDiets();
                    System.out.println("ID SESJI:"+session.getId());
                    writer.println(diety.size());
                    for(Dieta dieta : diety){
                        writer.println(dieta.id);
                        writer.println(dieta.name);
                        writer.println(dieta.category);
                    }
                } else if(text.startsWith("0607")) { //przypisywanie diety userowi
                    int idDiety = Integer.parseInt(reader.readLine());
                    //System.out.println("ID SESJI:"+session.getId());
                    writer.println(fridgedb.assignDiet(idDiety, session.getId()));
                } else if(text.startsWith("0605")) {
                    int idDiety = Integer.parseInt(reader.readLine());
                    boolean noErrors = fridgedb.removedietuser(session.getId(), idDiety);

                    if(noErrors) writer.println("ok"); else writer.println("-1");
                }else if(text.startsWith("0403")) { //wyświetlanie lodówki
                    List<Product> products = fridgedb.showFridge(session.getId());
                    writer.println(products.size());
                    for(Product product : products){
                        writer.println(product.name);
                        writer.println(product.category);
                        writer.println(product.unit);
                    }
                } else if (text.startsWith("0301")) { //usuń listę zakupów usera
                    int ileList = Integer.parseInt(reader.readLine());
                    boolean noErrors = true;
                    while (ileList > 0 && noErrors) {
                        ileList--;
                        String nazwa = reader.readLine();
                        noErrors = fridgedb.removeShoppingList(session.getId(), nazwa);
                    }
                    if (noErrors) writer.println("ok"); else writer.println("-1");
                } else if (text.startsWith("0307")) { //dodaj usera do listy zakupów
                    int Idlisty = Integer.parseInt(reader.readLine());
                    int userId = Integer.parseInt(reader.readLine());
                    System.out.println("LOL:"+Idlisty + " | " + userId);
                    if (fridgedb.addShoppingListUser(userId, Idlisty))
                        writer.println("ok");
                    else
                        writer.println("-1");
                }else if(text.startsWith("0309")) { //wszystkie listy zakupoów usera
                    List<String> shoppingLists = fridgedb.selectShoppingLists(session.getId());
                    writer.println("0309");
                    writer.println(shoppingLists.size()/2);
                    for(String s : shoppingLists){
                        writer.println(s);
                    }
                }else if(text.startsWith("0304")) { //dodaj produkty do listy zakupów
                    String name = reader.readLine();
                    int ileProduktow = Integer.parseInt(reader.readLine());
                    boolean noErrors = true;
                    while(ileProduktow > 0 && noErrors){
                        ileProduktow--;
                        int idProduktu = Integer.parseInt(reader.readLine());
                        int ilosc = Integer.parseInt(reader.readLine());
                        String opis = reader.readLine();
                        noErrors = fridgedb.addProductsToList(session.getId(), new ShoppingListProduct(idProduktu, ilosc, opis));
                        //noErrors = fridgedb.addProductsToList(session.getId(), name, new ShoppingListProduct(idProduktu, ilosc, opis));
                    }
                    if(noErrors) writer.println("ok"); else writer.println("-1");
                }
                else if(text.startsWith("0703")){ //wypisz kategorie
                    List<String> categories = fridgedb.selectCategories();
                    writer.println("0703");
                    writer.println(categories.size());
                    for (String s : categories) {
                        writer.println(s);
                    }
                } else writer.println("Server: echo " + text);
            } while (!text.equals("q"));

            socket.close();
        } catch (IOException | SQLException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}