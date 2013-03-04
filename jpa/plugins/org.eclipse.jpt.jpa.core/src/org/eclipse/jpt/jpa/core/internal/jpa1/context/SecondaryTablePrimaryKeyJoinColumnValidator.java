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
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class SecondaryTablePrimaryKeyJoinColumnValidator
	extends PrimaryKeyJoinColumnValidator
{
	private final SecondaryTable secondaryTable;

	public SecondaryTablePrimaryKeyJoinColumnValidator(
				SecondaryTable secondaryTable,
				BaseJoinColumn column,
				BaseJoinColumn.ParentAdapter owner) {
		super(column, owner);
		this.secondaryTable = secondaryTable;
	}

	protected boolean secondaryTableIsVirtual() {
		return this.secondaryTable.isVirtual();
	}

	protected String getSecondaryTableName() {
		return this.secondaryTable.getName();
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		return this.secondaryTableIsVirtual() ?
				this.buildVirtualSecondaryTableUnresolvedNameMessage() :
				super.buildUnresolvedNameMessage();
	}

	protected IMessage buildVirtualSecondaryTableUnresolvedNameMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getNameValidationTextRange(),
				JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME,
				this.getSecondaryTableName(),
				this.column.getName(),
				this.column.getDbTable().getName()
			);
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected IMessage buildUnresolvedReferencedColumnNameMessage() {
		return this.secondaryTableIsVirtual() ?
				this.buildVirtualSecondaryTableUnresolvedReferencedColumnNameMessage() :
				super.buildUnresolvedReferencedColumnNameMessage();
	}

	protected IMessage buildVirtualSecondaryTableUnresolvedReferencedColumnNameMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getReferencedColumnNameTextRange(),
				JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				this.getSecondaryTableName(),
				this.column.getReferencedColumnName(),
				this.column.getReferencedColumnDbTable().getName()
			);
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected IMessage buildUnspecifiedNameMultipleJoinColumnsMessage() {
		return this.secondaryTableIsVirtual() ?
				this.buildVirtualSecondaryTableUnspecifiedNameMultipleJoinColumnsMessage() :
				super.buildUnspecifiedNameMultipleJoinColumnsMessage();
	}

	protected IMessage buildVirtualSecondaryTableUnspecifiedNameMultipleJoinColumnsMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getNameValidationTextRange(),
				JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				this.getSecondaryTableName()
			);
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return this.secondaryTableIsVirtual() ?
				this.buildVirtualSecondaryTableUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() :
				super.buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();
	}

	protected IMessage buildVirtualSecondaryTableUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getReferencedColumnNameTextRange(),
				JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				this.getSecondaryTableName()
			);
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		throw new UnsupportedOperationException();
	}
}
