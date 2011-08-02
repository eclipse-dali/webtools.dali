/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn.Owner;
import org.eclipse.jpt.jpa.core.context.ReadOnlyOverride;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualOverride;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.resource.java.OverrideAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Specified Java override
 */
public abstract class AbstractJavaOverride<C extends JavaOverrideContainer, A extends OverrideAnnotation>
	extends AbstractJavaJpaContextNode
	implements JavaOverride
{
	protected final A overrideAnnotation;

	protected String name;


	protected AbstractJavaOverride(C parent, A overrideAnnotation) {
		super(parent);
		this.overrideAnnotation = overrideAnnotation;
		this.name = this.buildName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.buildName());
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	/**
	 * Strip the prefix (if necessary) before storing the name in the context
	 * model. Pass the unchanged name to the annotation.
	 */
	public void setName(String name) {
		this.overrideAnnotation.setName(name);
		this.setName_(this.stripPrefix(name));
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	/**
	 * Strip the prefix (if necessary) from the name taken from the annotation
	 * before storing it in the context model.
	 */
	protected String buildName() {
		return this.stripPrefix(this.overrideAnnotation.getName());
	}

	protected String stripPrefix(String rawName) {
		if (rawName == null) {
			return null;
		}
		String prefix = this.getPossiblePrefix();
		if (prefix == null) {
			return rawName;
		}
		int prefixLength = prefix.length();
		if ((rawName.length() > prefixLength) &&
				(rawName.charAt(prefixLength) == '.') &&
				rawName.startsWith(prefix)) {
			return rawName.substring(prefixLength + 1);
		}
		return rawName;
	}

	protected String getPossiblePrefix() {
		return this.isJpa2_0Compatible() ? this.getContainer2_0().getPossiblePrefix() : null;
	}


	// ********** specified/virtual **********

	public boolean isVirtual() {
		return false;
	}

	public JavaVirtualOverride convertToVirtual() {
		return this.getContainer().convertOverrideToVirtual(this);
	}


	// ********** misc **********

	@Override
	@SuppressWarnings("unchecked")
	public C getParent() {
		return (C) super.getParent();
	}

	protected C getContainer() {
		return this.getParent();
	}

	protected JavaOverrideContainer2_0 getContainer2_0() {
		return (JavaOverrideContainer2_0) this.getContainer();
	}

	public A getOverrideAnnotation() {
		return this.overrideAnnotation;
	}

	protected void initializeFrom(ReadOnlyOverride oldOverride) {
		this.setName(this.prefix(oldOverride.getName()));
	}

	protected void initializeFromVirtual(ReadOnlyOverride virtualOverride) {
		this.setName(this.prefix(virtualOverride.getName()));
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

	protected String prefix(String oldName) {
		if (oldName == null) {
			return null;
		}
		String prefix = this.getWritePrefix();
		return (prefix == null) ? oldName : (prefix + '.' + oldName);
	}

	protected String getWritePrefix() {
		return this.isJpa2_0Compatible() ? this.getContainer2_0().getWritePrefix() : null;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos, astRoot)) {
			return this.getJavaCandidateNames(filter);
		}
		return null;
	}

	protected boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.overrideAnnotation.nameTouches(pos, astRoot);
	}

	protected Iterable<String> getJavaCandidateNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateNames(filter));
	}

	private Iterable<String> getCandidateNames(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateNames(), filter);
	}

	protected abstract Iterable<String> getCandidateNames();


	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.buildValidator(astRoot).validate(messages, reporter);
	}

	protected JptValidator buildValidator(CompilationUnit astRoot) {
		return this.getContainer().buildOverrideValidator(this, buildTextRangeResolver(astRoot));
	}

	protected OverrideTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaOverrideTextRangeResolver(this, astRoot);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.overrideAnnotation.getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getContainer().getValidationTextRange(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.overrideAnnotation.getNameTextRange(astRoot), astRoot);
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getContainer().tableNameIsInvalid(tableName);
	}

	public Iterable<String> getCandidateTableNames() {
		return this.getContainer().getCandidateTableNames();
	}
}
