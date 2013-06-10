/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.jface;

import org.eclipse.jface.viewers.StructuredViewer;

/**
 * Implementations of this interface can be used to maintain the children
 * of a specific item. The implementation will monitor
 * the item for any changes that affect the list of children and
 * forward them appropriately to the manager.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see org.eclipse.jface.viewers.IContentProvider
 */
public interface ItemContentProvider {
	/**
	 * Dispose the item content provider.
	 * Stop listening to the provider's item, as appropriate.
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */	
	void dispose();


	/**
	 * An item content provider's manager provides a {@link StructuredViewer viewer}
	 * to be used for notifying the manager of any changes on the appropriate UI
	 * thread.
	 */
	interface Manager {
		/**
		 * Return the manager's viewer. This can be used to notify the manager
		 * of any changes on the viewer's UI thread.
		 */
		StructuredViewer getViewer();
	}
}
