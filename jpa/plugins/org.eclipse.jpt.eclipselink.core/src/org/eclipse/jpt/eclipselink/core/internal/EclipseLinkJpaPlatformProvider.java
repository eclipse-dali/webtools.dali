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
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.internal.platform.AbstractJpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkMappingFileProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToManyMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicCollectionMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkEmbeddableProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkEmbeddedIdMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkEmbeddedMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkEntityProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkIdMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkManyToManyMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkManyToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkMappedSuperclassProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkNullAttributeMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkOneToManyMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkTransientMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkVersionMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicCollectionMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMapMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkTransformationMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkVariableOneToOneMappingProvider;

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
	protected void addJavaTypeMappingProvidersTo(List<JavaTypeMappingProvider> providers) {
		//none specific to EclipseLink
	}

	@Override
	protected void addJavaAttributeMappingProvidersTo(List<JavaAttributeMappingProvider> providers) {
		providers.add(JavaEclipseLinkBasicCollectionMappingProvider.instance());
		providers.add(JavaEclipseLinkBasicMapMappingProvider.instance());
		providers.add(JavaEclipseLinkTransformationMappingProvider.instance());
		providers.add(JavaEclipseLinkVariableOneToOneMappingProvider.instance());
	}

	@Override
	protected void addDefaultJavaAttributeMappingProvidersTo(List<DefaultJavaAttributeMappingProvider> providers) {
		// these need to be checked first, so we can check for Basic last in case the reference object is Serializable
		providers.add(JavaEclipseLinkOneToOneMappingProvider.instance());
		providers.add(JavaEclipseLinkOneToManyMappingProvider.instance());
		providers.add(JavaEclipseLinkVariableOneToOneMappingProvider.instance());
	}
	
	
	// ********* ORM *********
	
	@Override
	protected void addOrmTypeMappingProvidersTo(List<OrmTypeMappingProvider> providers) {
		providers.add(OrmEclipseLinkEmbeddableProvider.instance());
		providers.add(OrmEclipseLinkEntityProvider.instance());
		providers.add(OrmEclipseLinkMappedSuperclassProvider.instance());
	}
	
	@Override
	protected void addMappingFileProvidersTo(List<MappingFileProvider> providers) {
		providers.add(EclipseLinkMappingFileProvider.instance());
	}

	@Override
	protected void addOrmAttributeMappingProvidersTo(List<OrmAttributeMappingProvider> providers) {
		providers.add(OrmEclipseLinkBasicCollectionMappingProvider.instance());
		providers.add(OrmEclipseLinkBasicMapMappingProvider.instance());
		providers.add(OrmEclipseLinkTransformationMappingProvider.instance());
		providers.add(OrmEclipseLinkVariableOneToOneMappingProvider.instance());
		providers.add(OrmEclipseLinkBasicMappingProvider.instance());
		providers.add(OrmEclipseLinkIdMappingProvider.instance());
		providers.add(OrmEclipseLinkEmbeddedIdMappingProvider.instance());
		providers.add(OrmEclipseLinkEmbeddedMappingProvider.instance());
		providers.add(OrmEclipseLinkManyToManyMappingProvider.instance());
		providers.add(OrmEclipseLinkManyToOneMappingProvider.instance());
		providers.add(OrmEclipseLinkOneToManyMappingProvider.instance());
		providers.add(OrmEclipseLinkOneToOneMappingProvider.instance());
		providers.add(OrmEclipseLinkVersionMappingProvider.instance());
		providers.add(OrmEclipseLinkTransientMappingProvider.instance());
		providers.add(OrmEclipseLinkNullAttributeMappingProvider.instance());
	}

}
