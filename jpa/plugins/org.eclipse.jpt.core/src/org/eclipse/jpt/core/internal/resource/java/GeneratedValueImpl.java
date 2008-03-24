/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.core.resource.java.GenerationType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

public class GeneratedValueImpl extends AbstractResourceAnnotation<Member> implements GeneratedValueAnnotation
{
	private final AnnotationElementAdapter<String> strategyAdapter;

	private final AnnotationElementAdapter<String> generatorAdapter;

	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> STRATEGY_ADAPTER = buildStrategyAdapter();

	private static final DeclarationAnnotationElementAdapter<String> GENERATOR_ADAPTER = buildGeneratorAdapter();

	private GenerationType strategy;

	private String generator;
	
		
	protected GeneratedValueImpl(JavaResourceNode parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.strategyAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, STRATEGY_ADAPTER);
		this.generatorAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, GENERATOR_ADAPTER);
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.strategy = this.strategy(astRoot);
		this.generator = this.generator(astRoot);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public GenerationType getStrategy() {
		return this.strategy;
	}
	
	public void setStrategy(GenerationType newStrategy) {
		GenerationType oldStrategy = this.strategy;
		this.strategy = newStrategy;
		this.strategyAdapter.setValue(GenerationType.toJavaAnnotationValue(newStrategy));
		firePropertyChanged(STRATEGY_PROPERTY, oldStrategy, newStrategy);
	}
	
	public String getGenerator() {
		return this.generator;
	}
	
	public void setGenerator(String newGenerator) {
		String oldGenerator = this.generator;
		this.generator = newGenerator;
		this.generatorAdapter.setValue(newGenerator);
		firePropertyChanged(GENERATOR_PROPERTY, oldGenerator, newGenerator);
	}

	public TextRange strategyTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(STRATEGY_ADAPTER, astRoot);
	}
	
	public TextRange generatorTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(GENERATOR_ADAPTER, astRoot);
	}

	// ********** java annotations -> persistence model **********
	public void updateFromJava(CompilationUnit astRoot) {
		this.setStrategy(this.strategy(astRoot));
		this.setGenerator(this.generator(astRoot));
	}

	protected GenerationType strategy(CompilationUnit astRoot) {
		return GenerationType.fromJavaAnnotationValue(this.strategyAdapter.getValue(astRoot));
	}
	
	protected String generator(CompilationUnit astRoot) {
		return this.generatorAdapter.getValue(astRoot);
	}
	
	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildStrategyAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.GENERATED_VALUE__STRATEGY, false);
	}

	private static DeclarationAnnotationElementAdapter<String> buildGeneratorAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.GENERATED_VALUE__GENERATOR, false);
	}
	
	public static class GeneratedValueAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final GeneratedValueAnnotationDefinition INSTANCE = new GeneratedValueAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private GeneratedValueAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return new GeneratedValueImpl(parent, member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}
		
		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
