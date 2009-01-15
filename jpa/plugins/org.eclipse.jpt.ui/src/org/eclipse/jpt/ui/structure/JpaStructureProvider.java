/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.structure;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;

/**
 * This provider is responsible to create the JPA Structure view contents and 
 * labels for a given JPA resource.
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaStructureProvider {

	/**
	 * Return the type of content supported by the structure provider.
	 */
	IContentType getContentType();

	/**
	 * Build an factory to create {@link TreeItemContentProvider}s
	 */
	TreeItemContentProviderFactory getTreeItemContentProviderFactory();

	/**
	 * Build a factory to create {@link ItemLabelProvider}s
	 */
	ItemLabelProviderFactory getItemLabelProviderFactory();

}
