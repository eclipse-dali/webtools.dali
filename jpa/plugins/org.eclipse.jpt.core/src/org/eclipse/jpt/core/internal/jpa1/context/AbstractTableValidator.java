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
package org.eclipse.jpt.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractTableValidator
	implements JptValidator
{

	protected final PersistentAttribute persistentAttribute;

	protected final Table table;

	protected final TableTextRangeResolver textRangeResolver;

	protected AbstractTableValidator(
				Table table,
				TableTextRangeResolver textRangeResolver) {
		this(null, table, textRangeResolver);
	}

	protected AbstractTableValidator(
				PersistentAttribute persistentAttribute,
				Table table,
				TableTextRangeResolver textRangeResolver) {
		super();
		this.persistentAttribute = persistentAttribute;
		this.table = table;
		this.textRangeResolver = textRangeResolver;
	}

	protected Table getTable() {
		return this.table;
	}

	protected TableTextRangeResolver getTextRangeResolver() {
		return this.textRangeResolver;
	}

	protected boolean isPersistentAttributeVirtual() {
		return this.persistentAttribute != null && this.persistentAttribute.isVirtual();
	}

	protected String getPersistentAttributeName() {
		return this.persistentAttribute.getName();
	}

	public boolean validate(List<IMessage> messages, IReporter reporter) {
		if (this.shouldValidateAgainstDatabase()) {
			return this.validateAgainstDatabase(messages);
		}
		return false;
	}

	protected boolean shouldValidateAgainstDatabase() {
		return this.table.shouldValidateAgainstDatabase();
	}

	protected boolean validateAgainstDatabase(List<IMessage> messages) {
		if ( ! this.table.hasResolvedCatalog()) {
			messages.add(buildUnresolvedCatalogMessage());
			return false;
		}

		if ( ! this.table.hasResolvedSchema()) {
			messages.add(buildUnresolvedSchemaMessage());
			return false;
		}

		if ( ! this.table.isResolved()) {
			if (this.table.getName() != null) { //if name is null, the validation will be handled elsewhere, such as the target entity is not defined
				messages.add(buildUnresolvedNameMessage());
			}
			return false;
		}
		return true;
	}

	protected IMessage buildUnresolvedCatalogMessage() {
		if (isPersistentAttributeVirtual()) {
			return this.buildVirtualAttributeUnresolvedCatalogMessage();
		}
		return this.buildUnresolvedCatalogMessage(this.getUnresolvedCatalogMessage());
	}

	protected abstract String getUnresolvedCatalogMessage();

	protected IMessage buildUnresolvedCatalogMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			message,
			new String[] {this.table.getCatalog(), this.table.getName()}, 
			this.table, 
			this.textRangeResolver.getCatalogTextRange()
		);
	}

	protected IMessage buildVirtualAttributeUnresolvedCatalogMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getVirtualAttributeUnresolvedCatalogMessage(),
			new String[] {this.getPersistentAttributeName(), this.table.getCatalog(), this.table.getName()},
			this.table, 
			this.textRangeResolver.getCatalogTextRange()
		);
	}

	protected abstract String getVirtualAttributeUnresolvedCatalogMessage();

	protected IMessage buildUnresolvedSchemaMessage() {
		if (isPersistentAttributeVirtual()) {
			return this.buildVirtualAttributeUnresolvedSchemaMessage();
		}
		return this.buildUnresolvedSchemaMessage(this.getUnresolvedSchemaMessage());
	}

	protected abstract String getUnresolvedSchemaMessage();

	protected IMessage buildUnresolvedSchemaMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			message,
			new String[] {this.table.getSchema(), this.table.getName()}, 
			this.table, 
			this.textRangeResolver.getSchemaTextRange()
		);
	}

	protected IMessage buildVirtualAttributeUnresolvedSchemaMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getVirtualAttributeUnresolvedSchemaMessage(),
			new String[] {this.getPersistentAttributeName(), this.table.getSchema(), this.table.getName()},
			this.table, 
			this.textRangeResolver.getSchemaTextRange()
		);
	}

	protected abstract String getVirtualAttributeUnresolvedSchemaMessage();

	protected IMessage buildUnresolvedNameMessage() {
		if (isPersistentAttributeVirtual()) {
			return this.buildVirtualAttributeUnresolvedNameMessage();
		}
		return this.buildUnresolvedNameMessage(this.getUnresolvedNameMessage());
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
			new String[] {this.getPersistentAttributeName(), this.table.getName()},
			this.table, 
			this.textRangeResolver.getNameTextRange()
		);
	}

	protected abstract String getVirtualAttributeUnresolvedNameMessage();

}
