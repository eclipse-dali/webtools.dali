/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn.Owner;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualOverride;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Virtual Java override
 */
public abstract class AbstractJavaVirtualOverride<C extends JavaOverrideContainer>
	extends AbstractJavaJpaContextNode
	implements JavaVirtualOverride
{
	protected final String name;  // never null


	protected AbstractJavaVirtualOverride(C parent, String name) {
		super(parent);
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public boolean isVirtual() {
		return true;
	}

	public JavaOverride convertToSpecified() {
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
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.buildValidator(astRoot).validate(messages, reporter);
	}

	protected JptValidator buildValidator(CompilationUnit astRoot) {
		return this.getContainer().buildOverrideValidator(this, this.buildTextRangeResolver(astRoot));
	}

	protected OverrideTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaOverrideTextRangeResolver(this, astRoot);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getContainer().getValidationTextRange(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(astRoot);
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getContainer().tableNameIsInvalid(tableName);
	}

	public Iterable<String> getCandidateTableNames() {
		return this.getContainer().getCandidateTableNames();
	}
}
