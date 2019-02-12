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

public class IdEntityPropertyElement extends EntityPropertyElem
{
	private GeneratedValueAttributes genAttrs;

	public IdEntityPropertyElement(String fqClassName, Table table)
	{
		super(fqClassName, table);
	}
	
	public IdEntityPropertyElement(String fqClassName, Table table, String propName, String propType)
	{
		super(fqClassName, table, propName, propType);
	}
		
	public IdEntityPropertyElement(IdEntityPropertyElement another)
	{
		super(another);
		if (another.genAttrs != null)
			genAttrs = new GeneratedValueAttributes(another.genAttrs);
	}
	
	public GeneratedValueAttributes getGeneratedValueAttrs()
	{
		return genAttrs;
	}
	
	public void setGeneratedValueAttrs(GeneratedValueAttributes genAttrs)
	{
		this.genAttrs = genAttrs;
	}
}
