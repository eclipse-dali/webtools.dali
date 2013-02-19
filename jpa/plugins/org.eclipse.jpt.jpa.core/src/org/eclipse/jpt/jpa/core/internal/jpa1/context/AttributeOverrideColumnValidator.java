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
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class AttributeOverrideColumnValidator
	extends NamedColumnValidator
{
	final ReadOnlyAttributeOverride override;

	public AttributeOverrideColumnValidator(
				ReadOnlyAttributeOverride override,
				ReadOnlyBaseColumn column,
				TableDescriptionProvider message) {
		super(column, message);
		this.override = override;
	}

	public AttributeOverrideColumnValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				ReadOnlyAttributeOverride override,
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
		return ValidationMessageTools.buildErrorValidationMessage(
				this.getVirtualOverrideUnresolvedNameMessage(),
				this.column.getResource(),
				this.column.getNameValidationTextRange(),
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
		return ValidationMessageTools.buildErrorValidationMessage(
				this.getVirtualAttributeUnresolvedNameMessage(),
				this.column.getResource(),
				this.getVirtualAttributeTextRange(),
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
			return ValidationMessageTools.buildErrorValidationMessage(
					this.getVirtualOverrideColumnTableNotValidMessage(),
					this.getColumn().getResource(),
					this.getColumn().getTableNameValidationTextRange(),
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
			return ValidationMessageTools.buildErrorValidationMessage(
					this.getVirtualAttributeColumnTableNotValidMessage(),
					this.getColumn().getResource(),
					AttributeOverrideColumnValidator.this.persistentAttribute.getValidationTextRange(),
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
