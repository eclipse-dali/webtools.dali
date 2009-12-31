/*******************************************************************************
 *  Copyright (c) 2008, 2009 Oracle. 
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
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmBasicMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkBasicMapping extends AbstractOrmBasicMapping<XmlBasic>
	implements EclipseLinkBasicMapping
{
	protected OrmEclipseLinkMutable mutable;
	
	
	public OrmEclipseLinkBasicMapping(OrmPersistentAttribute parent, XmlBasic resourceMapping) {
		super(parent, resourceMapping);
		this.mutable = new OrmEclipseLinkMutable(this, this.resourceAttributeMapping);
	}
	
	
	public EclipseLinkMutable getMutable() {
		return this.mutable;
	}

	@Override
	protected OrmConverter buildSpecifiedConverter(String converterType) {
		OrmConverter ormConverter = super.buildSpecifiedConverter(converterType);
		if (ormConverter != null) {
			return ormConverter;
		}
		if (this.valuesAreEqual(converterType, EclipseLinkConvert.ECLIPSE_LINK_CONVERTER)) {
			return new OrmEclipseLinkConvert(this, this.resourceAttributeMapping);
		}
		throw new IllegalArgumentException();
	}

	@Override
	protected String getResourceConverterType() {
		//check @Convert first, this is the order that EclipseLink searches
		if (this.resourceAttributeMapping.getConvert() != null) {
			return EclipseLinkConvert.ECLIPSE_LINK_CONVERTER;
		}
		return super.getResourceConverterType();
	}
	
	
	// **************** resource-context interaction ***************************
	
	@Override
	public void update() {
		super.update();
		this.mutable.update();
	}
	
	
	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// TODO - mutable validation
	}
}
