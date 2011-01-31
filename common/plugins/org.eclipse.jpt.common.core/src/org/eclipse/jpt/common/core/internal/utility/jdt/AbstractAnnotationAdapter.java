/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.utility.internal.StringTools;

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

	public void newMarkerAnnotation() {
		this.edit(this.buildNewMarkerAnnotationEditor());
	}

	public void newSingleMemberAnnotation() {
		this.edit(this.buildNewSingleMemberAnnotationEditor());
	}

	public void newNormalAnnotation() {
		this.edit(this.buildNewNormalAnnotationEditor());
	}

	public void removeAnnotation() {
		this.edit(this.buildRemoveAnnotationEditor());
	}

	public ASTNode getAstNode(CompilationUnit astRoot) {
		return this.daa.getAstNode(this.annotatedElement.getModifiedDeclaration(astRoot));
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.daa);
	}


	// ********** internal methods **********

	protected void edit(AnnotatedElement.Editor editor) {
		this.annotatedElement.edit(editor);
	}


	// ********** factory methods **********

	protected AnnotatedElement.Editor buildNewMarkerAnnotationEditor() {
		return new NewMarkerAnnotationEditor(this.daa);
	}

	protected AnnotatedElement.Editor buildNewSingleMemberAnnotationEditor() {
		return new NewSingleMemberAnnotationEditor(this.daa);
	}

	protected AnnotatedElement.Editor buildNewNormalAnnotationEditor() {
		return new NewNormalAnnotationEditor(this.daa);
	}

	protected AnnotatedElement.Editor buildRemoveAnnotationEditor() {
		return new RemoveAnnotationEditor(this.daa);
	}


	// ********** member classes **********

	protected static class NewMarkerAnnotationEditor implements AnnotatedElement.Editor {
		private final DeclarationAnnotationAdapter daa;

		NewMarkerAnnotationEditor(DeclarationAnnotationAdapter daa) {
			super();
			this.daa = daa;
		}
		public void edit(ModifiedDeclaration declaration) {
			this.daa.newMarkerAnnotation(declaration);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	protected static class NewSingleMemberAnnotationEditor implements AnnotatedElement.Editor {
		private final DeclarationAnnotationAdapter daa;

		NewSingleMemberAnnotationEditor(DeclarationAnnotationAdapter daa) {
			super();
			this.daa = daa;
		}
		public void edit(ModifiedDeclaration declaration) {
			this.daa.newSingleMemberAnnotation(declaration);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	protected static class NewNormalAnnotationEditor implements AnnotatedElement.Editor {
		private final DeclarationAnnotationAdapter daa;

		NewNormalAnnotationEditor(DeclarationAnnotationAdapter daa) {
			super();
			this.daa = daa;
		}
		public void edit(ModifiedDeclaration declaration) {
			this.daa.newNormalAnnotation(declaration);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
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
			return StringTools.buildToStringFor(this);
		}
	}

}
