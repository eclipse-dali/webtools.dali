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

import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericPersistence extends AbstractPersistenceJpaContextNode
	implements Persistence
{	
	protected XmlPersistence xmlPersistence;
	
	// the implementation here is a single persistence unit, although the API
	// is for a list.  we will want to support multiple persistence units soon.
	protected PersistenceUnit persistenceUnit;
	
	
	public GenericPersistence(PersistenceXml parent) {
		super(parent);
	}
	
	public String getId() {
		return PersistenceStructureNodes.PERSISTENCE_ID;
	}

	
	// **************** persistence units **************************************
	
	public ListIterator<PersistenceUnit> persistenceUnits() {
		if (persistenceUnit == null) {
			return EmptyListIterator.instance();
		}
		else {
			return new SingleElementListIterator<PersistenceUnit>(persistenceUnit);
		}
	}
	
	public int persistenceUnitsSize() {
		return (persistenceUnit == null) ? 0 : 1;
	}
	
	public PersistenceUnit addPersistenceUnit() {
		return addPersistenceUnit(persistenceUnitsSize());
	}
	
	public PersistenceUnit addPersistenceUnit(int index) {
		if (index > 0 || persistenceUnit != null) {
			throw new IllegalStateException("This implementation does not support multiple persistence units.");
		}
		XmlPersistenceUnit xmlPersistenceUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		persistenceUnit = createPersistenceUnit(xmlPersistenceUnit);
		xmlPersistence.getPersistenceUnits().add(xmlPersistenceUnit);
		fireItemAdded(PERSISTENCE_UNITS_LIST, index, persistenceUnit);
		return persistenceUnit;
	}
	
	public void removePersistenceUnit(PersistenceUnit persistenceUnit) {
		removePersistenceUnit(0);
	}
	
	public void removePersistenceUnit(int index) {
		if (index > 0 ) {
			throw new IllegalArgumentException(new Integer(index).toString());
		}
		PersistenceUnit oldPersistenceUnit = persistenceUnit;
		persistenceUnit = null;
		xmlPersistence.getPersistenceUnits().remove(index);
		fireItemRemoved(PERSISTENCE_UNITS_LIST, index, oldPersistenceUnit);
	}
	
	protected void addPersistenceUnit_(PersistenceUnit newPersistenceUnit) {
		persistenceUnit = newPersistenceUnit;
		fireItemAdded(PERSISTENCE_UNITS_LIST, 0, persistenceUnit);
	}
	
	protected void removePersistenceUnit_(PersistenceUnit oldPersistenceUnit) {
		persistenceUnit = null;
		fireItemRemoved(PERSISTENCE_UNITS_LIST, 0, oldPersistenceUnit);
	}
	
	
	// **************** updating ***********************************************
	
	public void initialize(XmlPersistence xmlPersistence) {
		this.xmlPersistence = xmlPersistence;
		initializePersistenceUnits(xmlPersistence);
	}
	
	protected void initializePersistenceUnits(XmlPersistence persistence) {
		// only adding one here, until we support multiple persistence units
		if (xmlPersistence.getPersistenceUnits().size() > 0) {
			persistenceUnit = createPersistenceUnit(persistence.getPersistenceUnits().get(0));
		}
	}
	
	public void update(XmlPersistence persistence) {
		this.xmlPersistence = persistence;
		XmlPersistenceUnit xmlPersistenceUnit = null;
		if (persistence.getPersistenceUnits().size() > 0) {
			xmlPersistenceUnit = persistence.getPersistenceUnits().get(0);
		}
				
		if (persistenceUnit != null) {
			if (xmlPersistenceUnit != null) {
				persistenceUnit.update(xmlPersistenceUnit);
			}
			else {
				removePersistenceUnit_(persistenceUnit);
			}
		}
		else {
			if (xmlPersistenceUnit != null) {
				addPersistenceUnit_(createPersistenceUnit(xmlPersistenceUnit));
			}
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
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		//persistence root validation
		addNoPersistenceUnitMessage(messages);
		
		// note to neil (or whomever): extraneous persistence units can be
		// accessed through the XmlPersistence resource object
		addMultiplePersistenceUnitMessage(messages);
		
		
		//persistence unit validation
		if (persistenceUnit != null) {
			persistenceUnit.addToMessages(messages);
		}
	}
	
	protected void addNoPersistenceUnitMessage(List<IMessage> messages) {
		if (persistenceUnit == null) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT,
						this, 
						this.validationTextRange())
				);
		}
	}
	
	protected void addMultiplePersistenceUnitMessage(List<IMessage> messages) {
		if (xmlPersistence.getPersistenceUnits().size() > 1) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.PERSISTENCE_MULTIPLE_PERSISTENCE_UNITS,
						this, 
						this.validationTextRange())
				);
		}
	}
}
