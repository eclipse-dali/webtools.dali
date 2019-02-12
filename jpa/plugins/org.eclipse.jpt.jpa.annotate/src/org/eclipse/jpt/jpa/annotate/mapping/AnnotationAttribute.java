/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.annotate.mapping;

public class AnnotationAttribute
{
	public String attrName;
	public String tagName;
	public String attrValue;
	
	public AnnotationAttribute(String name, String value)
	{
		this(name, name, value);
	}
	
	public AnnotationAttribute(String name, String tagName, String value)
	{
		this.attrName = name;
		this.tagName = tagName;
		this.attrValue = value;
	}	
	
	// copy constructor
	public AnnotationAttribute(AnnotationAttribute another)
	{
		attrName = another.attrName;
		tagName = another.tagName;
		attrValue = another.attrValue;
	}
	
}
