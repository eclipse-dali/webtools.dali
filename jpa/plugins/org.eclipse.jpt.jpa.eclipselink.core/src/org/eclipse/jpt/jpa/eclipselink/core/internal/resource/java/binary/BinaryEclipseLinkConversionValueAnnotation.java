/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConversionValueAnnotation;

/**
 * org.eclipse.persistence.annotations.ConversionValue
 */
final class BinaryEclipseLinkConversionValueAnnotation
	extends BinaryAnnotation
	implements EclipseLinkConversionValueAnnotation
{
	private String dataValue;
	private String objectValue;


	BinaryEclipseLinkConversionValueAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.dataValue = this.buildDataValue();
		this.objectValue = this.buildObjectValue();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setDataValue_(this.buildDataValue());
		this.setObjectValue_(this.buildObjectValue());
	}


	// ********** ConversionValueAnnotation implementation **********

	// ***** data value
	public String getDataValue() {
		return this.dataValue;
	}

	public void setDataValue(String dataValue) {
		throw new UnsupportedOperationException();
	}

	private void setDataValue_(String dataValue) {
		String old = this.dataValue;
		this.dataValue = dataValue;
		this.firePropertyChanged(DATA_VALUE_PROPERTY, old, dataValue);
	}

	private String buildDataValue() {
		return (String) this.getJdtMemberValue(EclipseLink.CONVERSION_VALUE__DATA_VALUE);
	}

	public TextRange getDataValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** object value
	public String getObjectValue() {
		return this.objectValue;
	}

	public void setObjectValue(String objectValue) {
		throw new UnsupportedOperationException();
	}

	private void setObjectValue_(String objectValue) {
		String old = this.objectValue;
		this.objectValue = objectValue;
		this.firePropertyChanged(OBJECT_VALUE_PROPERTY, old, objectValue);
	}

	private String buildObjectValue() {
		return (String) this.getJdtMemberValue(EclipseLink.CONVERSION_VALUE__OBJECT_VALUE);
	}

	public TextRange getObjectValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
