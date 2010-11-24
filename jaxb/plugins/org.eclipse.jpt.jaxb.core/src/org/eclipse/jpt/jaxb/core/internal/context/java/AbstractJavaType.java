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
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;


public abstract class AbstractJavaType
		extends AbstractJaxbContextNode
		implements JaxbType {
	
	protected final JavaResourceType resourceType;

	
	protected AbstractJavaType(JaxbContextRoot parent, JavaResourceType resourceType) {
		super(parent);
		this.resourceType = resourceType;
		
	}
	
	
	// *********** JaxbType impl ***********
	
	public JavaResourceType getJavaResourceType() {
		return this.resourceType;
	}
	
	public String getFullyQualifiedName() {
		return this.resourceType.getQualifiedName();
	}
	
	public String getPackageName() {
		return this.resourceType.getPackageName();
	}
	
	public String getTypeQualifiedName() {
		String packageName = getPackageName();
		return (packageName.length() == 0) ? getFullyQualifiedName() : getFullyQualifiedName().substring(packageName.length() + 1);
	}
	
	public String getSimpleName() {
		return this.resourceType.getName();
	}
}
