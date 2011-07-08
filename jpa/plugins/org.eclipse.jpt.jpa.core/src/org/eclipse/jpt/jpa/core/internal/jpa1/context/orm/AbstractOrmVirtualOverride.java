/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn.Owner;
import org.eclipse.jpt.jpa.core.context.orm.OrmOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualOverride;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmOverrideTextRangeResolver;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Virtual <code>orm.xml</code> override
 */
public abstract class AbstractOrmVirtualOverride<C extends OrmOverrideContainer>
	extends AbstractOrmXmlContextNode
	implements OrmVirtualOverride
{
	protected final String name;  // never null


	protected AbstractOrmVirtualOverride(C parent, String name) {
		super(parent);
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public boolean isVirtual() {
		return true;
	}

	public OrmOverride convertToSpecified() {
		return this.getContainer().convertOverrideToSpecified(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public C getParent() {
		return (C) super.getParent();
	}

	protected C getContainer() {
		return this.getParent();
	}

	public TypeMapping getTypeMapping() {
		return this.getContainer().getTypeMapping();
	}

	public Table resolveDbTable(String tableName) {
		return this.getContainer().resolveDbTable(tableName);
	}
	
	public String getDefaultTableName() {
		return this.getContainer().getDefaultTableName();
	}

	public JptValidator buildColumnValidator(ReadOnlyBaseColumn column, Owner owner, BaseColumnTextRangeResolver textRangeResolver) {
		return this.getContainer().buildColumnValidator(this, column, owner, textRangeResolver);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.buildValidator().validate(messages, reporter);
	}

	protected JptValidator buildValidator() {
		return this.getContainer().buildOverrideValidator(this, this.buildTextRangeResolver());
	}

	protected OverrideTextRangeResolver buildTextRangeResolver() {
		return new OrmOverrideTextRangeResolver(this);
	}

	public TextRange getValidationTextRange() {
		return this.getContainer().getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getContainer().tableNameIsInvalid(tableName);
	}

	public Iterator<String> candidateTableNames() {
		return this.getContainer().candidateTableNames();
	}
}
