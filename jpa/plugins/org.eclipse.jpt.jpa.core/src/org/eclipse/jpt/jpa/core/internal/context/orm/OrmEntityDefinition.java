/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.core.resource.xml.EmfTools;

/**
 * default ORM Entity provider
 */
public class OrmEntityDefinition
	implements OrmTypeMappingDefinition
{	
	// singleton
	private static final OrmEntityDefinition INSTANCE = 
			new OrmEntityDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static OrmTypeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private OrmEntityDefinition() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}
	
	public XmlTypeMapping buildResourceMapping(EFactory factory) {
		return EmfTools.create(
				factory, 
				OrmPackage.eINSTANCE.getXmlEntity(), 
				XmlEntity.class);
	}
	
	public OrmEntity buildContextMapping(
			OrmPersistentType parent, 
			XmlTypeMapping resourceMapping, 
			OrmXmlContextNodeFactory factory) {
		return factory.buildOrmEntity(parent, (XmlEntity) resourceMapping);
	}
}
