/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.core.resource.persistence.v2_0;

import org.eclipse.jpt.jpa.core.resource.persistence.JPA;

/**
 * JPA persistence2_0.xml-related stuff (elements, attributes etc.)
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
@SuppressWarnings("nls")
public interface JPA2_0
	extends JPA
{
	String SCHEMA_NAMESPACE = JPA.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd";
	String SCHEMA_VERSION = "2.0";
	
	// JPA 2.0 specific nodes
	
	String PERSISTENCE_UNIT__SHARED_CACHE_MODE = "shared-cache-mode";
	String PERSISTENCE_UNIT__VALIDATION_MODE = "validation-mode";
}
