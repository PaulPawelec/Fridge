class ShoppingListProduct extends Product{

    public ShoppingListProduct(){}

    public ShoppingListProduct(int id, int quantity, String description){
        this.id = id;
        this.description = description;
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    String description;
    int quantity;
}