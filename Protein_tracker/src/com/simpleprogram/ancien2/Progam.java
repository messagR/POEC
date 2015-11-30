/**
 * test
 */
package com.simpleprogram.ancien2;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.simpleprogram.HibernateUtilities;


/**
 * @author PC
 *
 */
public class Progam {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Training Hibernate");

		User user1 = new User();
		User user2 = new User("titi");
		User user3 = new User("toto");
		user3.setProteinData(new ProteinData(50, 100));
		User user4 = new User("tata");
		User user5;
		User user6;

		Session session = HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();

		session.save(user1);
		session.save(user2);
		session.save(user3);
		session.save(user4);

		// fait une mise à jour dans la base (et donc dans l'objet)
		user2.getProteinData().setTotal(20);
		session.delete(user1);

		for (UserHistory userHistory : user3.getHistory()) {
			System.out.println("1 : " + userHistory.getEntry());
		}

		session.getTransaction().commit();

		user2.getProteinData().setGoal(user2.getProteinData().getGoal() + 50);
		user3.addHistory(new UserHistory(new Date(), "name set to titi"));

		// si on veut faire un select sur un élément qui vient d'être inséré, il
		// vaut mieux commité et refaire une transaction
		session.beginTransaction();

		session.save(user2);
		session.update(user3);

		// avec connexion à la base
		user5 = (User) session.get(User.class, Integer.valueOf(3));
		// sans connexion à la base - renvoi une exception si pas trouvé
		// user5 = (User) session.load(User.class, Integer.valueOf(3));

		String request = "FROM User WHERE NAME = :name";
		Query query = session.createQuery(request);
		query.setParameter("name", "toto");
		List<User> list = query.list();
		user6 = list.get(0);

		user3.addHistory(new UserHistory(new Date(), "Added 50 proteins"));
		for (UserHistory userHistory : user5.getHistory()) {
			System.out.println("2 : " + userHistory.getEntry());
		}

		session.getTransaction().commit();
		session.close();

	}

}
