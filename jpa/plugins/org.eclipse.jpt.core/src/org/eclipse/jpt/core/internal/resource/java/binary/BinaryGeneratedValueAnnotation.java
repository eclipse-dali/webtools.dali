/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.core.resource.java.GenerationType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;

/**
 * javax.persistence.GeneratedValue
 */
public final class BinaryGeneratedValueAnnotation
	extends BinaryAnnotation
	implements GeneratedValueAnnotation
{
	private GenerationType strategy;
	private String generator;


	public BinaryGeneratedValueAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.strategy = this.buildStrategy();
		this.generator = this.buildGenerator();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setStrategy_(this.buildStrategy());
		this.setGenerator_(this.buildGenerator());
	}


	// ********** GeneratedValueAnnotation implementation **********

	// ***** strategy
	public GenerationType getStrategy() {
		return this.strategy;
	}
	
	public void setStrategy(GenerationType strategy) {
		throw new UnsupportedOperationException();
	}
	
	private void setStrategy_(GenerationType strategy) {
		GenerationType old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, strategy);
	}
	
	private GenerationType buildStrategy() {
		return GenerationType.fromJavaAnnotationValue(this.getJdtMemberValue(JPA.GENERATED_VALUE__STRATEGY));
	}
	
	public TextRange getStrategyTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	// ***** generator
	public String getGenerator() {
		return this.generator;
	}
	
	public void setGenerator(String generator) {
		throw new UnsupportedOperationException();
	}

	private void setGenerator_(String generator) {
		String old = this.generator;
		this.generator = generator;
		this.firePropertyChanged(GENERATOR_PROPERTY, old, generator);
	}

	private String buildGenerator() {
		return (String) this.getJdtMemberValue(JPA.GENERATED_VALUE__GENERATOR);
	}
	
	public TextRange getGeneratorTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean generatorTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
