/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaEntity;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.NullJavaCacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;

public class GenericJavaEntity
	extends AbstractJavaEntity
{
	// EclipseLink holds its cacheable in its caching
	protected final Cacheable2_0 cacheable;


	public GenericJavaEntity(JavaPersistentType parent, EntityAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
		this.cacheable = this.buildCacheable();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.cacheable.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.cacheable.update(monitor);
	}


	// ********** cacheable **********

	public Cacheable2_0 getCacheable() {
		return this.cacheable;
	}

	protected Cacheable2_0 buildCacheable() {
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
		CacheableReference2_0 parentEntity = (CacheableReference2_0) this.getParentEntity();
		return (parentEntity == null) ? null : parentEntity.getCacheable();
	}
}
