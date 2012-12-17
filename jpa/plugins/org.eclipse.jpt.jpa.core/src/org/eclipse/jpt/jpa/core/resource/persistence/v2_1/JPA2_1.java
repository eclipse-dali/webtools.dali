/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.core.resource.persistence.v2_1;

import org.eclipse.jpt.jpa.core.resource.persistence.JPA;

/**
 * JPA persistence2_1.xml-related stuff (elements, attributes etc.)
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
	extends JPA
{
	String SCHEMA_NAMESPACE = JPA.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://java.sun.com/xml/ns/persistence/persistence_2_1.xsd";
	String SCHEMA_VERSION = "2.1";
	
	// JPA 2.1 specific nodes

}
