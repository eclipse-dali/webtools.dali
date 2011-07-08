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

import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class AssociationOverrideJoinTableValidator
	extends AbstractJoinTableValidator
{
	private final ReadOnlyAssociationOverride override;

	public AssociationOverrideJoinTableValidator(
				ReadOnlyAssociationOverride override,
				ReadOnlyJoinTable table,
				TableTextRangeResolver textRangeResolver) {
		super(table, textRangeResolver);
		this.override = override;
	}

	public AssociationOverrideJoinTableValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				ReadOnlyAssociationOverride override, 
				ReadOnlyJoinTable table, 
				TableTextRangeResolver textRangeResolver) {
		super(persistentAttribute, table, textRangeResolver);
		this.override = override;
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnresolvedNameMessage() :
				super.buildUnresolvedNameMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_NAME,
			new String[] {
				this.override.getName(),
				this.table.getName()
			},
			this.table, 
			this.getTextRangeResolver().getNameTextRange()
		);
	}

	@Override
	protected IMessage buildUnresolvedCatalogMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnresolvedCatalogMessage() :
				super.buildUnresolvedCatalogMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedCatalogMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_CATALOG,
			new String[] {
				this.override.getName(),
				this.table.getCatalog()
			},
			this.table, 
			this.getTextRangeResolver().getCatalogTextRange()
		);
	}

	@Override
	protected IMessage buildUnresolvedSchemaMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnresolvedSchemaMessage() :
				super.buildUnresolvedSchemaMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedSchemaMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_SCHEMA,
			new String[] {
				this.override.getName(),
				this.table.getSchema()
			},
			this.table, 
			this.getTextRangeResolver().getSchemaTextRange()
		);
	}

	@Override
	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		throw this.buildNestedJoinTableNotSupportedException();
	}

	@Override
	protected IMessage buildVirtualAttributeUnresolvedCatalogMessage() {
		throw this.buildNestedJoinTableNotSupportedException();
	}

	@Override
	protected IMessage buildVirtualAttributeUnresolvedSchemaMessage() {
		throw this.buildNestedJoinTableNotSupportedException();
	}

	@Override
	protected String getVirtualAttributeUnresolvedCatalogMessage() {
		throw this.buildNestedJoinTableNotSupportedException();
	}

	@Override
	protected String getVirtualAttributeUnresolvedSchemaMessage() {
		throw this.buildNestedJoinTableNotSupportedException();
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		throw this.buildNestedJoinTableNotSupportedException();
	}

	protected UnsupportedOperationException buildNestedJoinTableNotSupportedException() {
		return new UnsupportedOperationException("A nested relationship mapping cannot specify a join table"); //$NON-NLS-1$
	}
}
