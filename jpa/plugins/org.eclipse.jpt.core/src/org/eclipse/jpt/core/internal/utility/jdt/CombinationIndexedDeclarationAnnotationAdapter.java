/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Manipulate an annotation that either occurs stand-alone, e.g.
 * <pre>
 *     &#64;Inner("zero")
 *     private int id;
 * </pre>
 *     
 * or is embedded in an element array within another annotation, e.g.
 * <pre>
 *     &#64;Outer(foo={&#64;Inner("zero"), &#64;Inner("one"), &#64;Inner("two")})
 *     private int id;
 *     
 *         annotationName = "Inner"
 *         containerAnnotationName = "Outer"
 *         elementName = "foo"
 *         index = 0-2
 * </pre>
 * 
 * This is a useful pattern because a declaration cannot have more
 * than one annotation of the same type, and allowing the stand-alone
 * configuration reduces clutter.
 * <br>
 * NB: This configuration only makes sense for "top-level" annotations, as
 * opposed to "nested" annotations. This is because annotation elements
 * can only be declared with a type of a single annotation and annotations
 * cannot be part of an inheritance hierarchy.
 * For example, the following configurations cannot *both* be supported:
 * <pre>
 *     &#64;Foo(bar=&#64;Outer(...))
 *     private int id;
 * 
 *     &#64;Foo(bar=&#64;Inner(...))  // not allowed
 *     private int id;
 * </pre>
 * 
 * NB: Behavior is undefined when both the stand-alone and the nested
 * configurations annotate the same declaration, e.g.
 * <pre>
 *     // undefined behavior
 *     &#64;Inner("zero")
 *     &#64;Outer(foo={&#64;Inner("zero"), &#64;Inner("one"), &#64;Inner("two")})
 *     private int id;
 * </pre>
 */
