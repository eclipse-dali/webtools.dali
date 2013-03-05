/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.GenerationType;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> generated value
 */
public class GenericOrmGeneratedValue
	extends AbstractOrmXmlContextModel<JpaContextModel>
	implements OrmGeneratedValue
{
	protected final XmlGeneratedValue xmlGeneratedValue;

	protected GenerationType specifiedStrategy;
	protected GenerationType defaultStrategy;

	protected String specifiedGenerator;
	protected String defaultGenerator;


	public GenericOrmGeneratedValue(JpaContextModel parent, XmlGeneratedValue xmlGeneratedValue) {
		super(parent);
		this.xmlGeneratedValue = xmlGeneratedValue;
		this.specifiedStrategy = this.buildSpecifiedStrategy();
		this.specifiedGenerator = xmlGeneratedValue.getGenerator();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedStrategy_(this.buildSpecifiedStrategy());
		this.setSpecifiedGenerator_(this.xmlGeneratedValue.getGenerator());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultStrategy(this.buildDefaultStrategy());
		this.setDefaultGenerator(this.buildDefaultGenerator());
	}


	// ********** strategy **********

	public GenerationType getStrategy() {
		return (this.specifiedStrategy != null) ? this.specifiedStrategy : this.defaultStrategy;
	}

	public GenerationType getSpecifiedStrategy() {
		return this.specifiedStrategy;
	}

	public void setSpecifiedStrategy(GenerationType strategy) {
		this.setSpecifiedStrategy_(strategy);
		this.xmlGeneratedValue.setStrategy(GenerationType.toOrmResourceModel(strategy));
	}

	protected void setSpecifiedStrategy_(GenerationType strategy) {
		GenerationType old = this.specifiedStrategy;
		this.specifiedStrategy = strategy;
		this.firePropertyChanged(SPECIFIED_STRATEGY_PROPERTY, old, strategy);
	}

	protected GenerationType buildSpecifiedStrategy() {
		return GenerationType.fromOrmResourceModel(this.xmlGeneratedValue.getStrategy());
	}

	public GenerationType getDefaultStrategy() {
		return this.defaultStrategy;
	}

	protected void setDefaultStrategy(GenerationType strategy) {
		GenerationType old = this.defaultStrategy;
		this.defaultStrategy = strategy;
		this.firePropertyChanged(DEFAULT_STRATEGY_PROPERTY, old, strategy);
	}

	// TODO
	protected GenerationType buildDefaultStrategy() {
		return DEFAULT_STRATEGY;
	}


	// ********** generator **********

	public String getGenerator() {
		return (this.specifiedGenerator != null) ? this.specifiedGenerator : this.defaultGenerator;
	}

	public String getSpecifiedGenerator() {
		return this.specifiedGenerator;
	}

	public void setSpecifiedGenerator(String generator) {
		this.setSpecifiedGenerator_(generator);
		this.xmlGeneratedValue.setGenerator(generator);
	}

	protected void setSpecifiedGenerator_(String generator) {
		String old = this.specifiedGenerator;
		this.specifiedGenerator = generator;
		this.firePropertyChanged(SPECIFIED_GENERATOR_PROPERTY, old, generator);
	}

	public String getDefaultGenerator() {
		return this.defaultGenerator;
	}

	protected void setDefaultGenerator(String generator) {
		String old = this.defaultGenerator;
		this.defaultGenerator = generator;
		this.firePropertyChanged(DEFAULT_GENERATOR_PROPERTY, old, generator);
	}

	// TODO
	protected String buildDefaultGenerator() {
		return null;
	}


	// ********** misc **********

	public XmlGeneratedValue getXmlGeneratedValue() {
		return this.xmlGeneratedValue;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		String generator = this.getGenerator();
		if (generator == null) {
			return;
		}

		for (Generator next : this.getPersistenceUnit().getGenerators()) {
			if (generator.equals(next.getName())) {
				return;
			}
		}

		messages.add(
			this.buildValidationMessage(
				this.parent,
				this.getGeneratorTextRange(),
				JptJpaCoreValidationMessages.UNRESOLVED_GENERATOR_NAME,
				generator
			)
		);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlGeneratedValue.getValidationTextRange();
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}

	public TextRange getGeneratorTextRange() {
		return this.getValidationTextRange(this.xmlGeneratedValue.getGeneratorTextRange());
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.generatorTouches(pos)) {
			return this.getCandidateGeneratorNames();
		}
		return null;
	}

	protected boolean generatorTouches(int pos) {
		return this.xmlGeneratedValue.generatorTouches(pos);
	}

	protected Iterable<String> getCandidateGeneratorNames() {
		return this.getPersistenceUnit().getUniqueGeneratorNames();
	}
}
