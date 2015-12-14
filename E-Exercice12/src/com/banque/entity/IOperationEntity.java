/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.entity;

import java.sql.Timestamp;

public interface IOperationEntity extends IEntity {

	public abstract Timestamp getDate();

	public abstract String getLibelle();

	public abstract Double getMontant();

	public abstract void setDate(Timestamp uneDate);

	public abstract void setLibelle(String unLibelle);

	public abstract void setMontant(Double unMontant);

	public abstract Integer getCompteId();

	public abstract void setCompteId(Integer compteId);

}