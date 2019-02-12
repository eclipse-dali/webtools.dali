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
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;

/**
 * default ORM Embeddable provider
 */
public class OrmEmbeddableDefinition
	implements OrmTypeMappingDefinition
{
	// singleton
	private static final OrmEmbeddableDefinition INSTANCE = 
			new OrmEmbeddableDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static OrmTypeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private OrmEmbeddableDefinition() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}
	
	public XmlTypeMapping buildResourceMapping(EFactory factory) {
		return EFactoryTools.create(
				factory, 
				OrmPackage.eINSTANCE.getXmlEmbeddable(), 
				XmlEmbeddable.class);
	}
	
	public OrmEmbeddable buildContextMapping(
			OrmPersistentType parent, 
			XmlTypeMapping resourceMapping, 
			OrmXmlContextModelFactory factory) {
		return factory.buildOrmEmbeddable(parent, (XmlEmbeddable) resourceMapping);
	}
}
