/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;

/**
 * default ORM MappedSuperclass provider
 */
public class OrmMappedSuperclassProvider
	implements OrmTypeMappingProvider
{	
	// singleton
	private static final OrmMappedSuperclassProvider INSTANCE = new OrmMappedSuperclassProvider();

	/**
	 * Return the singleton.
	 */
	public static OrmTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmMappedSuperclassProvider() {
		super();
	}
	
	public XmlTypeMapping buildResourceMapping() {
		return OrmFactory.eINSTANCE.createXmlMappedSuperclass();
	}

	public OrmMappedSuperclass buildMapping(OrmPersistentType parent, XmlTypeMapping resourceMapping, OrmXmlContextNodeFactory factory) {
		return factory.buildOrmMappedSuperclass(parent, (XmlMappedSuperclass) resourceMapping);
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.ORM_XML_CONTENT_TYPE;
	}

	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}

}
