/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkChangeTrackingAnnotation;

public class JavaEclipseLinkChangeTracking extends AbstractJavaJpaContextNode implements EclipseLinkChangeTracking
{
	protected JavaResourcePersistentType resourcePersistentType;
	
	protected EclipseLinkChangeTrackingType specifiedType;
	
	
	public JavaEclipseLinkChangeTracking(JavaTypeMapping parent) {
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
	
	protected String getChangeTrackingAnnotationName() {
		return EclipseLinkChangeTrackingAnnotation.ANNOTATION_NAME;
	}
	
	protected EclipseLinkChangeTrackingAnnotation getChangeTrackingAnnotation() {
		return (EclipseLinkChangeTrackingAnnotation) this.resourcePersistentType.getAnnotation(getChangeTrackingAnnotationName());
	}
	
	protected void addChangeTrackingAnnotation() {
		this.resourcePersistentType.addAnnotation(getChangeTrackingAnnotationName());
	}
	
	protected void removeChangeTrackingAnnotation() {
		this.resourcePersistentType.removeAnnotation(getChangeTrackingAnnotationName());
	}
	
	public EclipseLinkChangeTrackingType getType() {
		return (this.getSpecifiedType() != null) ? this.getSpecifiedType() : this.getDefaultType();
	}
	
	public EclipseLinkChangeTrackingType getDefaultType() {
		return DEFAULT_TYPE;
	}
	
	public EclipseLinkChangeTrackingType getSpecifiedType() {
		return this.specifiedType;
	}
	
	public void setSpecifiedType(EclipseLinkChangeTrackingType newSpecifiedType) {
		if (this.specifiedType == newSpecifiedType) {
			return;
		}
		
		EclipseLinkChangeTrackingType oldSpecifiedType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		
		if (newSpecifiedType != null) {
			if (getChangeTrackingAnnotation() == null) {
				addChangeTrackingAnnotation();
			}
			getChangeTrackingAnnotation().setValue(EclipseLinkChangeTrackingType.toJavaResourceModel(newSpecifiedType));
		}
		else {
			if (getChangeTrackingAnnotation() != null) {
				removeChangeTrackingAnnotation();
			}
		}
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedType_(EclipseLinkChangeTrackingType newSpecifiedType) {
		EclipseLinkChangeTrackingType oldSpecifiedType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);
	}
	
	public void initialize(JavaResourcePersistentType resourcePersistentType) {
		this.resourcePersistentType = resourcePersistentType;
		EclipseLinkChangeTrackingAnnotation changeTrackingAnnotation = this.getChangeTrackingAnnotation();
		this.specifiedType = changeTrackingType(changeTrackingAnnotation);
	}
	
	public void update(JavaResourcePersistentType resourcePersistentType) {
		this.resourcePersistentType = resourcePersistentType;
		EclipseLinkChangeTrackingAnnotation changeTrackingAnnotation = this.getChangeTrackingAnnotation();
		this.setSpecifiedType_(changeTrackingType(changeTrackingAnnotation));
	}
	
	protected EclipseLinkChangeTrackingType changeTrackingType(EclipseLinkChangeTrackingAnnotation changeTracking) {
		if (changeTracking == null) {
			return null;
		}
		else if (changeTracking.getValue() == null) {
			return EclipseLinkChangeTracking.DEFAULT_TYPE;
		}
		else {
			return EclipseLinkChangeTrackingType.fromJavaResourceModel(changeTracking.getValue());
		}
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		EclipseLinkChangeTrackingAnnotation changeTrackingAnnotation = getChangeTrackingAnnotation();
		TextRange textRange = changeTrackingAnnotation == null ? null : changeTrackingAnnotation.getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}
}