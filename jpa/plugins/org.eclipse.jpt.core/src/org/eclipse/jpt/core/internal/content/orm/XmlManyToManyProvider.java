/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.jpt.core.internal.IMappingKeys;

public class XmlManyToManyProvider implements IXmlAttributeMappingProvider
{
	// singleton
	private static final XmlManyToManyProvider INSTANCE = new XmlManyToManyProvider();

	/**
	 * Return the singleton.
	 */
	public static IXmlAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private XmlManyToManyProvider() {
		super();
	}

	public XmlAttributeMapping buildAttributeMapping() {
		return OrmFactory.eINSTANCE.createXmlManyToMany();
	}

	public String key() {
		return IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
}
