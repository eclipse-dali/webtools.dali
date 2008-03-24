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

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Adapt a member and a declaration annotation adapter.
 */
public abstract class AbstractAnnotationAdapter implements AnnotationAdapter {
	private final Member member;
	private final DeclarationAnnotationAdapter daa;


	// ********** constructor **********

	public AbstractAnnotationAdapter(Member member, DeclarationAnnotationAdapter daa) {
		super();
		this.member = member;
		this.daa = daa;
	}


	// ********** AnnotationAdapter implementation **********

	public Annotation annotation() {
		return this.daa.getAnnotation(this.member.modifiedDeclaration());
	}

	public Annotation annotation(CompilationUnit astRoot) {
		return this.daa.getAnnotation(this.member.modifiedDeclaration(astRoot));
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

	public ASTNode astNode() {
		return this.daa.astNode(this.member.modifiedDeclaration());
	}

	public ASTNode astNode(CompilationUnit astRoot) {
		return this.daa.astNode(this.member.modifiedDeclaration(astRoot));
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.daa);
	}


	// ********** internal methods **********

	protected void edit(Member.Editor editor) {
		this.member.edit(editor);
	}


	// ********** factory methods **********

	protected Member.Editor buildNewMarkerAnnotationEditor() {
		return new NewMarkerAnnotationEditor(this.daa);
	}

	protected Member.Editor buildNewSingleMemberAnnotationEditor() {
		return new NewSingleMemberAnnotationEditor(this.daa);
	}

	protected Member.Editor buildNewNormalAnnotationEditor() {
		return new NewNormalAnnotationEditor(this.daa);
	}

	protected Member.Editor buildRemoveAnnotationEditor() {
		return new RemoveAnnotationEditor(this.daa);
	}


	// ********** member classes **********

	protected static class NewMarkerAnnotationEditor implements Member.Editor {
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


	protected static class NewSingleMemberAnnotationEditor implements Member.Editor {
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


	protected static class NewNormalAnnotationEditor implements Member.Editor {
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


	protected static class RemoveAnnotationEditor implements Member.Editor {
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
