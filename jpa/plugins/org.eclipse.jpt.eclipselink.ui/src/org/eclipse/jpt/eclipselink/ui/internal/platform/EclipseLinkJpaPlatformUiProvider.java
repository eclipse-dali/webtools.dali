/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.platform;

import java.util.List;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultEclipseLinkVariableOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.EclipseLinkJavaBasicCollectionMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.EclipseLinkJavaBasicMapMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.EclipseLinkJavaTransformationMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.EclipseLinkJavaVariableOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkEntityMappingsDetailsProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmBasicCollectionMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmBasicMapMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmBasicMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmEmbeddableUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmEmbeddedIdMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmEmbeddedMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmEntityUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmIdMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmManyToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmManyToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmMappedSuperclassUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmTransformationMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmTransientMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmVariableOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmVersionMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.structure.EclipseLinkOrmResourceModelStructureProvider;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultTypeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.AbstractJpaPlatformUiProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

public class EclipseLinkJpaPlatformUiProvider extends AbstractJpaPlatformUiProvider
{

	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new EclipseLinkJpaPlatformUiProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkJpaPlatformUiProvider() {
		super();
	}


	// ********** details providers **********
	
	@Override
	protected void addDetailsProvidersTo(List<JpaDetailsProvider> providers) {
		providers.add(EclipseLinkEntityMappingsDetailsProvider.instance());
	}
	
	
	// ********** structure providers **********
	
	@Override
	protected void addMappingFileStructureProvidersTo(List<JpaStructureProvider> providers) {
		providers.add(EclipseLinkOrmResourceModelStructureProvider.instance());
	}
	
	
	// ********** type mapping ui providers **********

	@Override
	protected void addTypeMappingUiProvidersTo(List<TypeMappingUiProvider<?>> providers) {
		providers.add(EclipseLinkOrmEntityUiProvider.instance());
		providers.add(EclipseLinkOrmMappedSuperclassUiProvider.instance());
		providers.add(EclipseLinkOrmEmbeddableUiProvider.instance());
	}
	
	@Override
	protected void addDefaultTypeMappingUiProvidersTo(List<DefaultTypeMappingUiProvider<?>> providers) {
		//none specific to EclipseLink
	}
	
	
	// ********** attribute mapping ui providers **********
	
	@Override
	protected void addAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(EclipseLinkJavaBasicCollectionMappingUiProvider.instance());
		providers.add(EclipseLinkJavaBasicMapMappingUiProvider.instance());
		providers.add(EclipseLinkJavaVariableOneToOneMappingUiProvider.instance());
		providers.add(EclipseLinkJavaTransformationMappingUiProvider.instance());
		
		providers.add(EclipseLinkOrmIdMappingUiProvider.instance());
		providers.add(EclipseLinkOrmEmbeddedIdMappingUiProvider.instance());
		providers.add(EclipseLinkOrmBasicMappingUiProvider.instance());
		providers.add(EclipseLinkOrmBasicCollectionMappingUiProvider.instance());
		providers.add(EclipseLinkOrmBasicMapMappingUiProvider.instance());
		providers.add(EclipseLinkOrmVersionMappingUiProvider.instance());
		providers.add(EclipseLinkOrmManyToOneMappingUiProvider.instance());
		providers.add(EclipseLinkOrmOneToManyMappingUiProvider.instance());
		providers.add(EclipseLinkOrmEmbeddedMappingUiProvider.instance());
		providers.add(EclipseLinkOrmOneToOneMappingUiProvider.instance());
		providers.add(EclipseLinkOrmVariableOneToOneMappingUiProvider.instance());
		providers.add(EclipseLinkOrmManyToManyMappingUiProvider.instance());
		providers.add(EclipseLinkOrmTransformationMappingUiProvider.instance());
		providers.add(EclipseLinkOrmTransientMappingUiProvider.instance());
	}

	@Override
	protected void addDefaultAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(DefaultOneToOneMappingUiProvider.instance());
		providers.add(DefaultOneToManyMappingUiProvider.instance());
		providers.add(DefaultEclipseLinkVariableOneToOneMappingUiProvider.instance());
	}
}
