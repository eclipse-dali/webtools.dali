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
import org.eclipse.jpt.core.internal.context.orm.GenericEntityMappings;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkEntityMappingsImpl
	extends GenericEntityMappings
	implements EclipseLinkEntityMappings
{

	protected final EclipseLinkOrmConverterHolder converterHolder;
	
	public EclipseLinkEntityMappingsImpl(EclipseLinkOrmXml parent, XmlEntityMappings xmlEntityMappings) {
		super(parent, xmlEntityMappings);
		this.converterHolder = new EclipseLinkOrmConverterHolder(this);
		this.converterHolder.initialize(xmlEntityMappings);
	}
	
	@Override
	protected OrmPersistentType buildPersistentType(String mappingKey) {
		return getJpaFactory().buildEclipseLinkOrmPersistentType(this, mappingKey);
	}
	
	// **************** JpaNode impl *******************************************
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}
	
	@Override
	public EclipseLinkOrmXml getParent() {
		return (EclipseLinkOrmXml) super.getParent();
	}	
	
	
	// **************** EclipseLinkEntityMappings impl **********************************

	public ConverterHolder getConverterHolder() {
		return this.converterHolder;
	}
	
	@Override
	public void update() {
		super.update();
		this.converterHolder.update((XmlEntityMappings) this.xmlEntityMappings);
	}
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		this.converterHolder.validate(messages);
	}
}
