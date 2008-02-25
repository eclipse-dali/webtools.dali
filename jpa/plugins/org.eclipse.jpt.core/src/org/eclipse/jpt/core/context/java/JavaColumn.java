/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jpt.core.context.AbstractColumn;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;

public interface JavaColumn extends JavaNamedColumn, Column
{
	void initializeFromResource(ColumnAnnotation columnResource);
	
	void update(ColumnAnnotation columnResource);
	
	boolean isConnected();
	
	Owner owner();
	/**
	 * interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides)
	 */
	interface Owner extends AbstractColumn.Owner
	{
		ColumnAnnotation columnResource();
	}

}