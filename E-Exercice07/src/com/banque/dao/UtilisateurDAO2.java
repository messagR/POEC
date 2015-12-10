/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IUtilisateurEntity;
import com.banque.entity.UtilisateurEntity;

/**
 * Gestion des utilisateurs.
 */
public class UtilisateurDAO2 extends AbstractDAO2<IUtilisateurEntity> implements
IUtilisateurDAO {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de l'objet.
	 */
	public UtilisateurDAO2() {
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
	protected IUtilisateurEntity fromResultSet(ResultSet rs)
			throws SQLException {
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

	@Override
	public IUtilisateurEntity insert(IUtilisateurEntity pUneEntite,
			Connection connexion) throws ExceptionDao {
		if (pUneEntite == null) {
			return null;
		}
		boolean doCommit = false;
		boolean connexionCreated = connexion == null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (connexionCreated) {
				connexion = super.getConnexion();
				connexion.setAutoCommit(false);
			}

			ps = connexion
					.prepareStatement(
							"insert into "
									+ this.getTableName()
									+ " (nom,prenom,login,password,sex,derniereConnection) values (?,?,?,?,?,?);",
									Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pUneEntite.getNom());
			ps.setString(2, pUneEntite.getPrenom());
			ps.setString(3, pUneEntite.getLogin());
			ps.setString(4, pUneEntite.getPassword());
			ps.setByte(5, pUneEntite.getSex().byteValue());
			ps.setTimestamp(6, pUneEntite.getDerniereConnection());

			ps.execute();
			rs = ps.getGeneratedKeys();
			rs.next();
			pUneEntite.setId(Integer.valueOf(rs.getInt(1)));
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
					this.LOG.error("Impossible de fermer le statement!", e);
				}
			}
			if (connexionCreated && (connexion != null)) {
				try {
					connexion.close();
				} catch (SQLException e) {
					this.LOG.error("Impossible de fermer le connexion!", e);
				}

			}
		}

		return pUneEntite;
	}

	@Override
	public IUtilisateurEntity update(IUtilisateurEntity pUneEntite,
			Connection connexion) throws ExceptionDao {
		if (pUneEntite == null) {
			return null;
		}
		if (pUneEntite.getId() == null) {
			throw new ExceptionDao("L'entite n'a pas d'ID");
		}
		boolean doCommit = false;
		boolean connexionCreated = connexion == null;
		PreparedStatement ps = null;
		try {
			if (connexionCreated) {
				connexion = super.getConnexion();
				connexion.setAutoCommit(false);
			}

			ps = connexion
					.prepareStatement("update "
							+ this.getTableName()
							+ " set nom=?,prenom=?,login=?,password=?,sex=?,derniereConnection=? where "
							+ this.getPkName() + "=?;");
			ps.setString(1, pUneEntite.getNom());
			ps.setString(2, pUneEntite.getPrenom());
			ps.setString(3, pUneEntite.getLogin());
			ps.setString(4, pUneEntite.getPassword());
			ps.setByte(5, pUneEntite.getSex().byteValue());
			ps.setTimestamp(6, pUneEntite.getDerniereConnection());
			ps.setInt(7, pUneEntite.getId().intValue());

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
					this.LOG.error("Impossible de fermer le statement!", e);
				}
			}
			if (connexionCreated && (connexion != null)) {
				try {
					connexion.close();
				} catch (SQLException e) {
					this.LOG.error("Impossible de fermer le connexion!", e);
				}

			}
		}

		return pUneEntite;
	}

	@Override
	public IUtilisateurEntity selectLogin(String pLogin, Connection connexion)
			throws ExceptionDao {
		List<IUtilisateurEntity> allLogin = this.selectAll("login='" + pLogin
				+ "'", null, connexion);
		if ((allLogin == null) || allLogin.isEmpty()) {
			return null;
		}
		// On retourne le premier
		return allLogin.iterator().next();
	}

}