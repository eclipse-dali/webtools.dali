/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapsId2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;

/**
 * javax.persistence.MapsId
 */
public class BinaryMapsId2_0Annotation
	extends BinaryAnnotation
	implements MapsId2_0Annotation
{
	private String value;
	
	
	public BinaryMapsId2_0Annotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
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
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}
	
	
	// ********** MapsId2_0Annotation implementation **********
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String newValue) {
		throw new UnsupportedOperationException();
	}
	
	private void setValue_(String newValue) {
		String oldValue = this.value;
		this.value = newValue;
		this.firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	private String buildValue() {
		return (String) this.getJdtMemberValue(JPA2_0.MAPS_ID__VALUE);
	}
	
	public TextRange getValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	public boolean valueTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
}
