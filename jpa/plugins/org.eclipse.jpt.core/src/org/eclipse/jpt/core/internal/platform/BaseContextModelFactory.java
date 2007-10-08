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
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.jpt.core.internal.IContextModel;
import org.eclipse.jpt.core.internal.IContextModelFactory;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.context.BaseContextModel;

public class BaseContextModelFactory implements IContextModelFactory
{
	/*
	 * Singleton
	 */
	private static BaseContextModelFactory INSTANCE;
	
	
	/**
	 * Return the singleton
	 */
	public static BaseContextModelFactory instance() {
		if (INSTANCE == null) {
			INSTANCE = new BaseContextModelFactory();
		}
		return INSTANCE;
	}
	
	
	/*
	 * Restrict access
	 */
	private BaseContextModelFactory() {
		super();
	}
	
	
	public IContextModel buildContextModel(IJpaProject jpaProject) {
		return new BaseContextModel(jpaProject);
	}
}
