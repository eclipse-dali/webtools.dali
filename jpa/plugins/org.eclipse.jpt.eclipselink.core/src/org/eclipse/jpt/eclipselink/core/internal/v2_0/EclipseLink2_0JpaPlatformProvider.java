/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0;

import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.context.MappingFileDefinition;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.JarResourceModelProvider;
import org.eclipse.jpt.core.internal.JavaResourceModelProvider;
import org.eclipse.jpt.core.internal.OrmResourceModelProvider;
import org.eclipse.jpt.core.internal.PersistenceResourceModelProvider;
import org.eclipse.jpt.core.internal.context.java.JavaBasicMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddableProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedIdMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEntityProvider;
import org.eclipse.jpt.core.internal.context.java.JavaIdMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaManyToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaManyToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaMappedSuperclassProvider;
import org.eclipse.jpt.core.internal.context.java.JavaTransientMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaVersionMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmMappingFileDefinition;
import org.eclipse.jpt.core.internal.platform.AbstractJpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkOrmResourceModelProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkMappingFileDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicCollectionMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToManyMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMappingProvider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.EclipseLink1_1OrmResourceModelProvider;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.EclipseLink1_1MappingFileDefinition;

/**
 *  EclipseLink2_0JpaPlatformProvider
 */
public class EclipseLink2_0JpaPlatformProvider
	extends AbstractJpaPlatformProvider
{
	public static final String ID = "eclipselink2_0"; //$NON-NLS-1$

	// singleton
	private static final JpaPlatformProvider INSTANCE = 
			new EclipseLink2_0JpaPlatformProvider();
	
	
	/**
	 * Return the singleton.
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLink2_0JpaPlatformProvider() {
		super();
	}


	// ********** resource models **********
	
	@Override
	protected JpaResourceModelProvider[] buildResourceModelProviders() {
		// order should not be important here
		return new JpaResourceModelProvider[] {
			JavaResourceModelProvider.instance(),
			JarResourceModelProvider.instance(),
			PersistenceResourceModelProvider.instance(),
			OrmResourceModelProvider.instance(),
			EclipseLinkOrmResourceModelProvider.instance(),
			EclipseLink1_1OrmResourceModelProvider.instance()};
	}

	
	// ********* java type mappings *********	
	
	@Override
	protected JavaTypeMappingProvider[] buildNonNullJavaTypeMappingProviders() {
		// order determined by analyzing order that eclipselink uses
		// NOTE: no type mappings specific to eclipselink
		return new JavaTypeMappingProvider[] {
			JavaEntityProvider.instance(),
			JavaEmbeddableProvider.instance(),
			JavaMappedSuperclassProvider.instance()};
	}
	
	
	// ********* java attribute mappings *********	
	
	@Override
	protected JavaAttributeMappingProvider[] buildNonNullDefaultJavaAttributeMappingProviders() {
		// order determined by analyzing order that eclipselink uses
		// NOTE: no new attribute mappings from eclipselink 1.0 to 1.1
		return new JavaAttributeMappingProvider[] {
			JavaEmbeddedMappingProvider.instance(),
			JavaEclipseLinkOneToManyMappingProvider.instance(),
			JavaEclipseLinkOneToOneMappingProvider.instance(),
			JavaEclipseLinkVariableOneToOneMappingProvider.instance(),
			JavaBasicMappingProvider.instance()};
	}
	
	@Override
	protected JavaAttributeMappingProvider[] buildNonNullSpecifiedJavaAttributeMappingProviders() {
		// order determined by analyzing order that eclipselink uses
		// NOTE: no new attribute mappings from eclipselink 1.0 to 1.1
		return new JavaAttributeMappingProvider[] {
			JavaTransientMappingProvider.instance(),
			JavaEclipseLinkBasicCollectionMappingProvider.instance(),
			JavaEclipseLinkBasicMapMappingProvider.instance(),
			JavaIdMappingProvider.instance(),
			JavaVersionMappingProvider.instance(),
			JavaBasicMappingProvider.instance(),
			JavaEmbeddedMappingProvider.instance(),
			JavaEmbeddedIdMappingProvider.instance(),
			JavaEclipseLinkTransformationMappingProvider.instance(),
			JavaManyToManyMappingProvider.instance(),
			JavaManyToOneMappingProvider.instance(),
			JavaEclipseLinkOneToManyMappingProvider.instance(),
			JavaEclipseLinkOneToOneMappingProvider.instance(),
			JavaEclipseLinkVariableOneToOneMappingProvider.instance()};
	}
	
	
	// ********* mapping files *********	
	
	@Override
	protected MappingFileDefinition[] buildMappingFileDefinitions() {
		// order should not be important here
		return new MappingFileDefinition[] {
			GenericOrmMappingFileDefinition.instance(),
			EclipseLinkMappingFileDefinition.instance(),
			EclipseLink1_1MappingFileDefinition.instance()};
	}


}