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

import java.util.List;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTableColumn;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractNamedColumnValidator<C extends ReadOnlyNamedColumn>
	implements JptValidator
{
	/** this is <code>null</code> for columns defined on entities and secondary tables */
	protected final ReadOnlyPersistentAttribute persistentAttribute;

	protected final C column;

	protected final TableDescriptionProvider tableDescriptionProvider;

	protected final JptValidator tableValidator;


	protected AbstractNamedColumnValidator(
				C column) {
		this(null, column);
	}

	protected AbstractNamedColumnValidator(
				C column,
				TableDescriptionProvider provider) {
		this(null, column, provider);
	}

	protected AbstractNamedColumnValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				C column) {
		this(persistentAttribute, column, TableDescriptionProvider.Null.instance());
	}

	protected AbstractNamedColumnValidator(
				ReadOnlyPersistentAttribute persistentAttribute,
				C column,
				TableDescriptionProvider tableDescriptionProvider) {
		this.persistentAttribute = persistentAttribute;
		this.column = column;
		this.tableDescriptionProvider = tableDescriptionProvider;
		this.tableValidator = this.buildTableValidator();
	}

	protected JptValidator buildTableValidator() {
		return JptValidator.Null.instance();
	}

	public final boolean validate(List<IMessage> messages, IReporter reporter) {
		if (this.tableValidator.validate(messages, reporter)) {
			// validate the name only if the table is valid
			this.validateName(messages);
		}
		return true;
	}

	protected void validateName(List<IMessage> messages) {
		Table dbTable = this.column.getDbTable();
		if ((dbTable != null) && ! this.column.isResolved()) {
			messages.add(this.buildUnresolvedNameMessage());
		}
	}

	protected IMessage buildUnresolvedNameMessage() {
		return this.columnIsPartOfVirtualAttribute() ?
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
				this.column.getNameValidationTextRange()
			);
	}

	protected abstract String getUnresolvedNameMessage();

	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualAttributeUnresolvedNameMessage(),
				new String[] {
					this.persistentAttribute.getName(),
					this.column.getName(),
					this.column.getDbTable().getName()
				},
				this.column,
				this.getVirtualAttributeTextRange()
			);
	}

	protected abstract String getVirtualAttributeUnresolvedNameMessage();

	protected boolean columnIsPartOfVirtualAttribute() {
		return (this.persistentAttribute != null) &&
				this.persistentAttribute.isVirtual();
	}

	protected TextRange getVirtualAttributeTextRange() {
		return this.persistentAttribute.getValidationTextRange();
	}

	// ********** table description provider **********

	public interface TableDescriptionProvider {
		String getColumnTableDescriptionMessage();

		final class Null
			implements TableDescriptionProvider
		{
			private static final TableDescriptionProvider INSTANCE = new Null();
			public static TableDescriptionProvider instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			public String getColumnTableDescriptionMessage() {
				throw new UnsupportedOperationException();
			}
			@Override
			public String toString() {
				return ObjectTools.singletonToString(this);
			}
		}
	}


	// ********** base column table validator **********

	/**
	 * This column table validator (or its subclasses) can only be used by
	 * a validator for a <em>base</em> column, which specifies a table. This
	 * includes both normal columns and join columns.
	 */
	protected class BaseColumnTableValidator
		implements JptValidator
	{
		protected BaseColumnTableValidator() {
			super();
		}

		public boolean validate(List<IMessage> messages, IReporter reporter) {
			// if a column does not have a specified table or its specified table is same as
			// its default table, don't do the table validation against it - bug 377110
			String specifiedTable = this.getColumn().getSpecifiedTableName();
			String defaultTable = this.getColumn().getDefaultTableName();
			if (specifiedTable == null || ObjectTools.equals(specifiedTable, defaultTable)) {
				return true;
			}
			if (this.getColumn().tableNameIsInvalid()) {
				messages.add(this.buildTableNotValidMessage());
				return false;
			}
			return true;
		}
	
		protected ReadOnlyTableColumn getColumn() {
			return (ReadOnlyTableColumn) AbstractNamedColumnValidator.this.column;
		}

		protected IMessage buildTableNotValidMessage() {
			return AbstractNamedColumnValidator.this.columnIsPartOfVirtualAttribute() ?
					this.buildVirtualAttributeTableNotValidMessage() :
					this.buildTableNotValidMessage_();
		}
	
		protected IMessage buildTableNotValidMessage_() {
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					this.getColumnTableNotValidMessage(),
					new String[] {
						this.getColumn().getTableName(),
						this.getColumn().getName(),
						this.getColumnTableDescriptionMessage()
					},
					this.getColumn(),
					this.getColumn().getTableNameValidationTextRange()
				);
		}
	
		protected String getColumnTableNotValidMessage() {
			return JptJpaCoreValidationMessages.COLUMN_TABLE_NOT_VALID;
		}
	
		protected String getColumnTableDescriptionMessage()  {
			return AbstractNamedColumnValidator.this.tableDescriptionProvider.getColumnTableDescriptionMessage();
		}

		protected IMessage buildVirtualAttributeTableNotValidMessage() {
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					this.getVirtualAttributeColumnTableNotValidMessage(),
					new String[] {
						AbstractNamedColumnValidator.this.persistentAttribute.getName(),
						this.getColumn().getTableName(),
						this.getColumn().getName(),
						this.getColumnTableDescriptionMessage()
					},
					this.getColumn(),
					AbstractNamedColumnValidator.this.getVirtualAttributeTextRange()
				);
		}
	
		protected String getVirtualAttributeColumnTableNotValidMessage() {
			return JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_TABLE_NOT_VALID;
		}
	}
}
