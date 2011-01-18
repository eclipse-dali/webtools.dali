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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJoinFetchAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType;

public class JavaEclipseLinkJoinFetch
	extends AbstractJavaJpaContextNode
	implements EclipseLinkJoinFetch
{
	protected EclipseLinkJoinFetchType value;


	public JavaEclipseLinkJoinFetch(JavaAttributeMapping parent) {
		super(parent);
		this.value = this.buildValue();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setValue_(this.buildValue());
	}


	// ********** value **********

	public EclipseLinkJoinFetchType getValue() {
		return this.value;
	}

	public void setValue(EclipseLinkJoinFetchType value) {
		if (this.valuesAreDifferent(value, this.value)) {
			EclipseLinkJoinFetchAnnotation annotation = this.getJoinFetchAnnotation();
			if (value == null) {
				if (annotation != null) {
					this.removeJoinFetchAnnotation();
				}
			} else {
				if (annotation == null) {
					annotation = this.addJoinFetchAnnotation();
				}
				annotation.setValue(EclipseLinkJoinFetchType.toJavaResourceModel(value));
			}

			this.setValue_(value);
		}
	}

	protected void setValue_(EclipseLinkJoinFetchType value) {
		EclipseLinkJoinFetchType old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
	}

	private EclipseLinkJoinFetchType buildValue() {
		EclipseLinkJoinFetchAnnotation annotation = this.getJoinFetchAnnotation();
		if (annotation == null) {
			return null;
		}
		JoinFetchType annotationValue = annotation.getValue();
		return (annotationValue != null) ?
				EclipseLinkJoinFetchType.fromJavaResourceModel(annotationValue) :
				this.getDefaultValue(); // @JoinFetch is equivalent to @JoinFetch(JoinFetch.INNER)
	}

	protected EclipseLinkJoinFetchType getDefaultValue() {
		return DEFAULT_VALUE;
	}


	// ********** join fetch annotation **********

	protected EclipseLinkJoinFetchAnnotation getJoinFetchAnnotation() {
		return (EclipseLinkJoinFetchAnnotation) this.getResourcePersistentAttribute().getAnnotation(this.getJoinFetchAnnotationName());
	}

	protected EclipseLinkJoinFetchAnnotation addJoinFetchAnnotation() {
		return (EclipseLinkJoinFetchAnnotation) this.getResourcePersistentAttribute().addAnnotation(this.getJoinFetchAnnotationName());
	}

	protected void removeJoinFetchAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(this.getJoinFetchAnnotationName());
	}

	protected String getJoinFetchAnnotationName() {
		return EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME;
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
		EclipseLinkJoinFetchAnnotation annotation = this.getJoinFetchAnnotation();
		return (annotation == null) ? null : annotation.getTextRange(astRoot);
	}
}
