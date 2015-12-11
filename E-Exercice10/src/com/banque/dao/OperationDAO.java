/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.banque.dao.ex.ExceptionDao;
import com.banque.dao.util.AbstractJdbcMapper;
import com.banque.dao.util.OperationJdbcMapper;
import com.banque.entity.IOperationEntity;

/**
 * Gestion des operations.
 */
@Repository("operationDAO")
public class OperationDAO extends AbstractDAO<IOperationEntity> implements
IOperationDAO {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de l'objet.
	 */
	public OperationDAO() {
		super();
	}

	@Override
	public String getTableName() {
		return "operation";
	}

	@Override
	public String getPkName() {
		return "id";
	}

	@Override
	protected AbstractJdbcMapper<IOperationEntity> getMapper() {
		return new OperationJdbcMapper();
	}

	@Override
	public String getAllColumnNames() {
		return "id,libelle,montant,date,compteId";
	}

	@Override
	public IOperationEntity insert(IOperationEntity pUneEntite) throws ExceptionDao {
		if (pUneEntite == null) {
			return null;
		}
		try {
			PreparedStatementCreator psc = new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connexion) throws SQLException {
					PreparedStatement ps = connexion.prepareStatement(
							"insert into " + OperationDAO.this.getTableName()
							+ " (libelle, montant, date, compteId) values (?,?,?,?);",
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, pUneEntite.getLibelle());
					ps.setDouble(2, pUneEntite.getMontant().doubleValue());
					ps.setTimestamp(3, pUneEntite.getDate());
					ps.setInt(4, pUneEntite.getCompteId().intValue());
					return ps;
				}
			};
			KeyHolder kh = new GeneratedKeyHolder();
			this.getJdbcTemplate().update(psc, kh);
			pUneEntite.setId(Integer.valueOf(kh.getKey().intValue()));
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}

		return pUneEntite;
	}

	@Override
	public IOperationEntity update(IOperationEntity pUneEntite) throws ExceptionDao {
		if (pUneEntite == null) {
			return null;
		}
		if (pUneEntite.getId() == null) {
			throw new ExceptionDao("L'entite n'a pas d'ID");
		}
		try {
			this.getJdbcTemplate()
			.update("update " + this.getTableName()
			+ " set libelle=?, montant=?, date=?, compteId=? where "
			+ this.getPkName() + "=?;", pUneEntite.getLibelle(), pUneEntite.getMontant().doubleValue(),
					pUneEntite.getDate(), pUneEntite.getCompteId().intValue(), pUneEntite.getId().intValue());
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}

		return pUneEntite;
	}

	@Override
	public List<IOperationEntity> selectCriteria(Integer unCompteId,
			Date unDebut, Date uneFin, Boolean pCreditDebit)
					throws ExceptionDao {
		List<IOperationEntity> result = new ArrayList<IOperationEntity>();
		try {
			StringBuffer request = new StringBuffer();
			request.append("select ").append(this.getAllColumnNames())
			.append(" from ");
			request.append(this.getTableName());
			request.append(" where compteId=?");
			List<Object> gaps = new ArrayList<Object>();
			gaps.add(unCompteId);
			if ((unDebut != null) && (uneFin == null)) {
				request.append(" and date >= ?");
				gaps.add(unDebut);
			}

			if ((uneFin != null) && (unDebut == null)) {
				request.append(" and date <= ?");
				gaps.add(uneFin);
			}

			if ((uneFin != null) && (unDebut != null)) {
				request.append(" and date between ? and ?");
				gaps.add(unDebut);
				gaps.add(uneFin);
			}

			if (pCreditDebit != null) {
				if (pCreditDebit.booleanValue()) {
					request.append(" and montant >= 0");
				} else {
					request.append(" and montant <= 0");
				}
			}

			request.append(" order by date DESC");

			request.append(';');
			if (this.LOG.isDebugEnabled()) {
				this.LOG.debug("Requete: " + request.toString());
			}
			result = this.getJdbcTemplate().query(request.toString(), gaps.toArray(new Object[gaps.size()]),
					(RowMapper<IOperationEntity>) this.getMapper());
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}

		return result;
	}
}