/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import java.util.List;

import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IEntity;

/**
 * Interface mere de tous les DAO.
 *
 * @param <T>
 *            Objet gere par le DAO
 */
public interface IDAO<T extends IEntity> {

	/**
	 * Retourne le nom de la table cible par le DAO.
	 *
	 * @return le nom de la table principale ciblee par le DAO.
	 */
	public abstract String getTableName();

	/**
	 * Retourne le nom de la colonne qui represente la clef primaire de T.
	 *
	 * @return e nom de la colonne qui represente la clef primaire de T.
	 */
	public abstract String getPkName();

	/**
	 * Donne la liste de TOUTES les colonnes de l'element separes par une ','
	 *
	 * @return la liste de toutes les colonnes.
	 */
	public abstract String getAllColumnNames();

	/**
	 * Insert un element.
	 *
	 * @param uneEntite
	 *            l'element a inserer.
	 * @param connexion
	 *            une connection, peut etre null
	 * @return l'element insere.
	 * @throws ExceptionDao
	 *             si une erreur survient
	 */
	public abstract T insert(T uneEntite)
			throws ExceptionDao;

	/**
	 * Met a jour un element.
	 *
	 * @param uneEntite
	 *            l'element a mettre a jour.
	 * @param connexion
	 *            une connection, peut etre null
	 * @return l'element mis a jour.
	 * @throws ExceptionDao
	 *             si une erreur survient
	 */
	public abstract T update(T uneEntite)
			throws ExceptionDao;

	/**
	 * Supprime un element.
	 *
	 * @param pUneEntite
	 *            l'element a supprimer.
	 * @param connexion
	 *            une connection, peut etre null
	 * @return true si il a bien ete supprime
	 * @throws ExceptionDao
	 *             si une erreur survient
	 */
	public abstract boolean delete(T pUneEntite)
			throws ExceptionDao;

	/**
	 * Selectionne l'element ayant comme valeur de clef primaire le parametre.
	 *
	 * @param pUneClef
	 *            la valeur de la clef primaire
	 * @param connexion
	 *            une connection, peut etre null
	 *
	 * @return l'element trouve ou null si aucun
	 * @throws ExceptionDao
	 *             si une erreur survient
	 */
	public abstract T select(Object pUneClef)
			throws ExceptionDao;

	/**
	 * Selectionne tous les elements qui correspondent aux criteres.
	 *
	 * @param pAWhere
	 *            une clause where (sans 'where')
	 * @param pAnOrderBy
	 *            une clause orderby (sans 'orderby')
	 * @param connexion
	 *            une connection, peut etre null
	 *
	 * @return la liste des elements trouves ou une liste vide si aucun
	 * @throws ExceptionDao
	 *             si une erreur survient
	 */
	public abstract List<T> selectAll(String pAWhere, String pAnOrderBy) throws ExceptionDao;

}