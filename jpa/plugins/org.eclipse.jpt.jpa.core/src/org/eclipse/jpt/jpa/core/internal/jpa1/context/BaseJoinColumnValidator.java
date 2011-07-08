/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.BaseJoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class BaseJoinColumnValidator<C extends ReadOnlyBaseJoinColumn, R extends BaseJoinColumnTextRangeResolver>
	extends AbstractNamedColumnValidator<C, R>
{
	private final ReadOnlyBaseJoinColumn.Owner joinColumnOwner;

	protected BaseJoinColumnValidator(
				C column,
				ReadOnlyBaseJoinColumn.Owner joinColumnOwner,
				R textRangeResolver,
				TableDescriptionProvider provider) {
		super(column, textRangeResolver, provider);
		this.joinColumnOwner = joinColumnOwner;
	}

	protected BaseJoinColumnValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				C column,
				ReadOnlyBaseJoinColumn.Owner joinColumnOwner,
				R textRangeResolver,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, textRangeResolver, provider);
		this.joinColumnOwner = joinColumnOwner;
	}

	@Override
	//this method will only be called if the table validates correctly
	protected void validateName(List<IMessage> messages) {
		this.validateJoinColumnName(messages);
		this.validateReferencedColumnName(messages);
	}

	protected void validateJoinColumnName(List<IMessage> messages) {
		if ((this.column.getSpecifiedName() == null) && (this.joinColumnOwner.joinColumnsSize() > 1)) {
			messages.add(this.buildUnspecifiedNameMultipleJoinColumnsMessage());
		}
		else if (this.column.getName() != null){
			super.validateName(messages);
		}
		//If the name is null and there is only one join-column, one of these validation messages will apply
		// 1. target entity does not have a primary key
		// 2. target entity is not specified
		// 3. target entity is not an entity
	}

	protected void validateReferencedColumnName(List<IMessage> messages) {
		if ((this.column.getSpecifiedReferencedColumnName() == null) && (this.joinColumnOwner.joinColumnsSize() > 1)) {
			messages.add(this.buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage());
		}
		//bug 315292 is the reason we are only validating if there is a specified referenced column name
		else if (this.column.getSpecifiedReferencedColumnName() != null) {
			if ((this.column.getReferencedColumnDbTable() != null) && ! this.column.referencedColumnIsResolved()) {
				messages.add(this.buildUnresolvedReferencedColumnNameMessage());
			}
		}
		//If the referenced column name is null and there is only one join-column, one of these validation messages will apply
		// 1. target entity does not have a primary key
		// 2. target entity is not specified
		// 3. target entity is not an entity
	}

	protected IMessage buildUnresolvedReferencedColumnNameMessage() {
		return this.columnIsPartOfVirtualAttribute() ?
				this.buildVirtualAttributeUnresolvedReferencedColumnNameMessage() :
				this.buildUnresolvedReferencedColumnNameMessage(this.getUnresolvedReferencedColumnNameMessage());
	}

	protected abstract String getUnresolvedReferencedColumnNameMessage();

	protected IMessage buildUnresolvedReferencedColumnNameMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				message,
				new String[] {
					this.column.getReferencedColumnName(),
					this.column.getReferencedColumnDbTable().getName()
				},
				this.column,
				this.textRangeResolver.getReferencedColumnNameTextRange()
			);
	}

	protected IMessage buildVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualAttributeUnresolvedReferencedColumnNameMessage(),
				new String[] {
					this.persistentAttribute.getName(),
					this.column.getReferencedColumnName(),
					this.column.getReferencedColumnDbTable().getName()
				},
				this.column,
				this.textRangeResolver.getReferencedColumnNameTextRange()
			);
	}

	protected abstract String getVirtualAttributeUnresolvedReferencedColumnNameMessage();

	protected IMessage buildUnspecifiedNameMultipleJoinColumnsMessage() {
		return this.columnIsPartOfVirtualAttribute() ?
				this.buildVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() :
				this.buildUnspecifiedNameMultipleJoinColumnsMessage(this.getUnspecifiedNameMultipleJoinColumnsMessage());
	}

	protected abstract String getUnspecifiedNameMultipleJoinColumnsMessage();

	protected IMessage buildUnspecifiedNameMultipleJoinColumnsMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				message,
				StringTools.EMPTY_STRING_ARRAY,
				this.column,
				this.textRangeResolver.getNameTextRange()
			);
	}

	protected IMessage buildVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage(),
				new String[] {this.persistentAttribute.getName()},
				this.column,
				this.textRangeResolver.getNameTextRange()
			);
	}

	protected abstract String getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage();

	protected IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return this.columnIsPartOfVirtualAttribute() ?
				this.buildVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() :
				this.buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(this.getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage());
	}

	protected abstract String getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();

	protected IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				message,
				StringTools.EMPTY_STRING_ARRAY,
				this.column,
				this.textRangeResolver.getReferencedColumnNameTextRange()
			);
	}

	protected IMessage buildVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(),
				new String[] {this.persistentAttribute.getName()},
				this.column,
				this.textRangeResolver.getReferencedColumnNameTextRange()
			);
	}

	protected abstract String getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();
}
