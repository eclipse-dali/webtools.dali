/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_2;

import java.util.ArrayList;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.JpaPlatformProvider;
import org.eclipse.jpt.jpa.core.ResourceDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_1.AbstractEclipseLink2_1JpaPlatformProvider;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_2.context.orm.EclipseLinkOrmXml2_2Definition;

public class EclipseLink2_2JpaPlatformProvider
	extends AbstractEclipseLink2_1JpaPlatformProvider
{
	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLink2_2JpaPlatformProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLink2_2JpaPlatformProvider() {
		super();
	}
	
	
	// ********** resource models **********
	
	@Override
	public JptResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		if (contentType.equals(JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE)) {
			return JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_2_RESOURCE_TYPE;
		}
		return super.getMostRecentSupportedResourceType(contentType);
	}
	
	
	// ********** resource definitions **********

	@Override
	protected void addResourceDefinitionsTo(ArrayList<ResourceDefinition> definitions) {
		super.addResourceDefinitionsTo(definitions);
		definitions.add(EclipseLinkOrmXml2_2Definition.instance());
	}
}
