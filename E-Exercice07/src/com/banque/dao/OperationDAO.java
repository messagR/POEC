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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.IOperationEntity;
import com.banque.entity.OperationEntity;

/**
 * Gestion des operations.
 */
@Repository("operationDAO")
public class OperationDAO extends AbstractDAO<IOperationEntity> implements
IOperationDAO {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur de l'objet.
	 */
	public OperationDAO() {
		super();
	}

	@Override
	public String getTableName() {
		return "operation";
	}

	@Override
	public String getPkName() {
		return "id";
	}

	@Override
	protected IOperationEntity fromResultSet(ResultSet rs) throws SQLException {
		IOperationEntity result = new OperationEntity();
		result.setId(Integer.valueOf(rs.getInt("id")));
		result.setLibelle(rs.getString("libelle"));
		result.setMontant(Double.valueOf(rs.getDouble("montant")));
		result.setDate(rs.getTimestamp("date"));
		result.setCompteId(Integer.valueOf(rs.getInt("compteId")));
		return result;
	}

	@Override
	public String getAllColumnNames() {
		return "id,libelle,montant,date,compteId";
	}

	@Override
	public IOperationEntity insert(IOperationEntity pUneEntite,
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
									+ " (libelle, montant, date, compteId) values (?,?,?,?);",
									Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pUneEntite.getLibelle());
			ps.setDouble(2, pUneEntite.getMontant().doubleValue());
			ps.setTimestamp(3, pUneEntite.getDate());
			ps.setInt(4, pUneEntite.getCompteId().intValue());

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
	public IOperationEntity update(IOperationEntity pUneEntite,
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

			ps = connexion.prepareStatement("update " + this.getTableName()
			+ " set libelle=?, montant=?, date=?, compteId=? where "
			+ this.getPkName() + "=?;");
			ps.setString(1, pUneEntite.getLibelle());
			ps.setDouble(2, pUneEntite.getMontant().doubleValue());
			ps.setTimestamp(3, pUneEntite.getDate());
			ps.setInt(4, pUneEntite.getCompteId().intValue());
			ps.setInt(5, pUneEntite.getId().intValue());

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
	public List<IOperationEntity> selectCriteria(Integer unCompteId,
			Date unDebut, Date uneFin, Boolean pCreditDebit,
			Connection connexion) throws ExceptionDao {
		List<IOperationEntity> result = new ArrayList<IOperationEntity>();

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
			request.append(" where compteId=?");
			List<Object> gaps = new ArrayList<Object>();
			gaps.add(unCompteId);
			if ((unDebut != null) && (uneFin == null)) {
				request.append(" and date >= ?");
				gaps.add(unDebut);
			}

			if ((uneFin != null) && (unDebut == null)) {
				request.append(" and date <= ?");
				gaps.add(uneFin);
			}

			if ((uneFin != null) && (unDebut != null)) {
				request.append(" and date between ? and ?");
				gaps.add(unDebut);
				gaps.add(uneFin);
			}

			if (pCreditDebit != null) {
				if (pCreditDebit.booleanValue()) {
					request.append(" and montant >= 0");
				} else {
					request.append(" and montant <= 0");
				}
			}

			request.append(" order by date DESC");

			request.append(';');
			if (this.LOG.isDebugEnabled()) {
				this.LOG.debug("Requete: " + request.toString());
			}
			ps = connexion.prepareStatement(request.toString());
			super.setPrepareStatement(ps, gaps);

			rs = ps.executeQuery();
			while (rs.next()) {
				IOperationEntity ce = this.fromResultSet(rs);
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
}