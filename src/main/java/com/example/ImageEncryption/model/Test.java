package com.example.ImageEncryption.model;

abstract  class M{
	int a=30;
	static int b=70;
	public static  final int m1() {
		return 0;
	}
	
	public static  int m3() {
		System.out.println(" hi ");
		return 0;
	}
	
	
	
}


public class Test extends M {
	int a=10;
	static int b=50;
//	public int m1() {
//		return 1;
//	}
//	
	
	
	
	public static  int m3() {
		
		System.out.println(" hi  Im method shadowing");
		final int ab;
	
		return 0;
	
	}
	
	public static void main(String args[]) {
		System.out.println(" hi helllo");
	Test ss=	new Test();
	ss.m3();
	System.out.println(" hi helll  o "+ss.a +" b "+ss.b);
	}
	
}
