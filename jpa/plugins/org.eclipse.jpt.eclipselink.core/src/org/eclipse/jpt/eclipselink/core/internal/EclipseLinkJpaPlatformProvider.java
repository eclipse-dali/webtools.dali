/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import java.util.List;
import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.context.MappingFileProvider;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.ExtendedOrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.ExtendedOrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.internal.platform.AbstractJpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkMappingFileProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToManyMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaBasicCollectionMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaBasicMapMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaTransformationMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaVariableOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmBasicMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEmbeddableProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEmbeddedIdMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEmbeddedMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEntityProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmIdMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmManyToManyMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmManyToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmMappedSuperclassProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmOneToManyMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmVersionMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmBasicCollectionMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmBasicMapMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmTransformationMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmVariableOneToOneMappingProvider;

/**
 * EclipseLink platform
 */
public class EclipseLinkJpaPlatformProvider
	extends AbstractJpaPlatformProvider
{
	
	public static final String ID = "org.eclipse.eclipselink.platform"; //$NON-NLS-1$

	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLinkJpaPlatformProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkJpaPlatformProvider() {
		super();
	}

	
	// ********* JPA files *********	
	
	@Override
	protected void addResourceModelProvidersTo(List<JpaResourceModelProvider> providers) {
		providers.add(EclipseLinkOrmResourceModelProvider.instance());
	}

	
	// ********* java *********	

	@Override
	protected void addJavaTypeMappingProvidersTo(@SuppressWarnings("unused") List<JavaTypeMappingProvider> providers) {
		//none specific to EclipseLink
	}

	@Override
	protected void addJavaAttributeMappingProvidersTo(List<JavaAttributeMappingProvider> providers) {
		providers.add(JavaBasicCollectionMappingProvider.instance());
		providers.add(JavaBasicMapMappingProvider.instance());
		providers.add(JavaTransformationMappingProvider.instance());
		providers.add(JavaVariableOneToOneMappingProvider.instance());
	}

	@Override
	protected void addDefaultJavaAttributeMappingProvidersTo(List<DefaultJavaAttributeMappingProvider> providers) {
		// these need to be checked first, so we can check for Basic last in case the reference object is Serializable
		providers.add(EclipseLinkJavaOneToOneMappingProvider.instance());
		providers.add(EclipseLinkJavaOneToManyMappingProvider.instance());
		providers.add(JavaVariableOneToOneMappingProvider.instance());
	}
	
	
	// ********* ORM *********
	
	@Override
	protected void addOrmTypeMappingProvidersTo(@SuppressWarnings("unused") List<OrmTypeMappingProvider> providers) {
		//none specific to EclipseLink		
	}
	
	@Override
	protected void addMappingFileProvidersTo(List<MappingFileProvider> providers) {
		providers.add(EclipseLinkMappingFileProvider.instance());
	}

	@Override
	protected void addExtendedOrmTypeMappingProvidersTo(List<ExtendedOrmTypeMappingProvider> providers) {
		providers.add(EclipseLinkOrmEmbeddableProvider.instance());
		providers.add(EclipseLinkOrmEntityProvider.instance());
		providers.add(EclipseLinkOrmMappedSuperclassProvider.instance());
	}

	@Override
	protected void addOrmAttributeMappingProvidersTo(List<OrmAttributeMappingProvider> providers) {
		providers.add(OrmBasicCollectionMappingProvider.instance());
		providers.add(OrmBasicMapMappingProvider.instance());
		providers.add(OrmTransformationMappingProvider.instance());
		providers.add(OrmVariableOneToOneMappingProvider.instance());
	}

	@Override
	protected void addExtendedOrmAttributeMappingProvidersTo(List<ExtendedOrmAttributeMappingProvider> providers) {
		providers.add(EclipseLinkOrmBasicMappingProvider.instance());
		providers.add(EclipseLinkOrmIdMappingProvider.instance());
		providers.add(EclipseLinkOrmEmbeddedIdMappingProvider.instance());
		providers.add(EclipseLinkOrmEmbeddedMappingProvider.instance());
		providers.add(EclipseLinkOrmManyToManyMappingProvider.instance());
		providers.add(EclipseLinkOrmManyToOneMappingProvider.instance());
		providers.add(EclipseLinkOrmOneToManyMappingProvider.instance());
		providers.add(EclipseLinkOrmOneToOneMappingProvider.instance());
		providers.add(EclipseLinkOrmVersionMappingProvider.instance());
	}

}
