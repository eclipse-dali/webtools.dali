/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.xml;

/**
 * XML-related stuff.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
@SuppressWarnings("nls")
public interface XML
{

	String NAMESPACE = "xmlns";

	String NAMESPACE_XSI = "xmlns:xsi";
	String XSI_NAMESPACE_URL = "http://www.w3.org/2001/XMLSchema-instance";

	String XSI_SCHEMA_LOCATION = "xsi:schemaLocation";

	String VERSION_1_0_TEXT = "1.0";
	String VERSION_2_0_TEXT = "2.0";

}
