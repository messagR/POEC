package fr.banque;

class Entite implements IEntite {

	private int numero;

	protected Entite() {
		this(-1);
	}

	Entite(int numero) {
		super();
		this.setNumero(numero);
	}

	@Override
	public int getNumero() {
		return this.numero;
	}

	@Override
	public void setNumero(int num) {
		this.numero = num;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append(" [numero = ").append(this.getNumero()).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		if (this.getNumero() == -1) {
			return super.hashCode();
		}
		return (this.getClass().getName() + "_" + this.getNumero()).hashCode();
	}

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
