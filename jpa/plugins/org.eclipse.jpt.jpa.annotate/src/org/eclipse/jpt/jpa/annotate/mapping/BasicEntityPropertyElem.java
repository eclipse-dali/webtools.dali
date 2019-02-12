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

import org.eclipse.jpt.jpa.db.Table;


public class BasicEntityPropertyElem extends EntityPropertyElem
{
	private String temporalType;
	
	public BasicEntityPropertyElem(String fqClassName, Table table)
	{
		super(fqClassName, table);
	}
	
	public BasicEntityPropertyElem(String fqClassName, Table table, String propName, String propType)
	{
		super(fqClassName, table, propName, propType);
	}
	
	public BasicEntityPropertyElem(BasicEntityPropertyElem another)
	{
		super(another);
		temporalType = another.temporalType;
	}
	
	public boolean isSetTemporalType()
	{
		return temporalType != null;
	}
	
	public String getTemporalType()
	{
		return temporalType;
	}
	
	public void setTemporalType(String temporalType)
	{
		this.temporalType = temporalType;
	}
	
	public AnnotationAttribute getTemporalAnnotationAttribute()
	{
		AnnotationAttribute attr = null;
		if (temporalType != null)
		{
			attr = new AnnotationAttribute(
					AnnotationAttributeNames.VALUE, AnnotationAttrConverter.TEMPORAL, temporalType); 
		}
		return attr;
	}
}
