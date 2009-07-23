/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmEntity;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkOrmEntityImpl extends AbstractOrmEntity
	implements EclipseLinkOrmEntity
{
	protected final EclipseLinkOrmReadOnly readOnly;
	
	protected final EclipseLinkOrmCustomizer customizer;
	
	protected final EclipseLinkOrmChangeTracking changeTracking;
	
	protected final EclipseLinkOrmCachingImpl caching;
	
	protected final EclipseLinkOrmConverterHolder converterHolder;
	
	
	public EclipseLinkOrmEntityImpl(OrmPersistentType parent, XmlEntity resourceMapping) {
		super(parent, resourceMapping);
		this.readOnly = new EclipseLinkOrmReadOnly(this, (XmlReadOnly) this.resourceTypeMapping, getJavaReadOnly());
		this.customizer = new EclipseLinkOrmCustomizer(this, (XmlCustomizerHolder) this.resourceTypeMapping, getJavaCustomizer());
		this.changeTracking = new EclipseLinkOrmChangeTracking(this, (XmlChangeTrackingHolder) this.resourceTypeMapping, getJavaChangeTracking());
		this.caching = new EclipseLinkOrmCachingImpl(this, (XmlCacheHolder) this.resourceTypeMapping, getJavaCaching());
		this.converterHolder = new EclipseLinkOrmConverterHolder(this, (XmlConvertersHolder) this.resourceTypeMapping);
	}
	
	public EclipseLinkCaching getCaching() {
		return this.caching;
	}

	public EclipseLinkCustomizer getCustomizer() {
		return this.customizer;
	}

	public EclipseLinkChangeTracking getChangeTracking() {
		return this.changeTracking;
	}

	public EclipseLinkOrmReadOnly getReadOnly() {
		return this.readOnly;
	}
	
	public EclipseLinkConverterHolder getConverterHolder() {
		return this.converterHolder;
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
	protected EclipseLinkJavaEntity getJavaEntityForDefaults() {
		return (EclipseLinkJavaEntity) super.getJavaEntityForDefaults();
	}
	
	protected EclipseLinkReadOnly getJavaReadOnly() {
		EclipseLinkJavaEntity javaEntity = getJavaEntityForDefaults();
		return (javaEntity == null) ? null : javaEntity.getReadOnly();
	}
	
	protected EclipseLinkCustomizer getJavaCustomizer() {
		EclipseLinkJavaEntity javaEntity = getJavaEntityForDefaults();
		return (javaEntity == null) ? null : javaEntity.getCustomizer();
	}
	
	protected EclipseLinkChangeTracking getJavaChangeTracking() {
		EclipseLinkJavaEntity javaEntity = getJavaEntityForDefaults();
		return (javaEntity == null) ? null : javaEntity.getChangeTracking();
	}
	
	protected EclipseLinkJavaCaching getJavaCaching() {
		EclipseLinkJavaEntity javaEntity = getJavaEntityForDefaults();
		return (javaEntity == null) ? null : javaEntity.getCaching();
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
}
