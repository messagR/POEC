/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.service;

import java.util.List;

import com.banque.entity.ICompteEntity;
import com.banque.service.ex.AucunDroitException;
import com.banque.service.ex.EntityIntrouvableException;
import com.banque.service.ex.ErreurTechniqueException;

public interface ICompteService {

	/**
	 * Recupere un compte.
	 *
	 * @param unUtilisateurId
	 *            un id d'utilisateur
	 * @param unCompteId
	 *            un id de compte
	 * @return le compte trouve
	 * @throws EntityIntrouvableException
	 *             si le compte n'existe pas
	 * @throws AucunDroitException
	 *             si l'utilisateur n'a pas le droit d'acceder a ce compte
	 * @throws NullPointerException
	 *             si un des parametres est nul
	 * @throws ErreurTechniqueException
	 *             si un probleme survient
	 */
	public abstract ICompteEntity select(Integer unUtilisateurId,
			Integer unCompteId) throws EntityIntrouvableException,
			AucunDroitException, NullPointerException, ErreurTechniqueException;

	/**
	 * Recupere tous les comptes d'un utilisateur.
	 *
	 * @param unUtilisateurId
	 *            un id d'utilisateur
	 * @return les comptes trouves, une liste vide si aucun
	 * @throws EntityIntrouvableException
	 *             si le compte n'existe pas
	 * @throws AucunDroitException
	 *             si l'utilisateur n'a pas le droit d'acceder a ce compte
	 * @throws NullPointerException
	 *             si un des parametres est nul
	 * @throws ErreurTechniqueException
	 *             si un probleme survient
	 */
	public abstract List<ICompteEntity> selectAll(Integer unUtilisateurId)
			throws EntityIntrouvableException, AucunDroitException,
			NullPointerException, ErreurTechniqueException;

}