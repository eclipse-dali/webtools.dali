/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.EnumType;
import org.eclipse.jpt.core.context.EnumeratedConverter;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaEnumeratedConverter;
import org.eclipse.jpt.core.resource.java.EnumeratedAnnotation;

public class GenericJavaEnumeratedConverter
	extends AbstractJavaConverter
	implements JavaEnumeratedConverter
{
	protected final EnumeratedAnnotation enumeratedAnnotation;

	protected EnumType specifiedEnumType;
	protected EnumType defaultEnumType;


	public GenericJavaEnumeratedConverter(JavaAttributeMapping parent, EnumeratedAnnotation enumeratedAnnotation) {
		super(parent);
		this.enumeratedAnnotation = enumeratedAnnotation;
		this.specifiedEnumType = this.buildSpecifiedEnumType();
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

	public Class<? extends Converter> getType() {
		return EnumeratedConverter.class;
	}

	@Override
	protected String getAnnotationName() {
		return EnumeratedAnnotation.ANNOTATION_NAME;
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.enumeratedAnnotation.getTextRange(astRoot);
	}
}
