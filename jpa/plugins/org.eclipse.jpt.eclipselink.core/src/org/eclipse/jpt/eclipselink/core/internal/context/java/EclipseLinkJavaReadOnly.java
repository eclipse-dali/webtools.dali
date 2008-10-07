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
import org.eclipse.jpt.eclipselink.core.context.java.JavaReadOnly;
import org.eclipse.jpt.eclipselink.core.resource.java.ReadOnlyAnnotation;

public class EclipseLinkJavaReadOnly extends AbstractJavaJpaContextNode implements JavaReadOnly
{
	
	protected boolean readOnly;
	
	protected JavaResourcePersistentType resourcePersistentType;
	
	public EclipseLinkJavaReadOnly(JavaTypeMapping parent) {
		super(parent);
	}
	
	protected String getReadOnlyAnnotationName() {
		return ReadOnlyAnnotation.ANNOTATION_NAME;
	}
	
	protected ReadOnlyAnnotation getResourceReadOnly() {
		return (ReadOnlyAnnotation) this.resourcePersistentType.getAnnotation(getReadOnlyAnnotationName());
	}
	
	protected void addResourceReadOnly() {
		this.resourcePersistentType.addAnnotation(getReadOnlyAnnotationName());
	}
	
	protected void removeResourceReadOnly() {
		this.resourcePersistentType.removeAnnotation(getReadOnlyAnnotationName());
	}

	public boolean getReadOnly() {
		return this.readOnly;
	}
	
	public void setReadOnly(boolean newReadOnly) {
		if (this.readOnly == newReadOnly) {
			return;
		}
		boolean oldReadOnly = this.readOnly;
		this.readOnly = newReadOnly;

		if (newReadOnly) {
			addResourceReadOnly();
		}
		else {
			//have to check if annotation exists in case the change is from false to null or vice versa
			if (getResourceReadOnly() != null) {
				removeResourceReadOnly();
			}
		}
		firePropertyChanged(READ_ONLY_PROPERTY, oldReadOnly, newReadOnly);
	}
	
	protected void setReadOnly_(boolean newReadOnly) {
		boolean oldReadOnly = this.readOnly;
		this.readOnly = newReadOnly;
		firePropertyChanged(READ_ONLY_PROPERTY, oldReadOnly, newReadOnly);
	}
	
	public void initialize(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		this.readOnly = readOnly();
	}
	
	public void update(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		this.setReadOnly_(readOnly());
	}
	
	private boolean readOnly() {
		return getResourceReadOnly() != null;
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		ReadOnlyAnnotation resourceReadOnly = this.getResourceReadOnly();
		return resourceReadOnly == null ? null : resourceReadOnly.getTextRange(astRoot);
	}

}
