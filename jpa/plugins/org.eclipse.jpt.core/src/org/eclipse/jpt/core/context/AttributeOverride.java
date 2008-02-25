/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;


public interface AttributeOverride extends BaseOverride, Column.Owner
{
	Column getColumn();

	AttributeOverride.Owner owner();
	
	interface Owner extends BaseOverride.Owner
	{
		/**
		 * Return the column mapping with the given attribute name.
		 * Return null if it does not exist.  This column mapping
		 * will be found in the mapped superclass (or embeddable), not in the owning entity
		 */
		ColumnMapping columnMapping(String attributeName);
	}
}