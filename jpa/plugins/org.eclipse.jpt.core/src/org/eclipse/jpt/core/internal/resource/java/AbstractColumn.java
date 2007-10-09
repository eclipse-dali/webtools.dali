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


public interface AbstractColumn extends NamedColumn
{

	Boolean isUnique();
	
	void setUnique(Boolean unique);
	
	Boolean isNullable();
	
	void setNullable(Boolean nullable);
	
	Boolean isInsertable();
	
	void setInsertable(Boolean insertable);
	
	Boolean isUpdatable();
	
	void setUpdatable(Boolean updatable);
	
	String getTable();
	
	void setTable(String table);

}
