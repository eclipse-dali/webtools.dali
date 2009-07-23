/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1;

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
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.EclipseLinkMappingFile1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmBasicCollectionMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmBasicMapMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmBasicMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmEmbeddedIdMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmEmbeddedMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmIdMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmManyToManyMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmManyToOneMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmNullAttributeMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmOneToManyMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmOneToOneMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmTransformationMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmTransientMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmVariableOneToOneMapping1_1Provider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmVersionMapping1_1Provider;

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
	protected void addJavaTypeMappingProvidersTo(List<JavaTypeMappingProvider> providers) {
		//none specific to EclipseLink1.1
	}

	@Override
	protected void addJavaAttributeMappingProvidersTo(List<JavaAttributeMappingProvider> providers) {
		//none specific to EclipseLink1.1
	}

	@Override
	protected void addDefaultJavaAttributeMappingProvidersTo(List<DefaultJavaAttributeMappingProvider> providers) {
		//none specific to EclipseLink1.1
	}
	
	
	// ********* ORM *********
	
	@Override
	protected void addOrmTypeMappingProvidersTo(List<OrmTypeMappingProvider> providers) {
		//none specific to EclipseLink 1.1
	}
	
	@Override
	protected void addMappingFileProvidersTo(List<MappingFileProvider> providers) {
		providers.add(EclipseLinkMappingFile1_1Provider.instance());
	}

	@Override
	protected void addOrmAttributeMappingProvidersTo(List<OrmAttributeMappingProvider> providers) {
		providers.add(EclipseLinkOrmBasicCollectionMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmBasicMapMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmTransformationMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmVariableOneToOneMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmBasicMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmIdMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmEmbeddedIdMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmEmbeddedMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmManyToManyMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmManyToOneMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmOneToManyMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmOneToOneMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmVersionMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmTransientMapping1_1Provider.instance());
		providers.add(EclipseLinkOrmNullAttributeMapping1_1Provider.instance());
	}


}
