/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFileDefinition;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.platform.AbstractMappingFileDefinition;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

public class GenericOrmMappingFileDefinition
	extends AbstractMappingFileDefinition
{
	// singleton
	private static final MappingFileDefinition INSTANCE = 
			new GenericOrmMappingFileDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static MappingFileDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private GenericOrmMappingFileDefinition() {
		super();
	}
	
	
	public IContentType getContentType() {
		return JptCorePlugin.ORM_XML_CONTENT_TYPE;
	}
	
	public MappingFile buildMappingFile(MappingFileRef parent, JpaXmlResource resource, JpaFactory factory) {
		return factory.buildMappingFile(parent, resource);
	}
	
	
	// ********** ORM type mappings **********
	
	@Override
	protected OrmTypeMappingProvider[] buildOrmTypeMappingProviders() {
		// order should not matter here, but we'll use the same order as for java 
		// (@see {@link GenericJpaPlatformProvider})
		return new OrmTypeMappingProvider[] {
			OrmEntityProvider.instance(),
			OrmEmbeddableProvider.instance(),
			OrmMappedSuperclassProvider.instance()};
	}
	
	
	// ********** ORM attribute mappings **********
	
	@Override
	protected OrmAttributeMappingProvider[] buildOrmAttributeMappingProviders() {
		// order should not matter here, but we'll use the same order as for java
		// (@see {@link GenericJpaPlatformProvider})
		return new OrmAttributeMappingProvider[] {
			OrmTransientMappingProvider.instance(),
			OrmIdMappingProvider.instance(),
			OrmVersionMappingProvider.instance(),
			OrmBasicMappingProvider.instance(),
			OrmEmbeddedMappingProvider.instance(),
			OrmEmbeddedIdMappingProvider.instance(),
			OrmManyToManyMappingProvider.instance(),
			OrmManyToOneMappingProvider.instance(),
			OrmOneToManyMappingProvider.instance(),
			OrmOneToOneMappingProvider.instance(),
			OrmNullAttributeMappingProvider.instance()};
	}
}
