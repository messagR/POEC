/**
 * test
 */
package com.banque.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.banque.entity.IUtilisateurEntity;
import com.banque.entity.UtilisateurEntity;

/**
 * @author PC
 *
 */
public class UtilisateurJdbcMapper extends AbstractJdbcMapper<IUtilisateurEntity> {

	/**
	 * Constructeur de l'objet.
	 */
	public UtilisateurJdbcMapper() {
		super();
	}

	@Override
	public IUtilisateurEntity mapRow(ResultSet rs, int id) throws SQLException {
		IUtilisateurEntity result = new UtilisateurEntity();
		result.setId(Integer.valueOf(rs.getInt("id")));
		result.setNom(rs.getString("nom"));
		result.setPrenom(rs.getString("prenom"));
		result.setLogin(rs.getString("login"));
		result.setPassword(rs.getString("password"));
		result.setSex(Byte.valueOf(rs.getByte("sex")));
		result.setDerniereConnection(rs.getTimestamp("derniereConnection"));
		return result;
	}

}