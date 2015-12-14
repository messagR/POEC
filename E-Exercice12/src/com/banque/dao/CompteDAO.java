/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import com.banque.entity.CompteEntity;
import com.banque.entity.ICompteEntity;

/**
 * Gestion des comptes.
 */
public class CompteDAO extends AbstractDAO<ICompteEntity> implements ICompteDAO {

	/**
	 * Constructeur de l'objet.
	 */
	public CompteDAO() {
		super();
	}

	@Override
	public String getEntityClassName() {
		return CompteEntity.class.getName();
	}

	@Override
	public String getPkName() {
		return "id";
	}

}