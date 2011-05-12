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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractNamedColumnValidator<C extends NamedColumn, R extends NamedColumnTextRangeResolver>
	implements JptValidator
{
	// this is null for columns defined on entities and secondary tables
	protected final PersistentAttribute persistentAttribute;

	protected final C column;

	protected final R textRangeResolver;

	protected final TableDescriptionProvider tableDescriptionProvider;
	
	protected final TableValidator tableValidator;


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
		this(persistentAttribute, column, textRangeResolver, TableDescriptionProvider.Null.instance());
	}

	protected AbstractNamedColumnValidator(
				PersistentAttribute persistentAttribute,
				C column,
				R textRangeResolver,
				TableDescriptionProvider tableDescriptionProvider) {
		this.persistentAttribute = persistentAttribute;
		this.column = column;
		this.textRangeResolver = textRangeResolver;
		this.tableDescriptionProvider = tableDescriptionProvider;
		this.tableValidator = this.buildTableValidator();
	}

	protected TableValidator buildTableValidator() {
		return TableValidator.Null.instance();
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
		return this.columnParentIsVirtualAttribute() ?
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
					this.persistentAttribute.getName(),
					this.column.getName(),
					this.column.getDbTable().getName()
				},
				this.column,
				this.textRangeResolver.getNameTextRange()
			);
	}

	protected abstract String getVirtualAttributeUnresolvedNameMessage();

	public boolean columnParentIsVirtualAttribute() {
		return (this.persistentAttribute != null) &&
				this.persistentAttribute.isVirtual();
	}


	// ********** table validator **********

	public interface TableValidator
		extends JptValidator
	{
		boolean tableNameIsInvalid();

		final class Null
			implements TableValidator
		{
			private static final TableValidator INSTANCE = new Null();
			public static TableValidator instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			public boolean validate(List<IMessage> messages, IReporter reporter) {
				return true;
			}
			public boolean tableNameIsInvalid() {
				return false;
			}
			@Override
			public String toString() {
				return StringTools.buildToStringClassName(this.getClass());
			}
		}
	}

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
				return StringTools.buildToStringClassName(this.getClass());
			}
		}
	}

	protected class BaseColumnTableValidator
		implements TableValidator
	{
		protected BaseColumnTableValidator() {
			super();
		}

		protected BaseColumn getColumn() {
			return (BaseColumn) AbstractNamedColumnValidator.this.column;
		}

		protected BaseColumnTextRangeResolver getTextRangeResolver() {
			return (BaseColumnTextRangeResolver) AbstractNamedColumnValidator.this.textRangeResolver;
		}

		public boolean tableNameIsInvalid() {
			return this.getColumn().tableNameIsInvalid();
		}
	
		public boolean validate(List<IMessage> messages, IReporter reporter) {
			messages.add(this.buildTableNotValidMessage());
			return false;
		}
	
		protected IMessage buildTableNotValidMessage() {
			if (AbstractNamedColumnValidator.this.columnParentIsVirtualAttribute()) {
				return this.buildVirtualAttributeTableNotValidMessage();
			}
			return DefaultJpaValidationMessages.buildMessage(
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
	
		protected String getColumnTableNotValidMessage() {
			return JpaValidationMessages.COLUMN_TABLE_NOT_VALID;
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
						this.getColumn().getTable(),
						this.getColumn().getName(),
						this.getColumnTableDescriptionMessage()
					},
					this.getColumn(),
					this.getTextRangeResolver().getTableTextRange()
				);
		}
	
		protected String getVirtualAttributeColumnTableNotValidMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_TABLE_NOT_VALID;
		}
	}
}
