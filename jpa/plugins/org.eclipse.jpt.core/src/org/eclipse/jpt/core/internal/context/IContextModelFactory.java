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
package org.eclipse.jpt.core.internal.context;

import org.eclipse.jpt.core.internal.IJpaProject;


public interface IContextModelFactory
{
	/**
	 * Build a (updated) context model to be associated with the given JPA project.
	 * The context model will be built once, but updated many times.
	 * @see update(IJpaProject, IContextModel, IProgressMonitor)
	 */
	IContextModel buildContextModel(IJpaProject jpaProject);
	
	
}
