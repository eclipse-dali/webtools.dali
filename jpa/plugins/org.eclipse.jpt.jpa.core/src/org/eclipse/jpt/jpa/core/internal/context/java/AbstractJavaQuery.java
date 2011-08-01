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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.QueryHint;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryHint;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.java.QueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java query
 */
public abstract class AbstractJavaQuery<A extends QueryAnnotation>
	extends AbstractJavaJpaContextNode
	implements JavaQuery
{
	protected final A queryAnnotation;

	protected String name;

	protected String query;

	protected final ContextListContainer<JavaQueryHint, QueryHintAnnotation> queryHintContainer;


	protected AbstractJavaQuery(JavaJpaContextNode parent, A queryAnnotation) {
		super(parent);
		this.queryAnnotation = queryAnnotation;
		this.name = queryAnnotation.getName();
		this.query = queryAnnotation.getQuery();
		this.queryHintContainer = this.buildHintContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.queryAnnotation.getName());
		this.setQuery_(this.queryAnnotation.getQuery());
		this.syncHints();
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getHints());
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.queryAnnotation.setName(name);
		this.setName_(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** query **********

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.queryAnnotation.setQuery(query);
		this.setQuery_(query);
	}

	protected void setQuery_(String query) {
		String old = this.query;
		this.query = query;
		this.firePropertyChanged(QUERY_PROPERTY, old, query);
	}


	// ********** hints **********

	public ListIterable<JavaQueryHint> getHints() {
		return this.queryHintContainer.getContextElements();
	}

	public int getHintsSize() {
		return this.queryHintContainer.getContextElementsSize();
	}

	public JavaQueryHint addHint() {
		return this.addHint(this.getHintsSize());
	}

	public JavaQueryHint addHint(int index) {
		QueryHintAnnotation annotation = this.queryAnnotation.addHint(index);
		return this.queryHintContainer.addContextElement(index, annotation);
	}

	public void removeHint(QueryHint hint) {
		this.removeHint(this.queryHintContainer.indexOfContextElement((JavaQueryHint) hint));
	}

	public void removeHint(int index) {
		this.queryAnnotation.removeHint(index);
		this.queryHintContainer.removeContextElement(index);
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		this.queryAnnotation.moveHint(targetIndex, sourceIndex);
		this.queryHintContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaQueryHint buildHint(QueryHintAnnotation hintAnnotation) {
		return this.getJpaFactory().buildJavaQueryHint(this, hintAnnotation);
	}

	protected void syncHints() {
		this.queryHintContainer.synchronizeWithResourceModel();
	}

	protected void updateHints() {
		this.queryHintContainer.update();
	}

	protected ListIterable<QueryHintAnnotation> getHintAnnotations() {
		return this.queryAnnotation.getHints();
	}

	protected ContextListContainer<JavaQueryHint, QueryHintAnnotation> buildHintContainer() {
		return new HintContainer();
	}

	/**
	 * query hint container
	 */
	protected class HintContainer
		extends ContextListContainer<JavaQueryHint, QueryHintAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return HINTS_LIST;
		}
		@Override
		protected JavaQueryHint buildContextElement(QueryHintAnnotation resourceElement) {
			return AbstractJavaQuery.this.buildHint(resourceElement);
		}
		@Override
		protected ListIterable<QueryHintAnnotation> getResourceElements() {
			return AbstractJavaQuery.this.getHintAnnotations();
		}
		@Override
		protected QueryHintAnnotation getResourceElement(JavaQueryHint contextElement) {
			return contextElement.getQueryHintAnnotation();
		}
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateName(messages, astRoot);
		this.validateQuery(messages, reporter, astRoot);
	}

	protected void validateName(List<IMessage> messages, CompilationUnit astRoot) {
		if (StringTools.stringIsEmpty(this.name)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.QUERY_NAME_UNDEFINED,
					EMPTY_STRING_ARRAY,
					this,
					this.getNameTextRange(astRoot)
				)
			);
		}
	}

	protected void validateQuery(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (StringTools.stringIsEmpty(this.query)){
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.QUERY_STATEMENT_UNDEFINED,
					new String[] {this.name},
					this,
					this.getNameTextRange(astRoot)
				)
			);
		} else {
			this.validateQuery_(messages, reporter, astRoot);
		}
	}

	protected abstract void validateQuery_(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot);

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.queryAnnotation.getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.queryAnnotation.getNameTextRange(astRoot), astRoot);
	}


	// ********** misc **********

	@Override
	public JavaJpaContextNode getParent() {
		return (JavaJpaContextNode) super.getParent();
	}

	public A getQueryAnnotation() {
		return this.queryAnnotation;
	}

	public boolean overrides(Query other) {
		return MappingTools.nodeOverrides(this, other, PRECEDENCE_TYPE_LIST);
	}

	public boolean duplicates(Query other) {
		return MappingTools.nodesAreDuplicates(this, other);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
