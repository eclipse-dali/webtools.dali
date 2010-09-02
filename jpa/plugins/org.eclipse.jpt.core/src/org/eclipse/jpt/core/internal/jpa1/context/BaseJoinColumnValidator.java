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

import java.util.List;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.internal.context.BaseJoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.BaseColumnTableValidator.TableDescriptionProvider;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class BaseJoinColumnValidator extends AbstractNamedColumnValidator
{
	private final BaseJoinColumn.Owner joinColumnOwner;

	protected BaseJoinColumnValidator(
				BaseJoinColumn column,
				BaseJoinColumn.Owner joinColumnOwner,
				BaseJoinColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
		super(column, textRangeResolver, provider);
		this.joinColumnOwner = joinColumnOwner;
	}

	protected BaseJoinColumnValidator(
				PersistentAttribute persistentAttribute,
				BaseJoinColumn column,
				BaseJoinColumn.Owner joinColumnOwner,
				BaseJoinColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
		super(persistentAttribute, column, textRangeResolver, provider);
		this.joinColumnOwner = joinColumnOwner;
	}

	@Override
	public BaseJoinColumn getColumn() {
		return (BaseJoinColumn) super.getColumn();
	}

	@Override
	public BaseJoinColumnTextRangeResolver getTextRangeResolver() {
		return (BaseJoinColumnTextRangeResolver) super.getTextRangeResolver();
	}

	@Override
	//this method will only be called if the table validates correctly
	protected void validateName(List<IMessage> messages) {
		this.validateJoinColumnName(messages);
		this.validateReferencedColumnName(messages);
	}
	
	protected void validateJoinColumnName(List<IMessage> messages) {
		if (this.getColumn().getSpecifiedName() == null && this.joinColumnOwner.joinColumnsSize() > 1) {
			messages.add(this.buildUnspecifiedNameMultipleJoinColumnsMessage());
		}
		else if (this.getColumn().getName() != null){
			super.validateName(messages);
		}
		//If the name is null and there is only one join-column, one of these validation messages will apply
		// 1. target entity does not have a primary key
		// 2. target entity is not specified
		// 3. target entity is not an entity
	}
	
	protected void validateReferencedColumnName(List<IMessage> messages) {
		if (this.getColumn().getSpecifiedReferencedColumnName() == null && this.joinColumnOwner.joinColumnsSize() > 1) {
			messages.add(this.buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage());
		}
		else if (this.getColumn().getSpecifiedReferencedColumnName() != null) {
			if (this.getColumn().getReferencedColumnDbTable() != null && ! this.getColumn().isReferencedColumnResolved()) {
				messages.add(this.buildUnresolvedReferencedColumnNameMessage());
			}
		}
		//If the referenced column name is null and there is only one join-column, one of these validation messages will apply
		// 1. target entity does not have a primary key
		// 2. target entity is not specified
		// 3. target entity is not an entity
	}

	protected IMessage buildUnresolvedReferencedColumnNameMessage() {
		if (isPersistentAttributeVirtual()) {
			return this.buildVirtualAttributeUnresolvedReferencedColumnNameMessage();
		}
		return buildUnresolvedReferencedColumnNameMessage(this.getUnresolvedReferencedColumnNameMessage());
	}

	protected abstract String getUnresolvedReferencedColumnNameMessage();

	protected IMessage buildUnresolvedReferencedColumnNameMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			message,
			new String[] {this.getColumn().getReferencedColumnName(), this.getColumn().getReferencedColumnDbTable().getName()},
			this.getColumn(), 
			this.getTextRangeResolver().getReferencedColumnNameTextRange()
		);
	}

	protected IMessage buildVirtualAttributeUnresolvedReferencedColumnNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getVirtualAttributeUnresolvedReferencedColumnNameMessage(),
			new String[] {getPersistentAttributeName(), this.getColumn().getReferencedColumnName(), this.namedColumn.getDbTable().getName()},
			this.getColumn(), 
			this.getTextRangeResolver().getReferencedColumnNameTextRange()
		);
	}

	protected abstract String getVirtualAttributeUnresolvedReferencedColumnNameMessage();

	protected IMessage buildUnspecifiedNameMultipleJoinColumnsMessage() {
		if (isPersistentAttributeVirtual()) {
			return this.buildVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage();
		}
		return this.buildUnspecifiedNameMultipleJoinColumnsMessage(this.getUnspecifiedNameMultipleJoinColumnsMessage());
	}

	protected abstract String getUnspecifiedNameMultipleJoinColumnsMessage();

	protected IMessage buildUnspecifiedNameMultipleJoinColumnsMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			message,
			new String[0],
			this.getColumn(), 
			this.getTextRangeResolver().getNameTextRange()
		);
	}

	protected IMessage buildVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage(),
			new String[] {this.getPersistentAttributeName()},
			this.getColumn(), 
			this.getTextRangeResolver().getNameTextRange()
		);
	}

	protected abstract String getVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage();

	protected IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		if (isPersistentAttributeVirtual()) {
			return this.buildVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();
		}
		return this.buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(this.getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage());
	}

	protected abstract String getUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();

	protected IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			message,
			new String[0],
			this.getColumn(), 
			this.getTextRangeResolver().getReferencedColumnNameTextRange()
		);
	}

	protected IMessage buildVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(),
			new String[] {this.getPersistentAttributeName()},
			this.getColumn(), 
			this.getTextRangeResolver().getReferencedColumnNameTextRange()
		);
	}

	protected abstract String getVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage();
}
