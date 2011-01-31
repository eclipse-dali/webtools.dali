/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_1;

import java.util.ArrayList;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.core.ResourceDefinition;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.AbstractEclipseLink2_0JpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.internal.v2_1.context.orm.EclipseLinkOrmXml2_1Definition;

public abstract class AbstractEclipseLink2_1JpaPlatformProvider
	extends AbstractEclipseLink2_0JpaPlatformProvider
{
	protected AbstractEclipseLink2_1JpaPlatformProvider() {
		super();
	}


	// ********** resource models **********

	@Override
	public JptResourceType getMostRecentSupportedResourceType(IContentType contentType) {
		if (contentType.equals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE)) {
			return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_2_1_RESOURCE_TYPE;
		}
		return super.getMostRecentSupportedResourceType(contentType);
	}


	// ********** resource definitions **********

	@Override
	protected void addResourceDefinitionsTo(ArrayList<ResourceDefinition> definitions) {
		super.addResourceDefinitionsTo(definitions);
		definitions.add(EclipseLinkOrmXml2_1Definition.instance());
	}
}
