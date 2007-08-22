/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.mappings.IColumn;

public class ColumnContext extends BaseContext
{
	private IColumn column;
	
	public ColumnContext(IContext parentContext, IColumn column) {
		super(parentContext);
		this.column = column;
	}
	
	@Override
	protected void initialize() {}
	
	public IColumn getColumn() {
		return this.column;
	}
	
	public void refreshDefaults(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		this.column.refreshDefaults(defaultsContext);
	}
}
