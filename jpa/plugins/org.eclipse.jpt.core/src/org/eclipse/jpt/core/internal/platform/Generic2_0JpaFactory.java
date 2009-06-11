/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

/**
 * Central class that allows extenders to easily replace implementations of
 * various Dali interfaces.
 */
public class Generic2_0JpaFactory extends GenericJpaFactory
{
	public Generic2_0JpaFactory() {
		super();
	}
//	// ********** Context Nodes **********
//	
//	public MappingFile build2_0MappingFile(MappingFileRef parent, JpaXmlResource resource) {
//		return this.buildOrm2_0Xml(parent, resource);
//	}
//	
//	protected Orm2_0Xml buildOrm2_0Xml(MappingFileRef parent, JpaXmlResource resource) {
//		return new Orm2_0Xml(parent, resource);
//	}
//
//	
//	// ********** EclipseLink1.1-specific ORM Context Model **********
//	
//	public EntityMappings buildGeneric2_0EntityMappings(Generic2_0OrmXml parent, XmlEntityMappings xmlEntityMappings) {
//		return new Generic2_0EntityMappingsImpl(parent, xmlEntityMappings);
//	}

}
