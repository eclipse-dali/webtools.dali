/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.GenerationType;
import org.eclipse.jpt.core.internal.context.base.IGeneratedValue;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.GeneratedValue;

public class XmlGeneratedValue extends JpaContextNode implements IGeneratedValue
{

	protected GenerationType specifiedStrategy;

	protected String specifiedGenerator;
	protected String defaultGenerator;

	protected GeneratedValue generatedValue;
	
	protected XmlGeneratedValue(IJpaContextNode parent) {
		super(parent);
	}

	
	public GenerationType getStrategy() {
		return (this.getSpecifiedStrategy() == null) ? this.getDefaultStrategy() : this.getSpecifiedStrategy();
	}

	public GenerationType getDefaultStrategy() {
		return IGeneratedValue.DEFAULT_STRATEGY;
	}
	
	public GenerationType getSpecifiedStrategy() {
		return this.specifiedStrategy;
	}

	public void setSpecifiedStrategy(GenerationType newSpecifiedStrategy) {
		GenerationType oldStrategy = this.specifiedStrategy;
		this.specifiedStrategy = newSpecifiedStrategy;
		this.generatedValue.setStrategy(GenerationType.toOrmResourceModel(newSpecifiedStrategy));
		firePropertyChanged(SPECIFIED_STRATEGY_PROPERTY, oldStrategy, newSpecifiedStrategy);
	}

	public String getGenerator() {
		return (this.getSpecifiedGenerator() == null) ? this.getDefaultGenerator() : this.getSpecifiedGenerator();
	}

	public String getDefaultGenerator() {
		return this.defaultGenerator;
	}
	
	protected void setDefaultGenerator(String newDefaultGenerator) {
		String oldGenerator = this.defaultGenerator;
		this.defaultGenerator = newDefaultGenerator;
		firePropertyChanged(DEFAULT_GENERATOR_PROPERTY, oldGenerator, newDefaultGenerator);
	}

	public String getSpecifiedGenerator() {
		return this.specifiedGenerator;
	}

	public void setSpecifiedGenerator(String newSpecifiedGenerator) {
		String oldGenerator = this.specifiedGenerator;
		this.specifiedGenerator = newSpecifiedGenerator;
		this.generatedValue.setGenerator(newSpecifiedGenerator);
		firePropertyChanged(SPECIFIED_GENERATOR_PROPERTY, oldGenerator, newSpecifiedGenerator);
	}

	public ITextRange generatorTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
//	public ITextRange generatorTextRange() {
//		if (node == null) {
//			return ((XmlEObject) eContainer()).validationTextRange();
//		}
//		IDOMNode nameNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.GENERATED_VALUE__GENERATOR);
//		return (nameNode == null) ? validationTextRange() : buildTextRange(nameNode);
//	}
	
	// ********** resource model -> java context model **********

	public void initialize(GeneratedValue generatedValue) {
		this.generatedValue = generatedValue;
		this.specifiedStrategy = this.strategy(generatedValue);
		this.specifiedGenerator = this.generator(generatedValue);
		//TODO
		this.defaultGenerator = null;
	}

	public void update(GeneratedValue generatedValue) {
		this.generatedValue = generatedValue;
		this.setSpecifiedStrategy(this.strategy(generatedValue));
		this.setSpecifiedGenerator(this.generator(generatedValue));
		//TODO
		this.setDefaultGenerator(null);
	}

	protected GenerationType strategy(GeneratedValue generatedValue) {
		return GenerationType.fromOrmResourceModel(generatedValue.getStrategy());
	}
	
	protected String generator(GeneratedValue generatedValue) {
		return generatedValue.getGenerator();
	}
}
