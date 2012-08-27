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

import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
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
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualOverrideUnresolvedNameMessage(),
				new String[] {
					this.override.getName(),
					this.column.getName(),
					this.column.getDbTable().getName()
				},
				this.column,
				this.column.getNameTextRange()
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
					this.persistentAttribute.getName(),
					this.override.getName(),
					this.column.getName(),
					this.column.getDbTable().getName()
				},
				this.column,
				this.column.getNameTextRange()
			);
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME;
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
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					this.getVirtualOverrideColumnTableNotValidMessage(),
					new String[] {
						AttributeOverrideColumnValidator.this.override.getName(),
						this.getColumn().getTable(),
						this.getColumn().getName(),
						this.getColumnTableDescriptionMessage()
					},
					this.getColumn(),
					this.getColumn().getTableTextRange()
				);
		}

		protected String getVirtualOverrideColumnTableNotValidMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected IMessage buildVirtualAttributeTableNotValidMessage() {
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					this.getVirtualAttributeColumnTableNotValidMessage(),
					new String[] {
						AttributeOverrideColumnValidator.this.persistentAttribute.getName(),
						AttributeOverrideColumnValidator.this.override.getName(),
						this.getColumn().getTable(),
						this.getColumn().getName(),
						this.getColumnTableDescriptionMessage()
					},
					this.getColumn(),
					this.getColumn().getTableTextRange()
				);
		}

		@Override
		protected String getVirtualAttributeColumnTableNotValidMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID;
		}
	}
}
