/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAnyElementAnnotation;

/**
 * javax.xml.bind.annotation.XmlAnyElement
 */
public final class BinaryXmlAnyElementAnnotation
	extends BinaryAnnotation
	implements XmlAnyElementAnnotation
{
	private Boolean lax;
	private String value;


	public BinaryXmlAnyElementAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.lax = this.buildLax();
		this.value = this.buildValue();
	}

	public String getAnnotationName() {
		return JAXB.XML_ANY_ELEMENT;
	}

	@Override
	public void update() {
		super.update();
		this.setLax_(this.buildLax());
		this.setValue_(this.buildValue());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** XmlAnyElementAnnotation implementation **********

	// ***** lax
	public Boolean getLax() {
		return this.lax;
	}

	public void setLax(Boolean lax) {
		throw new UnsupportedOperationException();
	}

	private void setLax_(Boolean lax) {
		Boolean old = this.lax;
		this.lax = lax;
		this.firePropertyChanged(LAX_PROPERTY, old, lax);
	}

	private Boolean buildLax() {
		return (Boolean) this.getJdtMemberValue(JAXB.XML_ANY_ELEMENT__LAX);
	}

	public TextRange getLaxTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** value
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		throw new UnsupportedOperationException();
	}

	private void setValue_(String value) {
		String old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
		this.firePropertyChanged(FULLY_QUALIFIED_VALUE_CLASS_NAME_PROPERTY, old, value);
	}

	private String buildValue() {
		return (String) this.getJdtMemberValue(JAXB.XML_ANY_ELEMENT__VALUE);
	}

	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** fully-qualified value class name
	public String getFullyQualifiedValueClassName() {
		return this.value;
	}

}
