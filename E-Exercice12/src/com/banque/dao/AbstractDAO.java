/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IEntity;

/**
 * DAO standard.
 *
 * @param <T>
 *            la cible du DAO
 */
@Repository("abstactDAO")
abstract class AbstractDAO<T extends IEntity> implements IDAO<T> {

	protected Log LOG = LogFactory.getLog(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Constructeur de l'objet.
	 */
	AbstractDAO() {
		super();
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public T insert(T uneEntite) throws ExceptionDao {
		if (uneEntite == null) {
			return null;
		}
		try {
			Session pSession = this.getSessionFactory().getCurrentSession();
			pSession.save(uneEntite);
		} catch (Throwable e) {
			throw new ExceptionDao(e);
		}

		return uneEntite;
	}

	@Override
	public T update(T uneEntite) throws ExceptionDao {
		if (uneEntite == null) {
			return null;
		}
		try {
			Session pSession = this.getSessionFactory().getCurrentSession();

			pSession.evict(uneEntite);
			pSession.refresh(uneEntite, LockOptions.READ);
			pSession.update(uneEntite);
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}

		return uneEntite;
	}

	@Override
	public boolean delete(T pUneEntite) throws ExceptionDao {
		if (pUneEntite == null) {
			return false;
		}
		if (pUneEntite.getId() == null) {
			throw new ExceptionDao("L'entite n'a pas d'ID");
		}
		try {
			Session pSession = this.getSessionFactory().getCurrentSession();
			pSession.delete(pUneEntite);
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T select(Object pUneClef) throws ExceptionDao {

		List<T> resu = null;

		try {
			Session pSession = this.getSessionFactory().getCurrentSession();
			DetachedCriteria detachedCriteria = DetachedCriteria.forEntityName(this.getEntityClassName());
			detachedCriteria.add(Restrictions.eq(this.getPkName(), pUneClef));
			Criteria executableCriteria = detachedCriteria.getExecutableCriteria(pSession);
			resu = executableCriteria.list();
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}
		if (resu != null) {
			return resu.iterator().next();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectAll(String pAWhere, String pAnOrderBy) throws ExceptionDao {
		List<T> result = new ArrayList<T>();

		try {
			Session pSession = this.getSessionFactory().getCurrentSession();
			Query queryObject = null;
			StringBuffer request = new StringBuffer();
			request.append("select entity from ").append(this.getEntityClassName());
			request.append(" as entity");
			if (pAWhere != null) {
				request.append(" where ");
				request.append(pAWhere);
			}
			if (pAnOrderBy != null) {
				request.append(" order by ");
				request.append(pAnOrderBy);
			}
			if (this.LOG.isDebugEnabled()) {
				this.LOG.debug("Requete OQL: " + request.toString());
			}

			queryObject = pSession.createQuery(request.toString());
			result.addAll(queryObject.list());
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}
		return result;
	}
}
