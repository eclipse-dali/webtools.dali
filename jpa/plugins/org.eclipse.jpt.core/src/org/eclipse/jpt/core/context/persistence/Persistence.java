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
 * Context model corresponding to the XML resource model {@link XmlPersistence},
 * which corresponds to the <code>persistence</code> element in the
 * <code>persistence.xml</code> file.
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
	 * Return the persistence element's persistence units.
	 */
	ListIterator<PersistenceUnit> persistenceUnits();
	
	/**
	 * Return the number of persistence units.
	 */
	int persistenceUnitsSize();
	
	/**
	 * Add a persistence unit and return it.
	 */
	PersistenceUnit addPersistenceUnit();
	
	/**
	 * Add a persistence unit at the specified index and return it.
	 */
	PersistenceUnit addPersistenceUnit(int index);
	
	/**
	 * Remove the specified persistence unit from the persistence node.
	 */
	void removePersistenceUnit(PersistenceUnit persistenceUnit);
	
	/**
	 * Remove the persistence unit at the specified index from the persistence node.
	 */
	void removePersistenceUnit(int index);
	
	
	// **************** text range *********************************************
	
	/**
	 * Return whether the text representation of this persistence contains
	 * the specified text offset.
	 */
	boolean containsOffset(int textOffset);
	
}
