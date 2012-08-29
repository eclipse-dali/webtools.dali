/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaGeneratorContainer
	extends AbstractJavaJpaContextNode
	implements JavaGeneratorContainer
{
	protected final ParentAdapter parentAdapter;

	protected JavaSequenceGenerator sequenceGenerator;

	protected JavaTableGenerator tableGenerator;


	public GenericJavaGeneratorContainer(ParentAdapter parentAdapter) {
		super(parentAdapter.getGeneratorContainerParent());
		this.parentAdapter = parentAdapter;
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

	public JavaSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	public JavaSequenceGenerator addSequenceGenerator() {
		if (this.sequenceGenerator != null) {
			throw new IllegalStateException("sequence generator already exists: " + this.sequenceGenerator); //$NON-NLS-1$
		}
		SequenceGeneratorAnnotation annotation = this.buildSequenceGeneratorAnnotation();
		JavaSequenceGenerator generator = this.buildSequenceGenerator(annotation);
		this.setSequenceGenerator(generator);
		return generator;
	}

	protected SequenceGeneratorAnnotation buildSequenceGeneratorAnnotation() {
		return (SequenceGeneratorAnnotation) this.parentAdapter.getResourceAnnotatedElement().addAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}

	public void removeSequenceGenerator() {
		if (this.sequenceGenerator == null) {
			throw new IllegalStateException("sequence generator does not exist"); //$NON-NLS-1$
		}
		this.parentAdapter.getResourceAnnotatedElement().removeAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
		this.setSequenceGenerator(null);
	}

	protected JavaSequenceGenerator buildSequenceGenerator() {
		SequenceGeneratorAnnotation annotation = this.getSequenceGeneratorAnnotation();
		return (annotation == null) ? null : this.buildSequenceGenerator(annotation);
	}

	protected SequenceGeneratorAnnotation getSequenceGeneratorAnnotation() {
		return (SequenceGeneratorAnnotation) this.parentAdapter.getResourceAnnotatedElement().getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}

	protected JavaSequenceGenerator buildSequenceGenerator(SequenceGeneratorAnnotation sequenceGeneratorAnnotation) {
		return this.parentAdapter.parentSupportsGenerators() ?
				this.getJpaFactory().buildJavaSequenceGenerator(this, sequenceGeneratorAnnotation) :
				null;
	}

	protected void syncSequenceGenerator() {
		SequenceGeneratorAnnotation annotation = this.getSequenceGeneratorAnnotation();
		if (annotation == null) {
			if (this.sequenceGenerator != null) {
				this.setSequenceGenerator(null);
			}
		} else {
			if ((this.sequenceGenerator != null) && (this.sequenceGenerator.getGeneratorAnnotation() == annotation)) {
				this.sequenceGenerator.synchronizeWithResourceModel();
			} else {
				this.setSequenceGenerator(this.buildSequenceGenerator(annotation));
			}
		}
	}

	protected void setSequenceGenerator(JavaSequenceGenerator sequenceGenerator) {
		JavaSequenceGenerator old = this.sequenceGenerator;
		this.sequenceGenerator = sequenceGenerator;
		this.firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, old, sequenceGenerator);
	}


	// ********** table generator **********

	public JavaTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}

	public JavaTableGenerator addTableGenerator() {
		if (this.tableGenerator != null) {
			throw new IllegalStateException("table generator already exists: " + this.tableGenerator); //$NON-NLS-1$
		}
		TableGeneratorAnnotation annotation = this.buildTableGeneratorAnnotation();
		JavaTableGenerator generator = this.buildTableGenerator(annotation);
		this.setTableGenerator(generator);
		return generator;
	}

	protected TableGeneratorAnnotation buildTableGeneratorAnnotation() {
		return (TableGeneratorAnnotation) this.parentAdapter.getResourceAnnotatedElement().addAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}

	public void removeTableGenerator() {
		if (this.tableGenerator == null) {
			throw new IllegalStateException("table generator does not exist"); //$NON-NLS-1$
		}
		this.parentAdapter.getResourceAnnotatedElement().removeAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
		this.setTableGenerator(null);
	}

	protected JavaTableGenerator buildTableGenerator() {
		TableGeneratorAnnotation annotation = this.getTableGeneratorAnnotation();
		return (annotation == null) ? null : this.buildTableGenerator(annotation);
	}

	protected TableGeneratorAnnotation getTableGeneratorAnnotation() {
		return (TableGeneratorAnnotation) this.parentAdapter.getResourceAnnotatedElement().getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}

	protected JavaTableGenerator buildTableGenerator(TableGeneratorAnnotation tableGeneratorAnnotation) {
		return this.parentAdapter.parentSupportsGenerators() ?
				this.getJpaFactory().buildJavaTableGenerator(this, tableGeneratorAnnotation) :
				null;
	}

	protected void syncTableGenerator() {
		TableGeneratorAnnotation annotation = this.getTableGeneratorAnnotation();
		if (annotation == null) {
			if (this.tableGenerator != null) {
				this.setTableGenerator(null);
			}
		} else {
			if ((this.tableGenerator != null) && (this.tableGenerator.getGeneratorAnnotation() == annotation)) {
				this.tableGenerator.synchronizeWithResourceModel();
			} else {
				this.setTableGenerator(this.buildTableGenerator(annotation));
			}
		}
	}

	protected void setTableGenerator(JavaTableGenerator tableGenerator) {
		JavaTableGenerator old = this.tableGenerator;
		this.tableGenerator = tableGenerator;
		this.firePropertyChanged(TABLE_GENERATOR_PROPERTY, old, tableGenerator);
	}


	// ********** code completion **********

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
		TextRange textRange = this.getResourceTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}

	protected TextRange getResourceTextRange() {
		return this.parentAdapter.getResourceAnnotatedElement().getTextRange();
	}


	// ********** misc **********

	@Override
	public JavaJpaContextNode getParent() {
		return (JavaJpaContextNode) super.getParent();
	}

	public Iterable<Generator> getGenerators() {
		return new FilteringIterable<Generator>(this.getGenerators_(), NotNullFilter.<Generator>instance());
	}

	protected Iterable<Generator> getGenerators_() {
		return new ArrayIterable<Generator>(this.sequenceGenerator, this.tableGenerator);
	}
}
