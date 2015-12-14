/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IUtilisateurEntity;

/**
 * Interface representant l'utilisateur.
 */
public interface IUtilisateurDAO extends IDAO<IUtilisateurEntity> {

	/**
	 * Selectionne le premier utilisateur ayant le login indique.
	 *
	 * @param pLogin
	 *            un login.
	 * @param pSession
	 *            une session (peut etre null)
	 * @return l'utilisateur
	 * @throws ExceptionDao
	 *             si une erreur survient
	 */
	public abstract IUtilisateurEntity selectLogin(String pLogin) throws ExceptionDao;

}