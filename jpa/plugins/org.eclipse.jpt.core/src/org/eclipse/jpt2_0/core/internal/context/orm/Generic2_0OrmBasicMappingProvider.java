/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.internal.context.orm;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt2_0.core.internal.platform.Generic2_0JpaFactory;
import org.eclipse.jpt2_0.core.resource.orm.Orm2_0Factory;
import org.eclipse.jpt2_0.core.resource.orm.XmlBasic;

public class Generic2_0OrmBasicMappingProvider
	implements OrmAttributeMappingProvider
{
	// singleton
	private static final OrmAttributeMappingProvider INSTANCE = new Generic2_0OrmBasicMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private Generic2_0OrmBasicMappingProvider() {
		super();
	}

	public IContentType getContentType() {
		return JptCorePlugin.ORM2_0_XML_CONTENT_TYPE;
	}

	public String getKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlAttributeMapping buildResourceMapping() {
		return Orm2_0Factory.eINSTANCE.createXmlBasic();
	}
	
	public OrmAttributeMapping buildMapping(OrmPersistentAttribute parent, XmlAttributeMapping resourceMapping, JpaFactory factory) {
		return ((Generic2_0JpaFactory) factory).build2_0OrmBasicMapping(parent, (XmlBasic) resourceMapping);
	}

	public XmlAttributeMapping buildVirtualResourceMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping, JpaFactory factory) {
		return ((Generic2_0JpaFactory) factory).build2_0VirtualXmlBasic(ormTypeMapping, (JavaBasicMapping) javaAttributeMapping);
	}

}
