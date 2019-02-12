/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.GenerationType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.GeneratedValue</code>
 */
public final class SourceGeneratedValueAnnotation
	extends SourceAnnotation
	implements GeneratedValueAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> STRATEGY_ADAPTER = buildStrategyAdapter();
	private final AnnotationElementAdapter<String> strategyAdapter;
	private GenerationType strategy;
	private TextRange strategyTextRange;

	private static final DeclarationAnnotationElementAdapter<String> GENERATOR_ADAPTER = buildGeneratorAdapter();
	private final AnnotationElementAdapter<String> generatorAdapter;
	private String generator;
	private TextRange generatorTextRange;
	
		
	public SourceGeneratedValueAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.strategyAdapter = new AnnotatedElementAnnotationElementAdapter<String>(element, STRATEGY_ADAPTER);
		this.generatorAdapter = new AnnotatedElementAnnotationElementAdapter<String>(element, GENERATOR_ADAPTER);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.strategy = this.buildStrategy(astAnnotation);
		this.strategyTextRange = this.buildStrategyTextRange(astAnnotation);

		this.generator = this.buildGenerator(astAnnotation);
		this.generatorTextRange = this.buildGeneratorTextRange(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncStrategy(this.buildStrategy(astAnnotation));
		this.strategyTextRange = this.buildStrategyTextRange(astAnnotation);

		this.syncGenerator(this.buildGenerator(astAnnotation));
		this.generatorTextRange = this.buildGeneratorTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.strategy == null) &&
				(this.generator == null);
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
		if (ObjectTools.notEquals(this.strategy, strategy)) {
			this.strategy = strategy;
			this.strategyAdapter.setValue(GenerationType.toJavaAnnotationValue(strategy));
		}
	}
	
	private void syncStrategy(GenerationType astStrategy) {
		GenerationType old = this.strategy;
		this.strategy = astStrategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, astStrategy);
	}
	
	private GenerationType buildStrategy(Annotation astAnnotation) {
		return GenerationType.fromJavaAnnotationValue(this.strategyAdapter.getValue(astAnnotation));
	}
	
	public TextRange getStrategyTextRange() {
		return this.strategyTextRange;
	}
	
	private TextRange buildStrategyTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(STRATEGY_ADAPTER, astAnnotation);
	}
	
	// ***** generator
	public String getGenerator() {
		return this.generator;
	}
	
	public void setGenerator(String generator) {
		if (ObjectTools.notEquals(this.generator, generator)) {
			this.generator = generator;
			this.generatorAdapter.setValue(generator);
		}
	}

	private void syncGenerator(String astGenerator) {
		String old = this.generator;
		this.generator = astGenerator;
		this.firePropertyChanged(GENERATOR_PROPERTY, old, astGenerator);
	}

	private String buildGenerator(Annotation astAnnotation) {
		return this.generatorAdapter.getValue(astAnnotation);
	}
	
	public TextRange getGeneratorTextRange() {
		return this.generatorTextRange;
	}

	private TextRange buildGeneratorTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(GENERATOR_ADAPTER, astAnnotation);
	}

	public boolean generatorTouches(int pos) {
		return this.textRangeTouches(this.generatorTextRange, pos);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildStrategyAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.GENERATED_VALUE__STRATEGY);
	}

	private static DeclarationAnnotationElementAdapter<String> buildGeneratorAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.GENERATED_VALUE__GENERATOR);
	}
}
