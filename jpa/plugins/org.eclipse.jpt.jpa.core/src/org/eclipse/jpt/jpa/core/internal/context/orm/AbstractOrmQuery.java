/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.QueryHint;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryHint;
import org.eclipse.jpt.jpa.core.context.orm.OrmQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> query
 */
public abstract class AbstractOrmQuery<X extends XmlQuery>
	extends AbstractOrmXmlContextModel
	implements OrmQuery
{
	protected final X xmlQuery;

	protected String name;

	protected final ContextListContainer<OrmQueryHint, XmlQueryHint> hintContainer;


	protected AbstractOrmQuery(JpaContextModel parent, X xmlQuery) {
		super(parent);
		this.xmlQuery = xmlQuery;
		this.name = xmlQuery.getName();
		this.hintContainer = this.buildHintContainer();
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.xmlQuery.getName());
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
		this.setName_(name);
		this.xmlQuery.setName(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** hints **********

	public ListIterable<OrmQueryHint> getHints() {
		return this.hintContainer.getContextElements();
	}

	public int getHintsSize() {
		return this.hintContainer.getContextElementsSize();
	}

	public OrmQueryHint addHint() {
		return this.addHint(this.getHintsSize());
	}

	public OrmQueryHint addHint(int index) {
		XmlQueryHint xmlHint = this.buildXmlQueryHint();
		OrmQueryHint hint = this.hintContainer.addContextElement(index, xmlHint);
		this.xmlQuery.getHints().add(index, xmlHint);
		return hint;
	}

	protected XmlQueryHint buildXmlQueryHint() {
		return OrmFactory.eINSTANCE.createXmlQueryHint();
	}

	public void removeHint(QueryHint hint) {
		this.removeHint(this.hintContainer.indexOfContextElement((OrmQueryHint) hint));
	}

	public void removeHint(int index) {
		this.hintContainer.removeContextElement(index);
		this.xmlQuery.getHints().remove(index);
	}

	public void moveHint(int targetIndex, int sourceIndex) {
		this.hintContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlQuery.getHints().move(targetIndex, sourceIndex);
	}

	public OrmQueryHint getHint(int index) {
		return this.hintContainer.get(index);
	}

	protected OrmQueryHint buildHint(XmlQueryHint xmlHint) {
		return this.getContextNodeFactory().buildOrmQueryHint(this, xmlHint);
	}

	protected void syncHints() {
		this.hintContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlQueryHint> getXmlHints() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlQuery.getHints());
	}

	protected ContextListContainer<OrmQueryHint, XmlQueryHint> buildHintContainer() {
		HintContainer container = new HintContainer();
		container.initialize();
		return container;
	}

	/**
	 * query hint container
	 */
	protected class HintContainer
		extends ContextListContainer<OrmQueryHint, XmlQueryHint>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return HINTS_LIST;
		}
		@Override
		protected OrmQueryHint buildContextElement(XmlQueryHint resourceElement) {
			return AbstractOrmQuery.this.buildHint(resourceElement);
		}
		@Override
		protected ListIterable<XmlQueryHint> getResourceElements() {
			return AbstractOrmQuery.this.getXmlHints();
		}
		@Override
		protected XmlQueryHint getResourceElement(OrmQueryHint contextElement) {
			return contextElement.getXmlQueryHint();
		}
	}


	// ********** metadata conversion **********

	public void convertFrom(JavaQuery javaQuery) {
		this.setName(javaQuery.getName());
		for (JavaQueryHint javaQueryHint : javaQuery.getHints()) {
			this.addHint().convertFrom(javaQueryHint);
		}
	}

	// ********** validation **********

	public boolean supportsValidationMessages() {
		return true;
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
		TextRange textRange = this.xmlQuery.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.xmlQuery.getNameTextRange());
	}

	public boolean isEquivalentTo(JpaNamedContextModel node) {
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
		ArrayList<OrmQueryHint> hints1 = ListTools.list(this.getHints());
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

	public X getXmlQuery() {
		return this.xmlQuery;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
