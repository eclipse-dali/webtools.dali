/*******************************************************************************
 *  Copyright (c) 2007, 2009 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.persistence;

import java.util.List;
import java.util.ListIterator;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericPersistence extends AbstractXmlContextNode
	implements Persistence
{	
	protected XmlPersistence xmlPersistence;
	
	// the implementation here is a single persistence unit, although the API
	// is for a list.  we will want to support multiple persistence units soon.
	protected PersistenceUnit persistenceUnit;

	public GenericPersistence(PersistenceXml parent, XmlPersistence xmlPersistence) {
		super(parent);
		this.xmlPersistence = xmlPersistence;
		this.initializePersistenceUnits();
	}
	
	public String getId() {
		return PersistenceStructureNodes.PERSISTENCE_ID;
	}
	
	public IContentType getContentType() {
		return getParent().getContentType();
	}
	
	public XmlPersistence getXmlPersistence() {
		return this.xmlPersistence;
	}
	
	@Override
	public PersistenceXml getParent() {
		return (PersistenceXml) super.getParent();
	}
	
	// **************** persistence units **************************************
	
	public ListIterator<PersistenceUnit> persistenceUnits() {
		return (this.persistenceUnit == null) ? EmptyListIterator.<PersistenceUnit>instance() : this.persistenceUnits_();
	}
	
	protected ListIterator<PersistenceUnit> persistenceUnits_() {
		return new SingleElementListIterator<PersistenceUnit>(this.persistenceUnit);
	}
	
	public int persistenceUnitsSize() {
		return (this.persistenceUnit == null) ? 0 : 1;
	}
	
	public PersistenceUnit addPersistenceUnit() {
		return addPersistenceUnit(persistenceUnitsSize());
	}
	
	public PersistenceUnit addPersistenceUnit(int index) {
		if (index > 0 || this.persistenceUnit != null) {
			throw new IllegalStateException("This implementation does not support multiple persistence units."); //$NON-NLS-1$
		}
		XmlPersistenceUnit xmlPersistenceUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		this.persistenceUnit = buildPersistenceUnit(xmlPersistenceUnit);
		this.xmlPersistence.getPersistenceUnits().add(xmlPersistenceUnit);
		fireItemAdded(PERSISTENCE_UNITS_LIST, index, this.persistenceUnit);
		return this.persistenceUnit;
	}
	
	public void removePersistenceUnit(PersistenceUnit pu) {
		if (pu != this.persistenceUnit) {
			throw new IllegalArgumentException("Invalid persistence unit: " + pu); //$NON-NLS-1$
		}
		removePersistenceUnit(0);
	}
	
	public void removePersistenceUnit(int index) {
		if (index > 0 || this.persistenceUnit == null) {
			throw new IndexOutOfBoundsException("index: " + index); //$NON-NLS-1$
		}
		PersistenceUnit oldPersistenceUnit = this.persistenceUnit;
		this.persistenceUnit.dispose();
		this.persistenceUnit = null;
		this.xmlPersistence.getPersistenceUnits().remove(index);
		fireItemRemoved(PERSISTENCE_UNITS_LIST, index, oldPersistenceUnit);
	}
	
	protected void addPersistenceUnit_(PersistenceUnit newPersistenceUnit) {
		this.persistenceUnit = newPersistenceUnit;
		fireItemAdded(PERSISTENCE_UNITS_LIST, 0, this.persistenceUnit);
	}
	
	protected void removePersistenceUnit_(PersistenceUnit oldPersistenceUnit) {
		this.persistenceUnit.dispose();
		this.persistenceUnit = null;
		fireItemRemoved(PERSISTENCE_UNITS_LIST, 0, oldPersistenceUnit);
	}
	
	
	// **************** updating ***********************************************
	
	protected void initializePersistenceUnits() {
		// only adding one here, until we support multiple persistence units
		if (this.xmlPersistence.getPersistenceUnits().size() > 0) {
			this.persistenceUnit = buildPersistenceUnit(this.xmlPersistence.getPersistenceUnits().get(0));
		}
	}
	
	public void update(XmlPersistence persistence) {
		this.xmlPersistence = persistence;
		XmlPersistenceUnit xmlPersistenceUnit = null;
		if (persistence.getPersistenceUnits().size() > 0) {
			xmlPersistenceUnit = persistence.getPersistenceUnits().get(0);
		}
				
		if (this.persistenceUnit != null) {
			if (xmlPersistenceUnit != null) {
				this.persistenceUnit.update(xmlPersistenceUnit);
			}
			else {
				removePersistenceUnit_(this.persistenceUnit);
			}
		}
		else {
			if (xmlPersistenceUnit != null) {
				addPersistenceUnit_(buildPersistenceUnit(xmlPersistenceUnit));
			}
		}
	}
	
	@Override
	public void postUpdate() {
		super.postUpdate();
		if (this.persistenceUnit != null) {
			this.persistenceUnit.postUpdate();
		}
	}
	
	protected PersistenceUnit buildPersistenceUnit(XmlPersistenceUnit xmlPersistenceUnit) {
		return getJpaFactory().buildPersistenceUnit(this, xmlPersistenceUnit);
	}
	
	
	// *************************************************************************
	
	@Override
	public PersistenceUnit getPersistenceUnit() {
		throw new UnsupportedOperationException("No PersistenceUnit in this context"); //$NON-NLS-1$
	}
	
	public JpaStructureNode getStructureNode(int textOffset) {
		for (PersistenceUnit pu : CollectionTools.iterable(persistenceUnits())) {
			if (pu.containsOffset(textOffset)) {
				return pu.getStructureNode(textOffset);
			}
		}
		return this;
	}
	
	public boolean containsOffset(int textOffset) {
		return (this.xmlPersistence == null) ? false : this.xmlPersistence.containsOffset(textOffset);
	}
	
	public TextRange getSelectionTextRange() {
		return this.xmlPersistence.getSelectionTextRange();
	}
	
	public TextRange getValidationTextRange() {
		return this.xmlPersistence.getValidationTextRange();
	}

	public void dispose() {
		for (PersistenceUnit pu : CollectionTools.iterable(persistenceUnits())) {
			pu.dispose();
		}
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.checkForMultiplePersistenceUnits(messages);
		this.validatePersistenceUnit(messages, reporter);
	}

	/**
	 * extraneous persistence units can be
	 * accessed through the XmlPersistence resource object
	 */
	protected void checkForMultiplePersistenceUnits(List<IMessage> messages) {
		if (this.xmlPersistence.getPersistenceUnits().size() > 1) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.NORMAL_SEVERITY,
					JpaValidationMessages.PERSISTENCE_MULTIPLE_PERSISTENCE_UNITS,
					this, 
					this.getValidationTextRange()
				)
			);
		}
	}
	
	protected void validatePersistenceUnit(List<IMessage> messages, IReporter reporter) {
		if (this.persistenceUnit == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT,
					this, 
					this.getValidationTextRange()
				)
			);
			return;
		}
		this.persistenceUnit.validate(messages, reporter);
	}
}
