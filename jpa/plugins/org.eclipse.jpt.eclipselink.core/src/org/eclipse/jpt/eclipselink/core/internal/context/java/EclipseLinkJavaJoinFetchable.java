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
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchable;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchAnnotation;

public class EclipseLinkJavaJoinFetchable extends AbstractJavaJpaContextNode implements JoinFetchable
{
	
	protected boolean joinFetch;	
	protected JoinFetchType defaultJoinFetch;
	protected JoinFetchType specifiedJoinFetch;	
	
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	public EclipseLinkJavaJoinFetchable(JavaAttributeMapping parent) {
		super(parent);
	}

	protected String getJoinFetchAnnotationName() {
		return JoinFetchAnnotation.ANNOTATION_NAME;
	}
	
	protected JoinFetchAnnotation getResourceJoinFetch() {
		return (JoinFetchAnnotation) this.resourcePersistentAttribute.getSupportingAnnotation(getJoinFetchAnnotationName());
	}
	
	protected void addResourceJoinFetch() {
		this.resourcePersistentAttribute.addSupportingAnnotation(getJoinFetchAnnotationName());
	}
	
	protected void removeResourceJoinFetch() {
		this.resourcePersistentAttribute.removeSupportingAnnotation(getJoinFetchAnnotationName());
	}
	
	public boolean hasJoinFetch() {
		return this.joinFetch;
	}
	
	public void setJoinFetch(boolean newJoinFetch) {
		boolean oldJoinFetch = this.joinFetch;
		this.joinFetch = newJoinFetch;
		if (newJoinFetch) {
			addResourceJoinFetch();
		}
		else {
			removeResourceJoinFetch();
		}
		firePropertyChanged(JOIN_FETCH_PROPERTY, oldJoinFetch, newJoinFetch);
		setDefaultJoinFetch(caclulateDefaultJoinFetch());
	}
	
	protected void setJoinFetch_(boolean newJoinFetch) {
		boolean oldJoinFetch = this.joinFetch;
		this.joinFetch = newJoinFetch;
		firePropertyChanged(JOIN_FETCH_PROPERTY, oldJoinFetch, newJoinFetch);
	}
	
	protected JoinFetchType caclulateDefaultJoinFetch() {
		if (hasJoinFetch()) {
			return JoinFetchable.DEFAULT_JOIN_FETCH_TYPE;
		}
		return null;
	}
	
	public JoinFetchType getJoinFetch() {
		return (this.getSpecifiedJoinFetch() == null) ? this.getDefaultJoinFetch() : this.getSpecifiedJoinFetch();
	}

	public JoinFetchType getDefaultJoinFetch() {
		return this.defaultJoinFetch;
	}
	
	protected void setDefaultJoinFetch(JoinFetchType newDefaultJoinFetch) {
		JoinFetchType oldDefaultJoinFetch = this.defaultJoinFetch;
		this.defaultJoinFetch = newDefaultJoinFetch;
		firePropertyChanged(DEFAULT_JOIN_FETCH_PROPERTY, oldDefaultJoinFetch, newDefaultJoinFetch);
	}
		
	public JoinFetchType getSpecifiedJoinFetch() {
		return this.specifiedJoinFetch;
	}
	
	public void setSpecifiedJoinFetch(JoinFetchType newSpecifiedJoinFetch) {
		if (!hasJoinFetch()) {
			if (newSpecifiedJoinFetch != null) {
				setJoinFetch(true);
			}
			else {
				return;
			}
		}		
		JoinFetchType oldJoinFetch = this.specifiedJoinFetch;
		this.specifiedJoinFetch = newSpecifiedJoinFetch;
		this.getResourceJoinFetch().setValue(JoinFetchType.toJavaResourceModel(newSpecifiedJoinFetch));
		firePropertyChanged(JoinFetchable.SPECIFIED_JOIN_FETCH_PROPERTY, oldJoinFetch, newSpecifiedJoinFetch);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedJoinFetch_(JoinFetchType newSpecifiedJoinFetch) {
		JoinFetchType oldJoinFetch = this.specifiedJoinFetch;
		this.specifiedJoinFetch = newSpecifiedJoinFetch;
		firePropertyChanged(JoinFetchable.SPECIFIED_JOIN_FETCH_PROPERTY, oldJoinFetch, newSpecifiedJoinFetch);
	}
	
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		JoinFetchAnnotation resourceJoinFetch = this.getResourceJoinFetch();
		this.joinFetch = resourceJoinFetch != null;
		this.specifiedJoinFetch = this.specifiedJoinFetch(resourceJoinFetch);
		this.defaultJoinFetch = this.caclulateDefaultJoinFetch();
	}
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		JoinFetchAnnotation resourceJoinFetch = this.getResourceJoinFetch();
		setJoinFetch_(resourceJoinFetch != null);
		setSpecifiedJoinFetch_(this.specifiedJoinFetch(resourceJoinFetch));
		setDefaultJoinFetch(this.caclulateDefaultJoinFetch());
	}

	private JoinFetchType specifiedJoinFetch(JoinFetchAnnotation resourceJoinFetch) {
		return resourceJoinFetch == null ? null : JoinFetchType.fromJavaResourceModel(resourceJoinFetch.getValue());
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		JoinFetchAnnotation resourceJoinFetch = this.getResourceJoinFetch();
		return resourceJoinFetch == null ? null : resourceJoinFetch.getTextRange(astRoot);
	}
	
}
