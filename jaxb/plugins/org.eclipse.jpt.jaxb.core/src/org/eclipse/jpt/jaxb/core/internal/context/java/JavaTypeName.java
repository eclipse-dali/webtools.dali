/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.TypeDeclarationTools;
import org.eclipse.jpt.jaxb.core.context.TypeName;

public class JavaTypeName
		implements TypeName {
	
	protected String packageName;
	protected String qualifiedName;
	
	public JavaTypeName(JavaResourceAbstractType resourceType) {
		this.packageName = buildPackageName(resourceType);
		this.qualifiedName = buildQualifiedName(resourceType);
	}
	
	
	protected String buildPackageName(JavaResourceAbstractType resourceType) {
		return resourceType.getTypeBinding().getPackageName();
	}
	
	protected String buildQualifiedName(JavaResourceAbstractType resourceType) {
		return resourceType.getTypeBinding().getQualifiedName();
	}
	
	public String getPackageName() {
		return this.packageName;
	}
	
	public String getSimpleName() {
		return TypeDeclarationTools.simpleName(this.qualifiedName);
	}
	
	public String getTypeQualifiedName() {
		return (this.packageName.length() == 0) ? this.qualifiedName : this.qualifiedName.substring(packageName.length() + 1);
	}
	
	public String getFullyQualifiedName() {
		return this.qualifiedName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return ObjectTools.equals(this.packageName, ((JavaTypeName) obj).packageName)
				&& ObjectTools.equals(this.qualifiedName, ((JavaTypeName) obj).qualifiedName);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ObjectTools.hashCode(this.packageName);
		result = prime * result + ObjectTools.hashCode(this.qualifiedName);
		return result;
	}
}