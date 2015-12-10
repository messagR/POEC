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

import org.springframework.stereotype.Repository;

import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.CompteEntity;
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
	protected ICompteEntity fromResultSet(ResultSet rs) throws SQLException {
		ICompteEntity result = new CompteEntity();
		result.setId(Integer.valueOf(rs.getInt("id")));
		result.setLibelle(rs.getString("libelle"));
		result.setSolde(Double.valueOf(rs.getDouble("solde")));
		result.setDecouvert(Double.valueOf(rs.getDouble("decouvert")));
		result.setTaux(Double.valueOf(rs.getDouble("taux")));
		result.setUtilisateurId(Integer.valueOf(rs.getInt("utilisateurId")));
		return result;
	}

	@Override
	public ICompteEntity insert(ICompteEntity pUneEntite, Connection connexion)
			throws ExceptionDao {
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
									+ " (libelle, solde, decouvert, taux, utilisateurId) values (?,?,?,?,?);",
									Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pUneEntite.getLibelle());
			ps.setDouble(2, pUneEntite.getSolde().doubleValue());
			ps.setDouble(3, pUneEntite.getDecouvert().doubleValue());
			ps.setDouble(4, pUneEntite.getTaux().doubleValue());
			ps.setInt(5, pUneEntite.getUtilisateurId().intValue());

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
	public ICompteEntity update(ICompteEntity pUneEntite, Connection connexion)
			throws ExceptionDao {
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
							+ " set libelle=?, solde=?, decouvert=?, taux=?, utilisateurId=? where "
							+ this.getPkName() + "=?;");
			ps.setString(1, pUneEntite.getLibelle());
			ps.setDouble(2, pUneEntite.getSolde().doubleValue());
			ps.setDouble(3, pUneEntite.getDecouvert().doubleValue());
			ps.setDouble(4, pUneEntite.getTaux().doubleValue());
			ps.setInt(5, pUneEntite.getUtilisateurId().intValue());
			ps.setInt(6, pUneEntite.getId().intValue());

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

}