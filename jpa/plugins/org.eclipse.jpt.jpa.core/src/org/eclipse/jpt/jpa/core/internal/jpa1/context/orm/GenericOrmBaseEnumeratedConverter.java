/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.EnumType;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseEnumeratedConverter;

public class GenericOrmBaseEnumeratedConverter
	extends AbstractOrmConverter<OrmBaseEnumeratedConverter.ParentAdapter>
	implements OrmBaseEnumeratedConverter
{
	protected EnumType specifiedEnumType;


	public GenericOrmBaseEnumeratedConverter(OrmBaseEnumeratedConverter.ParentAdapter parentAdapter) {
		super(parentAdapter);
		this.specifiedEnumType = this.buildSpecifiedEnumType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedEnumType_(this.buildSpecifiedEnumType());
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
		this.parentAdapter.setXmlEnumType(EnumType.toOrmResourceModel(enumType));
	}

	protected EnumType buildSpecifiedEnumType() {
		return EnumType.fromOrmResourceModel(this.parentAdapter.getXmlEnumType());
	}

	/**
	 * There is no default enum type in XML.
	 * If you specify the enumerated element, you must
	 * specify either {@link EnumType#ORDINAL} or
	 * {@link EnumType#STRING}.
	 */
	public EnumType getDefaultEnumType() {
		return null;
	}


	// ********** misc **********

	public Class<BaseEnumeratedConverter> getConverterType() {
		return BaseEnumeratedConverter.class;
	}

	public void initialize() {
		this.specifiedEnumType = DEFAULT_ENUM_TYPE;
		this.setXmlEnumerated(this.specifiedEnumType);
	}


	// ********** validation **********

	@Override
	protected TextRange getXmlValidationTextRange() {
		return this.parentAdapter.getEnumTextRange();
	}
}
