/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.PrimaryKeyValidator;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmEntity;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheableHolder2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.EclipseLinkEntityPrimaryKeyValidator;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkEntityImpl
	extends AbstractOrmEntity
	implements OrmEclipseLinkEntity
{
	protected final OrmEclipseLinkReadOnly readOnly;
	
	protected final OrmEclipseLinkCustomizer customizer;
	
	protected final OrmEclipseLinkChangeTracking changeTracking;
	
	protected final OrmEclipseLinkCaching caching;
	
	protected final OrmEclipseLinkConverterHolder converterHolder;
	
	
	public OrmEclipseLinkEntityImpl(OrmPersistentType parent, XmlEntity resourceMapping) {
		super(parent, resourceMapping);
		this.readOnly = new OrmEclipseLinkReadOnly(this, (XmlReadOnly) this.resourceTypeMapping, getJavaReadOnly());
		this.customizer = new OrmEclipseLinkCustomizer(this, (XmlCustomizerHolder) this.resourceTypeMapping, getJavaCustomizer());
		this.changeTracking = new OrmEclipseLinkChangeTracking(this, (XmlChangeTrackingHolder) this.resourceTypeMapping, getJavaChangeTracking());
		this.caching = new OrmEclipseLinkCachingImpl(this, (XmlCacheHolder) this.resourceTypeMapping, (XmlCacheable_2_0) this.resourceTypeMapping, getJavaCaching());
		this.converterHolder = new OrmEclipseLinkConverterHolder(this, (XmlConvertersHolder) this.resourceTypeMapping);
	}
	
	
	@Override
	public XmlEntity getResourceTypeMapping() {
		return (XmlEntity) super.getResourceTypeMapping();
	}
	
	public boolean usesPrimaryKeyColumns() {
		return getResourceTypeMapping().getPrimaryKey() != null 
				|| usesJavaPrimaryKeyColumns();
	}
	
	public OrmEclipseLinkCaching getCaching() {
		return this.caching;
	}

	public EclipseLinkCustomizer getCustomizer() {
		return this.customizer;
	}

	public EclipseLinkChangeTracking getChangeTracking() {
		return this.changeTracking;
	}

	public OrmEclipseLinkReadOnly getReadOnly() {
		return this.readOnly;
	}
	
	public EclipseLinkConverterHolder getConverterHolder() {
		return this.converterHolder;
	}

	public OrmCacheable2_0 getCacheable() {
		return ((OrmCacheableHolder2_0) getCaching()).getCacheable();
	}
	
	public boolean calculateDefaultCacheable() {
		return ((CacheableHolder2_0) getCaching()).calculateDefaultCacheable();
	}
	
	// **************** resource-context interaction ***************************
	
	@Override
	public void update() {
		super.update();
		this.readOnly.update(getJavaReadOnly());
		this.customizer.update(getJavaCustomizer());
		this.changeTracking.update(getJavaChangeTracking());
		this.caching.update(getJavaCaching());
		this.converterHolder.update(); 
	}
	
	@Override
	protected JavaEclipseLinkEntity getJavaEntityForDefaults() {
		return (JavaEclipseLinkEntity) super.getJavaEntityForDefaults();
	}
	
	protected EclipseLinkReadOnly getJavaReadOnly() {
		JavaEclipseLinkEntity javaEntity = getJavaEntityForDefaults();
		return (javaEntity == null) ? null : javaEntity.getReadOnly();
	}
	
	protected EclipseLinkCustomizer getJavaCustomizer() {
		JavaEclipseLinkEntity javaEntity = getJavaEntityForDefaults();
		return (javaEntity == null) ? null : javaEntity.getCustomizer();
	}
	
	protected EclipseLinkChangeTracking getJavaChangeTracking() {
		JavaEclipseLinkEntity javaEntity = getJavaEntityForDefaults();
		return (javaEntity == null) ? null : javaEntity.getChangeTracking();
	}
	
	protected JavaEclipseLinkCaching getJavaCaching() {
		JavaEclipseLinkEntity javaEntity = getJavaEntityForDefaults();
		return (javaEntity == null) ? null : javaEntity.getCaching();
	}
	
	protected boolean usesJavaPrimaryKeyColumns() {
		JavaEclipseLinkEntity javaEntity = getJavaEntityForDefaults();
		return (javaEntity == null) ? false : javaEntity.usesPrimaryKeyColumns();
	}
	
	
	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.readOnly.validate(messages, reporter);
		this.customizer.validate(messages, reporter);
		this.changeTracking.validate(messages, reporter);
		this.caching.validate(messages, reporter);
	}
	
	@Override
	protected PrimaryKeyValidator buildPrimaryKeyValidator() {
		return new EclipseLinkEntityPrimaryKeyValidator(this, buildTextRangeResolver());
	}
}
