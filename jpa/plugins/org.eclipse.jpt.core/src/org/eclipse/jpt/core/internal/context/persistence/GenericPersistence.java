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
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericPersistence extends AbstractJpaContextNode
	implements Persistence
{	
	protected XmlPersistence xmlPersistence;
	
	protected final List<PersistenceUnit> persistenceUnits;
	
	
	public GenericPersistence(PersistenceXml parent) {
		super(parent);
		this.persistenceUnits = new ArrayList<PersistenceUnit>();
	}
	
	public String getId() {
		return PersistenceStructureNodes.PERSISTENCE_ID;
	}

	
	// **************** persistence units **************************************
	
	public ListIterator<PersistenceUnit> persistenceUnits() {
		return new CloneListIterator<PersistenceUnit>(persistenceUnits);
	}
	
	public int persistenceUnitsSize() {
		return persistenceUnits.size();
	}
	
	public PersistenceUnit addPersistenceUnit() {
		return addPersistenceUnit(persistenceUnits.size());
	}
	
	public PersistenceUnit addPersistenceUnit(int index) {
		XmlPersistenceUnit xmlPersistenceUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = createPersistenceUnit(xmlPersistenceUnit);
		persistenceUnits.add(index, persistenceUnit);
		xmlPersistence.getPersistenceUnits().add(xmlPersistenceUnit);
		fireItemAdded(PERSISTENCE_UNITS_LIST, index, persistenceUnit);
		return persistenceUnit;
	}
	
	public void removePersistenceUnit(PersistenceUnit persistenceUnit) {
		removePersistenceUnit(persistenceUnits.indexOf(persistenceUnit));
	}
	
	public void removePersistenceUnit(int index) {
		PersistenceUnit persistenceUnit = persistenceUnits.remove(index);
		xmlPersistence.getPersistenceUnits().remove(index);
		fireItemRemoved(PERSISTENCE_UNITS_LIST, index, persistenceUnit);
	}
	
	protected void addPersistenceUnit_(PersistenceUnit persistenceUnit) {
		addPersistenceUnit_(persistenceUnits.size(), persistenceUnit);
	}
	
	protected void addPersistenceUnit_(int index, PersistenceUnit persistenceUnit) {
		addItemToList(index, persistenceUnit, persistenceUnits, PERSISTENCE_UNITS_LIST);
	}
	
	protected void removePersistenceUnit_(PersistenceUnit persistenceUnit) {
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
		Iterator<PersistenceUnit> stream = persistenceUnits();
		Iterator<XmlPersistenceUnit> stream2 = persistence.getPersistenceUnits().iterator();
		
		while (stream.hasNext()) {
			PersistenceUnit persistenceUnit = stream.next();
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
	
	protected PersistenceUnit createPersistenceUnit(XmlPersistenceUnit xmlPersistenceUnit) {
		PersistenceUnit persistenceUnit = jpaFactory().buildPersistenceUnit(this);
		persistenceUnit.initialize(xmlPersistenceUnit);
		return persistenceUnit;
	}
	
	
	// *************************************************************************
	
	@Override
	public PersistenceUnit persistenceUnit() {
		throw new UnsupportedOperationException("No PersistenceUnit in this context");
	}
	
	public JpaStructureNode structureNode(int textOffset) {
		for (PersistenceUnit persistenceUnit : CollectionTools.iterable(persistenceUnits())) {
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
	
	public TextRange selectionTextRange() {
		return xmlPersistence.selectionTextRange();
	}
	
	public TextRange validationTextRange() {
		return xmlPersistence.validationTextRange();
	}


	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		//persistence root validation
		addNoPersistenceUnitMessage(messages);
		addMultiplePersistenceUnitMessage(messages);
		
		
		//persistence unit validation
		for (PersistenceUnit pu : persistenceUnits){
			pu.addToMessages(messages, astRoot);
		}
	}
	
	protected void addNoPersistenceUnitMessage(List<IMessage> messages) {
		if (persistenceUnits.size() == 0) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT,
						this, this.validationTextRange())
				);
		}
	}
	
	protected void addMultiplePersistenceUnitMessage(List<IMessage> messages) {
		if (persistenceUnits.size() > 1) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_MULTIPLE_PERSISTENCE_UNITS,
						this, this.validationTextRange())
				);
		}
	}
}
