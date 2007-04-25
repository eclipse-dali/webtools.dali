/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.jface;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class NullTreeContentProvider 
	implements ITreeContentProvider 
{
	public static NullTreeContentProvider INSTANCE = new NullTreeContentProvider();
	
	
	private NullTreeContentProvider() {
		super();
	}
	
	
	public Object[] getChildren(Object parentElement) {
		return new Object[0];
	}
	
	public Object getParent(Object element) {
		return null;
	}
	
	public boolean hasChildren(Object element) {
		return false;
	}
	
	public Object[] getElements(Object inputElement) {
		return new Object[0];
	}
	
	public void dispose() {}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
}
