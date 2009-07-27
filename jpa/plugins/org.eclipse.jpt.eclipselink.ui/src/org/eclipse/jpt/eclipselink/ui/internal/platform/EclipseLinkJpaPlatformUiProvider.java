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
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultJavaEclipseLinkOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultJavaEclipseLinkOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.DefaultJavaEclipseLinkVariableOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.JavaEclipseLinkBasicCollectionMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.JavaEclipseLinkBasicMapMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.JavaEclipseLinkTransformationMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.JavaEclipseLinkVariableOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkEntityMappingsDetailsProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkBasicCollectionMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkBasicMapMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkBasicMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkEmbeddableUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkEmbeddedIdMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkEmbeddedMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkEntityUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkIdMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkManyToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkManyToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkMappedSuperclassUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkOneToManyMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkTransformationMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkTransientMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkVariableOneToOneMappingUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.OrmEclipseLinkVersionMappingUiProvider;
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
