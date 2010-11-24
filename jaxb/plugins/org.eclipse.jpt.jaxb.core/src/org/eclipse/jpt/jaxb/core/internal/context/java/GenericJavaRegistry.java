/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;


public class GenericJavaRegistry
		extends AbstractJavaType
		implements JaxbRegistry {
	
	public GenericJavaRegistry(JaxbContextRoot parent, JavaResourceType resourceType) {
		super(parent, resourceType);
	}
	
	
	// ********** JaxbType impl **********
	
	public Kind getKind() {
		return Kind.REGISTRY;
	}
	
	
	public void synchronizeWithResourceModel() {
		// TODO Auto-generated method stub
		
	}
	
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
