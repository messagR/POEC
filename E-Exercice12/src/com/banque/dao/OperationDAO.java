/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IOperationEntity;
import com.banque.entity.OperationEntity;
import com.banque.util.HibernateSessionFactory;

/**
 * Gestion des operations.
 */
public class OperationDAO extends AbstractDAO<IOperationEntity> implements IOperationDAO {

	/**
	 * Constructeur de l'objet.
	 */
	public OperationDAO() {
		super();
	}

	@Override
	public String getEntityClassName() {
		return OperationEntity.class.getName();
	}

	@Override
	public String getPkName() {
		return "id";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IOperationEntity> selectCriteria(Integer unCompteId, Date unDebut, Date uneFin, Boolean pCreditDebit,
			Session pSession) throws ExceptionDao {

		List<IOperationEntity> result = new ArrayList<IOperationEntity>();
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
			request.append(" as entity where entity.compteId=?");
			List<Object> gaps = new ArrayList<Object>();
			gaps.add(unCompteId);
			if ((unDebut != null) && (uneFin == null)) {
				request.append(" and entity.date >= ?");
				gaps.add(unDebut);
			}

			if ((uneFin != null) && (unDebut == null)) {
				request.append(" and entity.date <= ?");
				gaps.add(uneFin);
			}

			if ((uneFin != null) && (unDebut != null)) {
				request.append(" and entity.date between ? and ?");
				gaps.add(unDebut);
				gaps.add(uneFin);
			}

			if (pCreditDebit != null) {
				if (pCreditDebit.booleanValue()) {
					request.append(" and entity.montant >= 0");
				} else {
					request.append(" and entity.montant <= 0");
				}
			}

			request.append(" order by entity.date DESC");

			if (this.LOG.isDebugEnabled()) {
				this.LOG.debug("Requete OQL: " + request.toString());
			}

			queryObject = pSession.createQuery(request.toString());
			Iterator<Object> iterGao = gaps.iterator();
			int id = 0;
			while (iterGao.hasNext()) {
				queryObject.setParameter(id++, iterGao.next());
			}

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