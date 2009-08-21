/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

/**
 * JPA <code>persistence.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PersistenceXml
	extends XmlContextNode, JpaStructureNode
{
	// **************** persistence *******************************************
	
	/**
	 * String constant associated with changes to the persistence property
	 */
	public final static String PERSISTENCE_PROPERTY = "persistence"; //$NON-NLS-1$

	/**
	 * Return the content represented by the root of the persistence.xml file.
	 * This may be null.
	 */
	Persistence getPersistence();

	/**
	 * Add a persistence node to the persistence.xml file and return the object 
	 * representing it.
	 * Throws {@link IllegalStateException} if a persistence node already exists.
	 */
	Persistence addPersistence();
	
	/**
	 * Remove the persistence node from the persistence.xml file.
	 * Throws {@link IllegalStateException} if a persistence node does not exist.
	 */
	void removePersistence();
	
	/**
	 * Return the resource model object
	 */
	JpaXmlResource getXmlResource();
	
	// **************** updating **********************************************
	
	/**
	 * Update the PersistenceXml context model object to match the JpaXmlResource 
	 * resource model object.
	 * @see org.eclipse.jpt.core.JpaProject#update()
	 */
	void update(JpaXmlResource resource);
}
