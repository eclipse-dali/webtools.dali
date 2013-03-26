/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchType;

public class EclipseLinkJavaJoinFetch
	extends AbstractJavaContextModel<JavaAttributeMapping>
	implements EclipseLinkJoinFetch
{
	protected EclipseLinkJoinFetchType value;


	public EclipseLinkJavaJoinFetch(JavaAttributeMapping parent) {
		super(parent);
		this.value = this.buildValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setValue_(this.buildValue());
	}


	// ********** value **********

	public EclipseLinkJoinFetchType getValue() {
		return this.value;
	}

	public void setValue(EclipseLinkJoinFetchType value) {
		if (this.valuesAreDifferent(value, this.value)) {
			JoinFetchAnnotation annotation = this.getJoinFetchAnnotation();
			if (value == null) {
				if (annotation != null) {
					this.removeJoinFetchAnnotation();
				}
			} else {
				if (annotation == null) {
					annotation = this.addJoinFetchAnnotation();
				}
				annotation.setValue(EclipseLinkJoinFetchType.toJavaResourceModel(value));
			}

			this.setValue_(value);
		}
	}

	protected void setValue_(EclipseLinkJoinFetchType value) {
		EclipseLinkJoinFetchType old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private EclipseLinkJoinFetchType buildValue() {
		JoinFetchAnnotation annotation = this.getJoinFetchAnnotation();
		if (annotation == null) {
			return null;
		}
		JoinFetchType annotationValue = annotation.getValue();
		return (annotationValue != null) ?
				EclipseLinkJoinFetchType.fromJavaResourceModel(annotationValue) :
				this.getDefaultValue(); // @JoinFetch is equivalent to @JoinFetch(JoinFetch.INNER)
	}

	protected EclipseLinkJoinFetchType getDefaultValue() {
		return DEFAULT_VALUE;
	}


	// ********** join fetch annotation **********

	protected JoinFetchAnnotation getJoinFetchAnnotation() {
		return (JoinFetchAnnotation) this.getResourceAttribute().getAnnotation(this.getJoinFetchAnnotationName());
	}

	protected JoinFetchAnnotation addJoinFetchAnnotation() {
		return (JoinFetchAnnotation) this.getResourceAttribute().addAnnotation(this.getJoinFetchAnnotationName());
	}

	protected void removeJoinFetchAnnotation() {
		this.getResourceAttribute().removeAnnotation(this.getJoinFetchAnnotationName());
	}

	protected String getJoinFetchAnnotationName() {
		return JoinFetchAnnotation.ANNOTATION_NAME;
	}


	// ********** misc **********

	protected JavaAttributeMapping getAttributeMapping() {
		return this.parent;
	}

	protected JavaResourceAttribute getResourceAttribute() {
		return this.getAttributeMapping().getResourceAttribute();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getAnnotationTextRange();
		return (textRange != null) ? textRange : this.getAttributeMapping().getValidationTextRange();
	}

	protected TextRange getAnnotationTextRange() {
		JoinFetchAnnotation annotation = this.getJoinFetchAnnotation();
		return (annotation == null) ? null : annotation.getTextRange();
	}
}
