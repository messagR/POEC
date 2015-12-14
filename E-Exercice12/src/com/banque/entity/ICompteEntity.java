/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.entity;

import java.math.BigDecimal;

/**
 *
 * @author renaud91
 *
 */
public interface ICompteEntity extends IEntity {

	public abstract BigDecimal getTaux();

	public abstract void setTaux(BigDecimal aTaux);

	public abstract BigDecimal getDecouvert();

	public abstract String getLibelle();

	public abstract BigDecimal getSolde();

	public abstract void setDecouvert(BigDecimal unDecouvert);

	public abstract void setLibelle(String unLibelle);

	public abstract void setSolde(BigDecimal unSolde);

	public abstract Integer getUtilisateurId();

	public abstract void setUtilisateurId(Integer utilisateurId);

}