/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingFilePersistenceUnitMetadata2_0;

/**
 * JPA 2.0
 * Context <code>orm.xml</code> persistence unit metadata.
 * Context model corresponding to the
 * XML resource model {@link XmlPersistenceUnitMetadata},
 * which corresponds to the <code>persistence-unit-metadata</code> element
 * in the <code>orm.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmPersistenceUnitMetadata2_0
	extends MappingFilePersistenceUnitMetadata2_0, OrmPersistenceUnitMetadata
{
	void setDescription(String description);
		String DESCRIPTION_PROPERTY = "description"; //$NON-NLS-1$
}
