/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.core.resource.orm.v2_1;

import org.eclipse.jpt.jpa.core.resource.orm.v2_0.JPA2_0;

/**
 * JPA 2.1 orm.xml-related stuff (elements, attributes etc.)
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
@SuppressWarnings("nls")
public interface JPA2_1
	extends JPA2_0
{
	String SCHEMA_NAMESPACE = "http://xmlns.jcp.org/xml/ns/persistence/orm";
	String SCHEMA_LOCATION = "http://xmlns.jcp.org/xml/ns/persistence/orm_2_1.xsd";
	String SCHEMA_VERSION = "2.1";
	
	// JPA 2.1 specific nodes

	String ATTRIBUTE_NAME = "attribute-name";
	String AUTO_APPLY = "auto-apply";
	String COLUMN_LIST = "column-list";
	String CONSTRAINT_MODE = "constraint-mode";
	String CONSTRUCTOR_RESULT = "constructor-result";
	String CONVERT = "convert";
	String CONVERTER = "converter";
	String DISABLE_CONVERSION = "disable-conversion";
	String FOREIGN_KEY = "foreign-key";
	String FOREIGN_KEY_DEFINITION = "foreign-key-definition";
	String INCLUDE_ALL_ATTRIBUTES = "include-all-attributes";
	String INDEX = "index";
	String INVERSE_FOREIGN_KEY = "inverse-foreign-key";
	String KEY_SUBGRAPH = "key-subgraph";
	String MAP_KEY_CONVERT = "map-key-convert";
	String MAP_KEY_FOREIGN_KEY = "map-key-foreign-key";
	String NAMED_ATTRIBUTE_NODE = "named-attribute-node";
	String NAMED_ENTITY_GRAPH = "named-entity-graph";
	String NAMED_STORED_PROCEDURE_QUERY = "named-stored-procedure-query";
	String PARAMETER = "parameter";
	String MODE = "mode";
	String PROCEDURE_NAME = "procedure-name";
	String PRIMARY_KEY_FOREIGN_KEY = "primary-key-foreign-key";
	String RESULT_CLASS = "result-class";
	String RESULT_SET_MAPPING = "result-set-mapping";
	String SUBCLASS_SUBGRAPH = "subclass-subgraph";
	String SUBGRAPH = "subgraph";

}
