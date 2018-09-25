/*******************************************************************************
 * Copyright (c) 2018 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.core.resource.orm.v2_2;

import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;

/**
 * JPA 2.2 orm.xml-related stuff (elements, attributes etc.)
 * 
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 */
public interface JPA2_2 extends JPA2_1 {
	String SCHEMA_NAMESPACE = "http://xmlns.jcp.org/xml/ns/persistence/orm";
	String SCHEMA_LOCATION = "http://xmlns.jcp.org/xml/ns/persistence/orm_2_2.xsd";
	String SCHEMA_VERSION = "2.2";
}
