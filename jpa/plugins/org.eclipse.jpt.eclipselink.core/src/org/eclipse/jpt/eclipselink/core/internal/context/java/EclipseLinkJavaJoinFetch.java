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
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJoinFetchAnnotation;

public class EclipseLinkJavaJoinFetch 
	extends AbstractJavaJpaContextNode 
	implements JoinFetch
{
	protected JoinFetchType joinFetchValue;	
	
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	
	public EclipseLinkJavaJoinFetch(JavaAttributeMapping parent) {
		super(parent);
	}
	
	
	protected String getJoinFetchAnnotationName() {
		return EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME;
	}
	
	protected EclipseLinkJoinFetchAnnotation getResourceJoinFetch() {
		return (EclipseLinkJoinFetchAnnotation) this.resourcePersistentAttribute.getSupportingAnnotation(getJoinFetchAnnotationName());
	}
	
	protected void addResourceJoinFetch() {
		this.resourcePersistentAttribute.addSupportingAnnotation(getJoinFetchAnnotationName());
	}
	
	protected void removeResourceJoinFetch() {
		this.resourcePersistentAttribute.removeSupportingAnnotation(getJoinFetchAnnotationName());
	}
	
	public JoinFetchType getValue() {
		return this.joinFetchValue;
	}
	
	protected JoinFetchType getDefaultValue() {
		return JoinFetch.DEFAULT_VALUE;
	}
	
	public void setValue(JoinFetchType newJoinFetchValue) {
		if (this.joinFetchValue == newJoinFetchValue) {
			return;
		}
				
		JoinFetchType oldJoinFetchValue = this.joinFetchValue;
		this.joinFetchValue = newJoinFetchValue;
		
		if (newJoinFetchValue != null) {
			if (getResourceJoinFetch() == null) {
				addResourceJoinFetch();
			}
			getResourceJoinFetch().setValue(JoinFetchType.toJavaResourceModel(newJoinFetchValue));		
		}
		else {
			if (getResourceJoinFetch() != null) {
				removeResourceJoinFetch();
			}
		}
		firePropertyChanged(JoinFetch.VALUE_PROPERTY, oldJoinFetchValue, newJoinFetchValue);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setValue_(JoinFetchType newJoinFetchValue) {
		JoinFetchType oldJoinFetchValue = this.joinFetchValue;
		this.joinFetchValue = newJoinFetchValue;
		firePropertyChanged(JoinFetch.VALUE_PROPERTY, oldJoinFetchValue, newJoinFetchValue);
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
	
	private JoinFetchType joinFetch(EclipseLinkJoinFetchAnnotation resourceJoinFetch) {
		if (resourceJoinFetch == null) {
			return null;
		}
		if (resourceJoinFetch.getValue() == null) { 
			// @JoinFetch is equivalent to @JoinFetch(JoinFetch.INNER)
			return getDefaultValue();
		}
		return JoinFetchType.fromJavaResourceModel(resourceJoinFetch.getValue());
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		EclipseLinkJoinFetchAnnotation resourceJoinFetch = this.getResourceJoinFetch();
		return resourceJoinFetch == null ? null : resourceJoinFetch.getTextRange(astRoot);
	}
}
