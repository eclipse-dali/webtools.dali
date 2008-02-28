/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.base;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.context.BaseJpaContent;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;

public class BaseJpaNavigatorContentProvider implements ICommonContentProvider
{
	public void init(ICommonContentExtensionSite config) {
	// TODO Auto-generated method stub
	}

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof BaseJpaContent) {
			BaseJpaContent baseJpaContent = (BaseJpaContent) parentElement;
			if (baseJpaContent.getPersistenceXml() != null) {
				return new Object[] {baseJpaContent.getPersistenceXml()};
			}
		}
		
		return new Object[0];
	}

	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasChildren(Object element) {
		return element instanceof BaseJpaContent;
	}

	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return null;
	}

	public void dispose() {
	// TODO Auto-generated method stub
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	// TODO Auto-generated method stub
	}

	public void restoreState(IMemento memento) {
	// TODO Auto-generated method stub
	}

	public void saveState(IMemento memento) {
	// TODO Auto-generated method stub
	}
}
