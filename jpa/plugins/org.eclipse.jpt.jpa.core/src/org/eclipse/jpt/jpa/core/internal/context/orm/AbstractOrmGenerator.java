/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlGenerator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> sequence or table generator
 */
public abstract class AbstractOrmGenerator<X extends XmlGenerator>
	extends AbstractOrmXmlContextModel<JpaContextModel>
	implements OrmGenerator
{
	protected final X xmlGenerator;

	protected String name;


	protected AbstractOrmGenerator(JpaContextModel parent, X xmlGenerator) {
		super(parent);
		this.xmlGenerator = xmlGenerator;
		this.name = xmlGenerator.getName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setName_(this.xmlGenerator.getName());
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.setName_(name);
		this.xmlGenerator.setName(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}



	// ********** validation **********

	public boolean supportsValidationMessages() {
		return true;
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
		TextRange textRange = this.xmlGenerator.getValidationTextRange();
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.xmlGenerator.getNameTextRange());
	}

	public boolean isEquivalentTo(Generator other) {
		return (this != other) &&
				(this.getGeneratorType() == other.getGeneratorType()) &&
				this.isEquivalentTo_(other);
	}
	
	protected boolean isEquivalentTo_(Generator other) {
		return ObjectTools.equals(this.name, other.getName());
	}


	// ********** metadata conversion **********
	
	public void convertFrom(JavaGenerator javaGenerator) {
		this.setName(javaGenerator.getName());
	}

	// ********** misc **********

	public X getXmlGenerator() {
		return this.xmlGenerator;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
