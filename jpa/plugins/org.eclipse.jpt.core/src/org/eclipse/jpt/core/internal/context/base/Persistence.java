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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class Persistence extends JpaContextNode
	implements IPersistence
{	
	protected List<IPersistenceUnit> persistenceUnits;
	
	
	public Persistence(IPersistenceXml persistenceXml) {
		super(persistenceXml);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		persistenceUnits = new ArrayList<IPersistenceUnit>();
	}
	
	
	// **************** persistence units **************************************
	
	public ListIterator<IPersistenceUnit> persistenceUnits() {
		return new CloneListIterator<IPersistenceUnit>(persistenceUnits);
	}
	
	public void addPersistenceUnit(IPersistenceUnit persistenceUnit) {
		persistenceUnits.add(persistenceUnit);
		fireListChanged(PERSISTENCE_UNITS_LIST);
	}
	
	public void addPersistenceUnit(int index, IPersistenceUnit persistenceUnit) {
		persistenceUnits.add(index, persistenceUnit);
		fireListChanged(PERSISTENCE_UNITS_LIST);
	}
	
	public void removePersistenceUnit(IPersistenceUnit persistenceUnit) {
		persistenceUnits.remove(persistenceUnit);
		fireListChanged(PERSISTENCE_UNITS_LIST);
	}
	
	public void removePersistenceUnit(int index) {
		persistenceUnits.remove(index);
		fireListChanged(PERSISTENCE_UNITS_LIST);
	}
	
	
	// **************** updating ***********************************************
	
	public void update(XmlPersistence persistence) {
		
		
	}
}
