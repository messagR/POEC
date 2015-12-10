/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IOperationEntity;

/**
 * Dao pour les operations.
 */
public interface IOperationDAO extends IDAO<IOperationEntity> {

	/**
	 * Selectionne les operations en fonction des criteres donnes.
	 *
	 * @param unCompteId
	 *            un compte id
	 * @param unDebut
	 *            une date de debut
	 * @param uneFin
	 *            une date de fin
	 * @param pCreditDebit
	 *            si vrai trouvera les credit, si faux trouvera les débits, si
	 *            null trouvera les deux
	 * @param connexion
	 *            la connexion (peut etre nulle)
	 * @return la liste des opertaions repondant aux criteres
	 * @throws ExceptionDao
	 *             si un probleme survient
	 *
	 */
	public abstract List<IOperationEntity> selectCriteria(Integer unCompteId,
			Date unDebut, Date uneFin, Boolean pCreditDebit,
			Connection connexion) throws ExceptionDao;

}