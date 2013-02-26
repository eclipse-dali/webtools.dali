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
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class AttributeOverrideColumnValidator
	extends NamedColumnValidator
{
	final AttributeOverride override;

	public AttributeOverrideColumnValidator(
				AttributeOverride override,
				ReadOnlyBaseColumn column,
				TableDescriptionProvider message) {
		super(column, message);
		this.override = override;
	}

	public AttributeOverrideColumnValidator(
				PersistentAttribute persistentAttribute,
				AttributeOverride override,
				ReadOnlyBaseColumn column,
				TableDescriptionProvider message) {
		super(persistentAttribute, column, message);
		this.override = override;
	}

	@Override
	protected JptValidator buildTableValidator() {
		return new TableValidator();
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualColumnUnresolvedNameMessage() :
				super.buildUnresolvedNameMessage();
	}

	protected IMessage buildVirtualColumnUnresolvedNameMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getNameValidationTextRange(),
				this.getVirtualOverrideUnresolvedNameMessage(),
				this.override.getName(),
				this.column.getName(),
				this.column.getDbTable().getName()
			);
	}

	protected ValidationMessage getVirtualOverrideUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.getVirtualAttributeTextRange(),
				this.getVirtualAttributeUnresolvedNameMessage(),
				this.persistentAttribute.getName(),
				this.override.getName(),
				this.column.getName(),
				this.column.getDbTable().getName()
			);
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME;
	}


	// ********** table validator **********

	protected class TableValidator
		extends BaseColumnTableValidator
	{
		protected TableValidator() {
			super();
		}

		@Override
		protected IMessage buildTableNotValidMessage() {
			return AttributeOverrideColumnValidator.this.override.isVirtual() ?
					this.buildVirtualOverrideColumnTableNotValidMessage() :
					super.buildTableNotValidMessage();
		}

		protected IMessage buildVirtualOverrideColumnTableNotValidMessage() {
			return ValidationMessageTools.buildValidationMessage(
					this.getColumn().getResource(),
					this.getColumn().getTableNameValidationTextRange(),
					this.getVirtualOverrideColumnTableNotValidMessage(),
					AttributeOverrideColumnValidator.this.override.getName(),
					this.getColumn().getTableName(),
					this.getColumn().getName(),
					this.getColumnTableDescriptionMessage()
				);
		}

		protected ValidationMessage getVirtualOverrideColumnTableNotValidMessage() {
			return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected IMessage buildVirtualAttributeTableNotValidMessage() {
			return ValidationMessageTools.buildValidationMessage(
					this.getColumn().getResource(),
					AttributeOverrideColumnValidator.this.persistentAttribute.getValidationTextRange(),
					this.getVirtualAttributeColumnTableNotValidMessage(),
					AttributeOverrideColumnValidator.this.persistentAttribute.getName(),
					AttributeOverrideColumnValidator.this.override.getName(),
					this.getColumn().getTableName(),
					this.getColumn().getName(),
					this.getColumnTableDescriptionMessage()
				);
		}

		@Override
		protected ValidationMessage getVirtualAttributeColumnTableNotValidMessage() {
			return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID;
		}
	}
}
