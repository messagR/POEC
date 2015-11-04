/**
 * test
 */
package fr.banque.entity;

import java.util.Date;

/**
 * @author PC
 *
 */
public interface IOperation extends IEntite {

	String getLibelle();

	double getMontant();

	Date getDate();

}