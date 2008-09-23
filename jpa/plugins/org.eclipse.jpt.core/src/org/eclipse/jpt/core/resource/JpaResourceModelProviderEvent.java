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
package org.eclipse.jpt.core.resource;

public class JpaResourceModelProviderEvent
{
	public final static int RESOURCE_LOADED = 0x1;
	
	public final static int RESOURCE_UNLOADED = 0x2;
	
	
	private JpaResourceModelProvider modelProvider;
	
	private int type;
	
	
	public JpaResourceModelProviderEvent(JpaResourceModelProvider modelProvider, int eventType) {
		super();
		this.modelProvider = modelProvider;
		this.type = eventType;
	}
	
	
	public JpaResourceModelProvider getModelProvider() {
		return modelProvider;
	}
	
	public int getType() {
		return type;
	}
}
