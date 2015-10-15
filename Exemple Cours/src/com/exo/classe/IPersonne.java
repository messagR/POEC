package com.exo.classe;

import java.io.IOException;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;

public interface IPersonne {

	public abstract String getNom();

	public abstract void setNom(String nom);

	public abstract String getPrenom();

	public abstract void setPrenom(String prenom);

	public abstract int getAge();

	public abstract void setAge(int age) throws MonException, IOException, SQLException, BadPaddingException;

	public abstract int inverser(int x, int y);

	public abstract void inverser(IPersonne p) throws MonException, BadPaddingException, IOException, SQLException;

	public abstract void afficher();

}