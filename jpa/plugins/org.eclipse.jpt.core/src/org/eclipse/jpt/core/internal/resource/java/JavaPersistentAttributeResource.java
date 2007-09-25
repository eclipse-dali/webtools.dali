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
	boolean isForField();
	
	boolean isForProperty();
	
	/**
	 * Return true if this attribute has any mapping or non-mapping annotations
	 * (of course only persistence related annotations)
	 * @return
	 */
	boolean hasAnyAnnotation();
}
