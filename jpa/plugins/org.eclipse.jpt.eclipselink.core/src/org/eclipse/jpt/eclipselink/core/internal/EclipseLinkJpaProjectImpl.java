/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.internal.AbstractJpaProject;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.EclipseLinkJpaProject;

/**
 * EclipseLink-specific JPA project.
 */
public class EclipseLinkJpaProjectImpl
	extends AbstractJpaProject
	implements EclipseLinkJpaProject
{


	// ********** constructor/initialization **********

	public EclipseLinkJpaProjectImpl(JpaProject.Config config) throws CoreException {
		super(config);
	}
	
	public JpaXmlResource getDefaultEclipseLinkOrmXmlResource() {
		return (JpaXmlResource) this.getResourceModel(
				JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH,
				JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE
			);
	}

}
