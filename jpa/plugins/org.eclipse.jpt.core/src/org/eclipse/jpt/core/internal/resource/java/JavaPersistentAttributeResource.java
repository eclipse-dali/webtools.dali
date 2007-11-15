/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;


//TODO how do we handle:
//  @Basic
//  private String foo, bar;

public interface JavaPersistentAttributeResource extends JavaPersistentResource
{
	String getName();
	
	boolean isForField();
	
	boolean isForProperty();
	
	boolean typeIsBasic();
		String TYPE_IS_BASIC_PROPERTY = "typeIsBasicProperty";
	
	String getQualifiedTypeName();
		String QUALFIED_TYPE_NAME_PROPERTY = "qualfiedTypeNameProperty";
	
	/**
	 * Return true if this attribute has any mapping or non-mapping annotations
	 * (of course only persistence related annotations)
	 */
	boolean hasAnyAnnotation();
}
