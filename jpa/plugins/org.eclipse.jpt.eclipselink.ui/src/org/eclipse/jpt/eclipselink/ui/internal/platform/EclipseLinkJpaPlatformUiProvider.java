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
import org.eclipse.jpt.eclipselink.ui.internal.details.java.DefaultJavaEclipseLinkOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.java.DefaultJavaEclipseLinkOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.java.DefaultJavaEclipseLinkVariableOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.java.JavaEclipseLinkBasicCollectionMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.java.JavaEclipseLinkBasicMapMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.java.JavaEclipseLinkTransformationMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.java.JavaEclipseLinkVariableOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.EclipseLinkEntityMappingsDetailsProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkBasicCollectionMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkBasicMapMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkBasicMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkEmbeddableUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkEmbeddedIdMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkEmbeddedMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkEntityUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkIdMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkManyToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkManyToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkMappedSuperclassUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkTransformationMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkTransientMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkVariableOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.details.orm.OrmEclipseLinkVersionMappingUiProvider;
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
		providers.add(OrmEclipseLinkEntityUiProvider.instance());
		providers.add(OrmEclipseLinkMappedSuperclassUiProvider.instance());
		providers.add(OrmEclipseLinkEmbeddableUiProvider.instance());
	}
	
	@Override
	protected void addDefaultTypeMappingUiProvidersTo(List<DefaultTypeMappingUiProvider<?>> providers) {
		//none specific to EclipseLink
	}
	
	
	// ********** attribute mapping ui providers **********
	
	@Override
	protected void addAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(JavaEclipseLinkBasicCollectionMappingUiProvider.instance());
		providers.add(JavaEclipseLinkBasicMapMappingUiProvider.instance());
		providers.add(JavaEclipseLinkVariableOneToOneMappingUiProvider.instance());
		providers.add(JavaEclipseLinkTransformationMappingUiProvider.instance());
		
		providers.add(OrmEclipseLinkIdMappingUiProvider.instance());
		providers.add(OrmEclipseLinkEmbeddedIdMappingUiProvider.instance());
		providers.add(OrmEclipseLinkBasicMappingUiProvider.instance());
		providers.add(OrmEclipseLinkBasicCollectionMappingUiProvider.instance());
		providers.add(OrmEclipseLinkBasicMapMappingUiProvider.instance());
		providers.add(OrmEclipseLinkVersionMappingUiProvider.instance());
		providers.add(OrmEclipseLinkManyToOneMappingUiProvider.instance());
		providers.add(OrmEclipseLinkOneToManyMappingUiProvider.instance());
		providers.add(OrmEclipseLinkEmbeddedMappingUiProvider.instance());
		providers.add(OrmEclipseLinkOneToOneMappingUiProvider.instance());
		providers.add(OrmEclipseLinkVariableOneToOneMappingUiProvider.instance());
		providers.add(OrmEclipseLinkManyToManyMappingUiProvider.instance());
		providers.add(OrmEclipseLinkTransformationMappingUiProvider.instance());
		providers.add(OrmEclipseLinkTransientMappingUiProvider.instance());
	}

	@Override
	protected void addDefaultAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(DefaultJavaEclipseLinkOneToOneMappingUiProvider.instance());
		providers.add(DefaultJavaEclipseLinkOneToManyMappingUiProvider.instance());
		providers.add(DefaultJavaEclipseLinkVariableOneToOneMappingUiProvider.instance());
	}
}
