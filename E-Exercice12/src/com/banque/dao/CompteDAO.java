/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import org.springframework.stereotype.Repository;

import com.banque.entity.CompteEntity;
import com.banque.entity.ICompteEntity;

/**
 * Gestion des comptes.
 */
@Repository("compteDAO")
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