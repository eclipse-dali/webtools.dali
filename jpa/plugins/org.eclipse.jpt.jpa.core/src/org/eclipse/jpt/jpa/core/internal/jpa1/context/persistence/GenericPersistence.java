/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceStructureNodes;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlContextNode;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.Persistence2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>persistence.xml</code> file
 * <br>
 * <code>persistence</code> element
 */
public class GenericPersistence
	extends AbstractPersistenceXmlContextNode
	implements Persistence2_0
{
	protected final XmlPersistence xmlPersistence;

	// The implementation here is a single persistence unit, although the API
	// is for a list. We want to support multiple persistence units someday....
	protected PersistenceUnit persistenceUnit;


	public GenericPersistence(PersistenceXml parent, XmlPersistence xmlPersistence) {
		super(parent);
		this.xmlPersistence = xmlPersistence;
		this.initializePersistenceUnits();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncPersistenceUnits();
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getPersistenceUnits());
	}


	// ********** persistence units **********

	public ListIterator<PersistenceUnit> persistenceUnits() {
		return this.getPersistenceUnits().iterator();
	}

	protected ListIterable<PersistenceUnit> getPersistenceUnits() {
		return (this.persistenceUnit == null) ? EmptyListIterable.<PersistenceUnit>instance() : this.getPersistenceUnits_();
	}

	protected ListIterable<PersistenceUnit> getPersistenceUnits_() {
		return new SingleElementListIterable<PersistenceUnit>(this.persistenceUnit);
	}

	public int persistenceUnitsSize() {
		return (this.persistenceUnit == null) ? 0 : 1;
	}

	public PersistenceUnit addPersistenceUnit() {
		return this.addPersistenceUnit(this.persistenceUnitsSize());
	}

	public PersistenceUnit addPersistenceUnit(int index) {
		if ((index > 0) || (this.persistenceUnit != null)) {
			throw new IllegalStateException("This implementation does not support multiple persistence units."); //$NON-NLS-1$
		}
		XmlPersistenceUnit xmlPersistenceUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		this.persistenceUnit = this.buildPersistenceUnit(xmlPersistenceUnit);
		this.xmlPersistence.getPersistenceUnits().add(xmlPersistenceUnit);
		this.fireItemAdded(PERSISTENCE_UNITS_LIST, index, this.persistenceUnit);
		return this.persistenceUnit;
	}

	public void removePersistenceUnit(PersistenceUnit pu) {
		if (pu != this.persistenceUnit) {
			throw new IllegalArgumentException("Invalid persistence unit: " + pu); //$NON-NLS-1$
		}
		this.removePersistenceUnit(0);
	}

	public void removePersistenceUnit(int index) {
		if ((index > 0) || (this.persistenceUnit == null)) {
			throw new IndexOutOfBoundsException("index: " + index); //$NON-NLS-1$
		}
		PersistenceUnit old = this.persistenceUnit;
		this.persistenceUnit.dispose();
		this.persistenceUnit = null;
		this.xmlPersistence.getPersistenceUnits().remove(index);
		this.fireItemRemoved(PERSISTENCE_UNITS_LIST, index, old);
	}

	// only building one here, until we support multiple persistence units...
	protected void initializePersistenceUnits() {
		XmlPersistenceUnit xmlPersistenceUnit = this.getXmlPersistenceUnit();
		if (xmlPersistenceUnit != null) {
			this.persistenceUnit = this.buildPersistenceUnit(xmlPersistenceUnit);
		}
	}

	protected void syncPersistenceUnits() {
		XmlPersistenceUnit xmlPersistenceUnit = this.getXmlPersistenceUnit();
		if (this.persistenceUnit == null) {
			if (xmlPersistenceUnit != null) {
				this.addPersistenceUnit_(this.buildPersistenceUnit(xmlPersistenceUnit));
			}
		} else {
			if (xmlPersistenceUnit == null) {
				this.removePersistenceUnit_();
			} else {
				if (this.persistenceUnit.getXmlPersistenceUnit() == xmlPersistenceUnit) {
					this.persistenceUnit.synchronizeWithResourceModel();
				} else {
					this.removePersistenceUnit_();
					this.addPersistenceUnit_(this.buildPersistenceUnit(xmlPersistenceUnit));
				}
			}
		}
	}

	protected XmlPersistenceUnit getXmlPersistenceUnit() {
		List<XmlPersistenceUnit> xmlPersistenceUnits = this.xmlPersistence.getPersistenceUnits();
		return xmlPersistenceUnits.isEmpty() ? null : xmlPersistenceUnits.get(0);
	}

	protected void addPersistenceUnit_(PersistenceUnit pu) {
		this.persistenceUnit = pu;
		this.fireItemAdded(PERSISTENCE_UNITS_LIST, 0, pu);
	}

	protected void removePersistenceUnit_() {
		PersistenceUnit old = this.persistenceUnit;
		this.persistenceUnit = null;
		old.dispose();
		this.fireItemRemoved(PERSISTENCE_UNITS_LIST, 0, old);
	}

	protected PersistenceUnit buildPersistenceUnit(XmlPersistenceUnit xmlPersistenceUnit) {
		return this.getContextNodeFactory().buildPersistenceUnit(this, xmlPersistenceUnit);
	}


	// ********** metamodel **********

	public void initializeMetamodel() {
		for (PersistenceUnit pu : this.getPersistenceUnits()) {
			((PersistenceUnit2_0) pu).initializeMetamodel();
		}
	}

	public void synchronizeMetamodel() {
		for (PersistenceUnit pu : this.getPersistenceUnits()) {
			((PersistenceUnit2_0) pu).synchronizeMetamodel();
		}
	}

	public void disposeMetamodel() {
		for (PersistenceUnit pu : this.getPersistenceUnits()) {
			((PersistenceUnit2_0) pu).disposeMetamodel();
		}
	}


	// ********** Persistence implementation **********

	public XmlPersistence getXmlPersistence() {
		return this.xmlPersistence;
	}

	public boolean containsOffset(int textOffset) {
		return (this.xmlPersistence == null) ? false : this.xmlPersistence.containsOffset(textOffset);
	}


	// ********** XmlContextNode implementation **********

	@Override
	public PersistenceXml getParent() {
		return (PersistenceXml) super.getParent();
	}

	public TextRange getValidationTextRange() {
		return this.xmlPersistence.getValidationTextRange();
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return PersistenceStructureNodes.PERSISTENCE_ID;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		for (PersistenceUnit pu : this.getPersistenceUnits()) {
			if (pu.containsOffset(textOffset)) {
				return pu.getStructureNode(textOffset);
			}
		}
		return this;
	}

	public TextRange getSelectionTextRange() {
		return this.xmlPersistence.getSelectionTextRange();
	}

	public void dispose() {
		for (PersistenceUnit pu : this.getPersistenceUnits()) {
			pu.dispose();
		}
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateVersion(messages);
		this.checkForMultiplePersistenceUnits(messages);
		this.validatePersistenceUnit(messages, reporter);
	}

	protected void validateVersion(List<IMessage> messages) {
		if (! this.getLatestDocumentVersion().equals(this.xmlPersistence.getVersion())) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.LOW_SEVERITY,
						JpaValidationMessages.XML_VERSION_NOT_LATEST,
						this,
						this.xmlPersistence.getVersionTextRange()));
		}
	}

	protected String getLatestDocumentVersion() {
		return this.getJpaPlatform().getMostRecentSupportedResourceType(
				JptJpaCorePlugin.PERSISTENCE_XML_CONTENT_TYPE).getVersion();
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
