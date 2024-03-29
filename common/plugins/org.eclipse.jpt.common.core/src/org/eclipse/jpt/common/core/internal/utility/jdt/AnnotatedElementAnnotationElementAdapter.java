/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

 /**
 * Adapt an annotated element and a declaration annotation element adapter.
 */
public class AnnotatedElementAnnotationElementAdapter<T>
	implements AnnotationElementAdapter<T>
{
	private final AnnotatedElement annotatedElement;
	private final DeclarationAnnotationElementAdapter<T> daea;


	// ********** constructor **********

	public AnnotatedElementAnnotationElementAdapter(AnnotatedElement annotatedElement, DeclarationAnnotationElementAdapter<T> daea) {
		super();
		this.annotatedElement = annotatedElement;
		this.daea = daea;
	}


	// ********** AnnotationElementAdapter implementation **********

	public T getValue() {
		return this.daea.getValue(this.annotatedElement.getModifiedDeclaration());
	}

	public T getValue(CompilationUnit astRoot) {
		return this.daea.getValue(this.annotatedElement.getModifiedDeclaration(astRoot));
	}

	public T getValue(Annotation annotation) {
		return this.daea.getValue(annotation);
	}

	public void setValue(T value) {
		this.edit(this.buildSetValueEditor(value));
	}

	public Expression getExpression(CompilationUnit astRoot) {
		return this.daea.getExpression(this.annotatedElement.getModifiedDeclaration(astRoot));
	}

	public Expression getExpression(Annotation annotation) {
		return this.daea.getExpression(annotation);
	}

	public ASTNode getAstNode(CompilationUnit astRoot) {
		return this.daea.getAstNode(this.annotatedElement.getModifiedDeclaration(astRoot));
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.daea);
	}


	// ********** internal methods **********

	protected void edit(AnnotatedElement.Editor editor) {
		this.annotatedElement.edit(editor);
	}

	protected AnnotatedElement.Editor buildSetValueEditor(T value) {
		return new SetValueEditor<T>(value, this.daea);
	}


	// ********** member classes **********

	protected static class SetValueEditor<T> implements AnnotatedElement.Editor {
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
			return ObjectTools.toString(this);
		}
	}

}
