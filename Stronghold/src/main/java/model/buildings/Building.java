package model.buildings;

public class Building {
    private String type;
    private String category;
    private String materialToBuild;
    private int numberOfMaterialToBuild;
    private int price;

    public Building(String type, String category, String materialToBuild, int numberOfMaterialToBuild, int price) {
        this.type = type;
        this.category = category;
        this.materialToBuild = materialToBuild;
        this.numberOfMaterialToBuild = numberOfMaterialToBuild;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public int getNumberOfMaterialToBuild() {
        return numberOfMaterialToBuild;
    }

    public String getMaterialToBuild() {
        return materialToBuild;
    }
}
