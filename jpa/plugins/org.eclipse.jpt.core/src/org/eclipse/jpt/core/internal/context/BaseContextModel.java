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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IContextModel;
import org.eclipse.jpt.core.internal.IJpaProject;

public class BaseContextModel implements IContextModel
{
	protected IJpaProject jpaProject;
	
	protected BaseJpaContent jpaContent;
	
	
	public BaseContextModel(IJpaProject jpaProject) {
		this.jpaProject = jpaProject;
		jpaContent = new BaseJpaContent();
	}
	
	
	public void update(IJpaProject jpaProject, IProgressMonitor monitor) {
		
	}
}
