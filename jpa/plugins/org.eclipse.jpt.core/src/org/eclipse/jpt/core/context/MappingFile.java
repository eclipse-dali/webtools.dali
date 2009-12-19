/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistentTypeContainer;

/**
 * JPA mapping file (typically <code>orm.xml</code>).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MappingFile
	extends XmlFile, PersistentTypeContainer
{
	/**
	 * covariant override
	 */
	MappingFileRef getParent();

	/**
	 * Return the mapping file's root.
	 */
	MappingFileRoot getRoot();

	/**
	 * Return the specified persistent type if it is listed in the mapping file;
	 * otherwise return null.
	 */
	PersistentType getPersistentType(String name);

	/**
	 * Update the context mapping file to match its resource mapping file.
	 * @see org.eclipse.jpt.core.JpaProject#update()
	 */
	void update();

}
