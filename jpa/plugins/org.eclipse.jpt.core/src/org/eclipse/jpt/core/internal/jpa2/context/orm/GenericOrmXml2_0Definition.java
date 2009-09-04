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
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlDefinition;
import org.eclipse.jpt.core.internal.jpa2.platform.GenericJpaOrm2_0Factory;

public class GenericOrmXml2_0Definition
	extends AbstractOrmXmlDefinition
{
	// singleton
	private static final OrmXmlDefinition INSTANCE = 
			new GenericOrmXml2_0Definition();
	
	
	/**
	 * Return the singleton
	 */
	public static OrmXmlDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private GenericOrmXml2_0Definition() {
		super();
	}
	
	@Override
	protected OrmXmlContextNodeFactory buildFactory() {
		return new GenericJpaOrm2_0Factory();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.ORM2_0_XML_CONTENT_TYPE;
	}
	
	
	// ********** ORM type mappings **********
	
	@Override
	protected OrmTypeMappingProvider[] buildOrmTypeMappingProviders() {
		// order should not matter here, but we'll use the same order as for java 
		// (@see {@link Generic2_0JpaPlatformProvider})
		return new OrmTypeMappingProvider[] {
			GenericOrmEntityMapping2_0Provider.instance(),
			GenericOrmEmbeddableMapping2_0Provider.instance(),
			GenericOrmMappedSuperclassMapping2_0Provider.instance()};
	}
	
	
	// ********** ORM attribute mappings **********
	
	@Override
	protected OrmAttributeMappingProvider[] buildOrmAttributeMappingProviders() {
		// order should not matter here, but we'll use the same order as for java
		// (@see {@link Generic2_0JpaPlatformProvider})
		return new OrmAttributeMappingProvider[] {
			GenericOrmTransientMapping2_0Provider.instance(),
			//OrmElementCollectionMappingProvider.instance(),
			GenericOrmIdMapping2_0Provider.instance(),
			GenericOrmVersionMapping2_0Provider.instance(),
			GenericOrmBasicMapping2_0Provider.instance(),
			GenericOrmEmbeddedMapping2_0Provider.instance(),
			GenericOrmEmbeddedIdMapping2_0Provider.instance(),
			GenericOrmManyToManyMapping2_0Provider.instance(),
			GenericOrmManyToOneMapping2_0Provider.instance(),
			GenericOrmOneToManyMapping2_0Provider.instance(),
			GenericOrmOneToOneMapping2_0Provider.instance(),
			GenericOrmNullAttributeMapping2_0Provider.instance()};
	}
}
