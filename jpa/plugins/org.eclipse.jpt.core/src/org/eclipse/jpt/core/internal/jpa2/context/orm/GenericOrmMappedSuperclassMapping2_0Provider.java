/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Factory;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;

public class GenericOrmMappedSuperclassMapping2_0Provider
	implements OrmTypeMappingProvider
{
	// singleton
	private static final OrmTypeMappingProvider INSTANCE = new GenericOrmMappedSuperclassMapping2_0Provider();

	/**
	 * Return the singleton.
	 */
	public static OrmTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private GenericOrmMappedSuperclassMapping2_0Provider() {
		super();
	}

	public IContentType getContentType() {
		return JptCorePlugin.ORM2_0_XML_CONTENT_TYPE;
	}

	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}
	
	public XmlTypeMapping buildResourceMapping() {
		return Orm2_0Factory.eINSTANCE.createXmlMappedSuperclass();
	}

	public OrmTypeMapping buildMapping(OrmPersistentType parent, XmlTypeMapping resourceMapping, OrmXmlContextNodeFactory factory) {
		return factory.buildOrmMappedSuperclass(parent, (XmlMappedSuperclass) resourceMapping);
	}

}
