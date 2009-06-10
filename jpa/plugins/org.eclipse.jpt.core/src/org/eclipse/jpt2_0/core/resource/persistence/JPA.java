/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.resource.persistence;

/**
 * JPA persistence2_0.xml-related stuff (elements, attributes etc.)
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
@SuppressWarnings("nls")
public interface JPA extends org.eclipse.jpt.core.resource.persistence.JPA {

	String NAMESPACE_URL = "http://java.sun.com/xml/ns/persistence";
	String SCHEMA_LOCATION_2_0 = "http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd";

	String PERSISTENCE_UNIT__CACHING = "caching";
	String PERSISTENCE_UNIT__VALIDATION_MODE = "validation-mode";

}
