/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.common.core.ContentTypeReference;
import org.eclipse.jpt.common.core.JptResourceModel;

/**
 * Map a content type to a JPA resource model.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 */
public interface JpaResourceModelProvider
	extends ContentTypeReference
{
	/**
	 * Build a JPA resource model for the specified JPA project and file.
	 * Use the specified factory for creation so extenders can simply override
	 * the appropriate creation method instead of building a provider for the
	 * same content.
	 */
	JptResourceModel buildResourceModel(JpaProject jpaProject, IFile file);
}
