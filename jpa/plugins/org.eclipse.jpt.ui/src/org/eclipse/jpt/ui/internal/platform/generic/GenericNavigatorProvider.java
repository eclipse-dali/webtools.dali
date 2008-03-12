/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.generic;

import org.eclipse.jpt.ui.internal.jface.ItemLabelProviderFactory;
import org.eclipse.jpt.ui.internal.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;

public class GenericNavigatorProvider implements JpaNavigatorProvider
{
	public ItemLabelProviderFactory itemLabelProviderFactory() {
		return new GenericNavigatorItemLabelProviderFactory();
	}

	public TreeItemContentProviderFactory treeItemContentProviderFactory() {
		return new GenericNavigatorItemContentProviderFactory();
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
	}
}
