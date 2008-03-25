/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.platform;

import org.eclipse.jpt.eclipselink.ui.internal.persistencexml.details.EclipseLinkNavigatorItemContentProviderFactory;
import org.eclipse.jpt.eclipselink.ui.internal.persistencexml.details.EclipseLinkNavigatorItemLabelProviderFactory;
import org.eclipse.jpt.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;

/**
 * EclipseLinkNavigatorProvider
 */
public class EclipseLinkNavigatorProvider implements JpaNavigatorProvider
{
	public ItemLabelProviderFactory itemLabelProviderFactory() {
		return new EclipseLinkNavigatorItemLabelProviderFactory();
	}

	public TreeItemContentProviderFactory treeItemContentProviderFactory() {
		return new EclipseLinkNavigatorItemContentProviderFactory();
	}

	public void dispose() {
	
	}
}
