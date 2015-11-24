package com.exo.jms.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
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
 * Servlet implementation class JmsProducer
 */
@WebServlet("/JmsProducer")
public class JmsProducer extends AbstractJmsServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LogManager.getLogger();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JmsProducer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmation_e.jsp");

		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			// Récupération et ouverture de la connexion auprès de la factory
			// JMS
			connection = this.getJmsFactory().createConnection();
			connection.start();
			// Récupération d'une session auprès de la connexion :
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// Définition de la destination des messages, ici une queue :
			Destination destination = session.createQueue("jmsj2ee.queue");
			// Définition du producteur associé à cette destination :
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			// Récupérez le paramètre de formulaire contenant le texte du
			// message et envoyez le :
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String text = request.getParameter("message") + " [" + sdf.format(new Date()) + "]";
			TextMessage msg = session.createTextMessage(text);
			producer.send(msg);
		} catch (JMSException e) {
			JmsProducer.LOG.error(e.getMessage());
			request.setAttribute("erreur", e.getMessage());
			dispatcher = request.getRequestDispatcher("/erreur_e.jsp");
			dispatcher.forward(request, response);
			return;
		} finally {
			if (producer != null) {
				try {
					producer.close();
				} catch (JMSException e) {
					JmsProducer.LOG.error(e.getMessage());
				}
			}
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					JmsProducer.LOG.error(e.getMessage());
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					JmsProducer.LOG.error(e.getMessage());
				}
			}
		}
		request.setAttribute("message", request.getParameter("message"));
		dispatcher.forward(request, response);
		return;
	}

}
