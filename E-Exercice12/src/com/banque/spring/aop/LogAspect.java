/**
 * test
 */
package com.banque.spring.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Log tous les appels vers les services.
 */
@Aspect
public class LogAspect {
	private final static Log LOG = LogFactory.getLog(LogAspect.class);

	/**
	 * Constructeur de l'objet.
	 */
	public LogAspect() {
		super();
	}

	/**
	 * Executer après l'appel à un service.
	 *
	 * @param jp
	 *            le join point
	 */
	@Around("execution(* com.banque.service.AbstractService+.*(..))")
	public Object cache(ProceedingJoinPoint pj) throws Throwable {
		if (LogAspect.LOG.isInfoEnabled()) {
			LogAspect.LOG.info("Passage avant " + pj.getTarget() + " " + pj.getSignature());
		}
		// Exécution de la vraie méthode et récupération du résultat
		Object value = pj.proceed();
		if (LogAspect.LOG.isInfoEnabled()) {
			LogAspect.LOG.info("Passage après " + pj.getTarget() + " " + pj.getSignature());
		}
		// Il faut retourner le résultat
		return value;
	}
}
