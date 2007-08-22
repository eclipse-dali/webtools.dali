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
import org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn;

public abstract class AbstractJoinColumnContext<E extends IAbstractJoinColumn> extends BaseContext
{
	protected E column;
	
	public AbstractJoinColumnContext(IContext parentContext, E column) {
		super(parentContext);
		this.column = column;
	}
	
	@Override
	protected void initialize() {}
	
	public E getColumn() {
		return this.column;
	}
	
	@Override
	public void refreshDefaults(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		super.refreshDefaults(defaultsContext, monitor);
		this.column.refreshDefaults(wrapDefaultsContext(defaultsContext));
	}
	
	public DefaultsContext wrapDefaultsContext(DefaultsContext defaultsContext) {
		return new DefaultsContextWrapper(defaultsContext) {
			public Object getDefault(String key) {
				if (key.equals(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_NAME_KEY)) {
					return buildDefaultName();
				}
				if (key.equals(BaseJpaPlatform.DEFAULT_JOIN_COLUMN_REFERENCED_COLUMN_NAME_KEY)) {
					return buildDefaultReferencedColumnName();
				}
				return super.getDefault(key);
			}
		};
	}
	
	protected abstract String buildDefaultName();
	
	protected abstract String buildDefaultReferencedColumnName();

}
