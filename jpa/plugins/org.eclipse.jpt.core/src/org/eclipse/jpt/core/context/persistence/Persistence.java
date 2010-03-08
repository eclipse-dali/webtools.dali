/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import java.util.ListIterator;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;

/**
 * The <code>persistence</code> element in the JPA <code>persistence.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.0
 */
public interface Persistence
	extends XmlContextNode, JpaStructureNode
{
	/**
	 * Return the resource model object associated with this context model object
	 */
	XmlPersistence getXmlPersistence();
	
	/**
	 * Covariant override.
	 */
	PersistenceXml getParent();
	
	// **************** persistence units **************************************
	
	/**
	 * String constant associated with changes to the persistence units list
	 */
	String PERSISTENCE_UNITS_LIST = "persistenceUnits"; //$NON-NLS-1$
	
	/**
	 * Return an iterator on the list of persistence units.
	 * This will not be null.
	 */
	ListIterator<PersistenceUnit> persistenceUnits();
	
	/**
	 * Return the size of the persistence unit list.
	 * @return
	 */
	int persistenceUnitsSize();
	
	/**
	 * Add a persistence unit to the persistence node and return the object 
	 * representing it.
	 */
	PersistenceUnit addPersistenceUnit();
	
	/**
	 * Add a persistence unit to the persistence node at the specified index and 
	 * return the object representing it.
	 */
	PersistenceUnit addPersistenceUnit(int index);
	
	/**
	 * Remove the persistence unit from the persistence node.
	 */
	void removePersistenceUnit(PersistenceUnit persistenceUnit);
	
	/**
	 * Remove the persistence unit at the specified index from the persistence node.
	 */
	void removePersistenceUnit(int index);
	
	
	// **************** updating ***********************************************
		
	/**
	 * Update the Persistence context model object to match the XmlPersistence 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(XmlPersistence persistence);
	
	
	// **************** text range *********************************************
	
	/**
	 * Return whether the text representation of this persistence contains
	 * the given text offset
	 */
	boolean containsOffset(int textOffset);
	
}
