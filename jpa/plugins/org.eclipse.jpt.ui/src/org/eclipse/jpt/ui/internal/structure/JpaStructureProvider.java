/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jpt.ui.internal.jface.ItemLabelProvider;
import org.eclipse.jpt.ui.internal.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.ui.internal.jface.TreeItemContentProvider;
import org.eclipse.jpt.ui.internal.jface.TreeItemContentProviderFactory;

public interface JpaStructureProvider
{
	// TODO - change this
	Object getInput();

	/**
	 * Build an factory to create {@link TreeItemContentProvider}s
	 */
	TreeItemContentProviderFactory treeItemContentProviderFactory();

	/**
	 * Build a factory to create {@link ItemLabelProvider}s
	 */
	ItemLabelProviderFactory itemLabelProviderFactory();

	void dispose();
}
