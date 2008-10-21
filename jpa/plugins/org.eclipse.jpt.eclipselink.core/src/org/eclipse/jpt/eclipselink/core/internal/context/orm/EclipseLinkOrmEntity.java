/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.eclipselink.core.context.Caching;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCustomizer;
import org.eclipse.jpt.eclipselink.core.context.java.JavaReadOnly;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmEntity extends GenericOrmEntity
	implements EclipseLinkEntity
{
	protected final EclipseLinkOrmReadOnly readOnly;
	protected final EclipseLinkOrmCustomizer customizer;
	protected final EclipseLinkOrmCaching caching;
	
	
	public EclipseLinkOrmEntity(OrmPersistentType parent) {
		super(parent);
		this.readOnly = new EclipseLinkOrmReadOnly(this);
		this.customizer = new EclipseLinkOrmCustomizer(this);
		this.caching = new EclipseLinkOrmCaching(this);
	}
	
	public Caching getCaching() {
		return this.caching;
	}

	public ChangeTracking getChangeTracking() {
		// TODO Auto-generated method stub
		return null;
	}

	public Customizer getCustomizer() {
		return this.customizer;
	}

	public EclipseLinkOrmReadOnly getReadOnly() {
		return this.readOnly;
	}
	
	
	// **************** resource-context interaction ***************************
	
	@Override
	public XmlEntity addToResourceModel(XmlEntityMappings entityMappings) {
		XmlEntity entity = EclipseLinkOrmFactory.eINSTANCE.createXmlEntity();
		getPersistentType().initialize(entity);
		entityMappings.getEntities().add(entity);
		return entity;
	}
	
	@Override
	public void initialize(XmlEntity entity) {
		super.initialize(entity);		
		this.readOnly.initialize((XmlReadOnly) entity, getJavaReadOnly());
		this.customizer.initialize((XmlCustomizerHolder) entity, getJavaCustomizer());
		this.caching.initialize((XmlCacheHolder) entity, getJavaCaching());
	}
	
	@Override
	public void update(XmlEntity entity) {
		super.update(entity);
		this.readOnly.update((XmlReadOnly) entity, getJavaReadOnly());
		this.customizer.update((XmlCustomizerHolder) entity, getJavaCustomizer());
		this.caching.update((XmlCacheHolder) entity, getJavaCaching());
	}
	
	@Override
	protected EclipseLinkJavaEntity getJavaEntityForDefaults() {
		return (EclipseLinkJavaEntity) super.getJavaEntityForDefaults();
	}
	
	protected JavaReadOnly getJavaReadOnly() {
		EclipseLinkJavaEntity javaEntity = getJavaEntityForDefaults();
		return (javaEntity == null) ? null : javaEntity.getReadOnly();
	}
	
	protected JavaCustomizer getJavaCustomizer() {
		EclipseLinkJavaEntity javaEntity = getJavaEntityForDefaults();
		return (javaEntity == null) ? null : javaEntity.getCustomizer();
	}
	
	protected JavaCaching getJavaCaching() {
		EclipseLinkJavaEntity javaEntity = getJavaEntityForDefaults();
		return (javaEntity == null) ? null : javaEntity.getCaching();
	}
	
	
	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		this.readOnly.validate(messages);
		this.customizer.validate(messages);
		this.caching.validate(messages);
	}
}
