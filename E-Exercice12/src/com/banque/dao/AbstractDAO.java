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
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IEntity;
import com.banque.util.HibernateSessionFactory;

/**
 * DAO standard.
 *
 * @param <T>
 *            la cible du DAO
 */
abstract class AbstractDAO<T extends IEntity> implements IDAO<T> {

	protected Log LOG = LogFactory.getLog(this.getClass());

	/**
	 * Constructeur de l'objet.
	 */
	AbstractDAO() {
		super();
	}

	@Override
	public T insert(T uneEntite, Session pSession) throws ExceptionDao {
		if (uneEntite == null) {
			return null;
		}
		boolean doCommit = false;
		boolean pSessionCreated = pSession == null;
		try {
			if (pSessionCreated) {
				pSession = HibernateSessionFactory.getInstance().openSession();
				pSession.beginTransaction();
			}
			pSession.save(uneEntite);
			doCommit = true;
		} catch (Exception e) {
			throw new ExceptionDao(e);
		} finally {
			if (pSessionCreated && (pSession != null)) {
				if (doCommit) {
					pSession.getTransaction().commit();
				} else {
					pSession.getTransaction().rollback();
				}
				try {
					pSession.close();
				} catch (HibernateException e) {
					this.LOG.error("Impossible de fermer la pSession!", e);
				}
			}
		}

		return uneEntite;
	}

	@Override
	public T update(T uneEntite, Session pSession) throws ExceptionDao {
		if (uneEntite == null) {
			return null;
		}
		boolean doCommit = false;
		boolean pSessionCreated = pSession == null;
		try {
			if (pSessionCreated) {
				pSession = HibernateSessionFactory.getInstance().openSession();
				pSession.beginTransaction();
			}

			pSession.evict(uneEntite);
			pSession.refresh(uneEntite, LockOptions.READ);
			pSession.update(uneEntite);
			doCommit = true;
		} catch (Exception e) {
			throw new ExceptionDao(e);
		} finally {
			if (pSessionCreated && (pSession != null)) {
				if (doCommit) {
					pSession.getTransaction().commit();
				} else {
					pSession.getTransaction().rollback();
				}
				try {
					pSession.close();
				} catch (HibernateException e) {
					this.LOG.error("Impossible de fermer la pSession!", e);
				}
			}
		}

		return uneEntite;
	}

	@Override
	public boolean delete(T pUneEntite, Session pSession) throws ExceptionDao {
		if (pUneEntite == null) {
			return false;
		}
		if (pUneEntite.getId() == null) {
			throw new ExceptionDao("L'entite n'a pas d'ID");
		}
		boolean doCommit = false;
		boolean pSessionCreated = pSession == null;
		try {
			if (pSessionCreated) {
				pSession = HibernateSessionFactory.getInstance().openSession();
				pSession.beginTransaction();
			}
			pSession.delete(pUneEntite);
			doCommit = true;
		} catch (Exception e) {
			throw new ExceptionDao(e);
		} finally {
			if (pSessionCreated && (pSession != null)) {
				if (doCommit) {
					pSession.getTransaction().commit();
				} else {
					pSession.getTransaction().rollback();
				}
				try {
					pSession.close();
				} catch (HibernateException e) {
					this.LOG.error("Impossible de fermer la pSession!", e);
				}
			}
		}

		return doCommit;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T select(Object pUneClef, Session pSession) throws ExceptionDao {

		List<T> resu = null;
		boolean pSessionCreated = pSession == null;
		boolean doCommit = false;

		try {
			if (pSessionCreated) {
				pSession = HibernateSessionFactory.getInstance().openSession();
				pSession.beginTransaction();
			}
			DetachedCriteria detachedCriteria = DetachedCriteria.forEntityName(this.getEntityClassName());
			detachedCriteria.add(Restrictions.eq(this.getPkName(), pUneClef));
			Criteria executableCriteria = detachedCriteria.getExecutableCriteria(pSession);
			resu = executableCriteria.list();
			doCommit = true;
		} catch (Exception e) {
			throw new ExceptionDao(e);
		} finally {
			if (pSessionCreated && (pSession != null)) {
				if (doCommit) {
					pSession.getTransaction().commit();
				} else {
					pSession.getTransaction().rollback();
				}
				try {
					pSession.close();
				} catch (HibernateException e) {
					this.LOG.error("Impossible de fermer la pSession!", e);
				}
			}
		}
		if (resu != null) {
			return resu.iterator().next();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectAll(String pAWhere, String pAnOrderBy, Session pSession) throws ExceptionDao {
		List<T> result = new ArrayList<T>();
		boolean pSessionCreated = pSession == null;
		boolean doCommit = false;

		try {
			if (pSessionCreated) {
				pSession = HibernateSessionFactory.getInstance().openSession();
				pSession.beginTransaction();
			}
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
			doCommit = true;
		} catch (Exception e) {
			throw new ExceptionDao(e);
		} finally {
			if (pSessionCreated && (pSession != null)) {
				if (doCommit) {
					pSession.getTransaction().commit();
				} else {
					pSession.getTransaction().rollback();
				}
				try {
					pSession.close();
				} catch (HibernateException e) {
					this.LOG.error("Impossible de fermer la pSession!", e);
				}
			}
		}
		return result;
	}
}
