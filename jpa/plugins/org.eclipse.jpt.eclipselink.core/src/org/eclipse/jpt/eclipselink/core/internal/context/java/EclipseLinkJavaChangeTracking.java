/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.ChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.context.java.JavaChangeTracking;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingAnnotation;

public class EclipseLinkJavaChangeTracking extends AbstractJavaJpaContextNode implements JavaChangeTracking
{
	
	protected boolean changeTracking;
	protected ChangeTrackingType specifiedChangeTrackingType;
	
	protected JavaResourcePersistentType resourcePersistentType;
	
	public EclipseLinkJavaChangeTracking(JavaTypeMapping parent) {
		super(parent);
	}
	
	@Override
	public JavaTypeMapping getParent() {
		return (JavaTypeMapping) super.getParent();
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}
	
	protected ChangeTrackingAnnotation getChangeTrackingAnnotation() {
		return (ChangeTrackingAnnotation) this.resourcePersistentType.getAnnotation(getChangeTrackingAnnotationName());
	}
	
	protected String getChangeTrackingAnnotationName() {
		return ChangeTrackingAnnotation.ANNOTATION_NAME;
	}
	
	public boolean hasChangeTracking() {
		return this.changeTracking;
	}
	
	public void setChangeTracking(boolean newChangeTracking) {
		boolean oldChangeTracking = this.changeTracking;
		this.changeTracking = newChangeTracking;
		if (newChangeTracking) {
			this.resourcePersistentType.addAnnotation(getChangeTrackingAnnotationName());
		}
		else {
			this.resourcePersistentType.removeAnnotation(getChangeTrackingAnnotationName());
		}
		firePropertyChanged(CHANGE_TRACKING_PROPERTY, oldChangeTracking, newChangeTracking);
	}
	
	protected void setChangeTracking_(boolean newChangeTracking) {
		boolean oldChangeTracking = this.changeTracking;
		this.changeTracking = newChangeTracking;
		firePropertyChanged(CHANGE_TRACKING_PROPERTY, oldChangeTracking, newChangeTracking);
	}
	
	public ChangeTrackingType getChangeTrackingType() {
		return (this.getSpecifiedChangeTrackingType() == null) ? this.getDefaultChangeTrackingType() : this.getSpecifiedChangeTrackingType();
	}

	public ChangeTrackingType getDefaultChangeTrackingType() {
		return DEFAULT_CHANGE_TRACKING_TYPE;
	}
	
	public ChangeTrackingType getSpecifiedChangeTrackingType() {
		return this.specifiedChangeTrackingType;
	}
	
	public void setSpecifiedChangeTrackingType(ChangeTrackingType newSpecifiedChangeTrackingType) {
		if (!hasChangeTracking()) {
			if (newSpecifiedChangeTrackingType != null) {
				setChangeTracking(true);
			}
			else {
				return;
			}
		}
		ChangeTrackingType oldSpecifiedChangeTrackingType = this.specifiedChangeTrackingType;
		this.specifiedChangeTrackingType = newSpecifiedChangeTrackingType;
		this.getChangeTrackingAnnotation().setValue(ChangeTrackingType.toJavaResourceModel(newSpecifiedChangeTrackingType));
		firePropertyChanged(SPECIFIED_CHANGE_TRACKING_TYPE_PROPERTY, oldSpecifiedChangeTrackingType, newSpecifiedChangeTrackingType);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedChangeTrackingType_(ChangeTrackingType newSpecifiedChangeTrackingType) {
		ChangeTrackingType oldSpecifiedChangeTrackingType = this.specifiedChangeTrackingType;
		this.specifiedChangeTrackingType = newSpecifiedChangeTrackingType;
		firePropertyChanged(SPECIFIED_CHANGE_TRACKING_TYPE_PROPERTY, oldSpecifiedChangeTrackingType, newSpecifiedChangeTrackingType);
	}

	
	public void initialize(JavaResourcePersistentType resourcePersistentType) {
		this.resourcePersistentType = resourcePersistentType;
		initialize(getChangeTrackingAnnotation());
	}
	
	protected void initialize(ChangeTrackingAnnotation changeTracking) {
		this.changeTracking = changeTracking != null;
		this.specifiedChangeTrackingType = specifiedChangeTrackingType(changeTracking);
	}
	
	public void update(JavaResourcePersistentType resourcePersistentType) {
		this.resourcePersistentType = resourcePersistentType;
		update(getChangeTrackingAnnotation());
	}

	protected void update(ChangeTrackingAnnotation changeTracking) {
		setChangeTracking_(changeTracking != null);
		setSpecifiedChangeTrackingType_(specifiedChangeTrackingType(changeTracking));
	}
	
	protected ChangeTrackingType specifiedChangeTrackingType(ChangeTrackingAnnotation changeTracking) {
		if (changeTracking == null) {
			return null;
		}
		return ChangeTrackingType.fromJavaResourceModel(changeTracking.getValue());
	}


	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		ChangeTrackingAnnotation changeTrackingAnnotation = getChangeTrackingAnnotation();
		TextRange textRange = changeTrackingAnnotation == null ? null : changeTrackingAnnotation.getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}
}
