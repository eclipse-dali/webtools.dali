/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.common.core.internal.resource.xml.EFactoryTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne;

public class OrmOneToOneMappingDefinition
	implements OrmAttributeMappingDefinition
{
	// singleton
	private static final OrmAttributeMappingDefinition INSTANCE = 
			new OrmOneToOneMappingDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static OrmAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private OrmOneToOneMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlAttributeMapping buildResourceMapping(EFactory factory) {
		return EFactoryTools.create(
				factory, 
				OrmPackage.eINSTANCE.getXmlOneToOne(), 
				XmlOneToOne.class);
	}
	
	public OrmAttributeMapping buildContextMapping(
			OrmSpecifiedPersistentAttribute parent, 
			XmlAttributeMapping resourceMapping, 
			OrmXmlContextModelFactory factory) {
		return factory.buildOrmOneToOneMapping(parent, (XmlOneToOne) resourceMapping);
	}
	
	public boolean isSingleRelationshipMapping() {
		return true;
	}
	
	public boolean isCollectionMapping() {
		return false;
	}
}
