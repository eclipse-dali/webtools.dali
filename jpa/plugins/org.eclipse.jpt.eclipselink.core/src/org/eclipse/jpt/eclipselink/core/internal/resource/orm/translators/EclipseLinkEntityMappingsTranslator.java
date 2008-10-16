/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.jpt.core.internal.resource.orm.translators.EntityMappingsTranslator;
import org.eclipse.wst.common.internal.emf.resource.ConstantAttributeTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkEntityMappingsTranslator extends EntityMappingsTranslator
	implements EclipseLinkOrmXmlMapper
{
	public static EclipseLinkEntityMappingsTranslator INSTANCE = new EclipseLinkEntityMappingsTranslator();
	
	
	public EclipseLinkEntityMappingsTranslator() {
		super(ENTITY_MAPPINGS, ORM_PKG.getXmlEntityMappings());
	}
	
	
	@Override
	protected Translator createNamespaceTranslator() {
		return new ConstantAttributeTranslator(XML_NS, ECLIPSELINK_ORM_NS_URL);
	}
	
	@Override
	protected Translator createSchemaLocationTranslator() {
		return new ConstantAttributeTranslator(XSI_SCHEMA_LOCATION, ECLIPSELINK_ORM_NS_URL + ' ' + ECLIPSELINK_ORM_SCHEMA_LOC_1_0);
	}
	
	@Override
	protected Translator createEntityTranslator() {
		return new EclipseLinkEntityTranslator(ENTITY, ORM_PKG.getXmlEntityMappings_Entities());
	}
	
	@Override
	protected Translator createMappedSuperclassTranslator() {
		return new EclipseLinkMappedSuperclassTranslator(MAPPED_SUPERCLASS, ORM_PKG.getXmlEntityMappings_MappedSuperclasses());
	}
}
