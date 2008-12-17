/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.GeneratedValue;
import org.eclipse.jpt.core.context.GenerationType;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public class GenericJavaGeneratedValue
	extends AbstractJavaJpaContextNode
	implements JavaGeneratedValue
{
	private GeneratedValueAnnotation resourceGeneratedValue;

	private GenerationType specifiedStrategy;

	private String specifiedGenerator;

	// always null?
	private String defaultGenerator;
	

	public GenericJavaGeneratedValue(JavaIdMapping parent) {
		super(parent);
	}

	public void initialize(GeneratedValueAnnotation generatedValueAnnotation) {
		this.resourceGeneratedValue = generatedValueAnnotation;
		this.specifiedStrategy = this.buildSpecifiedStrategy();
		this.specifiedGenerator = generatedValueAnnotation.getGenerator();
	}


	// ********** strategy **********

	public GenerationType getStrategy() {
		return (this.specifiedStrategy != null) ? this.specifiedStrategy : this.getDefaultStrategy();
	}

	public GenerationType getDefaultStrategy() {
		return GeneratedValue.DEFAULT_STRATEGY;
	}
	
	public GenerationType getSpecifiedStrategy() {
		return this.specifiedStrategy;
	}

	public void setSpecifiedStrategy(GenerationType strategy) {
		GenerationType old = this.specifiedStrategy;
		this.specifiedStrategy = strategy;
		this.resourceGeneratedValue.setStrategy(GenerationType.toJavaResourceModel(strategy));
		this.firePropertyChanged(SPECIFIED_STRATEGY_PROPERTY, old, strategy);
	}

	protected void setSpecifiedStrategy_(GenerationType strategy) {
		GenerationType old = this.specifiedStrategy;
		this.specifiedStrategy = strategy;
		this.firePropertyChanged(SPECIFIED_STRATEGY_PROPERTY, old, strategy);
	}


	// ********** generator **********

	public String getGenerator() {
		return (this.specifiedGenerator != null) ? this.specifiedGenerator : this.defaultGenerator;
	}

	public String getDefaultGenerator() {
		return this.defaultGenerator;
	}
	
	public String getSpecifiedGenerator() {
		return this.specifiedGenerator;
	}

	public void setSpecifiedGenerator(String generator) {
		String old = this.specifiedGenerator;
		this.specifiedGenerator = generator;
		this.resourceGeneratedValue.setGenerator(generator);
		this.firePropertyChanged(SPECIFIED_GENERATOR_PROPERTY, old, generator);
	}

	protected void setSpecifiedGenerator_(String generator) {
		String old = this.specifiedGenerator;
		this.specifiedGenerator = generator;
		this.firePropertyChanged(SPECIFIED_GENERATOR_PROPERTY, old, generator);
	}
	

	// ********** text ranges **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.resourceGeneratedValue.getTextRange(astRoot);
	}

	public TextRange getGeneratorTextRange(CompilationUnit astRoot) {
		return this.resourceGeneratedValue.getGeneratorTextRange(astRoot);
	}


	// ********** completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.generatorTouches(pos, astRoot)) {
			return this.javaCandidateGeneratorNames(filter);
		}
		return null;
	}

	protected boolean generatorTouches(int pos, CompilationUnit astRoot) {
		return this.resourceGeneratedValue.generatorTouches(pos, astRoot);
	}

	protected Iterator<String> javaCandidateGeneratorNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateGeneratorNames(filter));
	}

	protected Iterator<String> candidateGeneratorNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateGeneratorNames(), filter);
	}

	protected Iterator<String> candidateGeneratorNames() {
		return new TransformationIterator<Generator, String>(this.candidateGenerators()) {
			@Override
			protected String transform(Generator generator) {
				return generator.getName();
			}
		};
	}

	protected Iterator<Generator> candidateGenerators() {
		return this.getPersistenceUnit().allGenerators();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);

		String generator = this.getGenerator();
		if (generator == null) {
			return;
		}

		for (Iterator<Generator> stream = this.getPersistenceUnit().allGenerators(); stream.hasNext(); ) {
			if (generator.equals(stream.next().getName())) {
				return;
			}
		}

		messages.add(
			DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.ID_MAPPING_UNRESOLVED_GENERATOR_NAME,
				new String[] {generator},
				this.getParent(),
				this.getGeneratorTextRange(astRoot)
			)
		);
	}


	// ********** update from resource model **********

	public void update(GeneratedValueAnnotation generatedValueAnnotation) {
		this.resourceGeneratedValue = generatedValueAnnotation;
		this.setSpecifiedStrategy_(this.buildSpecifiedStrategy());
		this.setSpecifiedGenerator_(generatedValueAnnotation.getGenerator());
	}

	protected GenerationType buildSpecifiedStrategy() {
		return GenerationType.fromJavaResourceModel(this.resourceGeneratedValue.getStrategy());
	}

}
