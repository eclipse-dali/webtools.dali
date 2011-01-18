/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context;

import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.BaseColumnTableValidator.TableDescriptionProvider;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class AssociationOverrideJoinColumnValidator
	extends JoinColumnValidator
{
	final AssociationOverride override;


	public AssociationOverrideJoinColumnValidator(
				AssociationOverride override,
				JoinColumn column,
				JoinColumn.Owner joinColumnOwner,
				JoinColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
		super(column, joinColumnOwner, textRangeResolver, provider);
		this.override = override;
	}

	public AssociationOverrideJoinColumnValidator(
				PersistentAttribute persistentAttribute,
				AssociationOverride override,
				JoinColumn column,
				JoinColumn.Owner joinColumnOwner,
				JoinColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, joinColumnOwner, textRangeResolver, provider);
		this.override = override;
	}

	@Override
	protected TableValidator buildTableValidator() {
		return new AssociationOverrideJoinColumnTableValidator(this.persistentAttribute, this.column, this.textRangeResolver, this.tableDescriptionProvider);
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnresolvedNameMessage() :
				super.buildUnresolvedNameMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {
					this.override.getName(),
					this.column.getName(),
					this.column.getDbTable().getName()
				},
				this.column,
				this.textRangeResolver.getNameTextRange()
			);
	}

	@Override
	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualAttributeUnresolvedNameMessage(),
				new String[] {
					this.getPersistentAttributeName(),
					this.override.getName(),
					this.column.getName(),
					this.column.getDbTable().getName()
				},
				this.column,
				this.textRangeResolver.getNameTextRange()
			);
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	public IMessage buildUnresolvedReferencedColumnNameMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnresolvedReferencedColumnNameMessage() :
				super.buildUnresolvedReferencedColumnNameMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedReferencedColumnNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				new String[] {
					this.override.getName(),
					this.column.getReferencedColumnName(),
					this.column.getReferencedColumnDbTable().getName()
				},
				this.column,
				this.textRangeResolver.getReferencedColumnNameTextRange()
			);
	}

	@Override
	protected IMessage buildVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualAttributeUnresolvedReferencedColumnNameMessage(),
				new String[] {
					this.getPersistentAttributeName(),
					this.override.getName(),
					this.column.getReferencedColumnName(),
					this.column.getReferencedColumnDbTable().getName()
				},
				this.column,
				this.textRangeResolver.getReferencedColumnNameTextRange()
			);
	}

	@Override
	protected String getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected IMessage buildUnspecifiedNameMultipleJoinColumnsMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnspecifiedNameMultipleJoinColumnsMessage() :
				super.buildUnspecifiedNameMultipleJoinColumnsMessage();
	}

	protected IMessage buildVirtualOverrideUnspecifiedNameMultipleJoinColumnsMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[] {this.override.getName()},
				this.column,
				this.textRangeResolver.getNameTextRange()
			);
	}

	@Override
	protected IMessage buildVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage(),
				new String[] {
					this.getPersistentAttributeName(),
					this.override.getName()
				},
				this.column,
				this.textRangeResolver.getNameTextRange()
			);
	}

	@Override
	protected String getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() :
				super.buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();
	}

	protected IMessage buildVirtualOverrideUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[] {this.override.getName()},
				this.column,
				this.textRangeResolver.getReferencedColumnNameTextRange()
			);
	}

	@Override
	protected IMessage buildVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(),
				new String[] {
					this.getPersistentAttributeName(),
					this.override.getName()
				},
				this.column,
				this.textRangeResolver.getReferencedColumnNameTextRange()
			);
	}

	@Override
	protected String getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}


	public class AssociationOverrideJoinColumnTableValidator
		extends JoinColumnTableValidator
	{
		protected AssociationOverrideJoinColumnTableValidator(
				PersistentAttribute persistentAttribute,
				JoinColumn column,
				JoinColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
			super(persistentAttribute, column, textRangeResolver, provider);
		}

		@Override
		public IMessage buildTableNotValidMessage() {
			return AssociationOverrideJoinColumnValidator.this.override.isVirtual() ?
					this.buildVirtualOverrideColumnTableNotValidMessage() :
					super.buildTableNotValidMessage();
		}

		protected IMessage buildVirtualOverrideColumnTableNotValidMessage() {
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					this.getVirtualOverrideColumnTableNotValidMessage(),
					new String[] {
						AssociationOverrideJoinColumnValidator.this.override.getName(),
						this.column.getTable(),
						this.column.getName(),
						this.getColumnTableDescriptionMessage()
					},
					this.column,
					this.textRangeResolver.getTableTextRange()
				);
		}

		protected String getVirtualOverrideColumnTableNotValidMessage() {
			return JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected IMessage buildVirtualAttributeTableNotValidMessage() {
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					this.getVirtualAttributeColumnTableNotValidMessage(),
					new String[] {
						this.getPersistentAttributeName(),
						AssociationOverrideJoinColumnValidator.this.override.getName(),
						this.column.getTable(),
						this.column.getName(),
						this.getColumnTableDescriptionMessage()
					},
					this.column,
					this.textRangeResolver.getTableTextRange()
				);
		}

		@Override
		protected String getVirtualAttributeColumnTableNotValidMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID;
		}
	}
}
