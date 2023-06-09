package controller.functionalcontrollers;

public class Pair<E, K> {
    private final E object1;
    private final K object2;

    public Pair(E object1, K object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    public E getObject1() {
        return object1;
    }

    public K getObject2() {
        return object2;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) return false;
        return ((Pair<?, ?>) obj).getObject1().equals(object1) && ((Pair<?, ?>) obj).getObject2().equals(object2);
    }
}
