package utils;

public class Pair<E, K> {
	private E object1;
	private K object2;
	
	public Pair(E object1, K object2)
	{
		this.object1 = object1;
		this.object2 = object2;
	}

	public E getObject1() {
		return object1;
	}

	public K getObject2() {
		return object2;
	}

	public void setObject1(E object1) {
		this.object1 = object1;
	}

	public void setObject2(K object2) {
		this.object2 = object2;
	}

	public void set(E object1, K object2) {
		this.object1 = object1;
		this.object2 = object2;
	}
}
