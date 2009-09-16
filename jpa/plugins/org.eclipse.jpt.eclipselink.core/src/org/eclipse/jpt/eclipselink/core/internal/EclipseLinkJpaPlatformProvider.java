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

import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.context.MappingFileDefinition;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.core.internal.AbstractJpaPlatformProvider;
import org.eclipse.jpt.core.internal.JarResourceModelProvider;
import org.eclipse.jpt.core.internal.JavaResourceModelProvider;
import org.eclipse.jpt.core.internal.OrmResourceModelProvider;
import org.eclipse.jpt.core.internal.PersistenceResourceModelProvider;
import org.eclipse.jpt.core.internal.context.java.JavaBasicMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddableDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedIdMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaEntityDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaIdMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaManyToManyMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaManyToOneMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaMappedSuperclassDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaTransientMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaVersionMappingDefinition;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicCollectionMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToManyMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToOneMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMappingDefinition;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition;

/**
 * EclipseLink platform
 */
public class EclipseLinkJpaPlatformProvider
	extends AbstractJpaPlatformProvider
{
	public static final String ID = "org.eclipse.eclipselink.platform"; //$NON-NLS-1$

	// singleton
	private static final JpaPlatformProvider INSTANCE = 
			new EclipseLinkJpaPlatformProvider();
	
	
	/**
	 * Return the singleton.
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJpaPlatformProvider() {
		super();
	}
	
	
	// ********* resource models *********	
	
	@Override
	protected JpaResourceModelProvider[] buildResourceModelProviders() {
		// order should not be important here
		return new JpaResourceModelProvider[] {
			JavaResourceModelProvider.instance(),
			JarResourceModelProvider.instance(),
			PersistenceResourceModelProvider.instance(),
			OrmResourceModelProvider.instance(),
			EclipseLinkOrmResourceModelProvider.instance()};
	}

	
	// ********* java type mappings *********	
	
	@Override
	protected JavaTypeMappingDefinition[] buildNonNullJavaTypeMappingDefinitions() {
		// order determined by analyzing order that eclipselink uses
		// NOTE: no type mappings specific to eclipselink
		return new JavaTypeMappingDefinition[] {
			JavaEntityDefinition.instance(),
			JavaEmbeddableDefinition.instance(),
			JavaMappedSuperclassDefinition.instance()};
	}
	
	
	// ********* java attribute mappings *********	
	
	@Override
	protected JavaAttributeMappingDefinition[] buildNonNullDefaultJavaAttributeMappingDefinitions() {
		// order determined by analyzing order that eclipselink uses
		return new JavaAttributeMappingDefinition[] {
			JavaEmbeddedMappingDefinition.instance(),
			JavaEclipseLinkOneToManyMappingDefinition.instance(),
			JavaEclipseLinkOneToOneMappingDefinition.instance(),
			JavaEclipseLinkVariableOneToOneMappingDefinition.instance(),
			JavaBasicMappingDefinition.instance()};
	}
	
	@Override
	protected JavaAttributeMappingDefinition[] buildNonNullSpecifiedJavaAttributeMappingDefinitions() {
		// order determined by analyzing order that eclipselink uses
		return new JavaAttributeMappingDefinition[] {
			JavaTransientMappingDefinition.instance(),
			JavaEclipseLinkBasicCollectionMappingDefinition.instance(),
			JavaEclipseLinkBasicMapMappingDefinition.instance(),
			JavaIdMappingDefinition.instance(),
			JavaVersionMappingDefinition.instance(),
			JavaBasicMappingDefinition.instance(),
			JavaEmbeddedMappingDefinition.instance(),
			JavaEmbeddedIdMappingDefinition.instance(),
			JavaEclipseLinkTransformationMappingDefinition.instance(),
			JavaManyToManyMappingDefinition.instance(),
			JavaManyToOneMappingDefinition.instance(),
			JavaEclipseLinkOneToManyMappingDefinition.instance(),
			JavaEclipseLinkOneToOneMappingDefinition.instance(),
			JavaEclipseLinkVariableOneToOneMappingDefinition.instance()};
	}
	
	
	// ********* mapping files *********	
	
	@Override
	protected MappingFileDefinition[] buildMappingFileDefinitions() {
		// order should not be important here
		return new MappingFileDefinition[] {
			EclipseLinkOrmXmlDefinition.instance(),
			GenericOrmXmlDefinition.instance()};
	}
}
