/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingFilePersistenceUnitDefaults2_0;

/**
 * JPA 2.0 context model corresponding to the
 * XML resource model {@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlPersistenceUnitDefaults_2_0},
 * which corresponds to the <code>persistence-unit-defaults</code> element
 * in the <code>orm.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface OrmPersistenceUnitDefaults2_0
	extends MappingFilePersistenceUnitDefaults2_0, OrmPersistenceUnitDefaults
{
	void setDelimitedIdentifiers(boolean value);
		String DELIMITED_IDENTIFIERS_PROPERTY = "delimitedIdentifiers"; //$NON-NLS-1$
}
