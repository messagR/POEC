package fr.banque;

public abstract class Entite {

	private int numero;

	protected Entite() {
		this(-1);
	}

	Entite(int numero) {
		super();
		this.setNumero(numero);
	}

	/**
	 * @return the numero
	 */
	public int getNumero() {
		return this.numero;
	}

	/**
	 * @param numero
	 */
	public void setNumero(int num) {
		this.numero = num;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append(" [numero = ").append(this.getNumero()).append("]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (this.getNumero() == -1) {
			return super.hashCode();
		}
		return (this.getClass().getName() + "_" + this.getNumero()).hashCode();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof Entite){
			Entite c = (Entite) obj;
			return this.getNumero() == c.numero;
		}
		return false;
	}

}
