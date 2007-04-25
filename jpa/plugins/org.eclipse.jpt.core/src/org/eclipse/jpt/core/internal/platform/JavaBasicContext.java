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
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class JavaBasicContext extends JavaAttributeContext
{
	private ColumnContext columnContext;
	
	public JavaBasicContext(IContext parentContext, JavaBasic javaBasic) {
		super(parentContext, javaBasic);
		this.columnContext = new ColumnContext(this, javaBasic.getColumn());
	}
	
	protected JavaBasic getBasic() {
		return (JavaBasic) attributeMapping;
	}
	
	@Override
	public void refreshDefaultsInternal(DefaultsContext defaultsContext) {
		super.refreshDefaultsInternal(defaultsContext);
		this.columnContext.refreshDefaults(defaultsContext);
	}
	
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		addColumnMessages(messages);
	}
	
	protected void addColumnMessages(List<IMessage> messages) {
		JavaBasic basic = getBasic();
		ITypeMapping typeMapping = basic.typeMapping();
		IColumn column = basic.getColumn();
		String table = column.getTable();
		boolean doContinue = entityOwned() && column.isConnected();
		
		if (doContinue && typeMapping.tableNameIsInvalid(table)) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {table, column.getName()}, 
						column, column.getTableTextRange())
				);
			doContinue = false;
		}
		
		if (doContinue && ! column.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {column.getName()}, 
						column, column.getNameTextRange())
				);
		}
	}
}
