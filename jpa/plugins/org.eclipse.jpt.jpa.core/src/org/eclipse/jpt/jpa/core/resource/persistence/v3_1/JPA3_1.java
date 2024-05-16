/*******************************************************************************
 * Copyright (c) 2024 Lakshminarayana Nekkanti. All rights reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.persistence.v3_1;

import org.eclipse.jpt.jpa.core.resource.orm.v3_0.JPA3_0;

/**
 * JPA persistence3_1.xml-related stuff (elements, attributes etc.)
 * 
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 */
public interface JPA3_1 extends JPA3_0 {
	String SCHEMA_NAMESPACE = "https://jakarta.ee/xml/ns/persistence";
	String SCHEMA_LOCATION = "https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd";
	String SCHEMA_VERSION = "3.0";
	//Note the version is still 3.0 for JPA 3.1 https://jakarta.ee/xml/ns/persistence/

	// no JPA 3.1 specific nodes...
}
