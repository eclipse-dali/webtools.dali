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
import org.eclipse.jpt.eclipselink.core.context.ReadOnly;
import org.eclipse.jpt.eclipselink.core.context.java.JavaReadOnly;
import org.eclipse.jpt.eclipselink.core.resource.java.ReadOnlyAnnotation;

public class EclipseLinkJavaReadOnly extends AbstractJavaJpaContextNode implements JavaReadOnly
{
	
	protected Boolean specifiedReadOnly;
	
	protected JavaResourcePersistentType resourcePersistentType;
	
	public EclipseLinkJavaReadOnly(JavaTypeMapping parent) {
		super(parent);
	}
	
	protected String getReadOnlyAnnotationName() {
		return ReadOnlyAnnotation.ANNOTATION_NAME;
	}
	
	protected ReadOnlyAnnotation getResourceReadOnly() {
		return (ReadOnlyAnnotation) this.resourcePersistentType.getSupportingAnnotation(getReadOnlyAnnotationName());
	}
	
	protected void addResourceReadOnly() {
		this.resourcePersistentType.addSupportingAnnotation(getReadOnlyAnnotationName());
	}
	
	protected void removeResourceReadOnly() {
		this.resourcePersistentType.removeSupportingAnnotation(getReadOnlyAnnotationName());
	}

	public boolean isReadOnly() {
		return (this.getSpecifiedReadOnly() == null) ? this.isDefaultReadOnly() : this.getSpecifiedReadOnly().booleanValue();
	}
	
	public boolean isDefaultReadOnly() {
		return ReadOnly.DEFAULT_READ_ONLY;
	}
	
	public Boolean getSpecifiedReadOnly() {
		return this.specifiedReadOnly;
	}
	
	public void setSpecifiedReadOnly(Boolean newReadOnly) {
		if (this.specifiedReadOnly == newReadOnly) {
			return;
		}
		Boolean oldReadOnly = this.specifiedReadOnly;
		this.specifiedReadOnly = newReadOnly;

		if (newReadOnly != null && newReadOnly.booleanValue()) {
			addResourceReadOnly();
		}
		else {
			//have to check if annotation exists in case the change is from false to null or vice versa
			if (getResourceReadOnly() != null) {
				removeResourceReadOnly();
			}
		}
		firePropertyChanged(SPECIFIED_READ_ONLY_PROPERTY, oldReadOnly, newReadOnly);
	}
	
	protected void setSpecifiedReadOnly_(Boolean newReadOnly) {
		Boolean oldReadOnly = this.specifiedReadOnly;
		this.specifiedReadOnly = newReadOnly;
		firePropertyChanged(SPECIFIED_READ_ONLY_PROPERTY, oldReadOnly, newReadOnly);
	}
	
	public void initialize(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		this.specifiedReadOnly = readOnly();
	}
	
	public void update(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		this.setSpecifiedReadOnly_(readOnly());
	}
	
	private Boolean readOnly() {
		return getResourceReadOnly() == null ? null : Boolean.TRUE;
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		ReadOnlyAnnotation resourceReadOnly = this.getResourceReadOnly();
		return resourceReadOnly == null ? null : resourceReadOnly.getTextRange(astRoot);
	}

}
