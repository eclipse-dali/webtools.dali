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

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTrackingType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingAnnotation;

public class EclipseLinkJavaChangeTracking
	extends AbstractJavaContextModel<JavaTypeMapping>
	implements EclipseLinkChangeTracking
{
	protected EclipseLinkChangeTrackingType specifiedType;


	public EclipseLinkJavaChangeTracking(JavaTypeMapping parent) {
		super(parent);
		this.specifiedType = this.buildSpecifiedType();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedType_(this.buildSpecifiedType());
	}


	// ********** type **********  

	public EclipseLinkChangeTrackingType getType() {
		return (this.specifiedType != null) ? this.specifiedType : this.getDefaultType();
	}

	public EclipseLinkChangeTrackingType getSpecifiedType() {
		return this.specifiedType;
	}

	public void setSpecifiedType(EclipseLinkChangeTrackingType type) {
		if (this.valuesAreDifferent(type, this.specifiedType)) {
			ChangeTrackingAnnotation annotation = this.getChangeTrackingAnnotation();
			if (type == null) {
				if (annotation != null) {
					this.removeChangeTrackingAnnotation();
				}
			} else {
				if (annotation == null) {
					annotation = this.addChangeTrackingAnnotation();
				}
				annotation.setValue(EclipseLinkChangeTrackingType.toJavaResourceModel(type));
			}
			this.setSpecifiedType_(type);
		}
	}

	protected void setSpecifiedType_(EclipseLinkChangeTrackingType type) {
		EclipseLinkChangeTrackingType old = this.specifiedType;
		this.specifiedType = type;
		this.firePropertyChanged(SPECIFIED_TYPE_PROPERTY, old, type);
	}

	protected EclipseLinkChangeTrackingType buildSpecifiedType() {
		ChangeTrackingAnnotation annotation = this.getChangeTrackingAnnotation();
		if (annotation == null) {
			return null;
		}
		ChangeTrackingType value = annotation.getValue();
		return (value == null) ? DEFAULT_TYPE : EclipseLinkChangeTrackingType.fromJavaResourceModel(value);
	}

	public EclipseLinkChangeTrackingType getDefaultType() {
		return DEFAULT_TYPE;
	}


	// ********** change tracking annotation **********  

	protected ChangeTrackingAnnotation getChangeTrackingAnnotation() {
		return (ChangeTrackingAnnotation) this.getJavaResourceType().getAnnotation(this.getChangeTrackingAnnotationName());
	}

	protected ChangeTrackingAnnotation addChangeTrackingAnnotation() {
		return (ChangeTrackingAnnotation) this.getJavaResourceType().addAnnotation(this.getChangeTrackingAnnotationName());
	}

	protected void removeChangeTrackingAnnotation() {
		this.getJavaResourceType().removeAnnotation(this.getChangeTrackingAnnotationName());
	}

	protected String getChangeTrackingAnnotationName() {
		return ChangeTrackingAnnotation.ANNOTATION_NAME;
	}


	// ********** misc **********  

	protected JavaTypeMapping getTypeMapping() {
		return this.parent;
	}

	protected JavaPersistentType getPersistentType() {
		return this.getTypeMapping().getPersistentType();
	}

	protected JavaResourceType getJavaResourceType() {
		return this.getPersistentType().getJavaResourceType();
	}


	// ********** validation **********  

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getAnnotationTextRange();
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange();
	}

	protected TextRange getAnnotationTextRange() {
		ChangeTrackingAnnotation annotation = this.getChangeTrackingAnnotation();
		return (annotation == null) ? null : annotation.getTextRange();
	}
}
