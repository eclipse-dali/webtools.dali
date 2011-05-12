/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTableGenerator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmGeneratorContainer
	extends AbstractOrmXmlContextNode
	implements OrmGeneratorContainer
{
	protected final XmlGeneratorContainer xmlGeneratorContainer;

	protected OrmSequenceGenerator sequenceGenerator;

	protected OrmTableGenerator tableGenerator;


	public GenericOrmGeneratorContainer(XmlContextNode parent, XmlGeneratorContainer xmlGeneratorContainer) {
		super(parent);
		this.xmlGeneratorContainer = xmlGeneratorContainer;
		this.sequenceGenerator = this.buildSequenceGenerator();
		this.tableGenerator = this.buildTableGenerator();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncSequenceGenerator();
		this.syncTableGenerator();
	}

	@Override
	public void update() {
		super.update();
		if (this.sequenceGenerator != null) {
			this.sequenceGenerator.update();
		}
		if (this.tableGenerator != null) {
			this.tableGenerator.update();
		}
	}


	// ********** sequence generator **********

	public OrmSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	public OrmSequenceGenerator addSequenceGenerator() {
		if (this.sequenceGenerator != null) {
			throw new IllegalStateException("sequence generator already exists: " + this.sequenceGenerator); //$NON-NLS-1$
		}
		XmlSequenceGenerator xmlGenerator = this.buildXmlSequenceGenerator();
		OrmSequenceGenerator generator = this.buildSequenceGenerator(xmlGenerator);
		this.setSequenceGenerator_(generator);
		this.xmlGeneratorContainer.setSequenceGenerator(xmlGenerator);
		return generator;
	}

	protected XmlSequenceGenerator buildXmlSequenceGenerator() {
		return OrmFactory.eINSTANCE.createXmlSequenceGenerator();
	}

	public void removeSequenceGenerator() {
		if (this.sequenceGenerator == null) {
			throw new IllegalStateException("sequence generator does not exist"); //$NON-NLS-1$
		}
		this.setSequenceGenerator_(null);
		this.xmlGeneratorContainer.setSequenceGenerator(null);
	}

	protected OrmSequenceGenerator buildSequenceGenerator() {
		XmlSequenceGenerator xmlGenerator = this.getXmlSequenceGenerator();
		return (xmlGenerator == null) ? null : this.buildSequenceGenerator(xmlGenerator);
	}

	protected XmlSequenceGenerator getXmlSequenceGenerator() {
		return this.xmlGeneratorContainer.getSequenceGenerator();
	}

	protected OrmSequenceGenerator buildSequenceGenerator(XmlSequenceGenerator xmlSequenceGenerator) {
		return this.getContextNodeFactory().buildOrmSequenceGenerator(this, xmlSequenceGenerator);
	}

	protected void syncSequenceGenerator() {
		XmlSequenceGenerator xmlGenerator = this.getXmlSequenceGenerator();
		if (xmlGenerator == null) {
			if (this.sequenceGenerator != null) {
				this.setSequenceGenerator_(null);
			}
		} else {
			if ((this.sequenceGenerator != null) && (this.sequenceGenerator.getXmlGenerator() == xmlGenerator)) {
				this.sequenceGenerator.synchronizeWithResourceModel();
			} else {
				this.setSequenceGenerator_(this.buildSequenceGenerator(xmlGenerator));
			}
		}
	}

	protected void setSequenceGenerator_(OrmSequenceGenerator sequenceGenerator) {
		OrmSequenceGenerator old = this.sequenceGenerator;
		this.sequenceGenerator = sequenceGenerator;
		this.firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, old, sequenceGenerator);
	}


	// ********** table generator **********

	public OrmTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}

	public OrmTableGenerator addTableGenerator() {
		if (this.tableGenerator != null) {
			throw new IllegalStateException("table generator already exists: " + this.tableGenerator); //$NON-NLS-1$
		}
		XmlTableGenerator xmlGenerator = this.buildXmlTableGenerator();
		OrmTableGenerator generator = this.buildTableGenerator(xmlGenerator);
		this.setTableGenerator_(generator);
		this.xmlGeneratorContainer.setTableGenerator(xmlGenerator);
		return generator;
	}

	protected XmlTableGenerator buildXmlTableGenerator() {
		return OrmFactory.eINSTANCE.createXmlTableGenerator();
	}

	public void removeTableGenerator() {
		if (this.tableGenerator == null) {
			throw new IllegalStateException("table generator does not exist"); //$NON-NLS-1$
		}
		this.setTableGenerator_(null);
		this.xmlGeneratorContainer.setTableGenerator(null);
	}

	protected OrmTableGenerator buildTableGenerator() {
		XmlTableGenerator xmlGenerator = this.getXmlTableGenerator();
		return (xmlGenerator == null) ? null : this.buildTableGenerator(xmlGenerator);
	}

	protected XmlTableGenerator getXmlTableGenerator() {
		return this.xmlGeneratorContainer.getTableGenerator();
	}

	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator xmlTableGenerator) {
		return this.getContextNodeFactory().buildOrmTableGenerator(this, xmlTableGenerator);
	}

	protected void syncTableGenerator() {
		XmlTableGenerator xmlGenerator = this.getXmlTableGenerator();
		if (xmlGenerator == null) {
			if (this.tableGenerator != null) {
				this.setTableGenerator_(null);
			}
		} else {
			if ((this.tableGenerator != null) && (this.tableGenerator.getXmlGenerator() == xmlGenerator)) {
				this.tableGenerator.synchronizeWithResourceModel();
			} else {
				this.setTableGenerator_(this.buildTableGenerator(xmlGenerator));
			}
		}
	}

	protected void setTableGenerator_(OrmTableGenerator tableGenerator) {
		OrmTableGenerator old = this.tableGenerator;
		this.tableGenerator = tableGenerator;
		this.firePropertyChanged(TABLE_GENERATOR_PROPERTY, old, tableGenerator);
	}


	// ********** validation **********

	/**
	 * The generators are validated in the persistence unit.
	 * @see org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit#validateGenerators(List, IReporter)
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// generators are validated in the persistence unit
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlGeneratorContainer.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}


	// ********** misc **********

	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}
}
