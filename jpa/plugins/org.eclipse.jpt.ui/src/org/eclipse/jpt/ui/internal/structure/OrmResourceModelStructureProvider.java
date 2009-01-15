/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

public class OrmResourceModelStructureProvider
	implements JpaStructureProvider
{
	// singleton
	private static final JpaStructureProvider INSTANCE = new OrmResourceModelStructureProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaStructureProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmResourceModelStructureProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.ORM_XML_CONTENT_TYPE;
	}

	public TreeItemContentProviderFactory getTreeItemContentProviderFactory() {
		return new OrmItemContentProviderFactory();
	}
	
	public ItemLabelProviderFactory getItemLabelProviderFactory() {
		return new OrmItemLabelProviderFactory();
	}

}
