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
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class PrimaryKeyJoinColumnContext extends AbstractJoinColumnContext<IPrimaryKeyJoinColumn>
{
	
	public PrimaryKeyJoinColumnContext(IContext parentContext, IPrimaryKeyJoinColumn column) {
		super(parentContext, column);
	}

	//TODO This default is different for oneToOne mappings, we don't yet support pkJoinColumns there
	protected String buildDefaultReferencedColumnName() {
		return this.buildDefaultName();
	}

	protected String buildDefaultName() {
		IEntity entity = (IEntity) getColumn().getOwner().getTypeMapping();
		
		if (entity.getPrimaryKeyJoinColumns().size() != 1) {
			return null;
		}
		String pkColumnName = entity.parentEntity().primaryKeyColumnName();
		return pkColumnName;
	}

	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
	
		boolean doContinue = column.isConnected();
		if (doContinue && ! column.isResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME,
						new String[] {column.getName()}, 
						column, column.nameTextRange())
			);
		}
		
		if (doContinue && ! column.isReferencedColumnResolved()) {
			messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
						new String[] {column.getReferencedColumnName(), column.getName()}, 
						column, column.referencedColumnNameTextRange())
			);
		}
	}
}
