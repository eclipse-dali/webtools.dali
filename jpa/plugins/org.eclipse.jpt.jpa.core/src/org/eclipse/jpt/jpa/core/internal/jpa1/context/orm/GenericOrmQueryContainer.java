/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.internal.resource.xml.EFactoryTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmQueryContainer2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.NamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlQueryContainer;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> query container
 */
public class GenericOrmQueryContainer
	extends AbstractOrmXmlContextModel<JpaContextModel>
	implements OrmQueryContainer2_1
{
	protected final XmlQueryContainer xmlQueryContainer;

	protected final ContextListContainer<OrmNamedQuery, XmlNamedQuery> namedQueryContainer;
	protected final ContextListContainer<OrmNamedNativeQuery, XmlNamedNativeQuery> namedNativeQueryContainer;
	protected final ContextListContainer<OrmNamedStoredProcedureQuery2_1, XmlNamedStoredProcedureQuery> namedStoredProcedureQueryContainer;


	public GenericOrmQueryContainer(JpaContextModel parent, XmlQueryContainer xmlQueryContainer) {
		super(parent);
		this.xmlQueryContainer = xmlQueryContainer;
		this.namedQueryContainer = this.buildNamedQueryContainer();
		this.namedNativeQueryContainer = this.buildNamedNativeQueryContainer();
		this.namedStoredProcedureQueryContainer = this.buildNamedStoredProcedureQueryContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncNamedQueries();
		this.syncNamedNativeQueries();
		this.syncNamedStoredProcedureQueries();
	}

	@Override
	public void update() {
		super.update();
		this.updateModels(this.getNamedQueries());
		this.updateModels(this.getNamedNativeQueries());
		this.updateModels(this.getNamedStoredProcedureQueries());
	}


	// ********** queries **********

	@SuppressWarnings("unchecked")
	public Iterable<Query> getQueries() {
		return IterableTools.<Query>concatenate(
				this.getNamedQueries(), 
				this.getNamedNativeQueries(),
				this.getNamedStoredProcedureQueries());
	}


	// ********** named queries **********

	public ListIterable<OrmNamedQuery> getNamedQueries() {
		return this.namedQueryContainer.getContextElements();
	}

	public int getNamedQueriesSize() {
		return this.namedQueryContainer.getContextElementsSize();
	}

	public OrmNamedQuery addNamedQuery() {
		return this.addNamedQuery(this.getNamedQueriesSize());
	}

	public OrmNamedQuery addNamedQuery(int index) {
		XmlNamedQuery xmlQuery = this.buildXmlNamedQuery();
		OrmNamedQuery query = this.namedQueryContainer.addContextElement(index, xmlQuery);
		this.xmlQueryContainer.getNamedQueries().add(index, xmlQuery);
		return query;
	}

	protected XmlNamedQuery buildXmlNamedQuery() {
		return OrmFactory.eINSTANCE.createXmlNamedQuery();
	}

	public void removeNamedQuery(NamedQuery namedQuery) {
		this.removeNamedQuery(this.namedQueryContainer.indexOfContextElement((OrmNamedQuery) namedQuery));
	}

	public void removeNamedQuery(int index) {
		this.namedQueryContainer.removeContextElement(index);
		this.xmlQueryContainer.getNamedQueries().remove(index);
	}

	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		this.namedQueryContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlQueryContainer.getNamedQueries().move(targetIndex, sourceIndex);
	}

	protected OrmNamedQuery buildNamedQuery(XmlNamedQuery xmlNamedQuery) {
		return this.getContextModelFactory().buildOrmNamedQuery(this, xmlNamedQuery);
	}

	protected void syncNamedQueries() {
		this.namedQueryContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlNamedQuery> getXmlNamedQueries() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlQueryContainer.getNamedQueries());
	}

	protected ContextListContainer<OrmNamedQuery, XmlNamedQuery> buildNamedQueryContainer() {
		NamedQueryContainer container = new NamedQueryContainer();
		container.initialize();
		return container;
	}

	/**
	 * named query container
	 */
	protected class NamedQueryContainer
		extends ContextListContainer<OrmNamedQuery, XmlNamedQuery>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return NAMED_QUERIES_LIST;
		}
		@Override
		protected OrmNamedQuery buildContextElement(XmlNamedQuery resourceElement) {
			return GenericOrmQueryContainer.this.buildNamedQuery(resourceElement);
		}
		@Override
		protected ListIterable<XmlNamedQuery> getResourceElements() {
			return GenericOrmQueryContainer.this.getXmlNamedQueries();
		}
		@Override
		protected XmlNamedQuery getResourceElement(OrmNamedQuery contextElement) {
			return contextElement.getXmlQuery();
		}
	}

	// ********** named native queries **********

	public ListIterable<OrmNamedNativeQuery> getNamedNativeQueries() {
		return this.namedNativeQueryContainer.getContextElements();
	}

	public int getNamedNativeQueriesSize() {
		return this.namedNativeQueryContainer.getContextElementsSize();
	}

	public OrmNamedNativeQuery addNamedNativeQuery() {
		return this.addNamedNativeQuery(this.getNamedNativeQueriesSize());
	}

	public OrmNamedNativeQuery addNamedNativeQuery(int index) {
		XmlNamedNativeQuery xmlQuery = this.buildXmlNamedNativeQuery();
		OrmNamedNativeQuery query = this.namedNativeQueryContainer.addContextElement(index, xmlQuery);
		this.xmlQueryContainer.getNamedNativeQueries().add(index, xmlQuery);
		return query;
	}

	protected XmlNamedNativeQuery buildXmlNamedNativeQuery() {
		return OrmFactory.eINSTANCE.createXmlNamedNativeQuery();
	}

	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueryContainer.indexOfContextElement((OrmNamedNativeQuery) namedNativeQuery));
	}

	public void removeNamedNativeQuery(int index) {
		this.namedNativeQueryContainer.removeContextElement(index);
		this.xmlQueryContainer.getNamedNativeQueries().remove(index);
	}

	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		this.namedNativeQueryContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlQueryContainer.getNamedNativeQueries().move(targetIndex, sourceIndex);
	}

	protected OrmNamedNativeQuery buildNamedNativeQuery(XmlNamedNativeQuery xmlNamedNativeQuery) {
		return this.getContextModelFactory().buildOrmNamedNativeQuery(this, xmlNamedNativeQuery);
	}

	protected void syncNamedNativeQueries() {
		this.namedNativeQueryContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlNamedNativeQuery> getXmlNamedNativeQueries() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlQueryContainer.getNamedNativeQueries());
	}

	protected ContextListContainer<OrmNamedNativeQuery, XmlNamedNativeQuery> buildNamedNativeQueryContainer() {
		NamedNativeQueryContainer container = new NamedNativeQueryContainer();
		container.initialize();
		return container;
	}

	/**
	 * named query container
	 */
	protected class NamedNativeQueryContainer
		extends ContextListContainer<OrmNamedNativeQuery, XmlNamedNativeQuery>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return NAMED_NATIVE_QUERIES_LIST;
		}
		@Override
		protected OrmNamedNativeQuery buildContextElement(XmlNamedNativeQuery resourceElement) {
			return GenericOrmQueryContainer.this.buildNamedNativeQuery(resourceElement);
		}
		@Override
		protected ListIterable<XmlNamedNativeQuery> getResourceElements() {
			return GenericOrmQueryContainer.this.getXmlNamedNativeQueries();
		}
		@Override
		protected XmlNamedNativeQuery getResourceElement(OrmNamedNativeQuery contextElement) {
			return contextElement.getXmlQuery();
		}
	}


	// ********** named stored procedure queries **********

	public ListIterable<OrmNamedStoredProcedureQuery2_1> getNamedStoredProcedureQueries() {
		return this.namedStoredProcedureQueryContainer.getContextElements();
	}

	public int getNamedStoredProcedureQueriesSize() {
		return this.namedStoredProcedureQueryContainer.getContextElementsSize();
	}

	public OrmNamedStoredProcedureQuery2_1 addNamedStoredProcedureQuery() {
		return this.addNamedStoredProcedureQuery(this.getNamedStoredProcedureQueriesSize());
	}

	public OrmNamedStoredProcedureQuery2_1 addNamedStoredProcedureQuery(int index) {
		XmlNamedStoredProcedureQuery xmlQuery = this.buildXmlNamedStoredProcedureQuery();
		OrmNamedStoredProcedureQuery2_1 query = this.namedStoredProcedureQueryContainer.addContextElement(index, xmlQuery);
		this.xmlQueryContainer.getNamedStoredProcedureQueries().add(index, xmlQuery);
		return query;
	}

	protected XmlNamedStoredProcedureQuery buildXmlNamedStoredProcedureQuery() {
		return EFactoryTools.create(
			this.getResourceModelFactory(), 
			OrmPackage.eINSTANCE.getXmlNamedStoredProcedureQuery(), 
			XmlNamedStoredProcedureQuery.class);
	}

	public void removeNamedStoredProcedureQuery(NamedStoredProcedureQuery2_1 namedQuery) {
		this.removeNamedStoredProcedureQuery(this.namedStoredProcedureQueryContainer.indexOfContextElement((OrmNamedStoredProcedureQuery2_1) namedQuery));
	}

	public void removeNamedStoredProcedureQuery(int index) {
		this.namedStoredProcedureQueryContainer.removeContextElement(index);
		this.xmlQueryContainer.getNamedStoredProcedureQueries().remove(index);
	}

	public void moveNamedStoredProcedureQuery(int targetIndex, int sourceIndex) {
		this.namedStoredProcedureQueryContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlQueryContainer.getNamedStoredProcedureQueries().move(targetIndex, sourceIndex);
	}

	protected OrmNamedStoredProcedureQuery2_1 buildNamedStoredProcedureQuery(XmlNamedStoredProcedureQuery xmlNamedQuery) {
		return this.isOrmXml2_1Compatible() ?
				this.getContextModelFactory2_1().buildOrmNamedStoredProcedureQuery(this, xmlNamedQuery) :
				null;
	}

	protected void syncNamedStoredProcedureQueries() {
		this.namedStoredProcedureQueryContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlNamedStoredProcedureQuery> getXmlNamedStoredProcedureQueries() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlQueryContainer.getNamedStoredProcedureQueries());
	}

	protected ContextListContainer<OrmNamedStoredProcedureQuery2_1, XmlNamedStoredProcedureQuery> buildNamedStoredProcedureQueryContainer() {
		NamedStoredProcedureQueryContainer container = new NamedStoredProcedureQueryContainer();
		container.initialize();
		return container;
	}

	/**
	 * named query container
	 */
	protected class NamedStoredProcedureQueryContainer
		extends ContextListContainer<OrmNamedStoredProcedureQuery2_1, XmlNamedStoredProcedureQuery>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return NAMED_STORED_PROCEDURE_QUERIES_LIST;
		}
		@Override
		protected OrmNamedStoredProcedureQuery2_1 buildContextElement(XmlNamedStoredProcedureQuery resourceElement) {
			return GenericOrmQueryContainer.this.buildNamedStoredProcedureQuery(resourceElement);
		}
		@Override
		protected ListIterable<XmlNamedStoredProcedureQuery> getResourceElements() {
			return GenericOrmQueryContainer.this.getXmlNamedStoredProcedureQueries();
		}
		@Override
		protected XmlNamedStoredProcedureQuery getResourceElement(OrmNamedStoredProcedureQuery2_1 contextElement) {
			return contextElement.getXmlQuery();
		}
	}


	// ********** validation **********

	/**
	 * The queries are validated in the persistence unit.
	 * @see org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit#validateQueries(List, IReporter)
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// queries are validated in the persistence unit
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlQueryContainer.getValidationTextRange();
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}

}
