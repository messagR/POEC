/**
 * test
 */
package com.banque.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.banque.entity.IOperationEntity;
import com.banque.entity.OperationEntity;

/**
 * @author PC
 *
 */
public class OperationJdbcMapper extends AbstractJdbcMapper<IOperationEntity> {

	/**
	 * Constructeur de l'objet.
	 */
	public OperationJdbcMapper() {
		super();
	}

	@Override
	public IOperationEntity mapRow(ResultSet rs, int id) throws SQLException {
		IOperationEntity result = new OperationEntity();
		result.setId(Integer.valueOf(rs.getInt("id")));
		result.setLibelle(rs.getString("libelle"));
		result.setMontant(Double.valueOf(rs.getDouble("montant")));
		result.setDate(rs.getTimestamp("date"));
		result.setCompteId(Integer.valueOf(rs.getInt("compteId")));
		return result;
	}

}