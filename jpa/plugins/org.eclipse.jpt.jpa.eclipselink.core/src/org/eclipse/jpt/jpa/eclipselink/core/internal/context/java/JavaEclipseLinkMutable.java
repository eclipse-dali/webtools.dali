/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkMutableAnnotation;

public class JavaEclipseLinkMutable
	extends AbstractJavaJpaContextNode
	implements EclipseLinkMutable
{
	protected Boolean specifiedMutable;
	protected boolean defaultMutable;


	public JavaEclipseLinkMutable(JavaAttributeMapping parent) {
		super(parent);
		this.specifiedMutable = this.buildSpecifiedMutable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedMutable_(this.buildSpecifiedMutable());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultMutable(this.buildDefaultMutable());
	}


	// ********** mutable **********

	public boolean isMutable() {
		return (this.specifiedMutable != null) ? this.specifiedMutable.booleanValue() : this.defaultMutable;
	}

	public Boolean getSpecifiedMutable() {
		return this.specifiedMutable;
	}

	public void setSpecifiedMutable(Boolean mutable) {
		if (this.valuesAreDifferent(mutable, this.specifiedMutable)) {
			EclipseLinkMutableAnnotation annotation = this.getMutableAnnotation();
			if (mutable == null) {
				if (annotation != null) {
					this.removeMutableAnnotation();
				}
			} else {
				if (annotation == null) {
					annotation = this.addMutableAnnotation();
				}
				Boolean annotationValue = annotation.getValue();
				boolean annotationBooleanValue = (annotationValue == null) ? true : annotationValue.booleanValue();
				if (annotationBooleanValue != mutable.booleanValue()) {
					annotation.setValue(mutable);
				}
			}

			this.setSpecifiedMutable_(mutable);
		}
	}

	protected void setSpecifiedMutable_(Boolean mutable) {
		Boolean old = this.specifiedMutable;
		this.specifiedMutable = mutable;
		this.firePropertyChanged(SPECIFIED_MUTABLE_PROPERTY, old, mutable);
	}

	protected Boolean buildSpecifiedMutable() {
		EclipseLinkMutableAnnotation annotation = this.getMutableAnnotation();
		if (annotation == null) {
			return null;
		}

		// @Mutable is equivalent to @Mutable(true)
		Boolean annotationValue = annotation.getValue();
		return (annotationValue == null) ? Boolean.TRUE : annotationValue;
	}

	public boolean isDefaultMutable() {
		return this.defaultMutable;
	}

	protected void setDefaultMutable(boolean mutable) {
		boolean old = this.defaultMutable;
		this.defaultMutable = mutable;
		this.firePropertyChanged(DEFAULT_MUTABLE_PROPERTY, old, mutable);
	}

	protected boolean buildDefaultMutable() {
		JavaEclipseLinkPersistentAttribute javaAttribute = this.getPersistentAttribute();
		if (javaAttribute.typeIsDateOrCalendar()) {
			Boolean puTemporalMutable = this.getPersistenceUnit().getOptions().getTemporalMutable();
			return (puTemporalMutable == null) ? false : puTemporalMutable.booleanValue();
		}
		return javaAttribute.typeIsSerializable();
	}


	// ********** mutable annotation **********

	protected EclipseLinkMutableAnnotation getMutableAnnotation() {
		return (EclipseLinkMutableAnnotation) this.getResourceAttribute().getAnnotation(this.getMutableAnnotationName());
	}

	protected EclipseLinkMutableAnnotation addMutableAnnotation() {
		return (EclipseLinkMutableAnnotation) this.getResourceAttribute().addAnnotation(this.getMutableAnnotationName());
	}

	protected void removeMutableAnnotation() {
		this.getResourceAttribute().removeAnnotation(this.getMutableAnnotationName());
	}

	protected String getMutableAnnotationName() {
		return EclipseLinkMutableAnnotation.ANNOTATION_NAME;
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

	protected JavaEclipseLinkPersistentAttribute getPersistentAttribute() {
		return (JavaEclipseLinkPersistentAttribute) this.getAttributeMapping().getPersistentAttribute();
	}

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getAnnotationTextRange(astRoot);
		return (textRange != null) ? textRange : this.getAttributeMapping().getValidationTextRange(astRoot);
	}

	protected TextRange getAnnotationTextRange(CompilationUnit astRoot) {
		EclipseLinkMutableAnnotation annotation = this.getMutableAnnotation();
		return (annotation == null) ? null : annotation.getTextRange(astRoot);
	}
}
