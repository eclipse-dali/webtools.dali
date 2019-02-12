/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.jpa2_1.context.NamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaQueryContainer2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.NamedStoredProcedureQueryAnnotation2_1;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java query container
 */
public class GenericJavaQueryContainer
	extends AbstractJavaContextModel<JavaQueryContainer.Parent>
	implements JavaQueryContainer2_1
{
	protected final ContextListContainer<JavaNamedQuery, NamedQueryAnnotation> namedQueryContainer;
	protected final ContextListContainer<JavaNamedNativeQuery, NamedNativeQueryAnnotation> namedNativeQueryContainer;
	protected final ContextListContainer<JavaNamedStoredProcedureQuery2_1, NamedStoredProcedureQueryAnnotation2_1> namedStoredProcedureQueryContainer;


	public GenericJavaQueryContainer(JavaQueryContainer.Parent parent) {
		super(parent);
		this.namedQueryContainer = this.buildNamedQueryContainer();
		this.namedNativeQueryContainer = this.buildNamedNativeQueryContainer();
		this.namedStoredProcedureQueryContainer = this.buildNamedStoredProcedureQueryContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncNamedQueries(monitor);
		this.syncNamedNativeQueries(monitor);
		this.syncNamedStoredProcedureQueries(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateModels(this.getNamedQueries(), monitor);
		this.updateModels(this.getNamedNativeQueries(), monitor);
		this.updateModels(this.getNamedStoredProcedureQueries(), monitor);
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


	public ListIterable<JavaNamedQuery> getNamedQueries() {
		return this.namedQueryContainer;
	}

	public int getNamedQueriesSize() {
		return this.namedQueryContainer.size();
	}

	public JavaNamedQuery addNamedQuery() {
		return this.addNamedQuery(this.getNamedQueriesSize());
	}

	public JavaNamedQuery addNamedQuery(int index) {
		NamedQueryAnnotation annotation = this.addNamedQueryAnnotation(index);
		return this.namedQueryContainer.addContextElement(index, annotation);
	}

	protected NamedQueryAnnotation addNamedQueryAnnotation(int index) {
		return (NamedQueryAnnotation) this.parent.getResourceAnnotatedElement().addAnnotation(index, NamedQueryAnnotation.ANNOTATION_NAME);
	}

	public void removeNamedQuery(NamedQuery namedQuery) {
		this.removeNamedQuery(this.namedQueryContainer.indexOf((JavaNamedQuery) namedQuery));
	}

	public void removeNamedQuery(int index) {
		this.parent.getResourceAnnotatedElement().removeAnnotation(index, NamedQueryAnnotation.ANNOTATION_NAME);
		this.namedQueryContainer.remove(index);
	}

	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		this.parent.getResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, NamedQueryAnnotation.ANNOTATION_NAME);
		this.namedQueryContainer.move(targetIndex, sourceIndex);
	}

	protected JavaNamedQuery buildNamedQuery(NamedQueryAnnotation namedQueryAnnotation) {
		return this.getJpaFactory().buildJavaNamedQuery(this, namedQueryAnnotation);
	}

	protected void syncNamedQueries(IProgressMonitor monitor) {
		this.namedQueryContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<NamedQueryAnnotation> getNamedQueryAnnotations() {
		return IterableTools.downCast(this.getNestableNamedQueryAnnotations_());
	}

	protected ListIterable<NestableAnnotation> getNestableNamedQueryAnnotations_() {
		return this.parent.getResourceAnnotatedElement().getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaNamedQuery, NamedQueryAnnotation> buildNamedQueryContainer() {
		return this.buildSpecifiedContextListContainer(NAMED_QUERIES_LIST, new NamedQueryContainerAdapter());
	}

	/**
	 * named query container adapter
	 */
	public class NamedQueryContainerAdapter
		extends AbstractContainerAdapter<JavaNamedQuery, NamedQueryAnnotation>
	{
		public JavaNamedQuery buildContextElement(NamedQueryAnnotation resourceElement) {
			return GenericJavaQueryContainer.this.buildNamedQuery(resourceElement);
		}
		public ListIterable<NamedQueryAnnotation> getResourceElements() {
			return GenericJavaQueryContainer.this.getNamedQueryAnnotations();
		}
		public NamedQueryAnnotation extractResourceElement(JavaNamedQuery contextElement) {
			return contextElement.getQueryAnnotation();
		}
	}


	// ********** named native queries **********

	public ListIterable<JavaNamedNativeQuery> getNamedNativeQueries() {
		return this.namedNativeQueryContainer;
	}

	public int getNamedNativeQueriesSize() {
		return this.namedNativeQueryContainer.size();
	}

	public JavaNamedNativeQuery addNamedNativeQuery() {
		return this.addNamedNativeQuery(this.getNamedNativeQueriesSize());
	}

	public JavaNamedNativeQuery addNamedNativeQuery(int index) {
		NamedNativeQueryAnnotation annotation = this.addNamedNativeQueryAnnotation(index);
		return this.namedNativeQueryContainer.addContextElement(index, annotation);
	}

	protected NamedNativeQueryAnnotation addNamedNativeQueryAnnotation(int index) {
		return (NamedNativeQueryAnnotation) this.parent.getResourceAnnotatedElement().addAnnotation(index, NamedNativeQueryAnnotation.ANNOTATION_NAME);
	}

	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueryContainer.indexOf((JavaNamedNativeQuery) namedNativeQuery));
	}

	public void removeNamedNativeQuery(int index) {
		this.parent.getResourceAnnotatedElement().removeAnnotation(index, NamedNativeQueryAnnotation.ANNOTATION_NAME);
		this.namedNativeQueryContainer.remove(index);
	}

	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		this.parent.getResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, NamedNativeQueryAnnotation.ANNOTATION_NAME);
		this.namedNativeQueryContainer.move(targetIndex, sourceIndex);
	}

	protected JavaNamedNativeQuery buildNamedNativeQuery(NamedNativeQueryAnnotation namedNativeQueryAnnotation) {
		return this.getJpaFactory().buildJavaNamedNativeQuery(this, namedNativeQueryAnnotation);
	}

	protected void syncNamedNativeQueries(IProgressMonitor monitor) {
		this.namedNativeQueryContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<NamedNativeQueryAnnotation> getNamedNativeQueryAnnotations() {
		return IterableTools.downCast(this.getNestableNamedNativeQueryAnnotations_());
	}

	protected ListIterable<NestableAnnotation> getNestableNamedNativeQueryAnnotations_() {
		return this.parent.getResourceAnnotatedElement().getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaNamedNativeQuery, NamedNativeQueryAnnotation> buildNamedNativeQueryContainer() {
		return this.buildSpecifiedContextListContainer(NAMED_NATIVE_QUERIES_LIST, new NamedNativeQueryContainerAdapter());
	}

	/**
	 * named native query container adapter
	 */
	public class NamedNativeQueryContainerAdapter
		extends AbstractContainerAdapter<JavaNamedNativeQuery, NamedNativeQueryAnnotation>
	{
		public JavaNamedNativeQuery buildContextElement(NamedNativeQueryAnnotation resourceElement) {
			return GenericJavaQueryContainer.this.buildNamedNativeQuery(resourceElement);
		}
		public ListIterable<NamedNativeQueryAnnotation> getResourceElements() {
			return GenericJavaQueryContainer.this.getNamedNativeQueryAnnotations();
		}
		public NamedNativeQueryAnnotation extractResourceElement(JavaNamedNativeQuery contextElement) {
			return contextElement.getQueryAnnotation();
		}
	}

	// ********** named stored procedure queries **********

	public ListIterable<JavaNamedStoredProcedureQuery2_1> getNamedStoredProcedureQueries() {
		return this.namedStoredProcedureQueryContainer;
	}

	public int getNamedStoredProcedureQueriesSize() {
		return this.namedStoredProcedureQueryContainer.size();
	}

	public JavaNamedStoredProcedureQuery2_1 addNamedStoredProcedureQuery() {
		return this.addNamedStoredProcedureQuery(this.getNamedStoredProcedureQueriesSize());
	}

	public JavaNamedStoredProcedureQuery2_1 addNamedStoredProcedureQuery(int index) {
		NamedStoredProcedureQueryAnnotation2_1 annotation = this.addNamedStoredProcedureQueryAnnotation(index);
		return this.namedStoredProcedureQueryContainer.addContextElement(index, annotation);
	}

	protected NamedStoredProcedureQueryAnnotation2_1 addNamedStoredProcedureQueryAnnotation(int index) {
		return (NamedStoredProcedureQueryAnnotation2_1) this.parent.getResourceAnnotatedElement().addAnnotation(index, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME);
	}

	public void removeNamedStoredProcedureQuery(NamedStoredProcedureQuery2_1 namedStoredProcedureQuery) {
		this.removeNamedStoredProcedureQuery(this.namedStoredProcedureQueryContainer.indexOf((JavaNamedStoredProcedureQuery2_1) namedStoredProcedureQuery));
	}

	public void removeNamedStoredProcedureQuery(int index) {
		this.parent.getResourceAnnotatedElement().removeAnnotation(index, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME);
		this.namedStoredProcedureQueryContainer.remove(index);
	}

	public void moveNamedStoredProcedureQuery(int targetIndex, int sourceIndex) {
		this.parent.getResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME);
		this.namedStoredProcedureQueryContainer.move(targetIndex, sourceIndex);
	}

	protected JavaNamedStoredProcedureQuery2_1 buildNamedStoredProcedureQuery(NamedStoredProcedureQueryAnnotation2_1 namedStoredProcedureQueryAnnotation) {
		return this.getJpaFactory2_1().buildJavaNamedStoredProcedureQuery(this, namedStoredProcedureQueryAnnotation);
	}

	protected void syncNamedStoredProcedureQueries(IProgressMonitor monitor) {
		this.namedStoredProcedureQueryContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<NamedStoredProcedureQueryAnnotation2_1> getNamedStoredProcedureQueryAnnotations() {
		return IterableTools.downCast(this.getNestableNamedStoredProcedureQueryAnnotations_());
	}

	protected ListIterable<NestableAnnotation> getNestableNamedStoredProcedureQueryAnnotations_() {
		return this.parent.getResourceAnnotatedElement().getAnnotations(NamedStoredProcedureQueryAnnotation2_1.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaNamedStoredProcedureQuery2_1, NamedStoredProcedureQueryAnnotation2_1> buildNamedStoredProcedureQueryContainer() {
		return this.buildSpecifiedContextListContainer(NAMED_STORED_PROCEDURE_QUERIES_LIST, new NamedStoredProcedureQueryContainerAdapter());
	}

	/**
	 * named stored procedure query container adapter
	 */
	public class NamedStoredProcedureQueryContainerAdapter
		extends AbstractContainerAdapter<JavaNamedStoredProcedureQuery2_1, NamedStoredProcedureQueryAnnotation2_1>
	{
		public JavaNamedStoredProcedureQuery2_1 buildContextElement(NamedStoredProcedureQueryAnnotation2_1 resourceElement) {
			return GenericJavaQueryContainer.this.buildNamedStoredProcedureQuery(resourceElement);
		}
		public ListIterable<NamedStoredProcedureQueryAnnotation2_1> getResourceElements() {
			return GenericJavaQueryContainer.this.getNamedStoredProcedureQueryAnnotations();
		}
		public NamedStoredProcedureQueryAnnotation2_1 extractResourceElement(JavaNamedStoredProcedureQuery2_1 contextElement) {
			return contextElement.getQueryAnnotation();
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
		TextRange textRange = this.parent.getResourceAnnotatedElement().getTextRange();
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}
}
