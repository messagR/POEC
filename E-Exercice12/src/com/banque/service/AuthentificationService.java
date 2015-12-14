/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banque.dao.IUtilisateurDAO;
import com.banque.dao.UtilisateurDAO;
import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IUtilisateurEntity;
import com.banque.service.ex.AuthentificationException;
import com.banque.service.ex.ErreurTechniqueException;
import com.banque.service.ex.MauvaisMotdepasseException;
import com.banque.service.ex.UtilisateurInconnuException;

/**
 * Gestion de l'authentification.
 */
@Service("authentificationService")
public class AuthentificationService extends AbstractService implements IAuthentificationService {

	@Autowired
	private IUtilisateurDAO utilisateurDAO;

	/**
	 * Constructeur de l'objet.
	 */
	public AuthentificationService() {
		super();
		this.setUtilisateurDAO(new UtilisateurDAO());
	}

	/**
	 * Recupere la propriete <i>utilisateurDAO</i>.
	 *
	 * @return the utilisateurDAO la valeur de la propriete.
	 */
	public IUtilisateurDAO getUtilisateurDAO() {
		return this.utilisateurDAO;
	}

	/**
	 * Fixe la propriete <i>utilisateurDAO</i>.
	 *
	 * @param pUtilisateurDAO
	 *            la nouvelle valeur pour la propriete utilisateurDAO.
	 */
	public void setUtilisateurDAO(IUtilisateurDAO pUtilisateurDAO) {
		this.utilisateurDAO = pUtilisateurDAO;
	}

	@Override
	public IUtilisateurEntity authentifier(String pLogin, String pPassword)
			throws AuthentificationException, ErreurTechniqueException {
		if ((pLogin == null) || (pLogin.trim().length() == 0)) {
			throw new NullPointerException("login");
		}
		if ((pPassword == null) || (pPassword.trim().length() == 0)) {
			throw new NullPointerException("password");
		}
		IUtilisateurEntity resultat = null;
		try {
			resultat = this.getUtilisateurDAO().selectLogin(pLogin);
		} catch (ExceptionDao e) {
			throw new ErreurTechniqueException(e);
		}
		if (resultat == null) {
			throw new UtilisateurInconnuException();
		}
		if (!pPassword.equals(resultat.getPassword())) {
			throw new MauvaisMotdepasseException();
		}

		return resultat;
	}
}
