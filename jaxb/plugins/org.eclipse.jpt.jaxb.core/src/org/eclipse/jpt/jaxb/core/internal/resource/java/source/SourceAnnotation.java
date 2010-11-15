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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ASTNodeTextRange;
import org.eclipse.jpt.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.Annotation;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jaxb.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneListIterable;

/**
 * some common state and behavior for Java source annotations;
 * and lots of convenience methods
 */
public abstract class SourceAnnotation<A extends AnnotatedElement>
	extends SourceNode
	implements Annotation
{
	protected final A annotatedElement;

	protected final DeclarationAnnotationAdapter daa;

	protected final AnnotationAdapter annotationAdapter;


	/**
	 * constructor for straight member annotation
	 */
	protected SourceAnnotation(JavaResourceNode parent, A annotatedElement, DeclarationAnnotationAdapter daa) {
		this(parent, annotatedElement, daa, new ElementAnnotationAdapter(annotatedElement, daa));
	}

	/**
	 * constructor for nested annotation (typically)
	 */
	protected SourceAnnotation(JavaResourceNode parent, A annotatedElement, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent);
		this.annotatedElement = annotatedElement;
		this.daa = daa;
		this.annotationAdapter = annotationAdapter;
	}


	// ********** JavaResourceNode implementation **********

	public TextRange getTextRange(CompilationUnit astRoot) {
		return this.getAnnotationTextRange(astRoot);
	}


	// ********** Annotation implementation **********

	public org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot) {
		return this.annotationAdapter.getAnnotation(astRoot);
	}

	public void newAnnotation() {
		this.annotationAdapter.newMarkerAnnotation();
	}

	public void removeAnnotation() {
		this.annotationAdapter.removeAnnotation();
	}


	// ********** convenience methods **********

	/**
	 * Return the text range corresponding to the annotation.
	 * If the annotation is missing, return null.
	 */
	protected TextRange getAnnotationTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.getAstAnnotation(astRoot));
	}

	/**
	 * Convenience method.
	 * Return the text range corresponding to the specified element.
	 * If the specified element is missing, return the annotation's text range instead.
	 */
	protected TextRange getElementTextRange(DeclarationAnnotationElementAdapter<?> elementAdapter, CompilationUnit astRoot) {
		return this.getElementTextRange(this.getAnnotationElementTextRange(elementAdapter, astRoot), astRoot);
	}

	/**
	 * Convenience method. If the specified element text range is null
	 * return the member's text range instead.
	 */
	protected TextRange getElementTextRange(TextRange elementTextRange, CompilationUnit astRoot) {
		return (elementTextRange != null) ? elementTextRange : this.getAnnotationTextRange(astRoot);
	}

	/**
	 * Convenience method. Return whether the specified position exists and
	 * touches the specified element.
	 */
	protected boolean elementTouches(DeclarationAnnotationElementAdapter<?> elementAdapter, int pos, CompilationUnit astRoot) {
		return this.textRangeTouches(this.getAnnotationElementTextRange(elementAdapter, astRoot), pos);
	}

	/**
	 * Convenience method. Return whether the specified text range is not
	 * null (meaning the corresponding AST node exists) and the specified position touches it.
	 */
	protected boolean textRangeTouches(TextRange textRange, int pos) {
		return (textRange != null) && textRange.touches(pos);
	}

	/**
	 * Return the text range corresponding to the specified element.
	 * If the element is missing, return null.
	 */
	protected TextRange getAnnotationElementTextRange(DeclarationAnnotationElementAdapter<?> adapter, CompilationUnit astRoot) {
		return this.getTextRange(this.getAnnotationElementExpression(adapter, astRoot));
	}

	/**
	 * Return the specified AST DOM element.
	 */
	protected Expression getAnnotationElementExpression(DeclarationAnnotationElementAdapter<?> adapter, CompilationUnit astRoot) {
		return adapter.getExpression(this.annotatedElement.getModifiedDeclaration(astRoot));
	}

	/**
	 * Return the text range corresponding to the specified AST node.
	 * If the AST node is null, return null.
	 */
	protected TextRange getTextRange(ASTNode astNode) {
		return (astNode == null) ? null : new ASTNodeTextRange(astNode);
	}


	
	/**
	 * A container for nested annotations. The owner of the AnnotationContainer
	 * needs to call initialize(CompilationUnit) on it.
	 * @param <T> the type of the resource nestable annotations
	 */
	abstract class AnnotationContainer<T extends NestableAnnotation>
	{
		private final Vector<T> nestedAnnotations = new Vector<T>();

		protected AnnotationContainer() {
			super();
		}

		/**
		 * Return the annotations property name for firing property change notification
		 */
		protected abstract String getAnnotationsPropertyName();

		/**
		 * Return the element name of the nested annotations
		 */
		protected abstract String getElementName();

		/**
		 * Return the nested annotation name
		 */
		protected abstract String getNestedAnnotationName();

		/**
		 * Return a new nested annotation at the given index
		 */
		protected abstract T buildNestedAnnotation(int index);

		public void initialize(CompilationUnit astRoot) {
			// ignore the nested AST annotations themselves
			// (maybe someday we can use them during initialization...)
			int size = this.getNestedAstAnnotations(astRoot).size();
			for (int i = 0; i < size; i++) {
				T nestedAnnotation = this.buildNestedAnnotation(i);
				this.nestedAnnotations.add(i, nestedAnnotation);
				nestedAnnotation.initialize(astRoot);
			}
		}

		/**
		 * Synchronize the resource model annotations with those in the specified AST.
		 * Trigger the appropriate change notification.
		 */
		public void synchronize(CompilationUnit astRoot) {
			ArrayList<org.eclipse.jdt.core.dom.Annotation> astAnnotations = this.getNestedAstAnnotations(astRoot);
			Iterator<org.eclipse.jdt.core.dom.Annotation> astAnnotationStream = astAnnotations.iterator();

			for (T nestedAnnotation : this.getNestedAnnotations()) {
				if (astAnnotationStream.hasNext()) {
					// matching AST annotation is present - synchronize the nested annotation
					astAnnotationStream.next();  // maybe someday we can pass this to the update
					nestedAnnotation.synchronizeWith(astRoot);
				} else {
					// no more AST annotations - remove the remaining nested annotations and exit
					this.syncRemoveNestedAnnotations(astAnnotations.size());
					return;
				}
			}

			// add nested annotations for any remaining AST annotations
			while (astAnnotationStream.hasNext()) {
				this.syncAddNestedAnnotation(astAnnotationStream.next());
			}
		}

		public ListIterable<T> getNestedAnnotations() {
			return new LiveCloneListIterable<T>(this.nestedAnnotations);
		}

		public int getNestedAnnotationsSize() {
			return this.nestedAnnotations.size();
		}

		public T nestedAnnotationAt(int index) {
			return this.nestedAnnotations.get(index);
		}

		public T addNestedAnnotation(int index) {
			// add a new annotation to the end of the list...
			int sourceIndex = this.getNestedAnnotationsSize();
			T nestedAnnotation = this.buildNestedAnnotation(sourceIndex);
			this.nestedAnnotations.add(sourceIndex, nestedAnnotation);
			nestedAnnotation.newAnnotation();
			// ...then move it to the specified index
			this.moveNestedAnnotation(index, sourceIndex);
			return nestedAnnotation;
		}

		public T moveNestedAnnotation(int targetIndex, int sourceIndex) {
			if (targetIndex != sourceIndex) {
				return this.moveNestedAnnotation_(targetIndex, sourceIndex);
			}
			return null;
		}

		public T removeNestedAnnotation(int index) {
			T nestedAnnotation = this.nestedAnnotations.remove(index);
			nestedAnnotation.removeAnnotation();
			this.syncAstAnnotationsAfterRemove(index);
			return nestedAnnotation;
		}

		private T moveNestedAnnotation_(int targetIndex, int sourceIndex) {
			T nestedAnnotation = CollectionTools.move(this.nestedAnnotations, targetIndex, sourceIndex).get(targetIndex);
			this.syncAstAnnotationsAfterMove(targetIndex, sourceIndex, nestedAnnotation);
			return nestedAnnotation;
		}

		/**
		 * Return a list of the nested AST annotations.
		 */
		private ArrayList<org.eclipse.jdt.core.dom.Annotation> getNestedAstAnnotations(CompilationUnit astRoot) {
			ArrayList<org.eclipse.jdt.core.dom.Annotation> result = new ArrayList<org.eclipse.jdt.core.dom.Annotation>();
			org.eclipse.jdt.core.dom.Annotation astContainerAnnotation = this.getAstAnnotation(astRoot);
			if (astContainerAnnotation == null || astContainerAnnotation.isMarkerAnnotation()) {
				// no nested annotations
			}
			else if (astContainerAnnotation.isSingleMemberAnnotation()) {
				if (this.getElementName().equals("value")) { //$NON-NLS-1$
					Expression ex = ((SingleMemberAnnotation) astContainerAnnotation).getValue();
					this.addAstAnnotationsTo(ex, result);
				} else {
					// no nested annotations
				}
			}
			else if (astContainerAnnotation.isNormalAnnotation()) {
				MemberValuePair pair = this.getMemberValuePair((NormalAnnotation) astContainerAnnotation);
				if (pair == null) {
					// no nested annotations
				} else {
					this.addAstAnnotationsTo(pair.getValue(), result);
				}
			}
			return result;
		}

		/**
		 * Add whatever annotations are represented by the specified expression to
		 * the specified list. Do not add null to the list for any non-annotation expression.
		 */
		private void addAstAnnotationsTo(Expression expression, ArrayList<org.eclipse.jdt.core.dom.Annotation> astAnnotations) {
			if (expression == null) {
				//do not add null to the list, not sure how we would get here...
			}
			else if (expression.getNodeType() == ASTNode.ARRAY_INITIALIZER) {
				this.addAstAnnotationsTo((ArrayInitializer) expression, astAnnotations);
			}
			else {
				org.eclipse.jdt.core.dom.Annotation astAnnotation = this.getAstAnnotation_(expression);
				if (astAnnotation != null) {
					astAnnotations.add(astAnnotation);
				}
			}
		}

		private void addAstAnnotationsTo(ArrayInitializer arrayInitializer, ArrayList<org.eclipse.jdt.core.dom.Annotation> astAnnotations) {
			List<Expression> expressions = this.expressions(arrayInitializer);
			for (Expression expression : expressions) {
				org.eclipse.jdt.core.dom.Annotation astAnnotation = getAstAnnotation(expression);
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

		private org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot) {
			return SourceAnnotation.this.getAstAnnotation(astRoot);
		}

		/**
		 * If the specified expression is an annotation with the specified name, return it;
		 * otherwise return null.
		 */
		private org.eclipse.jdt.core.dom.Annotation getAstAnnotation(Expression expression) {
			// not sure how the expression could be null...
			return (expression == null) ? null : getAstAnnotation_(expression);
		}

		/**
		 * pre-condition: expression is not null
		 */
		private org.eclipse.jdt.core.dom.Annotation getAstAnnotation_(Expression expression) {
			switch (expression.getNodeType()) {
				case ASTNode.NORMAL_ANNOTATION:
				case ASTNode.SINGLE_MEMBER_ANNOTATION:
				case ASTNode.MARKER_ANNOTATION:
					org.eclipse.jdt.core.dom.Annotation astAnnotation = (org.eclipse.jdt.core.dom.Annotation) expression;
					if (this.getQualifiedName(astAnnotation).equals(this.getNestedAnnotationName())) {
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

		private MemberValuePair getMemberValuePair(NormalAnnotation annotation) {
			List<MemberValuePair> pairs = this.values(annotation);
			for (MemberValuePair pair : pairs) {
				if (pair.getName().getFullyQualifiedName().equals(this.getElementName())) {
					return pair;
				}
			}
			return null;
		}
		
		@SuppressWarnings("unchecked")
		protected List<MemberValuePair> values(NormalAnnotation na) {
			return na.values();
		}

		/**
		 * An annotation was moved within the specified annotation container from
		 * the specified source index to the specified target index.
		 * Synchronize the AST annotations with the resource model annotation container,
		 * starting with the lower index to prevent overlap.
		 */
		private void syncAstAnnotationsAfterMove(int targetIndex, int sourceIndex, T nestedAnnotation) {
			// move the Java annotation to the end of the list...
			nestedAnnotation.moveAnnotation(this.getNestedAnnotationsSize());
			// ...then shift the other AST annotations over one slot...
			if (sourceIndex < targetIndex) {
				for (int i = sourceIndex; i < targetIndex; i++) {
					this.nestedAnnotations.get(i).moveAnnotation(i);
				}
			} else {
				for (int i = sourceIndex; i > targetIndex; i-- ) {
					this.nestedAnnotations.get(i).moveAnnotation(i);
				}
			}
			// ...then move the AST annotation to the now empty slot at the target index
			nestedAnnotation.moveAnnotation(targetIndex);
		}

		/**
		 * An annotation was removed from the specified annotation container at the
		 * specified index.
		 * Synchronize the AST annotations with the resource model annotation container,
		 * starting at the specified index to prevent overlap.
		 */
		private void syncAstAnnotationsAfterRemove(int index) {
			for (int i = index; i < this.getNestedAnnotationsSize(); i++) {
				// the indices are the same because the model annotations are
				// already in the proper locations - it's the AST annotations that
				// need to be moved to the matching location
				this.nestedAnnotations.get(i).moveAnnotation(i);
			}
		}

		private void syncAddNestedAnnotation(org.eclipse.jdt.core.dom.Annotation astAnnotation) {
			int index = this.getNestedAnnotationsSize();
			T nestedAnnotation = this.buildNestedAnnotation(index);
			nestedAnnotation.initialize((CompilationUnit) astAnnotation.getRoot());
			this.nestedAnnotations.add(index, nestedAnnotation);
			SourceAnnotation.this.fireItemAdded(this.getAnnotationsPropertyName(), index, nestedAnnotation);
		}

		private void syncRemoveNestedAnnotations(int index) {
			SourceAnnotation.this.removeItemsFromList(index, this.nestedAnnotations, this.getAnnotationsPropertyName());
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}		
	}
}
