/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
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
public abstract class AbstractJavaQuery<P extends JavaQueryContainer, A extends QueryAnnotation>
	extends AbstractJavaContextModel<P>
	implements JavaQuery
{
	protected final A queryAnnotation;

	protected String name;

	protected final ContextListContainer<JavaQueryHint, QueryHintAnnotation> hintContainer;


	protected AbstractJavaQuery(P parent, A queryAnnotation) {
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
		this.updateModels(this.getHints());
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
		return this.hintContainer;
	}

	public int getHintsSize() {
		return this.hintContainer.size();
	}

	public JavaQueryHint addHint() {
		return this.addHint(this.getHintsSize());
	}

	public JavaQueryHint addHint(int index) {
		QueryHintAnnotation annotation = this.queryAnnotation.addHint(index);
		return this.hintContainer.addContextElement(index, annotation);
	}

	public void removeHint(QueryHint hint) {
		this.removeHint(this.hintContainer.indexOf((JavaQueryHint) hint));
	}

	public void removeHint(int index) {
		this.queryAnnotation.removeHint(index);
		this.hintContainer.remove(index);
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		this.queryAnnotation.moveHint(targetIndex, sourceIndex);
		this.hintContainer.move(targetIndex, sourceIndex);
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
		return this.buildSpecifiedContextListContainer(HINTS_LIST, new HintContainerAdapter());
	}

	/**
	 * hint container adapter
	 */
	public class HintContainerAdapter
		extends AbstractContainerAdapter<JavaQueryHint, QueryHintAnnotation>
	{
		public JavaQueryHint buildContextElement(QueryHintAnnotation resourceElement) {
			return AbstractJavaQuery.this.buildHint(resourceElement);
		}
		public ListIterable<QueryHintAnnotation> getResourceElements() {
			return AbstractJavaQuery.this.getHintAnnotations();
		}
		public QueryHintAnnotation extractResourceElement(JavaQueryHint contextElement) {
			return contextElement.getQueryHintAnnotation();
		}
	}

	// ********** validation **********

	public boolean supportsValidationMessages() {
		return MappingTools.modelIsInternalSource(this, this.getQueryAnnotation());
	}

	public void validate(JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateName(messages);
	}

	protected void validateName(List<IMessage> messages) {
		if (StringTools.isBlank(this.name)) {
			messages.add(
				this.buildValidationMessage(
					this.getNameTextRange(),
					JptJpaCoreValidationMessages.QUERY_NAME_UNDEFINED
				)
			);
		}
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.queryAnnotation.getTextRange();
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.queryAnnotation.getNameTextRange());
	}

	public boolean isEquivalentTo(Query other) {
		return (this != other) &&
				(this.getQueryType() == other.getQueryType()) &&
				this.isEquivalentTo_(other);
	}

	protected boolean isEquivalentTo_(Query other) {
		return ObjectTools.equals(this.name, other.getName()) &&
				this.hintsAreEquivalentTo(other);
	}

	protected boolean hintsAreEquivalentTo(Query other) {
		// get fixed lists of the hints
		ArrayList<JavaQueryHint> hints1 = ListTools.arrayList(this.getHints());
		ArrayList<? extends QueryHint> hints2 = ListTools.arrayList(other.getHints());
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

	public A getQueryAnnotation() {
		return this.queryAnnotation;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
