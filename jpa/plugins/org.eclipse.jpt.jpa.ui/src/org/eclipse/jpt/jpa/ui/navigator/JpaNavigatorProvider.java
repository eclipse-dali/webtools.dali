/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.navigator;

import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProviderFactory;

/**
 * This provider is responsible to create the Project Navigator view contents and 
 * labels for a given JPA project.
 *
 * @version 2.0
 * @since 2.0
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaNavigatorProvider
{
	/**
	 * Build an factory to create {@link TreeItemContentProvider}s
	 */
	TreeItemContentProviderFactory getTreeItemContentProviderFactory();
	
	/**
	 * Build a factory to create {@link ItemLabelProvider}s
	 */
	ItemLabelProviderFactory getItemLabelProviderFactory();
	
	void dispose();
}
