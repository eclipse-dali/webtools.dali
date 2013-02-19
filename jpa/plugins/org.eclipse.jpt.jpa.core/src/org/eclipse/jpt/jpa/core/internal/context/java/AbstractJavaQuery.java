/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.QueryHint;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryHint;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.java.QueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
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

	protected final ContextListContainer<JavaQueryHint, QueryHintAnnotation> hintContainer;


	protected AbstractJavaQuery(JpaContextNode parent, A queryAnnotation) {
		super(parent);
		this.queryAnnotation = queryAnnotation;
		this.name = queryAnnotation.getName();
		this.hintContainer = this.buildHintContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.queryAnnotation.getName());
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


	// ********** hints **********

	public ListIterable<JavaQueryHint> getHints() {
		return this.hintContainer.getContextElements();
	}

	public int getHintsSize() {
		return this.hintContainer.getContextElementsSize();
	}

	public JavaQueryHint addHint() {
		return this.addHint(this.getHintsSize());
	}

	public JavaQueryHint addHint(int index) {
		QueryHintAnnotation annotation = this.queryAnnotation.addHint(index);
		return this.hintContainer.addContextElement(index, annotation);
	}

	public void removeHint(QueryHint hint) {
		this.removeHint(this.hintContainer.indexOfContextElement((JavaQueryHint) hint));
	}

	public void removeHint(int index) {
		this.queryAnnotation.removeHint(index);
		this.hintContainer.removeContextElement(index);
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		this.queryAnnotation.moveHint(targetIndex, sourceIndex);
		this.hintContainer.moveContextElement(targetIndex, sourceIndex);
	}

	public JavaQueryHint getHint(int index) {
		return this.hintContainer.get(index);
	}

	protected JavaQueryHint buildHint(QueryHintAnnotation hintAnnotation) {
		return this.getJpaFactory().buildJavaQueryHint(this, hintAnnotation);
	}

	protected void syncHints() {
		this.hintContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<QueryHintAnnotation> getHintAnnotations() {
		return this.queryAnnotation.getHints();
	}

	protected ContextListContainer<JavaQueryHint, QueryHintAnnotation> buildHintContainer() {
		HintContainer container = new HintContainer();
		container.initialize();
		return container;
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

	public boolean supportsValidationMessages() {
		return MappingTools.nodeIsInternalSource(this, this.getQueryAnnotation());
	}

	public void validate(JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateName(messages);
	}

	protected void validateName(List<IMessage> messages) {
		if (StringTools.isBlank(this.name)) {
			messages.add(
				this.buildErrorValidationMessage(
					JptJpaCoreValidationMessages.QUERY_NAME_UNDEFINED,
					this.getNameTextRange()
				)
			);
		}
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.queryAnnotation.getTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.queryAnnotation.getNameTextRange());
	}

	public boolean isEquivalentTo(JpaNamedContextNode node) {
		return (this != node) &&
				(this.getType() == node.getType()) &&
				this.isEquivalentTo((Query) node);
	}

	protected boolean isEquivalentTo(Query other) {
		return ObjectTools.equals(this.name, other.getName()) &&
				this.hintsAreEquivalentTo(other);
	}

	protected boolean hintsAreEquivalentTo(Query other) {
		// get fixed lists of the hints
		ArrayList<JavaQueryHint> hints1 = ListTools.list(this.getHints());
		ArrayList<? extends QueryHint> hints2 = ListTools.list(other.getHints());
		if (hints1.size() != hints2.size()) {
			return false;
		}
		for (int i = 0; i < hints1.size(); i++) {
			if ( ! hints1.get(i).isEquivalentTo(hints2.get(i))) {
				return false;
			}
		}
		return true;
	}


	// ********** misc **********

	@Override
	public JavaQueryContainer getParent() {
		return (JavaQueryContainer) super.getParent();
	}

	public A getQueryAnnotation() {
		return this.queryAnnotation;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
