/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core;

import org.eclipse.core.resources.IFile;

/**
 * Listeners are notified whenever anything in the JPT resource model changes.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.2
 */
public interface JptResourceModel
	extends JptResourceTypeReference
{
	/**
	 * Return the model's file.
	 */
	IFile getFile();

	/**
	 * Changes to the resource model result in events.
	 * In particular, the JPA project performs an "update" whenever a resource
	 * model changes.
	 */
	void addResourceModelListener(JptResourceModelListener listener);

	/**
	 * @see #addResourceModelListener(JptResourceModelListener)
	 */
	void removeResourceModelListener(JptResourceModelListener listener);
}
