/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueriesAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueriesAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NestableAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java query container
 */
public class GenericJavaQueryContainer
	extends AbstractJavaJpaContextNode
	implements JavaQueryContainer
{
	protected final Owner owner;

	protected final Vector<JavaNamedQuery> namedQueries = new Vector<JavaNamedQuery>();
	protected NamedQueryContainerAdapter namedQueryContainerAdapter = new NamedQueryContainerAdapter();

	protected final Vector<JavaNamedNativeQuery> namedNativeQueries = new Vector<JavaNamedNativeQuery>();
	protected NamedNativeQueryContainerAdapter namedNativeQueryContainerAdapter = new NamedNativeQueryContainerAdapter();


	public GenericJavaQueryContainer(JavaJpaContextNode parent, Owner owner) {
		super(parent);
		this.owner = owner;
		this.initializeNamedQueries();
		this.initializeNamedNativeQueries();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncNamedQueries();
		this.syncNamedNativeQueries();
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getNamedQueries());
		this.updateNodes(this.getNamedNativeQueries());
	}


	// ********** named queries **********

	public ListIterator<JavaNamedQuery> namedQueries() {
		return this.getNamedQueries().iterator();
	}

	protected ListIterable<JavaNamedQuery> getNamedQueries() {
		return new LiveCloneListIterable<JavaNamedQuery>(this.namedQueries);
	}

	public int namedQueriesSize() {
		return this.namedQueries.size();
	}

	public JavaNamedQuery addNamedQuery() {
		return this.addNamedQuery(this.namedQueries.size());
	}

	public JavaNamedQuery addNamedQuery(int index) {
		NamedQueryAnnotation annotation = this.buildNamedQueryAnnotation(index);
		return this.addNamedQuery_(index, annotation);
	}

	protected NamedQueryAnnotation buildNamedQueryAnnotation(int index) {
		return (NamedQueryAnnotation) this.owner.getResourceAnnotatedElement().addAnnotation(index, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
	}

	public void removeNamedQuery(NamedQuery namedQuery) {
		this.removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}

	public void removeNamedQuery(int index) {
		this.owner.getResourceAnnotatedElement().removeAnnotation(index, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		this.removeNamedQuery_(index);
	}

	protected void removeNamedQuery_(int index) {
		this.removeItemFromList(index, this.namedQueries, NAMED_QUERIES_LIST);
	}

	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		this.owner.getResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, NamedQueriesAnnotation.ANNOTATION_NAME);
		this.moveItemInList(targetIndex, sourceIndex, this.namedQueries, NAMED_QUERIES_LIST);
	}

	protected void initializeNamedQueries() {
		for (NamedQueryAnnotation annotation : this.getNamedQueryAnnotations()) {
			this.namedQueries.add(this.buildNamedQuery(annotation));
		}
	}

	protected JavaNamedQuery buildNamedQuery(NamedQueryAnnotation namedQueryAnnotation) {
		return this.getJpaFactory().buildJavaNamedQuery(this, namedQueryAnnotation);
	}

	protected void syncNamedQueries() {
		ContextContainerTools.synchronizeWithResourceModel(this.namedQueryContainerAdapter);
	}

	protected Iterable<NamedQueryAnnotation> getNamedQueryAnnotations() {
		return new SubIterableWrapper<NestableAnnotation, NamedQueryAnnotation>(
				CollectionTools.iterable(this.namedQueryAnnotations())
			);
	}

	protected Iterator<NestableAnnotation> namedQueryAnnotations() {
		return this.owner.getResourceAnnotatedElement().annotations(NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
	}

	protected void moveNamedQuery_(int index, JavaNamedQuery namedQuery) {
		this.moveItemInList(index, namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}

	protected JavaNamedQuery addNamedQuery_(int index, NamedQueryAnnotation namedQueryAnnotation) {
		JavaNamedQuery query = this.buildNamedQuery(namedQueryAnnotation);
		this.addItemToList(index, query, this.namedQueries, NAMED_QUERIES_LIST);
		return query;
	}

	protected void removeNamedQuery_(JavaNamedQuery namedQuery) {
		this.removeNamedQuery_(this.namedQueries.indexOf(namedQuery));
	}

	/**
	 * named query container adapter
	 */
	protected class NamedQueryContainerAdapter
		implements ContextContainerTools.Adapter<JavaNamedQuery, NamedQueryAnnotation>
	{
		public Iterable<JavaNamedQuery> getContextElements() {
			return GenericJavaQueryContainer.this.getNamedQueries();
		}
		public Iterable<NamedQueryAnnotation> getResourceElements() {
			return GenericJavaQueryContainer.this.getNamedQueryAnnotations();
		}
		public NamedQueryAnnotation getResourceElement(JavaNamedQuery contextElement) {
			return contextElement.getQueryAnnotation();
		}
		public void moveContextElement(int index, JavaNamedQuery element) {
			GenericJavaQueryContainer.this.moveNamedQuery_(index, element);
		}
		public void addContextElement(int index, NamedQueryAnnotation resourceElement) {
			GenericJavaQueryContainer.this.addNamedQuery_(index, resourceElement);
		}
		public void removeContextElement(JavaNamedQuery element) {
			GenericJavaQueryContainer.this.removeNamedQuery_(element);
		}
	}


	// ********** named native queries **********

	public ListIterator<JavaNamedNativeQuery> namedNativeQueries() {
		return this.getNamedNativeQueries().iterator();
	}

	protected ListIterable<JavaNamedNativeQuery> getNamedNativeQueries() {
		return new LiveCloneListIterable<JavaNamedNativeQuery>(this.namedNativeQueries);
	}

	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}

	public JavaNamedNativeQuery addNamedNativeQuery() {
		return this.addNamedNativeQuery(this.namedNativeQueries.size());
	}

	public JavaNamedNativeQuery addNamedNativeQuery(int index) {
		NamedNativeQueryAnnotation annotation = this.buildNamedNativeQueryAnnotation(index);
		return this.addNamedNativeQuery_(index, annotation);
	}

	protected NamedNativeQueryAnnotation buildNamedNativeQueryAnnotation(int index) {
		return (NamedNativeQueryAnnotation) this.owner.getResourceAnnotatedElement().addAnnotation(index, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
	}

	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}

	public void removeNamedNativeQuery(int index) {
		this.owner.getResourceAnnotatedElement().removeAnnotation(index, NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		this.removeNamedNativeQuery_(index);
	}

	protected void removeNamedNativeQuery_(int index) {
		this.removeItemFromList(index, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}

	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		this.owner.getResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		this.moveItemInList(targetIndex, sourceIndex, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}

	protected void initializeNamedNativeQueries() {
		for (NamedNativeQueryAnnotation annotation : this.getNamedNativeQueryAnnotations()) {
			this.namedNativeQueries.add(this.buildNamedNativeQuery(annotation));
		}
	}

	protected JavaNamedNativeQuery buildNamedNativeQuery(NamedNativeQueryAnnotation namedNativeQueryAnnotation) {
		return this.getJpaFactory().buildJavaNamedNativeQuery(this, namedNativeQueryAnnotation);
	}

	protected void syncNamedNativeQueries() {
		ContextContainerTools.synchronizeWithResourceModel(this.namedNativeQueryContainerAdapter);
	}

	protected Iterable<NamedNativeQueryAnnotation> getNamedNativeQueryAnnotations() {
		return new SubIterableWrapper<NestableAnnotation, NamedNativeQueryAnnotation>(
				CollectionTools.iterable(this.namedNativeQueryAnnotations())
			);
	}

	protected Iterator<NestableAnnotation> namedNativeQueryAnnotations() {
		return this.owner.getResourceAnnotatedElement().annotations(NamedNativeQueryAnnotation.ANNOTATION_NAME, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
	}

	protected void moveNamedNativeQuery_(int index, JavaNamedNativeQuery namedNativeQuery) {
		this.moveItemInList(index, namedNativeQuery, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}

	protected JavaNamedNativeQuery addNamedNativeQuery_(int index, NamedNativeQueryAnnotation namedNativeQueryAnnotation) {
		JavaNamedNativeQuery query = this.buildNamedNativeQuery(namedNativeQueryAnnotation);
		this.addItemToList(index, query, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
		return query;
	}

	protected void removeNamedNativeQuery_(JavaNamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery_(this.namedNativeQueries.indexOf(namedNativeQuery));
	}

	/**
	 * named native query container adapter
	 */
	protected class NamedNativeQueryContainerAdapter
		implements ContextContainerTools.Adapter<JavaNamedNativeQuery, NamedNativeQueryAnnotation>
	{
		public Iterable<JavaNamedNativeQuery> getContextElements() {
			return GenericJavaQueryContainer.this.getNamedNativeQueries();
		}
		public Iterable<NamedNativeQueryAnnotation> getResourceElements() {
			return GenericJavaQueryContainer.this.getNamedNativeQueryAnnotations();
		}
		public NamedNativeQueryAnnotation getResourceElement(JavaNamedNativeQuery contextElement) {
			return contextElement.getQueryAnnotation();
		}
		public void moveContextElement(int index, JavaNamedNativeQuery element) {
			GenericJavaQueryContainer.this.moveNamedNativeQuery_(index, element);
		}
		public void addContextElement(int index, NamedNativeQueryAnnotation resourceElement) {
			GenericJavaQueryContainer.this.addNamedNativeQuery_(index, resourceElement);
		}
		public void removeContextElement(JavaNamedNativeQuery element) {
			GenericJavaQueryContainer.this.removeNamedNativeQuery_(element);
		}
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateQueries(messages, astRoot);
	}

	protected void validateQueries(List<IMessage> messages, CompilationUnit astRoot) {
		for (Iterator<JavaQuery> localQueries = this.queries(); localQueries.hasNext(); ) {
			JavaQuery localQuery = localQueries.next();
			String name = localQuery.getName();
			if (StringTools.stringIsEmpty(name)){
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.QUERY_NAME_UNDEFINED,
							new String[] {},
							localQuery,
							localQuery.getNameTextRange(astRoot)
						)
					);
			} else {
				List<String> reportedNames = new ArrayList<String>();
				for (Iterator<Query> globalQueries = this.getPersistenceUnit().queries(); globalQueries.hasNext(); ) {
					if (localQuery.duplicates(globalQueries.next()) && !reportedNames.contains(name)) {
						messages.add(
							DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.QUERY_DUPLICATE_NAME,
								new String[] {name},
								localQuery,
								localQuery.getNameTextRange(astRoot)
							)
						);
						reportedNames.add(name);
					}
				}
			}
			String query = localQuery.getQuery();
			if (StringTools.stringIsEmpty(query)){
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.QUERY_STATEMENT_UNDEFINED,
								new String[] {name},
								localQuery,
								localQuery.getNameTextRange(astRoot)
						)
				);
			}
		}
	}


	@SuppressWarnings("unchecked")
	public Iterator<JavaQuery> queries() {
		return new CompositeIterator<JavaQuery>(this.namedNativeQueries(), this.namedQueries());
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.owner.getResourceAnnotatedElement().getTextRange(astRoot);
	}

}
