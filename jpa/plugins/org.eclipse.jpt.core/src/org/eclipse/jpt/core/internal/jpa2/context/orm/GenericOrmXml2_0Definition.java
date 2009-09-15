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
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.orm.NullOrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlDefinition;
import org.eclipse.jpt.core.internal.context.orm.OrmBasicMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddableProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddedIdMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddedMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEntityProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmIdMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmManyToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmManyToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmMappedSuperclassProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmOneToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmOneToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmTransientMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmVersionMappingProvider;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Factory;

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
	
	
	public EFactory getResourceNodeFactory() {
		return Orm2_0Factory.eINSTANCE;
	}
	
	@Override
	protected OrmXmlContextNodeFactory buildContextNodeFactory() {
		return new GenericOrmXml2_0ContextNodeFactory();
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
			OrmEntityProvider.instance(),
			OrmEmbeddableProvider.instance(),
			OrmMappedSuperclassProvider.instance()};
	}
	
	
	// ********** ORM attribute mappings **********
	
	@Override
	protected OrmAttributeMappingProvider[] buildOrmAttributeMappingProviders() {
		// order should not matter here, but we'll use the same order as for java
		// (@see {@link Generic2_0JpaPlatformProvider})
		return new OrmAttributeMappingProvider[] {
			OrmTransientMappingProvider.instance(),
			//OrmElementCollectionMappingProvider.instance(),
			OrmIdMappingProvider.instance(),
			OrmVersionMappingProvider.instance(),
			OrmBasicMappingProvider.instance(),
			OrmEmbeddedMappingProvider.instance(),
			OrmEmbeddedIdMappingProvider.instance(),
			OrmManyToManyMappingProvider.instance(),
			OrmManyToOneMappingProvider.instance(),
			OrmOneToManyMappingProvider.instance(),
			OrmOneToOneMappingProvider.instance(),
			NullOrmAttributeMappingProvider.instance()};
	}
}
