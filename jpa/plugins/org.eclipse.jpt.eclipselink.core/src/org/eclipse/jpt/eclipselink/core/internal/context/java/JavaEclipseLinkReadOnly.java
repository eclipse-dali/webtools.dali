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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkReadOnlyAnnotation;

public class JavaEclipseLinkReadOnly
	extends AbstractJavaJpaContextNode
	implements EclipseLinkReadOnly
{
	protected Boolean specifiedReadOnly;  // TRUE or null


	public JavaEclipseLinkReadOnly(JavaEclipseLinkNonEmbeddableTypeMapping parent) {
		super(parent);
		this.specifiedReadOnly = this.buildSpecifiedReadOnly();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedReadOnly_(this.buildSpecifiedReadOnly());
	}


	// ********** read-only **********

	public boolean isReadOnly() {
		return (this.specifiedReadOnly != null) ? this.specifiedReadOnly.booleanValue() : this.isDefaultReadOnly();
	}

	public Boolean getSpecifiedReadOnly() {
		return this.specifiedReadOnly;
	}

	public void setSpecifiedReadOnly(Boolean readOnly) {
		readOnly = (readOnly == null) ? null : readOnly.booleanValue() ? readOnly : null;
		if (this.valuesAreDifferent(readOnly, this.specifiedReadOnly)) {
			EclipseLinkReadOnlyAnnotation annotation = this.getReadOnlyAnnotation();
			if (readOnly != null) {
				if (annotation == null) {
					this.addReadOnlyAnnotation();
				}
			} else {
				if (annotation != null) {
					this.removeReadOnlyAnnotation();
				}
			}

			this.setSpecifiedReadOnly_(readOnly);
		}
	}

	protected void setSpecifiedReadOnly_(Boolean readOnly) {
		Boolean old = this.specifiedReadOnly;
		this.specifiedReadOnly = readOnly;
		this.firePropertyChanged(SPECIFIED_READ_ONLY_PROPERTY, old, readOnly);
	}

	private Boolean buildSpecifiedReadOnly() {
		return (this.getReadOnlyAnnotation() == null) ? null : Boolean.TRUE;
	}

	public boolean isDefaultReadOnly() {
		return DEFAULT_READ_ONLY;
	}


	// ********** read-only annotation **********

	protected EclipseLinkReadOnlyAnnotation getReadOnlyAnnotation() {
		return (EclipseLinkReadOnlyAnnotation) this.getResourcePersistentType().getAnnotation(this.getReadOnlyAnnotationName());
	}

	protected void addReadOnlyAnnotation() {
		this.getResourcePersistentType().addAnnotation(this.getReadOnlyAnnotationName());
	}

	protected void removeReadOnlyAnnotation() {
		this.getResourcePersistentType().removeAnnotation(this.getReadOnlyAnnotationName());
	}

	protected String getReadOnlyAnnotationName() {
		return EclipseLinkReadOnlyAnnotation.ANNOTATION_NAME;
	}


	// ********** misc **********

	@Override
	public JavaEclipseLinkNonEmbeddableTypeMapping getParent() {
		return (JavaEclipseLinkNonEmbeddableTypeMapping) super.getParent();
	}

	protected JavaEclipseLinkNonEmbeddableTypeMapping getTypeMapping() {
		return this.getParent();
	}

	protected JavaPersistentType getPersistentType() {
		return this.getTypeMapping().getPersistentType();
	}

	protected JavaResourcePersistentType getResourcePersistentType() {
		return this.getTypeMapping().getResourcePersistentType();
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		EclipseLinkReadOnlyAnnotation annotation = this.getReadOnlyAnnotation();
		return (annotation == null) ? null : annotation.getTextRange(astRoot);
	}
}
