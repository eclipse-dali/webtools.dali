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
import org.eclipse.jpt.core.internal.resource.orm.Attributes;
import org.eclipse.jpt.core.internal.resource.orm.Embedded;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;

public class XmlEmbeddedMappingProvider implements IXmlAttributeMappingProvider
{
	// singleton
	private static final XmlEmbeddedMappingProvider INSTANCE = new XmlEmbeddedMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static IXmlAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private XmlEmbeddedMappingProvider() {
		super();
	}

	public XmlEmbeddedMapping buildAttributeMapping(IJpaBaseContextFactory factory, XmlPersistentAttribute parent) {
		return new XmlEmbeddedMapping(parent);
	}

	public String key() {
		return IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}
	
	public Embedded createAndAddOrmResourceMapping(XmlPersistentAttribute xmlPersistentAttribute, Attributes attributes) {
		Embedded embedded = OrmFactory.eINSTANCE.createEmbedded();
		xmlPersistentAttribute.initialize(embedded);
		attributes.getEmbeddeds().add(embedded);
		return embedded;
	}
}
