/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.platform.base.IJpaBaseContextFactory;

public class XmlTransientMappingProvider implements IXmlAttributeMappingProvider
{
	// singleton
	private static final XmlTransientMappingProvider INSTANCE = new XmlTransientMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static IXmlAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private XmlTransientMappingProvider() {
		super();
	}
	
	public String key() {
		return IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlTransientMapping buildAttributeMapping(IJpaBaseContextFactory factory, XmlPersistentAttribute parent) {
		return new XmlTransientMapping(parent);
	}
}
