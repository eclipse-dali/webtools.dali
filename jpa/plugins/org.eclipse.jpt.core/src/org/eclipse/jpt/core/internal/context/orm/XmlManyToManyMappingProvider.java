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

public class XmlManyToManyMappingProvider implements IXmlAttributeMappingProvider
{
	// singleton
	private static final XmlManyToManyMappingProvider INSTANCE = new XmlManyToManyMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static IXmlAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private XmlManyToManyMappingProvider() {
		super();
	}
	
	public String key() {
		return IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	public XmlManyToManyMapping buildAttributeMapping(IJpaBaseContextFactory factory, XmlPersistentAttribute parent) {
		return new XmlManyToManyMapping(parent);
	}
}
