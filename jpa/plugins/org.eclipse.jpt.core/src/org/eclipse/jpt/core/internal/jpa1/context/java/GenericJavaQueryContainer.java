/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.core.context.java.JavaQuery;
import org.eclipse.jpt.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.core.resource.java.NamedNativeQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NamedQueriesAnnotation;
import org.eclipse.jpt.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaQueryContainer extends AbstractJavaJpaContextNode
	implements JavaQueryContainer
{
	protected JavaResourceAnnotatedElement javaResourceAnnotatedElement;

	protected final List<JavaNamedQuery> namedQueries;

	protected final List<JavaNamedNativeQuery> namedNativeQueries;
	
	public GenericJavaQueryContainer(JavaJpaContextNode parent) {
		super(parent);
		this.namedQueries = new ArrayList<JavaNamedQuery>();
		this.namedNativeQueries = new ArrayList<JavaNamedNativeQuery>();
	}

	@Override
	public JavaJpaContextNode getParent() {
		return (JavaJpaContextNode) super.getParent();
	}
	
	
	public ListIterator<JavaNamedQuery> namedQueries() {
		return new CloneListIterator<JavaNamedQuery>(this.namedQueries);
	}
	
	public int namedQueriesSize() {
		return this.namedQueries.size();
	}
	
	public JavaNamedQuery addNamedQuery(int index) {
		JavaNamedQuery namedQuery = getJpaFactory().buildJavaNamedQuery(this);
		this.namedQueries.add(index, namedQuery);
		NamedQueryAnnotation namedQueryAnnotation = 
				(NamedQueryAnnotation) this.javaResourceAnnotatedElement.
					addAnnotation(
						index, NamedQueryAnnotation.ANNOTATION_NAME, 
						NamedQueriesAnnotation.ANNOTATION_NAME);
		namedQuery.initialize(namedQueryAnnotation);
		fireItemAdded(NAMED_QUERIES_LIST, index, namedQuery);
		return namedQuery;
	}
	
	protected void addNamedQuery(int index, JavaNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}
	
	protected void addNamedQuery(JavaNamedQuery namedQuery) {
		this.addNamedQuery(this.namedQueries.size(), namedQuery);
	}
	
	public void removeNamedQuery(NamedQuery namedQuery) {
		removeNamedQuery(this.namedQueries.indexOf(namedQuery));
	}
	
	public void removeNamedQuery(int index) {
		JavaNamedQuery removedNamedQuery = this.namedQueries.remove(index);
		this.javaResourceAnnotatedElement.removeAnnotation(
				index, NamedQueryAnnotation.ANNOTATION_NAME, NamedQueriesAnnotation.ANNOTATION_NAME);
		fireItemRemoved(NAMED_QUERIES_LIST, index, removedNamedQuery);
	}	
	
	protected void removeNamedQuery_(JavaNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedQueries, targetIndex, sourceIndex);
		this.javaResourceAnnotatedElement.moveAnnotation(
				targetIndex, sourceIndex, NamedQueriesAnnotation.ANNOTATION_NAME);
		fireItemMoved(NAMED_QUERIES_LIST, targetIndex, sourceIndex);		
	}
	
	public ListIterator<JavaNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<JavaNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public JavaNamedNativeQuery addNamedNativeQuery(int index) {
		JavaNamedNativeQuery namedNativeQuery = getJpaFactory().buildJavaNamedNativeQuery(this);
		this.namedNativeQueries.add(index, namedNativeQuery);
		NamedNativeQueryAnnotation namedNativeQueryAnnotation = 
				(NamedNativeQueryAnnotation) this.javaResourceAnnotatedElement.
					addAnnotation(
						index, NamedNativeQueryAnnotation.ANNOTATION_NAME, 
						NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		namedNativeQuery.initialize(namedNativeQueryAnnotation);		
		fireItemAdded(NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, JavaNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}
	
	protected void addNamedNativeQuery(JavaNamedNativeQuery namedNativeQuery) {
		this.addNamedNativeQuery(this.namedNativeQueries.size(), namedNativeQuery);
	}
	
	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueries.indexOf(namedNativeQuery));
	}
	
	public void removeNamedNativeQuery(int index) {
		JavaNamedNativeQuery removedNamedNativeQuery = this.namedNativeQueries.remove(index);
		this.javaResourceAnnotatedElement.removeAnnotation(
				index, NamedNativeQueryAnnotation.ANNOTATION_NAME, 
				NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		fireItemRemoved(NAMED_NATIVE_QUERIES_LIST, index, removedNamedNativeQuery);
	}	
	
	protected void removeNamedNativeQuery_(JavaNamedNativeQuery namedNativeQuery) {
		removeItemFromList(namedNativeQuery, this.namedNativeQueries, NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.namedNativeQueries, targetIndex, sourceIndex);
		this.javaResourceAnnotatedElement.moveAnnotation(
				targetIndex, sourceIndex, NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		fireItemMoved(NAMED_NATIVE_QUERIES_LIST, targetIndex, sourceIndex);		
	}

	
	
	public void initialize(JavaResourceAnnotatedElement jrae) {
		this.javaResourceAnnotatedElement = jrae;
		this.initializeNamedQueries();
		this.initializeNamedNativeQueries();
	}

	protected void initializeNamedQueries() {
		for (Iterator<NestableAnnotation> stream = this.javaResourceAnnotatedElement.
					annotations(
						NamedQueryAnnotation.ANNOTATION_NAME, 
						NamedQueriesAnnotation.ANNOTATION_NAME); 
					stream.hasNext(); ) {
			this.namedQueries.add(buildNamedQuery((NamedQueryAnnotation) stream.next()));
		}
	}
	
	protected void initializeNamedNativeQueries() {
		for (Iterator<NestableAnnotation> stream = this.javaResourceAnnotatedElement.
					annotations(
						NamedNativeQueryAnnotation.ANNOTATION_NAME, 
						NamedNativeQueriesAnnotation.ANNOTATION_NAME); 
					stream.hasNext(); ) {
			this.namedNativeQueries.add(buildNamedNativeQuery((NamedNativeQueryAnnotation) stream.next()));
		}
	}
	
	protected JavaNamedQuery buildNamedQuery(NamedQueryAnnotation namedQueryResource) {
		JavaNamedQuery namedQuery = getJpaFactory().buildJavaNamedQuery(this);
		namedQuery.initialize(namedQueryResource);
		return namedQuery;
	}
	
	protected JavaNamedNativeQuery buildNamedNativeQuery(NamedNativeQueryAnnotation namedNativeQueryResource) {
		JavaNamedNativeQuery namedNativeQuery = getJpaFactory().buildJavaNamedNativeQuery(this);
		namedNativeQuery.initialize(namedNativeQueryResource);
		return namedNativeQuery;
	}

	public void update(JavaResourceAnnotatedElement jrae) {
		this.javaResourceAnnotatedElement = jrae;
		this.updateNamedQueries();
		this.updateNamedNativeQueries();
	}
	
	protected void updateNamedQueries() {
		ListIterator<JavaNamedQuery> queries = namedQueries();
		Iterator<NestableAnnotation> resourceNamedQueries = 
				this.javaResourceAnnotatedElement.annotations(
					NamedQueryAnnotation.ANNOTATION_NAME, 
					NamedQueriesAnnotation.ANNOTATION_NAME);
		
		while (queries.hasNext()) {
			JavaNamedQuery namedQuery = queries.next();
			if (resourceNamedQueries.hasNext()) {
				namedQuery.update((NamedQueryAnnotation) resourceNamedQueries.next());
			}
			else {
				removeNamedQuery_(namedQuery);
			}
		}
		
		while (resourceNamedQueries.hasNext()) {
			addNamedQuery(buildNamedQuery((NamedQueryAnnotation) resourceNamedQueries.next()));
		}
	}
	
	protected void updateNamedNativeQueries() {
		ListIterator<JavaNamedNativeQuery> queries = namedNativeQueries();
		Iterator<NestableAnnotation> resourceNamedNativeQueries = 
				this.javaResourceAnnotatedElement.annotations(
					NamedNativeQueryAnnotation.ANNOTATION_NAME, 
					NamedNativeQueriesAnnotation.ANNOTATION_NAME);
		
		while (queries.hasNext()) {
			JavaNamedNativeQuery namedQuery = queries.next();
			if (resourceNamedNativeQueries.hasNext()) {
				namedQuery.update((NamedNativeQueryAnnotation) resourceNamedNativeQueries.next());
			}
			else {
				removeNamedNativeQuery_(namedQuery);
			}
		}
		
		while (resourceNamedNativeQueries.hasNext()) {
			addNamedNativeQuery(buildNamedNativeQuery((NamedNativeQueryAnnotation) resourceNamedNativeQueries.next()));
		}	
	}
	
	
	//********** Validation ********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateQueries(messages, astRoot);
	}
	
	protected void validateQueries(List<IMessage> messages, CompilationUnit astRoot) {
		for (Iterator<JavaQuery> localQueries = this.queries(); localQueries.hasNext(); ) {
			JavaQuery localQuery = localQueries.next();
			for (Iterator<Query> globalQueries = this.getPersistenceUnit().queries(); globalQueries.hasNext(); ) {
				if (localQuery.duplicates(globalQueries.next())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.QUERY_DUPLICATE_NAME,
							new String[] {localQuery.getName()},
							localQuery,
							localQuery.getNameTextRange(astRoot)
						)
					);
				}
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Iterator<JavaQuery> queries() {
		return new CompositeIterator<JavaQuery>(this.namedNativeQueries(), this.namedQueries());
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.javaResourceAnnotatedElement.getTextRange(astRoot);
	}

}
