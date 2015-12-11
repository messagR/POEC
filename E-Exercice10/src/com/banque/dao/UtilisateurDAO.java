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
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.banque.dao.ex.ExceptionDao;
import com.banque.dao.util.AbstractJdbcMapper;
import com.banque.dao.util.UtilisateurJdbcMapper;
import com.banque.entity.IUtilisateurEntity;

/**
 * Gestion des utilisateurs.
 */
@Repository("utilisateurDAO")
public class UtilisateurDAO extends AbstractDAO<IUtilisateurEntity> implements
IUtilisateurDAO {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de l'objet.
	 */
	public UtilisateurDAO() {
		super();
	}

	@Override
	public String getTableName() {
		return "utilisateur";
	}

	@Override
	public String getPkName() {
		return "id";
	}

	@Override
	public String getAllColumnNames() {
		return "id,nom,prenom,login,password,sex,derniereConnection";
	}

	@Override
	protected AbstractJdbcMapper<IUtilisateurEntity> getMapper() {
		return new UtilisateurJdbcMapper();
	}

	@Override
	public IUtilisateurEntity insert(IUtilisateurEntity pUneEntite) throws ExceptionDao {
		if (pUneEntite == null) {
			return null;
		}
		try {
			PreparedStatementCreator psc = new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connexion) throws SQLException {
					PreparedStatement ps = connexion.prepareStatement(
							"insert into " + UtilisateurDAO.this.getTableName()
							+ " (nom,prenom,login,password,sex,derniereConnection) values (?,?,?,?,?,?);",
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, pUneEntite.getNom());
					ps.setString(2, pUneEntite.getPrenom());
					ps.setString(3, pUneEntite.getLogin());
					ps.setString(4, pUneEntite.getPassword());
					ps.setByte(5, pUneEntite.getSex().byteValue());
					ps.setTimestamp(6, pUneEntite.getDerniereConnection());
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
	public IUtilisateurEntity update(IUtilisateurEntity pUneEntite) throws ExceptionDao {
		if (pUneEntite == null) {
			return null;
		}
		if (pUneEntite.getId() == null) {
			throw new ExceptionDao("L'entite n'a pas d'ID");
		}
		try {
			this.getJdbcTemplate()
			.update("update " + this.getTableName()
			+ " set nom=?,prenom=?,login=?,password=?,sex=?,derniereConnection=? where "
			+ this.getPkName() + "=?;", pUneEntite.getNom(), pUneEntite.getPrenom(),
			pUneEntite.getLogin(), pUneEntite.getPassword(), pUneEntite.getSex().byteValue(),
					pUneEntite.getDerniereConnection(), pUneEntite.getId().intValue());
		} catch (Exception e) {
			throw new ExceptionDao(e);
		}

		return pUneEntite;
	}

	@Override
	public IUtilisateurEntity selectLogin(String pLogin)
			throws ExceptionDao {
		List<IUtilisateurEntity> allLogin = this.selectAll("login='" + pLogin + "'", null);
		if ((allLogin == null) || allLogin.isEmpty()) {
			return null;
		}
		// On retourne le premier
		return allLogin.iterator().next();
	}

}