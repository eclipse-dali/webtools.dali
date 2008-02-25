/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.JpaContextNode;

public interface PersistenceUnitMetadata extends JpaContextNode
{
	boolean isXmlMappingMetadataComplete();
	void setXmlMappingMetadataComplete(boolean value);
		String XML_MAPPING_METADATA_COMPLETE_PROPERTY = "xmlMappingMetadataCompleteProperty";

	PersistenceUnitDefaults getPersistenceUnitDefaults();
	
	void initialize(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings);
	
	void update(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings);

}
