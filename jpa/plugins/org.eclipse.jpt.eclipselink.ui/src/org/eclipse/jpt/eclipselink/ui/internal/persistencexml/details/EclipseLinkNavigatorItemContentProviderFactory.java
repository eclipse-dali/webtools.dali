/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistencexml.details;

import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.platform.generic.PersistenceXmlItemContentProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;

/**
 * EclipseLinkNavigatorItemContentProviderFactory
 */
public class EclipseLinkNavigatorItemContentProviderFactory
					implements TreeItemContentProviderFactory
{
	public TreeItemContentProvider buildItemContentProvider(
					Object item, 
					DelegatingContentAndLabelProvider contentAndLabelProvider) {
		
		DelegatingTreeContentAndLabelProvider treeContentAndLabelProvider = 
			(DelegatingTreeContentAndLabelProvider) contentAndLabelProvider;
		
		if (item instanceof PersistenceXml) {
			return new PersistenceXmlItemContentProvider((PersistenceXml) item, treeContentAndLabelProvider);
		}
		return null;
	}
}
