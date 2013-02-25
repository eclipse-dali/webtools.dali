/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.resource.java.GeneratorAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java sequence or table generator
 */
public abstract class AbstractJavaGenerator<A extends GeneratorAnnotation>
	extends AbstractJavaJpaContextModel
	implements JavaGenerator
{
	protected final A generatorAnnotation;

	protected String name;


	protected AbstractJavaGenerator(JavaGeneratorContainer parent, A generatorAnnotation) {
		super(parent);
		this.generatorAnnotation = generatorAnnotation;
		this.name = generatorAnnotation.getName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.generatorAnnotation.getName());
	}
	

	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.generatorAnnotation.setName(name);
		this.setName_(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	
	// ********** validation **********

	public boolean supportsValidationMessages() {
		return MappingTools.nodeIsInternalSource(this, this.getGeneratorAnnotation());
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		if (StringTools.isBlank(this.name)){
			messages.add(
				this.buildValidationMessage(
					this.getNameTextRange(),
					JptJpaCoreValidationMessages.GENERATOR_NAME_UNDEFINED
				)
			);
		}
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.generatorAnnotation.getTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.generatorAnnotation.getNameTextRange());
	}

	
	public boolean isEquivalentTo(JpaNamedContextModel node) {
		return (this != node) &&
				(this.getType() == node.getType()) &&
				this.isEquivalentTo((Generator) node);
	}
	
	protected boolean isEquivalentTo(Generator generator) {
		return ObjectTools.equals(this.name, generator.getName());
	}


	// ********** misc **********

	@Override
	public JavaGeneratorContainer getParent() {
		return (JavaGeneratorContainer) super.getParent();
	}

	public A getGeneratorAnnotation() {
		return this.generatorAnnotation;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
