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

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

public class BaseJpaNavigatorLabelProvider implements ICommonLabelProvider
{
	public void init(ICommonContentExtensionSite config) {
	// TODO Auto-generated method stub
	}

	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getText(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
	// TODO Auto-generated method stub
	}

	public void dispose() {
	// TODO Auto-generated method stub
	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	// TODO Auto-generated method stub
	}

	public void restoreState(IMemento memento) {
	// TODO Auto-generated method stub
	}

	public void saveState(IMemento memento) {
	// TODO Auto-generated method stub
	}

	public String getDescription(Object anElement) {
		// TODO Auto-generated method stub
		return null;
	}
}
