/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.EnumType;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.resource.java.BaseEnumeratedAnnotation;

public class GenericJavaBaseEnumeratedConverter
	extends AbstractJavaConverter
	implements JavaBaseEnumeratedConverter
{
	protected final BaseEnumeratedAnnotation enumeratedAnnotation;

	protected EnumType specifiedEnumType;
	protected EnumType defaultEnumType;


	public GenericJavaBaseEnumeratedConverter(Converter.ParentAdapter<JavaAttributeMapping> parentAdapter, BaseEnumeratedAnnotation enumeratedAnnotation) {
		super(parentAdapter);
		this.enumeratedAnnotation = enumeratedAnnotation;
		this.specifiedEnumType = this.buildSpecifiedEnumType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedEnumType_(this.buildSpecifiedEnumType());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultEnumType(this.buildDefaultEnumType());
	}


	// ********** enum type **********

	public EnumType getEnumType() {
		return (this.specifiedEnumType != null) ? this.specifiedEnumType : this.defaultEnumType;
	}

	public EnumType getSpecifiedEnumType() {
		return this.specifiedEnumType;
	}

	public void setSpecifiedEnumType(EnumType enumType) {
		this.enumeratedAnnotation.setValue(EnumType.toJavaResourceModel(enumType));
		this.setSpecifiedEnumType_(enumType);
	}

	protected void setSpecifiedEnumType_(EnumType enumType) {
		EnumType old = this.specifiedEnumType;
		this.specifiedEnumType = enumType;
		this.firePropertyChanged(SPECIFIED_ENUM_TYPE_PROPERTY, old, enumType);
	}

	protected EnumType buildSpecifiedEnumType() {
		return EnumType.fromJavaResourceModel(this.enumeratedAnnotation.getValue());
	}

	public EnumType getDefaultEnumType() {
		return this.defaultEnumType;
	}

	protected void setDefaultEnumType(EnumType enumType) {
		EnumType old = this.defaultEnumType;
		this.defaultEnumType = enumType;
		this.firePropertyChanged(DEFAULT_ENUM_TYPE_PROPERTY, old, enumType);
	}

	protected EnumType buildDefaultEnumType() {
		return DEFAULT_ENUM_TYPE;
	}


	// ********** misc **********

	public Class<BaseEnumeratedConverter> getConverterType() {
		return BaseEnumeratedConverter.class;
	}

	public BaseEnumeratedAnnotation getConverterAnnotation() {
		return this.enumeratedAnnotation;
	}
}
