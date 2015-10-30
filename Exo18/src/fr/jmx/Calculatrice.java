/**
 * test
 */
package fr.jmx;

/**
 * @author PC
 *
 */
public class Calculatrice implements CalculatriceMBean {

	private double dernierResultat;

	public Calculatrice() {
	}

	public Calculatrice(double dernierResulat) {
		this.setDernierResultat(dernierResulat);
	}

	public double getDernierResultat() {
		return this.dernierResultat;
	}

	public void setDernierResultat(double dernierResultat) {
		this.dernierResultat = dernierResultat;
	}

	@Override
	public double add(double c1, double c2) {
		this.setDernierResultat(c1 + c2);
		return this.getDernierResultat();
	}

	@Override
	public double sub(double c1, double c2) {
		this.setDernierResultat(c1 - c2);
		return this.getDernierResultat();
	}

	@Override
	public double mult(double c1, double c2) {
		this.setDernierResultat(c1 * c2);
		return this.getDernierResultat();
	}

	@Override
	public double div(double c1, double c2) {
		this.setDernierResultat(c1 / c2);
		return this.getDernierResultat();
	}
}
