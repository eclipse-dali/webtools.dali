/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.java.JavaCacheable2_0;
import org.eclipse.jpt.core.jpa2.resource.java.Cacheable2_0Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaCacheable2_0
	extends AbstractJavaJpaContextNode 
	implements JavaCacheable2_0
{
	protected boolean defaultCacheable;
	protected Boolean specifiedCacheable;
	
	protected JavaResourcePersistentType resourcePersistentType;
	
	public GenericJavaCacheable2_0(JavaTypeMapping parent) {
		super(parent);
	}

	@Override
	public JavaTypeMapping getParent() {
		return (JavaTypeMapping) super.getParent();
	}
	
	protected String getCacheableAnnotationName() {
		return Cacheable2_0Annotation.ANNOTATION_NAME;
	}
	
	protected Cacheable2_0Annotation getResourceCacheable() {
		return (Cacheable2_0Annotation) this.resourcePersistentType.getAnnotation(getCacheableAnnotationName());
	}
	
	protected void addResourceCacheable() {
		this.resourcePersistentType.addAnnotation(getCacheableAnnotationName());
	}
	
	protected void removeResourceCacheable() {
		this.resourcePersistentType.removeAnnotation(getCacheableAnnotationName());
	}

	protected boolean calculateDefaultCacheable() {
		return false;
		//TODO
//		JavaEclipseLinkPersistentAttribute javaAttribute = (JavaEclipseLinkPersistentAttribute) this.getAttributeMapping().getPersistentAttribute();
//		if (javaAttribute.typeIsDateOrCalendar()) {
//			Boolean persistenceUnitDefaultMutable = this.getPersistenceUnit().getOptions().getTemporalMutable();
//			return persistenceUnitDefaultMutable == null ? false : persistenceUnitDefaultMutable.booleanValue();
//		}
//		return javaAttribute.typeIsSerializable();
	}

	public boolean isCacheable() {
		return this.specifiedCacheable != null ? this.specifiedCacheable.booleanValue() : this.defaultCacheable; 
	}
	
	public boolean isDefaultCacheable() {
		return this.defaultCacheable;
	}
	
	protected void setDefaultCacheable(boolean newDefaultCacheable) {
		boolean oldDefaultCacheable = this.defaultCacheable;
		this.defaultCacheable = newDefaultCacheable;
		firePropertyChanged(DEFAULT_CACHEABLE_PROPERTY, oldDefaultCacheable, newDefaultCacheable);
	}
	
	public Boolean getSpecifiedCacheable() {
		return this.specifiedCacheable;
	}
	
	public void setSpecifiedCacheable(Boolean newSpecifiedCacheable) {
		if (this.specifiedCacheable == newSpecifiedCacheable) {
			return;
		}
		Boolean oldSpecifiedCacheable = this.specifiedCacheable;
		this.specifiedCacheable = newSpecifiedCacheable;

		if (newSpecifiedCacheable != null) {
			if (getResourceCacheable() == null) {
				addResourceCacheable();
			}
			if (newSpecifiedCacheable.booleanValue()) {
				if (getResourceCacheable().getValue() == Boolean.FALSE) {
					getResourceCacheable().setValue(null);
				}
			}
			else {
				getResourceCacheable().setValue(Boolean.FALSE);
			}
		}
		else {
			removeResourceCacheable();
		}
		firePropertyChanged(SPECIFIED_CACHEABLE_PROPERTY, oldSpecifiedCacheable, newSpecifiedCacheable);
	}
	
	protected void setSpecifiedCacheable_(Boolean newSpecifiedCacheable) {
		Boolean oldSpecifiedCacheable = this.specifiedCacheable;
		this.specifiedCacheable = newSpecifiedCacheable;
		firePropertyChanged(SPECIFIED_CACHEABLE_PROPERTY, oldSpecifiedCacheable, newSpecifiedCacheable);
	}
	
	public void initialize(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		Cacheable2_0Annotation resourceCacheable = this.getResourceCacheable();
		this.specifiedCacheable = this.specifiedCacheable(resourceCacheable);
		this.defaultCacheable = this.calculateDefaultCacheable();
	}
	
	public void update(JavaResourcePersistentType jrpt) {
		this.resourcePersistentType = jrpt;
		Cacheable2_0Annotation resourceCacheable = this.getResourceCacheable();
		this.setSpecifiedCacheable_(this.specifiedCacheable(resourceCacheable));
		this.setDefaultCacheable(this.calculateDefaultCacheable());
	}
	
	private Boolean specifiedCacheable(Cacheable2_0Annotation resourceCacheable) {
		if (resourceCacheable == null) {
			return null;
		}
		if (resourceCacheable.getValue() == null) { //@Cacheable is equivalent to @Cacheable(true)
			return Boolean.TRUE;
		}
		return resourceCacheable.getValue();
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		Cacheable2_0Annotation resourceCacheable = this.getResourceCacheable();
		return resourceCacheable == null ? null : resourceCacheable.getTextRange(astRoot);
	}
}
