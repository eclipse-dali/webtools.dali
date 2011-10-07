/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import java.util.ArrayList;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaPlatformProvider;
import org.eclipse.jpt.jpa.core.ResourceDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEmbeddableDefinition2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaEntityDefinition2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaMappedSuperclassDefinition2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_1Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_2Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_3Definition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_4Definition;


public class EclipseLink2_4JpaPlatformProvider
		extends AbstractEclipseLink2_0JpaPlatformProvider {

	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLink2_4JpaPlatformProvider();


	/**
	 * Return the singleton
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLink2_4JpaPlatformProvider() {
		super();
	}


	// ********** resource models **********

	@Override
	public JptResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		if (contentType.equals(JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE)) {
			return JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_4_RESOURCE_TYPE;
		}
		return super.getMostRecentSupportedResourceType(contentType);
	}


	// ********** resource definitions **********

	@Override
	protected void addResourceDefinitionsTo(ArrayList<ResourceDefinition> definitions) {
		super.addResourceDefinitionsTo(definitions);
		CollectionTools.addAll(definitions, RESOURCE_DEFINITIONS);
	}

	protected static final ResourceDefinition[] RESOURCE_DEFINITIONS = new ResourceDefinition[] {
		EclipseLinkOrmXml2_1Definition.instance(),
		EclipseLinkOrmXml2_2Definition.instance(),
		EclipseLinkOrmXml2_3Definition.instance(),
		EclipseLinkOrmXml2_4Definition.instance()
	};

	// ********* Java type mappings *********

	@Override
	protected void addJavaTypeMappingDefinitionsTo(ArrayList<JavaTypeMappingDefinition> definitions) {
		CollectionTools.addAll(definitions, JAVA_TYPE_MAPPING_DEFINITIONS);
	}

	// order matches that used by EclipseLink
	// NB: no EclipseLink-specific mappings
	protected static final JavaTypeMappingDefinition[] JAVA_TYPE_MAPPING_DEFINITIONS = new JavaTypeMappingDefinition[] {
		EclipseLinkJavaEntityDefinition2_3.instance(),
		EclipseLinkJavaEmbeddableDefinition2_0.instance(),
		EclipseLinkJavaMappedSuperclassDefinition2_3.instance()
	};

}
