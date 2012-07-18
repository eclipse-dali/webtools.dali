/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.InheritanceType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.Inheritance</code>
 */
public final class SourceInheritanceAnnotation
	extends SourceAnnotation
	implements InheritanceAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> STRATEGY_ADAPTER = buildStrategyAdapter();
	private final AnnotationElementAdapter<String> strategyAdapter;
	private InheritanceType strategy;
	private TextRange strategyTextRange;


	public SourceInheritanceAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.strategyAdapter = new AnnotatedElementAnnotationElementAdapter<String>(element, STRATEGY_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.strategy = this.buildStrategy(astAnnotation);
		this.strategyTextRange = this.buildStrategyTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncStrategy(this.buildStrategy(astAnnotation));
		this.strategyTextRange = this.buildStrategyTextRange(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.strategy == null);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.strategy);
	}


	// ********** InheritanceAnnotation implementation **********

	// ***** strategy
	public InheritanceType getStrategy() {
		return this.strategy;
	}

	public void setStrategy(InheritanceType strategy) {
		if (this.attributeValueHasChanged(this.strategy, strategy)) {
			this.strategy = strategy;
			this.strategyAdapter.setValue(InheritanceType.toJavaAnnotationValue(strategy));
		}
	}

	private void syncStrategy(InheritanceType astStrategy) {
		InheritanceType old = this.strategy;
		this.strategy = astStrategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, astStrategy);
	}

	private InheritanceType buildStrategy(Annotation astAnnotation) {
		return InheritanceType.fromJavaAnnotationValue(this.strategyAdapter.getValue(astAnnotation));
	}

	public TextRange getStrategyTextRange() {
		return this.strategyTextRange;
	}

	private TextRange buildStrategyTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(STRATEGY_ADAPTER, astAnnotation);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildStrategyAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.INHERITANCE__STRATEGY);
	}

}
