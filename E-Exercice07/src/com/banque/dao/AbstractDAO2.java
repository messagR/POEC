/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IEntity;

/**
 * DAO standard.
 *
 * @param <T>
 *            la cible du DAO
 */
public abstract class AbstractDAO2<T extends IEntity> implements Serializable,
IDAO<T> {

	private static final long serialVersionUID = 1L;

	// private final static String DB_DRIVER = "com.mysql.jdbc.Driver";
	// private final static String DB_URL =
	// "jdbc:mysql://localhost:3306/banque";
	// private final static String DB_LOGIN = "root";
	// private final static String DB_PWD = "root";

	private Properties property;

	protected Log LOG = LogFactory.getLog(this.getClass());

	/**
	 * Constructeur de l'objet.
	 */
	public AbstractDAO2() {
		super();
	}

	public Properties getProperty() {
		return this.property;
	}

	public void setPr(Properties property) {
		this.property = property;
	}

	@Override
	public abstract String getTableName();

	@Override
	public abstract String getPkName();

	@Override
	public abstract String getAllColumnNames();

	@Override
	public abstract T insert(T uneEntite, Connection connexion)
			throws ExceptionDao;

	@Override
	public abstract T update(T uneEntite, Connection connexion)
			throws ExceptionDao;

	@Override
	public boolean delete(T pUneEntite, Connection connexion)
			throws ExceptionDao {
		if (pUneEntite == null) {
			return false;
		}
		if (pUneEntite.getId() == null) {
			throw new ExceptionDao("L'entite n'a pas d'ID");
		}
		boolean doCommit = false;
		boolean connexionCreated = connexion == null;
		PreparedStatement ps = null;
		try {
			if (connexionCreated) {
				connexion = this.getConnexion();
				connexion.setAutoCommit(false);
			}

			ps = connexion.prepareStatement("delete from "
					+ this.getTableName() + " where " + this.getPkName()
					+ "=?;");
			ps.setInt(1, pUneEntite.getId().intValue());

			ps.execute();
			doCommit = true;

		} catch (Exception e) {
			throw new ExceptionDao(e);
		} finally {
			if (connexionCreated && (connexion != null)) {
				if (doCommit) {
					try {
						connexion.commit();
					} catch (SQLException e) {
						this.LOG.error("Impossible de faire un commit!", e);
					}
				} else {
					try {
						connexion.rollback();
					} catch (SQLException e) {
						this.LOG.error("Impossible de faire un rollback!", e);
					}
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					this.LOG.error("Impossible de fermer le prepareStatement!",
							e);
				}
			}
			if (connexionCreated && (connexion != null)) {
				try {
					connexion.close();
				} catch (SQLException e) {
					this.LOG.error("Impossible de fermer la connexion!", e);
				}

			}
		}

		return doCommit;
	}

	@Override
	public T select(Object pUneClef, Connection connexion) throws ExceptionDao {
		if (pUneClef == null) {
			return null;
		}
		T result = null;

		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean connexionCreated = connexion == null;
		try {
			if (connexionCreated) {
				connexion = this.getConnexion();
				connexion.setReadOnly(true);
			}
			ps = connexion.prepareStatement("select "
					+ this.getAllColumnNames() + " from " + this.getTableName()
					+ " where " + this.getPkName() + "=?;");
			if (pUneClef instanceof Number) {
				ps.setInt(1, ((Number) pUneClef).intValue());
			} else {
				ps.setInt(1, Integer.valueOf(pUneClef.toString()).intValue());
			}
			rs = ps.executeQuery();
			rs.next();
			result = this.fromResultSet(rs);

		} catch (Exception e) {
			throw new ExceptionDao(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					this.LOG.error("Impossible de fermer le resultset!", e);
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					this.LOG.error("Impossible de fermer le prepareStatement!",
							e);
				}
			}
			if (connexionCreated && (connexion != null)) {
				try {
					connexion.close();
				} catch (SQLException e) {
					this.LOG.error("Impossible de fermer la connexion!", e);
				}

			}
		}

		return result;
	}

	@Override
	public List<T> selectAll(String pAWhere, String pAnOrderBy,
			Connection connexion) throws ExceptionDao {
		List<T> result = new ArrayList<T>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean connexionCreated = connexion == null;
		try {
			if (connexionCreated) {
				connexion = this.getConnexion();
				connexion.setReadOnly(true);
			}

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
			ps = connexion.prepareStatement(request.toString());

			rs = ps.executeQuery();
			while (rs.next()) {
				T ce = this.fromResultSet(rs);
				result.add(ce);
			}

		} catch (Exception e) {
			throw new ExceptionDao(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					this.LOG.error("Impossible de fermer le resultset!", e);
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					this.LOG.error("Impossible de fermer le prepareStatement!",
							e);
				}
			}
			if (connexionCreated && (connexion != null)) {
				try {
					connexion.close();
				} catch (SQLException e) {
					this.LOG.error("Impossible de fermer la connexion!", e);
				}

			}
		}

		return result;
	}

	@Override
	public Connection getConnexion() throws ExceptionDao {
		try {
			// Class.forName(AbstractDAO.DB_DRIVER);
			Class.forName(this.getProperty().getProperty("db.driver"));
		} catch (Exception e) {
			LogFactory.getLog(AbstractDAO2.class).error(
					"Impossible de charger le driver pour la base", e);
			throw new ExceptionDao(e);
		}

		try {
			// return DriverManager.getConnection(AbstractDAO.DB_URL,
			// AbstractDAO.DB_LOGIN, AbstractDAO.DB_PWD);
			return DriverManager.getConnection(this.getProperty().getProperty("db.url"),
					this.getProperty().getProperty("db.login"), this.getProperty().getProperty("db.password"));
		} catch (SQLException e) {
			LogFactory.getLog(AbstractDAO2.class).error(
					"Erreur lors de l'acces a la base", e);
			throw new ExceptionDao(e);
		}
	}

	/**
	 * Transforme un resultset en element
	 *
	 * @param pR
	 *            le result set
	 * @return l'ement cree
	 * @throws SQLException
	 *             si une erreur survient
	 */
	protected abstract T fromResultSet(ResultSet pR) throws SQLException;

	/**
	 * Place les elements dans la requete.
	 *
	 * @param ps
	 *            la requete
	 * @param gaps
	 *            les elements
	 * @throws SQLException
	 *             si un des elements ne rentre pas
	 */
	protected void setPrepareStatement(PreparedStatement ps, List<Object> gaps)
			throws SQLException {
		Iterator<Object> iter = gaps.iterator();
		int id = 0;
		while (iter.hasNext()) {
			id++;
			Object lE = iter.next();
			if (lE == null) {
				continue;
			}
			if (lE instanceof String) {
				ps.setString(id, (String) lE);
			} else if (lE instanceof Date) {
				ps.setDate(id, (Date) lE);
			} else if (lE instanceof java.util.Date) {
				ps.setDate(id, new Date(((java.util.Date) lE).getTime()));
			} else if (lE instanceof Timestamp) {
				ps.setTimestamp(id, (Timestamp) lE);
			} else if (lE instanceof Integer) {
				ps.setInt(id, ((Integer) lE).intValue());
			} else if (lE instanceof Double) {
				ps.setDouble(id, ((Double) lE).doubleValue());
			} else {
				throw new SQLException("Invalid type '"
						+ lE.getClass().getSimpleName() + "'");
			}

		}
	}
}
