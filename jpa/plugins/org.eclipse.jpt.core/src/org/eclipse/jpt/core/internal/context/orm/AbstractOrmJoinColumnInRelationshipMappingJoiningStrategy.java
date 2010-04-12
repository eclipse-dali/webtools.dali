/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn.Owner;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy 
	extends AbstractOrmJoinColumnJoiningStrategy
{
	
	protected AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy(
			OrmJoinColumnEnabledRelationshipReference parent,
			XmlJoinColumnsMapping resource) {
		super(parent, resource);
	}
	
	@Override
	protected Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}
	
	@Override
	public OrmJoinColumnEnabledRelationshipReference getRelationshipReference() {
		return (OrmJoinColumnEnabledRelationshipReference) super.getRelationshipReference();
	}

	protected abstract Entity getRelationshipTargetEntity();

	public String getColumnTableNotValidDescription() {
		return JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY;
	}

	public boolean isOverridableAssociation() {
		return true;
	}
		
	public TextRange getValidationTextRange() {
		return this.getRelationshipReference().getValidationTextRange();
	}

	// ********** join column owner adapter **********

	protected class JoinColumnOwner
		implements OrmJoinColumn.Owner 
	{
		protected JoinColumnOwner() {
			super();
		}
		
		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.getTableName();
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public String getAttributeName() {
			return getRelationshipMapping().getName();
		}

		public PersistentAttribute getPersistentAttribute() {
			return getRelationshipMapping().getPersistentAttribute();
		}

		public TypeMapping getTypeMapping() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.getRelationshipSource();
		}

		public Entity getRelationshipTarget() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.tableNameIsInvalid(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.candidateTableNames();
		}
		
		public Table getDbTable(String tableName) {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.getDbTable(tableName);
		}
		
		public Table getReferencedColumnDbTable() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.getReferencedColumnDbTable();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.defaultJoinColumn == joinColumn;
		}

		public int joinColumnsSize() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.joinColumnsSize();
		}
		
		public TextRange getValidationTextRange() {
			return AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy.this.getValidationTextRange();
		}
		
		protected boolean isPersistentAttributeVirtual() {
			return getPersistentAttribute().isVirtual();
		}

		public IMessage buildTableNotValidMessage(BaseColumn column, TextRange textRange) {
			if (this.isPersistentAttributeVirtual()) {
				return this.buildVirtualTableNotValidMessage(column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_TABLE_NOT_VALID,
				new String[] {
					column.getTable(),
					column.getName(),
					JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY}, 
				column, 
				textRange
			);
		}

		protected IMessage buildVirtualTableNotValidMessage(BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_TABLE_NOT_VALID,
				new String[] {
					this.getAttributeName(),
					column.getTable(), column.getName(),
					JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY},
				column, 
				textRange
			);
		}

		public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
			if (this.isPersistentAttributeVirtual()) {
				return this.buildVirtualUnresolvedNameMessage(column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column,
				textRange
			);
		}

		protected IMessage buildVirtualUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {this.getAttributeName(), column.getName(), column.getDbTable().getName()},
				column, 
				textRange
			);
		}

		public IMessage buildUnresolvedReferencedColumnNameMessage(BaseJoinColumn column, TextRange textRange) {
			if (this.isPersistentAttributeVirtual()) {
				return this.buildVirtualUnresolvedReferencedColumnNameMessage(column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				new String[] {column.getReferencedColumnName(), column.getReferencedColumnDbTable().getName()},
				column,
				textRange
			);
		}

		protected IMessage buildVirtualUnresolvedReferencedColumnNameMessage(BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				new String[] {this.getAttributeName(), column.getReferencedColumnName(), column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}

		public IMessage buildUnspecifiedNameMultipleJoinColumnsMessage(BaseJoinColumn column, TextRange textRange) {
			if (this.isPersistentAttributeVirtual()) {
				return this.buildVirtualUnspecifiedNameMultipleJoinColumnsMessage(column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[0],
				column,
				textRange
			);
		}

		protected IMessage buildVirtualUnspecifiedNameMultipleJoinColumnsMessage(BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[] {this.getAttributeName()},
				column, 
				textRange
			);
		}

		public IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(BaseJoinColumn column, TextRange textRange) {
			if (this.isPersistentAttributeVirtual()) {
				return this.buildVirtualUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[0],
				column,
				textRange
			);
		}

		protected IMessage buildVirtualUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[] {this.getAttributeName()},
				column, 
				textRange
			);
		}
	}

}
