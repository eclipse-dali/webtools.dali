/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class OrmEclipseLinkConverter<X extends XmlNamedConverter>
	extends AbstractOrmXmlContextModel
	implements EclipseLinkConverter, TypeRefactoringParticipant
{
	protected final X xmlConverter;

	protected String name;


	protected OrmEclipseLinkConverter(JpaContextModel parent, X xmlConverter) {
		super(parent);
		this.xmlConverter = xmlConverter;
		this.name = xmlConverter.getName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.xmlConverter.getName());
	}

	@Override
	public void update() {
		super.update();
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.setName_(name);
		this.xmlConverter.setName(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** misc **********

	public X getXmlConverter() {
		return this.xmlConverter;
	}

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}

	public char getEnclosingTypeSeparator() {
		return '$';
	}


	// ********** validation **********

	public boolean supportsValidationMessages() {
		return true;
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateName(messages);
	}

	protected void validateName(List<IMessage> messages) {
		if (StringTools.isBlank(this.name)) {
			messages.add(
				this.buildValidationMessage(
					this.getNameTextRange(),
					JptJpaEclipseLinkCoreValidationMessages.CONVERTER_NAME_UNDEFINED
				)
			);
			return;
		}

		if (ArrayTools.contains(EclipseLinkConvert.RESERVED_CONVERTER_NAMES, this.name)) {
			messages.add(
				this.buildValidationMessage(
					this.getNameTextRange(),
					JptJpaEclipseLinkCoreValidationMessages.RESERVED_CONVERTER_NAME
				)
			);
		}
	}
	
	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlConverter.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.xmlConverter.getNameTextRange());
	}

	public boolean isEquivalentTo(JpaNamedContextModel node) {
		return (this != node) &&
				(this.getType() == node.getType()) &&
				ObjectTools.equals(this.name, node.getName());
	}

	// ********** metadata conversion **********

	public void convertFrom(JavaEclipseLinkConverter<?> javaConverter) {
		this.setName(javaConverter.getName());
	}
}
