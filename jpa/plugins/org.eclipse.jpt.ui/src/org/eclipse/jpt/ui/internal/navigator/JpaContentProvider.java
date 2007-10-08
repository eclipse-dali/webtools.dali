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
package org.eclipse.jpt.ui.internal.navigator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;

public class JpaContentProvider
	implements ITreeContentProvider
{
	public JpaContentProvider() {
		super();
	}
	
	/**
	 * @see IContentProvider#dispose()
	 */
	public void dispose() {
		// TODO 
	}
	
	/**
	 * @see IContentProvider#inputChanged(Viewer, Object, Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @see ITreeContentProvider#getParent(Object)
	 */
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see ITreeContentProvider#hasChildren(Object)
	 */
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/** 
	 * @see ITreeContentProvider#getChildren(Object)
	 */
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IAdaptable) {
			IProject project = (IProject) ((IAdaptable) parentElement).getAdapter(IProject.class);
			
			if (project != null) {
				IJpaProject jpaProject = JptCorePlugin.getJpaProject(project);
				
				if (jpaProject != null) {
					return new Object[] {jpaProject.getContextModel()};
				}
			}
		}
		
		return new Object[0];
	}
	
	/**
	 * @see IStructuredContentProvider#getElements(Object)
	 */
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}
}
