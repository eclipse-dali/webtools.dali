/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmGenerator;
import org.eclipse.jpt.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmGeneratorContainer extends AbstractXmlContextNode
	implements OrmGeneratorContainer
{
	protected OrmSequenceGenerator sequenceGenerator;

	protected OrmTableGenerator tableGenerator;
	
	protected final XmlGeneratorContainer resourceGeneratorContainer;
	
	public GenericOrmGeneratorContainer(XmlContextNode parent, XmlGeneratorContainer resourceGeneratorContainer) {
		super(parent);
		this.resourceGeneratorContainer = resourceGeneratorContainer;
		this.initializeSequenceGenerator();
		this.initializeTableGenerator();
	}
	
	public OrmSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists"); //$NON-NLS-1$
		}
		XmlSequenceGenerator resourceSequenceGenerator = OrmFactory.eINSTANCE.createXmlSequenceGenerator();
		this.sequenceGenerator = buildSequenceGenerator(resourceSequenceGenerator);
		this.resourceGeneratorContainer.setSequenceGenerator(resourceSequenceGenerator);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed"); //$NON-NLS-1$
		}
		OrmSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.resourceGeneratorContainer.setSequenceGenerator(null);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, null);
	}
	
	public OrmSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(OrmSequenceGenerator newSequenceGenerator) {
		OrmSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}

	public OrmTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists"); //$NON-NLS-1$
		}
		XmlTableGenerator resourceTableGenerator = OrmFactory.eINSTANCE.createXmlTableGenerator();
		this.tableGenerator = buildTableGenerator(resourceTableGenerator);
		this.resourceGeneratorContainer.setTableGenerator(resourceTableGenerator);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed"); //$NON-NLS-1$
		}
		OrmTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.resourceGeneratorContainer.setTableGenerator(null);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, null);
	}
	
	public OrmTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}

	protected void setTableGenerator(OrmTableGenerator newTableGenerator) {
		OrmTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = newTableGenerator;
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, newTableGenerator);
	}
	
	protected void initializeSequenceGenerator() {
		if (this.resourceGeneratorContainer.getSequenceGenerator() != null) {
			this.sequenceGenerator = buildSequenceGenerator(this.resourceGeneratorContainer.getSequenceGenerator());
		}
	}
	
	protected OrmSequenceGenerator buildSequenceGenerator(XmlSequenceGenerator resourceSequenceGenerator) {
		return getJpaFactory().buildOrmSequenceGenerator(this, resourceSequenceGenerator);
	}

	protected void initializeTableGenerator() {
		if (this.resourceGeneratorContainer.getTableGenerator() != null) {
			this.tableGenerator = buildTableGenerator(this.resourceGeneratorContainer.getTableGenerator());
		}
	}
	
	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator resourceTableGenerator) {
		return getJpaFactory().buildOrmTableGenerator(this, resourceTableGenerator);
	}
	
	public void update() {
		this.updateSequenceGenerator();
		this.updateTableGenerator();
	}
	
	protected void updateSequenceGenerator() {
		if (this.resourceGeneratorContainer.getSequenceGenerator() == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(buildSequenceGenerator(this.resourceGeneratorContainer.getSequenceGenerator()));
			}
			else {
				getSequenceGenerator().update(this.resourceGeneratorContainer.getSequenceGenerator());
			}
		}
	}
	
	protected void updateTableGenerator() {
		if (this.resourceGeneratorContainer.getTableGenerator() == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(buildTableGenerator(this.resourceGeneratorContainer.getTableGenerator()));
			}
			else {
				getTableGenerator().update(this.resourceGeneratorContainer.getTableGenerator());
			}
		}
	}

	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateGenerators(messages);
	}
	
	protected void validateGenerators(List<IMessage> messages) {
		for (Iterator<OrmGenerator> localGenerators = this.generators(); localGenerators.hasNext(); ) {
			OrmGenerator localGenerator = localGenerators.next();
			for (Iterator<Generator> globalGenerators = this.getPersistenceUnit().generators(); globalGenerators.hasNext(); ) {
				if (localGenerator.duplicates(globalGenerators.next())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
							new String[] {localGenerator.getName()},
							localGenerator,
							localGenerator.getNameTextRange()
						)
					);
				}
			}
		}
	}
	
	protected Iterator<OrmGenerator> generators() {
		ArrayList<OrmGenerator> generators = new ArrayList<OrmGenerator>();
		this.addGeneratorsTo(generators);
		return generators.iterator();
	}

	protected void addGeneratorsTo(ArrayList<OrmGenerator> generators) {
		if (this.sequenceGenerator != null) {
			generators.add(this.sequenceGenerator);
		}
		if (this.tableGenerator != null) {
			generators.add(this.tableGenerator);
		}
	}

	public TextRange getValidationTextRange() {
		return this.resourceGeneratorContainer.getValidationTextRange();
	}
}
