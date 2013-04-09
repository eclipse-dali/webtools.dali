/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.annotate.mapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AnnotationAttributes
{
	private Map<String, AnnotationAttribute> attrs;
	
	public AnnotationAttributes()
	{
		attrs = new HashMap<String, AnnotationAttribute>(6);
	}
	
	// copy constructor
	public AnnotationAttributes(AnnotationAttributes another)
	{
		this();
		Iterator<String> it = another.attrs.keySet().iterator();
		while (it.hasNext())
		{
			String key = it.next();
			AnnotationAttribute attr = another.attrs.get(key);
			attrs.put(key, new AnnotationAttribute(attr));
		}
	}
	
	public void setAnnotationAttribute(AnnotationAttribute attr)
	{
		attrs.put(attr.attrName, attr);
	}
	
	public AnnotationAttribute getAnnotationAttribute(String name)
	{
		return attrs.get(name);
	}
	
	public void removeAnnotationAttribute(String name)
	{
		attrs.remove(name);
	}
	
	public AnnotationAttribute[] getAnnotationAttributes(String[] attrNames)
	{
		AnnotationAttribute[] attrs = new AnnotationAttribute[attrNames.length];
		int index = 0;
		for (String attrName : attrNames)
		{
			attrs[index++] = this.attrs.get(attrName);
		}
		return attrs;
	}
	
	public boolean isEmpty()
	{
		return attrs.isEmpty();
	}
	
	public void dispose()
	{
		attrs.clear();
	}
}
