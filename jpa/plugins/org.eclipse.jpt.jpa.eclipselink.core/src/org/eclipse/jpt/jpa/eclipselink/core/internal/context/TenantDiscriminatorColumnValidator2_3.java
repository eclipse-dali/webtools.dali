/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context;

import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AbstractNamedColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
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
	protected IMessage buildUnresolvedNameMessage(String message) {
		return DefaultEclipseLinkJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				message,
				new String[] {
					this.column.getName(),
					this.column.getDbTable().getName()
				},
				this.column,
				this.column.getNameTextRange()
			);
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		return this.column.isVirtual() ?
				this.buildUnresolvedNameMessage(this.getVirtualTenantDiscriminatorColumnUnresolvedNameMessage()) :
				super.buildUnresolvedNameMessage();
	}

	protected String getVirtualTenantDiscriminatorColumnUnresolvedNameMessage() {
		return JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getUnresolvedNameMessage() {
		return JptJpaEclipseLinkCoreValidationMessages.TENANT_DISCRIMINATOR_COLUMN_UNRESOLVED_NAME;
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
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
			return DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					this.getColumnTableNotValidMessage(),
					new String[] {
						this.getColumn().getTableName(),
						this.getColumn().getName(),
						this.getColumnTableDescriptionMessage()
					},
					this.getColumn(),
					this.getColumn().getTableNameTextRange()
				);
		}

		@Override
		protected String getColumnTableNotValidMessage() {
			return this.getColumn().isVirtual() ?
					this.getVirtualTenantDiscriminatorColumnTableNotValidMessage() :
					this.getColumnTableNotValidMessage_();
		}

		protected String getVirtualTenantDiscriminatorColumnTableNotValidMessage() {
			return JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID;
		}

		protected String getColumnTableNotValidMessage_() {
			return JptJpaEclipseLinkCoreValidationMessages.TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected String getVirtualAttributeColumnTableNotValidMessage() {
			return JptJpaEclipseLinkCoreValidationMessages.VIRTUAL_ATTRIBUTE_TENANT_DISCRIMINATOR_COLUMN_TABLE_NOT_VALID;
		}
	}
}
