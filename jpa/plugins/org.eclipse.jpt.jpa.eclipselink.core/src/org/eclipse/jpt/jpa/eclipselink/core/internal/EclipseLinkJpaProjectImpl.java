/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaProject;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;

/**
 * EclipseLink-specific JPA project.
 */
public class EclipseLinkJpaProjectImpl
		extends AbstractJpaProject
		implements EclipseLinkJpaProject {
	
	public EclipseLinkJpaProjectImpl(JpaProject.Config config) {
		super(config);
	}
	
	public JpaXmlResource getDefaultEclipseLinkOrmXmlResource() {
		return (JpaXmlResource) this.getResourceModel(
				JptJpaEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH,
				JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE);
	}
}
