/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkPrivateOwnedAnnotation;

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
		return (EclipseLinkPrivateOwnedAnnotation) this.getResourceAttribute().getAnnotation(this.getPrivateOwnedAnnotationName());
	}

	protected EclipseLinkPrivateOwnedAnnotation addPrivateOwnedAnnotation() {
		return (EclipseLinkPrivateOwnedAnnotation) this.getResourceAttribute().addAnnotation(this.getPrivateOwnedAnnotationName());
	}

	protected void removePrivateOwnedAnnotation() {
		this.getResourceAttribute().removeAnnotation(this.getPrivateOwnedAnnotationName());
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

	protected JavaResourceAttribute getResourceAttribute() {
		return this.getAttributeMapping().getResourceAttribute();
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getAnnotationTextRange(astRoot);
		return (textRange != null) ? textRange : this.getAttributeMapping().getValidationTextRange(astRoot);
	}

	protected TextRange getAnnotationTextRange(CompilationUnit astRoot) {
		EclipseLinkPrivateOwnedAnnotation annotation = this.getPrivateOwnedAnnotation();
		return (annotation == null) ? null : annotation.getTextRange(astRoot);
	}
}
