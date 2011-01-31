/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.navigator;

import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProviderFactory;

/**
 * Defines content and label provider factories for Project Navigator view for a given JAXB project.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.0
 * @since 3.0
 */
public interface JaxbNavigatorUi {
	
	/**
	 * Return the factory to create {@link TreeItemContentProvider}s
	 */
	TreeItemContentProviderFactory getTreeItemContentProviderFactory();
	
	/**
	 * Return the factory to create {@link ItemLabelProvider}s
	 */
	ItemLabelProviderFactory getItemLabelProviderFactory();
}
