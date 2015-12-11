/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * Classe parente de tous les services.
 */
@Service("abstractService")
public abstract class AbstractService {

	protected Log LOG = LogFactory.getLog(this.getClass());

	/**
	 * Constructeur de l'objet.
	 */
	public AbstractService() {
		super();
	}
}
