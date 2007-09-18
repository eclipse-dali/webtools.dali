/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class AssociationOverrideContext extends BaseContext
{
	IAssociationOverride associationOverride;
	
	private Collection<JoinColumnContext> joinColumnContexts;
	
	public AssociationOverrideContext(IContext parentContext, IAssociationOverride associationOverride) {
		super(parentContext);
		this.associationOverride = associationOverride;
		this.joinColumnContexts = buildJoinColumnContexts();
	}
	
	@Override
	protected void initialize() {	
	}
	
	protected Collection<JoinColumnContext> buildJoinColumnContexts() {
		Collection<JoinColumnContext> contexts = new ArrayList<JoinColumnContext>();
		for (IJoinColumn joinColumn : this.associationOverride.getJoinColumns() ) {
			contexts.add(new JoinColumnContext(this, joinColumn));
		}
		return contexts;
	}
	
	@Override
	public void refreshDefaults(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		super.refreshDefaults(defaultsContext, monitor);
		for (JoinColumnContext context : this.joinColumnContexts) {
			context.refreshDefaults(defaultsContext, monitor);
		}
	}

	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		addJoinColumnMessages(messages);
	}
	
	protected void addJoinColumnMessages(List<IMessage> messages) {
		ITypeMapping typeMapping = associationOverride.typeMapping();
		
		for (IJoinColumn joinColumn : associationOverride.getJoinColumns()) {
			String table = joinColumn.getTable();
			boolean doContinue = joinColumn.isConnected();
			
			if (doContinue && typeMapping.tableNameIsInvalid(table)) {
				if (associationOverride.isVirtual()) {
					messages.add(
						JpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							IJpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_TABLE,
							new String[] {associationOverride.getName(), table, joinColumn.getName()},
							joinColumn, joinColumn.tableTextRange())
					);
				}
				else {
					messages.add(
						JpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							IJpaValidationMessages.JOIN_COLUMN_UNRESOLVED_TABLE,
							new String[] {table, joinColumn.getName()}, 
							joinColumn, joinColumn.tableTextRange())
					);
				}
				doContinue = false;
			}
			
			if (doContinue && ! joinColumn.isResolved()) {
				if (associationOverride.isVirtual()) {
					messages.add(
						JpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							IJpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME,
							new String[] {associationOverride.getName(), joinColumn.getName()}, 
							joinColumn, joinColumn.nameTextRange())
					);
				}
				else {
					messages.add(
						JpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							IJpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
							new String[] {joinColumn.getName()}, 
							joinColumn, joinColumn.nameTextRange())
					);
				}
			}
			
			if (doContinue && ! joinColumn.isReferencedColumnResolved()) {
				if (associationOverride.isVirtual()) {
					messages.add(
						JpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							IJpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
							new String[] {associationOverride.getName(), joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
							joinColumn, joinColumn.referencedColumnNameTextRange())
					);
				}
				else {
					messages.add(
						JpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							IJpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
							new String[] {joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
							joinColumn, joinColumn.referencedColumnNameTextRange())
					);
				}
			}
		}
	}
}