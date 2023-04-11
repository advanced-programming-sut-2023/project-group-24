package model.buildings;

public class Building {
    private String type;
    private String category;
    User owner;
    private String materialToBuild;
    private int numberOfMaterialToBuild;
    private int price;

    public Building(String type,
                    String category,
                    User owner,
                    String materialToBuild,
                    int numberOfMaterialToBuild,
                    int price) {
        //TODO
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
