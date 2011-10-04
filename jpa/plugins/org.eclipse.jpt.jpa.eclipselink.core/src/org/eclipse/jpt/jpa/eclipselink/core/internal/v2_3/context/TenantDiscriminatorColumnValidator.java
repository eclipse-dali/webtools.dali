/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.context;

import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractNamedColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.ReadOnlyTenantDiscriminatorColumn;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class TenantDiscriminatorColumnValidator
	extends AbstractNamedColumnValidator<ReadOnlyTenantDiscriminatorColumn, NamedColumnTextRangeResolver>
{
	public TenantDiscriminatorColumnValidator(
			ReadOnlyTenantDiscriminatorColumn namedColumn,
			NamedColumnTextRangeResolver textRangeResolver) {
		super(namedColumn, textRangeResolver, new EntityTableDescriptionProvider());
	}

	@Override
	protected JptValidator buildTableValidator() {
		return new TableValidator();
	}

	@Override
	protected IMessage buildUnresolvedNameMessage(String message) {
		return DefaultEclipseLinkJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				message,
				new String[] {
					this.column.getName(),
					this.column.getDbTable().getName()
				},
				this.column,
				this.textRangeResolver.getNameTextRange()
			);
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		return this.column.isVirtual() ?
				this.buildUnresolvedNameMessage(this.getVirtualTenantDiscriminatorColumnUnresolvedNameMessage()) :
				super.buildUnresolvedNameMessage();
	}

	protected String getVirtualTenantDiscriminatorColumnUnresolvedNameMessage() {
		return EclipseLinkJpaValidationMessages.VIRTUAL_TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return EclipseLinkJpaValidationMessages.TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		return EclipseLinkJpaValidationMessages.VIRTUAL_ATTRIBUTE_TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	}


	// ********** table validator **********

	protected class TableValidator
		extends BaseColumnTableValidator
	{
		protected TableValidator() {
			super();
		}

		@Override
		protected ReadOnlyTenantDiscriminatorColumn getColumn() {
			return (ReadOnlyTenantDiscriminatorColumn) super.getColumn();
		}

		@Override
		protected IMessage buildTableNotValidMessage_() {
			return DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					this.getColumnTableNotValidMessage(),
					new String[] {
						this.getColumn().getTable(),
						this.getColumn().getName(),
						this.getColumnTableDescriptionMessage()
					},
					this.getColumn(),
					this.getTextRangeResolver().getTableTextRange()
				);
		}

		@Override
		protected String getColumnTableNotValidMessage() {
			return this.getColumn().isVirtual() ?
					this.getVirtualTenantDiscriminatorColumnTableNotValidMessage() :
					this.getColumnTableNotValidMessage_();
		}

		protected String getVirtualTenantDiscriminatorColumnTableNotValidMessage() {
			return EclipseLinkJpaValidationMessages.VIRTUAL_TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID;
		}

		protected String getColumnTableNotValidMessage_() {
			return EclipseLinkJpaValidationMessages.TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected String getVirtualAttributeColumnTableNotValidMessage() {
			return EclipseLinkJpaValidationMessages.VIRTUAL_ATTRIBUTE_TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID;
		}
	}
}
