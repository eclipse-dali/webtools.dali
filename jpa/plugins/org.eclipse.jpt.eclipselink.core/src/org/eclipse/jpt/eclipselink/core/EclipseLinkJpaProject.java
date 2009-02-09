/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

/**
 * A JPA project is associated with an Eclipse project (and its corresponding
 * Java project). It holds the "resource" model that corresponds to the various
 * JPA-related resources (the persistence.xml file, its mapping files [orm.xml],
 * and the Java source files). It also holds the "context" model that represents
 * the JPA metadata, as derived from spec-defined defaults, Java source code
 * annotations, and XML descriptors.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface EclipseLinkJpaProject
	extends JpaProject
{

	/**
	 * Return the resource model object that corresponds to the file
	 * 'META-INF/eclipselink-orm.xml' if that file has the eclipselink ContentType
	 * 
	 * @see JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH
	 */
	JpaXmlResource getDefaultEclipseLinkOrmXmlResource();
	
}
