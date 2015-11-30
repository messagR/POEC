/**
 * test
 */
package com.simpleprogram;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


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
		user3.addHistory(new UserHistory(new Date(), "protein initialise to	toto"));
		User user4 = new User("tata", new ProteinData(10, 20));
		user4.addHistory(new UserHistory(new Date(), "create user tata"));
		user4.addHistory(new UserHistory(new Date(), "protein initialise to tata"));
		user4.addHistory(new UserHistory(new Date(), "create goalAlert to tata"));
		User user5;
		User user6;

		Session session =
				HibernateUtilities.getSessionFactory().openSession();
		session.beginTransaction();

		session.save(user1);
		session.save(user2);
		session.save(user3);
		session.save(user4);

		// fait une mise à jour dans la base (et donc dans l'objet)
		user2.getProteinData().setTotal(20);
		user2.addHistory(new UserHistory(new Date(), "protein change to titi"));
		user2.addGoalAlert(new GoalAlert("allez grosse fégnasse, bouge toi le cul !"));
		user2.addGoalAlert(new GoalAlert("GOAL !!!"));
		user2.addHistory(new UserHistory(new Date(), "create goalAlert to titi"));
		user4.getProteinData().setTotal(30);
		user4.addHistory(new UserHistory(new Date(), "protein change to tata"));

		for (UserHistory userHistory : user2.getHistory()) {
			System.out.println("1 : " + userHistory.getEntry());
		}
		session.delete(user1);
		user1.addHistory(new UserHistory(new Date(), "delete user ?"));

		session.getTransaction().commit();

		user2.getProteinData().setGoal(user2.getProteinData().getGoal() +
				50);
		user2.addHistory(new UserHistory(new Date(), "change goal to titi"));
		user3.getProteinData().setGoal(user3.getProteinData().getGoal() +
				50);
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
		query.setString("name", "tata");
		List<User> list = query.list();
		user6 = list.get(0);
		user6.addHistory(new UserHistory(new Date(), "load user tata"));

		request = "select user.name, user.id FROM User user WHERE user.proteinData.total > 20";
		query = session.createQuery(request);
		List<Object[]> list2 = query.list();
		for (Object[] object : list2) {
			System.out.println("user " + object[1] + " : " + object[0]);
		}

		List<Object[]> list3 = session.createCriteria(User.class)
				.setProjection(
						Projections.projectionList().add(Projections.property("id")).add(Projections.property("name")))
				.createCriteria("proteinData")
				.add(Restrictions.gt("total", 20))
				.list();
		for (Object[] object : list3) {
			System.out.println("user " + object[0] + " : " + object[1]);
		}

		query = session.getNamedQuery("maRequete");
		list2 = query.list();
		for (Object[] object : list2) {
			System.out.println("user " + object[1] + " : " + object[0]);
		}

		for (int i = 0; i < 10; i++) {
			User user = new User();
			user.setName("toto" + i);
			session.save(user);
			if ((i % 2) == 0) {
				session.flush();
				session.clear();
			}
			System.out.println(user.getName());
		}

		session.getTransaction().commit();
		session.close();

		// exo cache de niveau 2
		Session session2 = HibernateUtilities.getSessionFactory().openSession();
		session2.beginTransaction();
		User userCache = new User("tutu", new ProteinData(25, 43));
		session2.save(userCache);
		User userCache2 = (User) session2.get(User.class, Integer.valueOf(15));
		session2.getTransaction().commit();
		session2.close();
		// à la fermeture de la session le cache de niveau un se vide mais pas
		// le cache de niveau 2

		Session session3 = HibernateUtilities.getSessionFactory().openSession();
		session3.beginTransaction();
		// récupère l'utilisateur dnas le cache et non dans la base de données
		// grâce au cache de niveau 2
		User userCache3 = (User) session3.get(User.class, Integer.valueOf(15));
		session3.getTransaction().commit();
		session3.close();

	}

}
