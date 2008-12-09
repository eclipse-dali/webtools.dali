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
package org.eclipse.jpt.core;

import org.eclipse.core.resources.IResource;

/**
 * Represents an object that can be described as being part of an {@link IResource}
 */
public interface IResourcePart
{
	/**
	 * Return the resource of which this object is a part
	 */
	IResource getResource();
}
