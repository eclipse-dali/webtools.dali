/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.common.core.internal.resource.xml.EFactoryTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasicCollection;

public class EclipseLinkOrmBasicCollectionMappingDefinition
	implements OrmAttributeMappingDefinition
{
	// singleton
	private static final EclipseLinkOrmBasicCollectionMappingDefinition INSTANCE = 
			new EclipseLinkOrmBasicCollectionMappingDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmBasicCollectionMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlAttributeMapping buildResourceMapping(EFactory factory) {
		return EFactoryTools.create(
				factory, 
				EclipseLinkOrmPackage.eINSTANCE.getXmlBasicCollection(), 
				XmlBasicCollection.class);
	}
	
	public OrmAttributeMapping buildContextMapping(
			OrmSpecifiedPersistentAttribute parent, 
			XmlAttributeMapping resourceMapping, 
			OrmXmlContextModelFactory factory) {
		return ((EclipseLinkOrmXmlContextModelFactory) factory).
				buildOrmEclipseLinkBasicCollectionMapping(parent, (XmlBasicCollection) resourceMapping);
	}
	
	public boolean isSingleRelationshipMapping() {
		return false;
	}
	
	public boolean isCollectionMapping() {
		return false;
	}
}
