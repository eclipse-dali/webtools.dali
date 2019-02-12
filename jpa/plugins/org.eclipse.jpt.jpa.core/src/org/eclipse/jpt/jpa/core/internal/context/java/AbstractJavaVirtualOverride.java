/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.TableColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualOverride;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Virtual Java override
 */
public abstract class AbstractJavaVirtualOverride<P extends JavaOverrideContainer>
	extends AbstractJavaContextModel<P>
	implements JavaVirtualOverride
{
	protected final String name;  // never null


	protected AbstractJavaVirtualOverride(P parent, String name) {
		super(parent);
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public boolean isVirtual() {
		return true;
	}

	public JavaSpecifiedOverride convertToSpecified() {
		return this.getContainer().convertOverrideToSpecified(this);
	}

	protected P getContainer() {
		return this.parent;
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

	public JpaValidator buildColumnValidator(BaseColumn column, TableColumn.ParentAdapter parentAdapter) {
		return this.getContainer().buildColumnValidator(this, column, parentAdapter);
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

	protected JpaValidator buildValidator() {
		return this.getContainer().buildOverrideValidator(this);
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

	public Iterable<String> getCandidateTableNames() {
		return this.getContainer().getCandidateTableNames();
	}
}
