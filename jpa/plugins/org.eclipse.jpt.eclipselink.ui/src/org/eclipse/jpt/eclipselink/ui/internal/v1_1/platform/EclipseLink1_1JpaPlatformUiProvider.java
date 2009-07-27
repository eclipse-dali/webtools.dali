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
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkBasicCollectionMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkBasicMapMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkBasicMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkEmbeddable1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkEmbeddedIdMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkEmbeddedMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkEntity1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkIdMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkManyToManyMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkManyToOneMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkMappedSuperclass1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkOneToManyMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkOneToOneMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkTransformationMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkTransientMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkVariableOneToOneMapping1_1UiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.orm.details.OrmEclipseLinkVersionMapping1_1UiProvider;
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
		providers.add(OrmEclipseLinkEntity1_1UiProvider.instance());
		providers.add(OrmEclipseLinkMappedSuperclass1_1UiProvider.instance());
		providers.add(OrmEclipseLinkEmbeddable1_1UiProvider.instance());
	}
	
	@Override
	protected void addDefaultTypeMappingUiProvidersTo(List<DefaultTypeMappingUiProvider<?>> providers) {
		//none specific to EclipseLink 1.1
	}
	
	
	// ********** attribute mapping ui providers **********
	
	@Override
	protected void addAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(OrmEclipseLinkIdMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkEmbeddedIdMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkBasicMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkBasicCollectionMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkBasicMapMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkVersionMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkManyToOneMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkOneToManyMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkEmbeddedMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkOneToOneMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkVariableOneToOneMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkManyToManyMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkTransformationMapping1_1UiProvider.instance());
		providers.add(OrmEclipseLinkTransientMapping1_1UiProvider.instance());
	}
	
	@Override
	protected void addDefaultAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		//none specific to EclipseLink 1.1
	}
}
