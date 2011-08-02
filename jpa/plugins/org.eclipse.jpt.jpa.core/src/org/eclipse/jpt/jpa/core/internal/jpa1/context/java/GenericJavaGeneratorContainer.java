/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
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
	protected final Owner owner;

	protected JavaSequenceGenerator sequenceGenerator;

	protected JavaTableGenerator tableGenerator;


	public GenericJavaGeneratorContainer(JavaJpaContextNode parent, Owner owner) {
		super(parent);
		this.owner = owner;
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
		return (SequenceGeneratorAnnotation) this.owner.getResourceAnnotatedElement().addAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}

	public void removeSequenceGenerator() {
		if (this.sequenceGenerator == null) {
			throw new IllegalStateException("sequence generator does not exist"); //$NON-NLS-1$
		}
		this.owner.getResourceAnnotatedElement().removeAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
		this.setSequenceGenerator(null);
	}

	protected JavaSequenceGenerator buildSequenceGenerator() {
		SequenceGeneratorAnnotation annotation = this.getSequenceGeneratorAnnotation();
		return (annotation == null) ? null : this.buildSequenceGenerator(annotation);
	}

	protected SequenceGeneratorAnnotation getSequenceGeneratorAnnotation() {
		return (SequenceGeneratorAnnotation) this.owner.getResourceAnnotatedElement().getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}

	protected JavaSequenceGenerator buildSequenceGenerator(SequenceGeneratorAnnotation sequenceGeneratorAnnotation) {
		return this.isVirtual() ? null : this.getJpaFactory().buildJavaSequenceGenerator(this, sequenceGeneratorAnnotation);
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
		return (TableGeneratorAnnotation) this.owner.getResourceAnnotatedElement().addAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}

	public void removeTableGenerator() {
		if (this.tableGenerator == null) {
			throw new IllegalStateException("table generator does not exist"); //$NON-NLS-1$
		}
		this.owner.getResourceAnnotatedElement().removeAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
		this.setTableGenerator(null);
	}

	protected JavaTableGenerator buildTableGenerator() {
		TableGeneratorAnnotation annotation = this.getTableGeneratorAnnotation();
		return (annotation == null) ? null : this.buildTableGenerator(annotation);
	}

	protected TableGeneratorAnnotation getTableGeneratorAnnotation() {
		return (TableGeneratorAnnotation) this.owner.getResourceAnnotatedElement().getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}

	protected JavaTableGenerator buildTableGenerator(TableGeneratorAnnotation tableGeneratorAnnotation) {
		return this.isVirtual() ? null : this.getJpaFactory().buildJavaTableGenerator(this, tableGeneratorAnnotation);
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
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.tableGenerator != null) {
			result = this.tableGenerator.getJavaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		if (this.sequenceGenerator != null) {
			result = this.sequenceGenerator.getJavaCompletionProposals(pos, filter, astRoot);
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
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		// generators are validated in the persistence unit
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getResourceTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

	protected TextRange getResourceTextRange(CompilationUnit astRoot) {
		return this.owner.getResourceAnnotatedElement().getTextRange(astRoot);
	}


	// ********** misc **********

	@Override
	public JavaJpaContextNode getParent() {
		return (JavaJpaContextNode) super.getParent();
	}

	/**
	 * Return whether the container is <em>virtual</em> and, as a result, does
	 * not have either a sequence or table generator. As of JPA 2.0, this is
	 * only true when the container's parent is a virtual ID mapping.
	 */
	// TODO bjv need to add API to JavaGeneratorContainer.Owner
	protected boolean isVirtual() {
		JavaJpaContextNode p = this.getParent();
		if (p instanceof JavaIdMapping) {
			JavaIdMapping idMapping = (JavaIdMapping) p;
			return idMapping.getPersistentAttribute().isVirtual();
		}
		return false;
	}
}
