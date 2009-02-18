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
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.orm.AbstractEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkEntityMappingsImpl
	extends AbstractEntityMappings
	implements EclipseLinkEntityMappings
{

	protected final EclipseLinkOrmConverterHolder converterHolder;
	
	public EclipseLinkEntityMappingsImpl(OrmXml parent, XmlEntityMappings resource) {
		super(parent, resource);
		this.converterHolder = new EclipseLinkOrmConverterHolder(this, (XmlEntityMappings) this.xmlEntityMappings);
	}
	
	@Override
	protected PersistenceUnitMetadata buildPersistenceUnitMetadata() {
		return 	getJpaFactory().buildEclipseLinkPersistenceUnitMetadata(this, (XmlEntityMappings) this.xmlEntityMappings);
	}	
	
	@Override
	protected OrmPersistentType buildPersistentType(XmlTypeMapping resourceMapping) {
		return getJpaFactory().buildEclipseLinkOrmPersistentType(this, resourceMapping);
	}
	
	// **************** JpaNode impl *******************************************
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory) super.getJpaFactory();
	}
	
	// **************** EclipseLinkEntityMappings impl **********************************

	public ConverterHolder getConverterHolder() {
		return this.converterHolder;
	}
	
	
	@Override
	public void update() {
		super.update();
		this.converterHolder.update();
	}
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.converterHolder.validate(messages, reporter);
	}
}
