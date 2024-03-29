/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class PrimaryKeyJoinColumnValidator
	extends BaseJoinColumnValidator<BaseJoinColumn>
{
	protected PrimaryKeyJoinColumnValidator(
				BaseJoinColumn column,
				BaseJoinColumn.ParentAdapter parentAdapter) {
		super(column, parentAdapter, TableDescriptionProvider.Null.instance());
	}

	protected PrimaryKeyJoinColumnValidator(
				PersistentAttribute persistentAttribute,
				BaseJoinColumn column,
				BaseJoinColumn.ParentAdapter parentAdapter) {
		super(persistentAttribute, column, parentAdapter, TableDescriptionProvider.Null.instance());
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		return this.column.isVirtual() ?
				this.buildUnresolvedNameMessage(this.getVirtualPKJoinColumnUnresolvedNameMessage()) :
				super.buildUnresolvedNameMessage();
	}

	protected ValidationMessage getVirtualPKJoinColumnUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected IMessage buildUnresolvedReferencedColumnNameMessage() {
		return this.column.isVirtual() ?
				this.buildUnresolvedReferencedColumnNameMessage(this.getVirtualPKJoinColumnUnresolvedReferencedColumnNameMessage()) :
				super.buildUnresolvedReferencedColumnNameMessage();
	}

	protected ValidationMessage getVirtualPKJoinColumnUnresolvedReferencedColumnNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected IMessage buildUnspecifiedNameMultipleJoinColumnsMessage() {
		return this.column.isVirtual() ?
				this.buildUnspecifiedNameMultipleJoinColumnsMessage(this.getVirtualPKJoinColumnUnspecifiedNameMultipleJoinColumnsMessage()) :
				super.buildUnspecifiedNameMultipleJoinColumnsMessage();
	}

	protected ValidationMessage getVirtualPKJoinColumnUnspecifiedNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return this.column.isVirtual() ?
				this.buildUnspecifiedNameMultipleJoinColumnsMessage(this.getVirtualPKJoinColumnUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage()) :
				super.buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();
	}

	protected ValidationMessage getVirtualPKJoinColumnUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected ValidationMessage getUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected ValidationMessage getUnresolvedReferencedColumnNameMessage() {
		return JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected ValidationMessage getUnspecifiedNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected ValidationMessage getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JptJpaCoreValidationMessages.PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}
}
