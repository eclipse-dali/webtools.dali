/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.BaseColumnTableValidator.NullTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.BaseColumnTableValidator.TableDescriptionProvider;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractNamedColumnValidator<C extends NamedColumn, R extends NamedColumnTextRangeResolver>
	implements JptValidator
{
	protected final PersistentAttribute persistentAttribute;

	protected final C column;

	protected final R textRangeResolver;

	protected final TableValidator tableValidator;

	protected final TableDescriptionProvider tableDescriptionProvider;

	protected AbstractNamedColumnValidator(
				C column,
				R textRangeResolver) {
		this(null, column, textRangeResolver);
	}

	protected AbstractNamedColumnValidator(
				C column,
				R textRangeResolver,
				TableDescriptionProvider provider) {
		this(null, column, textRangeResolver, provider);
	}

	protected AbstractNamedColumnValidator(
				PersistentAttribute persistentAttribute,
				C column,
				R textRangeResolver) {
		this(persistentAttribute, column, textRangeResolver, new NullTableDescriptionProvider());
	}

	protected AbstractNamedColumnValidator(
				PersistentAttribute persistentAttribute,
				C column,
				R textRangeResolver,
				TableDescriptionProvider provider) {
		this.persistentAttribute = persistentAttribute;
		this.column = column;
		this.textRangeResolver = textRangeResolver;
		this.tableDescriptionProvider = provider;
		this.tableValidator = this.buildTableValidator();
	}

	protected TableValidator buildTableValidator() {
		return new NullTableValidator();
	}

	protected boolean isPersistentAttributeVirtual() {
		return (this.persistentAttribute != null) && this.persistentAttribute.isVirtual();
	}

	protected String getPersistentAttributeName() {
		return this.persistentAttribute.getName();
	}

	public boolean validate(List<IMessage> messages, IReporter reporter) {
		if (this.tableValidator.tableNameIsInvalid()) {
			return this.tableValidator.validate(messages, reporter);
		}
		this.validateName(messages);
		return true;
	}

	protected void validateName(List<IMessage> messages) {
		Table dbTable = this.column.getDbTable();
		if ((dbTable != null) && ! this.column.isResolved()) {
			messages.add(this.buildUnresolvedNameMessage());
		}
	}

	protected IMessage buildUnresolvedNameMessage() {
		return this.isPersistentAttributeVirtual() ?
				this.buildVirtualAttributeUnresolvedNameMessage() :
				this.buildUnresolvedNameMessage(this.getUnresolvedNameMessage());
	}

	protected IMessage buildUnresolvedNameMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
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

	protected abstract String getUnresolvedNameMessage();

	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualAttributeUnresolvedNameMessage(),
				new String[] {
					this.getPersistentAttributeName(),
					this.column.getName(),
					this.column.getDbTable().getName()
				},
				this.column,
				this.textRangeResolver.getNameTextRange()
			);
	}

	protected abstract String getVirtualAttributeUnresolvedNameMessage();


	// ********** table validator **********

	public interface TableValidator
		extends JptValidator
	{
		boolean tableNameIsInvalid();
	}

	public static class NullTableValidator
		implements TableValidator
	{
		public boolean validate(List<IMessage> messages, IReporter reporter) {
			return true;
		}
		public boolean tableNameIsInvalid() {
			return false;
		}
	}
}
