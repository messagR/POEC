/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.entity;

import java.io.Serializable;

/**
 * Le bean qui represente une entité. <br>
 */
public abstract class AbstractEntity implements Serializable, IEntity {

	private static final long serialVersionUID = 1L;

	protected Integer id;

	/**
	 * Constructeur de l'objet. <br>
	 */
	public AbstractEntity() {
		super();
	}

	/**
	 * Constructeur de l'objet. <br>
	 *
	 * @param unId
	 *            l'id d'un compte
	 */
	public AbstractEntity(Integer unId) {
		super();
		this.setId(unId);
	}

	@Override
	public final Integer getId() {
		return this.id;
	}

	@Override
	public final void setId(Integer unId) {
		this.id = unId;
	}

	@Override
	public int hashCode() {
		if (this.getId() != null) {
			return (this.getClass().getName() + "-" + this.getId()).hashCode();
		}
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof AbstractEntity) {
			return (((IEntity) obj).getId() == this.getId())
					|| ((IEntity) obj).getId().equals(this.getId());
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{class=");
		sb.append(this.getClass().getName());
		sb.append(",id=");
		sb.append(this.getId());
		sb.append('}');
		return sb.toString();
	}
}