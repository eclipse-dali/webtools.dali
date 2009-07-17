/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.resource.java;

/**
 * JPA Java-related stuff (annotations etc.)
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
@SuppressWarnings("nls")
public interface JPA {

	// JPA package
	String PACKAGE = "javax.persistence";
	String PACKAGE_ = PACKAGE + '.';
	
	
	// JPA 2.0 annotations
	String ACCESS = PACKAGE_ + "Access";
		String ACCESS__VALUE = "value";
	
	String ASSOCIATION_OVERRIDE__JOIN_TABLE = "joinTable";
	
	String SEQUENCE_GENERATOR__CATALOG = "catalog";
	String SEQUENCE_GENERATOR__SCHEMA = "schema";

	// JPA 2.0 enums
	String ACCESS_TYPE = PACKAGE_ + "AccessType";
		String ACCESS_TYPE_ = ACCESS_TYPE + '.';
		String ACCESS_TYPE__FIELD = ACCESS_TYPE_ + "FIELD";
		String ACCESS_TYPE__PROPERTY = ACCESS_TYPE_ + "PROPERTY";

}
