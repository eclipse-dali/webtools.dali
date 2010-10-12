/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.core.resource.java.GenerationType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.GeneratedValue
 */
public final class SourceGeneratedValueAnnotation
	extends SourceAnnotation<Member>
	implements GeneratedValueAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> STRATEGY_ADAPTER = buildStrategyAdapter();
	private final AnnotationElementAdapter<String> strategyAdapter;
	private GenerationType strategy;

	private static final DeclarationAnnotationElementAdapter<String> GENERATOR_ADAPTER = buildGeneratorAdapter();
	private final AnnotationElementAdapter<String> generatorAdapter;
	private String generator;
	
		
	public SourceGeneratedValueAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.strategyAdapter = new AnnotatedElementAnnotationElementAdapter<String>(attribute, STRATEGY_ADAPTER);
		this.generatorAdapter = new AnnotatedElementAnnotationElementAdapter<String>(attribute, GENERATOR_ADAPTER);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.strategy = this.buildStrategy(astRoot);
		this.generator = this.buildGenerator(astRoot);
	}
	
	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncStrategy(this.buildStrategy(astRoot));
		this.syncGenerator(this.buildGenerator(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.strategy);
	}


	// ********** GeneratedValueAnnotation implementation **********

	// ***** strategy
	public GenerationType getStrategy() {
		return this.strategy;
	}
	
	public void setStrategy(GenerationType strategy) {
		if (this.attributeValueHasChanged(this.strategy, strategy)) {
			this.strategy = strategy;
			this.strategyAdapter.setValue(GenerationType.toJavaAnnotationValue(strategy));
		}
	}
	
	private void syncStrategy(GenerationType astStrategy) {
		GenerationType old = this.strategy;
		this.strategy = astStrategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, astStrategy);
	}
	
	private GenerationType buildStrategy(CompilationUnit astRoot) {
		return GenerationType.fromJavaAnnotationValue(this.strategyAdapter.getValue(astRoot));
	}
	
	public TextRange getStrategyTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(STRATEGY_ADAPTER, astRoot);
	}
	
	// ***** generator
	public String getGenerator() {
		return this.generator;
	}
	
	public void setGenerator(String generator) {
		if (this.attributeValueHasChanged(this.generator, generator)) {
			this.generator = generator;
			this.generatorAdapter.setValue(generator);
		}
	}

	private void syncGenerator(String astGenerator) {
		String old = this.generator;
		this.generator = astGenerator;
		this.firePropertyChanged(GENERATOR_PROPERTY, old, astGenerator);
	}

	private String buildGenerator(CompilationUnit astRoot) {
		return this.generatorAdapter.getValue(astRoot);
	}
	
	public TextRange getGeneratorTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(GENERATOR_ADAPTER, astRoot);
	}

	public boolean generatorTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(GENERATOR_ADAPTER, pos, astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildStrategyAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.GENERATED_VALUE__STRATEGY, false);
	}

	private static DeclarationAnnotationElementAdapter<String> buildGeneratorAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.GENERATED_VALUE__GENERATOR, false);
	}

}
