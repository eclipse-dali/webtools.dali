/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.binary;

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.resource.java.ConversionValueAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * org.eclipse.persistence.annotations.ObjectTypeConverter
 */
public final class BinaryObjectTypeConverterAnnotation
	extends BinaryBaseTypeConverterAnnotation
	implements ObjectTypeConverterAnnotation
{
	private String defaultObjectValue;
	private final Vector<ConversionValueAnnotation> conversionValues;


	public BinaryObjectTypeConverterAnnotation(JavaResourcePersistentMember parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.defaultObjectValue = this.buildDefaultObjectValue();
		this.conversionValues = this.buildConversionValues();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultObjectValue_(this.buildDefaultObjectValue());
		this.updateConversionValues();
	}


	// ********** BinaryNamedConverterAnnotation implementation **********

	@Override
	String getNameElementName() {
		return EclipseLinkJPA.OBJECT_TYPE_CONVERTER__NAME;
	}

	// ********** BinaryBaseTypeConverterAnnotation implementation **********

	@Override
	String getDataTypeElementName() {
		return EclipseLinkJPA.OBJECT_TYPE_CONVERTER__DATA_TYPE;
	}

	@Override
	String getObjectTypeElementName() {
		return EclipseLinkJPA.OBJECT_TYPE_CONVERTER__OBJECT_TYPE;
	}

	// ********** ObjectTypeConverterAnnotation implementation **********

	// ***** default object value
	public String getDefaultObjectValue() {
		return this.defaultObjectValue;
	}

	public void setDefaultObjectValue(String defaultObjectValue) {
		throw new UnsupportedOperationException();
	}

	private void setDefaultObjectValue_(String defaultObjectValue) {
		String old = this.defaultObjectValue;
		this.defaultObjectValue = defaultObjectValue;
		this.firePropertyChanged(DEFAULT_OBJECT_VALUE_PROPERTY, old, defaultObjectValue);
	}

	private String buildDefaultObjectValue() {
		return (String) this.getJdtMemberValue(EclipseLinkJPA.OBJECT_TYPE_CONVERTER__DEFAULT_OBJECT_VALUE);
	}

	public TextRange getDefaultObjectValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** conversion values
	public ListIterator<ConversionValueAnnotation> conversionValues() {
		return new CloneListIterator<ConversionValueAnnotation>(this.conversionValues);
	}

	public int conversionValuesSize() {
		return this.conversionValues.size();
	}

	public ConversionValueAnnotation conversionValueAt(int index) {
		return this.conversionValues.get(index);
	}

	public int indexOfConversionValue(ConversionValueAnnotation conversionValue) {
		return this.conversionValues.indexOf(conversionValue);
	}

	public ConversionValueAnnotation addConversionValue(int index) {
		throw new UnsupportedOperationException();
	}

	public void moveConversionValue(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeConversionValue(int index) {
		throw new UnsupportedOperationException();
	}

	private Vector<ConversionValueAnnotation> buildConversionValues() {
		Object[] jdtConversionValues = this.getJdtMemberValues(EclipseLinkJPA.OBJECT_TYPE_CONVERTER__CONVERSION_VALUES);
		Vector<ConversionValueAnnotation> result = new Vector<ConversionValueAnnotation>(jdtConversionValues.length);
		for (Object jdtConversionValue : jdtConversionValues) {
			result.add(new BinaryConversionValueAnnotation(this, (IAnnotation) jdtConversionValue));
		}
		return result;
	}

	// TODO
	private void updateConversionValues() {
		throw new UnsupportedOperationException();
	}

}
