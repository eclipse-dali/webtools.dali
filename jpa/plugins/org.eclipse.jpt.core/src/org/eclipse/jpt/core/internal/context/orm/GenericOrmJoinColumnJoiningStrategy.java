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

public class GenericOrmJoinColumnJoiningStrategy 
	extends AbstractOrmJoinColumnJoiningStrategy
{
	
	public GenericOrmJoinColumnJoiningStrategy(
			OrmJoinColumnEnabledRelationshipReference parent,
			XmlJoinColumnsMapping resource) {
		super(parent, resource);
	}
	
	@Override
	protected Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}
	
	public TypeMapping getTypeMapping() {
		return getRelationshipMapping().getTypeMapping();
	}

	public String getTableName() {
		return getTypeMapping().getPrimaryTableName();
	}

	public Table getDbTable(String tableName) {
		return getTypeMapping().getDbTable(tableName);
	}

	public boolean tableNameIsInvalid(String tableName) {
		return getTypeMapping().tableNameIsInvalid(tableName);
	}

	public String getColumnTableNotValidDescription() {
		return JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY;
	}

	public boolean isOverridableAssociation() {
		return true;
	}
	
	@Override
	public OrmJoinColumnEnabledRelationshipReference getRelationshipReference() {
		return (OrmJoinColumnEnabledRelationshipReference) super.getRelationshipReference();
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
			return GenericOrmJoinColumnJoiningStrategy.this.getTableName();
		}
		
		public Entity getTargetEntity() {
			return getRelationshipMapping().getResolvedTargetEntity();
		}
		
		public String getAttributeName() {
			return getRelationshipMapping().getName();
		}
		
		public PersistentAttribute getPersistentAttribute() {
			return getRelationshipMapping().getPersistentAttribute();
		}
		
		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return getTypeMapping().associatedTableNamesIncludingInherited();
		}

		public TypeMapping getTypeMapping() {
			return GenericOrmJoinColumnJoiningStrategy.this.getTypeMapping();
		}
		
		public Table getDbTable(String tableName) {
			return getTypeMapping().getDbTable(tableName);
		}
		
		public Table getReferencedColumnDbTable() {
			Entity targetEntity = getTargetEntity();
			return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return GenericOrmJoinColumnJoiningStrategy.this.defaultJoinColumn == joinColumn;
		}
		
		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public int joinColumnsSize() {
			return GenericOrmJoinColumnJoiningStrategy.this.joinColumnsSize();
		}
		
		public TextRange getValidationTextRange() {
			return GenericOrmJoinColumnJoiningStrategy.this.getValidationTextRange();
		}
		
		protected boolean isPersistentAttributeVirtual() {
			return getPersistentAttribute().isVirtual();
		}

		protected String getPersistentAttributeName() {
			return getPersistentAttribute().getName();
		}

		public IMessage buildTableNotValidMessage(BaseColumn column, TextRange textRange) {
			if (this.isPersistentAttributeVirtual()) {
				return this.buildVirtualTableNotValidMessage(column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_TABLE_NOT_VALID_FOR_THIS_ENTITY,
				new String[] {column.getTable(), column.getName()}, 
				column, 
				textRange
			);
		}

		protected IMessage buildVirtualTableNotValidMessage(BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_TABLE_NOT_VALID_FOR_THIS_ENTITY,
				new String[] {this.getPersistentAttributeName(), column.getTable(), column.getName()},
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
				new String[] {this.getPersistentAttributeName(), column.getName(), column.getDbTable().getName()},
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
				new String[] {this.getPersistentAttributeName(), column.getReferencedColumnName(), column.getReferencedColumnDbTable().getName()},
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
				new String[] {this.getPersistentAttributeName()},
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
				new String[] {this.getPersistentAttributeName()},
				column, 
				textRange
			);
		}
	}

}
