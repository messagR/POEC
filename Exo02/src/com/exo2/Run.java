package com.exo2;

public class Run {

	public static void main(String[] args) {
		Point2D p1 = new Point2D();
		p1.afficher();
		p1.translater(2, 5);
		p1.afficher();
		System.out.println(Point2D.getNbObjet());

		Point2D p2 = new Point2D(2, 5);
		p2.afficher();
		p2.translater(5, 2);
		p2.afficher();
		System.out.println(Point2D.getNbObjet());

		Point3D p3 = new Point3D(2, 5, 7);
		p3.afficher();
		p3.translater(5, 2, 7);
		p3.afficher();
		System.out.println(Point2D.getNbObjet());

	}

}
