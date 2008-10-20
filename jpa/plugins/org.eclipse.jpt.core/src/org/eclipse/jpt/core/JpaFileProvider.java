/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.resources.IFile;

/**
 * Map a content type to a JPA file.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaFileProvider {

	/**
	 * Return the JPA file's content type ID.
	 */
	String getContentId();

	/**
	 * Build a JPA file for the specified JPA project and file.
	 * Use the specified factory for creation so extenders can simply override
	 * the appropriate creation method instead of building a provider for the
	 * same content.
	 */
	JpaFile buildJpaFile(JpaProject jpaProject, IFile file, JpaFactory factory);

}
