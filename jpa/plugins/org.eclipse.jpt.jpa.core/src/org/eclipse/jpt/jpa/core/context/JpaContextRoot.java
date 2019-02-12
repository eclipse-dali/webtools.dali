/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;

/**
 * Root of the Dali JPA context model.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.0
 */
public interface JpaContextRoot
	extends JpaContextModel
{
	// ********** persistence.xml **********

	/**
	 * String constant associated with changes to the persistence XML property
	 * @see #addPropertyChangeListener(String, org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener)
	 */
	String PERSISTENCE_XML_PROPERTY = "persistenceXml"; //$NON-NLS-1$

	/** 
	 * Return the content represented by the <code>persistence.xml</code>
	 * file associated with the context model root's JPA project.
	 * This may be <code>null</code>.
	 */
	PersistenceXml getPersistenceXml();
}
