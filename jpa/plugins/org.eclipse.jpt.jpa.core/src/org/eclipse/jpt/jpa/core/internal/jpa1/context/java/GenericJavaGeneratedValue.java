/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.jpa.core.context.GenerationType;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java generated value
 */
public class GenericJavaGeneratedValue
	extends AbstractJavaJpaContextNode
	implements JavaGeneratedValue
{
	protected final GeneratedValueAnnotation generatedValueAnnotation;

	protected GenerationType specifiedStrategy;
	protected GenerationType defaultStrategy;

	protected String specifiedGenerator;
	protected String defaultGenerator;


	public GenericJavaGeneratedValue(JavaIdMapping parent, GeneratedValueAnnotation generatedValueAnnotation) {
		super(parent);
		this.generatedValueAnnotation = generatedValueAnnotation;
		this.specifiedStrategy = this.buildSpecifiedStrategy();
		this.specifiedGenerator = generatedValueAnnotation.getGenerator();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedStrategy_(this.buildSpecifiedStrategy());
		this.setSpecifiedGenerator_(this.generatedValueAnnotation.getGenerator());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultStrategy(this.buildDefaultStrategy());
		this.setDefaultGenerator(this.buildDefaultGenerator());
	}


	// ********** strategy **********

	public GenerationType getStrategy() {
		return (this.specifiedStrategy != null) ? this.specifiedStrategy : this.defaultStrategy;
	}

	public GenerationType getSpecifiedStrategy() {
		return this.specifiedStrategy;
	}

	public void setSpecifiedStrategy(GenerationType strategy) {
		this.generatedValueAnnotation.setStrategy(GenerationType.toJavaResourceModel(strategy));
		this.setSpecifiedStrategy_(strategy);
	}

	protected void setSpecifiedStrategy_(GenerationType strategy) {
		GenerationType old = this.specifiedStrategy;
		this.specifiedStrategy = strategy;
		this.firePropertyChanged(SPECIFIED_STRATEGY_PROPERTY, old, strategy);
	}

	protected GenerationType buildSpecifiedStrategy() {
		return GenerationType.fromJavaResourceModel(this.generatedValueAnnotation.getStrategy());
	}

	public GenerationType getDefaultStrategy() {
		return this.defaultStrategy;
	}

	protected void setDefaultStrategy(GenerationType strategy) {
		GenerationType old = this.defaultStrategy;
		this.defaultStrategy = strategy;
		this.firePropertyChanged(DEFAULT_STRATEGY_PROPERTY, old, strategy);
	}

	protected GenerationType buildDefaultStrategy() {
		return DEFAULT_STRATEGY;
	}


	// ********** generator **********

	public String getGenerator() {
		return (this.specifiedGenerator != null) ? this.specifiedGenerator : this.defaultGenerator;
	}

	public String getSpecifiedGenerator() {
		return this.specifiedGenerator;
	}

	public void setSpecifiedGenerator(String generator) {
		this.generatedValueAnnotation.setGenerator(generator);
		this.setSpecifiedGenerator_(generator);
	}

	protected void setSpecifiedGenerator_(String generator) {
		String old = this.specifiedGenerator;
		this.specifiedGenerator = generator;
		this.firePropertyChanged(SPECIFIED_GENERATOR_PROPERTY, old, generator);
	}

	public String getDefaultGenerator() {
		return this.defaultGenerator;
	}

	protected void setDefaultGenerator(String generator) {
		String old = this.defaultGenerator;
		this.defaultGenerator = generator;
		this.firePropertyChanged(DEFAULT_GENERATOR_PROPERTY, old, generator);
	}

	protected String buildDefaultGenerator() {
		return null;
	}

	public TextRange getGeneratorTextRange(CompilationUnit astRoot) {
		return this.generatedValueAnnotation.getGeneratorTextRange(astRoot);
	}


	// ********** misc **********

	public GeneratedValueAnnotation getGeneratedValueAnnotation() {
		return this.generatedValueAnnotation;
	}


	// ********** Java completion proposals **********

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
		return this.generatedValueAnnotation.generatorTouches(pos, astRoot);
	}

	protected Iterator<String> javaCandidateGeneratorNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateGeneratorNames(filter));
	}

	protected Iterator<String> candidateGeneratorNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateGeneratorNames(), filter);
	}

	protected Iterator<String> candidateGeneratorNames() {
		return this.getPersistenceUnit().getUniqueGeneratorNames().iterator();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);

		String generator = this.getGenerator();
		if (generator == null) {
			return;
		}

		for (Iterator<Generator> stream = this.getPersistenceUnit().generators(); stream.hasNext(); ) {
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

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.generatedValueAnnotation.getTextRange(astRoot);
	}
}
