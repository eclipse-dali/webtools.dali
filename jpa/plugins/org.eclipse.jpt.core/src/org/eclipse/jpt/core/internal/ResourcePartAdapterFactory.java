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
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.core.IResourcePart;

public class ResourcePartAdapterFactory implements IAdapterFactory
{
	private static Class[] PROPERTIES= new Class[] {
		IResourcePart.class
	};
		
	public Class[] getAdapterList() {
		return PROPERTIES;
	}
	
	public Object getAdapter(final Object adaptableObject, Class adapterType) {
		if (adapterType == IResourcePart.class) {
			// assume adaptableObject is an instance of IResource - see extension
			return new IResourcePart() {
				public IResource getResource() {
					return (IResource) adaptableObject;
				}
			};
		}
		return null;
	}
}
