/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.GenerationType;
import org.eclipse.jpt.core.internal.context.base.IGeneratedValue;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValue;


public class JavaGeneratedValue extends JavaContextModel implements IJavaGeneratedValue
{
	protected GenerationType strategy;

	protected String generator;

	protected String defaultGenerator;
	
	protected GeneratedValue generatedValueResource;
	
	public JavaGeneratedValue(IJavaAttributeMapping parent) {
		super(parent);
	}

	public void initializeFromResource(GeneratedValue generatedValue) {
		this.generatedValueResource = generatedValue;
		this.strategy = this.strategy(generatedValue);
		this.generator = this.generator(generatedValue);
	}
	
	public GenerationType getStrategy() {
		return (this.getSpecifiedStrategy() == null) ? this.getDefaultStrategy() : this.getSpecifiedStrategy();
	}

	public GenerationType getDefaultStrategy() {
		return IGeneratedValue.DEFAULT_STRATEGY;
	}
	
	public GenerationType getSpecifiedStrategy() {
		return this.strategy;
	}

	public void setSpecifiedStrategy(GenerationType newStrategy) {
		GenerationType oldStrategy = this.strategy;
		this.strategy = newStrategy;
		this.generatedValueResource.setStrategy(GenerationType.toJavaResourceModel(newStrategy));
		firePropertyChanged(IGeneratedValue.SPECIFIED_STRATEGY_PROPERTY, oldStrategy, newStrategy);
	}

	protected void setSpecifiedStrategy_(GenerationType newStrategy) {
		GenerationType oldStrategy = this.strategy;
		this.strategy = newStrategy;
		firePropertyChanged(IGeneratedValue.SPECIFIED_STRATEGY_PROPERTY, oldStrategy, newStrategy);
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
		firePropertyChanged(IGeneratedValue.SPECIFIED_GENERATOR_PROPERTY, oldGenerator, newGenerator);
	}

	protected void setSpecifiedGenerator_(String newGenerator) {
		String oldGenerator = this.generator;
		this.generator = newGenerator;
		firePropertyChanged(IGeneratedValue.SPECIFIED_GENERATOR_PROPERTY, oldGenerator, newGenerator);
	}
	
	public ITextRange validationTextRange(CompilationUnit astRoot) {
		return null;//TODO //this.member.annotationTextRange(DECLARATION_ANNOTATION_ADAPTER);
	}

	public ITextRange generatorTextRange(CompilationUnit astRoot) {
		return this.generatedValueResource.generatorTextRange(astRoot);
	}

	// ********** resource model -> java context model **********

	public void update(GeneratedValue generatedValue) {
		this.generatedValueResource = generatedValue;
		this.setSpecifiedStrategy_(this.strategy(generatedValue));
		this.setSpecifiedGenerator_(this.generator(generatedValue));
	}

	protected GenerationType strategy(GeneratedValue generatedValue) {
		return GenerationType.fromJavaResourceModel(generatedValue.getStrategy());
	}
	
	protected String generator(GeneratedValue generatedValue) {
		return generatedValue.getGenerator();
	}
}
