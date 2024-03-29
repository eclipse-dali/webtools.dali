/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaGeneratorContainer
	extends AbstractJavaContextModel<JavaGeneratorContainer.Parent>
	implements JavaGeneratorContainer
{
	protected JavaSequenceGenerator sequenceGenerator;

	protected JavaTableGenerator tableGenerator;


	public GenericJavaGeneratorContainer(JavaGeneratorContainer.Parent parent) {
		super(parent);
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
		return (SequenceGeneratorAnnotation) this.parent.getResourceAnnotatedElement().addAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}

	public void removeSequenceGenerator() {
		if (this.sequenceGenerator == null) {
			throw new IllegalStateException("sequence generator does not exist"); //$NON-NLS-1$
		}
		this.parent.getResourceAnnotatedElement().removeAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
		this.setSequenceGenerator(null);
	}

	protected JavaSequenceGenerator buildSequenceGenerator() {
		SequenceGeneratorAnnotation annotation = this.getSequenceGeneratorAnnotation();
		return (annotation == null) ? null : this.buildSequenceGenerator(annotation);
	}

	protected SequenceGeneratorAnnotation getSequenceGeneratorAnnotation() {
		return (SequenceGeneratorAnnotation) this.parent.getResourceAnnotatedElement().getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}

	protected JavaSequenceGenerator buildSequenceGenerator(SequenceGeneratorAnnotation sequenceGeneratorAnnotation) {
		return this.parent.supportsGenerators() ?
				this.getJpaFactory().buildJavaSequenceGenerator(this, sequenceGeneratorAnnotation) :
				null;
	}

	protected void syncSequenceGenerator(IProgressMonitor monitor) {
		SequenceGeneratorAnnotation annotation = this.getSequenceGeneratorAnnotation();
		if (annotation == null) {
			if (this.sequenceGenerator != null) {
				this.setSequenceGenerator(null);
			}
		} else {
			if ((this.sequenceGenerator != null) && (this.sequenceGenerator.getGeneratorAnnotation() == annotation)) {
				this.sequenceGenerator.synchronizeWithResourceModel(monitor);
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
		return (TableGeneratorAnnotation) this.parent.getResourceAnnotatedElement().addAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}

	public void removeTableGenerator() {
		if (this.tableGenerator == null) {
			throw new IllegalStateException("table generator does not exist"); //$NON-NLS-1$
		}
		this.parent.getResourceAnnotatedElement().removeAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
		this.setTableGenerator(null);
	}

	protected JavaTableGenerator buildTableGenerator() {
		TableGeneratorAnnotation annotation = this.getTableGeneratorAnnotation();
		return (annotation == null) ? null : this.buildTableGenerator(annotation);
	}

	protected TableGeneratorAnnotation getTableGeneratorAnnotation() {
		return (TableGeneratorAnnotation) this.parent.getResourceAnnotatedElement().getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}

	protected JavaTableGenerator buildTableGenerator(TableGeneratorAnnotation tableGeneratorAnnotation) {
		return this.parent.supportsGenerators() ?
				this.getJpaFactory().buildJavaTableGenerator(this, tableGeneratorAnnotation) :
				null;
	}

	protected void syncTableGenerator(IProgressMonitor monitor) {
		TableGeneratorAnnotation annotation = this.getTableGeneratorAnnotation();
		if (annotation == null) {
			if (this.tableGenerator != null) {
				this.setTableGenerator(null);
			}
		} else {
			if ((this.tableGenerator != null) && (this.tableGenerator.getGeneratorAnnotation() == annotation)) {
				this.tableGenerator.synchronizeWithResourceModel(monitor);
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
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}

	protected TextRange getResourceTextRange() {
		return this.parent.getResourceAnnotatedElement().getTextRange();
	}


	// ********** misc **********

	public Iterable<Generator> getGenerators() {
		return IterableTools.removeNulls(this.getGenerators_());
	}

	protected Iterable<Generator> getGenerators_() {
		return IterableTools.<Generator>iterable(this.sequenceGenerator, this.tableGenerator);
	}
}
