/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorValueAnnotation;


public class BinaryXmlDiscriminatorValueAnnotation
		extends BinaryAnnotation
		implements XmlDiscriminatorValueAnnotation {
	
	private String value;
	
	
	public BinaryXmlDiscriminatorValueAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = buildValue();
	}
	
	
	public String getAnnotationName() {
		return ELJaxb.XML_DISCRIMINATOR_VALUE;
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
		return (String) this.getJdtMemberValue(ELJaxb.XML_DISCRIMINATOR_VALUE__VALUE);
	}
	
	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public boolean valueTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
}
