public class Recipe {
    public Recipe(int id, String name, String category, int preparationTime, String description, String image) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.preparationTime = preparationTime;
        this.description = description;
        this.image = image;
    }
    public Recipe(){}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    int id;
    String name;
    String category;
    int preparationTime;
    String description;
    String image;
}
