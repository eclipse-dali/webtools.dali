/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.java.JavaGenerator;
import org.eclipse.jpt.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaGeneratorContainer extends AbstractJavaJpaContextNode
	implements JavaGeneratorContainer
{
	protected JavaResourcePersistentMember javaResourcePersistentMember;

	protected JavaSequenceGenerator sequenceGenerator;

	protected JavaTableGenerator tableGenerator;
	
	public GenericJavaGeneratorContainer(JavaJpaContextNode parent) {
		super(parent);
	}

	@Override
	public JavaJpaContextNode getParent() {
		return (JavaJpaContextNode) super.getParent();
	}
	
	public JavaTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists"); //$NON-NLS-1$
		}
		this.tableGenerator = getJpaFactory().buildJavaTableGenerator(this);
		TableGeneratorAnnotation tableGeneratorResource = (TableGeneratorAnnotation) this.javaResourcePersistentMember.addSupportingAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
		this.tableGenerator.initialize(tableGeneratorResource);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed"); //$NON-NLS-1$
		}
		JavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.javaResourcePersistentMember.removeSupportingAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, null);
	}
	
	public JavaTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}
	
	protected void setTableGenerator(JavaTableGenerator newTableGenerator) {
		JavaTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = newTableGenerator;
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, newTableGenerator);
	}

	public JavaSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists"); //$NON-NLS-1$
		}
		this.sequenceGenerator = getJpaFactory().buildJavaSequenceGenerator(this);
		SequenceGeneratorAnnotation sequenceGeneratorResource = (SequenceGeneratorAnnotation) this.javaResourcePersistentMember.addSupportingAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
		this.sequenceGenerator.initialize(sequenceGeneratorResource);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed"); //$NON-NLS-1$
		}
		JavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.javaResourcePersistentMember.removeSupportingAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator,null);
	}
	
	public JavaSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(JavaSequenceGenerator newSequenceGenerator) {
		JavaSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}
	
	
	public void initialize(JavaResourcePersistentMember jrpm) {
		this.javaResourcePersistentMember = jrpm;
		this.initializeTableGenerator();
		this.initializeSequenceGenerator();
	}
	
	protected void initializeTableGenerator() {
		TableGeneratorAnnotation tableGeneratorResource = getResourceTableGenerator();
		if (tableGeneratorResource != null) {
			this.tableGenerator = buildTableGenerator(tableGeneratorResource);
		}
	}
	
	protected void initializeSequenceGenerator() {
		SequenceGeneratorAnnotation sequenceGeneratorResource = getResourceSequenceGenerator();
		if (sequenceGeneratorResource != null) {
			this.sequenceGenerator = buildSequenceGenerator(sequenceGeneratorResource);
		}
	}

	public void update(JavaResourcePersistentMember jrpm) {
		this.javaResourcePersistentMember = jrpm;
		this.updateTableGenerator();
		this.updateSequenceGenerator();
	}
	
	protected void updateTableGenerator() {
		TableGeneratorAnnotation tableGeneratorResource = getResourceTableGenerator();
		if (tableGeneratorResource == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(buildTableGenerator(tableGeneratorResource));
			}
			else {
				getTableGenerator().update(tableGeneratorResource);
			}
		}
	}
	
	protected JavaTableGenerator buildTableGenerator(TableGeneratorAnnotation tableGeneratorResource) {
		JavaTableGenerator generator = getJpaFactory().buildJavaTableGenerator(this);
		generator.initialize(tableGeneratorResource);
		return generator;
	}
	
	protected TableGeneratorAnnotation getResourceTableGenerator() {
		return (TableGeneratorAnnotation) this.javaResourcePersistentMember.getSupportingAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME);
	}

	protected void updateSequenceGenerator() {
		SequenceGeneratorAnnotation sequenceGeneratorResource = getResourceSequenceGenerator();
		if (sequenceGeneratorResource == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(buildSequenceGenerator(sequenceGeneratorResource));
			}
			else {
				getSequenceGenerator().update(sequenceGeneratorResource);
			}
		}
	}
	
	protected JavaSequenceGenerator buildSequenceGenerator(SequenceGeneratorAnnotation sequenceGeneratorResource) {
		JavaSequenceGenerator generator = getJpaFactory().buildJavaSequenceGenerator(this);
		generator.initialize(sequenceGeneratorResource);
		return generator;
	}
	
	protected SequenceGeneratorAnnotation getResourceSequenceGenerator() {
		return (SequenceGeneratorAnnotation) this.javaResourcePersistentMember.getSupportingAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME);
	}
	
	//******************** Code Completion *************************

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.getTableGenerator() != null) {
			result = this.getTableGenerator().javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		if (this.getSequenceGenerator() != null) {
			result = this.getSequenceGenerator().javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	//********** Validation ********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateGenerators(messages, astRoot);
	}
	
	protected void validateGenerators(List<IMessage> messages, CompilationUnit astRoot) {
		for (Iterator<JavaGenerator> localGenerators = this.generators(); localGenerators.hasNext(); ) {
			JavaGenerator localGenerator = localGenerators.next();
			for (Iterator<Generator> globalGenerators = this.getPersistenceUnit().generators(); globalGenerators.hasNext(); ) {
				if (localGenerator.duplicates(globalGenerators.next())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
							new String[] {localGenerator.getName()},
							localGenerator,
							localGenerator.getNameTextRange(astRoot)
						)
					);
				}
			}
		}
	}

	protected final Iterator<JavaGenerator> generators() {
		ArrayList<JavaGenerator> generators = new ArrayList<JavaGenerator>();
		this.addGeneratorsTo(generators);
		return generators.iterator();
	}

	protected void addGeneratorsTo(ArrayList<JavaGenerator> generators) {
		if (this.sequenceGenerator != null) {
			generators.add(this.sequenceGenerator);
		}
		if (this.tableGenerator != null) {
			generators.add(this.tableGenerator);
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.javaResourcePersistentMember.getTextRange(astRoot);
	}

}
