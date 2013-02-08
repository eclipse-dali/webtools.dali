/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlySecondaryTable;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class SecondaryTablePrimaryKeyJoinColumnValidator
	extends PrimaryKeyJoinColumnValidator
{
	private final ReadOnlySecondaryTable secondaryTable;

	public SecondaryTablePrimaryKeyJoinColumnValidator(
				ReadOnlySecondaryTable secondaryTable,
				ReadOnlyBaseJoinColumn column,
				ReadOnlyBaseJoinColumn.Owner owner) {
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
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {
					this.getSecondaryTableName(),
					this.column.getName(),
					this.column.getDbTable().getName()
				},
				this.column,
				this.column.getNameTextRange()
			);
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected IMessage buildUnresolvedReferencedColumnNameMessage() {
		return this.secondaryTableIsVirtual() ?
				this.buildVirtualSecondaryTableUnresolvedReferencedColumnNameMessage() :
				super.buildUnresolvedReferencedColumnNameMessage();
	}

	protected IMessage buildVirtualSecondaryTableUnresolvedReferencedColumnNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				new String[] {
					this.getSecondaryTableName(),
					this.column.getReferencedColumnName(),
					this.column.getReferencedColumnDbTable().getName()
				},
				this.column,
				this.column.getReferencedColumnNameTextRange()
			);
	}

	@Override
	protected String getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected IMessage buildUnspecifiedNameMultipleJoinColumnsMessage() {
		return this.secondaryTableIsVirtual() ?
				this.buildVirtualSecondaryTableUnspecifiedNameMultipleJoinColumnsMessage() :
				super.buildUnspecifiedNameMultipleJoinColumnsMessage();
	}

	protected IMessage buildVirtualSecondaryTableUnspecifiedNameMultipleJoinColumnsMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[] {this.getSecondaryTableName()},
				this.column,
				this.column.getNameTextRange()
			);
	}

	@Override
	protected String getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return this.secondaryTableIsVirtual() ?
				this.buildVirtualSecondaryTableUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() :
				super.buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();
	}

	protected IMessage buildVirtualSecondaryTableUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JptJpaCoreValidationMessages.VIRTUAL_SECONDARY_TABLE_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[] {this.getSecondaryTableName()},
				this.column,
				this.column.getReferencedColumnNameTextRange()
			);
	}

	@Override
	protected String getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		throw new UnsupportedOperationException();
	}
}
