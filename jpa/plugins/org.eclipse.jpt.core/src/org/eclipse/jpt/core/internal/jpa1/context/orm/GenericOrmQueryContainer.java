/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmQuery;
import org.eclipse.jpt.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlQueryContainer;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmQueryContainer extends AbstractOrmXmlContextNode
	implements OrmQueryContainer
{
	private final XmlQueryContainer resourceQueryContainer;
	
	protected final List<OrmNamedQuery> namedQueries;

	protected final List<OrmNamedNativeQuery> namedNativeQueries;


	public GenericOrmQueryContainer(XmlContextNode parent, XmlQueryContainer resourceQueryContainer) {
		super(parent);
		this.resourceQueryContainer = resourceQueryContainer;
		this.namedQueries = new ArrayList<OrmNamedQuery>();
		this.namedNativeQueries = new ArrayList<OrmNamedNativeQuery>();
		this.initializeNamedQueries();
		this.initializeNamedNativeQueries();
	}

	public ListIterator<OrmNamedQuery> namedQueries() {
		return new CloneListIterator<OrmNamedQuery>(this.namedQueries);
	}
	
	public int namedQueriesSize() {
		return this.namedQueries.size();
	}
	
	public OrmNamedQuery addNamedQuery(int index) {
		XmlNamedQuery resourceNamedQuery = OrmFactory.eINSTANCE.createXmlNamedQuery();
		OrmNamedQuery contextNamedQuery = buildNamedQuery(resourceNamedQuery);
		this.namedQueries.add(index, contextNamedQuery);
		this.resourceQueryContainer.getNamedQueries().add(index, resourceNamedQuery);
		this.fireItemAdded(NAMED_QUERIES_LIST, index, contextNamedQuery);
		return contextNamedQuery;
	}
	
	protected void addNamedQuery(int index, OrmNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}
		
	protected void addNamedQuery(OrmNamedQuery namedQuery) {
		this.addNamedQuery(this.namedQueries.size(), namedQuery);
	}
		
	public void removeNamedQuery(NamedQuery namedQuery) {
		removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}
	
	public void removeNamedQuery(int index) {
		OrmNamedQuery namedQuery = this.namedQueries.remove(index);
		this.resourceQueryContainer.getNamedQueries().remove(index);
		fireItemRemoved(NAMED_QUERIES_LIST, index, namedQuery);
	}
	
	protected void removeNamedQuery_(OrmNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedQueries, targetIndex, sourceIndex);
		this.resourceQueryContainer.getNamedQueries().move(targetIndex, sourceIndex);
		fireItemMoved(NAMED_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<OrmNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<OrmNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public OrmNamedNativeQuery addNamedNativeQuery(int index) {
		XmlNamedNativeQuery resourceNamedNativeQuery = OrmFactory.eINSTANCE.createXmlNamedNativeQuery();
		OrmNamedNativeQuery contextNamedNativeQuery = buildNamedNativeQuery(resourceNamedNativeQuery);
		this.namedNativeQueries.add(index, contextNamedNativeQuery);
		this.resourceQueryContainer.getNamedNativeQueries().add(index, resourceNamedNativeQuery);
		this.fireItemAdded(NAMED_NATIVE_QUERIES_LIST, index, contextNamedNativeQuery);
		return contextNamedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, OrmNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}
	
	protected void addNamedNativeQuery(OrmNamedNativeQuery namedNativeQuery) {
		this.addNamedNativeQuery(this.namedNativeQueries.size(), namedNativeQuery);
	}
	
	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}
	
	public void removeNamedNativeQuery(int index) {
		OrmNamedNativeQuery namedNativeQuery = this.namedNativeQueries.remove(index);
		this.resourceQueryContainer.getNamedNativeQueries().remove(index);
		fireItemRemoved(NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
	}

	protected void removeNamedNativeQuery_(OrmNamedNativeQuery namedNativeQuery) {
		removeItemFromList(namedNativeQuery, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}
		
	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedNativeQueries, targetIndex, sourceIndex);
		this.resourceQueryContainer.getNamedNativeQueries().move(targetIndex, sourceIndex);
		fireItemMoved(NAMED_NATIVE_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	
	protected void initializeNamedQueries() {
		for (XmlNamedQuery namedQuery : this.resourceQueryContainer.getNamedQueries()) {
			this.namedQueries.add(buildNamedQuery(namedQuery));
		}
	}
	
	protected void initializeNamedNativeQueries() {
		for (XmlNamedNativeQuery namedNativeQuery : this.resourceQueryContainer.getNamedNativeQueries()) {
			this.namedNativeQueries.add(buildNamedNativeQuery(namedNativeQuery));
		}
	}
	
	public void update() {
		this.updateNamedQueries();
		this.updateNamedNativeQueries();
	}
	
	protected void updateNamedQueries() {
		// make a copy of the XML queries (to prevent ConcurrentModificationException)
		Iterator<XmlNamedQuery> xmlQueries = new CloneIterator<XmlNamedQuery>(this.resourceQueryContainer.getNamedQueries());
		
		for (Iterator<OrmNamedQuery> contextQueries = this.namedQueries(); contextQueries.hasNext(); ) {
			OrmNamedQuery contextQuery = contextQueries.next();
			if (xmlQueries.hasNext()) {
				contextQuery.update(xmlQueries.next());
			}
			else {
				removeNamedQuery_(contextQuery);
			}
		}
		
		while (xmlQueries.hasNext()) {
			addNamedQuery(buildNamedQuery(xmlQueries.next()));
		}
	}

	protected OrmNamedQuery buildNamedQuery(XmlNamedQuery resourceNamedQuery) {
		return getXmlContextNodeFactory().buildOrmNamedQuery(this, resourceNamedQuery);
	}

	protected void updateNamedNativeQueries() {
		// make a copy of the XML queries (to prevent ConcurrentModificationException)
		Iterator<XmlNamedNativeQuery> xmlQueries = new CloneIterator<XmlNamedNativeQuery>(this.resourceQueryContainer.getNamedNativeQueries());
		
		for (Iterator<OrmNamedNativeQuery> contextQueries = this.namedNativeQueries(); contextQueries.hasNext(); ) {
			OrmNamedNativeQuery contextQuery = contextQueries.next();
			if (xmlQueries.hasNext()) {
				contextQuery.update(xmlQueries.next());
			}
			else {
				removeNamedNativeQuery_(contextQuery);
			}
		}
		
		while (xmlQueries.hasNext()) {
			addNamedNativeQuery(buildNamedNativeQuery(xmlQueries.next()));
		}
	}

	protected OrmNamedNativeQuery buildNamedNativeQuery(XmlNamedNativeQuery resourceNamedNativeQuery) {
		return getXmlContextNodeFactory().buildOrmNamedNativeQuery(this, resourceNamedNativeQuery);
	}
	
	
	//************ validation ***************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateQueries(messages);
	}
	
	protected void validateQueries(List<IMessage> messages) {
		for (Iterator<OrmQuery> localQueries = this.queries(); localQueries.hasNext(); ) {
			OrmQuery localQuery = localQueries.next();
			for (Iterator<Query> globalQueries = this.getPersistenceUnit().queries(); globalQueries.hasNext(); ) {
				if (localQuery.duplicates(globalQueries.next())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.QUERY_DUPLICATE_NAME,
							new String[] {localQuery.getName()},
							localQuery,
							localQuery.getNameTextRange())
					);
				}
			}
		}
	}
	
	/**
	 * Return all the queries, named and named native.
	 */
	@SuppressWarnings("unchecked")
	protected Iterator<OrmQuery> queries() {
		return new CompositeIterator<OrmQuery>(
						namedQueries(),
						namedNativeQueries()
				);
	}

	public TextRange getValidationTextRange() {
		return this.resourceQueryContainer.getValidationTextRange();
	}
}
