/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.MappingFilePersistenceUnitDefaults;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;

/**
 * Context model corresponding to the
 * XML resource model {@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults},
 * which corresponds to the <code>persistence-unit-defaults</code> element
 * in the <code>orm.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface OrmPersistenceUnitDefaults
	extends MappingFilePersistenceUnitDefaults
{
	/**
	 * Covariant override.
	 */
	OrmPersistenceUnitMetadata getParent();

	// ********** access **********
	void setAccess(AccessType value);
		String ACCESS_PROPERTY = "access"; //$NON-NLS-1$

	// ********** schema container **********
	SchemaContainer getDbSchemaContainer();

	// ********** catalog **********
	String getSpecifiedCatalog();
	void setSpecifiedCatalog(String newSpecifiedCatalog);
		String SPECIFIED_CATALOG_PROPERTY = "specifiedCatalog"; //$NON-NLS-1$
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalog"; //$NON-NLS-1$
	Catalog getDbCatalog();

	// ********** schema **********
	String getSpecifiedSchema();
	void setSpecifiedSchema(String newSpecifiedSchema);
		String SPECIFIED_SCHEMA_PROPERTY = "specifiedSchema"; //$NON-NLS-1$
	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchema"; //$NON-NLS-1$
	Schema getDbSchema();

	// ********** cascade persist **********
	void setCascadePersist(boolean value);
		String CASCADE_PERSIST_PROPERTY = "cascadePersist"; //$NON-NLS-1$
}
