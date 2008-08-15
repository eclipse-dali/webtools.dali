/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PersistenceUnitDefaults extends OrmJpaContextNode
{

	/**
	 * Return the specifiedSchema if not null, otherwise return the defaultSchema.
	 */
	String getSchema();
	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchemaProperty";
	String getSpecifiedSchema();
	void setSpecifiedSchema(String newSpecifiedSchema);
		String SPECIFIED_SCHEMA_PROPERTY = "specifiedSchemaProperty";

	/**
	 * Return the specifiedCatalog if not null, otherwise return the defaultCatalog.
	 */
	String getCatalog();
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalogProperty";
	String getSpecifiedCatalog();
	void setSpecifiedCatalog(String newSpecifiedCatalog);
		String SPECIFIED_CATALOG_PROPERTY = "specifiedCatalogProperty";

	AccessType getAccess();
	void setAccess(AccessType value);
		String ACCESS_PROPERTY = "accessProperty";

	boolean isCascadePersist();

	void setCascadePersist(boolean value);
	String CASCADE_PERSIST_PROPERTY = "cascadePersistProperty";
	
	
	/**
	 * Update the PersistenceUnitDefaults context model object to match the XmlEntityMappings 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(XmlEntityMappings entityMappings);

}
