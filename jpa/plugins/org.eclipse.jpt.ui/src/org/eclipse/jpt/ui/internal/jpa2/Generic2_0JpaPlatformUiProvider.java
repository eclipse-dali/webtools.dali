/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2;

import java.util.List;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultTypeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.AbstractJpaPlatformUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0BasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0EmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0EmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0EmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0EntityUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0IdMappingUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0ManyToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0ManyToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0MappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0OneToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0OneToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0TransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.orm.details.Orm2_0VersionMappingUiProvider;
import org.eclipse.jpt.ui.internal.jpa2.structure.Orm2_0ResourceModelStructureProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class Generic2_0JpaPlatformUiProvider extends AbstractJpaPlatformUiProvider
{

	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new Generic2_0JpaPlatformUiProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private Generic2_0JpaPlatformUiProvider() {
		super();
	}


	// ********** details providers **********
	
	@Override
	protected void addDetailsProvidersTo(List<JpaDetailsProvider> providers) {
	}
	
	
	// ********** structure providers **********
	
	@Override
	protected void addMappingFileStructureProvidersTo(List<JpaStructureProvider> providers) {
		providers.add(Orm2_0ResourceModelStructureProvider.instance());
	}

	
	// ********** type mapping ui providers **********

	@Override
	protected void addTypeMappingUiProvidersTo(List<TypeMappingUiProvider<?>> providers) {
		providers.add(Orm2_0EntityUiProvider.instance());
		providers.add(Orm2_0MappedSuperclassUiProvider.instance());
		providers.add(Orm2_0EmbeddableUiProvider.instance());
	}
	
	@Override
	protected void addDefaultTypeMappingUiProvidersTo(List<DefaultTypeMappingUiProvider<?>> providers) {
	}
	
	
	// ********** attribute mapping ui providers **********
	
	@Override
	protected void addAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
//		providers.add(JavaElementCollectionMappingUiProvider.instance());
		
		providers.add(Orm2_0IdMappingUiProvider.instance());
		providers.add(Orm2_0EmbeddedIdMappingUiProvider.instance());
		providers.add(Orm2_0BasicMappingUiProvider.instance());
		providers.add(Orm2_0VersionMappingUiProvider.instance());
		providers.add(Orm2_0ManyToOneMappingUiProvider.instance());
		providers.add(Orm2_0OneToManyMappingUiProvider.instance());
		providers.add(Orm2_0EmbeddedMappingUiProvider.instance());
		providers.add(Orm2_0OneToOneMappingUiProvider.instance());
		providers.add(Orm2_0ManyToManyMappingUiProvider.instance());
//		providers.add(OrmElementCollectionMappingUiProvider.instance());
		providers.add(Orm2_0TransientMappingUiProvider.instance());
	}
	
	@Override
	protected void addDefaultAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
	}
}