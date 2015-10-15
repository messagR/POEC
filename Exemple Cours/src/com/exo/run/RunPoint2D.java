package com.exo.run;

import com.exo.classe.Point2D;
import com.exo.classe.Point3D;
import com.exo.classe.Point3Dv2;

public class RunPoint2D {

	// enlève le warning sur la ligne ou il peut y avoir un pointeur null
	// @SuppressWarnings("null")
	// enlève les warnings de null pointer exception et de not used
	// @SuppressWarnings(value = { "null", "unused" })
	public static void main(String[] args) {


		Point2D p1 = new Point2D(25, 30);
		p1.afficher();
		Point3D p2 = new Point3D(5, 5, 5);
		Point3Dv2 p2b = new Point3Dv2(5, 5, 5);
		p2.afficher();
		Object p3 = new Point2D();
		Point2D p4 = new Point3D(55, 55, 55);
		// Point2D p4b = new Point3Dv2(55, 55, 55); -> impossible
		p4.afficher();
		Object[] tab1 = new Object[10];
		tab1[0] = new Point2D();
		tab1[1] = new Point3D();
		tab1[2] = new Point2D(5, 5);
		tab1[3] = new Point3D(10, 10, 10);
		tab1[4] = "Toto";
		for (int i = 0; i < tab1.length; i++) {
			if (tab1[i] instanceof Point3D) {
				((Point3D) tab1[i]).setZ(50);
			}
		}

		int[] tab = new int[10];
		int tab2[] = new int[10];
		Point2D tab3[] = new Point2D[25];
		int tab4[] = { 0, 5, 8, 9 };
		int matrice[][] = new int[5][10];
		// récupère la taille d'un tableau multidimension
		// 1ère dimension
		int t = matrice.length;
		// 2me dimension
		int t2 = matrice[0].length;
		tab[5] = 10;
		System.out.println("taille de tab " + tab.length);
		tab3[0] = new Point2D();
		tab3[0].setXx(1);
		Point2D p11 = new Point2D(2, 5);
		Point2D tab5[] = { p11, tab3[0], new Point2D() };
		System.out.println(tab5[1].getXx());

	}

}
