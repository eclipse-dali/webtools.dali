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
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.AbstractNamedColumnValidator.TableValidator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class BaseColumnTableValidator implements TableValidator
{

	protected final PersistentAttribute persistentAttribute;

	protected final BaseColumn column;

	protected final BaseColumnTextRangeResolver textRangeResolver;

	protected final TableDescriptionProvider tableDescriptionProvider;

	protected BaseColumnTableValidator(
		PersistentAttribute persistentAttribute, BaseColumn column, BaseColumnTextRangeResolver textRangeResolver, TableDescriptionProvider tableDescriptionProvider) {
		super();
		this.persistentAttribute = persistentAttribute;
		this.column = column;
		this.textRangeResolver = textRangeResolver;
		this.tableDescriptionProvider = tableDescriptionProvider;
	}

	public BaseColumn getColumn() {
		return this.column;
	}

	public BaseColumnTextRangeResolver getTextRangeResolver() {
		return this.textRangeResolver;
	}

	protected boolean isPersistentAttributeVirtual() {
		return this.persistentAttribute != null && this.persistentAttribute.isVirtual();
	}

	protected String getPersistentAttributeName() {
		return this.persistentAttribute.getName();
	}

	public boolean isTableNameInvalid() {
		return this.getColumn().tableNameIsInvalid();
	}
	
	public boolean validate(List<IMessage> messages, IReporter reporter) {
		messages.add(buildTableNotValidMessage());
		return false;
	}

	public IMessage buildTableNotValidMessage() {
		if (isPersistentAttributeVirtual()) {
			return buildVirtualAttributeTableNotValidMessage();
		}
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getColumnTableNotValidMessage(),
			new String[] {
				getColumn().getTable(),
				getColumn().getName(),
				this.getColumnTableDescriptionMessage()}, 
			getColumn(),
			getTextRangeResolver().getTableTextRange()
		);
	}
	
	protected String getColumnTableNotValidMessage() {
		return JpaValidationMessages.COLUMN_TABLE_NOT_VALID;
	}

	protected String getColumnTableDescriptionMessage()  {
		return this.tableDescriptionProvider.getColumnTableDescriptionMessage();
	}

	protected IMessage buildVirtualAttributeTableNotValidMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getVirtualAttributeColumnTableNotValidMessage(),
			new String[] {
				getPersistentAttributeName(),
				getColumn().getTable(),
				getColumn().getName(),
				this.getColumnTableDescriptionMessage()},
			getColumn(), 
			getTextRangeResolver().getTableTextRange()
		);
	}
	
	protected String getVirtualAttributeColumnTableNotValidMessage() {
		return JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_TABLE_NOT_VALID;
	}


	public static interface TableDescriptionProvider {
		String getColumnTableDescriptionMessage();
	}
	
	public static class NullTableDescriptionProvider implements TableDescriptionProvider {
		public String getColumnTableDescriptionMessage() {
			throw new UnsupportedOperationException();
		}
	}
}
