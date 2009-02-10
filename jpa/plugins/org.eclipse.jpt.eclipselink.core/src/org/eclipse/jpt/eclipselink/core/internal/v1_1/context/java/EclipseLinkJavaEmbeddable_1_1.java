/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1.context.java;

import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaEmbeddableImpl;

public class EclipseLinkJavaEmbeddable_1_1 
	extends EclipseLinkJavaEmbeddableImpl
{
	public EclipseLinkJavaEmbeddable_1_1(JavaPersistentType parent) {
		super(parent);
	}
	
	
	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		// EclipseLink 1.1 allows all mapping types within an embeddable
		return true;
	}
}
