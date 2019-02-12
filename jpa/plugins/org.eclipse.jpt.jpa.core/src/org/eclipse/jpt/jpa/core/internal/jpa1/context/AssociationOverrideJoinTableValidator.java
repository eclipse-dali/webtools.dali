/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class AssociationOverrideJoinTableValidator
	extends AbstractJoinTableValidator
{
	private final AssociationOverride override;

	public AssociationOverrideJoinTableValidator(
				AssociationOverride override,
				JoinTable table) {
		super(table);
		this.override = override;
	}

	public AssociationOverrideJoinTableValidator(
				PersistentAttribute persistentAttribute,
				AssociationOverride override, 
				JoinTable table) {
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
		return ValidationMessageTools.buildValidationMessage(
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
		return ValidationMessageTools.buildValidationMessage(
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
		return ValidationMessageTools.buildValidationMessage(
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
