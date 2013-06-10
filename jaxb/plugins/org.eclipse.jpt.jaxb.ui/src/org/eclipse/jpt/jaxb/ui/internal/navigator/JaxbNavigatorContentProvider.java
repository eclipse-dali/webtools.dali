/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.navigator;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.navigator.NavigatorContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;

/**
 * See org.eclipse.jpt.jpa.ui.internal.navigator.JpaNavigatorContentProvider
 */
public class JaxbNavigatorContentProvider
	extends NavigatorContentProvider
{
	public JaxbNavigatorContentProvider() {
		super();
	}

	@Override
	protected ResourceManager buildResourceManager() {
		return new LocalResourceManager(JFaceResources.getResources(WorkbenchTools.getDisplay()));
	}

	@Override
	protected ItemTreeContentProvider.Factory buildItemContentProviderFactory() {
		return new JaxbNavigatorItemContentProviderFactory();
	}

	@Override
	protected ItemExtendedLabelProvider.Factory buildItemLabelProviderFactory() {
		return new JaxbNavigatorItemLabelProviderFactory();
	}
}
