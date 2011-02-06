/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmEntity;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.NullOrmCacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlCacheable_2_0;

public class GenericOrmEntity
	extends AbstractOrmEntity<XmlEntity>
{
	// EclipseLink holds its cacheable in its caching
	protected final OrmCacheable2_0 cacheable;


	public GenericOrmEntity(OrmPersistentType parent, XmlEntity xmlEntity) {
		super(parent, xmlEntity);
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

	public OrmCacheable2_0 getCacheable() {
		return this.cacheable;
	}

	protected OrmCacheable2_0 buildCacheable() {
		return this.isJpa2_0Compatible() ?
				this.getContextNodeFactory2_0().buildOrmCacheable(this) :
				new NullOrmCacheable2_0(this);
	}

	public boolean calculateDefaultCacheable() {
		CacheableHolder2_0 javaEntity = (CacheableHolder2_0) this.getJavaTypeMappingForDefaults();
		if (javaEntity != null) {
			return javaEntity.getCacheable().isCacheable();
		}

		Cacheable2_0 parentCacheable = this.getParentCacheable();
		return (parentCacheable != null) ?
				parentCacheable.isCacheable() :
				((PersistenceUnit2_0) this.getPersistenceUnit()).calculateDefaultCacheable();
	}

	protected Cacheable2_0 getParentCacheable() {
		CacheableHolder2_0 parentEntity = (CacheableHolder2_0) this.getParentEntity();
		return (parentEntity == null) ? null : parentEntity.getCacheable();
	}

	public XmlCacheable_2_0 getXmlCacheable() {
		return this.getXmlTypeMapping();
	}
}
