/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;

/**
 * EclipseLink-specific JPA project.
 */
public class EclipseLinkJpaProjectImpl
		extends AbstractJpaProject
		implements EclipseLinkJpaProject {
	
	public EclipseLinkJpaProjectImpl(JpaProject.Config config) {
		super(config);
	}
	
	public JptXmlResource getDefaultEclipseLinkOrmXmlResource() {
		return (JptXmlResource) this.getResourceModel(
				XmlEntityMappings.DEFAULT_RUNTIME_PATH,
				XmlEntityMappings.CONTENT_TYPE);
	}
}
