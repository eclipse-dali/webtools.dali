/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.JavaJpaFile;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.platform.generic.JavaPersistentTypeItemContentProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProvider;


public class JavaItemContentProviderFactory extends GeneralJpaMappingItemContentProviderFactory
{
	@Override
	public TreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentProvider) {
		DelegatingTreeContentAndLabelProvider treeContentProvider = (DelegatingTreeContentAndLabelProvider) contentProvider;
		if (item instanceof JavaJpaFile) {
			return new ResourceModelItemContentProvider((JavaJpaFile) item, treeContentProvider);
		}
		return super.buildItemContentProvider(item, treeContentProvider);
	}
	
	@Override
	protected TreeItemContentProvider buildPersistentTypeItemContentProvider(PersistentType persistentType, DelegatingTreeContentAndLabelProvider treeContentProvider) {
		return new JavaPersistentTypeItemContentProvider((JavaPersistentType) persistentType, treeContentProvider);
	}
}
