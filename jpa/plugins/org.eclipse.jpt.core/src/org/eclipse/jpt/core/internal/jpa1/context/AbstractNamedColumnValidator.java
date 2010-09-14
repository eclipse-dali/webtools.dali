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
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.BaseColumnTableValidator.NullTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.BaseColumnTableValidator.TableDescriptionProvider;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractNamedColumnValidator
	implements JptValidator
{

	protected final PersistentAttribute persistentAttribute;

	protected final NamedColumn namedColumn;

	protected final NamedColumnTextRangeResolver textRangeResolver;

	protected final TableValidator tableValidator;

	protected final TableDescriptionProvider tableDescriptionProvider;

	protected AbstractNamedColumnValidator(
				NamedColumn column,
				NamedColumnTextRangeResolver textRangeResolver) {
		this(null, column, textRangeResolver);
	}

	protected AbstractNamedColumnValidator(
				NamedColumn column,
				NamedColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
		this(null, column, textRangeResolver, provider);
	}

	protected AbstractNamedColumnValidator(
				PersistentAttribute persistentAttribute,
				NamedColumn column,
				NamedColumnTextRangeResolver textRangeResolver) {
		this(persistentAttribute, column, textRangeResolver, new NullTableDescriptionProvider());
	}

	protected AbstractNamedColumnValidator(
				PersistentAttribute persistentAttribute,
				NamedColumn column,
				NamedColumnTextRangeResolver textRangeResolver,
				TableDescriptionProvider provider) {
		this.persistentAttribute = persistentAttribute;
		this.namedColumn = column;
		this.textRangeResolver = textRangeResolver;
		this.tableDescriptionProvider = provider;
		this.tableValidator = this.buildTableValidator();
	}

	protected TableValidator buildTableValidator() {
		return new NullTableValidator();
	}

	public NamedColumn getColumn() {
		return this.namedColumn;
	}

	public NamedColumnTextRangeResolver getTextRangeResolver() {
		return this.textRangeResolver;
	}

	protected boolean isPersistentAttributeVirtual() {
		return this.persistentAttribute != null && this.persistentAttribute.isVirtual();
	}

	protected String getPersistentAttributeName() {
		return this.persistentAttribute.getName();
	}

	public boolean validate(List<IMessage> messages, IReporter reporter) {
		if (this.tableValidator.isTableNameInvalid()) {
			return this.tableValidator.validate(messages, reporter);
		}
		this.validateName(messages);
		return true;
	}

	protected void validateName(List<IMessage> messages) {
		Table dbTable = this.namedColumn.getDbTable();
		if (dbTable != null && !this.namedColumn.isResolved()) {
			messages.add(this.buildUnresolvedNameMessage());
		}
	}

	protected IMessage buildUnresolvedNameMessage() {
		if (isPersistentAttributeVirtual()) {
			return this.buildVirtualAttributeUnresolvedNameMessage();
		}
		return this.buildUnresolvedNameMessage(this.getUnresolvedNameMessage());
	}

	protected IMessage buildUnresolvedNameMessage(String message) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			message,
			new String[] {this.namedColumn.getName(), this.namedColumn.getDbTable().getName()}, 
			this.namedColumn,
			this.textRangeResolver.getNameTextRange()
		);
	}

	protected abstract String getUnresolvedNameMessage();

	protected IMessage buildVirtualAttributeUnresolvedNameMessage() {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			this.getVirtualAttributeUnresolvedNameMessage(),
			new String[] {this.getPersistentAttributeName(), this.namedColumn.getName(), this.namedColumn.getDbTable().getName()},
			this.namedColumn, 
			this.textRangeResolver.getNameTextRange()
		);
	}

	protected abstract String getVirtualAttributeUnresolvedNameMessage();

	public interface TableValidator extends JptValidator {
		boolean isTableNameInvalid();
	}
	
	public static class NullTableValidator implements TableValidator {
		public boolean validate(List<IMessage> messages, IReporter reporter) {
			return true;
		}
		public boolean isTableNameInvalid() {
			return false;
		}
	}
}
