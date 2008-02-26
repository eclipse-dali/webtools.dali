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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.GeneratedValue;
import org.eclipse.jpt.core.context.GenerationType;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;

public class GenericOrmGeneratedValue extends AbstractJpaContextNode implements OrmGeneratedValue
{

	protected GenerationType specifiedStrategy;

	protected String specifiedGenerator;
	protected String defaultGenerator;

	protected XmlGeneratedValue generatedValue;
	
	public GenericOrmGeneratedValue(JpaContextNode parent) {
		super(parent);
	}
	
	public GenerationType getStrategy() {
		return (this.getSpecifiedStrategy() == null) ? this.getDefaultStrategy() : this.getSpecifiedStrategy();
	}

	public GenerationType getDefaultStrategy() {
		return GeneratedValue.DEFAULT_STRATEGY;
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
	
	protected void setSpecifiedStrategy_(GenerationType newSpecifiedStrategy) {
		GenerationType oldStrategy = this.specifiedStrategy;
		this.specifiedStrategy = newSpecifiedStrategy;
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

	protected void setSpecifiedGenerator_(String newSpecifiedGenerator) {
		String oldGenerator = this.specifiedGenerator;
		this.specifiedGenerator = newSpecifiedGenerator;
		firePropertyChanged(SPECIFIED_GENERATOR_PROPERTY, oldGenerator, newSpecifiedGenerator);
	}

	public TextRange generatorTextRange(CompilationUnit astRoot) {
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

	public void initialize(XmlGeneratedValue generatedValue) {
		this.generatedValue = generatedValue;
		this.specifiedStrategy = this.strategy(generatedValue);
		this.specifiedGenerator = this.generator(generatedValue);
		//TODO
		this.defaultGenerator = null;
	}

	public void update(XmlGeneratedValue generatedValue) {
		this.generatedValue = generatedValue;
		this.setSpecifiedStrategy_(this.strategy(generatedValue));
		this.setSpecifiedGenerator_(this.generator(generatedValue));
		//TODO
		this.setDefaultGenerator(null);
	}

	protected GenerationType strategy(XmlGeneratedValue generatedValue) {
		return GenerationType.fromOrmResourceModel(generatedValue.getStrategy());
	}
	
	protected String generator(XmlGeneratedValue generatedValue) {
		return generatedValue.getGenerator();
	}
}
