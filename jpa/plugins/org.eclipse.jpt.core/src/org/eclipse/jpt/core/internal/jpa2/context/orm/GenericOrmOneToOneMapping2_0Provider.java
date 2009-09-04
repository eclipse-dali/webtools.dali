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

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Factory;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;

public class GenericOrmOneToOneMapping2_0Provider
	implements OrmAttributeMappingProvider
{
	// singleton
	private static final OrmAttributeMappingProvider INSTANCE = new GenericOrmOneToOneMapping2_0Provider();

	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private GenericOrmOneToOneMapping2_0Provider() {
		super();
	}

	public String getKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	public XmlAttributeMapping buildResourceMapping() {
		return Orm2_0Factory.eINSTANCE.createXmlOneToOne();
	}

	public OrmAttributeMapping buildMapping(
			OrmPersistentAttribute parent, 
			XmlAttributeMapping resourceMapping, 
			OrmXmlContextNodeFactory factory) {
		
		return factory.buildOrmOneToOneMapping(
			parent, (XmlOneToOne) resourceMapping);
	}
	
	public XmlAttributeMapping buildVirtualResourceMapping(
			OrmTypeMapping ormTypeMapping, 
			JavaAttributeMapping javaAttributeMapping, 
			OrmXmlContextNodeFactory factory) {
		
		return factory.buildVirtualXmlOneToOne(
			ormTypeMapping, (JavaOneToOneMapping2_0) javaAttributeMapping);
	}
}
