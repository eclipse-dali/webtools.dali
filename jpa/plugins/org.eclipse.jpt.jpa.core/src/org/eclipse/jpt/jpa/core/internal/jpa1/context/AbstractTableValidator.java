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

import java.util.List;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractTableValidator
	implements JptValidator
{
	/** this is <code>null</code> for tables defined on entities */
	protected final ReadOnlyPersistentAttribute persistentAttribute;

	protected final ReadOnlyTable table;

	protected final TableTextRangeResolver textRangeResolver;

	protected AbstractTableValidator(
				ReadOnlyTable table,
				TableTextRangeResolver textRangeResolver) {
		this(null, table, textRangeResolver);
	}

	protected AbstractTableValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				ReadOnlyTable table,
				TableTextRangeResolver textRangeResolver) {
		super();
		this.persistentAttribute = persistentAttribute;
		this.table = table;
		this.textRangeResolver = textRangeResolver;
	}

	protected TableTextRangeResolver getTextRangeResolver() {
		return this.textRangeResolver;
	}

	public boolean validate(List<IMessage> messages, IReporter reporter) {
		return this.validatesAgainstDatabase() &&
			 	this.validateAgainstDatabase(messages);
	}

	protected boolean validatesAgainstDatabase() {
		return this.table.validatesAgainstDatabase();
	}

	protected boolean validateAgainstDatabase(List<IMessage> messages) {
		if ( ! this.table.catalogIsResolved()) {
			messages.add(this.buildUnresolvedCatalogMessage());
			return false;
		}

		if ( ! this.table.schemaIsResolved()) {
			messages.add(this.buildUnresolvedSchemaMessage());
			return false;
		}

		if ( ! this.table.isResolved()) {
			if (this.table.getName() != null) { //if name is null, the validation will be handled elsewhere, such as the target entity is not defined
				messages.add(this.buildUnresolvedNameMessage());
			}
			return false;
		}
		return true;
	}

	protected IMessage buildUnresolvedCatalogMessage() {
		return this.tableIsPartOfVirtualAttribute() ?
				this.buildVirtualAttributeUnresolvedCatalogMessage() :
				this.buildUnresolvedCatalogMessage(this.getUnresolvedCatalogMessage());
	}

	protected abstract String getUnresolvedCatalogMessage();

	protected IMessage buildUnresolvedCatalogMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			message,
			new String[] {
				this.table.getCatalog(),
				this.table.getName()
			},
			this.table,
			this.textRangeResolver.getCatalogTextRange()
		);
	}

	protected IMessage buildVirtualAttributeUnresolvedCatalogMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getVirtualAttributeUnresolvedCatalogMessage(),
			new String[] {
				this.persistentAttribute.getName(),
				this.table.getCatalog(),
				this.table.getName()
			},
			this.table,
			this.textRangeResolver.getCatalogTextRange()
		);
	}

	protected abstract String getVirtualAttributeUnresolvedCatalogMessage();

	protected IMessage buildUnresolvedSchemaMessage() {
		return this.tableIsPartOfVirtualAttribute() ?
				this.buildVirtualAttributeUnresolvedSchemaMessage() :
				this.buildUnresolvedSchemaMessage(this.getUnresolvedSchemaMessage());
	}

	protected abstract String getUnresolvedSchemaMessage();

	protected IMessage buildUnresolvedSchemaMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			message,
			new String[] {
				this.table.getSchema(),
				this.table.getName()
			},
			this.table,
			this.textRangeResolver.getSchemaTextRange()
		);
	}

	protected IMessage buildVirtualAttributeUnresolvedSchemaMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getVirtualAttributeUnresolvedSchemaMessage(),
			new String[] {
				this.persistentAttribute.getName(),
				this.table.getSchema(),
				this.table.getName()
			},
			this.table,
			this.textRangeResolver.getSchemaTextRange()
		);
	}

	protected abstract String getVirtualAttributeUnresolvedSchemaMessage();

	protected IMessage buildUnresolvedNameMessage() {
		return this.tableIsPartOfVirtualAttribute() ?
				this.buildVirtualAttributeUnresolvedNameMessage() :
				this.buildUnresolvedNameMessage(this.getUnresolvedNameMessage());
	}

	protected abstract String getUnresolvedNameMessage();

	protected IMessage buildUnresolvedNameMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			message,
			new String[] {this.table.getName()},
			this.table,
			this.textRangeResolver.getNameTextRange()
		);
	}

	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getVirtualAttributeUnresolvedNameMessage(),
			new String[] {
				this.persistentAttribute.getName(),
				this.table.getName()
			},
			this.table,
			this.textRangeResolver.getNameTextRange()
		);
	}

	protected abstract String getVirtualAttributeUnresolvedNameMessage();

	protected boolean tableIsPartOfVirtualAttribute() {
		return (this.persistentAttribute != null) &&
				this.persistentAttribute.isVirtual();
	}
}
