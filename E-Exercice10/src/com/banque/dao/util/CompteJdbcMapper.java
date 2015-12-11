/**
 * test
 */
package com.banque.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.banque.entity.CompteEntity;
import com.banque.entity.ICompteEntity;

/**
 * @author PC
 *
 */
public class CompteJdbcMapper extends AbstractJdbcMapper<ICompteEntity> {

	/**
	 * Constructeur de l'objet.
	 */
	public CompteJdbcMapper() {
		super();
	}

	@Override
	public ICompteEntity mapRow(ResultSet rs, int id) throws SQLException {
		ICompteEntity result = new CompteEntity();
		result.setId(Integer.valueOf(rs.getInt("id")));
		result.setLibelle(rs.getString("libelle"));
		result.setSolde(Double.valueOf(rs.getDouble("solde")));
		result.setDecouvert(Double.valueOf(rs.getDouble("decouvert")));
		result.setTaux(Double.valueOf(rs.getDouble("taux")));
		result.setUtilisateurId(Integer.valueOf(rs.getInt("utilisateurId")));
		return result;
	}

}