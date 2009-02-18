/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.core.context.GeneratedValue;
import org.eclipse.jpt.core.context.GenerationType;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * 
 */
public class GenericOrmGeneratedValue
	extends AbstractXmlContextNode
	implements OrmGeneratedValue
{
	protected XmlGeneratedValue resourceGeneratedValue;
	
	protected GenerationType specifiedStrategy;

	protected String specifiedGenerator;
	protected String defaultGenerator;


	public GenericOrmGeneratedValue(XmlContextNode parent, XmlGeneratedValue resourceGeneratedValue) {
		super(parent);
		this.initialize(resourceGeneratedValue);
	}
	
	protected void initialize(XmlGeneratedValue xmlGeneratedValue) {
		this.resourceGeneratedValue = xmlGeneratedValue;
		this.specifiedStrategy = this.buildStrategy();
		this.specifiedGenerator = xmlGeneratedValue.getGenerator();
		//TODO
		this.defaultGenerator = null;
	}

	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}


	// ********** strategy **********

	public GenerationType getStrategy() {
		return (this.specifiedStrategy != null) ? this.specifiedStrategy : this.getDefaultStrategy();
	}

	public GenerationType getDefaultStrategy() {
		return GeneratedValue.DEFAULT_STRATEGY;
	}
	
	public GenerationType getSpecifiedStrategy() {
		return this.specifiedStrategy;
	}

	public void setSpecifiedStrategy(GenerationType strategy) {
		GenerationType old = this.specifiedStrategy;
		this.specifiedStrategy = strategy;
		this.resourceGeneratedValue.setStrategy(GenerationType.toOrmResourceModel(strategy));
		this.firePropertyChanged(SPECIFIED_STRATEGY_PROPERTY, old, strategy);
	}
	
	protected void setSpecifiedStrategy_(GenerationType strategy) {
		GenerationType old = this.specifiedStrategy;
		this.specifiedStrategy = strategy;
		this.firePropertyChanged(SPECIFIED_STRATEGY_PROPERTY, old, strategy);
	}


	// ********** generator **********

	public String getGenerator() {
		return (this.specifiedGenerator != null) ? this.specifiedGenerator : this.defaultGenerator;
	}

	public String getDefaultGenerator() {
		return this.defaultGenerator;
	}
	
	protected void setDefaultGenerator(String generator) {
		String old = this.defaultGenerator;
		this.defaultGenerator = generator;
		this.firePropertyChanged(DEFAULT_GENERATOR_PROPERTY, old, generator);
	}

	public String getSpecifiedGenerator() {
		return this.specifiedGenerator;
	}

	public void setSpecifiedGenerator(String generator) {
		String old = this.specifiedGenerator;
		this.specifiedGenerator = generator;
		this.resourceGeneratedValue.setGenerator(generator);
		this.firePropertyChanged(SPECIFIED_GENERATOR_PROPERTY, old, generator);
	}

	protected void setSpecifiedGenerator_(String generator) {
		String old = this.specifiedGenerator;
		this.specifiedGenerator = generator;
		this.firePropertyChanged(SPECIFIED_GENERATOR_PROPERTY, old, generator);
	}


	// ********** text ranges **********

	public TextRange getValidationTextRange() {
		TextRange validationTextRange = this.resourceGeneratedValue.getValidationTextRange();
		return validationTextRange != null ? validationTextRange : getParent().getValidationTextRange();
	}

	public TextRange getGeneratorTextRange() {
		TextRange textRange =  this.resourceGeneratedValue.getGeneratorTextRange();
		return textRange != null ? textRange : getValidationTextRange();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		String generator = this.getGenerator();
		if (generator == null) {
			return;
		}

		for (Iterator<Generator> stream = this.getPersistenceUnit().generators(); stream.hasNext(); ) {
			if (generator.equals(stream.next().getName())) {
				return;
			}
		}

		messages.add(
			DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.ID_MAPPING_UNRESOLVED_GENERATOR_NAME,
				new String[] {generator},
				this.getParent(),
				this.getGeneratorTextRange()
			)
		);
	}


	// ********** update from resource model **********

	public void update(XmlGeneratedValue xmlGeneratedValue) {
		this.resourceGeneratedValue = xmlGeneratedValue;
		this.setSpecifiedStrategy_(this.buildStrategy());
		this.setSpecifiedGenerator_(xmlGeneratedValue.getGenerator());
		//TODO
		this.setDefaultGenerator(null);
	}

	protected GenerationType buildStrategy() {
		return GenerationType.fromOrmResourceModel(this.resourceGeneratedValue.getStrategy());
	}
	
}
