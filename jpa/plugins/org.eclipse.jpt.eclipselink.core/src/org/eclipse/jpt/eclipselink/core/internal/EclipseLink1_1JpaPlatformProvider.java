/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLink1_1MappingFileProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmBasicCollectionMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmBasicMapMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmBasicMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmEmbeddedIdMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmEmbeddedMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmIdMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmManyToManyMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmManyToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmNullAttributeMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmOneToManyMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmTransformationMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmVariableOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmVersionMappingProvider;

/**
 * EclipseLink platform
 */
public class EclipseLink1_1JpaPlatformProvider
	extends AbstractJpaPlatformProvider
{
	public static final String ID = "eclipselink1_1"; //$NON-NLS-1$

	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLink1_1JpaPlatformProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLink1_1JpaPlatformProvider() {
		super();
	}

	
	// ********* JPA files *********	
	
	@Override
	protected void addResourceModelProvidersTo(List<JpaResourceModelProvider> providers) {
		providers.add(EclipseLink1_1OrmResourceModelProvider.instance());
	}

	
	// ********* java *********	

	@Override
	protected void addJavaTypeMappingProvidersTo(@SuppressWarnings("unused") List<JavaTypeMappingProvider> providers) {
		//none specific to EclipseLink1.1
	}

	@Override
	protected void addJavaAttributeMappingProvidersTo(@SuppressWarnings("unused") List<JavaAttributeMappingProvider> providers) {
		//none specific to EclipseLink1.1
	}

	@Override
	protected void addDefaultJavaAttributeMappingProvidersTo(@SuppressWarnings("unused") List<DefaultJavaAttributeMappingProvider> providers) {
		//none specific to EclipseLink1.1
	}
	
	
	// ********* ORM *********
	
	@Override
	protected void addOrmTypeMappingProvidersTo(@SuppressWarnings("unused") List<OrmTypeMappingProvider> providers) {
		//none specific to EclipseLink 1.1
	}
	
	@Override
	protected void addMappingFileProvidersTo(List<MappingFileProvider> providers) {
		providers.add(EclipseLink1_1MappingFileProvider.instance());
	}

	@Override
	protected void addOrmAttributeMappingProvidersTo(List<OrmAttributeMappingProvider> providers) {
		providers.add(EclipseLink1_1OrmBasicCollectionMappingProvider.instance());
		providers.add(EclipseLink1_1OrmBasicMapMappingProvider.instance());
		providers.add(EclipseLink1_1OrmTransformationMappingProvider.instance());
		providers.add(EclipseLink1_1OrmVariableOneToOneMappingProvider.instance());
		providers.add(EclipseLink1_1OrmBasicMappingProvider.instance());
		providers.add(EclipseLink1_1OrmIdMappingProvider.instance());
		providers.add(EclipseLink1_1OrmEmbeddedIdMappingProvider.instance());
		providers.add(EclipseLink1_1OrmEmbeddedMappingProvider.instance());
		providers.add(EclipseLink1_1OrmManyToManyMappingProvider.instance());
		providers.add(EclipseLink1_1OrmManyToOneMappingProvider.instance());
		providers.add(EclipseLink1_1OrmOneToManyMappingProvider.instance());
		providers.add(EclipseLink1_1OrmOneToOneMappingProvider.instance());
		providers.add(EclipseLink1_1OrmVersionMappingProvider.instance());
		providers.add(EclipseLink1_1OrmNullAttributeMappingProvider.instance());
	}


}
