/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Cacheable2_0Annotation;

/**
 * Java cacheable
 */
public class GenericJavaCacheable2_0
	extends AbstractJavaContextModel<JavaCacheableReference2_0>
	implements Cacheable2_0
{
	protected Boolean specifiedCacheable;
	protected boolean defaultCacheable;


	public GenericJavaCacheable2_0(JavaCacheableReference2_0 parent) {
		super(parent);
		this.specifiedCacheable = this.buildSpecifiedCacheable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedCacheable_(this.buildSpecifiedCacheable());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultCacheable(this.buildDefaultCacheable());
	}


	// ********** cacheable **********

	public boolean isCacheable() {
		return (this.specifiedCacheable != null) ? this.specifiedCacheable.booleanValue() : this.defaultCacheable;
	}

	public Boolean getSpecifiedCacheable() {
		return this.specifiedCacheable;
	}

	public void setSpecifiedCacheable(Boolean cacheable) {
		if (this.valuesAreDifferent(cacheable, this.specifiedCacheable)) {
			if (cacheable == null) {
				this.removeCacheableAnnotation();
			} else {
				Cacheable2_0Annotation annotation = this.getCacheableAnnotationForUpdate();
				Boolean value = annotation.getValue();
				if (cacheable.booleanValue()) {
					if ((value != null) && ! value.booleanValue()) {  // @Cacheable(false)
						annotation.setValue(null);  // set to @Cacheable
					}
				} else {
					annotation.setValue(Boolean.FALSE);
				}
			}

			this.setSpecifiedCacheable_(cacheable);
		}
	}

	protected void setSpecifiedCacheable_(Boolean cacheable) {
		Boolean old = this.specifiedCacheable;
		this.specifiedCacheable = cacheable;
		this.firePropertyChanged(SPECIFIED_CACHEABLE_PROPERTY, old, cacheable);
	}

	private Boolean buildSpecifiedCacheable() {
		Cacheable2_0Annotation annotation = this.getCacheableAnnotation();
		if (annotation == null) {
			return null;
		}
		Boolean value = annotation.getValue();
		// @Cacheable is equivalent to @Cacheable(true)
		return (value != null) ? value : Boolean.TRUE;
	}

	public boolean isDefaultCacheable() {
		return this.defaultCacheable;
	}

	protected void setDefaultCacheable(boolean cacheable) {
		boolean old = this.defaultCacheable;
		this.defaultCacheable = cacheable;
		this.firePropertyChanged(DEFAULT_CACHEABLE_PROPERTY, old, cacheable);
	}

	protected boolean buildDefaultCacheable() {
		return this.getCacheableReference().calculateDefaultCacheable();
	}


	// ********** cacheable annotation **********

	/**
	 * Return <code>null</code> if the annotation does not exists.
	 */
	protected Cacheable2_0Annotation getCacheableAnnotation() {
		return (Cacheable2_0Annotation) this.getJavaResourceType().getAnnotation(this.getCacheableAnnotationName());
	}

	/**
	 * Build the annotation if it does not exist.
	 */
	protected Cacheable2_0Annotation getCacheableAnnotationForUpdate() {
		Cacheable2_0Annotation annotation = this.getCacheableAnnotation();
		return (annotation != null) ? annotation : this.buildCacheableAnnotation();
	}

	protected Cacheable2_0Annotation buildCacheableAnnotation() {
		return (Cacheable2_0Annotation) this.getJavaResourceType().addAnnotation(this.getCacheableAnnotationName());
	}

	protected void removeCacheableAnnotation() {
		this.getJavaResourceType().removeAnnotation(this.getCacheableAnnotationName());
	}

	protected String getCacheableAnnotationName() {
		return Cacheable2_0Annotation.ANNOTATION_NAME;
	}


	// ********** misc **********

	protected JavaCacheableReference2_0 getCacheableReference() {
		return this.parent;
	}

	protected JavaResourceType getJavaResourceType() {
		return this.getCacheableReference().getJavaResourceType();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getAnnotationTextRange();
		return (textRange != null) ? textRange : this.getCacheableReference().getValidationTextRange();
	}

	protected TextRange getAnnotationTextRange() {
		Cacheable2_0Annotation annotation = this.getCacheableAnnotation();
		return (annotation == null) ? null : annotation.getTextRange();
	}
}
