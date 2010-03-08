/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public interface MappingKeys {

	String ENTITY_TYPE_MAPPING_KEY = "entity";
	String MAPPED_SUPERCLASS_TYPE_MAPPING_KEY = "mappedSuperclass";
	String EMBEDDABLE_TYPE_MAPPING_KEY = "embeddable";
	String NULL_TYPE_MAPPING_KEY = null;

	String BASIC_ATTRIBUTE_MAPPING_KEY = "basic";
	String ID_ATTRIBUTE_MAPPING_KEY = "id";
	String VERSION_ATTRIBUTE_MAPPING_KEY = "version";
	String ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY  = "oneToOne";
	String ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY = "oneToMany";
	String MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY = "manyToOne";
	String MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY = "manyToMany";
	String EMBEDDED_ATTRIBUTE_MAPPING_KEY = "embedded";
	String EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY = "embeddedId";
	String TRANSIENT_ATTRIBUTE_MAPPING_KEY = "transient";
	String NULL_ATTRIBUTE_MAPPING_KEY = null;

}
