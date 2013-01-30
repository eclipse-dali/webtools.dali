/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;

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

	protected TextRange nameTextRange;

	protected TextRange textRange;

	// ********** construction/initialization **********

	SourceAnnotatedElement(JavaResourceNode parent, E annotatedElement) {
		super(parent);
		this.annotatedElement = annotatedElement;
	}

	/**
	 * Subclasses are responsible for calling this initialize method.
	 * The ASTNode will be used to initialize the annotations and the Name node
	 * will be used to cache the name text range.
	 */
	//TODO We need some sort of "initializer" object to clean up this initialization dance.
	//SourceMember has yet another initialize(IBinding) method that subclasses have to call
	//then subclasses could just call initialize(adapterObject) or even pass it in the constructor.
	//same object would be used for the synchronize method as well
	// The "initializer" interface might look like this: 
	//  getNameNode() : Name
	//  getAnnotationNode() : ASTNode - the ASTNode that has the annotations on it
	//  getBinding() : IBinding - SourceMember and its subclasses have an initialize that takes an IBinding
	//  getTypeBinding() : ITypeBinding  - this is for SourceAttribute
	protected void initialize(ASTNode node, Name nameNode) {
		this.textRange =  this.buildTextRange(node);
		this.nameTextRange = ASTTools.buildTextRange(nameNode);
		this.initializeAnnotations(node);
	}

	/**
	 * Gather up all the significant AST annotations
	 * and build the corresponding Dali annotations.
	 */
	private void initializeAnnotations(ASTNode node) {
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
			Annotation annotation = this.buildAnnotation(annotationName, astAnnotation);
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

	/**
	 * Subclasses are responsible for calling this synchronizeWith method.
	 * The ASTNode will be used to sync the annotations and cache the text range
	 * and the Name node will be used to cache the name text range.
	 */
	protected void synchronizeWith(ASTNode node, Name nameNode) {
		this.textRange =  this.buildTextRange(node);
		this.nameTextRange = ASTTools.buildTextRange(nameNode);
		this.syncAnnotations(node);
	}

	// ********** annotations **********

	public Iterable<Annotation> getAnnotations() {
		return IterableTools.cloneLive(this.annotations.values());
	}

	public int getAnnotationsSize() {
		return this.annotations.size();
	}

	public Annotation getAnnotation(String annotationName) {
		return this.annotations.get(annotationName);
	}

	public Annotation getContainerAnnotation(String containerAnnotationName) {
		CombinationAnnotationContainer container = this.annotationContainers.get(this.getAnnotationProvider().getNestableAnnotationName(containerAnnotationName));
		return (container == null) ? null : container.getContainerAnnotation();		
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
		annotation.removeAnnotation();
	}

	/* CU private */ boolean annotationIsValid(String annotationName) {
		return IterableTools.contains(this.getAnnotationProvider().getAnnotationNames(), annotationName);
	}

	/* CU private */ Annotation buildAnnotation(String annotationName) {
		return this.getAnnotationProvider().buildAnnotation(this, this.annotatedElement, annotationName);
	}

	/* CU private */ Annotation buildAnnotation(String annotationName, org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		//TODO pass the astAnnotation to the buildAnnotation() method
		Annotation annotation = this.getAnnotationProvider().buildAnnotation(this, this.annotatedElement, annotationName);
		annotation.initialize(astAnnotation);
		return annotation;
	}


	// ********** combination annotations **********

	private Iterable<NestableAnnotation> getNestableAnnotations() {
		return IterableTools.children(this.getAnnotationContainers(), CombinationAnnotationContainer_.NESTED_ANNOTATIONS_TRANSFORMER);
	}

	private Iterable<CombinationAnnotationContainer> getAnnotationContainers() {
		return IterableTools.cloneLive(this.annotationContainers.values());
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
			throw new ArrayIndexOutOfBoundsException("size: 0 index: " + index); //$NON-NLS-1$
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
		return IterableTools.contains(this.getAnnotationProvider().getContainerAnnotationNames(), annotationName);
	}

	/* CU private */ boolean annotationIsValidNestable(String annotationName) {
		return IterableTools.contains(this.getAnnotationProvider().getNestableAnnotationNames(), annotationName);
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
				annotation = this.buildAnnotation(annotationName, astAnnotation);
				annotationsToAdd.put(annotationName, annotation);
			} else {
				annotation.synchronizeWith(astAnnotation);
			}
		}
		
		// add annotations first, otherwise it might cause unnecessary remove/add behavior elsewhere
		this.annotations.putAll(annotationsToAdd);
		this.fireItemsAdded(ANNOTATIONS_COLLECTION, annotationsToAdd.values());
		
		for (String annotationName : annotationsToRemove.keySet()) {
			this.annotations.remove(annotationName);
		}
		this.fireItemsRemoved(ANNOTATIONS_COLLECTION, annotationsToRemove.values());
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
			if (IterableTools.contains(annotationNames, annotation.getAnnotationName())) {
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

	public TextRange getTextRange() {
		return this.textRange;
	}

	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}

	public TextRange getTextRange(String nestableAnnotationName) {
		CombinationAnnotationContainer container = this.annotationContainers.get(nestableAnnotationName);
		if (container == null) {
			return null;
		}
		Annotation annotation = container.getContainerAnnotation();
		if (annotation == null) {
			annotation = container.getNestedAnnotation(0);
		}
		return annotation.getTextRange();
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
			// because container annotations are also valid annotations
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
			return ObjectTools.toString(this, node);
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
		/* CU private */ static final Transformer<CombinationAnnotationContainer_, Iterable<NestableAnnotation>> NESTED_ANNOTATIONS_TRANSFORMER = new NestedAnnotationsTransformer();
		/* CU private */ static final class NestedAnnotationsTransformer
			extends TransformerAdapter<CombinationAnnotationContainer_, Iterable<NestableAnnotation>>
		{
			@Override
			public Iterable<NestableAnnotation> transform(CombinationAnnotationContainer_ container) {
				return container.getNestedAnnotations();
			}
		}

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
			this.containerAnnotation = this.buildContainerAnnotation(this.containerAnnotationName, astContainerAnnotation);
		}

		private Annotation buildContainerAnnotation(String name) {
			return SourceAnnotatedElement.this.buildAnnotation(name);
		}

		private Annotation buildContainerAnnotation(String name, org.eclipse.jdt.core.dom.Annotation astContainerAnnotation) {
			return SourceAnnotatedElement.this.buildAnnotation(name, astContainerAnnotation);
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
			nestedAnnotation.initialize(astStandaloneNestableAnnotation);
		}

		/**
		 * If we get here, the container annotation is either empty or does
		 * <em>not</em> exist but the standalone nestable annotation does.
		 */
		void synchronizeNestableAnnotation(org.eclipse.jdt.core.dom.Annotation astStandaloneNestableAnnotation) {
			this.containerAnnotation = null;
			if (this.nestedAnnotations.size() == 0) {
				// container annotation is present but empty
				this.syncAddNestedAnnotation(astStandaloneNestableAnnotation);
			} else {
				this.nestedAnnotations.get(0).synchronizeWith(astStandaloneNestableAnnotation);
				// remove any remaining nested annotations
				this.syncRemoveNestedAnnotations(1);
			}
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
			if (this.nestedAnnotations.size() <= 2) {
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
