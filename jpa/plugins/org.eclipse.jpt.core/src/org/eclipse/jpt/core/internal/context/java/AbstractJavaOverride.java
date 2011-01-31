/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.ReadOnlyOverride;
import org.eclipse.jpt.core.context.java.JavaOverride;
import org.eclipse.jpt.core.context.java.JavaOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaVirtualOverride;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.core.resource.java.OverrideAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
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
		return this.getContainer().getPossiblePrefix();
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

	public C getContainer() {
		return this.getParent();
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

	protected String prefix(String oldName) {
		if (oldName == null) {
			return null;
		}
		String prefix = this.getWritePrefix();
		return (prefix == null) ? oldName : (prefix + '.' + oldName);
	}

	protected String getWritePrefix() {
		return this.getContainer().getWritePrefix();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos, astRoot)) {
			return this.javaCandidateNames(filter);
		}
		return null;
	}

	protected boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.overrideAnnotation.nameTouches(pos, astRoot);
	}

	protected Iterator<String> javaCandidateNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateNames(filter));
	}

	private Iterator<String> candidateNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateNames(), filter);
	}

	protected abstract Iterator<String> candidateNames();


	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.buildValidator(astRoot).validate(messages, reporter);
	}

	protected JptValidator buildValidator(CompilationUnit astRoot) {
		return this.getContainer().buildValidator(this, buildTextRangeResolver(astRoot));
	}

	protected OverrideTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaOverrideTextRangeResolver(this, astRoot);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.overrideAnnotation.getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.overrideAnnotation.getNameTextRange(astRoot);
		return (textRange != null) ? textRange : this.getValidationTextRange(astRoot);
	}
}
