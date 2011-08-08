/*******************************************************************************
 *  Copyright (c) 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkMultitenantAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.MultitenantType;

/**
 * org.eclipse.persistence.annotations.Multitenant
 */
public class BinaryEclipseLinkMultitenantAnnotation
	extends BinaryAnnotation
	implements EclipseLinkMultitenantAnnotation
{
	private MultitenantType value;


	public BinaryEclipseLinkMultitenantAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
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


	// ********** EclipseLinkMultitenantAnnotation implementation **********

	// ***** value
	public MultitenantType getValue() {
		return this.value;
	}

	public void setValue(MultitenantType value) {
		throw new UnsupportedOperationException();
	}

	private void setValue_(MultitenantType value) {
		MultitenantType old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private MultitenantType buildValue() {
		return MultitenantType.fromJavaAnnotationValue(this.getJdtMemberValue(EclipseLink.JOIN_FETCH__VALUE));
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
