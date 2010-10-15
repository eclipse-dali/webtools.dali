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
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;

/**
 * javax.xml.bind.annotation.XmlAccessorOrder
 */
public final class BinaryXmlAccessorOrderAnnotation
	extends BinaryAnnotation
	implements XmlAccessorOrderAnnotation
{
	private XmlAccessOrder value;


	public BinaryXmlAccessorOrderAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
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


	//*************** XmlAccessorOrderAnnotation implementation ****************

	// ***** value
	public XmlAccessOrder getValue() {
		return this.value;
	}

	public void setValue(XmlAccessOrder value) {
		throw new UnsupportedOperationException();
	}

	private void setValue_(XmlAccessOrder value) {
		XmlAccessOrder old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private XmlAccessOrder buildValue() {
		return XmlAccessOrder.fromJavaAnnotationValue(this.getJdtMemberValue(JAXB.XML_ACCESSOR_ORDER__VALUE));
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
