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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.java.JavaCustomizer;
import org.eclipse.jpt.eclipselink.core.context.java.JavaReadOnly;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmMappedSuperclass extends GenericOrmMappedSuperclass
	implements EclipseLinkMappedSuperclass
{
	protected final EclipseLinkOrmReadOnly readOnly;
	protected final EclipseLinkOrmCustomizer customizer;
	
	
	public EclipseLinkOrmMappedSuperclass(OrmPersistentType parent) {
		super(parent);
		this.readOnly = new EclipseLinkOrmReadOnly(this);
		this.customizer = getJpaFactory().buildOrmCustomizer(this);
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
		return this.customizer;
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
		this.readOnly.initialize((XmlReadOnly) mappedSuperclass, getJavaReadOnly());
		this.customizer.initialize((XmlCustomizerHolder) mappedSuperclass, getJavaCustomizer());
	}
	
	@Override
	public void update(XmlMappedSuperclass mappedSuperclass) {
		super.update(mappedSuperclass);
		this.readOnly.update((XmlReadOnly) mappedSuperclass, getJavaReadOnly());
		this.customizer.update((XmlCustomizerHolder) mappedSuperclass, getJavaCustomizer());
	}
	
	@Override
	protected EclipseLinkJavaMappedSuperclass getJavaMappedSuperclassForDefaults() {
		return (EclipseLinkJavaMappedSuperclass) super.getJavaMappedSuperclassForDefaults();
	}
	
	protected JavaReadOnly getJavaReadOnly() {
		EclipseLinkJavaMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclassForDefaults();
		return (javaMappedSuperclass == null) ? null : javaMappedSuperclass.getReadOnly();
	}
	
	protected JavaCustomizer getJavaCustomizer() {
		EclipseLinkJavaMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclassForDefaults();
		return (javaMappedSuperclass == null) ? null : javaMappedSuperclass.getCustomizer();
	}
	
	
	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		this.readOnly.validate(messages);
		this.customizer.validate(messages);
	}
}
