/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package fr.web;

/**
 * Interface avec les informations pour la base de données. <br/>
 */
public interface IServlet {

	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost/banque";
	public static final String DB_LOGIN = "root";
	public static final String DB_PWD = "root";

}
