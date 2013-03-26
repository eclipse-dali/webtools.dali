/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class AssociationOverrideJoinColumnValidator
	extends JoinColumnValidator
{
	final AssociationOverride override;


	public AssociationOverrideJoinColumnValidator(
				AssociationOverride override,
				JoinColumn column,
				JoinColumn.ParentAdapter joinColumnParentAdapter,
				TableDescriptionProvider provider) {
		super(column, joinColumnParentAdapter, provider);
		this.override = override;
	}

	public AssociationOverrideJoinColumnValidator(
				PersistentAttribute persistentAttribute,
				AssociationOverride override,
				JoinColumn column,
				JoinColumn.ParentAdapter joinColumnParentAdapter,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, joinColumnParentAdapter, provider);
		this.override = override;
	}

	@Override
	protected JpaValidator buildTableValidator() {
		return new TableValidator();
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnresolvedNameMessage() :
				super.buildUnresolvedNameMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedNameMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getNameValidationTextRange(),
				JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME,
				this.override.getName(),
				this.column.getName(),
				this.column.getDbTable().getName()
			);
	}

	@Override
	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.persistentAttribute.getValidationTextRange(),
				this.getVirtualAttributeUnresolvedNameMessage(),
				this.persistentAttribute.getName(),
				this.override.getName(),
				this.column.getName(),
				this.column.getDbTable().getName()
			);
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected IMessage buildUnresolvedReferencedColumnNameMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnresolvedReferencedColumnNameMessage() :
				super.buildUnresolvedReferencedColumnNameMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedReferencedColumnNameMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getReferencedColumnNameTextRange(),
				JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				this.override.getName(),
				this.column.getReferencedColumnName(),
				this.column.getReferencedColumnDbTable().getName()
			);
	}

	@Override
	protected IMessage buildVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.persistentAttribute.getValidationTextRange(),
				this.getVirtualAttributeUnresolvedReferencedColumnNameMessage(),
				this.persistentAttribute.getName(),
				this.override.getName(),
				this.column.getReferencedColumnName(),
				this.column.getReferencedColumnDbTable().getName()
			);
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected IMessage buildUnspecifiedNameMultipleJoinColumnsMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnspecifiedNameMultipleJoinColumnsMessage() :
				super.buildUnspecifiedNameMultipleJoinColumnsMessage();
	}

	protected IMessage buildVirtualOverrideUnspecifiedNameMultipleJoinColumnsMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getNameValidationTextRange(),
				JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				this.override.getName()
			);
	}

	@Override
	protected IMessage buildVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.persistentAttribute.getValidationTextRange(),
				this.getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage(),
				this.persistentAttribute.getName(),
				this.override.getName()
			);
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() :
				super.buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();
	}

	protected IMessage buildVirtualOverrideUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getReferencedColumnNameTextRange(),
				JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				this.override.getName()
			);
	}

	@Override
	protected IMessage buildVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.persistentAttribute.getValidationTextRange(),
				this.getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(),
				this.persistentAttribute.getName(),
				this.override.getName()
			);
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}


	// ********** table validator **********

	protected class TableValidator
		extends JoinColumnValidator.TableValidator
	{
		protected TableValidator() {
			super();
		}

		@Override
		protected IMessage buildTableNotValidMessage() {
			return AssociationOverrideJoinColumnValidator.this.override.isVirtual() ?
					this.buildVirtualOverrideColumnTableNotValidMessage() :
					super.buildTableNotValidMessage();
		}

		protected IMessage buildVirtualOverrideColumnTableNotValidMessage() {
			return ValidationMessageTools.buildValidationMessage(
					this.getColumn().getResource(),
					this.getColumn().getTableNameValidationTextRange(),
					this.getVirtualOverrideColumnTableNotValidMessage(),
					AssociationOverrideJoinColumnValidator.this.override.getName(),
					this.getColumn().getTableName(),
					this.getColumn().getName(),
					this.getColumnTableDescriptionMessage()
				);
		}

		protected ValidationMessage getVirtualOverrideColumnTableNotValidMessage() {
			return JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected IMessage buildVirtualAttributeTableNotValidMessage() {
			return ValidationMessageTools.buildValidationMessage(
					this.getColumn().getResource(),
					AssociationOverrideJoinColumnValidator.this.persistentAttribute.getValidationTextRange(),
					this.getVirtualAttributeColumnTableNotValidMessage(),
					AssociationOverrideJoinColumnValidator.this.persistentAttribute.getName(),
					AssociationOverrideJoinColumnValidator.this.override.getName(),
					this.getColumn().getTableName(),
					this.getColumn().getName(),
					this.getColumnTableDescriptionMessage()
				);
		}

		@Override
		protected ValidationMessage getVirtualAttributeColumnTableNotValidMessage() {
			return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID;
		}
	}
}
