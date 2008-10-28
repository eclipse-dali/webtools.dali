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
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEmbeddable;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmEmbeddableImpl extends GenericOrmEmbeddable
	implements EclipseLinkOrmEmbeddable
{
	protected final EclipseLinkOrmCustomizer customizer;
	
	protected final EclipseLinkOrmChangeTracking changeTracking;
	
	protected final EclipseLinkOrmConverterHolder converterHolder;
	
	public EclipseLinkOrmEmbeddableImpl(OrmPersistentType parent) {
		super(parent);
		this.customizer = new EclipseLinkOrmCustomizer(this);
		this.changeTracking = new EclipseLinkOrmChangeTracking(this);
		this.converterHolder = new EclipseLinkOrmConverterHolder(this);
	}

	public Customizer getCustomizer() {
		return this.customizer;
	}
	
	public ChangeTracking getChangeTracking() {
		return this.changeTracking;
	}

	public ConverterHolder getConverterHolder() {
		return this.converterHolder;
	}

	
	
	// **************** resource-context interaction ***************************
	
	@Override
	public XmlEmbeddable addToResourceModel(XmlEntityMappings entityMappings) {
		XmlEmbeddable embeddable = EclipseLinkOrmFactory.eINSTANCE.createXmlEmbeddable();
		getPersistentType().initialize(embeddable);
		entityMappings.getEmbeddables().add(embeddable);
		return embeddable;
	}
	
	@Override
	public void initialize(XmlEmbeddable embeddable) {
		super.initialize(embeddable);
		this.customizer.initialize((XmlCustomizerHolder) embeddable, getJavaCustomizer());
		this.changeTracking.initialize((XmlChangeTrackingHolder) embeddable, getJavaChangeTracking());
		this.converterHolder.initialize((XmlConvertersHolder) embeddable); 
	}
	
	@Override
	public void update(XmlEmbeddable embeddable) {
		super.update(embeddable);
		this.customizer.update((XmlCustomizerHolder) embeddable, getJavaCustomizer());
		this.changeTracking.update((XmlChangeTrackingHolder) embeddable, getJavaChangeTracking());
		this.converterHolder.update((XmlConvertersHolder) embeddable); 
	}
	
	@Override
	protected EclipseLinkJavaEmbeddable getJavaEmbeddableForDefaults() {
		return (EclipseLinkJavaEmbeddable) super.getJavaEmbeddableForDefaults();
	}
	
	
	protected Customizer getJavaCustomizer() {
		EclipseLinkJavaEmbeddable javaEmbeddable = getJavaEmbeddableForDefaults();
		return (javaEmbeddable == null) ? null : javaEmbeddable.getCustomizer();
	}
	
	protected ChangeTracking getJavaChangeTracking() {
		EclipseLinkJavaEmbeddable javaEmbeddable = getJavaEmbeddableForDefaults();
		return (javaEmbeddable == null) ? null : javaEmbeddable.getChangeTracking();
	}
	
	
	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		this.customizer.validate(messages);
		this.changeTracking.validate(messages);
	}
}
