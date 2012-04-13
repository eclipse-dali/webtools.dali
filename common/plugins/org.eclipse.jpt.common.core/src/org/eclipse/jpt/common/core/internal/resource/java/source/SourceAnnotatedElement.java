/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.TransformerAdapter;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;

/**
 * Java source annotated element
 */
abstract class SourceAnnotatedElement<E extends AnnotatedElement>
	extends SourceNode
	implements JavaResourceAnnotatedElement
{
	final E annotatedElement;

	/**
	 * Annotations keyed by annotation name;
	 * no duplicates (the Java compiler does not allow duplicate annotations).
	 */
	private final Hashtable<String, Annotation> annotations = new Hashtable<String, Annotation>();

	/**
	 * Cache the null annotation objects or they will be rebuilt on every access.
	 * Make this a HashMap for performance, not concerned with duplicate creation and
	 * unlikely that multiple threads will access this.
	 */
	private final HashMap<String, Annotation> nullAnnotationCache = new HashMap<String, Annotation>();

	/**
	 * Annotation containers keyed by <em>nestable</em> annotation name.
	 * This is used to store annotations that can be both standalone and nested
	 * and are moved back and forth between the two.
	 */
	private final Hashtable<String, CombinationAnnotationContainer> annotationContainers = new Hashtable<String, CombinationAnnotationContainer>();


	// ********** construction/initialization **********

	SourceAnnotatedElement(JavaResourceNode parent, E annotatedElement) {
		super(parent);
		this.annotatedElement = annotatedElement;
	}

	/**
	 * Gather up all the significant AST annotations
	 * and build the corresponding Dali annotations.
	 */
	public void initialize(CompilationUnit astRoot) {
		ASTNode node = this.annotatedElement.getBodyDeclaration(astRoot);
		AnnotationVisitor visitor = new AnnotationVisitor(node);
		node.accept(visitor);
		this.initializeAnnotations(visitor.astAnnotations);
		// container annotations take precedence over...
		this.initializeContainerAnnotations(visitor.astContainerAnnotations);
		// ...standalone nestable annotations
		this.initializeStandaloneNestableAnnotations(visitor.astStandaloneNestableAnnotations);
	}

	private void initializeAnnotations(HashMap<String, org.eclipse.jdt.core.dom.Annotation> astAnnotations) {
		for (Map.Entry<String, org.eclipse.jdt.core.dom.Annotation> entry : astAnnotations.entrySet()) {
			String annotationName = entry.getKey();
			org.eclipse.jdt.core.dom.Annotation astAnnotation = entry.getValue();
			Annotation annotation = this.buildAnnotation(annotationName);
			annotation.initialize((CompilationUnit) astAnnotation.getRoot());  // TODO pass the AST annotation!
			this.annotations.put(annotationName, annotation);
		}
	}

	private void initializeContainerAnnotations(HashMap<String, org.eclipse.jdt.core.dom.Annotation> astContainerAnnotations) {
		for (Map.Entry<String, org.eclipse.jdt.core.dom.Annotation> entry : astContainerAnnotations.entrySet()) {
			String containerAnnotationName = entry.getKey();
			org.eclipse.jdt.core.dom.Annotation astAnnotation = entry.getValue();
			String nestableAnnotationName = this.getNestableAnnotationName(containerAnnotationName);
			CombinationAnnotationContainer container = new CombinationAnnotationContainer(nestableAnnotationName, containerAnnotationName);
			container.initializeFromContainerAnnotation(astAnnotation);
			this.annotationContainers.put(nestableAnnotationName, container);
		}
	}

	private void initializeStandaloneNestableAnnotations(HashMap<String, org.eclipse.jdt.core.dom.Annotation> astStandaloneNestableAnnotations) {
		for (Map.Entry<String, org.eclipse.jdt.core.dom.Annotation> entry : astStandaloneNestableAnnotations.entrySet()) {
			String nestableAnnotationName = entry.getKey();
			org.eclipse.jdt.core.dom.Annotation astAnnotation = entry.getValue();
			// if we already have an annotation container (because there was a container annotation)
			// ignore the standalone nestable annotation
			if (this.annotationContainers.get(nestableAnnotationName) == null) {
				CombinationAnnotationContainer container = new CombinationAnnotationContainer(nestableAnnotationName);
				container.initializeFromStandaloneAnnotation(astAnnotation);
				this.annotationContainers.put(nestableAnnotationName, container);
			}
		}
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncAnnotations(this.annotatedElement.getBodyDeclaration(astRoot));
	}


	// ********** annotations **********

	public Iterable<Annotation> getAnnotations() {
		return new LiveCloneIterable<Annotation>(this.annotations.values());
	}

	public int getAnnotationsSize() {
		return this.annotations.size();
	}

	public Annotation getAnnotation(String annotationName) {
		// TODO one reason we search the containers is validation, we need to have separate API for getting the container annotation.
		//The validation in org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlAnyElementMapping is an example 
		if (this.annotationIsValidContainer(annotationName)) {
			CombinationAnnotationContainer container = this.annotationContainers.get(this.getAnnotationProvider().getNestableAnnotationName(annotationName));
			return (container == null) ? null : container.getContainerAnnotation();
		}
		return this.annotations.get(annotationName);
	}

	public Annotation getNonNullAnnotation(String annotationName) {
		Annotation annotation = this.getAnnotation(annotationName);
		return (annotation != null) ? annotation : this.getNullAnnotation(annotationName);
	}

	private Annotation getNullAnnotation(String annotationName) {
		Annotation annotation = this.nullAnnotationCache.get(annotationName);
		if (annotation == null) {
			annotation = this.buildNullAnnotation(annotationName);
			this.nullAnnotationCache.put(annotationName, annotation);
		}
		return annotation;
	}

	private Annotation buildNullAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildNullAnnotation(this, annotationName);
	}

	public Annotation addAnnotation(String annotationName) {
		Annotation annotation = this.buildAnnotation(annotationName);
		this.annotations.put(annotationName, annotation);
		annotation.newAnnotation();
		return annotation;
	}

	public void removeAnnotation(String annotationName) {
		Annotation annotation = this.annotations.remove(annotationName);
		if (annotation != null) {
			annotation.removeAnnotation();
		}
	}

	/* CU private */ boolean annotationIsValid(String annotationName) {
		return CollectionTools.contains(this.getAnnotationProvider().getAnnotationNames(), annotationName);
	}

	/* CU private */ Annotation buildAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildAnnotation(this, this.annotatedElement, annotationName);
	}


	// ********** combination annotations **********

	private Iterable<NestableAnnotation> getNestableAnnotations() {
		return new CompositeIterable<NestableAnnotation>(this.getNestableAnnotationLists());
	}

	private Iterable<Iterable<NestableAnnotation>> getNestableAnnotationLists() {
		return new TransformationIterable<CombinationAnnotationContainer_, Iterable<NestableAnnotation>>(this.getAnnotationContainers(), ANNOTATION_CONTAINER_NESTED_ANNOTATIONS_TRANSFORMER);
	}

	private static final Transformer<CombinationAnnotationContainer_, Iterable<NestableAnnotation>> ANNOTATION_CONTAINER_NESTED_ANNOTATIONS_TRANSFORMER = new AnnotationContainerNestedAnnotationsTransformer();
	/* CU private */ static final class AnnotationContainerNestedAnnotationsTransformer
		extends TransformerAdapter<CombinationAnnotationContainer_, Iterable<NestableAnnotation>>
	{
		@Override
		public Iterable<NestableAnnotation> transform(CombinationAnnotationContainer_ container) {
			return container.getNestedAnnotations();
		}
	}
	
	private Iterable<CombinationAnnotationContainer> getAnnotationContainers() {
		return new LiveCloneIterable<CombinationAnnotationContainer>(this.annotationContainers.values());
	}

	public ListIterable<NestableAnnotation> getAnnotations(String nestableAnnotationName) {
		CombinationAnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		return (container != null) ? container.getNestedAnnotations() : EmptyListIterable.<NestableAnnotation> instance();
	}

	public int getAnnotationsSize(String nestableAnnotationName) {
		CombinationAnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		return (container == null) ? 0 : container.getNestedAnnotationsSize();
	}

	public NestableAnnotation getAnnotation(int index, String nestableAnnotationName) {
		CombinationAnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		if (container == null) {
			throw new ArrayIndexOutOfBoundsException("Container annotation does not exist.");
		}
		return container.getNestedAnnotation(index);
	}

	private String getNestableAnnotationName(String containerAnnotationName) {
		return this.getAnnotationProvider().getNestableAnnotationName(containerAnnotationName);
	}

	/* CU private */ String getContainerAnnotationName(String nestableAnnotationName) {
		return this.getAnnotationProvider().getContainerAnnotationName(nestableAnnotationName);
	}

	/* CU private */ String getNestableElementName(String nestableAnnotationName) {
		return this.getAnnotationProvider().getNestableElementName(nestableAnnotationName);
	}

	public NestableAnnotation addAnnotation(int index, String nestableAnnotationName) {
		CombinationAnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		if (container == null) {
			container = new CombinationAnnotationContainer(nestableAnnotationName);
			this.annotationContainers.put(nestableAnnotationName, container);
		}
		return container.addNestedAnnotation(index);
	}

	public void moveAnnotation(int targetIndex, int sourceIndex, String nestableAnnotationName) {
		this.annotationContainers.get(nestableAnnotationName).moveNestedAnnotation(targetIndex, sourceIndex);
	}

	public void removeAnnotation(int index, String nestableAnnotationName) {
		CombinationAnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		container.removeNestedAnnotation(index);
		if (container.isEmpty()) {
			this.annotationContainers.remove(nestableAnnotationName);
		}
	}

	/* CU private */ boolean annotationIsValidContainer(String annotationName) {
		return CollectionTools.contains(this.getAnnotationProvider().getContainerAnnotationNames(), annotationName);
	}

	/* CU private */ boolean annotationIsValidNestable(String annotationName) {
		return CollectionTools.contains(this.getAnnotationProvider().getNestableAnnotationNames(), annotationName);
	}

	/* CU private */ NestableAnnotation buildNestableAnnotation(String annotationName, int index) {
		return this.getAnnotationProvider().buildAnnotation(this, this.annotatedElement, annotationName, index);
	}

	/* CU private */ void nestedAnnotationAdded(String collectionName, NestableAnnotation addedAnnotation) {
		this.fireItemAdded(collectionName, addedAnnotation);
	}

	/* CU private */ void nestedAnnotationsRemoved(String collectionName, Collection<? extends NestableAnnotation> removedAnnotations) {
		this.fireItemsRemoved(collectionName, removedAnnotations);
	}


	// ***** all annotations *****

	Annotation setPrimaryAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames) {
		// clear out extraneous annotations
		HashSet<String> annotationNames = new HashSet<String>();
		CollectionTools.addAll(annotationNames, supportingAnnotationNames);
		if (primaryAnnotationName != null) {
			annotationNames.add(primaryAnnotationName);
		}
		this.retainAnnotations(annotationNames);
		this.retainAnnotationContainers(annotationNames);

		// add the primary annotation
		if (primaryAnnotationName == null) {
			return null;
		}
		Annotation primaryAnnotation = this.getAnnotation(primaryAnnotationName);
		if (primaryAnnotation == null) {
			primaryAnnotation = this.buildAnnotation(primaryAnnotationName);
			this.annotations.put(primaryAnnotationName, primaryAnnotation);
			primaryAnnotation.newAnnotation();
		}
		return primaryAnnotation;
	}

	private void retainAnnotations(HashSet<String> annotationNames) {
		synchronized (this.annotations) {
			for (Iterator<Map.Entry<String, Annotation>> stream = this.annotations.entrySet().iterator(); stream.hasNext(); ) {
				Map.Entry<String, Annotation> entry = stream.next();
				String annotationName = entry.getKey();
				Annotation annotation = entry.getValue();
				if ( ! annotationNames.contains(annotationName)) {
					stream.remove();
					annotation.removeAnnotation();
				}
			}
		}
	}

	private void retainAnnotationContainers(HashSet<String> annotationNames) {
		synchronized (this.annotationContainers) {
			for (Iterator<Map.Entry<String, CombinationAnnotationContainer>> stream = this.annotationContainers.entrySet().iterator(); stream.hasNext(); ) {
				Map.Entry<String, CombinationAnnotationContainer> entry = stream.next();
				String nestableAnnotationName = entry.getKey();
				CombinationAnnotationContainer container = entry.getValue();
				Annotation containerAnnotation = container.getContainerAnnotation();
				if (containerAnnotation != null) {
					if ( ! annotationNames.contains(container.getContainerAnnotationName())) {
						stream.remove();
						containerAnnotation.removeAnnotation();
					}
				} else {
					// standalone "nestable" annotation
					if ( ! annotationNames.contains(nestableAnnotationName)) {
						stream.remove();
						container.getNestedAnnotation(0).removeAnnotation();
					}
				}
			}
		}
	}

	/**
	 * Gather up all the significant AST annotations
	 * and add or sync the corresponding Dali annotations.
	 */
	private void syncAnnotations(ASTNode node) {
		AnnotationVisitor visitor = new AnnotationVisitor(node);
		node.accept(visitor);
		this.syncAnnotations(visitor.astAnnotations);
		this.syncAnnotationContainers(visitor.astContainerAnnotations, visitor.astStandaloneNestableAnnotations);
	}

	private void syncAnnotations(HashMap<String, org.eclipse.jdt.core.dom.Annotation> astAnnotations) {
		HashMap<String, Annotation> annotationsToRemove = new HashMap<String, Annotation>(this.annotations);
		HashMap<String, Annotation> annotationsToAdd = new HashMap<String, Annotation>();
		for (Map.Entry<String, org.eclipse.jdt.core.dom.Annotation> entry : astAnnotations.entrySet()) {
			String annotationName = entry.getKey();
			org.eclipse.jdt.core.dom.Annotation astAnnotation = entry.getValue();
			Annotation annotation = annotationsToRemove.remove(annotationName);
			if (annotation == null) {
				annotation = this.buildAnnotation(annotationName);
				annotation.initialize((CompilationUnit) astAnnotation.getRoot());  // TODO pass the AST annotation!
				annotationsToAdd.put(annotationName, annotation);
			} else {
				annotation.synchronizeWith((CompilationUnit) astAnnotation.getRoot());  // TODO pass the AST annotation!
			}
		}

		for (String annotationName : annotationsToRemove.keySet()) {
			this.annotations.remove(annotationName);
		}
		this.fireItemsRemoved(ANNOTATIONS_COLLECTION, annotationsToRemove.values());

		this.annotations.putAll(annotationsToAdd);
		this.fireItemsAdded(ANNOTATIONS_COLLECTION, annotationsToAdd.values());
	}

	private void syncAnnotationContainers(HashMap<String, org.eclipse.jdt.core.dom.Annotation> astContainerAnnotations, HashMap<String, org.eclipse.jdt.core.dom.Annotation> astStandaloneNestableAnnotations) {
		HashMap<String, CombinationAnnotationContainer> containersToRemove = new HashMap<String, CombinationAnnotationContainer>(this.annotationContainers);
		HashMap<String, CombinationAnnotationContainer> containersToAdd = new HashMap<String, CombinationAnnotationContainer>();

		for (Map.Entry<String, org.eclipse.jdt.core.dom.Annotation> entry : astContainerAnnotations.entrySet()) {
			String containerAnnotationName = entry.getKey();
			org.eclipse.jdt.core.dom.Annotation astContainerAnnotation = entry.getValue();
			String nestableAnnotationName = this.getNestableAnnotationName(containerAnnotationName);
			CombinationAnnotationContainer container = containersToRemove.remove(nestableAnnotationName);
			if (container == null) {
				container = new CombinationAnnotationContainer(nestableAnnotationName, containerAnnotationName);
				container.initializeFromContainerAnnotation(astContainerAnnotation);
				containersToAdd.put(nestableAnnotationName, container);
			} else {
				container.synchronize(astContainerAnnotation);
			}
			// if it exists, strip out the standalone annotation
			// corresponding to the current container annotation
			astStandaloneNestableAnnotations.remove(nestableAnnotationName);
		}

		for (Map.Entry<String, org.eclipse.jdt.core.dom.Annotation> entry : astStandaloneNestableAnnotations.entrySet()) {
			String nestableAnnotationName = entry.getKey();
			org.eclipse.jdt.core.dom.Annotation astNestableAnnotation = entry.getValue();
			CombinationAnnotationContainer container = containersToRemove.remove(nestableAnnotationName);
			if (container == null) {
				container = new CombinationAnnotationContainer(nestableAnnotationName);
				container.initializeFromStandaloneAnnotation(astNestableAnnotation);
				containersToAdd.put(nestableAnnotationName, container);
			} else {
				container.synchronizeNestableAnnotation(astNestableAnnotation);
			}
		}

		ArrayList<NestableAnnotation> removedNestableAnnotations = new ArrayList<NestableAnnotation>();
		for (String nestableAnnotationName : containersToRemove.keySet()) {
			CombinationAnnotationContainer container = this.annotationContainers.remove(nestableAnnotationName);
			CollectionTools.addAll(removedNestableAnnotations, container.getNestedAnnotations());
		}
		this.fireItemsRemoved(NESTABLE_ANNOTATIONS_COLLECTION, removedNestableAnnotations);

		ArrayList<NestableAnnotation> addedNestableAnnotations = new ArrayList<NestableAnnotation>();
		for (Map.Entry<String, CombinationAnnotationContainer> entry : containersToAdd.entrySet()) {
			String nestableAnnotationName = entry.getKey();
			CombinationAnnotationContainer container = entry.getValue();
			this.annotationContainers.put(nestableAnnotationName, container);
			CollectionTools.addAll(addedNestableAnnotations, container.getNestedAnnotations());
		}
		this.fireItemsAdded(NESTABLE_ANNOTATIONS_COLLECTION, addedNestableAnnotations);
	}

	@SuppressWarnings("unchecked")
	public Iterable<Annotation> getTopLevelAnnotations() {
		return new CompositeIterable<Annotation>(
					this.getAnnotations(),
					this.getContainerOrStandaloneNestableAnnotations()
				);
	}

	private Iterable<Annotation> getContainerOrStandaloneNestableAnnotations() {
		return new TransformationIterable<CombinationAnnotationContainer_, Annotation>(this.getAnnotationContainers(), TOP_LEVEL_ANNOTATION_CONTAINER_TRANSFORMER);
	}

	private static final Transformer<CombinationAnnotationContainer_, Annotation> TOP_LEVEL_ANNOTATION_CONTAINER_TRANSFORMER = new TopLevelAnnotationContainerTransformer();
	static final class TopLevelAnnotationContainerTransformer
		extends TransformerAdapter<CombinationAnnotationContainer_, Annotation>
	{
		@Override
		public Annotation transform(CombinationAnnotationContainer_ container) {
			Annotation containerAnnotation = container.getContainerAnnotation();
			return (containerAnnotation != null) ? containerAnnotation : container.getNestedAnnotation(0);
		}
	}

	public boolean isAnnotated() {
		return ! this.isUnannotated();
	}

	public boolean isUnannotated() {
		return this.annotations.isEmpty() && this.annotationContainers.isEmpty();
	}

	public boolean isAnnotatedWithAnyOf(Iterable<String> annotationNames) {
		for (Annotation annotation : this.getSignificantAnnotations()) {
			if (CollectionTools.contains(annotationNames, annotation.getAnnotationName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the "significant" annotations;
	 * i.e. ignore the container annotations (they have no semantics).
	 */
	@SuppressWarnings("unchecked")
	private Iterable<Annotation> getSignificantAnnotations() {
		return new CompositeIterable<Annotation>(
					this.getAnnotations(),
					this.getNestableAnnotations()
				);
	}


	// ********** misc **********

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
		CombinationAnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		if (container == null) {
			return null;
		}
		Annotation annotation = container.getContainerAnnotation();
		if (annotation == null) {
			annotation = container.getNestedAnnotation(0);
		}
		return annotation.getTextRange(astRoot);
	}

	private TextRange buildTextRange(ASTNode astNode) {
		return (astNode == null) ? null : ASTTools.buildTextRange(astNode);
	}


	// ********** AST visitor **********

	/**
	 * This annotation visitor gathers up all the <em>significant</em>
	 * (i.e. non-duplicate with a valid name) AST annotations
	 * container annotations and standalone nestable annotations for its
	 * {@link #node}.
	 */
	/* CU private */ class AnnotationVisitor
			extends ASTVisitor
	{
		private final ASTNode node;
		final HashMap<String, org.eclipse.jdt.core.dom.Annotation> astAnnotations = new HashMap<String, org.eclipse.jdt.core.dom.Annotation>();
		final HashMap<String, org.eclipse.jdt.core.dom.Annotation> astContainerAnnotations = new HashMap<String, org.eclipse.jdt.core.dom.Annotation>();
		final HashMap<String, org.eclipse.jdt.core.dom.Annotation> astStandaloneNestableAnnotations = new HashMap<String, org.eclipse.jdt.core.dom.Annotation>();

		AnnotationVisitor(ASTNode node) {
			super();
			this.node = node;
		}

		@Override
		public boolean visit(SingleMemberAnnotation annotation) {
			return this.visit_(annotation);
		}

		@Override
		public boolean visit(NormalAnnotation annotation) {
			return this.visit_(annotation);
		}

		@Override
		public boolean visit(MarkerAnnotation annotation) {
			return this.visit_(annotation);
		}

		/**
		 * Process only the annotations for the {@link #node}; ignore any children.
		 */
		private boolean visit_(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
			if (astAnnotation.getParent() == this.node) {
				this.visitChildAnnotation(astAnnotation);
			}
			return false;  // => do *not* visit children
		}

		/**
		 * For each annotation name we save only the first one
		 * and ignore duplicates.
		 */
		private void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
			String astAnnotationName = this.resolveAnnotationName(astAnnotation);
			if (astAnnotationName == null) {
				return;
			}
			// check whether the annotation is a valid container annotation first
			// because container validations are also valid annotations
			// TODO remove container annotations from list of annotations???
			if (SourceAnnotatedElement.this.annotationIsValidContainer(astAnnotationName)) {
				if (this.astContainerAnnotations.get(astAnnotationName) == null) {
					this.astContainerAnnotations.put(astAnnotationName, astAnnotation);
				}
			}
			else if (SourceAnnotatedElement.this.annotationIsValid(astAnnotationName)) {
				if (this.astAnnotations.get(astAnnotationName) == null) {
					this.astAnnotations.put(astAnnotationName, astAnnotation);
				}
			}
			else if (SourceAnnotatedElement.this.annotationIsValidNestable(astAnnotationName)) {
				if (this.astStandaloneNestableAnnotations.get(astAnnotationName) == null) {
					this.astStandaloneNestableAnnotations.put(astAnnotationName, astAnnotation);
				}
			}
		}

		/**
		 * Return the specified annotation's (fully-qualified) class name.
		 */
		private String resolveAnnotationName(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
			IAnnotationBinding annotationBinding = astAnnotation.resolveAnnotationBinding();
			if (annotationBinding == null) {
				return null;
			}
			ITypeBinding annotationTypeBinding = annotationBinding.getAnnotationType();
			return (annotationTypeBinding == null) ? null : annotationTypeBinding.getQualifiedName();
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, node);
		}
	}


	// ********** annotation container **********

	/**
	 * Use this interface to make static references to the annotation container.
	 * Sort of a hack....
	 * @see AnnotationContainerNestedAnnotationsTransformer
	 * @see TopLevelAnnotationContainerTransformer
	 */
	private interface CombinationAnnotationContainer_ {
		Annotation getContainerAnnotation();
		ListIterable<NestableAnnotation> getNestedAnnotations();
		NestableAnnotation getNestedAnnotation(int index);
	}


	/**
	 * Annotation container for top-level "combination" annotations that allow
	 * a single nestable annotation to stand alone, outside of its standard
	 * container annotation, and represent a single-element array.
	 */
	/* CU private */ class CombinationAnnotationContainer
		extends AnnotationContainer<NestableAnnotation>
		implements CombinationAnnotationContainer_
	{
		/**
		 * The name of the nestable annotation that be either a top-level
		 * standalone annotation or nested within the container annotation.
		 */
		private final String nestableAnnotationName;

		/**
		 * The name of the container annotation, used to build the
		 * {@link #containerAnnotation container annotation} as necessary.
		 */
		private final String containerAnnotationName;

		/**
		 * This is <code>null</code> if the container annotation does not exist
		 * but the standalone nestable annotation does.
		 */
		private Annotation containerAnnotation;


		CombinationAnnotationContainer(String nestableAnnotationName) {
			this(nestableAnnotationName, SourceAnnotatedElement.this.getContainerAnnotationName(nestableAnnotationName));
		}

		CombinationAnnotationContainer(String nestableAnnotationName, String containerAnnotationName) {
			super();
			if ((nestableAnnotationName == null) || (containerAnnotationName == null)) {
				throw new NullPointerException();
			}
			this.nestableAnnotationName = nestableAnnotationName;
			this.containerAnnotationName = containerAnnotationName;
		}

		@Override
		public void initializeFromContainerAnnotation(org.eclipse.jdt.core.dom.Annotation astContainerAnnotation) {
			super.initializeFromContainerAnnotation(astContainerAnnotation);
			this.containerAnnotation = this.buildContainerAnnotation(this.containerAnnotationName);
		}

		private Annotation buildContainerAnnotation(String name) {
			return SourceAnnotatedElement.this.buildAnnotation(name);
		}

		public Annotation getContainerAnnotation() {
			return this.containerAnnotation;
		}

		String getContainerAnnotationName() {
			return this.containerAnnotationName;
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

		void initializeFromStandaloneAnnotation(org.eclipse.jdt.core.dom.Annotation astStandaloneNestableAnnotation) {
			NestableAnnotation nestedAnnotation = this.buildNestedAnnotation(0);
			this.nestedAnnotations.add(nestedAnnotation);
			nestedAnnotation.initialize((CompilationUnit) astStandaloneNestableAnnotation.getRoot());  // TODO pass the AST annotation!
		}

		/**
		 * If we get here, the container annotation does <em>not</em> exist but
		 * the standalone nestable annotation does.
		 */
		void synchronizeNestableAnnotation(org.eclipse.jdt.core.dom.Annotation astStandaloneNestableAnnotation) {
			if (this.nestedAnnotations.size() == 0) {
				throw new IllegalStateException();  // should not get here...
			}

			this.containerAnnotation = null;
			this.nestedAnnotations.get(0).synchronizeWith((CompilationUnit) astStandaloneNestableAnnotation.getRoot());  // TODO pass the AST annotation!
			// remove any remaining nested annotations
			this.syncRemoveNestedAnnotations(1);
		}

		@Override
		public NestableAnnotation addNestedAnnotation(int index) {
			if ((this.nestedAnnotations.size() == 1) && (this.containerAnnotation == null)) {
				this.containerAnnotation = this.buildContainerAnnotation(this.containerAnnotationName);
			}
			return super.addNestedAnnotation(index);
		}

		@Override
		public NestableAnnotation removeNestedAnnotation(int index) {
			if (this.nestedAnnotations.size() == 2) {
				this.containerAnnotation = null;
			}
			return super.removeNestedAnnotation(index);
		}

		/**
		 * <strong>NB:</strong> This is a <em>collection</em> name.
		 * @see #nestedAnnotationAdded(int, NestableAnnotation)
		 * @see #nestedAnnotationsRemoved(int, List)
		 */
		@Override
		protected String getNestedAnnotationsListName() {
			throw new UnsupportedOperationException();
		}

		/**
		 * <strong>NB:</strong> Convert to a <em>collection</em> change.
		 */
		@Override
		void nestedAnnotationAdded(int index, NestableAnnotation addedAnnotation) {
			SourceAnnotatedElement.this.nestedAnnotationAdded(NESTABLE_ANNOTATIONS_COLLECTION, addedAnnotation);
		}

		/**
		 * <strong>NB:</strong> Convert to a <em>collection</em> change.
		 */
		@Override
		void nestedAnnotationsRemoved(int index, List<NestableAnnotation> removedAnnotations) {
			SourceAnnotatedElement.this.nestedAnnotationsRemoved(NESTABLE_ANNOTATIONS_COLLECTION, removedAnnotations);
		}
	}
}
