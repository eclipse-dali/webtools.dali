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

import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.BaseColumnTableValidator.TableDescriptionProvider;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class AttributeOverrideColumnValidator extends NamedColumnValidator
{
	private final AttributeOverride override;
	
	public AttributeOverrideColumnValidator(
				AttributeOverride override,
				BaseColumn column,
				BaseColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider message) {
		super(column, textRangeResolver, message);
		this.override = override;
	}

	public AttributeOverrideColumnValidator(
				PersistentAttribute persistentAttribute,
				AttributeOverride override,
				BaseColumn column,
				BaseColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider message) {
		super(persistentAttribute, column, textRangeResolver, message);
		this.override = override;
	}

	@Override
	protected TableValidator buildTableValidator() {
		return new AttributeOverrideColumnTableValidator(this.persistentAttribute, this.getColumn(), this.getTextRangeResolver(), this.tableDescriptionProvider);
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		if (this.override.isVirtual()) {
			return this.buildVirtualColumnUnresolvedNameMessage();
		}
		return super.buildUnresolvedNameMessage();
	}
	
	protected IMessage buildVirtualColumnUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			getVirtualOverrideUnresolvedNameMessage(),
			new String[] {this.override.getName(), this.namedColumn.getName(), this.namedColumn.getDbTable().getName()},
			this.namedColumn, 
			this.textRangeResolver.getNameTextRange()
		);
	}

	protected String getVirtualOverrideUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getVirtualAttributeUnresolvedNameMessage(),
			new String[] {
				getPersistentAttributeName(), 
				this.override.getName(), 
				getColumn().getName(), 
				getColumn().getDbTable().getName()},
			this.namedColumn, 
			this.textRangeResolver.getNameTextRange()
		);
	}
	
	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME;
	}


	public class AttributeOverrideColumnTableValidator extends BaseColumnTableValidator {

		protected AttributeOverrideColumnTableValidator(
					PersistentAttribute persistentAttribute,
					BaseColumn column,
					BaseColumnTextRangeResolver textRangeResolver,
					TableDescriptionProvider provider) {
			super(persistentAttribute, column, textRangeResolver, provider);
		}
		
		@Override
		public IMessage buildTableNotValidMessage() {
			if (AttributeOverrideColumnValidator.this.override.isVirtual()) {
				return this.buildVirtualOverrideColumnTableNotValidMessage();
			}
			return super.buildTableNotValidMessage();
		}
		
		protected IMessage buildVirtualOverrideColumnTableNotValidMessage() {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				getVirtualOverrideColumnTableNotValidMessage(),
				new String[] {
					AttributeOverrideColumnValidator.this.override.getName(),
					this.getColumn().getTable(),
					this.getColumn().getName(),
					this.getColumnTableDescriptionMessage()},
				this.getColumn(), 
				getTextRangeResolver().getTableTextRange()
			);
		}

		protected String getVirtualOverrideColumnTableNotValidMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID;
		}
		
		@Override
		protected String getVirtualAttributeColumnTableNotValidMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID;
		}
	}
}
