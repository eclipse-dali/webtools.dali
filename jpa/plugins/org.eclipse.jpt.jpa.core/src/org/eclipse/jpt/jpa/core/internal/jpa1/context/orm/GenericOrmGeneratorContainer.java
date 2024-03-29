/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTableGenerator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmGeneratorContainer
	extends AbstractOrmXmlContextModel<JpaContextModel>
	implements OrmGeneratorContainer
{
	protected final XmlGeneratorContainer xmlGeneratorContainer;

	protected OrmSequenceGenerator sequenceGenerator;

	protected OrmTableGenerator tableGenerator;


	public GenericOrmGeneratorContainer(JpaContextModel parent, XmlGeneratorContainer xmlGeneratorContainer) {
		super(parent);
		this.xmlGeneratorContainer = xmlGeneratorContainer;
		this.sequenceGenerator = this.buildSequenceGenerator();
		this.tableGenerator = this.buildTableGenerator();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncSequenceGenerator(monitor);
		this.syncTableGenerator(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		if (this.sequenceGenerator != null) {
			this.sequenceGenerator.update(monitor);
		}
		if (this.tableGenerator != null) {
			this.tableGenerator.update(monitor);
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
		return this.getContextModelFactory().buildOrmSequenceGenerator(this, xmlSequenceGenerator);
	}

	protected void syncSequenceGenerator(IProgressMonitor monitor) {
		XmlSequenceGenerator xmlGenerator = this.getXmlSequenceGenerator();
		if (xmlGenerator == null) {
			if (this.sequenceGenerator != null) {
				this.setSequenceGenerator_(null);
			}
		} else {
			if ((this.sequenceGenerator != null) && (this.sequenceGenerator.getXmlGenerator() == xmlGenerator)) {
				this.sequenceGenerator.synchronizeWithResourceModel(monitor);
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
		return this.getContextModelFactory().buildOrmTableGenerator(this, xmlTableGenerator);
	}

	protected void syncTableGenerator(IProgressMonitor monitor) {
		XmlTableGenerator xmlGenerator = this.getXmlTableGenerator();
		if (xmlGenerator == null) {
			if (this.tableGenerator != null) {
				this.setTableGenerator_(null);
			}
		} else {
			if ((this.tableGenerator != null) && (this.tableGenerator.getXmlGenerator() == xmlGenerator)) {
				this.tableGenerator.synchronizeWithResourceModel(monitor);
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
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.tableGenerator != null) {
			result = this.tableGenerator.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		if (this.sequenceGenerator != null) {
			result = this.sequenceGenerator.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	// ********** misc **********

	public Iterable<Generator> getGenerators() {
		return IterableTools.removeNulls(this.getGenerators_());
	}

	protected Iterable<Generator> getGenerators_() {
		return IterableTools.<Generator>iterable(this.sequenceGenerator, this.tableGenerator);
	}
}
