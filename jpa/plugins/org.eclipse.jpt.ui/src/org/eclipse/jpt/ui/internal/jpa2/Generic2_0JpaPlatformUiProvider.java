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
import org.eclipse.jpt.ui.internal.jpa2.details.orm.EntityMappings2_0DetailsProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmBasicMapping2_0UiProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmEmbeddable2_0UiProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmEmbeddedIdMapping2_0UiProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmEmbeddedMapping2_0UiProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmEntity2_0UiProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmIdMapping2_0UiProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmManyToManyMapping2_0UiProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmManyToOneMapping2_0UiProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmMappedSuperclass2_0UiProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmOneToManyMapping2_0UiProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmOneToOneMapping2_0UiProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmTransientMapping2_0UiProvider;
import org.eclipse.jpt.ui.internal.jpa2.details.orm.OrmVersionMapping2_0UiProvider;
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
		providers.add(EntityMappings2_0DetailsProvider.instance());
	}
	
	
	// ********** structure providers **********
	
	@Override
	protected void addMappingFileStructureProvidersTo(List<JpaStructureProvider> providers) {
		providers.add(Orm2_0ResourceModelStructureProvider.instance());
	}

	
	// ********** type mapping ui providers **********

	@Override
	protected void addTypeMappingUiProvidersTo(List<TypeMappingUiProvider<?>> providers) {
		providers.add(OrmEntity2_0UiProvider.instance());
		providers.add(OrmMappedSuperclass2_0UiProvider.instance());
		providers.add(OrmEmbeddable2_0UiProvider.instance());
	}
	
	@Override
	protected void addDefaultTypeMappingUiProvidersTo(List<DefaultTypeMappingUiProvider<?>> providers) {
	}
	
	
	// ********** attribute mapping ui providers **********
	
	@Override
	protected void addAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
//		providers.add(JavaElementCollectionMappingUiProvider.instance());
		
		providers.add(OrmIdMapping2_0UiProvider.instance());
		providers.add(OrmEmbeddedIdMapping2_0UiProvider.instance());
		providers.add(OrmBasicMapping2_0UiProvider.instance());
		providers.add(OrmVersionMapping2_0UiProvider.instance());
		providers.add(OrmManyToOneMapping2_0UiProvider.instance());
		providers.add(OrmOneToManyMapping2_0UiProvider.instance());
		providers.add(OrmEmbeddedMapping2_0UiProvider.instance());
		providers.add(OrmOneToOneMapping2_0UiProvider.instance());
		providers.add(OrmManyToManyMapping2_0UiProvider.instance());
//		providers.add(OrmElementCollectionMappingUiProvider.instance());
		providers.add(OrmTransientMapping2_0UiProvider.instance());
	}
	
	@Override
	protected void addDefaultAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
	}
}