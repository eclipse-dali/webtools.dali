/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class TableContext extends BaseContext
{
	private ITable table;
	
	public TableContext(IContext parentContext, ITable table) {
		super(parentContext);
		this.table = table;
	}
	
	@Override
	protected void initialize() {}
	
	public ITable getTable() {
		return this.table;
	}
	
	public void refreshDefaults(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		this.table.refreshDefaults(defaultsContext);
	}
	
	public DefaultsContext wrapDefaultsContext(DefaultsContext defaultsContext) {
		return new DefaultsContextWrapper(defaultsContext) {
			public Object getDefault(String key) {
				if (key.equals(BaseJpaPlatform.DEFAULT_COLUMN_TABLE_KEY)) {
					return getTable().getName();
				}
				return super.getDefault(key);
			}
		};
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		boolean doContinue = table.isConnected();
		String schema = table.getSchema();
		
		if (doContinue && ! table.hasResolvedSchema()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.TABLE_UNRESOLVED_SCHEMA,
						new String[] {schema, table.getName()}, 
						table, table.schemaTextRange())
				);
			doContinue = false;
		}
		
		if (doContinue && ! table.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.TABLE_UNRESOLVED_NAME,
						new String[] {table.getName()}, 
						table, table.nameTextRange())
				);
		}
	}
}
