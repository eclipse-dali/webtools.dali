/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.BaseTypeConverterAnnotation;

/**
 * <code>
 * <ul>
 * <li>org.eclipse.persistence.annotations.TypeConverter
 * <li>org.eclipse.persistence.annotations.ObjectTypeConverter
 * </ul>
 * </code>
 */
abstract class BinaryEclipseLinkBaseTypeConverterAnnotation
	extends BinaryEclipseLinkNamedConverterAnnotation
	implements BaseTypeConverterAnnotation
{
	String dataType;
	String objectType;


	BinaryEclipseLinkBaseTypeConverterAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.dataType = this.buildDataType();
		this.objectType = this.buildObjectType();
	}

	@Override
	public void update() {
		super.update();
		this.setDataType_(this.buildDataType());
		this.setObjectType_(this.buildObjectType());
	}


	// ********** ObjectTypeConverterAnnotation implementation **********

	// ***** data type
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		throw new UnsupportedOperationException();
	}

	private void setDataType_(String dataType) {
		String old = this.dataType;
		this.dataType = dataType;
		this.firePropertyChanged(DATA_TYPE_PROPERTY, old, dataType);
	}

	public String getFullyQualifiedDataType() {
		return this.dataType;
	}

	private String buildDataType() {
		return (String) this.getJdtMemberValue(this.getDataTypeElementName());
	}

	public TextRange getDataTypeTextRange() {
		throw new UnsupportedOperationException();
	}

	abstract String getDataTypeElementName();

	// ***** object type
	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		throw new UnsupportedOperationException();
	}

	private void setObjectType_(String objectType) {
		String old = this.objectType;
		this.objectType = objectType;
		this.firePropertyChanged(OBJECT_TYPE_PROPERTY, old, objectType);
	}

	public String getFullyQualifiedObjectType() {
		return this.objectType;
	}

	private String buildObjectType() {
		return (String) this.getJdtMemberValue(this.getObjectTypeElementName());
	}

	public TextRange getObjectTypeTextRange() {
		throw new UnsupportedOperationException();
	}

	abstract String getObjectTypeElementName();
}
