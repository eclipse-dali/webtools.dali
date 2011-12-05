/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.EnumType;
import org.eclipse.jpt.jpa.core.context.EnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEnumeratedConverter;

public class GenericOrmEnumeratedConverter
	extends AbstractOrmConverter
	implements OrmEnumeratedConverter
{
	protected EnumType specifiedEnumType;
	protected EnumType defaultEnumType;


	public GenericOrmEnumeratedConverter(OrmAttributeMapping parent,  OrmEnumeratedConverter.Owner owner) {
		super(parent, owner);
		this.specifiedEnumType = this.buildSpecifiedEnumType();
	}

	@Override
	protected OrmEnumeratedConverter.Owner getOwner() {
		return (OrmEnumeratedConverter.Owner) super.getOwner();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedEnumType_(this.buildSpecifiedEnumType());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultEnumType(this.buildDefaultEnumType());
	}


	// ********** enum type **********

	public EnumType getEnumType() {
		return (this.specifiedEnumType != null) ? this.specifiedEnumType : this.getDefaultEnumType();
	}

	public EnumType getSpecifiedEnumType() {
		return this.specifiedEnumType;
	}

	public void setSpecifiedEnumType(EnumType enumType) {
		this.setSpecifiedEnumType_(enumType);
		this.setXmlEnumerated(enumType);
	}

	protected void setSpecifiedEnumType_(EnumType enumType) {
		EnumType old = this.specifiedEnumType;
		this.specifiedEnumType = enumType;
		this.firePropertyChanged(SPECIFIED_ENUM_TYPE_PROPERTY, old, enumType);
	}

	protected void setXmlEnumerated(EnumType enumType) {
		this.getOwner().setXmlEnumType(EnumType.toOrmResourceModel(enumType));
	}

	protected EnumType buildSpecifiedEnumType() {
		return EnumType.fromOrmResourceModel(this.getOwner().getXmlEnumType());
	}

	public EnumType getDefaultEnumType() {
		return this.defaultEnumType;
	}

	protected void setDefaultEnumType(EnumType enumType) {
		EnumType old = this.defaultEnumType;
		this.defaultEnumType = enumType;
		this.firePropertyChanged(DEFAULT_ENUM_TYPE_PROPERTY, old, enumType);
	}

	/**
	 * There is no default enum type in XML.
	 * If you specify the enumerated element, you must
	 * specify either {@link EnumType#ORDINAL} or
	 * {@link EnumType#STRING}.
	 */
	protected EnumType buildDefaultEnumType() {
		return null;
	}


	// ********** misc **********

	public Class<? extends Converter> getType() {
		return EnumeratedConverter.class;
	}

	public void initialize() {
		this.specifiedEnumType = DEFAULT_ENUM_TYPE;
		this.setXmlEnumerated(this.specifiedEnumType);
	}


	// ********** validation **********

	@Override
	protected TextRange getXmlValidationTextRange() {
		return this.getOwner().getEnumTextRange();
	}
}
