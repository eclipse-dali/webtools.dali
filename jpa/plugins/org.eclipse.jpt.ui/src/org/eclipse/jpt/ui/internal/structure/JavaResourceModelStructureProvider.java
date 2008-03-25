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

import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.ui.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.ui.jface.TreeItemContentProviderFactory;

public class JavaResourceModelStructureProvider
	extends ResourceModelStructureProvider
{
	public JavaResourceModelStructureProvider(JavaResourceModel resourceModel) {
		super(resourceModel);
	}
	
	public TreeItemContentProviderFactory treeItemContentProviderFactory() {
		return new JavaItemContentProviderFactory();
	}
	
	public ItemLabelProviderFactory itemLabelProviderFactory() {
		return new JavaItemLabelProviderFactory();
	}
}
