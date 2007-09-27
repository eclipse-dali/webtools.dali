/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class JavaVersionContext extends JavaAttributeContext
{
	private ColumnContext columnContext;
	
	public JavaVersionContext(IContext parentContext, JavaVersion javaVersion) {
		super(parentContext, javaVersion);
		this.columnContext = new ColumnContext(this, javaVersion.getColumn());
	}
	
	protected JavaVersion version() {
		return (JavaVersion) attributeMapping;
	}
	
	@Override
	protected void refreshDefaultsInternal(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		super.refreshDefaultsInternal(defaultsContext, monitor);
		this.columnContext.refreshDefaults(defaultsContext, monitor);
	}
	
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		addColumnMessages(messages);
	}
	
	protected void addColumnMessages(List<IMessage> messages) {
		JavaVersion version = version();
		ITypeMapping typeMapping = version.typeMapping();
		IColumn column = version.getColumn();
		String table = column.getTable();
		boolean doContinue = entityOwned() && column.isConnected();
		
		if (doContinue && typeMapping.tableNameIsInvalid(table)) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {table, column.getName()}, 
						column, column.tableTextRange())
				);
			doContinue = false;
		}
		
		if (doContinue && ! column.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {column.getName()}, 
						column, column.nameTextRange())
				);
		}
	}
}
