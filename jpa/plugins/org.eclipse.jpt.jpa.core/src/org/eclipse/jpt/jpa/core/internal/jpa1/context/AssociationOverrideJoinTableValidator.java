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
import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class AssociationOverrideJoinTableValidator
	extends AbstractJoinTableValidator
{
	private final ReadOnlyAssociationOverride override;

	public AssociationOverrideJoinTableValidator(
				ReadOnlyAssociationOverride override,
				ReadOnlyJoinTable table) {
		super(table);
		this.override = override;
	}

	public AssociationOverrideJoinTableValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				ReadOnlyAssociationOverride override, 
				ReadOnlyJoinTable table) {
		super(persistentAttribute, table);
		this.override = override;
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnresolvedNameMessage() :
				super.buildUnresolvedNameMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedNameMessage() {
		return ValidationMessageTools.buildErrorValidationMessage(
				this.table.getResource(),
				this.table.getNameValidationTextRange(),
				JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_NAME,
				this.override.getName(),
				this.table.getName()
			);
	}

	@Override
	protected IMessage buildUnresolvedCatalogMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnresolvedCatalogMessage() :
				super.buildUnresolvedCatalogMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedCatalogMessage() {
		return ValidationMessageTools.buildErrorValidationMessage(
				this.table.getResource(),
				this.table.getCatalogValidationTextRange(),
				JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_CATALOG,
				this.override.getName(),
				this.table.getCatalog()
			);
	}

	@Override
	protected IMessage buildUnresolvedSchemaMessage() {
		return this.override.isVirtual() ?
				this.buildVirtualOverrideUnresolvedSchemaMessage() :
				super.buildUnresolvedSchemaMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedSchemaMessage() {
		return ValidationMessageTools.buildErrorValidationMessage(
				this.table.getResource(),
				this.table.getSchemaValidationTextRange(),
				JptJpaCoreValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_SCHEMA,
				this.override.getName(),
				this.table.getSchema()
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
	protected ValidationMessage getVirtualAttributeUnresolvedCatalogMessage() {
		throw this.buildNestedJoinTableNotSupportedException();
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedSchemaMessage() {
		throw this.buildNestedJoinTableNotSupportedException();
	}

	@Override
	protected ValidationMessage getVirtualAttributeUnresolvedNameMessage() {
		throw this.buildNestedJoinTableNotSupportedException();
	}

	protected UnsupportedOperationException buildNestedJoinTableNotSupportedException() {
		return new UnsupportedOperationException("A nested relationship mapping cannot specify a join table"); //$NON-NLS-1$
	}
}
