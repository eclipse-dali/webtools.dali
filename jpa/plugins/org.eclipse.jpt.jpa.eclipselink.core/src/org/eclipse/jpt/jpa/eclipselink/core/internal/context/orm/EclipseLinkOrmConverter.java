/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class EclipseLinkOrmConverter<X extends XmlNamedConverter>
	extends AbstractOrmXmlContextModel<JpaContextModel>
	implements EclipseLinkConverter, TypeRefactoringParticipant
{
	protected final X xmlConverter;

	protected String name;


	protected EclipseLinkOrmConverter(JpaContextModel parent, X xmlConverter) {
		super(parent);
		this.xmlConverter = xmlConverter;
		this.name = xmlConverter.getName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setName_(this.xmlConverter.getName());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
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
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.xmlConverter.getNameTextRange());
	}

	public boolean isEquivalentTo(EclipseLinkConverter other) {
		return (this != other) &&
				(this.getConverterType() == other.getConverterType()) &&
				this.isEquivalentTo_(other);
	}

	protected boolean isEquivalentTo_(EclipseLinkConverter other) {
		return ObjectTools.equals(this.name, other.getName());
	}

	// ********** metadata conversion **********

	public void convertFrom(EclipseLinkJavaConverter<?> javaConverter) {
		this.setName(javaConverter.getName());
	}
}
