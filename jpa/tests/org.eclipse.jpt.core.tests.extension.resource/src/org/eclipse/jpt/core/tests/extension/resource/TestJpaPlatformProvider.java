/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.ResourceDefinition;
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
import org.eclipse.jpt.core.internal.context.java.JavaOneToManyMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaOneToOneMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaTransientMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaVersionMappingDefinition;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericPersistenceXmlDefinition;

public class TestJpaPlatformProvider extends AbstractJpaPlatformProvider
{
	public static final String ID = "core.testJpaPlatform"; //$NON-NLS-1$
	
	// singleton
	private static final JpaPlatformProvider INSTANCE = 
			new TestJpaPlatformProvider();
	
	
	/**
	 * Return the singleton.
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private TestJpaPlatformProvider() {
		super();
	}
	
	
	// ********** resource models **********
	
	public JpaResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		if (contentType.equals(JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE)) {
			return JptCorePlugin.JAVA_SOURCE_RESOURCE_TYPE;
		}
		else if (contentType.equals(JptCorePlugin.JAR_CONTENT_TYPE)) {
			return JptCorePlugin.JAR_RESOURCE_TYPE;
		}
		else if (contentType.equals(JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE)) {
			return JptCorePlugin.PERSISTENCE_XML_1_0_RESOURCE_TYPE;
		}
		else if (contentType.equals(JptCorePlugin.ORM_XML_CONTENT_TYPE)) {
			return JptCorePlugin.ORM_XML_1_0_RESOURCE_TYPE;
		}
		else {
			throw new IllegalArgumentException(contentType.toString());
		}
	}
	
	@Override
	protected JpaResourceModelProvider[] buildResourceModelProviders() {
		// order should not be important here
		return new JpaResourceModelProvider[] {
			JavaResourceModelProvider.instance(),
			JarResourceModelProvider.instance(),
			PersistenceResourceModelProvider.instance(),
			OrmResourceModelProvider.instance()};
	}
	
	
	// ********** Java type mappings **********
	
	@Override
	protected JavaTypeMappingDefinition[] buildNonNullJavaTypeMappingDefinitions() {
		// order determined by analyzing order that reference implementation (toplink) uses
		return new JavaTypeMappingDefinition[] {
			JavaEntityDefinition.instance(),
			JavaEmbeddableDefinition.instance(),
			JavaMappedSuperclassDefinition.instance(),
			JavaTestTypeMappingDefinition.instance()};  // added
	}


	// ********** Java attribute mappings **********
	@Override
	protected JavaAttributeMappingDefinition[] buildNonNullDefaultJavaAttributeMappingDefinitions() {
		// order determined by analyzing order that reference implementation (toplink) uses
		return new JavaAttributeMappingDefinition[] {
			JavaEmbeddedMappingDefinition.instance(),
			JavaBasicMappingDefinition.instance()};
	}
	
	@Override
	protected JavaAttributeMappingDefinition[] buildNonNullSpecifiedJavaAttributeMappingDefinitions() {
		// order determined by analyzing order that reference implementation (toplink) uses
		return new JavaAttributeMappingDefinition[] {
			JavaTransientMappingDefinition.instance(),
			JavaIdMappingDefinition.instance(),
			JavaVersionMappingDefinition.instance(),
			JavaBasicMappingDefinition.instance(),
			JavaEmbeddedMappingDefinition.instance(),
			JavaEmbeddedIdMappingDefinition.instance(),
			JavaManyToManyMappingDefinition.instance(),
			JavaManyToOneMappingDefinition.instance(),
			JavaOneToManyMappingDefinition.instance(),
			JavaOneToOneMappingDefinition.instance(),
			JavaTestAttributeMappingDefinition.instance()}; // added
	}
	
	// ********** Mapping Files **********
	
	@Override
	protected ResourceDefinition[] buildResourceDefinitions() {
		return new ResourceDefinition[] {
			GenericPersistenceXmlDefinition.instance(),
			GenericOrmXmlDefinition.instance()};
	}
}
