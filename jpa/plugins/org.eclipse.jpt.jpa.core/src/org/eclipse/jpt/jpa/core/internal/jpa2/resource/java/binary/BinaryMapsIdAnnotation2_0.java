/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapsIdAnnotation2_0;

/**
 * <code>javax.persistence.MapsId</code>
 */
public class BinaryMapsIdAnnotation2_0
	extends BinaryAnnotation
	implements MapsIdAnnotation2_0
{
	private String value;
	
	
	public BinaryMapsIdAnnotation2_0(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
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
	
	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public boolean valueTouches(int pos) {
		throw new UnsupportedOperationException();
	}
}
