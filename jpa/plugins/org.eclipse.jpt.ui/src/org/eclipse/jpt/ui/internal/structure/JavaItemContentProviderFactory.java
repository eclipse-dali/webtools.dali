/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.platform.generic.JavaPersistentTypeItemContentProvider;
import org.eclipse.jpt.ui.internal.platform.generic.PersistentAttributeItemContentProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;


public class JavaItemContentProviderFactory implements TreeItemContentProviderFactory
{
	public TreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentProvider) {
		DelegatingTreeContentAndLabelProvider treeContentProvider = (DelegatingTreeContentAndLabelProvider) contentProvider;
		if (item instanceof JpaFile) {
			return new ResourceModelItemContentProvider((JpaFile) item, treeContentProvider);
		}
		if (item instanceof JavaPersistentType) {
			return new JavaPersistentTypeItemContentProvider((JavaPersistentType) item, treeContentProvider);
		}
		if (item instanceof JavaPersistentAttribute) {
			return new PersistentAttributeItemContentProvider((JavaPersistentAttribute) item, treeContentProvider);
		}
		return null;
	}
}
