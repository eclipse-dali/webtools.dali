/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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

public class JavaEclipseLinkPrivateOwned
	extends AbstractJavaJpaContextNode
	implements EclipseLinkPrivateOwned
{
	protected boolean privateOwned;


	public JavaEclipseLinkPrivateOwned(JavaAttributeMapping parent) {
		super(parent);
		this.privateOwned = this.buildPrivateOwned();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setPrivateOwned_(this.buildPrivateOwned());
	}


	// ********** private owned **********

	public boolean isPrivateOwned() {
		return this.privateOwned;
	}

	public void setPrivateOwned(boolean privateOwned) {
		if (privateOwned != this.privateOwned) {
			EclipseLinkPrivateOwnedAnnotation annotation = this.getPrivateOwnedAnnotation();
			if (privateOwned) {
				if (annotation == null) {
					this.addPrivateOwnedAnnotation();
				}
			} else {
				if (annotation != null) {
					this.removePrivateOwnedAnnotation();
				}
			}

			this.setPrivateOwned_(privateOwned);
		}
	}

	protected void setPrivateOwned_(boolean privateOwned) {
		boolean old = this.privateOwned;
		this.privateOwned = privateOwned;
		this.firePropertyChanged(PRIVATE_OWNED_PROPERTY, old, privateOwned);
	}

	protected boolean buildPrivateOwned() {
		return this.getPrivateOwnedAnnotation() != null;
	}


	// ********** private owned annotation **********

	protected EclipseLinkPrivateOwnedAnnotation getPrivateOwnedAnnotation() {
		return (EclipseLinkPrivateOwnedAnnotation) this.getResourcePersistentAttribute().getAnnotation(this.getPrivateOwnedAnnotationName());
	}

	protected EclipseLinkPrivateOwnedAnnotation addPrivateOwnedAnnotation() {
		return (EclipseLinkPrivateOwnedAnnotation) this.getResourcePersistentAttribute().addAnnotation(this.getPrivateOwnedAnnotationName());
	}

	protected void removePrivateOwnedAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(this.getPrivateOwnedAnnotationName());
	}

	protected String getPrivateOwnedAnnotationName() {
		return EclipseLinkPrivateOwnedAnnotation.ANNOTATION_NAME;
	}


	// ********** misc **********

	@Override
	public JavaAttributeMapping getParent() {
		return (JavaAttributeMapping) super.getParent();
	}

	protected JavaAttributeMapping getAttributeMapping() {
		return this.getParent();
	}

	protected JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return this.getAttributeMapping().getResourcePersistentAttribute();
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		EclipseLinkPrivateOwnedAnnotation annotation = this.getPrivateOwnedAnnotation();
		return (annotation == null) ? null : annotation.getTextRange(astRoot);
	}
}
