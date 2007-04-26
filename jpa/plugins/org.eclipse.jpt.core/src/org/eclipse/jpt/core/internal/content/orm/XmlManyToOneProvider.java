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

public class XmlManyToOneProvider implements IXmlAttributeMappingProvider
{
	// singleton
	private static final XmlManyToOneProvider INSTANCE = new XmlManyToOneProvider();

	/**
	 * Return the singleton.
	 */
	public static IXmlAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private XmlManyToOneProvider() {
		super();
	}

	
	public XmlAttributeMapping buildAttributeMapping() {
		return OrmFactory.eINSTANCE.createXmlManyToOne();
	}

	public String key() {
		return IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
}
