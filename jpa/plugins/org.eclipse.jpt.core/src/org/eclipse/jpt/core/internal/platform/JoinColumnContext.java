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
import org.eclipse.jpt.core.internal.content.orm.XmlRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class JoinColumnContext extends AbstractJoinColumnContext<IJoinColumn>
{
	public JoinColumnContext(IContext parentContext, IJoinColumn column) {
		super(parentContext, column);
	}
	
	/**
	 * return the join column's default name;
	 * which is typically &lt;attribute name&gt;_&lt;referenced column name&gt;
	 * but, if we don't have an attribute name (e.g. in a unidirectional
	 * OneToMany or ManyToMany) is
	 * &lt;target entity name&gt;_&lt;referenced column name&gt;
	 */
	// <attribute name>_<referenced column name>
	//     or
	// <target entity name>_<referenced column name>
	protected String buildDefaultName() {
		if (getColumn().getOwner().joinColumns().size() != 1) {
			return null;
		}
		String prefix = getColumn().getOwner().attributeName();
		if (prefix == null) {
			prefix = targetEntityName();
		}
		if (prefix == null) {
			return null;
		}
		// TODO not sure which of these is correct...
		// (the spec implies that the referenced column is always the
		// primary key column of the target entity)
		// String targetColumn = this.targetPrimaryKeyColumnName();
		String targetColumn = getColumn().getReferencedColumnName();
		if (targetColumn == null) {
			return null;
		}
		return prefix + "_" + targetColumn;
	}
	
	/**
	 * return the name of the target entity
	 */
	private String targetEntityName() {
		IEntity targetEntity = getColumn().getOwner().targetEntity();
		return (targetEntity == null) ? null : targetEntity.getName();
	}

	protected String buildDefaultReferencedColumnName() {
		if (getColumn().getOwner().joinColumns().size() != 1) {
			return null;
		}
		return this.targetPrimaryKeyColumnName();
	}
	/**
	 * return the name of the single primary key column of the target entity
	 */
	private String targetPrimaryKeyColumnName() {
		IEntity targetEntity = getColumn().getOwner().targetEntity();
		return (targetEntity == null) ? null : targetEntity.primaryKeyColumnName();
	}
	
	/** used internally as a mechanism to short circuit continued message adding */
	private boolean doContinue;
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
	
		//String table = column.getTable();
		XmlRelationshipMapping mapping = 
			(XmlRelationshipMapping) column.getOwner().getRelationshipMapping();
		//XmlTypeMapping typeMapping = 
		//	(XmlTypeMapping) column.getOwner().getTypeMapping();
		doContinue = column.isConnected();
		
//		if (doContinue && typeMapping.tableNameIsInvalid(table)) {
//			if (mapping.isVirtual()) {
//				messages.add(
//					JpaValidationMessages.buildMessage(
//						IMessage.HIGH_SEVERITY,
//						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_TABLE,
//						new String[] {mapping.getPersistentAttribute().getName(), table, column.getName()},
//						column, column.getTableTextRange())
//				);
//			}
//			else {
//				messages.add(
//					JpaValidationMessages.buildMessage(
//						IMessage.HIGH_SEVERITY,
//						IJpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
//						new String[] {table, column.getName()}, 
//						column, column.getTableTextRange())
//				);
//			}
//			doContinue = false;
//		}
//		
		if (doContinue && ! column.isResolved()) {
			if (mapping.isVirtual()) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME,
						new String[] {mapping.getPersistentAttribute().getName(), column.getName()}, 
						column, column.nameTextRange())
				);
			}
			else {
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
}
