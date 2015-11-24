package com.exo.jms.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class JmsConsumer
 */
@WebServlet("/JmsConsumer")
public class JmsConsumer extends AbstractJmsServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LogManager.getLogger();

	/**
	 * @see AbstractJmsServlet#AbstractJmsServlet()
	 */
	public JmsConsumer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/recevoir.jsp");

		javax.jms.Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;
		List<String> tousLesMessages = new ArrayList<String>();
		try {
			// Récupération et ouverture de la connexion auprès de la factory
			// JMS
			connection = this.getJmsFactory().createConnection();
			connection.start();
			// Récupération d'une session auprès de la connexion :
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// Définition de la destination des messages, ici une queue :
			Destination destination = session.createQueue("jmsj2ee.queue");
			// Définition du consommateur associé à cette destination :
			consumer = session.createConsumer(destination);
			// Récupérez de tous les messages et stoquage du texte du message
			// dans une liste :
			Message message = null;
			do {
				message = consumer.receive(500);
				if (message instanceof TextMessage) {
					TextMessage tm = (TextMessage) message;
					tousLesMessages.add(tm.getText());
				}
			} while (message != null);
		} catch (JMSException e) {
			JmsConsumer.LOG.error(e.getMessage());
			request.setAttribute("erreur", e.getMessage());
			dispatcher = request.getRequestDispatcher("/erreur_c.jsp");
			dispatcher.forward(request, response);
			return;
		} finally {
			if (consumer != null) {
				try {
					consumer.close();
				} catch (JMSException e) {
					JmsConsumer.LOG.error(e.getMessage());
				}
			}
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					JmsConsumer.LOG.error(e.getMessage());
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					JmsConsumer.LOG.error(e.getMessage());
				}
			}
		}
		request.setAttribute("tousLesMessages", tousLesMessages);
		dispatcher.forward(request, response);
		return;
	}

}
