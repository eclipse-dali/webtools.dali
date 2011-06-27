/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;

public abstract class AbstractJavaAttributeMapping<A extends Annotation>
	extends AbstractJavaContextNode
	implements JaxbAttributeMapping
{

	protected boolean default_;

	protected AbstractJavaAttributeMapping(JaxbPersistentAttribute parent) {
		super(parent);
		this.default_ = this.buildDefault();
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.updateDefault();
	}


	// ********** annotation **********

	@SuppressWarnings("unchecked")
	public A getMappingAnnotation() {
		return (A) this.getAnnotation_();
	}

	protected Annotation getAnnotation_() {
		return this.getJavaResourceAttribute().getAnnotation(this.getAnnotationName());
	}

	protected abstract String getAnnotationName();

	/**
	 * This method should only be called on mappings that can occur by default
	 * (e.g. <code>XmlElement</code>).
	 */
	public A getAnnotationForUpdate() {
		A annotation = this.getMappingAnnotation();
		if (annotation == null) {
			this.getPersistentAttribute().setMappingKey(this.getKey());
			annotation = this.getMappingAnnotation();
			if (annotation == null) {
				throw new IllegalStateException("missing annotation: " + this); //$NON-NLS-1$
			}
		}
		return annotation;
	}


	// ********** default **********

	public boolean isDefault() {
		return this.default_;
	}

	protected void setDefault(boolean default_) {
		boolean old = this.default_;
		this.default_ = default_;
		this.firePropertyChanged(DEFAULT_PROPERTY, old, default_);
	}

	public void updateDefault() {
		this.setDefault(this.buildDefault());
	}

	protected boolean buildDefault() {
		return this.getMappingAnnotation() == null;
	}


	// ********** validation **********

	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getAnnotation_() == null ? getJavaResourceAttribute().getNameTextRange(astRoot) : getAnnotation_().getTextRange(astRoot);
	}


	// ********** misc **********

	@Override
	public JaxbPersistentAttribute getParent() {
		return (JaxbPersistentAttribute) super.getParent();
	}

	public JaxbPersistentAttribute getPersistentAttribute() {
		return this.getParent();
	}
	
	public JaxbPersistentClass getPersistentClass() {
		return getPersistentAttribute().getParent();
	}
	
	public JaxbPackage getJaxbPackage() {
		return getPersistentClass().getJaxbPackage();
	}

	public JavaResourceAttribute getJavaResourceAttribute() {
		return this.getPersistentAttribute().getJavaResourceAttribute();
	}

	public String getJavaResourceAttributeType() {
		return this.getPersistentAttribute().getJavaResourceAttributeTypeName();
	}
	
	public String getAttributeTypeName() {
		return getJavaResourceAttributeType();
	}
	
	public Iterable<String> getDirectlyReferencedTypeNames() {
		return new SingleElementIterable(getPersistentAttribute().getJavaResourceAttributeTypeName());
	}
}
