/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Vector;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceXmlContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.Persistence2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>persistence.xml</code> file
 * <br>
 * <code>persistence</code> element
 */
public class GenericPersistence
	extends AbstractPersistenceXmlContextModel<PersistenceXml>
	implements Persistence2_0
{
	protected final XmlPersistence xmlPersistence;

	// The implementation here is a single persistence unit, although the API
	// is for a list. We want to support multiple persistence units someday....
	protected PersistenceUnit persistenceUnit;

	protected final Vector<PersistenceUnit> structureChildren = new Vector<PersistenceUnit>();

	public GenericPersistence(PersistenceXml parent, XmlPersistence xmlPersistence) {
		super(parent);
		this.xmlPersistence = xmlPersistence;
		this.initializePersistenceUnits();
		this.initializeStructureChildren();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncPersistenceUnits(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateModels(this.getPersistenceUnits(), monitor);
		this.updateStructureChildren();
	}

	public void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		if (this.getResource().equals(jpaFile.getFile())) {
			rootStructureNodes.add(this);
		} else {
			if (this.persistenceUnit != null) {
				this.persistenceUnit.addRootStructureNodesTo(jpaFile, rootStructureNodes);
			}
		}
	}


	// ********** persistence units **********

	public ListIterable<PersistenceUnit> getPersistenceUnits() {
		return (this.persistenceUnit == null) ? EmptyListIterable.<PersistenceUnit>instance() : this.getPersistenceUnits_();
	}

	protected ListIterable<PersistenceUnit> getPersistenceUnits_() {
		return new SingleElementListIterable<PersistenceUnit>(this.persistenceUnit);
	}

	public int getPersistenceUnitsSize() {
		return (this.persistenceUnit == null) ? 0 : 1;
	}

	public PersistenceUnit getPersistenceUnit(int index) {
		if ((index != 0) || (this.persistenceUnit == null)) {
			throw this.buildIOOBE(index);
		}
		return this.persistenceUnit;
	}

	protected IndexOutOfBoundsException buildIOOBE(int index) {
		return new IndexOutOfBoundsException("index: " + index); //$NON-NLS-1$
	}

	public PersistenceUnit addPersistenceUnit() {
		return this.addPersistenceUnit(this.getPersistenceUnitsSize());
	}

	public PersistenceUnit addPersistenceUnit(int index) {
		if ((index != 0) || (this.persistenceUnit != null)) {
			throw this.buildIOOBE(index);
		}
		XmlPersistenceUnit xmlPersistenceUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		this.persistenceUnit = this.buildPersistenceUnit(xmlPersistenceUnit);
		this.xmlPersistence.getPersistenceUnits().add(xmlPersistenceUnit);
		this.fireItemAdded(PERSISTENCE_UNITS_LIST, index, this.persistenceUnit);
		this.persistenceUnit.setName(this.getJpaProject().getName());  // default to the project name
		return this.persistenceUnit;
	}

	public void removePersistenceUnit(PersistenceUnit pu) {
		if (pu != this.persistenceUnit) {
			throw new IllegalArgumentException("Invalid persistence unit: " + pu); //$NON-NLS-1$
		}
		this.removePersistenceUnit(0);
	}

	public void removePersistenceUnit(int index) {
		if ((index != 0) || (this.persistenceUnit == null)) {
			throw this.buildIOOBE(index);
		}
		PersistenceUnit old = this.persistenceUnit;
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

	protected void syncPersistenceUnits(IProgressMonitor monitor) {
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
					this.persistenceUnit.synchronizeWithResourceModel(monitor);
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
		this.fireItemRemoved(PERSISTENCE_UNITS_LIST, 0, old);
	}

	protected PersistenceUnit buildPersistenceUnit(XmlPersistenceUnit xmlPersistenceUnit) {
		return this.getContextModelFactory().buildPersistenceUnit(this, xmlPersistenceUnit);
	}

	// ********** metamodel **********

	public void initializeMetamodel() {
		for (PersistenceUnit pu : this.getPersistenceUnits()) {
			((PersistenceUnit2_0) pu).initializeMetamodel();
		}
	}

	public IStatus synchronizeMetamodel(IProgressMonitor monitor) {
		for (PersistenceUnit pu : this.getPersistenceUnits()) {
			IStatus status = ((PersistenceUnit2_0) pu).synchronizeMetamodel(monitor);
			if (status.getSeverity() == IStatus.CANCEL) {
				return status;  // seems reasonable...
			}
		}
		return Status.OK_STATUS;
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


	// ********** XmlContextNode implementation **********

	public PersistenceXml getPersistenceXml() {
		return this.parent;
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlPersistence.getValidationTextRange();
		return (textRange != null) ? textRange : this.getPersistenceXml().getValidationTextRange();
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<Persistence> getStructureType() {
		return Persistence.class;
	}

	public TextRange getSelectionTextRange() {
		return this.xmlPersistence.getSelectionTextRange();
	}

	public TextRange getFullTextRange() {
		return this.xmlPersistence.getFullTextRange();
	}

	public boolean containsOffset(int textOffset) {
		return this.xmlPersistence.containsOffset(textOffset);
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		for (JpaStructureNode child : this.getStructureChildren()) {
			if (child.containsOffset(textOffset)) {
				return child.getStructureNode(textOffset);
			}
		}
		return this;
	}

	protected void initializeStructureChildren() {
		CollectionTools.addAll(this.structureChildren, this.getPersistenceUnits());
	}

	protected void updateStructureChildren() {
		this.synchronizeCollection(this.getPersistenceUnits(), this.structureChildren, STRUCTURE_CHILDREN_COLLECTION);
	}

	public Iterable<PersistenceUnit> getStructureChildren() {
		return IterableTools.cloneLive(this.structureChildren);
	}

	public int getStructureChildrenSize() {
		return this.structureChildren.size();
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
		if (! this.getLatestDocumentVersion().equals(this.xmlPersistence.getDocumentVersion())) {
			messages.add(
					this.buildValidationMessage(
						this.xmlPersistence.getVersionTextRange(),
						JptJpaCoreValidationMessages.XML_VERSION_NOT_LATEST
					)
				);
		}
	}

	protected String getLatestDocumentVersion() {
		return this.getJpaPlatform().getMostRecentSupportedResourceType(
				XmlPersistence.CONTENT_TYPE).getVersion();
	}

	/**
	 * extraneous persistence units can be
	 * accessed through the XmlPersistence resource object
	 */
	protected void checkForMultiplePersistenceUnits(List<IMessage> messages) {
		if (this.xmlPersistence.getPersistenceUnits().size() > 1) {
			messages.add(
				this.buildValidationMessage(
					this.getValidationTextRange(),
					JptJpaCoreValidationMessages.PERSISTENCE_MULTIPLE_PERSISTENCE_UNITS
				)
			);
		}
	}

	protected void validatePersistenceUnit(List<IMessage> messages, IReporter reporter) {
		if (this.persistenceUnit == null) {
			messages.add(
				this.buildValidationMessage(
					this.getValidationTextRange(),
					JptJpaCoreValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT
				)
			);
			return;
		}
		this.persistenceUnit.validate(messages, reporter);
	}
}
