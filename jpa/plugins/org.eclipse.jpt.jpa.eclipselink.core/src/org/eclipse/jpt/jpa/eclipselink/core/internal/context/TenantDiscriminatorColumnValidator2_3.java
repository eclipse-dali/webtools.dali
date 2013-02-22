/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context;

import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractNamedColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class TenantDiscriminatorColumnValidator2_3
	extends AbstractNamedColumnValidator<ReadOnlyTenantDiscriminatorColumn2_3>
{
	public TenantDiscriminatorColumnValidator2_3(
			ReadOnlyTenantDiscriminatorColumn2_3 namedColumn) {
		super(namedColumn, new EntityTableDescriptionProvider());
	}

	@Override
	protected JptValidator buildTableValidator() {
		return new TableValidator();
	}

	@Override
	protected IMessage buildUnresolvedNameMessage(ValidationMessage message) {
		return ValidationMessageTools.buildValidationMessage(
				this.column.getResource(),
				this.column.getNameValidationTextRange(),
				message,
				this.column.getName(),
				this.column.getDbTable().getName()
			);
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		return this.column.isVirtual() ?
				this.buildUnresolvedNameMessage(this.getVirtualTenantDiscriminatorColumnUnresolvedNameMessage()) :
				super.buildUnresolvedNameMessage();
	}

	protected ValidationMessage getVirtualTenantDiscriminatorColumnUnresolvedNameMessage() {
		return JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected ValidationMessage getUnresolvedNameMessage() {
		return JptJpaEclipseLinkCoreValidationMessages.TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		return JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	}


	// ********** table validator **********

	protected class TableValidator
		extends BaseColumnTableValidator
	{
		protected TableValidator() {
			super();
		}

		@Override
		protected ReadOnlyTenantDiscriminatorColumn2_3 getColumn() {
			return (ReadOnlyTenantDiscriminatorColumn2_3) super.getColumn();
		}

		@Override
		protected IMessage buildTableNotValidMessage_() {
			return ValidationMessageTools.buildValidationMessage(
					this.getColumn().getResource(),
					this.getColumn().getTableNameValidationTextRange(),
					this.getColumnTableNotValidMessage(),
					this.getColumn().getTableName(),
					this.getColumn().getName(),
					this.getColumnTableDescriptionMessage()
				);
		}

		@Override
		protected ValidationMessage getColumnTableNotValidMessage() {
			return this.getColumn().isVirtual() ?
					this.getVirtualTenantDiscriminatorColumnTableNotValidMessage() :
					this.getColumnTableNotValidMessage_();
		}

		protected ValidationMessage getVirtualTenantDiscriminatorColumnTableNotValidMessage() {
			return JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID;
		}

		protected ValidationMessage getColumnTableNotValidMessage_() {
			return JptJpaEclipseLinkCoreValidationMessages.TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected ValidationMessage getVirtualAttributeColumnTableNotValidMessage() {
			return JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID;
		}
	}
}
