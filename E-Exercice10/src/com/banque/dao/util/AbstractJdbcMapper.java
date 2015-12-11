/**
 * test
 */
package com.banque.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author PC
 *
 */
public abstract class AbstractJdbcMapper<T> implements RowMapper<T>, ResultSetExtractor<List<T>> {

	/**
	 * Constructeur de l'objet.
	 */
	AbstractJdbcMapper() {
		super();
	}

	@Override
	public List<T> extractData(ResultSet rs) throws SQLException, DataAccessException {
		// Cette methode du ResultSetExtractor fait simplement appel a celle du
		// RowMapper qui
		// devra etre ecrite par les enfants de cette classe.
		List<T> resultat = new ArrayList<>();
		int id = 0;
		while (rs.next()) {
			T result = this.mapRow(rs, id);
			resultat.add(result);
			id++;
		}
		return resultat;
	}

	@Override
	public abstract T mapRow(ResultSet rs, int id) throws SQLException;
}