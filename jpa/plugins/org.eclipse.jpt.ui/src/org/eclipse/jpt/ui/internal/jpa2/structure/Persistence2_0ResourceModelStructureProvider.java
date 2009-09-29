/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.structure;

import org.eclipse.jpt.ui.internal.structure.PersistenceItemContentProviderFactory;
import org.eclipse.jpt.ui.internal.structure.PersistenceItemLabelProviderFactory;
import org.eclipse.jpt.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

public class Persistence2_0ResourceModelStructureProvider
	implements JpaStructureProvider
{
	// singleton
	private static final JpaStructureProvider INSTANCE = new Persistence2_0ResourceModelStructureProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaStructureProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singelton usage
	 */
	private Persistence2_0ResourceModelStructureProvider() {
		super();
	}
	
	
	public TreeItemContentProviderFactory getTreeItemContentProviderFactory() {
		return new PersistenceItemContentProviderFactory();
	}
	
	public ItemLabelProviderFactory getItemLabelProviderFactory() {
		return new PersistenceItemLabelProviderFactory();
	}
}
