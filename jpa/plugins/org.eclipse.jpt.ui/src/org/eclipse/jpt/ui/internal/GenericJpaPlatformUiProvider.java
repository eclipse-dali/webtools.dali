/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import java.util.List;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultTypeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.DefaultBasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.DefaultEmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaBasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaDefaultTypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaEmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaEmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaEmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaEntityUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaManyToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaManyToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaMappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaOneToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaOneToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaPersistentAttributeDetailsProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaPersistentTypeDetailsProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaTransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.JavaVersionMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.java.NullAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.EntityMappingsDetailsProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmBasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmEmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmEmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmEmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmEntityUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmManyToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmManyToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmMappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmOneToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmOneToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmPersistentAttributeDetailsProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmPersistentTypeDetailsProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmTransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.orm.OrmVersionMappingUiProvider;
import org.eclipse.jpt.ui.internal.structure.OrmResourceModelStructureProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatformUiProvider extends AbstractJpaPlatformUiProvider
{

	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new GenericJpaPlatformUiProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private GenericJpaPlatformUiProvider() {
		super();
	}


	// ********** details providers **********
	
	@Override
	protected void addDetailsProvidersTo(List<JpaDetailsProvider> providers) {
		providers.add(JavaPersistentTypeDetailsProvider.instance());
		providers.add(JavaPersistentAttributeDetailsProvider.instance());
		providers.add(EntityMappingsDetailsProvider.instance());
		providers.add(OrmPersistentTypeDetailsProvider.instance());
		providers.add(OrmPersistentAttributeDetailsProvider.instance());
	}
	
	
	// ********** structure providers **********
	
	@Override
	protected void addMappingFileStructureProvidersTo(List<JpaStructureProvider> providers) {
		providers.add(OrmResourceModelStructureProvider.instance());
	}

	
	// ********** type mapping ui providers **********

	@Override
	protected void addTypeMappingUiProvidersTo(List<TypeMappingUiProvider<?>> providers) {
		providers.add(JavaEntityUiProvider.instance());
		providers.add(JavaMappedSuperclassUiProvider.instance());
		providers.add(JavaEmbeddableUiProvider.instance());
		providers.add(OrmEntityUiProvider.instance());
		providers.add(OrmMappedSuperclassUiProvider.instance());
		providers.add(OrmEmbeddableUiProvider.instance());
	}
	
	@Override
	protected void addDefaultTypeMappingUiProvidersTo(List<DefaultTypeMappingUiProvider<?>> providers) {
		providers.add(JavaDefaultTypeMappingUiProvider.instance());
	}
	
	
	// ********** attribute mapping ui providers **********
	
	@Override
	protected void addAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(JavaIdMappingUiProvider.instance());
		providers.add(JavaEmbeddedIdMappingUiProvider.instance());
		providers.add(JavaBasicMappingUiProvider.instance());
		providers.add(JavaVersionMappingUiProvider.instance());
		providers.add(JavaManyToOneMappingUiProvider.instance());
		providers.add(JavaOneToManyMappingUiProvider.instance());
		providers.add(JavaOneToOneMappingUiProvider.instance());
		providers.add(JavaManyToManyMappingUiProvider.instance());
		providers.add(JavaEmbeddedMappingUiProvider.instance());
		providers.add(JavaTransientMappingUiProvider.instance());
		
		providers.add(OrmIdMappingUiProvider.instance());
		providers.add(OrmEmbeddedIdMappingUiProvider.instance());
		providers.add(OrmBasicMappingUiProvider.instance());
		providers.add(OrmVersionMappingUiProvider.instance());
		providers.add(OrmManyToOneMappingUiProvider.instance());
		providers.add(OrmOneToManyMappingUiProvider.instance());
		providers.add(OrmOneToOneMappingUiProvider.instance());
		providers.add(OrmManyToManyMappingUiProvider.instance());
		providers.add(OrmEmbeddedMappingUiProvider.instance());
		providers.add(OrmTransientMappingUiProvider.instance());
	}
	
	@Override
	protected void addDefaultAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(DefaultBasicMappingUiProvider.instance());
		providers.add(DefaultEmbeddedMappingUiProvider.instance());
		providers.add(NullAttributeMappingUiProvider.instance());
	}
}
