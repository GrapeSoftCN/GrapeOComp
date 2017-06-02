package test;

import httpServer.booter;

public class testPerson {
	public static void main(String[] args) {
		booter booter = new booter();
		System.out.println("GrapeTest!");
		try {
			System.setProperty("AppName", "GrapeTest");
			booter.start(6004);
		} catch (Exception e) {

		}
	}
}
