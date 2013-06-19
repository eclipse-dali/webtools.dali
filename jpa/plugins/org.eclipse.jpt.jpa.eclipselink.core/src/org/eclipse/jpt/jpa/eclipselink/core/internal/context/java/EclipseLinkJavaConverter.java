/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.NamedConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>
 * <ul>
 * <li>org.eclipse.persistence.annotations.Converter
 * <li>org.eclipse.persistence.annotations.StructConverter
 * <li>org.eclipse.persistence.annotations.ObjectTypeConverter
 * <li>org.eclipse.persistence.annotations.TypeConverter
 * </ul>
 * </code>
 */
public abstract class EclipseLinkJavaConverter<A extends NamedConverterAnnotation>
	extends AbstractJavaContextModel<EclipseLinkJavaConverterContainer>
	implements EclipseLinkConverter
{
	protected final A converterAnnotation;

	protected String name;


	protected EclipseLinkJavaConverter(EclipseLinkJavaConverterContainer parent, A converterAnnotation) {
		super(parent);
		this.converterAnnotation = converterAnnotation;
		this.name = converterAnnotation.getName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.converterAnnotation.getName());
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
		this.converterAnnotation.setName(name);
		this.setName_(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** misc **********

	public A getConverterAnnotation() {
		return this.converterAnnotation;
	}

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}

	public char getEnclosingTypeSeparator() {
		return '.';
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** validation **********

	public boolean supportsValidationMessages() {
		return MappingTools.modelIsInternalSource(this, this.getConverterAnnotation());
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
		TextRange textRange = this.converterAnnotation.getTextRange();
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}
	
	public TextRange getNameTextRange(){
		return this.getValidationTextRange(this.getConverterAnnotation().getNameTextRange());
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

	public abstract void convertTo(EclipseLinkOrmConverterContainer ormConverterContainer);
	
	public abstract void delete();
}
