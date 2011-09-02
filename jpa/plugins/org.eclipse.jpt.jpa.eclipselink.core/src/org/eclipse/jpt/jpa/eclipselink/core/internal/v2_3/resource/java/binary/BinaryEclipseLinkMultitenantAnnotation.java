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
import org.eclipse.jpt.jpa.eclipselink.core.v2_4.resource.java.EclipseLink2_4;

/**
 * org.eclipse.persistence.annotations.Multitenant
 */
public class BinaryEclipseLinkMultitenantAnnotation
	extends BinaryAnnotation
	implements EclipseLinkMultitenantAnnotation
{
	private MultitenantType value;
	private Boolean includeCriteria;


	public BinaryEclipseLinkMultitenantAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = this.buildValue();
		this.includeCriteria = this.buildIncludeCriteria();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setValue_(this.buildValue());
		this.setIncludeCriteria_(this.buildIncludeCriteria());
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

	// ***** include criteria
	public Boolean getIncludeCriteria() {
		return this.includeCriteria;
	}

	public void setIncludeCriteria(Boolean includeCriteria) {
		throw new UnsupportedOperationException();
	}

	private void setIncludeCriteria_(Boolean includeCriteria) {
		Boolean old = this.includeCriteria;
		this.includeCriteria = includeCriteria;
		this.firePropertyChanged(INCLUDE_CRITERIA_PROPERTY, old, includeCriteria);
	}

	private Boolean buildIncludeCriteria() {
		return (Boolean) this.getJdtMemberValue(EclipseLink2_4.MULTITENANT__INCLUDE_CRITERIA);
	}

	public TextRange getIncludeCriteriaTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