public class CombinationIndexedDeclarationAnnotationAdapter
	implements IndexedDeclarationAnnotationAdapter
{

	/**
	 * this adapter is used when the annotation is "stand-alone":
	 * <pre>
	 *     &#64;Inner("zero")
	 * </pre>
	 * and is only used when the index is 0 or 1
	 */
	private final SimpleDeclarationAnnotationAdapter standAloneAnnotationAdapter;

	/**
	 * this adapter is used when the annotation is "nested":
	 * <pre>
	 *     &#64;Outer(foo={&#64;Inner("zero"), &#64;Inner("one")})
	 * </pre>
	 */
	private final NestedIndexedDeclarationAnnotationAdapter nestedAnnotationAdapter;

	/**
	 * this adapter is for the "nested" annotation at the zero index;
	 * and is only used when the index is 1
	 */
	private final NestedIndexedDeclarationAnnotationAdapter zeroNestedAnnotationAdapter;

	// reduce NLS checks
	protected static final String VALUE = "value"; //$NON-NLS-1$


	// ********** constructors **********

	/**
	 * default element name is "value"
	 * <pre>
	 *     &#64;Inner("zero")
	 *     &#64;Outer({&#64;Inner("zero"), &#64;Inner("one")})
	 * </pre>
	 */
	public CombinationIndexedDeclarationAnnotationAdapter(String annotationName, String containerAnnotationName, int index) {
		this(annotationName, containerAnnotationName, VALUE, index);
	}

	public CombinationIndexedDeclarationAnnotationAdapter(String annotationName, String containerAnnotationName, String elementName, int index) {
		this(new SimpleDeclarationAnnotationAdapter(annotationName), new SimpleDeclarationAnnotationAdapter(containerAnnotationName), elementName, index, annotationName);
	}

	/**
	 * default element name is "value"
	 */
	public CombinationIndexedDeclarationAnnotationAdapter(
			SimpleDeclarationAnnotationAdapter standAloneAnnotationAdapter, 
			SimpleDeclarationAnnotationAdapter containerAnnotationAdapter, 
			int index, 
			String nestedAnnotationName
	) {
		this(standAloneAnnotationAdapter, containerAnnotationAdapter, VALUE, index, nestedAnnotationName);
	}

	public CombinationIndexedDeclarationAnnotationAdapter(
			SimpleDeclarationAnnotationAdapter standAloneAnnotationAdapter, 
			SimpleDeclarationAnnotationAdapter containerAnnotationAdapter, 
			String elementName, 
			int index, 
			String nestedAnnotationName
	) {
		super();
		this.standAloneAnnotationAdapter = standAloneAnnotationAdapter;
		this.nestedAnnotationAdapter = new NestedIndexedDeclarationAnnotationAdapter(containerAnnotationAdapter, elementName, index, nestedAnnotationName);
		this.zeroNestedAnnotationAdapter = new NestedIndexedDeclarationAnnotationAdapter(containerAnnotationAdapter, elementName, 0, nestedAnnotationName);
	}


	// ********** DeclarationAnnotationAdapter implementation **********

	public Annotation getAnnotation(ModifiedDeclaration declaration) {
		if (this.getIndex() == 0) {
			// check for the stand-alone annotation
			Annotation standAloneAnnotation = this.getStandAloneAnnotation(declaration);
			if (standAloneAnnotation != null) {
				return standAloneAnnotation;
			}
		}
		return this.getNestedAnnotation(declaration);
	}

	/**
	 * <pre>
	 * [none] => &#64;Inner
	 *     or
	 * &#64;Inner("lorem ipsum") => &#64;Inner
	 *     or
	 * &#64;Inner(text="lorem ipsum") => &#64;Inner
	 *     or
	 * &#64;Outer(foo={&#64;Inner, &#64;Inner}) => &#64;Outer(foo={&#64;Inner, &#64;Inner, &#64;Inner})
	 *     or
	 * &#64;Outer(foo=&#64;Inner) => &#64;Outer(foo={&#64;Inner, &#64;Inner})
	 *     or
	 * &#64;Inner => &#64;Outer(foo={&#64;Inner, &#64;Inner})
	 *     etc.
	 * </pre>
	 */
	public MarkerAnnotation newMarkerAnnotation(ModifiedDeclaration declaration) {
		return (MarkerAnnotation) this.newAnnotation(MARKER_ANNOTATION_FACTORY, declaration);
	}

	public SingleMemberAnnotation newSingleMemberAnnotation(ModifiedDeclaration declaration) {
		return (SingleMemberAnnotation) this.newAnnotation(SINGLE_MEMBER_ANNOTATION_FACTORY, declaration);
	}

	public NormalAnnotation newNormalAnnotation(ModifiedDeclaration declaration) {
		return (NormalAnnotation) this.newAnnotation(NORMAL_ANNOTATION_FACTORY, declaration);
	}

	public void removeAnnotation(ModifiedDeclaration declaration) {
		if (this.getIndex() == 0) {
			// check for the stand-alone annotation
			if (this.standAloneAnnotationIsPresent(declaration)) {
				this.removeStandAloneAnnotation(declaration);
				return;
			}
		}
		this.removeNestedAnnotation(declaration);
		if (this.nestedElementCanBeConvertedToStandAlone(declaration)) {
			this.convertLastElementAnnotationToStandAloneAnnotation(declaration);
		}
	}

	public ASTNode getAstNode(ModifiedDeclaration declaration) {
		// if the annotation is missing, delegate to the nested annotation adapter
		Annotation annotation = this.getAnnotation(declaration);
		return (annotation != null) ? annotation : this.nestedAnnotationAdapter.getAstNode(declaration);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getAnnotationName());
	}


	// ********** IndexedDeclarationAnnotationAdapter implementation **********

	public int getIndex() {
		return this.nestedAnnotationAdapter.getIndex();
	}

	public void moveAnnotation(int newIndex, ModifiedDeclaration declaration) {
		int oldIndex = this.getIndex();
		if (newIndex == oldIndex) {
			return;
		}

		Annotation standAloneAnnotation = this.getStandAloneAnnotation(declaration);
		if (standAloneAnnotation == null) {
			this.moveNestedAnnotation(newIndex, declaration);
			if (this.nestedElementCanBeConvertedToStandAlone(declaration)) {
				this.convertLastElementAnnotationToStandAloneAnnotation(declaration);
			}
		} else {
			if ((oldIndex == 0) && (newIndex == 1)) {
				// this is one of two situations where we transition from stand-alone to container
				this.moveStandAloneAnnotationToContainerAnnotation(standAloneAnnotation, declaration);
				this.moveNestedAnnotation(newIndex, declaration);
			} else if (newIndex == 0) {
				// we are moving a 'null' entry on top of the stand-alone, so remove it
				this.removeStandAloneAnnotation(declaration);
			} else {
				throw new IllegalStateException("old index = " + oldIndex + "; new index = " + newIndex); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}


	// ********** internal methods **********

	/**
	 * build the appropriate new annotation,
	 * which may require moving the 0th annotation from "stand-alone" to "nested"
	 */
	private Annotation newAnnotation(AnnotationFactory annotationFactory, ModifiedDeclaration declaration) {
		if (this.getIndex() ==  0) {
			return this.newZeroAnnotation(annotationFactory, declaration);
		}
		if (this.zeroNestedAnnotationIsPresent(declaration)) {
			// manipulate the container annotation - ignore the stand-alone annotation(?)
			// @Outer(foo=@Inner("zero")) => @Outer(foo={@Inner("zero"), @Inner})
			//     or
			// @Outer(foo={@Inner("zero"), @Inner("one")}) => @Outer(foo={@Inner("zero"), @Inner})
			return annotationFactory.newAnnotation(this.nestedAnnotationAdapter, declaration);
		}

		// this is one of two situations where we transition from stand-alone to container
		this.moveStandAloneAnnotationToContainerAnnotation(declaration);
		// once the stand-alone annotation is moved to index=0, build the new annotation at index=1
		return annotationFactory.newAnnotation(this.nestedAnnotationAdapter, declaration);
	}

	/**
	 * the index is 0 - build the appropriate new annotation,
	 * which may be either "stand-alone" or "nested"
	 */
	private Annotation newZeroAnnotation(AnnotationFactory annotationFactory, ModifiedDeclaration declaration) {
		if (this.standAloneAnnotationIsPresent(declaration)) {
			// replace the stand-alone annotation - ignore the container annotation(?)
			// @Inner(text="lorem ipsum") => @Inner
			return annotationFactory.newAnnotation(this.standAloneAnnotationAdapter, declaration);
		}
		if (this.containerAnnotationIsPresent(declaration)) {
			// manipulate the container annotation
			// @Outer(foo=@Inner(text="lorem ipsum")) => @Outer(foo=@Inner)
			return annotationFactory.newAnnotation(this.nestedAnnotationAdapter, declaration);
		}
		// neither annotation is present - add a new stand-alone annotation
		return annotationFactory.newAnnotation(this.standAloneAnnotationAdapter, declaration);
	}

	/**
	 * move the stand-alone annotation to the container annotation at index=0
	 */
	private void moveStandAloneAnnotationToContainerAnnotation(ModifiedDeclaration declaration) {
		Annotation standAloneAnnotation = this.getStandAloneAnnotation(declaration);
		if (standAloneAnnotation == null) {
			throw new IllegalStateException("the stand-alone annotation is missing"); //$NON-NLS-1$
		}
		this.moveStandAloneAnnotationToContainerAnnotation(standAloneAnnotation, declaration);
	}

	/**
	 * move the specified, non-null, stand-alone annotation to
	 * the container annotation at index=0
	 */
	private void moveStandAloneAnnotationToContainerAnnotation(Annotation standAloneAnnotation, ModifiedDeclaration declaration) {
		if (standAloneAnnotation.isMarkerAnnotation()) {
			this.zeroNestedAnnotationAdapter.newMarkerAnnotation(declaration);
		} else if (standAloneAnnotation.isSingleMemberAnnotation()) {
			Expression vv = ((SingleMemberAnnotation) standAloneAnnotation).getValue();
			vv = (Expression) ASTNode.copySubtree(vv.getAST(), vv);
			this.zeroNestedAnnotationAdapter.newSingleMemberAnnotation(declaration).setValue(vv);
		} else if (standAloneAnnotation.isNormalAnnotation()) {
			NormalAnnotation newNA = this.zeroNestedAnnotationAdapter.newNormalAnnotation(declaration);
			List<MemberValuePair> values = this.values(newNA);
			for (MemberValuePair pair : this.values((NormalAnnotation) standAloneAnnotation)) {
				values.add((MemberValuePair) ASTNode.copySubtree(pair.getAST(), pair));
			}
		} else {
			throw new IllegalStateException("unknown annotation type: " + standAloneAnnotation); //$NON-NLS-1$
		}
		this.removeStandAloneAnnotation(declaration);
	}

	/**
	 * return whether the "nested" annotation container has been reduced to
	 * a single element (and the array initializer is converted to just
	 * the single remaining element) and can be further converted to the
	 * "stand-alone" annotation:
	 * <pre>
	 *     &#64;Outer(foo={&#64;Inner("zero"), &#64;Inner("one")}) =>
	 *     &#64;Outer(foo=&#64;Inner("zero")) =>
	 *     &#64;Inner("zero")
	 * </pre>
	 */
	private boolean nestedElementCanBeConvertedToStandAlone(ModifiedDeclaration declaration) {
		Annotation containerAnnotation = this.getContainerAnnotation(declaration);
		if (containerAnnotation == null) {
			return false;
		}
		if (containerAnnotation.isMarkerAnnotation()) {
			return false;
		}
		if (containerAnnotation.isSingleMemberAnnotation()) {
			if (this.getElementName().equals(VALUE)) {
				return (((SingleMemberAnnotation) containerAnnotation).getValue().getNodeType() != ASTNode.ARRAY_INITIALIZER)
						&& (this.zeroNestedAnnotationAdapter.getAnnotation(declaration) != null);
			}
			return false;
		}
		if (containerAnnotation.isNormalAnnotation()) {
			NormalAnnotation na = (NormalAnnotation) containerAnnotation;
			if (na.values().size() == 0) {
				return false;  // there are no elements present
			}
			if (na.values().size() != 1) {
				return false;  // there are other elements present - leave them all alone
			}
			MemberValuePair pair = (MemberValuePair) na.values().get(0);
			if (this.getElementName().equals(pair.getName().getFullyQualifiedName())) {
				return (pair.getValue().getNodeType() != ASTNode.ARRAY_INITIALIZER)
						&& (this.zeroNestedAnnotationAdapter.getAnnotation(declaration) != null);
			}
			return false;
		}
		throw new IllegalStateException("unknown annotation type: " + containerAnnotation); //$NON-NLS-1$
	}

	/**
	 * move the annotation in the container annotation at index=0
	 * to the stand-alone annotation
	 */
	private void convertLastElementAnnotationToStandAloneAnnotation(ModifiedDeclaration declaration) {
		Annotation last = this.zeroNestedAnnotationAdapter.getAnnotation(declaration);
		if (last == null) {
			throw new IllegalStateException("the last nested annotation is missing"); //$NON-NLS-1$
		} else if (last.isMarkerAnnotation()) {
			this.newStandAloneMarkerAnnotation(declaration);
		} else if (last.isSingleMemberAnnotation()) {
			Expression vv = ((SingleMemberAnnotation) last).getValue();
			vv = (Expression) ASTNode.copySubtree(vv.getAST(), vv);
			this.newStandAloneSingleMemberAnnotation(declaration).setValue(vv);
		} else if (last.isNormalAnnotation()) {
			NormalAnnotation newNA = this.newStandAloneNormalAnnotation(declaration);
			List<MemberValuePair> values = this.values(newNA);
			for (MemberValuePair pair : this.values((NormalAnnotation) last)) {
				values.add((MemberValuePair) ASTNode.copySubtree(pair.getAST(), pair));
			}
		} else {
			throw new IllegalStateException("unknown annotation type: " + last); //$NON-NLS-1$
		}
		this.removeContainerAnnotation(declaration);
	}

	private boolean standAloneAnnotationIsPresent(ModifiedDeclaration declaration) {
		return this.getStandAloneAnnotation(declaration) != null;
	}

	private Annotation getStandAloneAnnotation(ModifiedDeclaration declaration) {
		return this.standAloneAnnotationAdapter.getAnnotation(declaration);
	}

	private MarkerAnnotation newStandAloneMarkerAnnotation(ModifiedDeclaration declaration) {
		return this.standAloneAnnotationAdapter.newMarkerAnnotation(declaration);
	}

	private SingleMemberAnnotation newStandAloneSingleMemberAnnotation(ModifiedDeclaration declaration) {
		return this.standAloneAnnotationAdapter.newSingleMemberAnnotation(declaration);
	}

	private NormalAnnotation newStandAloneNormalAnnotation(ModifiedDeclaration declaration) {
		return this.standAloneAnnotationAdapter.newNormalAnnotation(declaration);
	}

	private void removeStandAloneAnnotation(ModifiedDeclaration declaration) {
		this.standAloneAnnotationAdapter.removeAnnotation(declaration);
	}

	private Annotation getNestedAnnotation(ModifiedDeclaration declaration) {
		return this.nestedAnnotationAdapter.getAnnotation(declaration);
	}

	private void moveNestedAnnotation(int newIndex, ModifiedDeclaration declaration) {
		this.nestedAnnotationAdapter.moveAnnotation(newIndex, declaration);
	}

	private void removeNestedAnnotation(ModifiedDeclaration declaration) {
		this.nestedAnnotationAdapter.removeAnnotation(declaration);
	}

	private boolean containerAnnotationIsPresent(ModifiedDeclaration declaration) {
		return this.getContainerAnnotation(declaration) != null;
	}

	private Annotation getContainerAnnotation(ModifiedDeclaration declaration) {
		return this.nestedAnnotationAdapter.getOuterAnnotationAdapter().getAnnotation(declaration);
	}

	private void removeContainerAnnotation(ModifiedDeclaration declaration) {
		this.nestedAnnotationAdapter.getOuterAnnotationAdapter().removeAnnotation(declaration);
	}

	private boolean zeroNestedAnnotationIsPresent(ModifiedDeclaration declaration) {
		return this.getZeroNestedAnnotation(declaration) != null;
	}

	private Annotation getZeroNestedAnnotation(ModifiedDeclaration declaration) {
		return this.zeroNestedAnnotationAdapter.getAnnotation(declaration);
	}

	private String getAnnotationName() {
		return this.nestedAnnotationAdapter.getAnnotationName();
	}

	private String getElementName() {
		return this.nestedAnnotationAdapter.getElementName();
	}

	@SuppressWarnings("unchecked")
	protected List<MemberValuePair> values(NormalAnnotation na) {
		return na.values();
	}


	// ********** annotation factories **********

	/**
	 * define interface that allows us to "re-use" the nasty code in
	 * #newAnnotation(AnnotationFactory, ModifiedDeclaration)
	 */
	private interface AnnotationFactory {
		Annotation newAnnotation(DeclarationAnnotationAdapter adapter, ModifiedDeclaration declaration);
	}

	private static final AnnotationFactory MARKER_ANNOTATION_FACTORY = new AnnotationFactory() {
		public Annotation newAnnotation(DeclarationAnnotationAdapter adapter, ModifiedDeclaration declaration) {
			return adapter.newMarkerAnnotation(declaration);
		}
		@Override
		public String toString() {
			return "MarkerAnnotationFactory"; //$NON-NLS-1$
		}
	};

	private static final AnnotationFactory SINGLE_MEMBER_ANNOTATION_FACTORY = new AnnotationFactory() {
		public Annotation newAnnotation(DeclarationAnnotationAdapter adapter, ModifiedDeclaration declaration) {
			return adapter.newSingleMemberAnnotation(declaration);
		}
		@Override
		public String toString() {
			return "SingleMemberAnnotationFactory"; //$NON-NLS-1$
		}
	};

	private static final AnnotationFactory NORMAL_ANNOTATION_FACTORY = new AnnotationFactory() {
		public Annotation newAnnotation(DeclarationAnnotationAdapter adapter, ModifiedDeclaration declaration) {
			return adapter.newNormalAnnotation(declaration);
		}
		@Override
		public String toString() {
			return "NormalAnnotationFactory"; //$NON-NLS-1$
		}
	};

}
