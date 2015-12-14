/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import java.util.List;

import org.hibernate.Session;

import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IEntity;

/**
 * Dao generique.
 *
 * @param <T>
 *            le type d'entite
 */
public interface IDAO<T extends IEntity> {

	/**
	 * Donne le nom de la classe des entites gerees
	 *
	 * @return le nom de la classe des entites gerees
	 */
	public abstract String getEntityClassName();

	/**
	 * Retourne le nom de la colonne qui represente la clef primaire de T.
	 *
	 * @return e nom de la colonne qui represente la clef primaire de T.
	 */
	public abstract String getPkName();

	/**
	 * Insert un element.
	 *
	 * @param uneEntite
	 *            l'element a inserer.
	 * @param pSession
	 *            une Session, peut etre null
	 * @return l'element insere.
	 * @throws ExceptionDao
	 *             si une erreur survient
	 */
	public abstract T insert(T uneEntite, Session pSession) throws ExceptionDao;

	/**
	 * Met a jour un element.
	 *
	 * @param uneEntite
	 *            l'element a mettre a jour.
	 * @param pSession
	 *            une Session, peut etre null
	 * @return l'element mis a jour.
	 * @throws ExceptionDao
	 *             si une erreur survient
	 */
	public abstract T update(T uneEntite, Session pSession) throws ExceptionDao;

	/**
	 * Supprime un element.
	 *
	 * @param pUneEntite
	 *            l'element a supprimer.
	 * @param pSession
	 *            une Session, peut etre null
	 * @return true si il a bien ete supprime
	 * @throws ExceptionDao
	 *             si une erreur survient
	 */
	public abstract boolean delete(T pUneEntite, Session pSession) throws ExceptionDao;

	/**
	 * Selectionne l'element ayant comme valeur de clef primaire le parametre.
	 *
	 * @param pUneClef
	 *            la valeur de la clef primaire
	 * @param pSession
	 *            une Session, peut etre null
	 *
	 * @return l'element trouve ou null si aucun
	 * @throws ExceptionDao
	 *             si une erreur survient
	 */
	public abstract T select(Object pUneClef, Session pSession) throws ExceptionDao;

	/**
	 * Selectionne tous les elements qui correspondent aux criteres.
	 *
	 * @param pAWhere
	 *            une clause where (sans 'where')
	 * @param pAnOrderBy
	 *            une clause orderby (sans 'orderby')
	 * @param pSession
	 *            une Session, peut etre null
	 *
	 * @return la liste des elements trouves ou une liste vide si aucun
	 * @throws ExceptionDao
	 *             si une erreur survient
	 */
	public abstract List<T> selectAll(String pAWhere, String pAnOrderBy, Session pSession) throws ExceptionDao;

}