/*******************************************************************************
 *  Copyright (c) 2012, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorNodeAnnotation;


public class BinaryXmlDiscriminatorNodeAnnotation
		extends BinaryAnnotation
		implements XmlDiscriminatorNodeAnnotation {
	
	private String value;
	
	
	public BinaryXmlDiscriminatorNodeAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = buildValue();
	}
	
	
	public String getAnnotationName() {
		return ELJaxb.XML_DISCRIMINATOR_NODE;
	}
	
	@Override
	public void update() {
		super.update();
		setValue_(buildValue());
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}
	
	
	// ***** value *****
	
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
	}
	
	private String buildValue() {
		return (String) this.getJdtMemberValue(ELJaxb.XML_DISCRIMINATOR_NODE__VALUE);
	}
	
	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public TextRange getValueValidationTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public boolean valueTouches(int pos) {
		throw new UnsupportedOperationException();
	}
}
