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
package org.eclipse.jpt.core.internal.context.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaStructureNode;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class Persistence extends JpaContextNode
	implements IPersistence
{	
	protected XmlPersistence xmlPersistence;
	
	protected final List<IPersistenceUnit> persistenceUnits;
	
	
	public Persistence(IPersistenceXml parent) {
		super(parent);
		this.persistenceUnits = new ArrayList<IPersistenceUnit>();
	}
	
	public String getId() {
		return IPersistenceStructureNodes.PERSISTENCE_ID;
	}

	
	// **************** persistence units **************************************
	
	public ListIterator<IPersistenceUnit> persistenceUnits() {
		return new CloneListIterator<IPersistenceUnit>(persistenceUnits);
	}
	
	public int persistenceUnitsSize() {
		return persistenceUnits.size();
	}
	
	public IPersistenceUnit addPersistenceUnit() {
		return addPersistenceUnit(persistenceUnits.size());
	}
	
	public IPersistenceUnit addPersistenceUnit(int index) {
		XmlPersistenceUnit xmlPersistenceUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = createPersistenceUnit(xmlPersistenceUnit);
		persistenceUnits.add(index, persistenceUnit);
		xmlPersistence.getPersistenceUnits().add(xmlPersistenceUnit);
		fireItemAdded(PERSISTENCE_UNITS_LIST, index, persistenceUnit);
		return persistenceUnit;
	}
	
	public void removePersistenceUnit(IPersistenceUnit persistenceUnit) {
		removePersistenceUnit(persistenceUnits.indexOf(persistenceUnit));
	}
	
	public void removePersistenceUnit(int index) {
		IPersistenceUnit persistenceUnit = persistenceUnits.remove(index);
		xmlPersistence.getPersistenceUnits().remove(index);
		fireItemRemoved(PERSISTENCE_UNITS_LIST, index, persistenceUnit);
	}
	
	protected void addPersistenceUnit_(IPersistenceUnit persistenceUnit) {
		addPersistenceUnit_(persistenceUnits.size(), persistenceUnit);
	}
	
	protected void addPersistenceUnit_(int index, IPersistenceUnit persistenceUnit) {
		addItemToList(index, persistenceUnit, persistenceUnits, PERSISTENCE_UNITS_LIST);
	}
	
	protected void removePersistenceUnit_(IPersistenceUnit persistenceUnit) {
		removePersistenceUnit_(persistenceUnits.indexOf(persistenceUnit));
	}
	
	protected void removePersistenceUnit_(int index) {
		removeItemFromList(index, persistenceUnits, PERSISTENCE_UNITS_LIST);
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
	
	public IJpaStructureNode structureNode(int textOffset) {
		for (IPersistenceUnit persistenceUnit : CollectionTools.iterable(persistenceUnits())) {
			if (persistenceUnit.containsOffset(textOffset)) {
				return persistenceUnit.structureNode(textOffset);
			}
		}
		return this;
	}
	
	public boolean containsOffset(int textOffset) {
		if (xmlPersistence == null) {
			return false;
		}
		return xmlPersistence.containsOffset(textOffset);
	}
	
	public ITextRange selectionTextRange() {
		return xmlPersistence.selectionTextRange();
	}
	
	public ITextRange validationTextRange() {
		return xmlPersistence.validationTextRange();
	}


	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		//persistence root validation
		addNoPersistenceUnitMessage(messages);
		addMultiplePersistenceUnitMessage(messages);
		
		
		//persistence unit validation
		for (IPersistenceUnit pu : persistenceUnits){
			pu.addToMessages(messages, astRoot);
		}
	}
	
	protected void addNoPersistenceUnitMessage(List<IMessage> messages) {
		if (persistenceUnits.size() == 0) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT,
						this, this.validationTextRange())
				);
		}
	}
	
	protected void addMultiplePersistenceUnitMessage(List<IMessage> messages) {
		if (persistenceUnits.size() > 1) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENCE_MULTIPLE_PERSISTENCE_UNITS,
						this, this.validationTextRange())
				);
		}
	}
}
