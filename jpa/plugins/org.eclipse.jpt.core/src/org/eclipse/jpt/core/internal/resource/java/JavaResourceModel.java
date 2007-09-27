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
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IResourceModel;

public class JavaResourceModel implements IResourceModel
{
	public JavaResourceModel() {
		super();
	}
	
	public String getResourceType() {
		return JAVA_RESOURCE_TYPE;
	}
	
	public void handleJavaElementChangedEvent(ElementChangedEvent event) {
		// TODO Auto-generated method stub
	}
	
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	public IJpaContentNode getContentNode(int offset) {
		// TODO Auto-generated method stub
		return null;
	}
}
