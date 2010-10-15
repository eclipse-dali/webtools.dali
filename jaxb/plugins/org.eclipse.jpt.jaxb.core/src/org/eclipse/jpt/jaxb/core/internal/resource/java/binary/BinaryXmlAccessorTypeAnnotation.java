/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;

/**
 * javax.xml.bind.annotation.XmlAccessorType
 */
public final class BinaryXmlAccessorTypeAnnotation
	extends BinaryAnnotation
	implements XmlAccessorTypeAnnotation
{
	private XmlAccessType value;


	public BinaryXmlAccessorTypeAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = this.buildValue();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setValue_(this.buildValue());
	}


	//*************** XmlAccessorTypeAnnotation implementation ****************

	// ***** value
	public XmlAccessType getValue() {
		return this.value;
	}

	public void setValue(XmlAccessType value) {
		throw new UnsupportedOperationException();
	}

	private void setValue_(XmlAccessType value) {
		XmlAccessType old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private XmlAccessType buildValue() {
		return XmlAccessType.fromJavaAnnotationValue(this.getJdtMemberValue(JAXB.XML_ACCESSOR_TYPE__VALUE));
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
