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
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.core.resource.java.InheritanceType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Type;

/**
 * javax.persistence.Inheritance
 */
public final class SourceInheritanceAnnotation
	extends SourceAnnotation<Type>
	implements InheritanceAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> STRATEGY_ADAPTER = buildStrategyAdapter();
	private final AnnotationElementAdapter<String> strategyAdapter;
	private InheritanceType strategy;


	public SourceInheritanceAnnotation(JavaResourcePersistentType parent, Type type) {
		super(parent, type, DECLARATION_ANNOTATION_ADAPTER);
		this.strategyAdapter = new AnnotatedElementAnnotationElementAdapter<String>(type, STRATEGY_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.strategy = this.buildStrategy(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncStrategy(this.buildStrategy(astRoot));
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

	private InheritanceType buildStrategy(CompilationUnit astRoot) {
		return InheritanceType.fromJavaAnnotationValue(this.strategyAdapter.getValue(astRoot));
	}

	public TextRange getStrategyTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(STRATEGY_ADAPTER, astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildStrategyAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.INHERITANCE__STRATEGY);
	}

}
