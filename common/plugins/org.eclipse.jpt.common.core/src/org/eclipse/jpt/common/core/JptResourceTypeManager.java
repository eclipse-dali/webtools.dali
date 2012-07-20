/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core;

import org.eclipse.core.runtime.content.IContentType;

/**
 * The <code>org.eclipse.jpt.common.core.resourceTypes</code> extension point
 * corresponding to a {@link JptWorkspace Dali workspace}.
 * <p>
 * See <code>org.eclipse.jpt.common.core/plugin.xml:resourceTypes</code>.
 * <p>
 * Not intended to be implemented by clients.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see JptResourceType
 * @version 3.3
 * @since 3.3
 */
public interface JptResourceTypeManager {
	/**
	 * Return the manager's Dali workspace.
	 */
	JptWorkspace getJptWorkspace();


	// ********** resource types **********

	/**
	 * Return the defined resource types.
	 */
	Iterable<JptResourceType> getResourceTypes();

	/**
	 * Return the defined resource types for the specified content type.
	 */
	Iterable<JptResourceType> getResourceTypes(IContentType contentType);

	/**
	 * Return the resource type for the specified content type and
	 * {@link JptResourceType#UNDETERMINED_VERSION indeterminate version}.
	 * Return <code>null</code> if the resource type does not exist.
	 */
	JptResourceType getResourceType(IContentType contentType);

	/**
	 * Return the resource type for the specified content type and version.
	 * Return <code>null</code> if the resource type does not exist.
	 */
	JptResourceType getResourceType(IContentType contentType, String version);
}
