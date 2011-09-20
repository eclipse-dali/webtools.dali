/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;

/**
 * Java source annotated element (annotations)
 */
abstract class SourceAnnotatedElement<A extends AnnotatedElement>
	extends SourceNode
	implements JavaResourceAnnotatedElement
{
	final A annotatedElement;

	/**
	 * annotations; no duplicates (java compiler has an error for duplicates)
	 */
	final Vector<Annotation> annotations = new Vector<Annotation>();

	/**
	 * Annotation containers keyed on nestable annotation name.
	 * This is used to store annotations that can be both standalone and nested
	 * and are moved back and forth between the 2.
	 */
	final Map<String, AnnotationContainer> annotationContainers = new HashMap<String, AnnotationContainer>();

	// ********** construction/initialization **********

	SourceAnnotatedElement(JavaResourceNode parent, A annotatedElement) {
		super(parent);
		this.annotatedElement = annotatedElement;
	}

	public void initialize(CompilationUnit astRoot) {
		ASTNode node = this.annotatedElement.getBodyDeclaration(astRoot);
		node.accept(this.buildInitialAnnotationVisitor(node));
	}

	private ASTVisitor buildInitialAnnotationVisitor(ASTNode node) {
		return new InitialAnnotationVisitor(node);
	}

	/**
	 * called from {@link InitialAnnotationVisitor}
	 */
	/* private */ void addInitialAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
		String jdtAnnotationName = ASTTools.resolveAnnotation(node);
		if (jdtAnnotationName != null) {
			if(this.annotationIsValidContainer(jdtAnnotationName)) {
				String nestableAnnotationName = this.getNestableAnnotationName(jdtAnnotationName);
				AnnotationContainer container = new AnnotationContainer(nestableAnnotationName);
				container.initialize(node);
				this.annotationContainers.put(nestableAnnotationName, container);
			}
			else if (this.annotationIsValid(jdtAnnotationName)) {
				if (this.selectAnnotationNamed(this.annotations, jdtAnnotationName) == null) { // ignore duplicates
					Annotation annotation = this.buildAnnotation(jdtAnnotationName);
					annotation.initialize((CompilationUnit) node.getRoot());
					this.annotations.add(annotation);
				}
			}
			else if(this.annotationIsValidNestable(jdtAnnotationName)) {
				AnnotationContainer container = new AnnotationContainer(jdtAnnotationName);
				container.initializeNestableAnnotation(node);
				this.annotationContainers.put(jdtAnnotationName, container);
			}
		}
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncAnnotations(this.annotatedElement.getBodyDeclaration(astRoot));
	}


	// ********** annotations **********

	public Iterable<Annotation> getAnnotations() {
		return new LiveCloneIterable<Annotation>(this.annotations);
	}

	public int getAnnotationsSize() {
		return this.annotations.size();
	}

	protected Iterable<NestableAnnotation> getNestableAnnotations() {
		return new CompositeIterable<NestableAnnotation>(this.getNestableAnnotationLists());
	}

	private Iterable<Iterable<NestableAnnotation>> getNestableAnnotationLists() {
		return new TransformationIterable<AnnotationContainer, Iterable<NestableAnnotation>>(this.annotationContainers.values()) {
			@Override
			protected Iterable<NestableAnnotation> transform(AnnotationContainer container) {
				return container.getNestedAnnotations();
			}
		};
	}

	public Annotation getAnnotation(String annotationName) {
		if (this.annotationIsValidContainer(annotationName)) {
			AnnotationContainer container = this.annotationContainers.get(getAnnotationProvider().getNestableAnnotationName(annotationName));
			return container == null ? null : container.getContainerAnnotation();
		}
		return this.selectAnnotationNamed(this.getAnnotations(), annotationName);
	}

	public Annotation getNonNullAnnotation(String annotationName) {
		Annotation annotation = this.getAnnotation(annotationName);
		return (annotation != null) ? annotation : this.buildNullAnnotation(annotationName);
	}

	public ListIterable<NestableAnnotation> getAnnotations(String nestableAnnotationName) {
		AnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		return container != null ? container.getNestedAnnotations() : EmptyListIterable.<NestableAnnotation> instance();
	}

	public int getAnnotationsSize(String nestableAnnotationName) {
		AnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		return container == null ? 0 : container.getNestedAnnotationsSize();
	}

	public NestableAnnotation getAnnotation(int index, String nestableAnnotationName) {
		AnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		return container == null ? null : container.nestedAnnotationAt(index);
	}

	public Annotation getContainerAnnotation(String containerAnnotationName) {
		AnnotationContainer container = this.annotationContainers.get(getAnnotationProvider().getNestableAnnotationName(containerAnnotationName));
		return container == null ? null : container.getContainerAnnotation();
	}

	private String getNestableAnnotationName(String containerAnnotationName) {
		return getAnnotationProvider().getNestableAnnotationName(containerAnnotationName);
	}

	private String getNestableElementName(String nestableAnnotationName) {
		return getAnnotationProvider().getNestableElementName(nestableAnnotationName);		
	}

	public Annotation addAnnotation(String annotationName) {
		Annotation annotation = this.buildAnnotation(annotationName);
		this.annotations.add(annotation);
		annotation.newAnnotation();
		return annotation;
	}

	public NestableAnnotation addAnnotation(int index, String nestableAnnotationName) {
		AnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		if (container == null) {
			container = new AnnotationContainer(nestableAnnotationName);
			this.annotationContainers.put(nestableAnnotationName, container);
		}
		return container.addNestedAnnotation(index);
	}

	public void moveAnnotation(int targetIndex, int sourceIndex, String nestableAnnotationName) {
		this.annotationContainers.get(nestableAnnotationName).moveNestedAnnotation(targetIndex, sourceIndex);
	}

	public void removeAnnotation(String annotationName) {
		Annotation annotation = this.getAnnotation(annotationName);
		if (annotation != null) {
			this.removeAnnotation(annotation);
		}
	}

	private void removeAnnotation(Annotation annotation) {
		this.annotations.remove(annotation);
		annotation.removeAnnotation();
	}

	public void removeAnnotation(int index, String nestableAnnotationName) {
		AnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		container.removeNestedAnnotation(index);
		if (container.isEmpty()) {
			this.annotationContainers.remove(nestableAnnotationName);
		}
	}

	protected boolean annotationIsValid(String annotationName) {
		return CollectionTools.contains(this.getValidAnnotationNames(), annotationName);
	}

	protected boolean annotationIsValidContainer(String annotationName) {
		return CollectionTools.contains(this.getValidContainerAnnotationNames(), annotationName);
	}

	protected boolean annotationIsValidNestable(String annotationName) {
		return CollectionTools.contains(this.getValidNestableAnnotationNames(), annotationName);
	}

	Iterable<String> getValidAnnotationNames() {
		return this.getAnnotationProvider().getAnnotationNames();
	}

	Iterable<String> getValidContainerAnnotationNames() {
		return this.getAnnotationProvider().getContainerAnnotationNames();
	}

	Iterable<String> getValidNestableAnnotationNames() {
		return this.getAnnotationProvider().getNestableAnnotationNames();
	}

	Annotation buildAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildAnnotation(this, this.annotatedElement, annotationName);
	}

	Annotation buildNullAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullAnnotation(this, annotationName);
	}

	NestableAnnotation buildNestableAnnotation(String annotationName, int index) {
		return this.getAnnotationProvider().buildAnnotation(this, this.annotatedElement, annotationName, index);
	}

	private void syncAnnotations(ASTNode node) {
		HashSet<Annotation> annotationsToRemove = new HashSet<Annotation>(this.annotations);

		HashSet<AnnotationContainer> containersToRemove = new HashSet<AnnotationContainer>(this.annotationContainers.values());
		node.accept(this.buildSynchronizeAnnotationVisitor(node, annotationsToRemove, containersToRemove));

		for (Annotation annotation : annotationsToRemove) {
			this.removeItemFromCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
		}

		for (AnnotationContainer annotationContainer : containersToRemove) {
			this.annotationContainers.remove(annotationContainer.getNestedAnnotationName());
			fireItemsRemoved(NESTABLE_ANNOTATIONS_COLLECTION, CollectionTools.collection(annotationContainer.getNestedAnnotations()));
		}

		Iterator<String> keys = this.annotationContainers.keySet().iterator();
		
		while (keys.hasNext()) {
			String annotationName = keys.next();
			if (this.annotationContainers.get(annotationName).getNestedAnnotationsSize() == 0) {
				keys.remove();
			}
		}
	}

	private ASTVisitor buildSynchronizeAnnotationVisitor(ASTNode node, Set<Annotation> annotationsToRemove, Set<AnnotationContainer> containersToRemove) {
		return new SynchronizeAnnotationVisitor(node, annotationsToRemove, containersToRemove);
	}

	/**
	 * called from {@link SynchronizeAnnotationVisitor}
	 */
	/* private */ void addOrSyncAnnotation(org.eclipse.jdt.core.dom.Annotation node, Set<Annotation> annotationsToRemove, Set<AnnotationContainer> containersToRemove) {
		String jdtAnnotationName = ASTTools.resolveAnnotation(node);
		if (jdtAnnotationName != null) {
			if (this.annotationIsValidContainer(jdtAnnotationName)) {
				this.addOrSyncContainerAnnotation_(node, jdtAnnotationName, containersToRemove);
			}
			else if (this.annotationIsValid(jdtAnnotationName)) {
				this.addOrSyncAnnotation_(node, jdtAnnotationName, annotationsToRemove);
			}
			else if(this.annotationIsValidNestable(jdtAnnotationName)) {
				this.addOrSyncNestableAnnotation_(node, jdtAnnotationName, containersToRemove);
			}
		}
	}

	/**
	 * pre-condition: jdtAnnotationName is valid
	 */
	private void addOrSyncAnnotation_(org.eclipse.jdt.core.dom.Annotation node, String jdtAnnotationName, Set<Annotation> annotationsToRemove) {
		Annotation annotation = this.selectAnnotationNamed(annotationsToRemove, jdtAnnotationName);
		if (annotation != null) {
			annotation.synchronizeWith((CompilationUnit) node.getRoot());
			annotationsToRemove.remove(annotation);
		} else {
			annotation = this.buildAnnotation(jdtAnnotationName);
			annotation.initialize((CompilationUnit) node.getRoot());
			this.addItemToCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
		}
	}

	/**
	 * pre-condition: jdtAnnotationName is valid
	 */
	private void addOrSyncNestableAnnotation_(org.eclipse.jdt.core.dom.Annotation node, String nestableAnnotationName, Set<AnnotationContainer> containersToRemove) {
		AnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		if (container != null) {
			container.synchronizeNestableAnnotation(node);
			containersToRemove.remove(container);
		}
		else {
			container = new AnnotationContainer(nestableAnnotationName);
			container.initializeNestableAnnotation(node);
			this.annotationContainers.put(nestableAnnotationName, container);
			this.fireItemAdded(NESTABLE_ANNOTATIONS_COLLECTION, container.nestedAnnotationAt(0));
		}
	}

	/**
	 * pre-condition: node is valid container annotation
	 */
	private void addOrSyncContainerAnnotation_(org.eclipse.jdt.core.dom.Annotation node, String containerAnnotationName, Set<AnnotationContainer> containersToRemove) {
		String nestableAnnotationName = this.getNestableAnnotationName(containerAnnotationName);
		AnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		if (container == null) {
			container = new AnnotationContainer(nestableAnnotationName);
			container.initialize(node);
			this.annotationContainers.put(nestableAnnotationName, container);
			this.fireItemsAdded(NESTABLE_ANNOTATIONS_COLLECTION, CollectionTools.collection(container.getNestedAnnotations()));
		}
		else {
			container.synchronize(node);
			containersToRemove.remove(container);
		}
	}


	// ********** miscellaneous **********

	public boolean isAnnotated() {
		return ! this.annotations.isEmpty() || ! this.annotationContainers.isEmpty();
	}

	public boolean isAnnotatedWith(Iterable<String> annotationNames) {
		for (Annotation annotation : this.getAnnotations()) {
			if (CollectionTools.contains(annotationNames, annotation.getAnnotationName())) {
				return true;
			}
		}
		for (Annotation annotation : this.getNestableAnnotations()) {
			if (CollectionTools.contains(annotationNames, annotation.getAnnotationName())) {
				return true;
			}
		}
		return false;
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		// the AST is null for virtual Java attributes
		// TODO remove the AST null check once we start storing text ranges
		// in the resource model
		return (astRoot == null) ? null : this.buildTextRange(this.annotatedElement.getBodyDeclaration(astRoot));
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		// the AST is null for virtual Java attributes
		// TODO remove the AST null check once we start storing text ranges
		// in the resource model
		return (astRoot == null) ? null : this.annotatedElement.getNameTextRange(astRoot);
	}

	public TextRange getTextRange(String nestableAnnotationName, CompilationUnit astRoot) {
		Annotation containerAnnotation = getContainerAnnotation(getAnnotationProvider().getContainerAnnotationName(nestableAnnotationName));
		if (containerAnnotation != null) {
			return containerAnnotation.getTextRange(astRoot);
		}
		Annotation nestableAnnotation = getAnnotation(0, nestableAnnotationName);
		return nestableAnnotation == null ? null : nestableAnnotation.getTextRange(astRoot);
	}


	private Annotation selectAnnotationNamed(Iterable<Annotation> list, String annotationName) {
		for (Annotation annotation : list) {
			if (annotation.getAnnotationName().equals(annotationName)) {
				return annotation;
			}
		}
		return null;
	}

	private TextRange buildTextRange(ASTNode astNode) {
		return (astNode == null) ? null : ASTTools.buildTextRange(astNode);
	}


	// ********** AST visitors **********

	/**
	 * annotation visitor
	 */
	protected static abstract class AnnotationVisitor
			extends ASTVisitor
	{
		protected final ASTNode node;


		protected AnnotationVisitor(ASTNode node) {
			super();
			this.node = node;
		}

		@Override
		public boolean visit(SingleMemberAnnotation node) {
			return this.visit_(node);
		}

		@Override
		public boolean visit(NormalAnnotation node) {
			return this.visit_(node);
		}

		@Override
		public boolean visit(MarkerAnnotation node) {
			return this.visit_(node);
		}

		protected boolean visit_(org.eclipse.jdt.core.dom.Annotation node) {
			// ignore annotations for child members, only this member
			if (node.getParent() == this.node) {
				this.visitChildAnnotation(node);
			}
			return false;
		}

		protected abstract void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation node);
	}


	/**
	 * initial annotation visitor
	 */
	protected class InitialAnnotationVisitor
			extends AnnotationVisitor
	{
		protected InitialAnnotationVisitor(ASTNode node) {
			super(node);
		}

		@Override
		protected void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
			SourceAnnotatedElement.this.addInitialAnnotation(node);
		}
	}


	/**
	 * synchronize annotation visitor
	 */
	protected class SynchronizeAnnotationVisitor
			extends AnnotationVisitor
	{
		protected final Set<Annotation> annotationsToRemove;
		protected final Set<AnnotationContainer> containersToRemove;

		protected SynchronizeAnnotationVisitor(ASTNode node, Set<Annotation> annotationsToRemove, Set<AnnotationContainer> containersToRemove) {
			super(node);
			this.annotationsToRemove = annotationsToRemove;
			this.containersToRemove = containersToRemove;
		}

		@Override
		protected void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
			SourceAnnotatedElement.this.addOrSyncAnnotation(node, this.annotationsToRemove, this.containersToRemove);
		}
	}


	class AnnotationContainer extends SourceNode.AnnotationContainer<NestableAnnotation>
	{
		private final String nestableAnnotationName;

		private Annotation containerAnnotation;
	
		protected AnnotationContainer(String nestableAnnotationName) {
			super();
			this.nestableAnnotationName = nestableAnnotationName;
		}

		@Override
		public void initialize(org.eclipse.jdt.core.dom.Annotation astContainerAnnotation) {
			super.initialize(astContainerAnnotation);
			this.containerAnnotation = this.buildContainerAnnotation(ASTTools.resolveAnnotation(astContainerAnnotation));
		}

		protected Annotation buildContainerAnnotation(String name) {
			return getAnnotationProvider().buildAnnotation(SourceAnnotatedElement.this, SourceAnnotatedElement.this.annotatedElement, name);
		}

		protected Annotation getContainerAnnotation() {
			return this.containerAnnotation;
		}

		@Override
		public void synchronize(org.eclipse.jdt.core.dom.Annotation astContainerAnnotation) {
			super.synchronize(astContainerAnnotation);
		}

		/**
		 * Return the element name of the nested annotations
		 */
		@Override
		protected String getElementName() {
			return SourceAnnotatedElement.this.getNestableElementName(this.nestableAnnotationName);
		}

		/**
		 * Return the nested annotation name
		 */
		@Override
		protected String getNestedAnnotationName() {
			return this.nestableAnnotationName;
		}

		/**
		 * Return a new nested annotation at the given index
		 */
		@Override
		protected NestableAnnotation buildNestedAnnotation(int index) {
			return SourceAnnotatedElement.this.buildNestableAnnotation(this.nestableAnnotationName, index);
		}

		public void initializeNestableAnnotation(org.eclipse.jdt.core.dom.Annotation standaloneNestableAnnotation) {
			NestableAnnotation nestedAnnotation = this.buildNestedAnnotation(0);
			this.nestedAnnotations.add(nestedAnnotation);
			nestedAnnotation.initialize((CompilationUnit) standaloneNestableAnnotation.getRoot());
		}

		public void synchronizeNestableAnnotation(org.eclipse.jdt.core.dom.Annotation standaloneNestableAnnotation) {
			if (this.getNestedAnnotationsSize() > 1) {
				//ignore the new standalone annotation as a container annotation already exists
			}
			else if (this.getNestedAnnotationsSize() == 1) {
				this.containerAnnotation = null;
				this.nestedAnnotationAt(0).synchronizeWith((CompilationUnit) standaloneNestableAnnotation.getRoot());
			}
		}

		@Override
		public NestableAnnotation addNestedAnnotation(int index) {
			if (getNestedAnnotationsSize() == 1 && getContainerAnnotation() == null) {
				this.containerAnnotation = buildContainerAnnotation(getAnnotationProvider().getContainerAnnotationName(getNestedAnnotationName()));
			}
			return super.addNestedAnnotation(index);
		}

		@Override
		public NestableAnnotation removeNestedAnnotation(int index) {
			if (getNestedAnnotationsSize() == 2) {
				this.containerAnnotation = null;
			}
			return super.removeNestedAnnotation(index);
		}

		@Override
		protected void fireItemAdded(int index, NestableAnnotation nestedAnnotation) {
			SourceAnnotatedElement.this.fireItemAdded(NESTABLE_ANNOTATIONS_COLLECTION, nestedAnnotation);
		}

		@Override
		protected void fireItemsRemoved(int index, List<NestableAnnotation> removedItems) {
			SourceAnnotatedElement.this.fireItemsRemoved(NESTABLE_ANNOTATIONS_COLLECTION, removedItems);			
		}
	}
}
