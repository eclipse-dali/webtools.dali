/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantType2_3;

/**
 * <code>org.eclipse.persistence.annotations.Multitenant</code>
 */
public class EclipseLinkBinaryMultitenantAnnotation2_3
	extends BinaryAnnotation
	implements MultitenantAnnotation2_3
{
	private MultitenantType2_3 value;
	private Boolean includeCriteria; // added in EclipseLink 2.4


	public EclipseLinkBinaryMultitenantAnnotation2_3(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
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

	public boolean isSpecified() {
		return true;
	}

	// ***** value
	public MultitenantType2_3 getValue() {
		return this.value;
	}

	public void setValue(MultitenantType2_3 value) {
		throw new UnsupportedOperationException();
	}

	private void setValue_(MultitenantType2_3 value) {
		MultitenantType2_3 old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private MultitenantType2_3 buildValue() {
		return MultitenantType2_3.fromJavaAnnotationValue(this.getJdtMemberValue(EclipseLink.JOIN_FETCH__VALUE));
	}

	public TextRange getValueTextRange() {
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
		return (Boolean) this.getJdtMemberValue(EclipseLink.MULTITENANT__INCLUDE_CRITERIA);
	}

	public TextRange getIncludeCriteriaTextRange() {
		throw new UnsupportedOperationException();
	}
}
