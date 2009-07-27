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
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmEmbeddable;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkEmbeddable;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkEmbeddable;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkEmbeddableImpl extends AbstractOrmEmbeddable
	implements OrmEclipseLinkEmbeddable
{
	protected final OrmEclipseLinkCustomizer customizer;
	
	protected final OrmEclipseLinkChangeTracking changeTracking;
	
	protected final OrmEclipseLinkConverterHolder converterHolder;
	
	public OrmEclipseLinkEmbeddableImpl(OrmPersistentType parent, XmlEmbeddable resourceMapping) {
		super(parent, resourceMapping);
		this.customizer = new OrmEclipseLinkCustomizer(this, (XmlCustomizerHolder) this.resourceTypeMapping, getJavaCustomizer());
		this.changeTracking = new OrmEclipseLinkChangeTracking(this, (XmlChangeTrackingHolder) this.resourceTypeMapping, getJavaChangeTracking());
		this.converterHolder = new OrmEclipseLinkConverterHolder(this, (XmlConvertersHolder) this.resourceTypeMapping); 
	}

	public EclipseLinkCustomizer getCustomizer() {
		return this.customizer;
	}
	
	public EclipseLinkChangeTracking getChangeTracking() {
		return this.changeTracking;
	}

	public EclipseLinkConverterHolder getConverterHolder() {
		return this.converterHolder;
	}

	
	
	// **************** resource-context interaction ***************************

	@Override
	public void update() {
		super.update();
		this.customizer.update(getJavaCustomizer());
		this.changeTracking.update(getJavaChangeTracking());
		this.converterHolder.update(); 
	}
	
	@Override
	protected JavaEclipseLinkEmbeddable getJavaEmbeddableForDefaults() {
		return (JavaEclipseLinkEmbeddable) super.getJavaEmbeddableForDefaults();
	}
	
	
	protected EclipseLinkCustomizer getJavaCustomizer() {
		JavaEclipseLinkEmbeddable javaEmbeddable = getJavaEmbeddableForDefaults();
		return (javaEmbeddable == null) ? null : javaEmbeddable.getCustomizer();
	}
	
	protected EclipseLinkChangeTracking getJavaChangeTracking() {
		JavaEclipseLinkEmbeddable javaEmbeddable = getJavaEmbeddableForDefaults();
		return (javaEmbeddable == null) ? null : javaEmbeddable.getChangeTracking();
	}
	
	
	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.customizer.validate(messages, reporter);
		this.changeTracking.validate(messages, reporter);
	}
}
