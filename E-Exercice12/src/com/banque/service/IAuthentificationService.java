/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.service;

import com.banque.entity.IUtilisateurEntity;
import com.banque.service.ex.AuthentificationException;
import com.banque.service.ex.ErreurTechniqueException;

/**
 * Service d'authentification
 */
public interface IAuthentificationService {

	/**
	 * Authentifie un utilisateur.
	 *
	 * @param pLogin
	 *            le login
	 * @param pPassword
	 *            le password
	 * @return l'utilisateur authentifie, leve une exception sinon
	 * @throws AuthentificationException
	 *             si un probleme survient
	 * @throws ErreurTechniqueException
	 *             si un probleme survient
	 * @throws NullPointerException
	 *             avec comme message le nom du parametre null
	 */
	public abstract IUtilisateurEntity authentifier(String pLogin, String pPassword)
			throws AuthentificationException, ErreurTechniqueException;

}