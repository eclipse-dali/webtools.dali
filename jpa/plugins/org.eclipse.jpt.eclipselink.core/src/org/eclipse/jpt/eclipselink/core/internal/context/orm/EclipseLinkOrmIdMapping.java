/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmIdMapping;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkIdMapping;
import org.eclipse.jpt.eclipselink.core.context.Mutable;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlId;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmIdMapping extends GenericOrmIdMapping
	implements EclipseLinkIdMapping
{
	protected EclipseLinkOrmMutable mutable;
	
	
	public EclipseLinkOrmIdMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.mutable = new EclipseLinkOrmMutable(this);
	}
	
	
	public Mutable getMutable() {
		return this.mutable;
	}

	@Override
	protected OrmConverter buildSpecifiedConverter(String converterType) {
		OrmConverter ormConverter = super.buildSpecifiedConverter(converterType);
		if (ormConverter != null) {
			return ormConverter;
		}
		if (converterType == Convert.ECLIPSE_LINK_CONVERTER) {
			return new EclipseLinkOrmConvert(this, (XmlId) this.resourceAttributeMapping);
		}
		return null;
	}
	
	@Override
	protected String specifiedConverterType() {
		String specifiedConverterType = super.specifiedConverterType();
		if (specifiedConverterType != null) {
			return specifiedConverterType;
		}
		if (((XmlId) this.resourceAttributeMapping).getConvert() != null) {
			return Convert.ECLIPSE_LINK_CONVERTER;
		}
		return null;
	}
	
	// **************** resource-context interaction ***************************
	
	@Override
	public XmlId addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlId id = EclipseLinkOrmFactory.eINSTANCE.createXmlIdImpl();
		getPersistentAttribute().initialize(id);
		typeMapping.getAttributes().getIds().add(id);
		return id;
	}
	
	@Override
	public void initialize(XmlAttributeMapping xmlAttributeMapping) {
		super.initialize(xmlAttributeMapping);	
		this.mutable.initialize((XmlMutable) this.resourceAttributeMapping);
	}
	
	@Override
	public void update() {
		super.update();
		this.mutable.update();
	}
	
	
	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		// TODO - mutable validation
	}
}
