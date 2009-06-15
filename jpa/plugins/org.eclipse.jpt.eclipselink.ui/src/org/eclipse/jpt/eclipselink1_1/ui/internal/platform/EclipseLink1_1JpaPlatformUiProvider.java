/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.ui.internal.platform;

import java.util.List;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmBasicCollectionMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmBasicMapMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmBasicMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmEmbeddableUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmEmbeddedIdMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmEmbeddedMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmEntityUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmIdMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmManyToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmManyToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmMappedSuperclassUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmTransformationMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmTransientMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmVariableOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.orm.details.EclipseLink1_1OrmVersionMappingUiProvider;
import org.eclipse.jpt.eclipselink1_1.ui.internal.structure.EclipseLink1_1OrmResourceModelStructureProvider;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultTypeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.AbstractJpaPlatformUiProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

public class EclipseLink1_1JpaPlatformUiProvider extends AbstractJpaPlatformUiProvider
{

	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new EclipseLink1_1JpaPlatformUiProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLink1_1JpaPlatformUiProvider() {
		super();
	}


	// ********** details providers **********
	
	@Override
	protected void addDetailsProvidersTo(List<JpaDetailsProvider> providers) {
		//none specific to EclipseLink 1.1
	}
	
	// ********** structure providers **********
	
	@Override
	protected void addMappingFileStructureProvidersTo(List<JpaStructureProvider> providers) {
		providers.add(EclipseLink1_1OrmResourceModelStructureProvider.instance());
	}

	
	// ********** type mapping ui providers **********

	@Override
	protected void addTypeMappingUiProvidersTo(List<TypeMappingUiProvider<?>> providers) {
		providers.add(EclipseLink1_1OrmEntityUiProvider.instance());
		providers.add(EclipseLink1_1OrmMappedSuperclassUiProvider.instance());
		providers.add(EclipseLink1_1OrmEmbeddableUiProvider.instance());
	}
	
	@Override
	protected void addDefaultTypeMappingUiProvidersTo(List<DefaultTypeMappingUiProvider<?>> providers) {
		//none specific to EclipseLink 1.1
	}
	
	
	// ********** attribute mapping ui providers **********
	
	@Override
	protected void addAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(EclipseLink1_1OrmIdMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmEmbeddedIdMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmBasicMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmBasicCollectionMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmBasicMapMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmVersionMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmManyToOneMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmOneToManyMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmEmbeddedMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmOneToOneMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmVariableOneToOneMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmManyToManyMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmTransformationMappingUiProvider.instance());
		providers.add(EclipseLink1_1OrmTransientMappingUiProvider.instance());
	}
	
	@Override
	protected void addDefaultAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		//none specific to EclipseLink 1.1
	}
}
