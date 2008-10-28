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
import org.eclipse.jpt.core.internal.context.orm.GenericOrmMappedSuperclass;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.Caching;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.ReadOnly;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCaching;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmMappedSuperclassImpl extends GenericOrmMappedSuperclass
	implements EclipseLinkOrmMappedSuperclass
{
	protected final EclipseLinkOrmReadOnly readOnly;
	
	protected final EclipseLinkOrmCustomizer customizer;
	
	protected final EclipseLinkOrmChangeTracking changeTracking;
	
	protected final EclipseLinkOrmCaching caching;
	
	protected final EclipseLinkOrmConverterHolder converterHolder;
	
	
	public EclipseLinkOrmMappedSuperclassImpl(OrmPersistentType parent) {
		super(parent);
		this.readOnly = new EclipseLinkOrmReadOnly(this);
		this.customizer = new EclipseLinkOrmCustomizer(this);
		this.changeTracking = new EclipseLinkOrmChangeTracking(this);
		this.caching = new EclipseLinkOrmCaching(this);
		this.converterHolder = new EclipseLinkOrmConverterHolder(this);
}
	
	public Caching getCaching() {
		return this.caching;
	}

	public Customizer getCustomizer() {
		return this.customizer;
	}

	public ChangeTracking getChangeTracking() {
		return this.changeTracking;
	}

	public EclipseLinkOrmReadOnly getReadOnly() {
		return this.readOnly;
	}
	
	public ConverterHolder getConverterHolder() {
		return this.converterHolder;
	}
	
	
	// **************** resource-context interaction ***************************
	
	@Override
	public XmlMappedSuperclass addToResourceModel(XmlEntityMappings entityMappings) {
		XmlMappedSuperclass mappedSuperclass = EclipseLinkOrmFactory.eINSTANCE.createXmlMappedSuperclass();
		getPersistentType().initialize(mappedSuperclass);
		entityMappings.getMappedSuperclasses().add(mappedSuperclass);
		return mappedSuperclass;
	}
	
	@Override
	public void initialize(XmlMappedSuperclass mappedSuperclass) {
		super.initialize(mappedSuperclass);
		this.readOnly.initialize((XmlReadOnly) mappedSuperclass, getJavaReadOnly());
		this.customizer.initialize((XmlCustomizerHolder) mappedSuperclass, getJavaCustomizer());
		this.changeTracking.initialize((XmlChangeTrackingHolder) mappedSuperclass, getJavaChangeTracking());
		this.caching.initialize((XmlCacheHolder) mappedSuperclass, getJavaCaching());
		this.converterHolder.initialize((XmlConvertersHolder) mappedSuperclass); 
	}
	
	@Override
	public void update(XmlMappedSuperclass mappedSuperclass) {
		super.update(mappedSuperclass);
		this.readOnly.update((XmlReadOnly) mappedSuperclass, getJavaReadOnly());
		this.customizer.update((XmlCustomizerHolder) mappedSuperclass, getJavaCustomizer());
		this.changeTracking.update((XmlChangeTrackingHolder) mappedSuperclass, getJavaChangeTracking());
		this.caching.update((XmlCacheHolder) mappedSuperclass, getJavaCaching());
		this.converterHolder.update((XmlConvertersHolder) mappedSuperclass); 
	}
	
	@Override
	protected EclipseLinkJavaMappedSuperclass getJavaMappedSuperclassForDefaults() {
		return (EclipseLinkJavaMappedSuperclass) super.getJavaMappedSuperclassForDefaults();
	}
	
	protected ReadOnly getJavaReadOnly() {
		EclipseLinkJavaMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclassForDefaults();
		return (javaMappedSuperclass == null) ? null : javaMappedSuperclass.getReadOnly();
	}
	
	protected Customizer getJavaCustomizer() {
		EclipseLinkJavaMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclassForDefaults();
		return (javaMappedSuperclass == null) ? null : javaMappedSuperclass.getCustomizer();
	}
	
	protected ChangeTracking getJavaChangeTracking() {
		EclipseLinkJavaMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclassForDefaults();
		return (javaMappedSuperclass == null) ? null : javaMappedSuperclass.getChangeTracking();
	}
	
	protected JavaCaching getJavaCaching() {
		EclipseLinkJavaMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclassForDefaults();
		return (javaMappedSuperclass == null) ? null : javaMappedSuperclass.getCaching();
	}
	
	
	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		this.readOnly.validate(messages);
		this.customizer.validate(messages);
		this.changeTracking.validate(messages);
		this.caching.validate(messages);
	}
}
