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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.utility.internal.StringTools;

 /**
 * Adapt a member and a declaration annotation element adapter.
 */
public class MemberAnnotationElementAdapter<T>
	implements AnnotationElementAdapter<T>
{
	private final Member member;
	private final DeclarationAnnotationElementAdapter<T> daea;


	// ********** constructor **********

	public MemberAnnotationElementAdapter(Member member, DeclarationAnnotationElementAdapter<T> daea) {
		super();
		this.member = member;
		this.daea = daea;
	}


	// ********** AnnotationElementAdapter implementation **********

	public T getValue() {
		return this.daea.getValue(this.member.getModifiedDeclaration());
	}

	public T getValue(CompilationUnit astRoot) {
		return this.daea.getValue(this.member.getModifiedDeclaration(astRoot));
	}

	public void setValue(T value) {
		this.edit(this.buildSetValueEditor(value));
	}

	public Expression getExpression() {
		return this.daea.getExpression(this.member.getModifiedDeclaration());
	}

	public Expression getExpression(CompilationUnit astRoot) {
		return this.daea.getExpression(this.member.getModifiedDeclaration(astRoot));
	}

	public ASTNode getAstNode() {
		return this.daea.getAstNode(this.member.getModifiedDeclaration());
	}

	public ASTNode getAstNode(CompilationUnit astRoot) {
		return this.daea.getAstNode(this.member.getModifiedDeclaration(astRoot));
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.daea);
	}


	// ********** internal methods **********

	protected void edit(Member.Editor editor) {
		this.member.edit(editor);
	}

	protected Member.Editor buildSetValueEditor(T value) {
		return new SetValueEditor<T>(value, this.daea);
	}


	// ********** member classes **********

	protected static class SetValueEditor<T> implements Member.Editor {
		private final DeclarationAnnotationElementAdapter<T> daea;
		private final T value;

		SetValueEditor(T value, DeclarationAnnotationElementAdapter<T> daea) {
			super();
			this.value = value;
			this.daea = daea;
		}
		public void edit(ModifiedDeclaration declaration) {
			this.daea.setValue(this.value, declaration);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}

}
