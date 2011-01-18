/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaEntity;
import org.eclipse.jpt.core.internal.jpa2.context.java.NullJavaCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.resource.java.EntityAnnotation;

public class GenericJavaEntity
	extends AbstractJavaEntity
{
	// EclipseLink holds its cacheable in its caching
	protected final JavaCacheable2_0 cacheable;


	public GenericJavaEntity(JavaPersistentType parent, EntityAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
		this.cacheable = this.buildCacheable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.cacheable.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.cacheable.update();
	}


	// ********** cacheable **********

	public JavaCacheable2_0 getCacheable() {
		return this.cacheable;
	}

	protected JavaCacheable2_0 buildCacheable() {
		return this.isJpa2_0Compatible() ?
				this.getJpaFactory2_0().buildJavaCacheable(this) :
				new NullJavaCacheable2_0(this);
	}

	public boolean calculateDefaultCacheable() {
		Cacheable2_0 parentCacheable = this.getParentCacheable();
		return (parentCacheable != null) ?
				parentCacheable.isCacheable() :
				((PersistenceUnit2_0) this.getPersistenceUnit()).calculateDefaultCacheable();
	}

	protected Cacheable2_0 getParentCacheable() {
		CacheableHolder2_0 parentEntity = (CacheableHolder2_0) this.getParentEntity();
		return (parentEntity == null) ? null : parentEntity.getCacheable();
	}
}
