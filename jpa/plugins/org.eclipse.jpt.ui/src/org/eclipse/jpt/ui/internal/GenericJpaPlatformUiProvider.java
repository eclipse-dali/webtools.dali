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
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.details.DefaultTypeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaDefaultTypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaEmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaEntityUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaMappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaPersistentAttributeDetailsProvider;
import org.eclipse.jpt.ui.internal.java.details.JavaPersistentTypeDetailsProvider;
import org.eclipse.jpt.ui.internal.orm.details.EntityMappingsDetailsProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmEmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmEntityUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmMappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmPersistentAttributeDetailsProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmPersistentTypeDetailsProvider;
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
}
