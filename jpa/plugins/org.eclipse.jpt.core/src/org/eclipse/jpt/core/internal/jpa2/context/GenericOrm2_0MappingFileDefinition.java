/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFileDefinition;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmBasicMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmEmbeddableMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmEmbeddedIdMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmEmbeddedMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmEntityMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmIdMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmManyToManyMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmManyToOneMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmMappedSuperclassMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmNullAttributeMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmOneToManyMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmOneToOneMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmTransientMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmVersionMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.platform.Generic2_0JpaFactory;
import org.eclipse.jpt.core.internal.platform.AbstractMappingFileDefinition;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

public class GenericOrm2_0MappingFileDefinition
	extends AbstractMappingFileDefinition
{
	// singleton
	private static final MappingFileDefinition INSTANCE = 
			new GenericOrm2_0MappingFileDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static MappingFileDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private GenericOrm2_0MappingFileDefinition() {
		super();
	}
	
	
	public IContentType getContentType() {
		return JptCorePlugin.ORM2_0_XML_CONTENT_TYPE;
	}
	
	public MappingFile buildMappingFile(MappingFileRef parent, JpaXmlResource resource, JpaFactory factory) {
		return ((Generic2_0JpaFactory) factory).buildMappingFile2_0(parent, resource);
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
