/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;

public class OrmOneToManyMappingProvider
	implements OrmAttributeMappingProvider
{
	// singleton
	private static final OrmAttributeMappingProvider INSTANCE = new OrmOneToManyMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmOneToManyMappingProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.ORM_XML_CONTENT_TYPE;
	}
	
	public String getKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlAttributeMapping buildResourceMapping() {
		return OrmFactory.eINSTANCE.createXmlOneToManyImpl();
	}

	public OrmAttributeMapping buildMapping(OrmPersistentAttribute parent, XmlAttributeMapping resourceMapping, JpaFactory factory) {
		return factory.buildOrmOneToManyMapping(parent, (XmlOneToMany) resourceMapping);
	}
	
	public XmlAttributeMapping buildVirtualResourceMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping, JpaFactory factory) {
		return factory.buildVirtualXmlOneToMany(ormTypeMapping, (JavaOneToManyMapping) javaAttributeMapping);
	}

}
