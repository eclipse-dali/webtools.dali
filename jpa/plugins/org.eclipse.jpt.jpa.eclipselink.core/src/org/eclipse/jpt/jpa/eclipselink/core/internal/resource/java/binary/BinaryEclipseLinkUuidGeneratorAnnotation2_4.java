/*******************************************************************************
 *  Copyright (c) 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkUuidGeneratorAnnotation2_4;

/**
 * org.eclipse.persistence.annotations.Multitenant
 */
public class BinaryEclipseLinkUuidGeneratorAnnotation2_4
	extends BinaryAnnotation
	implements EclipseLinkUuidGeneratorAnnotation2_4
{
	private String name;


	public BinaryEclipseLinkUuidGeneratorAnnotation2_4(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
	}


	// ********** EclipseLinkMultitenantAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(EclipseLink.UUID_GENERATOR__NAME);
	}

	public TextRange getNameTextRange() {
		throw new UnsupportedOperationException();
	}


	public Integer getInitialValue() {
		return null;
	}
	public void setInitialValue(Integer initialValue) {
		throw new UnsupportedOperationException();
	}
	public TextRange getInitialValueTextRange() {
		throw new UnsupportedOperationException();
	}
	public Integer getAllocationSize() {
		return null;
	}
	public void setAllocationSize(Integer allocationSize) {
		throw new UnsupportedOperationException();
	}
	public TextRange getAllocationSizeTextRange() {
		throw new UnsupportedOperationException();
	}
}
