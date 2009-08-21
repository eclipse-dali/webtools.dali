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
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJoinFetchAnnotation;

public class JavaEclipseLinkJoinFetch 
	extends AbstractJavaJpaContextNode 
	implements EclipseLinkJoinFetch
{
	protected EclipseLinkJoinFetchType joinFetchValue;	
	
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	
	public JavaEclipseLinkJoinFetch(JavaAttributeMapping parent) {
		super(parent);
	}
	
	
	protected String getJoinFetchAnnotationName() {
		return EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME;
	}
	
	protected EclipseLinkJoinFetchAnnotation getResourceJoinFetch() {
		return (EclipseLinkJoinFetchAnnotation) this.resourcePersistentAttribute.getAnnotation(getJoinFetchAnnotationName());
	}
	
	protected void addResourceJoinFetch() {
		this.resourcePersistentAttribute.addAnnotation(getJoinFetchAnnotationName());
	}
	
	protected void removeResourceJoinFetch() {
		this.resourcePersistentAttribute.removeAnnotation(getJoinFetchAnnotationName());
	}
	
	public EclipseLinkJoinFetchType getValue() {
		return this.joinFetchValue;
	}
	
	protected EclipseLinkJoinFetchType getDefaultValue() {
		return EclipseLinkJoinFetch.DEFAULT_VALUE;
	}
	
	public void setValue(EclipseLinkJoinFetchType newJoinFetchValue) {
		if (this.joinFetchValue == newJoinFetchValue) {
			return;
		}
				
		EclipseLinkJoinFetchType oldJoinFetchValue = this.joinFetchValue;
		this.joinFetchValue = newJoinFetchValue;
		
		if (newJoinFetchValue != null) {
			if (getResourceJoinFetch() == null) {
				addResourceJoinFetch();
			}
			getResourceJoinFetch().setValue(EclipseLinkJoinFetchType.toJavaResourceModel(newJoinFetchValue));		
		}
		else {
			if (getResourceJoinFetch() != null) {
				removeResourceJoinFetch();
			}
		}
		firePropertyChanged(EclipseLinkJoinFetch.VALUE_PROPERTY, oldJoinFetchValue, newJoinFetchValue);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setValue_(EclipseLinkJoinFetchType newJoinFetchValue) {
		EclipseLinkJoinFetchType oldJoinFetchValue = this.joinFetchValue;
		this.joinFetchValue = newJoinFetchValue;
		firePropertyChanged(EclipseLinkJoinFetch.VALUE_PROPERTY, oldJoinFetchValue, newJoinFetchValue);
	}
	
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		EclipseLinkJoinFetchAnnotation resourceJoinFetch = this.getResourceJoinFetch();
		this.joinFetchValue = this.joinFetch(resourceJoinFetch);
	}
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		EclipseLinkJoinFetchAnnotation resourceJoinFetch = this.getResourceJoinFetch();
		setValue_(joinFetch(resourceJoinFetch));
	}
	
	private EclipseLinkJoinFetchType joinFetch(EclipseLinkJoinFetchAnnotation resourceJoinFetch) {
		if (resourceJoinFetch == null) {
			return null;
		}
		if (resourceJoinFetch.getValue() == null) { 
			// @JoinFetch is equivalent to @JoinFetch(JoinFetch.INNER)
			return getDefaultValue();
		}
		return EclipseLinkJoinFetchType.fromJavaResourceModel(resourceJoinFetch.getValue());
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		EclipseLinkJoinFetchAnnotation resourceJoinFetch = this.getResourceJoinFetch();
		return resourceJoinFetch == null ? null : resourceJoinFetch.getTextRange(astRoot);
	}
}
