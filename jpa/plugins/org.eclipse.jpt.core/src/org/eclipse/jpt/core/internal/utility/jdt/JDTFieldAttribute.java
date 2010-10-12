/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import java.util.List;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.core.utility.jdt.FieldAttribute;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.CommandExecutor;

/**
 * Adapt and extend a JDT field.
 * Attribute based on a Java field, e.g.
 *     private int foo;
 */
public class JDTFieldAttribute
	extends JDTAttribute
	implements FieldAttribute
{

	// ********** constructors **********

	public JDTFieldAttribute(
			Type declaringType,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		this(declaringType, name, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, DefaultAnnotationEditFormatter.instance());
	}
	
	public JDTFieldAttribute(
			Type declaringType,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringType, name, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, annotationEditFormatter);
	}

	/**
	 * constructor for testing
	 */
	public JDTFieldAttribute(Type declaringType, String name, int occurrence, ICompilationUnit compilationUnit) {
		this(declaringType, name, occurrence, compilationUnit, CommandExecutor.Default.instance(), DefaultAnnotationEditFormatter.instance());
	}


	// ********** Member/Attribute/FieldAttribute implementation **********
	@Override
	public ModifiedDeclaration getModifiedDeclaration(CompilationUnit astRoot) {
		return new JDTModifiedDeclaration(this.getBodyDeclaration(astRoot));
	}

	public IVariableBinding getBinding(CompilationUnit astRoot) {
		return this.getFragment(astRoot).resolveBinding();
	}

	public FieldDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		return this.getSelectedDeclaration(astRoot, FIELD_DECLARATION_SELECTOR);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return new ASTNodeTextRange(this.getFragment(astRoot).getName());
	}

	public String getAttributeName() {
		return this.getName_();
	}

	public ITypeBinding getTypeBinding(CompilationUnit astRoot) {
		return this.getBodyDeclaration(astRoot).getType().resolveBinding();
	}

	@Override
	public boolean isField() {
		return true;
	}

	public boolean isPersistable(CompilationUnit astRoot) {
		IVariableBinding binding = this.getBinding(astRoot);
		return (binding == null) ? false : JPTTools.fieldIsPersistable(new JPTToolsAdapter(binding));
	}


	// ********** internal **********

	protected VariableDeclarationFragment getFragment(CompilationUnit astRoot) {
		return this.getSelectedDeclaration(astRoot, VARIABLE_DECLARATION_FRAGMENT_SELECTOR);
	}

	/**
	 * return either a FieldDeclaration or a VariableDeclarationFragment,
	 * depending on the specified selector;
	 * 
	 * handle multiple fields declared in a single statement:
	 *     private int foo, bar;
	 */
	protected <T extends ASTNode> T getSelectedDeclaration(CompilationUnit astRoot, Selector<T> selector) {
		String name = this.getName_();
		int occurrence = this.getOccurrence();
		int count = 0;
		for (FieldDeclaration fieldDeclaration : this.getDeclaringTypeFieldDeclarations(astRoot)) {
			for (VariableDeclarationFragment fragment : fragments(fieldDeclaration)) {
				if (fragment.getName().getFullyQualifiedName().equals(name)) {
					count++;
					if (count == occurrence) {
						return selector.select(fieldDeclaration, fragment);
					}
				}
			}
		}
		// return null if the field is no longer in the source code;
		// this can happen when the context model has not yet
		// been synchronized with the resource model but is still
		// asking for an ASTNode (e.g. during a selection event)
		return null;
	}

	protected FieldDeclaration[] getDeclaringTypeFieldDeclarations(CompilationUnit astRoot) {
		return this.getDeclaringTypeDeclaration(astRoot).getFields();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected static List<VariableDeclarationFragment> fragments(FieldDeclaration fd) {
		return fd.fragments();
	}


	// ********** Selector **********

	// I'm not quite sure this interface is worth the resulting obfuscation,
	// but, then, I kept changing both methods, so...  ~bjv
	protected interface Selector<T extends ASTNode> {
		T select(FieldDeclaration fieldDeclaration, VariableDeclarationFragment variableDeclarationFragment);
		String getDescription();
	}

	protected static final Selector<FieldDeclaration> FIELD_DECLARATION_SELECTOR =
		new Selector<FieldDeclaration>() {
			public FieldDeclaration select(FieldDeclaration fieldDeclaration, VariableDeclarationFragment variableDeclarationFragment) {
				return fieldDeclaration;
			}
			public String getDescription() {
				return "field declaration"; //$NON-NLS-1$
			}
			@Override
			public String toString() {
				return "FIELD_DECLARATION_SELECTOR"; //$NON-NLS-1$
			}
		};

	protected static final Selector<VariableDeclarationFragment> VARIABLE_DECLARATION_FRAGMENT_SELECTOR =
		new Selector<VariableDeclarationFragment>() {
			public VariableDeclarationFragment select(FieldDeclaration fieldDeclaration, VariableDeclarationFragment variableDeclarationFragment) {
				return variableDeclarationFragment;
			}
			public String getDescription() {
				return "variable declaration fragment"; //$NON-NLS-1$
			}
			@Override
			public String toString() {
				return "VARIABLE_DECLARATION_FRAGMENT_SELECTOR"; //$NON-NLS-1$
			}
		};


	// ********** JPTTools adapter **********

	/**
	 * JPTTools needs an adapter so it can work with either an IField
	 * or an IVariableBinding etc.
	 */
	protected static class JPTToolsAdapter implements JPTTools.FieldAdapter {
		private final IVariableBinding fieldBinding;

		protected JPTToolsAdapter(IVariableBinding fieldBinding) {
			super();
			if (fieldBinding == null) {
				throw new NullPointerException();
			}
			this.fieldBinding = fieldBinding;
		}

		public int getModifiers() {
			return this.fieldBinding.getModifiers();
		}

	}

}
