/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.banque.dao.ex.ExceptionDao;
import com.banque.dao.util.AbstractJdbcMapper;
import com.banque.entity.IEntity;

/**
 * DAO standard.
 *
 * @param <T>
 *            la cible du DAO
 */
@Repository("abstractDAO")
public abstract class AbstractDAO<T extends IEntity> implements Serializable,
IDAO<T> {

	private static final long serialVersionUID = 1L;

	protected Log LOG = LogFactory.getLog(this.getClass());

	private JdbcTemplate jdbcTemplate;

	/**
	 * Constructeur de l'objet.
	 */
	public AbstractDAO() {
		super();
	}

	protected JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	@Autowired
	public void setJdbcTemplate(@Qualifier("jdbcTemplate") JdbcTemplate pJdbcTemplate) {
		this.jdbcTemplate = pJdbcTemplate;
	}

	@Override
	public abstract String getTableName();

	@Override
	public abstract String getPkName();

	@Override
	public abstract String getAllColumnNames();

	@Override
	public abstract T insert(T uneEntite)
			throws ExceptionDao;

	@Override
	public abstract T update(T uneEntite)
			throws ExceptionDao;

	protected abstract AbstractJdbcMapper<T> getMapper();

	@Override
	public boolean delete(T pUneEntite)
			throws ExceptionDao {
		if (pUneEntite == null) {
			return false;
		}
		if (pUneEntite.getId() == null) {
			throw new ExceptionDao("L'entite n'a pas d'ID");
		}
		int resu = 0;
		try {
			resu = this.jdbcTemplate.update("delete from " + this.getTableName() + " where " + this.getPkName() + "=?;",
					pUneEntite.getId().intValue());
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}

		return resu == 1;
	}

	@Override
	public T select(Object pUneClef) throws ExceptionDao {
		if (pUneClef == null) {
			return null;
		}
		T result = null;
		int valeur;
		try {
			if (pUneClef instanceof Number) {
				valeur = ((Number) pUneClef).intValue();
			} else {
				valeur = Integer.valueOf(pUneClef.toString()).intValue();
			}
			result = this.jdbcTemplate.queryForObject("select " + this.getAllColumnNames() + " from "
					+ this.getTableName()
					+ " where "
					+ this.getPkName() + "=?;", this.getMapper(), valeur);

		} catch (Exception e) {
			throw new ExceptionDao(e);
		}

		return result;
	}

	@Override
	public List<T> selectAll(String pAWhere, String pAnOrderBy) throws ExceptionDao {
		List<T> result = new ArrayList<T>();
		try {
			StringBuffer request = new StringBuffer();
			request.append("select ").append(this.getAllColumnNames())
			.append(" from ");
			request.append(this.getTableName());
			if (pAWhere != null) {
				request.append(" where ");
				request.append(pAWhere);
			}
			if (pAnOrderBy != null) {
				request.append(" order by ");
				request.append(pAnOrderBy);
			}
			request.append(';');
			if (this.LOG.isDebugEnabled()) {
				this.LOG.debug("Requete: " + request.toString());
			}
			result = this.jdbcTemplate.query(request.toString(), (RowMapper<T>) this.getMapper());
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}

		return result;
	}
}
