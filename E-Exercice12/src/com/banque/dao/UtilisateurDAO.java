/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IUtilisateurEntity;
import com.banque.entity.UtilisateurEntity;

/**
 * Gestion des operations.
 */
@Repository("utilisateurDAO")
public class UtilisateurDAO extends AbstractDAO<IUtilisateurEntity> implements IUtilisateurDAO {

	/**
	 * Constructeur de l'objet.
	 */
	public UtilisateurDAO() {
		super();
	}

	@Override
	public String getEntityClassName() {
		return UtilisateurEntity.class.getName();
	}

	@Override
	public String getPkName() {
		return "id";
	}

	/**
	 * Selectionne le premier utilisateur ayant le login indique.
	 *
	 * @param pLogin
	 *            un login.
	 * @param pSession
	 *            une Session (peut etre null)
	 * @return l'utilisateur
	 * @throws ExceptionDao
	 *             si une erreur survient
	 */
	@Override
	public IUtilisateurEntity selectLogin(String pLogin) throws ExceptionDao {
		List<IUtilisateurEntity> allLogin = super.selectAll("entity.login='" + pLogin + "'", null);
		if ((allLogin == null) || allLogin.isEmpty()) {
			return null;
		}
		// On retourne le premier
		return allLogin.iterator().next();
	}

}