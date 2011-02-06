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
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class AssociationOverrideJoinTableValidator extends AbstractJoinTableValidator
{
	private final AssociationOverride override;

	public AssociationOverrideJoinTableValidator(
				AssociationOverride override,
				JoinTable table,
				TableTextRangeResolver textRangeResolver) {
		super(table, textRangeResolver);
		this.override = override;
	}

	public AssociationOverrideJoinTableValidator(
				PersistentAttribute persistentAttribute,
				AssociationOverride override, 
				JoinTable table, 
				TableTextRangeResolver textRangeResolver) {
		super(persistentAttribute, table, textRangeResolver);
		this.override = override;
	}

	@Override
	protected IMessage buildUnresolvedNameMessage() {
		if (this.override.isVirtual()) {
			return this.buildVirtualOverrideUnresolvedNameMessage();
		}
		return super.buildUnresolvedNameMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_NAME,
			new String[] {this.override.getName(), this.getTable().getName()},
			this.getTable(), 
			this.getTextRangeResolver().getNameTextRange()
		);
	}

	@Override
	protected IMessage buildUnresolvedCatalogMessage() {
		if (this.override.isVirtual()) {
			return this.buildVirtualOverrideUnresolvedCatalogMessage();
		}
		return super.buildUnresolvedCatalogMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedCatalogMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_CATALOG,
			new String[] {this.override.getName(), this.getTable().getCatalog()},
			this.getTable(), 
			this.getTextRangeResolver().getCatalogTextRange()
		);
	}

	@Override
	protected IMessage buildUnresolvedSchemaMessage() {
		if (this.override.isVirtual()) {
			return this.buildVirtualOverrideUnresolvedSchemaMessage();
		}
		return super.buildUnresolvedSchemaMessage();
	}

	protected IMessage buildVirtualOverrideUnresolvedSchemaMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_TABLE_UNRESOLVED_SCHEMA,
			new String[] {this.override.getName(), this.getTable().getSchema()},
			this.getTable(), 
			this.getTextRangeResolver().getSchemaTextRange()
		);
	}

	@Override
	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		throw new UnsupportedOperationException("Nested relationship mappings with JoinTable are unsupported"); //$NON-NLS-1$
	}

	@Override
	protected IMessage buildVirtualAttributeUnresolvedCatalogMessage() {
		throw new UnsupportedOperationException("Nested relationship mappings with JoinTable are unsupported"); //$NON-NLS-1$
	}

	@Override
	protected IMessage buildVirtualAttributeUnresolvedSchemaMessage() {
		throw new UnsupportedOperationException("Nested relationship mappings with JoinTable are unsupported"); //$NON-NLS-1$
	}

	@Override
	protected String getVirtualAttributeUnresolvedCatalogMessage() {
		throw new UnsupportedOperationException("Nested relationship mappings with JoinTable are unsupported"); //$NON-NLS-1$
	}

	@Override
	protected String getVirtualAttributeUnresolvedSchemaMessage() {
		throw new UnsupportedOperationException("Nested relationship mappings with JoinTable are unsupported"); //$NON-NLS-1$
	}

	@Override
	protected String getVirtualAttributeUnresolvedNameMessage() {
		throw new UnsupportedOperationException("Nested relationship mappings with JoinTable are unsupported"); //$NON-NLS-1$
	}
}
