public class Product {
    public Product(int id, String name, String category, String unit, String picture) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.picture = picture;
    }
    public Product(){}

    int id;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getUnit() {
        return unit;
    }

    public String getPicture() {
        return picture;
    }

    String name;
    String category;
    String unit;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    String picture;
}
