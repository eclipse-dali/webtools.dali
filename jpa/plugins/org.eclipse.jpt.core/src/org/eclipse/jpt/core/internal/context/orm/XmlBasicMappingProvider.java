/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.platform.base.IJpaBaseContextFactory;
import org.eclipse.jpt.core.internal.resource.orm.Attributes;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;

public class XmlBasicMappingProvider implements IXmlAttributeMappingProvider
{
	// singleton
	private static final XmlBasicMappingProvider INSTANCE = new XmlBasicMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static IXmlAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private XmlBasicMappingProvider() {
		super();
	}

	public String key() {
		return IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	public XmlBasicMapping buildAttributeMapping(IJpaBaseContextFactory factory, XmlPersistentAttribute parent) {
		return new XmlBasicMapping(parent);
	}
	
	public Basic createAndAddOrmResourceMapping(XmlPersistentAttribute xmlPersistentAttribute, Attributes attributes) {
		Basic basic = OrmFactory.eINSTANCE.createBasic();
		xmlPersistentAttribute.initialize(basic);
		attributes.getBasics().add(basic);
		return basic;
	}

}
