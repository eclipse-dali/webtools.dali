/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context;

import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.internal.context.BaseJoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.BaseColumnTableValidator.NullTableDescriptionProvider;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class PrimaryKeyJoinColumnValidator extends BaseJoinColumnValidator
{
	protected PrimaryKeyJoinColumnValidator(
				BaseJoinColumn column,
				BaseJoinColumn.Owner owner,
				BaseJoinColumnTextRangeResolver textRangeResolver) {
		super(column, owner, textRangeResolver, new NullTableDescriptionProvider());
	}
	
	@Override
	public IMessage buildUnresolvedNameMessage() {
		if (getColumn().isVirtual()) {
			return this.buildUnresolvedNameMessage(this.getVirtualPKJoinColumnUnresolvedNameMessage());
		}
		return super.buildUnresolvedNameMessage();
	}

	@Override
	protected IMessage buildUnresolvedReferencedColumnNameMessage() {
		if (getColumn().isVirtual()) {
			return this.buildUnresolvedReferencedColumnNameMessage(this.getVirtualPKJoinColumnUnresolvedReferencedColumnNameMessage());
		}
		return super.buildUnresolvedReferencedColumnNameMessage();
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JpaValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	}
	
	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		throw new UnsupportedOperationException();
	}

	protected String getVirtualPKJoinColumnUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getUnresolvedReferencedColumnNameMessage() {
		return JpaValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return JpaValidationMessages.PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	protected String getVirtualPKJoinColumnUnresolvedReferencedColumnNameMessage() {
		return JpaValidationMessages.VIRTUAL_PRIMARY_KEY_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME;
	}

	@Override
	protected String getUnspecifiedNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.PRIMARY_KEY_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return JpaValidationMessages.PRIMARY_KEY_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS;
	}

	@Override
	protected String getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		throw new UnsupportedOperationException();
	}
}
