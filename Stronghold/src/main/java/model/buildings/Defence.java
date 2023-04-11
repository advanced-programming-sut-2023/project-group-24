package model.buildings;

public class Defence extends Building {
    int maxHp;
    int hp;
    boolean canBeLivedIn;
    int capacity;
    boolean hasHeight;
    int height;
    ArrayList<Person> residents;

    public Defence(String type,
                   String category,
                   String materialToBuild,
                   int numberOfMaterialToBuild,
                   int price,
                   int hp,
                   int capacity,
                   int height) {
        super(type, category, materialToBuild, numberOfMaterialToBuild, price);
        //TODO set the other attributes
    }

    public int getHp() {
        return hp;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getHeight() {
        return height;
    }

    public int getCapacityLeft() {
        //TODO capacity left
    }

    public void addResident(Person person) {
        //TODO add person
    }

    public void removeResident(Person person) {
        //TODO check and remove person
    }

    public void loseHp(int amount) {
        //TODO lose hp
    }

    public void repair() {
        //TODO maxTheHp
    }
}
