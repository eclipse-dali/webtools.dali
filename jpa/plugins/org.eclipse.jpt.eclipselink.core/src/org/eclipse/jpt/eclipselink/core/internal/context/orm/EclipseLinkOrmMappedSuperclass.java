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

import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmMappedSuperclass;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.Caching;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.JavaReadOnly;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly;

public class EclipseLinkOrmMappedSuperclass extends GenericOrmMappedSuperclass
	implements EclipseLinkMappedSuperclass
{
	protected final EclipseLinkOrmReadOnly readOnly;
	
	
	public EclipseLinkOrmMappedSuperclass(OrmPersistentType parent) {
		super(parent);
		this.readOnly = getJpaFactory().buildOrmReadOnly(this);
	}
	
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}
	
	public Caching getCaching() {
		// TODO Auto-generated method stub
		return null;
	}

	public ChangeTracking getChangeTracking() {
		// TODO Auto-generated method stub
		return null;
	}

	public Customizer getCustomizer() {
		// TODO Auto-generated method stub
		return null;
	}

	public EclipseLinkOrmReadOnly getReadOnly() {
		return this.readOnly;
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
		getReadOnly().initialize((XmlReadOnly) mappedSuperclass, getJavaReadOnly());
	}
	
	@Override
	public void update(XmlMappedSuperclass mappedSuperclass) {
		super.update(mappedSuperclass);
		getReadOnly().update((XmlReadOnly) mappedSuperclass, getJavaReadOnly());
	}
	
	@Override
	public EclipseLinkJavaMappedSuperclass getJavaMappedSuperclass() {
		return (EclipseLinkJavaMappedSuperclass) super.getJavaMappedSuperclass();
	}
	
	protected JavaReadOnly getJavaReadOnly() {
		EclipseLinkJavaMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclass();
		return (javaMappedSuperclass == null) ? null : javaMappedSuperclass.getReadOnly();
	}
}
