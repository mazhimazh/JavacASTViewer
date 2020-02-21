package test;

public class Test02 {

	class Parent {
	}

	public static void main(String[] args) {
		new Test02().test();
	}

	public void test() {
		Parent a = new Parent();
		System.out.println(a.getClass().getSimpleName()); 
		System.out.println(a.getClass().getCanonicalName());
		System.out.println(a.getClass().getTypeName());
	}

}
