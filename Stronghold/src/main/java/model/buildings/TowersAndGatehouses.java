package model.buildings;

public class TowersAndGatehouses extends Building {
    int maxHp;
    int hp;
    boolean canBeLivedIn;
    int capacity;
    boolean hasHeight;
    int height;
    ArrayList<Person> residents;

    public TowersAndGatehouses(String type,
                               String category,
                               User owner,
                               String materialToBuild,
                               int numberOfMaterialToBuild,
                               int price,
                               int hp,
                               int capacity,
                               int height) {
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

    public int getNumberOfResidents() {
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
