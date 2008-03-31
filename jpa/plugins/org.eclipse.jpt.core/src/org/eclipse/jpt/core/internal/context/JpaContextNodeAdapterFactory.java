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
package org.eclipse.jpt.core.internal.context;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.core.context.JpaContextNode;

public class JpaContextNodeAdapterFactory
	implements IAdapterFactory
{
	private static Class[] PROPERTIES = 
		new Class[] { IResource.class, IFile.class };
	
	
	public Class[] getAdapterList() {
		return PROPERTIES;
	}
	
	public Object getAdapter(Object element, Class key) {
		JpaContextNode node;
		
		if (element instanceof JpaContextNode) {
			node = (JpaContextNode) element;
		}
		else {
			return null;
		}
		
		IResource resource = node.getResource();
		
		if (key.equals(IResource.class)) {
			return resource;
		}
		else if (key.equals(IFile.class) && resource != null && resource.getType() == IResource.FILE) {
			return (IFile) resource;
		}
		
		return null;
	}	
}