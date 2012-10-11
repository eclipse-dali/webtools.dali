/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Adapt an annotated element and a declaration annotation adapter.
 */
public abstract class AbstractAnnotationAdapter implements AnnotationAdapter {
	private final AnnotatedElement annotatedElement;
	private final DeclarationAnnotationAdapter daa;


	// ********** constructor **********

	public AbstractAnnotationAdapter(AnnotatedElement annotatedElement, DeclarationAnnotationAdapter daa) {
		super();
		this.annotatedElement = annotatedElement;
		this.daa = daa;
	}


	// ********** AnnotationAdapter implementation **********

	public Annotation getAnnotation(CompilationUnit astRoot) {
		return this.daa.getAnnotation(this.annotatedElement.getModifiedDeclaration(astRoot));
	}

	public MarkerAnnotation newMarkerAnnotation() {
		NewMarkerAnnotationEditor editor = this.buildNewMarkerAnnotationEditor();
		this.edit(editor);
		return editor.getResult();
	}

	public SingleMemberAnnotation newSingleMemberAnnotation() {
		NewSingleMemberAnnotationEditor editor = this.buildNewSingleMemberAnnotationEditor();
		this.edit(editor);
		return editor.getResult();
	}

	public NormalAnnotation newNormalAnnotation() {
		NewNormalAnnotationEditor editor = this.buildNewNormalAnnotationEditor();
		this.edit(editor);
		return editor.getResult();
	}

	public void removeAnnotation() {
		this.edit(this.buildRemoveAnnotationEditor());
	}

	public ASTNode getAstNode(CompilationUnit astRoot) {
		return this.daa.getAstNode(this.annotatedElement.getModifiedDeclaration(astRoot));
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.daa);
	}


	// ********** internal methods **********

	protected void edit(AnnotatedElement.Editor editor) {
		this.annotatedElement.edit(editor);
	}


	// ********** factory methods **********

	protected NewMarkerAnnotationEditor buildNewMarkerAnnotationEditor() {
		return new NewMarkerAnnotationEditor(this.daa);
	}

	protected NewSingleMemberAnnotationEditor buildNewSingleMemberAnnotationEditor() {
		return new NewSingleMemberAnnotationEditor(this.daa);
	}

	protected NewNormalAnnotationEditor buildNewNormalAnnotationEditor() {
		return new NewNormalAnnotationEditor(this.daa);
	}

	protected AnnotatedElement.Editor buildRemoveAnnotationEditor() {
		return new RemoveAnnotationEditor(this.daa);
	}


	// ********** member classes **********

	protected static class NewMarkerAnnotationEditor implements AnnotatedElement.Editor {
		private final DeclarationAnnotationAdapter daa;

		private MarkerAnnotation result;

		NewMarkerAnnotationEditor(DeclarationAnnotationAdapter daa) {
			super();
			this.daa = daa;
		}
		public void edit(ModifiedDeclaration declaration) {
			this.result = this.daa.newMarkerAnnotation(declaration);
		}
		public MarkerAnnotation getResult() {
			return this.result;
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	protected static class NewSingleMemberAnnotationEditor implements AnnotatedElement.Editor {
		private final DeclarationAnnotationAdapter daa;

		private SingleMemberAnnotation result;

		NewSingleMemberAnnotationEditor(DeclarationAnnotationAdapter daa) {
			super();
			this.daa = daa;
		}
		public void edit(ModifiedDeclaration declaration) {
			this.result = this.daa.newSingleMemberAnnotation(declaration);
		}
		public SingleMemberAnnotation getResult() {
			return this.result;
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	protected static class NewNormalAnnotationEditor implements AnnotatedElement.Editor {
		private final DeclarationAnnotationAdapter daa;

		private NormalAnnotation result;

		NewNormalAnnotationEditor(DeclarationAnnotationAdapter daa) {
			super();
			this.daa = daa;
		}
		public void edit(ModifiedDeclaration declaration) {
			this.result = this.daa.newNormalAnnotation(declaration);
		}
		public NormalAnnotation getResult() {
			return this.result;
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	protected static class RemoveAnnotationEditor implements AnnotatedElement.Editor {
		private final DeclarationAnnotationAdapter daa;

		RemoveAnnotationEditor(DeclarationAnnotationAdapter daa) {
			super();
			this.daa = daa;
		}
		public void edit(ModifiedDeclaration declaration) {
			this.daa.removeAnnotation(declaration);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

}
