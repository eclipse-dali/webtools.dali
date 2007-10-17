/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class GeneratedValueImpl extends AbstractAnnotationResource<Member> implements GeneratedValue
{
	private static final String ANNOTATION_NAME = JPA.GENERATED_VALUE;

	private final AnnotationElementAdapter<String> strategyAdapter;

	private final AnnotationElementAdapter<String> generatorAdapter;

	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> STRATEGY_ADAPTER = buildStrategyAdapter();

	private static final DeclarationAnnotationElementAdapter<String> GENERATOR_ADAPTER = buildGeneratorAdapter();

	private GenerationType strategy;

	private String generator;
	
		
	protected GeneratedValueImpl(JavaResource parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.strategyAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, STRATEGY_ADAPTER);
		this.generatorAdapter = new ShortCircuitAnnotationElementAdapter<String>(member, GENERATOR_ADAPTER);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public GenerationType getStrategy() {
		return this.strategy;
	}
	
	public void setStrategy(GenerationType strategy) {
		this.strategy = strategy;
		this.strategyAdapter.setValue(GenerationType.toJavaAnnotationValue(strategy));
		
	}
	public String getGenerator() {
		return this.generator;
	}
	
	public void setGenerator(String generator) {
		this.generator = generator;
		this.generatorAdapter.setValue(generator);
	}

	// ********** java annotations -> persistence model **********
	public void updateFromJava(CompilationUnit astRoot) {
		setStrategy(GenerationType.fromJavaAnnotationValue(this.strategyAdapter.getValue(astRoot)));
		setGenerator(this.generatorAdapter.getValue(astRoot));
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

		public Annotation buildAnnotation(JavaResource parent, Member member) {
			return new GeneratedValueImpl(parent, member);
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
