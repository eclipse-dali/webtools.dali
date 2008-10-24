/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.GeneratedValue;
import org.eclipse.jpt.core.context.GenerationType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmGeneratedValue extends AbstractXmlContextNode implements OrmGeneratedValue
{

	protected GenerationType specifiedStrategy;

	protected String specifiedGenerator;
	protected String defaultGenerator;

	protected XmlGeneratedValue resourceGeneratedValue;
	
	public GenericOrmGeneratedValue(XmlContextNode parent, XmlGeneratedValue resourceGeneratedValue) {
		super(parent);
		this.initialize(resourceGeneratedValue);
	}
	
	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
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
		this.resourceGeneratedValue.setStrategy(GenerationType.toOrmResourceModel(newSpecifiedStrategy));
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
		this.resourceGeneratedValue.setGenerator(newSpecifiedGenerator);
		firePropertyChanged(SPECIFIED_GENERATOR_PROPERTY, oldGenerator, newSpecifiedGenerator);
	}

	protected void setSpecifiedGenerator_(String newSpecifiedGenerator) {
		String oldGenerator = this.specifiedGenerator;
		this.specifiedGenerator = newSpecifiedGenerator;
		firePropertyChanged(SPECIFIED_GENERATOR_PROPERTY, oldGenerator, newSpecifiedGenerator);
	}

	public TextRange getGeneratorTextRange() {
		TextRange textRange =  this.resourceGeneratedValue.getGeneratorTextRange();
		return textRange != null ? textRange : getValidationTextRange();
	}

	
	// ********** resource model -> java context model **********

	protected void initialize(XmlGeneratedValue resourceGeneratedValue) {
		this.resourceGeneratedValue = resourceGeneratedValue;
		this.specifiedStrategy = this.strategy(resourceGeneratedValue);
		this.specifiedGenerator = this.generator(resourceGeneratedValue);
		//TODO
		this.defaultGenerator = null;
	}

	public void update(XmlGeneratedValue resourceGeneratedValue) {
		this.resourceGeneratedValue = resourceGeneratedValue;
		this.setSpecifiedStrategy_(this.strategy(resourceGeneratedValue));
		this.setSpecifiedGenerator_(this.generator(resourceGeneratedValue));
		//TODO
		this.setDefaultGenerator(null);
	}

	protected GenerationType strategy(XmlGeneratedValue resourceGeneratedValue) {
		return GenerationType.fromOrmResourceModel(resourceGeneratedValue.getStrategy());
	}
	
	protected String generator(XmlGeneratedValue resourceGeneratedValue) {
		return resourceGeneratedValue.getGenerator();
	}
	
	public TextRange getValidationTextRange() {
		TextRange validationTextRange = this.resourceGeneratedValue.getValidationTextRange();
		return validationTextRange != null ? validationTextRange : getParent().getValidationTextRange();
	}
}
