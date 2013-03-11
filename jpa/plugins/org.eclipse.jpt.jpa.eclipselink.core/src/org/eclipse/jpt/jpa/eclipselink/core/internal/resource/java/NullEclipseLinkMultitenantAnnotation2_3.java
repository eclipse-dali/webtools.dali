/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java;

import org.eclipse.jpt.common.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantType2_3;

/**
 * <code>org.eclipse.persistence.annotations.Multitenant</code>
 */
public final class NullEclipseLinkMultitenantAnnotation2_3
	extends NullAnnotation<MultitenantAnnotation2_3>
	implements MultitenantAnnotation2_3
{
	protected NullEclipseLinkMultitenantAnnotation2_3(JavaResourceAnnotatedElement parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public boolean isSpecified() {
		return false;
	}

	// ***** type
	public MultitenantType2_3 getValue() {
		return null;
	}

	public void setValue(MultitenantType2_3 value) {
		if (value != null) {
			this.addAnnotation().setValue(value);
		}
	}

	public TextRange getValueTextRange() {
		return null;
	}

	// ***** include criteria
	public Boolean getIncludeCriteria() {
		return null;
	}

	public void setIncludeCriteria(Boolean includeCriteria) {
		if (includeCriteria != null) {
			this.addAnnotation().setIncludeCriteria(includeCriteria);
		}
	}

	public TextRange getIncludeCriteriaTextRange() {
		return null;
	}

}
