/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v1_1.platform;

import java.util.List;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmBasicCollectionMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmBasicMapMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmBasicMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmEmbeddable1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmEmbeddedIdMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmEmbeddedMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmEntity1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmIdMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmManyToManyMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmManyToOneMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmMappedSuperclass1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmOneToManyMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmOneToOneMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmTransformationMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmTransientMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmVariableOneToOneMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.EclipseLinkOrmVersionMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.structure.EclipseLink1_1OrmResourceModelStructureProvider;
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
		providers.add(EclipseLinkOrmEntity1_1UiProvider.instance());
		providers.add(EclipseLinkOrmMappedSuperclass1_1UiProvider.instance());
		providers.add(EclipseLinkOrmEmbeddable1_1UiProvider.instance());
	}
	
	@Override
	protected void addDefaultTypeMappingUiProvidersTo(List<DefaultTypeMappingUiProvider<?>> providers) {
		//none specific to EclipseLink 1.1
	}
	
	
	// ********** attribute mapping ui providers **********
	
	@Override
	protected void addAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(EclipseLinkOrmIdMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmEmbeddedIdMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmBasicMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmBasicCollectionMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmBasicMapMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmVersionMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmManyToOneMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmOneToManyMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmEmbeddedMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmOneToOneMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmVariableOneToOneMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmManyToManyMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmTransformationMapping1_1UiProvider.instance());
		providers.add(EclipseLinkOrmTransientMapping1_1UiProvider.instance());
	}
	
	@Override
	protected void addDefaultAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		//none specific to EclipseLink 1.1
	}
}
