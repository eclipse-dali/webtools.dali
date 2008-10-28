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
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.ChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingAnnotation;

public class EclipseLinkJavaChangeTracking extends AbstractJavaJpaContextNode implements ChangeTracking
{
	protected JavaResourcePersistentType resourcePersistentType;
	
	protected ChangeTrackingType specifiedType;
	
	
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
	
	protected String getChangeTrackingAnnotationName() {
		return ChangeTrackingAnnotation.ANNOTATION_NAME;
	}
	
	protected ChangeTrackingAnnotation getChangeTrackingAnnotation() {
		return (ChangeTrackingAnnotation) this.resourcePersistentType.getSupportingAnnotation(getChangeTrackingAnnotationName());
	}
	
	protected void addChangeTrackingAnnotation() {
		this.resourcePersistentType.addSupportingAnnotation(getChangeTrackingAnnotationName());
	}
	
	protected void removeChangeTrackingAnnotation() {
		this.resourcePersistentType.removeSupportingAnnotation(getChangeTrackingAnnotationName());
	}
	
	public ChangeTrackingType getType() {
		return (this.getSpecifiedType() != null) ? this.getSpecifiedType() : this.getDefaultType();
	}
	
	public ChangeTrackingType getDefaultType() {
		return DEFAULT_TYPE;
	}
	
	public ChangeTrackingType getSpecifiedType() {
		return this.specifiedType;
	}
	
	public void setSpecifiedType(ChangeTrackingType newSpecifiedType) {
		if (this.specifiedType == newSpecifiedType) {
			return;
		}
		
		ChangeTrackingType oldSpecifiedType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		
		if (newSpecifiedType != null) {
			if (getChangeTrackingAnnotation() == null) {
				addChangeTrackingAnnotation();
			}
			getChangeTrackingAnnotation().setValue(ChangeTrackingType.toJavaResourceModel(newSpecifiedType));
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
	protected void setSpecifiedType_(ChangeTrackingType newSpecifiedType) {
		ChangeTrackingType oldSpecifiedType = this.specifiedType;
		this.specifiedType = newSpecifiedType;
		firePropertyChanged(SPECIFIED_TYPE_PROPERTY, oldSpecifiedType, newSpecifiedType);
	}
	
	public void initialize(JavaResourcePersistentType resourcePersistentType) {
		this.resourcePersistentType = resourcePersistentType;
		ChangeTrackingAnnotation changeTrackingAnnotation = this.getChangeTrackingAnnotation();
		this.specifiedType = changeTrackingType(changeTrackingAnnotation);
	}
	
	public void update(JavaResourcePersistentType resourcePersistentType) {
		this.resourcePersistentType = resourcePersistentType;
		ChangeTrackingAnnotation changeTrackingAnnotation = this.getChangeTrackingAnnotation();
		this.setSpecifiedType_(changeTrackingType(changeTrackingAnnotation));
	}
	
	protected ChangeTrackingType changeTrackingType(ChangeTrackingAnnotation changeTracking) {
		if (changeTracking == null) {
			return null;
		}
		else if (changeTracking.getValue() == null) {
			return ChangeTracking.DEFAULT_TYPE;
		}
		else {
			return ChangeTrackingType.fromJavaResourceModel(changeTracking.getValue());
		}
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		ChangeTrackingAnnotation changeTrackingAnnotation = getChangeTrackingAnnotation();
		TextRange textRange = changeTrackingAnnotation == null ? null : changeTrackingAnnotation.getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}
}