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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.GeneratedValue;
import org.eclipse.jpt.core.context.GenerationType;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.core.utility.TextRange;


public class GenericJavaGeneratedValue extends AbstractJavaJpaContextNode implements JavaGeneratedValue
{
	protected GenerationType strategy;

	protected String generator;

	protected String defaultGenerator;
	
	protected GeneratedValueAnnotation generatedValueResource;
	
	public GenericJavaGeneratedValue(JavaAttributeMapping parent) {
		super(parent);
	}

	public void initialize(GeneratedValueAnnotation generatedValue) {
		this.generatedValueResource = generatedValue;
		this.strategy = this.strategy(generatedValue);
		this.generator = this.generator(generatedValue);
	}
	
	public GenerationType getStrategy() {
		return (this.getSpecifiedStrategy() == null) ? this.getDefaultStrategy() : this.getSpecifiedStrategy();
	}

	public GenerationType getDefaultStrategy() {
		return GeneratedValue.DEFAULT_STRATEGY;
	}
	
	public GenerationType getSpecifiedStrategy() {
		return this.strategy;
	}

	public void setSpecifiedStrategy(GenerationType newStrategy) {
		GenerationType oldStrategy = this.strategy;
		this.strategy = newStrategy;
		this.generatedValueResource.setStrategy(GenerationType.toJavaResourceModel(newStrategy));
		firePropertyChanged(GeneratedValue.SPECIFIED_STRATEGY_PROPERTY, oldStrategy, newStrategy);
	}

	protected void setSpecifiedStrategy_(GenerationType newStrategy) {
		GenerationType oldStrategy = this.strategy;
		this.strategy = newStrategy;
		firePropertyChanged(GeneratedValue.SPECIFIED_STRATEGY_PROPERTY, oldStrategy, newStrategy);
	}

	public String getGenerator() {
		return (this.getSpecifiedGenerator() == null) ? this.getDefaultGenerator() : this.getSpecifiedGenerator();
	}

	public String getSpecifiedGenerator() {
		return this.generator;
	}

	public String getDefaultGenerator() {
		return this.defaultGenerator;
	}
	
	public void setSpecifiedGenerator(String newGenerator) {
		String oldGenerator = this.generator;
		this.generator = newGenerator;
		this.generatedValueResource.setGenerator(newGenerator);
		firePropertyChanged(GeneratedValue.SPECIFIED_GENERATOR_PROPERTY, oldGenerator, newGenerator);
	}

	protected void setSpecifiedGenerator_(String newGenerator) {
		String oldGenerator = this.generator;
		this.generator = newGenerator;
		firePropertyChanged(GeneratedValue.SPECIFIED_GENERATOR_PROPERTY, oldGenerator, newGenerator);
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.generatedValueResource.getTextRange(astRoot);
	}

	public TextRange getGeneratorTextRange(CompilationUnit astRoot) {
		return this.generatedValueResource.getGeneratorTextRange(astRoot);
	}

	// ********** resource model -> java context model **********

	public void update(GeneratedValueAnnotation generatedValue) {
		this.generatedValueResource = generatedValue;
		this.setSpecifiedStrategy_(this.strategy(generatedValue));
		this.setSpecifiedGenerator_(this.generator(generatedValue));
	}

	protected GenerationType strategy(GeneratedValueAnnotation generatedValue) {
		return GenerationType.fromJavaResourceModel(generatedValue.getStrategy());
	}
	
	protected String generator(GeneratedValueAnnotation generatedValue) {
		return generatedValue.getGenerator();
	}
}
