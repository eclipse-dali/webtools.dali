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

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class BaseJoinColumnValidator<C extends BaseJoinColumn>
	extends AbstractNamedColumnValidator<C>
{
	protected final BaseJoinColumn.ParentAdapter joinColumnParentAdapter;

	protected BaseJoinColumnValidator(
				C column,
				BaseJoinColumn.ParentAdapter joinColumnParentAdapter,
				TableDescriptionProvider provider) {
		super(column, provider);
		this.joinColumnParentAdapter = joinColumnParentAdapter;
	}

	protected BaseJoinColumnValidator(
				PersistentAttribute persistentAttribute,
				C column,
				BaseJoinColumn.ParentAdapter joinColumnParentAdapter,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, provider);
		this.joinColumnParentAdapter = joinColumnParentAdapter;
	}

	@Override
	//this method will only be called if the table validates correctly
	protected void validateName(List<IMessage> messages) {
		this.validateJoinColumnName(messages);
		this.validateReferencedColumnName(messages);
	}

	protected void validateJoinColumnName(List<IMessage> messages) {
		if ((this.column.getSpecifiedName() == null) && (this.joinColumnParentAdapter.getJoinColumnsSize() > 1)) {
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
		if ((this.column.getSpecifiedReferencedColumnName() == null) && (this.joinColumnParentAdapter.getJoinColumnsSize() > 1)) {
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

	protected abstract ValidationMessage getUnresolvedReferencedColumnNameMessage();

	protected IMessage buildUnresolvedReferencedColumnNameMessage(ValidationMessage message) {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getReferencedColumnNameTextRange(),
				message,
				this.column.getReferencedColumnName(),
				this.column.getReferencedColumnDbTable().getName()
			);
	}

	protected IMessage buildVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.getVirtualAttributeTextRange(),
				this.getVirtualAttributeUnresolvedReferencedColumnNameMessage(),
				this.persistentAttribute.getName(),
				this.column.getReferencedColumnName(),
				this.column.getReferencedColumnDbTable().getName()
			);
	}

	protected abstract ValidationMessage getVirtualAttributeUnresolvedReferencedColumnNameMessage();

	protected IMessage buildUnspecifiedNameMultipleJoinColumnsMessage() {
		return this.columnIsPartOfVirtualAttribute() ?
				this.buildVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() :
				this.buildUnspecifiedNameMultipleJoinColumnsMessage(this.getUnspecifiedNameMultipleJoinColumnsMessage());
	}

	protected abstract ValidationMessage getUnspecifiedNameMultipleJoinColumnsMessage();

	protected IMessage buildUnspecifiedNameMultipleJoinColumnsMessage(ValidationMessage message) {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getNameValidationTextRange(),
				message
			);
	}

	protected IMessage buildVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.getVirtualAttributeTextRange(),
				this.getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage(),
				this.persistentAttribute.getName()
			);
	}

	protected abstract ValidationMessage getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage();

	protected IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return this.columnIsPartOfVirtualAttribute() ?
				this.buildVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() :
				this.buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(this.getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage());
	}

	protected abstract ValidationMessage getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();

	protected IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(ValidationMessage message) {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getReferencedColumnNameTextRange(),
				message
			);
	}

	protected IMessage buildVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.getVirtualAttributeTextRange(),
				this.getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(),
				this.persistentAttribute.getName()
			);
	}

	protected abstract ValidationMessage getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();
}
