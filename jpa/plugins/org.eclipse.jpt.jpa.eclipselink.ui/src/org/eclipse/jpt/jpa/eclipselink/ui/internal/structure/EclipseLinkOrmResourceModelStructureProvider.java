/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.structure;

import org.eclipse.jpt.common.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.jpa.ui.internal.structure.OrmItemContentProviderFactory;
import org.eclipse.jpt.jpa.ui.internal.structure.OrmItemLabelProviderFactory;
import org.eclipse.jpt.jpa.ui.structure.JpaStructureProvider;

public class EclipseLinkOrmResourceModelStructureProvider
	implements JpaStructureProvider
{
	// singleton
	private static final JpaStructureProvider INSTANCE = new EclipseLinkOrmResourceModelStructureProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaStructureProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmResourceModelStructureProvider() {
		super();
	}
	
	
	public TreeItemContentProviderFactory getTreeItemContentProviderFactory() {
		return new OrmItemContentProviderFactory();
	}
	
	public ItemLabelProviderFactory getItemLabelProviderFactory() {
		return new OrmItemLabelProviderFactory();
	}
}
