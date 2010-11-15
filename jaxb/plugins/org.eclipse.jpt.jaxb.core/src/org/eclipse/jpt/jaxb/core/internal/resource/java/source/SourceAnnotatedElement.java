/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ASTNodeTextRange;
import org.eclipse.jpt.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.Annotation;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jaxb.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;

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
	 * Nestable annotations keyed on nestable annotation name
	 */
	final Map<String, List<NestableAnnotation>> nestableAnnotations = new HashMap<String, List<NestableAnnotation>>();

	// ********** construction/initialization **********

	SourceAnnotatedElement(JavaResourceNode parent, A annotatedElement) {
		super(parent);
		this.annotatedElement = annotatedElement;
	}

	public void initialize(CompilationUnit astRoot) {
		this.annotatedElement.getBodyDeclaration(astRoot).accept(this.buildInitialAnnotationVisitor(astRoot));
	}

	private ASTVisitor buildInitialAnnotationVisitor(CompilationUnit astRoot) {
		return new InitialAnnotationVisitor(astRoot, this.annotatedElement.getBodyDeclaration(astRoot));
	}

	/**
	 * called from {@link InitialAnnotationVisitor}
	 */
	/* private */ void addInitialAnnotation(org.eclipse.jdt.core.dom.Annotation node, CompilationUnit astRoot) {
		String jdtAnnotationName = ASTTools.resolveAnnotation(node);
		if (jdtAnnotationName != null) {
			if (this.annotationIsValid(jdtAnnotationName)) {
				if (this.selectAnnotationNamed(this.annotations, jdtAnnotationName) == null) { // ignore duplicates
					Annotation annotation = this.buildAnnotation(jdtAnnotationName);
					annotation.initialize(astRoot);
					this.annotations.add(annotation);
				}
			}
			else if(this.annotationIsValidNestable(jdtAnnotationName)) {
				Vector<NestableAnnotation> annotations = new Vector<NestableAnnotation>();
				NestableAnnotation annotation = this.buildNestableAnnotation(jdtAnnotationName, 0);
				annotations.add(annotation);
				annotation.initialize(astRoot);
				this.nestableAnnotations.put(jdtAnnotationName, annotations);
			}
			else if(this.annotationIsValidContainer(jdtAnnotationName)) {
				String nestableAnnotationName = this.getNestableAnnotationName(jdtAnnotationName);
				List<org.eclipse.jdt.core.dom.Annotation> nestedAnnotations = 
					this.getNestedAstAnnotations(node, this.getNestableElementName(jdtAnnotationName), nestableAnnotationName);
				int index = 0;
				Vector<NestableAnnotation> annotations = new Vector<NestableAnnotation>();
				for (org.eclipse.jdt.core.dom.Annotation nestedAstAnnotation : nestedAnnotations) {
					NestableAnnotation annotation = this.buildNestableAnnotation(ASTTools.resolveAnnotation(nestedAstAnnotation), index++);
					annotations.add(annotation);					
					annotation.initialize(astRoot);
				}
				this.nestableAnnotations.put(nestableAnnotationName, annotations);
			}
		}
	}

	/**
	 * Return a list of the nested AST annotations.
	 */
	private ArrayList<org.eclipse.jdt.core.dom.Annotation> getNestedAstAnnotations(org.eclipse.jdt.core.dom.Annotation astContainerAnnotation, String elementName, String nestedAnnotationName) {
		ArrayList<org.eclipse.jdt.core.dom.Annotation> result = new ArrayList<org.eclipse.jdt.core.dom.Annotation>();
		if (astContainerAnnotation == null || astContainerAnnotation.isMarkerAnnotation()) {
			// no nested annotations
		}
		else if (astContainerAnnotation.isSingleMemberAnnotation()) {
			if (elementName.equals("value")) { //$NON-NLS-1$
				Expression ex = ((SingleMemberAnnotation) astContainerAnnotation).getValue();
				addAstAnnotationsTo(ex, nestedAnnotationName, result);
			} else {
				// no nested annotations
			}
		}
		else if (astContainerAnnotation.isNormalAnnotation()) {
			MemberValuePair pair = this.getMemberValuePair((NormalAnnotation) astContainerAnnotation, elementName);
			if (pair == null) {
				// no nested annotations
			} else {
				addAstAnnotationsTo(pair.getValue(), nestedAnnotationName, result);
			}
		}
		return result;
	}
	/**
	 * Add whatever annotations are represented by the specified expression to
	 * the specified list. Do not add null to the list for any non-annotation expression.
	 */
	private void addAstAnnotationsTo(Expression expression, String annotationName, ArrayList<org.eclipse.jdt.core.dom.Annotation> astAnnotations) {
		if (expression == null) {
			//do not add null to the list, not sure how we would get here...
		}
		else if (expression.getNodeType() == ASTNode.ARRAY_INITIALIZER) {
			this.addAstAnnotationsTo((ArrayInitializer) expression, annotationName, astAnnotations);
		}
		else {
			org.eclipse.jdt.core.dom.Annotation astAnnotation = getAstAnnotation_(expression, annotationName);
			if (astAnnotation != null) {
				astAnnotations.add(astAnnotation);
			}
		}
	}

	private void addAstAnnotationsTo(ArrayInitializer arrayInitializer, String annotationName, ArrayList<org.eclipse.jdt.core.dom.Annotation> astAnnotations) {
		List<Expression> expressions = this.expressions(arrayInitializer);
		for (Expression expression : expressions) {
			org.eclipse.jdt.core.dom.Annotation astAnnotation = this.getAstAnnotation(expression, annotationName);
			if (astAnnotation != null) {
				astAnnotations.add(astAnnotation);
			}
		}
	}
	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<Expression> expressions(ArrayInitializer arrayInitializer) {
		return arrayInitializer.expressions();
	}

	/**
	 * If the specified expression is an annotation with the specified name, return it;
	 * otherwise return null.
	 */
	private org.eclipse.jdt.core.dom.Annotation getAstAnnotation(Expression expression, String annotationName) {
		// not sure how the expression could be null...
		return (expression == null) ? null : getAstAnnotation_(expression, annotationName);
	}

	/**
	 * pre-condition: expression is not null
	 */
	private org.eclipse.jdt.core.dom.Annotation getAstAnnotation_(Expression expression, String annotationName) {
		switch (expression.getNodeType()) {
			case ASTNode.NORMAL_ANNOTATION:
			case ASTNode.SINGLE_MEMBER_ANNOTATION:
			case ASTNode.MARKER_ANNOTATION:
				org.eclipse.jdt.core.dom.Annotation astAnnotation = (org.eclipse.jdt.core.dom.Annotation) expression;
				if (this.getQualifiedName(astAnnotation).equals(annotationName)) {
					return astAnnotation;
				}
				return null;
			default:
				return null;
		}
	}

	private String getQualifiedName(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
		ITypeBinding typeBinding = astAnnotation.resolveTypeBinding();
		if (typeBinding != null) {
			String resolvedName = typeBinding.getQualifiedName();
			if (resolvedName != null) {
				return resolvedName;
			}
		}
		return astAnnotation.getTypeName().getFullyQualifiedName();
	}

	private MemberValuePair getMemberValuePair(NormalAnnotation annotation, String elementName) {
		List<MemberValuePair> pairs = this.values(annotation);
		for (MemberValuePair pair : pairs) {
			if (pair.getName().getFullyQualifiedName().equals(elementName)) {
				return pair;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected List<MemberValuePair> values(NormalAnnotation na) {
		return na.values();
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncAnnotations(astRoot);
	}


	// ********** annotations **********

	public Iterable<Annotation> getAnnotations() {
		return new LiveCloneIterable<Annotation>(this.annotations);
	}

	public int getAnnotationsSize() {
		return this.annotations.size();
	}

	public Annotation getAnnotation(String annotationName) {
		return this.selectAnnotationNamed(this.getAnnotations(), annotationName);
	}

	public Annotation getNonNullAnnotation(String annotationName) {
		Annotation annotation = this.getAnnotation(annotationName);
		return (annotation != null) ? annotation : this.buildNullAnnotation(annotationName);
	}

	public ListIterable<NestableAnnotation> getAnnotations(String nestableAnnotationName) {
		List<NestableAnnotation> annotations = this.nestableAnnotations.get(nestableAnnotationName);
		if (annotations != null) {
			return new LiveCloneListIterable<NestableAnnotation>(annotations);
		}
		return EmptyListIterable.instance();
	}

	public int getAnnotationsSize(String nestableAnnotationName) {
		List<NestableAnnotation> annotations = this.nestableAnnotations.get(nestableAnnotationName);
		return annotations == null ? 0 : annotations.size();
	}

	public NestableAnnotation getAnnotation(int index, String nestableAnnotationName) {
		List<NestableAnnotation> annotations = this.nestableAnnotations.get(nestableAnnotationName);
		return annotations == null ? null : annotations.get(index);
	}
	
	private String getNestableAnnotationName(String containerAnnotationName) {
		return getAnnotationProvider().getNestableAnnotationName(containerAnnotationName);
	}

	private String getNestableElementName(String containerAnnotationName) {
		return getAnnotationProvider().getNestableElementName(containerAnnotationName);		
	}

	public Annotation addAnnotation(String annotationName) {
		Annotation annotation = this.buildAnnotation(annotationName);
		this.annotations.add(annotation);
		annotation.newAnnotation();
		return annotation;
	}

	/**
	 * 1. check for a container annotation;
	 *     if it is present, add a nested annotation to it
	 * 2. check for a stand-alone nested annotation;
	 *     if it is missing, add a stand-alone nested annotation
	 * 3. if there is an existing stand-alone nested annotation,
	 *     add a container annotation and move the stand-alone nested annotation to it
	 *     and add a new nested annotation to it also
	 */
	public NestableAnnotation addAnnotation(int index, String nestableAnnotationName) {
			List<NestableAnnotation> nestableAnnotations = this.nestableAnnotations.get(nestableAnnotationName);
			if (nestableAnnotations == null) {
				nestableAnnotations = new Vector<NestableAnnotation>();
				this.nestableAnnotations.put(nestableAnnotationName, nestableAnnotations);
			}
			//build the new nestable annotation at the end of the list then move it to the correct location.
			int sourceIndex = this.getAnnotationsSize(nestableAnnotationName);
			NestableAnnotation annotation = this.buildNestableAnnotation(nestableAnnotationName, sourceIndex);
			nestableAnnotations.add(sourceIndex, annotation);
			annotation.newAnnotation();
			this.moveAnnotation(index, sourceIndex, nestableAnnotationName);
			return annotation;
	}

	public void moveAnnotation(int targetIndex, int sourceIndex, String nestableAnnotationName) {
		List<NestableAnnotation> nestedAnnotations = this.nestableAnnotations.get(nestableAnnotationName);
		NestableAnnotation nestedAnnotation = nestedAnnotations.get(sourceIndex);
		CollectionTools.move(nestedAnnotations, targetIndex, sourceIndex);
		
		this.syncAstAnnotationsAfterMove(targetIndex, sourceIndex, nestedAnnotations, nestedAnnotation);
	}

	/**
	 * An annotation was moved within the specified annotation container from
	 * the specified source index to the specified target index.
	 * Synchronize the AST annotations with the resource model annotation container,
	 * starting with the lower index to prevent overlap.
	 */
	private void syncAstAnnotationsAfterMove(int targetIndex, int sourceIndex, List<NestableAnnotation> nestedAnnotations, NestableAnnotation nestedAnnotation) {
		// move the Java annotation to the end of the list...
		nestedAnnotation.moveAnnotation(nestedAnnotations.size());
		// ...then shift the other AST annotations over one slot...
		List<NestableAnnotation> nestableAnnotations = CollectionTools.list(nestedAnnotations);
		if (sourceIndex < targetIndex) {
			for (int i = sourceIndex; i < targetIndex; i++) {
				nestableAnnotations.get(i).moveAnnotation(i);
			}
		} else {
			for (int i = sourceIndex; i > targetIndex; i-- ) {
				nestableAnnotations.get(i).moveAnnotation(i);
			}
		}
		// ...then move the AST annotation to the now empty slot at the target index
		nestedAnnotation.moveAnnotation(targetIndex);
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
		NestableAnnotation nestableAnnotation = this.nestableAnnotations.get(nestableAnnotationName).remove(index);
		nestableAnnotation.removeAnnotation();
		this.syncAstAnnotationsAfterRemove(index, this.nestableAnnotations.get(nestableAnnotationName));
	}

	/**
	 * An annotation was removed from the specified annotation container at the
	 * specified index.
	 * Synchronize the AST annotations with the resource model annotation container,
	 * starting at the specified index to prevent overlap.
	 */
	private void syncAstAnnotationsAfterRemove(int index, List<NestableAnnotation> nestedAnnotations) {
		for (int i = index; i < nestedAnnotations.size(); i++) {
			// the indices are the same because the model annotations are
			// already in the proper locations - it's the AST annotations that
			// need to be moved to the matching location
			nestedAnnotations.get(i).moveAnnotation(i);
		}
	}

	private boolean annotationIsValid(String annotationName) {
		return CollectionTools.contains(this.getValidAnnotationNames(), annotationName);
	}

	private boolean annotationIsValidContainer(String annotationName) {
		return CollectionTools.contains(this.getValidContainerAnnotationNames(), annotationName);
	}

	private boolean annotationIsValidNestable(String annotationName) {
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

	private void syncAnnotations(CompilationUnit astRoot) {
		HashSet<Annotation> annotationsToRemove = new HashSet<Annotation>(this.annotations);
		HashSet<NestableAnnotation> nestableAnnotationsToRemove = new HashSet<NestableAnnotation>();
		for (List<NestableAnnotation> annotations : this.nestableAnnotations.values()) {
			nestableAnnotationsToRemove.addAll(annotations);
		}

		this.annotatedElement.getBodyDeclaration(astRoot).accept(this.buildSynchronizeAnnotationVisitor(astRoot, annotationsToRemove, nestableAnnotationsToRemove));

		for (Annotation annotation : annotationsToRemove) {
			this.removeItemFromCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
		}
		for (NestableAnnotation nestableAnnotation :  nestableAnnotationsToRemove) {
			List<NestableAnnotation> list = this.nestableAnnotations.get(nestableAnnotation.getAnnotationName());
			list.remove(nestableAnnotation);
			if (list.isEmpty()) {
				this.nestableAnnotations.remove(nestableAnnotation.getAnnotationName());
			}
			this.fireItemRemoved(NESTABLE_ANNOTATIONS_COLLECTION, nestableAnnotation);
		}
	}

	private ASTVisitor buildSynchronizeAnnotationVisitor(CompilationUnit astRoot, Set<Annotation> annotationsToRemove, Set<NestableAnnotation> nestableAnnotationsToRemove) {
		return new SynchronizeAnnotationVisitor(astRoot, this.annotatedElement.getBodyDeclaration(astRoot), annotationsToRemove, nestableAnnotationsToRemove);
	}

	/**
	 * called from {@link SynchronizeAnnotationVisitor}
	 */
	/* private */ void addOrSyncAnnotation(org.eclipse.jdt.core.dom.Annotation node, CompilationUnit astRoot, Set<Annotation> annotationsToRemove, Set<NestableAnnotation> nestableAnnotationsToRemove) {
		String jdtAnnotationName = ASTTools.resolveAnnotation(node);
		if (jdtAnnotationName != null) {
			if (this.annotationIsValid(jdtAnnotationName)) {
				this.addOrSyncAnnotation_(jdtAnnotationName, astRoot, annotationsToRemove);
			}
			else if(this.annotationIsValidNestable(jdtAnnotationName)) {
				this.addOrSyncNestableAnnotation_(jdtAnnotationName, astRoot, nestableAnnotationsToRemove);
			}
			else if(this.annotationIsValidContainer(jdtAnnotationName)) {
				this.addOrSyncContainerAnnotation_(node, jdtAnnotationName, astRoot, nestableAnnotationsToRemove);
			}
		}
	}

	/**
	 * pre-condition: jdtAnnotationName is valid
	 */
	private void addOrSyncAnnotation_(String jdtAnnotationName, CompilationUnit astRoot, Set<Annotation> annotationsToRemove) {
		Annotation annotation = this.selectAnnotationNamed(annotationsToRemove, jdtAnnotationName);
		if (annotation != null) {
			annotation.synchronizeWith(astRoot);
			annotationsToRemove.remove(annotation);
		} else {
			annotation = this.buildAnnotation(jdtAnnotationName);
			annotation.initialize(astRoot);
			this.addItemToCollection(annotation, this.annotations, ANNOTATIONS_COLLECTION);
		}
	}

	/**
	 * pre-condition: jdtAnnotationName is valid
	 */
	private void addOrSyncNestableAnnotation_(String jdtAnnotationName, CompilationUnit astRoot, Set<NestableAnnotation> annotationsToRemove) {
		List<NestableAnnotation> nestableAnnotations = this.nestableAnnotations.get(jdtAnnotationName);
		if (nestableAnnotations != null) {
			if (nestableAnnotations.size() > 1) {
				//ignore the new standalone annotation as a container annotation already exists
			}
			else if (nestableAnnotations.size() == 1) {
				nestableAnnotations.get(0).synchronizeWith(astRoot);
				annotationsToRemove.remove(nestableAnnotations.get(0));
			}
		}
		else {
			nestableAnnotations = new Vector<NestableAnnotation>();
			NestableAnnotation annotation = this.buildNestableAnnotation(jdtAnnotationName, 0);
			annotation.initialize(astRoot);
			this.nestableAnnotations.put(jdtAnnotationName, nestableAnnotations);
			this.fireItemAdded(NESTABLE_ANNOTATIONS_COLLECTION, annotation);
		}
	}

	/**
	 * pre-condition: node is valid container annotation
	 */
	private void addOrSyncContainerAnnotation_(org.eclipse.jdt.core.dom.Annotation node, String containerAnnotationName, CompilationUnit astRoot, Set<NestableAnnotation> annotationsToRemove) {
		String nestableAnnotationName = this.getNestableAnnotationName(containerAnnotationName);
		List<org.eclipse.jdt.core.dom.Annotation> nestedAstAnnotations = 
			this.getNestedAstAnnotations(node, this.getNestableElementName(containerAnnotationName), nestableAnnotationName);
		List<NestableAnnotation> nestedAnnotations = this.nestableAnnotations.get(nestableAnnotationName);
		if (nestedAnnotations == null) {
			nestedAnnotations = new Vector<NestableAnnotation>();
			int index = 0;
			for (org.eclipse.jdt.core.dom.Annotation nestedAstAnnotation : nestedAstAnnotations) {
				NestableAnnotation annotation = this.buildNestableAnnotation(ASTTools.resolveAnnotation(nestedAstAnnotation), index++);
				annotation.initialize(astRoot);
				nestedAnnotations.add(annotation);
			}
			this.nestableAnnotations.put(nestableAnnotationName, nestedAnnotations);
			this.fireItemsAdded(NESTABLE_ANNOTATIONS_COLLECTION, nestedAnnotations);
		}
		else {
			Iterator<org.eclipse.jdt.core.dom.Annotation> astAnnotationStream = nestedAstAnnotations.iterator();
			Iterator<NestableAnnotation> nestedAnnotationsStream = nestedAnnotations.iterator();
			int index = 0;
			while (nestedAnnotationsStream.hasNext()) {
				index++;
				NestableAnnotation nestedAnnotation = nestedAnnotationsStream.next();
				if (astAnnotationStream.hasNext()) {
					// matching AST annotation is present - synchronize the nested annotation
					astAnnotationStream.next();  // maybe someday we can pass this to the update
					nestedAnnotation.synchronizeWith(astRoot);
					annotationsToRemove.remove(nestedAnnotation);
				}
			}
			while (astAnnotationStream.hasNext()) {
				NestableAnnotation nestedAnnotation = this.buildNestableAnnotation(ASTTools.resolveAnnotation(astAnnotationStream.next()), index++);
				nestedAnnotation.initialize(astRoot);
				nestedAnnotations.add(nestedAnnotation);
				this.fireItemAdded(NESTABLE_ANNOTATIONS_COLLECTION, nestedAnnotation);
			}
		}
	}



	// ********** miscellaneous **********

	public boolean isAnnotated() {
		return ! this.annotations.isEmpty() || ! this.nestableAnnotations.isEmpty();
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return this.fullTextRange(astRoot);
	}

	private TextRange fullTextRange(CompilationUnit astRoot) {
		return this.buildTextRange(this.annotatedElement.getBodyDeclaration(astRoot));
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.annotatedElement.getNameTextRange(astRoot);
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
		return (astNode == null) ? null : new ASTNodeTextRange(astNode);
	}


	// ********** AST visitors **********

	/**
	 * annotation visitor
	 */
	protected static abstract class AnnotationVisitor
			extends ASTVisitor
	{
		protected final CompilationUnit astRoot;
		protected final ASTNode node;


		protected AnnotationVisitor(CompilationUnit astRoot, ASTNode node) {
			super();
			this.astRoot = astRoot;
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
		protected InitialAnnotationVisitor(CompilationUnit astRoot, ASTNode node) {
			super(astRoot, node);
		}

		@Override
		protected void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
			SourceAnnotatedElement.this.addInitialAnnotation(node, this.astRoot);
		}
	}


	/**
	 * synchronize annotation visitor
	 */
	protected class SynchronizeAnnotationVisitor
			extends AnnotationVisitor
	{
		protected final Set<Annotation> annotationsToRemove;

		protected final Set<NestableAnnotation> nestableAnnotationsToRemove;

		protected SynchronizeAnnotationVisitor(CompilationUnit astRoot, ASTNode node, Set<Annotation> annotationsToRemove, Set<NestableAnnotation> nestableAnnotationsToRemove) {
			super(astRoot, node);
			this.annotationsToRemove = annotationsToRemove;
			this.nestableAnnotationsToRemove = nestableAnnotationsToRemove;
		}

		@Override
		protected void visitChildAnnotation(org.eclipse.jdt.core.dom.Annotation node) {
			SourceAnnotatedElement.this.addOrSyncAnnotation(node, this.astRoot, this.annotationsToRemove, this.nestableAnnotationsToRemove);
		}
	}
}
