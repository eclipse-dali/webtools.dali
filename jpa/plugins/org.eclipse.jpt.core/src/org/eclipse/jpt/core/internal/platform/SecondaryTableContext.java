/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class SecondaryTableContext extends BaseContext
{
	private ISecondaryTable secondaryTable;
	
	private Collection<PrimaryKeyJoinColumnContext> pkJoinColumnContexts;

	public SecondaryTableContext(IContext parentContext, ISecondaryTable secondaryTable) {
		super(parentContext);
		this.secondaryTable = secondaryTable;
		this.pkJoinColumnContexts = buildPkJoinColumnContexts();
	}
	
	protected Collection<PrimaryKeyJoinColumnContext> buildPkJoinColumnContexts() {
		Collection<PrimaryKeyJoinColumnContext> contexts = new ArrayList<PrimaryKeyJoinColumnContext>();
		for (IPrimaryKeyJoinColumn pkJoinColumn : getSecondaryTable().getPrimaryKeyJoinColumns()) {
			contexts.add(new PrimaryKeyJoinColumnContext(this, pkJoinColumn));
		}
		
		return contexts;
	}

	@Override
	protected void initialize() {}
	
	public ISecondaryTable getSecondaryTable() {
		return this.secondaryTable;
	}
	
	public void refreshDefaults(DefaultsContext defaultsContext) {
		this.secondaryTable.refreshDefaults(defaultsContext);
		for (PrimaryKeyJoinColumnContext context : this.pkJoinColumnContexts) {
			context.refreshDefaults(defaultsContext);
		}
	}
	
	public DefaultsContext wrapDefaultsContext(final DefaultsContext defaultsContext) {
		return new DefaultsContext() {
			public Object getDefault(String key) {
//				if (key.equals(BaseJpaPlatform.DEFAULT_COLUMN_TABLE_KEY)) {
//					return getTable().getName();
//				}
				return defaultsContext.getDefault(key);
			}
			
			public IPersistentType persistentType(String fullyQualifiedTypeName) {
				return defaultsContext.persistentType(fullyQualifiedTypeName);
			}
		};
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		addTableMessages(messages);
		for (PrimaryKeyJoinColumnContext context : this.pkJoinColumnContexts) {
			context.addToMessages(messages);
		}
	}
	
	protected void addTableMessages(List<IMessage> messages) {
		boolean doContinue = secondaryTable.isConnected();
		String schema = secondaryTable.getSchema();
		
		if (doContinue && ! secondaryTable.hasResolvedSchema()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_SCHEMA,
						new String[] {schema, secondaryTable.getName()}, 
						secondaryTable, secondaryTable.schemaTextRange())
				);
			doContinue = false;
		}
		
		if (doContinue && ! secondaryTable.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_NAME,
						new String[] {secondaryTable.getName()}, 
						secondaryTable, secondaryTable.nameTextRange())
				);
		}
	}

}
