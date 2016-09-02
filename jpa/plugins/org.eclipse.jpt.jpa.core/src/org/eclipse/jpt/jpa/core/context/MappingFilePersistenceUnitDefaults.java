/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Persistence unit defaults held by a mapping file.
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
public interface MappingFilePersistenceUnitDefaults
	extends AccessReference, SchemaReference
{
	/**
	 * Covariant override.
	 */
	MappingFilePersistenceUnitMetadata getParent();

	/**
	 * Return all the relationhips in the persistence unit are to be cascade
	 * persist, unless overridden by a local annotation or XML setting.
	 */
	boolean isCascadePersist();
		String CASCADE_PERSIST_PROPERTY = "cascadePersist"; //$NON-NLS-1$
}
