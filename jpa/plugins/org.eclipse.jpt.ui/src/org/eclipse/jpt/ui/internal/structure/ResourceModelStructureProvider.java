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

import org.eclipse.jpt.core.ResourceModel;

public abstract class ResourceModelStructureProvider
	implements JpaStructureProvider
{
	protected ResourceModel resourceModel;
	
	
	public ResourceModelStructureProvider(ResourceModel resourceModel) {
		this.resourceModel = resourceModel;
	}
	
	public Object getInput() {
		return resourceModel;
	}
	
	public void dispose() {
		// TODO Auto-generated method stub	
	}
}
