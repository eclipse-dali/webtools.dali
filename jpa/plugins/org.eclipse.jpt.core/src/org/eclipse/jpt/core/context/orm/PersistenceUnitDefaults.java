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

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.JpaContextNode;


public interface PersistenceUnitDefaults extends JpaContextNode
{

	String getSchema();
	void setSchema(String value);
		String SCHEMA_PROPERTY = "schemaProperty";
		
	String getCatalog();
	void setCatalog(String value);
		String CATALOG_PROPERTY = "catalogProperty";

	AccessType getAccess();
	void setAccess(AccessType value);
		String ACCESS_PROPERTY = "accessProperty";

	boolean isCascadePersist();

	void setCascadePersist(boolean value);
	String CASCADE_PERSIST_PROPERTY = "cascadePersistProperty";
	
	void initialize(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings);
	
	void update(org.eclipse.jpt.core.resource.orm.EntityMappings entityMappings);

}
