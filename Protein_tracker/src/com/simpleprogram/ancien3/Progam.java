/**
 * test
 */
package com.simpleprogram.ancien3;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


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
		user1.addHistory(new UserHistory(new Date(), "create user ?"));
		User user2 = new User("titi");
		user2.addHistory(new UserHistory(new Date(), "create user titi"));
		User user3 = new User("toto", new ProteinData(50, 110));
		user3.addHistory(new UserHistory(new Date(), "create user toto"));
		user3.addHistory(new UserHistory(new Date(), "protein initialise to toto"));
		User user4 = new User("tata", new ProteinData(10, 20), new GoalAlert("Atteint"));
		user4.addHistory(new UserHistory(new Date(), "create user tata"));
		user4.addHistory(new UserHistory(new Date(), "protein initialise to tata"));
		user4.addHistory(new UserHistory(new Date(), "create goalAlert to tata"));
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
		user2.addHistory(new UserHistory(new Date(), "protein change to titi"));
		user2.setGoalAlert(new GoalAlert("GOAL !!!"));
		user2.addHistory(new UserHistory(new Date(), "create goalAlert to titi"));
		user4.getProteinData().setTotal(30);
		user4.addHistory(new UserHistory(new Date(), "protein change to tata"));

		for (UserHistory userHistory : user2.getHistory()) {
			System.out.println("1 : " + userHistory.getEntry());
		}
		session.delete(user1);
		user1.addHistory(new UserHistory(new Date(), "delete user ?"));

		session.getTransaction().commit();

		user2.getProteinData().setGoal(user2.getProteinData().getGoal() + 50);
		user2.addHistory(new UserHistory(new Date(), "change goal to titi"));
		user3.getProteinData().setGoal(user3.getProteinData().getGoal() + 50);
		user3.addHistory(new UserHistory(new Date(), "change goal to toto"));

		// si on veut faire un select sur un élément qui vient d'être inséré, il
		// vaut mieux commité et refaire une transaction
		session.beginTransaction();

		session.save(user2);
		session.update(user3);

		// avec connexion à la base
		user5 = (User) session.get(User.class, Integer.valueOf(3));
		// sans connexion à la base - renvoi une exception si pas trouvé
		// user5 = (User) session.load(User.class, Integer.valueOf(3));
		user5.addHistory(new UserHistory(new Date(), "load user toto"));

		String request = "FROM User WHERE NAME = :name";
		Query query = session.createQuery(request);
		query.setParameter("name", "tata");
		List<User> list = query.list();
		user6 = list.get(0);
		user6.addHistory(new UserHistory(new Date(), "load user tata"));

		session.getTransaction().commit();
		session.close();

	}

}
