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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class Persistence extends JpaContextNode
	implements IPersistence
{	
	protected XmlPersistence xmlPersistence;
	
	protected final List<IPersistenceUnit> persistenceUnits;
	
	
	public Persistence(IPersistenceXml parent) {
		super(parent);
		this.persistenceUnits = new ArrayList<IPersistenceUnit>();
	}
	
	
	// **************** persistence units **************************************
	
	public ListIterator<IPersistenceUnit> persistenceUnits() {
		return new CloneListIterator<IPersistenceUnit>(persistenceUnits);
	}
	
	public IPersistenceUnit addPersistenceUnit() {
		return addPersistenceUnit(persistenceUnits.size());
	}
	
	public IPersistenceUnit addPersistenceUnit(int index) {
		XmlPersistenceUnit xmlPersistenceUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = createPersistenceUnit(xmlPersistenceUnit);
		persistenceUnits.add(index, persistenceUnit);
		xmlPersistence.getPersistenceUnits().add(xmlPersistenceUnit);
		fireListChanged(PERSISTENCE_UNITS_LIST);
		return persistenceUnit;
	}
	
	public void removePersistenceUnit(IPersistenceUnit persistenceUnit) {
		removePersistenceUnit(persistenceUnits.indexOf(persistenceUnit));
	}
	
	public void removePersistenceUnit(int index) {
		persistenceUnits.remove(index);
		xmlPersistence.getPersistenceUnits().remove(index);
		fireListChanged(PERSISTENCE_UNITS_LIST);
	}
	
	protected void addPersistenceUnit_(IPersistenceUnit persistenceUnit) {
		addPersistenceUnit_(persistenceUnits.size(), persistenceUnit);
	}
	
	protected void addPersistenceUnit_(int index, IPersistenceUnit persistenceUnit) {
		persistenceUnits.add(index, persistenceUnit);
		fireListChanged(PERSISTENCE_UNITS_LIST);
	}
	
	protected void removePersistenceUnit_(IPersistenceUnit persistenceUnit) {
		removePersistenceUnit_(persistenceUnits.indexOf(persistenceUnit));
	}
	
	protected void removePersistenceUnit_(int index) {
		persistenceUnits.remove(index);
		fireListChanged(PERSISTENCE_UNITS_LIST);
	}
	
	
	// **************** updating ***********************************************
	
	public void initialize(XmlPersistence xmlPersistence) {
		this.xmlPersistence = xmlPersistence;
		initializePersistenceUnits(xmlPersistence);
	}
	
	protected void initializePersistenceUnits(XmlPersistence persistence) {
		for (XmlPersistenceUnit xmlPersistenceUnit : persistence.getPersistenceUnits()) {
			this.persistenceUnits.add(createPersistenceUnit(xmlPersistenceUnit));
		}
	}

	public void update(XmlPersistence persistence) {
		this.xmlPersistence = persistence;
		Iterator<IPersistenceUnit> stream = persistenceUnits();
		Iterator<XmlPersistenceUnit> stream2 = persistence.getPersistenceUnits().iterator();
		
		while (stream.hasNext()) {
			IPersistenceUnit persistenceUnit = stream.next();
			if (stream2.hasNext()) {
				persistenceUnit.update(stream2.next());
			}
			else {
				removePersistenceUnit_(persistenceUnit);
			}
		}
		
		while (stream2.hasNext()) {
			addPersistenceUnit_(createPersistenceUnit(stream2.next()));
		}
	}
	
	protected IPersistenceUnit createPersistenceUnit(XmlPersistenceUnit xmlPersistenceUnit) {
		IPersistenceUnit persistenceUnit = jpaFactory().createPersistenceUnit(this);
		persistenceUnit.initialize(xmlPersistenceUnit);
		return persistenceUnit;
	}
	
	
	// *************************************************************************
	
	@Override
	public IPersistenceUnit persistenceUnit() {
		throw new UnsupportedOperationException("No PersistenceUnit in this context");
	}
	
	public ITextRange validationTextRange() {
		return this.xmlPersistence.validationTextRange();
	}
}
