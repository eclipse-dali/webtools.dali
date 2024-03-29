/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNullAttributeMapping;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public class NullOrmAttributeMappingDefinition
	implements OrmAttributeMappingDefinition
{
	// singleton
	private static final OrmAttributeMappingDefinition INSTANCE = 
			new NullOrmAttributeMappingDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private NullOrmAttributeMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlAttributeMapping buildResourceMapping(EFactory factory) {
		throw new UnsupportedOperationException();
	}
	
	public OrmAttributeMapping buildContextMapping(OrmSpecifiedPersistentAttribute parent, XmlAttributeMapping resourceMapping, OrmXmlContextModelFactory factory) {
		return factory.buildOrmNullAttributeMapping(parent, (XmlNullAttributeMapping) resourceMapping);
	}
	
	public boolean isSingleRelationshipMapping() {
		return false;
	}
	
	public boolean isCollectionMapping() {
		return false;
	}
}
