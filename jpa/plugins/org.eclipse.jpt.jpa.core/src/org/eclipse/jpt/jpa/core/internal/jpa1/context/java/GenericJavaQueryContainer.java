/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubListIterableWrapper;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
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

	protected final ContextListContainer<JavaNamedQuery, NamedQueryAnnotation> namedQueryContainer;
	protected final ContextListContainer<JavaNamedNativeQuery, NamedNativeQueryAnnotation> namedNativeQueryContainer;


	public GenericJavaQueryContainer(JavaJpaContextNode parent, Owner owner) {
		super(parent);
		this.owner = owner;
		this.namedQueryContainer = this.buildNamedQueryContainer();
		this.namedNativeQueryContainer = this.buildNamedNativeQueryContainer();
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
		this.updateNamedQueries();
		this.updateNamedNativeQueries();
	}


	// ********** named queries **********


	public ListIterable<JavaNamedQuery> getNamedQueries() {
		return this.namedQueryContainer.getContextElements();
	}

	public int getNamedQueriesSize() {
		return this.namedQueryContainer.getContextElementsSize();
	}

	public JavaNamedQuery addNamedQuery() {
		return this.addNamedQuery(this.getNamedQueriesSize());
	}

	public JavaNamedQuery addNamedQuery(int index) {
		NamedQueryAnnotation annotation = this.addNamedQueryAnnotation(index);
		return this.namedQueryContainer.addContextElement(index, annotation);
	}

	protected NamedQueryAnnotation addNamedQueryAnnotation(int index) {
		return (NamedQueryAnnotation) this.owner.getResourceAnnotatedElement().addAnnotation(index, NamedQueryAnnotation.ANNOTATION_NAME);
	}

	public void removeNamedQuery(NamedQuery namedQuery) {
		this.removeNamedQuery(this.namedQueryContainer.indexOfContextElement((JavaNamedQuery) namedQuery));
	}

	public void removeNamedQuery(int index) {
		this.owner.getResourceAnnotatedElement().removeAnnotation(index, NamedQueryAnnotation.ANNOTATION_NAME);
		this.namedQueryContainer.removeContextElement(index);
	}

	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		this.owner.getResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, NamedQueryAnnotation.ANNOTATION_NAME);
		this.namedQueryContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaNamedQuery buildNamedQuery(NamedQueryAnnotation namedQueryAnnotation) {
		return this.getJpaFactory().buildJavaNamedQuery(this, namedQueryAnnotation);
	}

	protected void syncNamedQueries() {
		this.namedQueryContainer.synchronizeWithResourceModel();
	}

	protected void updateNamedQueries() {
		this.namedQueryContainer.update();
	}

	protected ListIterable<NamedQueryAnnotation> getNamedQueryAnnotations() {
		return new SubListIterableWrapper<NestableAnnotation, NamedQueryAnnotation>(this.getNestableNamedQueryAnnotations_());
	}

	protected ListIterable<NestableAnnotation> getNestableNamedQueryAnnotations_() {
		return this.owner.getResourceAnnotatedElement().getAnnotations(NamedQueryAnnotation.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaNamedQuery, NamedQueryAnnotation> buildNamedQueryContainer() {
		return new NamedQueryContainer();
	}

	/**
	 * named query container
	 */
	protected class NamedQueryContainer
		extends ContextListContainer<JavaNamedQuery, NamedQueryAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return NAMED_QUERIES_LIST;
		}
		@Override
		protected JavaNamedQuery buildContextElement(NamedQueryAnnotation resourceElement) {
			return GenericJavaQueryContainer.this.buildNamedQuery(resourceElement);
		}
		@Override
		protected ListIterable<NamedQueryAnnotation> getResourceElements() {
			return GenericJavaQueryContainer.this.getNamedQueryAnnotations();
		}
		@Override
		protected NamedQueryAnnotation getResourceElement(JavaNamedQuery contextElement) {
			return contextElement.getQueryAnnotation();
		}
	}


	// ********** named native queries **********

	public ListIterable<JavaNamedNativeQuery> getNamedNativeQueries() {
		return this.namedNativeQueryContainer.getContextElements();
	}

	public int getNamedNativeQueriesSize() {
		return this.namedNativeQueryContainer.getContextElementsSize();
	}

	public JavaNamedNativeQuery addNamedNativeQuery() {
		return this.addNamedNativeQuery(this.getNamedNativeQueriesSize());
	}

	public JavaNamedNativeQuery addNamedNativeQuery(int index) {
		NamedNativeQueryAnnotation annotation = this.addNamedNativeQueryAnnotation(index);
		return this.namedNativeQueryContainer.addContextElement(index, annotation);
	}

	protected NamedNativeQueryAnnotation addNamedNativeQueryAnnotation(int index) {
		return (NamedNativeQueryAnnotation) this.owner.getResourceAnnotatedElement().addAnnotation(index, NamedNativeQueryAnnotation.ANNOTATION_NAME);
	}

	public void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery) {
		this.removeNamedNativeQuery(this.namedNativeQueryContainer.indexOfContextElement((JavaNamedNativeQuery) namedNativeQuery));
	}

	public void removeNamedNativeQuery(int index) {
		this.owner.getResourceAnnotatedElement().removeAnnotation(index, NamedNativeQueryAnnotation.ANNOTATION_NAME);
		this.namedNativeQueryContainer.removeContextElement(index);
	}

	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		this.owner.getResourceAnnotatedElement().moveAnnotation(targetIndex, sourceIndex, NamedNativeQueryAnnotation.ANNOTATION_NAME);
		this.namedNativeQueryContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected JavaNamedNativeQuery buildNamedNativeQuery(NamedNativeQueryAnnotation namedNativeQueryAnnotation) {
		return this.getJpaFactory().buildJavaNamedNativeQuery(this, namedNativeQueryAnnotation);
	}

	protected void syncNamedNativeQueries() {
		this.namedNativeQueryContainer.synchronizeWithResourceModel();
	}

	protected void updateNamedNativeQueries() {
		this.namedNativeQueryContainer.update();
	}

	protected ListIterable<NamedNativeQueryAnnotation> getNamedNativeQueryAnnotations() {
		return new SubListIterableWrapper<NestableAnnotation, NamedNativeQueryAnnotation>(this.getNestableNamedNativeQueryAnnotations_());
	}

	protected ListIterable<NestableAnnotation> getNestableNamedNativeQueryAnnotations_() {
		return this.owner.getResourceAnnotatedElement().getAnnotations(NamedNativeQueryAnnotation.ANNOTATION_NAME);
	}

	protected ContextListContainer<JavaNamedNativeQuery, NamedNativeQueryAnnotation> buildNamedNativeQueryContainer() {
		return new NamedNativeQueryContainer();
	}

	/**
	 * named query container
	 */
	protected class NamedNativeQueryContainer
		extends ContextListContainer<JavaNamedNativeQuery, NamedNativeQueryAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return NAMED_NATIVE_QUERIES_LIST;
		}
		@Override
		protected JavaNamedNativeQuery buildContextElement(NamedNativeQueryAnnotation resourceElement) {
			return GenericJavaQueryContainer.this.buildNamedNativeQuery(resourceElement);
		}
		@Override
		protected ListIterable<NamedNativeQueryAnnotation> getResourceElements() {
			return GenericJavaQueryContainer.this.getNamedNativeQueryAnnotations();
		}
		@Override
		protected NamedNativeQueryAnnotation getResourceElement(JavaNamedNativeQuery contextElement) {
			return contextElement.getQueryAnnotation();
		}
	}


	// ********** validation **********

	/**
	 * The queries are validated in the persistence unit.
	 * @see org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit#validateQueries(List, IReporter)
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		// queries are validated in the persistence unit
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.owner.getResourceAnnotatedElement().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}


	// ********** misc **********

	@Override
	public JavaJpaContextNode getParent() {
		return (JavaJpaContextNode) super.getParent();
	}
}
