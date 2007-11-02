/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;

public interface IPersistence extends IJpaContextNode
{
	void initialize(XmlPersistence xmlPersistence);

	
	// **************** persistence units **************************************
	
	ListIterator<IPersistenceUnit> persistenceUnits();
	
	void addPersistenceUnit(IPersistenceUnit persistenceUnit);
	
	void addPersistenceUnit(int index, IPersistenceUnit persistenceUnit);
	
	void removePersistenceUnit(IPersistenceUnit persistenceUnit);
	
	void removePersistenceUnit(int index);
	
	public final static String PERSISTENCE_UNITS_LIST = "persistenceUnits";
	
	
	// **************** updating ***********************************************
	
	void update(XmlPersistence persistence);
}
