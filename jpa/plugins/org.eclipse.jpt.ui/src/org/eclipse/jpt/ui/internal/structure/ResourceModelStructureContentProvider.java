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
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.utility.internal.CollectionTools;


public abstract class ResourceModelStructureContentProvider
	implements ITreeContentProvider
{
	public ResourceModelStructureContentProvider() {
		super();
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
	}
	
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}
	
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IResourceModel) {
			IResourceModel resourceModel = (IResourceModel) parentElement;
			return CollectionTools.array(resourceModel.rootContextNodes());
		}
		return new Object[0];
	}
	
	public boolean hasChildren(Object element) {
		return true;
	}
	
	public Object getParent(Object element) {
		return null;
	}
}
