/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;

/**
 * default ORM MappedSuperclass definition
 */
public class OrmMappedSuperclassDefinition
	implements OrmTypeMappingDefinition
{	
	// singleton
	private static final OrmMappedSuperclassDefinition INSTANCE = 
			new OrmMappedSuperclassDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static OrmTypeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private OrmMappedSuperclassDefinition() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}
	
	public XmlTypeMapping buildResourceMapping(EFactory factory) {
		return EFactoryTools.create(
				factory, 
				OrmPackage.eINSTANCE.getXmlMappedSuperclass(), 
				XmlMappedSuperclass.class);
	}
	
	public OrmMappedSuperclass buildContextMapping(
			OrmPersistentType parent, 
			XmlTypeMapping resourceMapping, 
			OrmXmlContextModelFactory factory) {
		return factory.buildOrmMappedSuperclass(parent, (XmlMappedSuperclass) resourceMapping);
	}
}
