package com.exo.jms.servlet;

import javax.jms.ConnectionFactory;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet JMS abstraite servant au consommatteur et producteur.
 */
public class AbstractJmsServlet extends HttpServlet {
	private final static Logger LOG = LogManager.getLogger();

	public final static String JMS_URI = "tcp://localhost:61616?wireFormat.maxInactivityDuration=0";
	private ConnectionFactory jmsFactory;
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur.
	 */
	protected AbstractJmsServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		AbstractJmsServlet.LOG.trace("Init - start");
		super.init(config);
		this.jmsFactory = new ActiveMQConnectionFactory(AbstractJmsServlet.JMS_URI);
		AbstractJmsServlet.LOG.trace("Init - ok");

	}

	/**
	 * Donne acces a la factory JMS.
	 *
	 * @return la factory JMS
	 */
	protected final ConnectionFactory getJmsFactory() {
		return this.jmsFactory;
	}
}
