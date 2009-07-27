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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkPrivateOwnedAnnotation;

public class JavaEclipseLinkPrivateOwned extends AbstractJavaJpaContextNode implements EclipseLinkPrivateOwned
{
	protected boolean privateOwned;
	
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	public JavaEclipseLinkPrivateOwned(JavaAttributeMapping parent) {
		super(parent);
	}
	
	protected String getPrivateOwnedAnnotationName() {
		return EclipseLinkPrivateOwnedAnnotation.ANNOTATION_NAME;
	}
	
	protected EclipseLinkPrivateOwnedAnnotation getResourcePrivateOwned() {
		return (EclipseLinkPrivateOwnedAnnotation) this.resourcePersistentAttribute.getSupportingAnnotation(getPrivateOwnedAnnotationName());
	}
	
	protected void addResourcePrivateOwned() {
		this.resourcePersistentAttribute.addSupportingAnnotation(getPrivateOwnedAnnotationName());
	}
	
	protected void removeResourcePrivateOwned() {
		this.resourcePersistentAttribute.removeSupportingAnnotation(getPrivateOwnedAnnotationName());
	}

	public boolean isPrivateOwned() {
		return this.privateOwned;
	}
	
	public void setPrivateOwned(boolean newPrivateOwned) {
		if (this.privateOwned == newPrivateOwned) {
			return;
		}
		boolean oldPrivateOwned = this.privateOwned;
		this.privateOwned = newPrivateOwned;

		if (newPrivateOwned) {
			addResourcePrivateOwned();
		}
		else {
			//have to check if annotation exists in case the change is from false to null or vice versa
			if (getResourcePrivateOwned() != null) {
				removeResourcePrivateOwned();
			}
		}
		firePropertyChanged(PRIVATE_OWNED_PROPERTY, oldPrivateOwned, newPrivateOwned);
	}
	
	protected void setPrivateOwned_(boolean newPrivateOwned) {
		boolean oldPrivateOwned = this.privateOwned;
		this.privateOwned = newPrivateOwned;
		firePropertyChanged(PRIVATE_OWNED_PROPERTY, oldPrivateOwned, newPrivateOwned);
	}
	
	public void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		this.privateOwned = privateOwned();
	}
	
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.resourcePersistentAttribute = jrpa;
		this.setPrivateOwned_(privateOwned());
	}
	
	private boolean privateOwned() {
		return getResourcePrivateOwned() != null;
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		EclipseLinkPrivateOwnedAnnotation resourcePrivateOwned = this.getResourcePrivateOwned();
		return resourcePrivateOwned == null ? null : resourcePrivateOwned.getTextRange(astRoot);
	}

}
