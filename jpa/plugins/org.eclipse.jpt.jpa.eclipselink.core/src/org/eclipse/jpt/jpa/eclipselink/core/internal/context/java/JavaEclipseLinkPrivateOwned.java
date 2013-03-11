/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkPrivateOwned;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.PrivateOwnedAnnotation;

public class JavaEclipseLinkPrivateOwned
	extends AbstractJavaContextModel<JavaAttributeMapping>
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
			PrivateOwnedAnnotation annotation = this.getPrivateOwnedAnnotation();
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

	protected PrivateOwnedAnnotation getPrivateOwnedAnnotation() {
		return (PrivateOwnedAnnotation) this.getResourceAttribute().getAnnotation(this.getPrivateOwnedAnnotationName());
	}

	protected PrivateOwnedAnnotation addPrivateOwnedAnnotation() {
		return (PrivateOwnedAnnotation) this.getResourceAttribute().addAnnotation(this.getPrivateOwnedAnnotationName());
	}

	protected void removePrivateOwnedAnnotation() {
		this.getResourceAttribute().removeAnnotation(this.getPrivateOwnedAnnotationName());
	}

	protected String getPrivateOwnedAnnotationName() {
		return PrivateOwnedAnnotation.ANNOTATION_NAME;
	}


	// ********** misc **********

	protected JavaAttributeMapping getAttributeMapping() {
		return this.parent;
	}

	protected JavaResourceAttribute getResourceAttribute() {
		return this.getAttributeMapping().getResourceAttribute();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getAnnotationTextRange();
		return (textRange != null) ? textRange : this.getAttributeMapping().getValidationTextRange();
	}

	protected TextRange getAnnotationTextRange() {
		PrivateOwnedAnnotation annotation = this.getPrivateOwnedAnnotation();
		return (annotation == null) ? null : annotation.getTextRange();
	}
}
