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

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.banque.dao.ex.ExceptionDao;
import com.banque.dao.util.AbstractJdbcMapper;
import com.banque.dao.util.CompteJdbcMapper;
import com.banque.entity.ICompteEntity;

/**
 * Gestion des comptes.
 */
@Repository("compteDAO")
public class CompteDAO extends AbstractDAO<ICompteEntity> implements ICompteDAO {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de l'objet.
	 */
	public CompteDAO() {
		super();
	}

	@Override
	public String getTableName() {
		return "compte";
	}

	@Override
	public String getPkName() {
		return "id";
	}

	@Override
	public String getAllColumnNames() {
		return "id,libelle,solde,decouvert,taux,utilisateurId";
	}

	@Override
	protected AbstractJdbcMapper<ICompteEntity> getMapper() {
		return new CompteJdbcMapper();
	}

	@Override
	public ICompteEntity insert(ICompteEntity pUneEntite)
			throws ExceptionDao {
		if (pUneEntite == null) {
			return null;
		}
		try {
			PreparedStatementCreator psc = new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connexion) throws SQLException {
					PreparedStatement ps = connexion.prepareStatement(
							"insert into " + CompteDAO.this.getTableName()
							+ " (libelle, solde, decouvert, taux, utilisateurId) values (?,?,?,?,?);",
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, pUneEntite.getLibelle());
					ps.setDouble(2, pUneEntite.getSolde().doubleValue());
					ps.setDouble(3, pUneEntite.getDecouvert().doubleValue());
					ps.setDouble(4, pUneEntite.getTaux().doubleValue());
					ps.setInt(5, pUneEntite.getUtilisateurId().intValue());
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
	public ICompteEntity update(ICompteEntity pUneEntite)
			throws ExceptionDao {
		if (pUneEntite == null) {
			return null;
		}
		if (pUneEntite.getId() == null) {
			throw new ExceptionDao("L'entite n'a pas d'ID");
		}
		try {
			this.getJdbcTemplate()
			.update(
					"update " + this.getTableName()
					+ " set libelle=?, solde=?, decouvert=?, taux=?, utilisateurId=? where " + this.getPkName() + "=?;",
					pUneEntite.getLibelle(), pUneEntite.getSolde().doubleValue(),
					pUneEntite.getDecouvert().doubleValue(), pUneEntite.getTaux().doubleValue(),
					pUneEntite.getUtilisateurId().intValue(), pUneEntite.getId().intValue());
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}

		return pUneEntite;
	}

}