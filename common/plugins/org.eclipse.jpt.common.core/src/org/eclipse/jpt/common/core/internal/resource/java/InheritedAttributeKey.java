/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java;

public class InheritedAttributeKey {
	
	private String typeName;
	
	private String attributeName;
	
	private int occurrence;
	
	
	public InheritedAttributeKey(String typeName, String attributeName, int occurrence) {
		this.typeName = typeName;
		this.attributeName = attributeName;
		this.occurrence = occurrence;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || ! (obj instanceof InheritedAttributeKey)) {
			return false;
		}
		InheritedAttributeKey other = (InheritedAttributeKey) obj;
		return this.typeName.equals(other.typeName)
				&& this.attributeName.equals(other.attributeName)
				&& this.occurrence == other.occurrence;
	}
	
	@Override
	public int hashCode() {
		return this.typeName.hashCode() 
				* this.attributeName.hashCode() 
				* this.occurrence;
	}
}