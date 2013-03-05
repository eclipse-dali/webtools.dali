/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.common.core.resource.xml.EmfTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlBasic;

public class OrmBasicMappingDefinition
	implements OrmAttributeMappingDefinition
{
	// singleton
	private static final OrmAttributeMappingDefinition INSTANCE = 
			new OrmBasicMappingDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static OrmAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private OrmBasicMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlAttributeMapping buildResourceMapping(EFactory factory) {
		return EmfTools.create(
				factory, 
				OrmPackage.eINSTANCE.getXmlBasic(), 
				XmlBasic.class);
	}
	
	public OrmBasicMapping buildContextMapping(
			OrmPersistentAttribute parent, 
			XmlAttributeMapping resourceMapping, 
			OrmXmlContextModelFactory factory) {
		return factory.buildOrmBasicMapping(parent, (XmlBasic) resourceMapping);
	}
	
	public boolean isSingleRelationshipMapping() {
		return false;
	}
	
	public boolean isCollectionMapping() {
		return false;
	}
}
