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

import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class PrimaryKeyJoinColumnValidator
	extends BaseJoinColumnValidator<ReadOnlyBaseJoinColumn>
{
	protected PrimaryKeyJoinColumnValidator(
				ReadOnlyBaseJoinColumn column,
				ReadOnlyBaseJoinColumn.Owner owner) {
		super(column, owner, TableDescriptionProvider.Null.instance());
	}

	protected PrimaryKeyJoinColumnValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				ReadOnlyBaseJoinColumn column,
				ReadOnlyBaseJoinColumn.Owner owner) {
		super(persistentAttribute, column, owner, TableDescriptionProvider.Null.instance());
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		return this.column.isVirtual() ?
				this.buildUnresolvedNameMessage(this.getVirtualPKJoinColumnUnresolvedNameMessage()) :
				super.buildUnresolvedNameMessage();
	}

	protected String getVirtualPKJoinColumnUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected IMessage buildUnresolvedReferencedColumnNameMessage() {
		return this.column.isVirtual() ?
				this.buildUnresolvedReferencedColumnNameMessage(this.getVirtualPKJoinColumnUnresolvedReferencedColumnNameMessage()) :
				super.buildUnresolvedReferencedColumnNameMessage();
	}

	protected String getVirtualPKJoinColumnUnresolvedReferencedColumnNameMessage() {
		return JpaValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected IMessage buildUnspecifiedNameMultipleJoinColumnsMessage() {
		return this.column.isVirtual() ?
				this.buildUnspecifiedNameMultipleJoinColumnsMessage(this.getVirtualPKJoinColumnUnspecifiedNameMultipleJoinColumnsMessage()) :
				super.buildUnspecifiedNameMultipleJoinColumnsMessage();
	}

	protected String getVirtualPKJoinColumnUnspecifiedNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return this.column.isVirtual() ?
				this.buildUnspecifiedNameMultipleJoinColumnsMessage(this.getVirtualPKJoinColumnUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage()) :
				super.buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();
	}

	protected String getVirtualPKJoinColumnUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getUnresolvedReferencedColumnNameMessage() {
		return JpaValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected String getUnspecifiedNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}
}
