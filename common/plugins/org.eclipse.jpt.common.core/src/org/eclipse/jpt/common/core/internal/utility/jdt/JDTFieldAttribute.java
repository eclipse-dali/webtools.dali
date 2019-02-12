/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import java.util.List;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.FieldAttribute;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.common.utility.command.CommandContext;
import org.eclipse.jpt.common.utility.internal.command.DefaultCommandContext;

/**
 * Adapt and extend a JDT field.
 * Attribute based on a Java field, e.g.
 *     private int foo;
 */
public class JDTFieldAttribute
	extends JDTMember
	implements FieldAttribute
{

	// ********** constructors **********

	public JDTFieldAttribute(
			Type declaringType,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext) {
		this(declaringType, name, occurrence, compilationUnit, modifySharedDocumentCommandContext, DefaultAnnotationEditFormatter.instance());
	}
	
	public JDTFieldAttribute(
			Type declaringType,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringType, name, occurrence, compilationUnit, modifySharedDocumentCommandContext, annotationEditFormatter);
	}

	/**
	 * constructor for testing
	 */
	public JDTFieldAttribute(Type declaringType, String name, int occurrence, ICompilationUnit compilationUnit) {
		this(declaringType, name, occurrence, compilationUnit, DefaultCommandContext.instance(), DefaultAnnotationEditFormatter.instance());
	}


	// ********** Member/Attribute/FieldAttribute implementation **********

	@Override
	protected Type getDeclaringType() {
		return (Type) super.getDeclaringType();
	}

	@Override
	public FieldDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		return this.getSelectedDeclaration(astRoot, FIELD_DECLARATION_SELECTOR);
	}

	public String getAttributeName() {
		return this.getName();
	}

	public boolean isField() {
		return true;
	}


	// ********** internal **********

	protected TypeDeclaration getDeclaringTypeDeclaration(CompilationUnit astRoot) {
		// assume the declaring type is not an enum or annotation
		// since they do not have field or method declarations
		return this.getDeclaringType().getBodyDeclaration(astRoot);
	}

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
		String name = this.getName();
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
		TypeDeclaration typeDeclaration = this.getDeclaringTypeDeclaration(astRoot);
		// the declaration can be null if the resource is out of sync with the file system
		return (typeDeclaration == null) ? EMPTY_FIELD_DECLARATION_ARRAY : typeDeclaration.getFields();
	}
	protected static final FieldDeclaration[] EMPTY_FIELD_DECLARATION_ARRAY = new FieldDeclaration[0];

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
}
