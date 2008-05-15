/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.CommandExecutorProvider;

/**
 * Adapt and extend a JDT type.
 */
public class JDTType
	extends JDTMember
	implements Type
{

	/**
	 * constructor for the compilation unit's primary type
	 */
	public JDTType(
			TypeDeclaration typeDeclaration,  // exclude annotations and enums
			ICompilationUnit compilationUnit,
			CommandExecutorProvider modifySharedDocumentCommandExecutorProvider) {
		this(typeDeclaration, compilationUnit, modifySharedDocumentCommandExecutorProvider, DefaultAnnotationEditFormatter.instance());
	}

	/**
	 * constructor for the compilation unit's primary type
	 */
	public JDTType(
			TypeDeclaration typeDeclaration,  // exclude annotations and enums
			ICompilationUnit compilationUnit,
			CommandExecutorProvider modifySharedDocumentCommandExecutorProvider,
			AnnotationEditFormatter annotationEditFormatter) {
		this(null, typeDeclaration, 1, compilationUnit, modifySharedDocumentCommandExecutorProvider, annotationEditFormatter);
	}

	/**
	 * constructor for nested types
	 */
	public JDTType(
			Type declaringType,
			TypeDeclaration typeDeclaration,  // exclude annotations and enums
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutorProvider modifySharedDocumentCommandExecutorProvider) {
		this(declaringType, typeDeclaration, occurrence, compilationUnit, modifySharedDocumentCommandExecutorProvider, DefaultAnnotationEditFormatter.instance());
	}

	/**
	 * constructor for nested types
	 */
	public JDTType(
			Type declaringType,
			TypeDeclaration typeDeclaration,  // exclude annotations and enums
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutorProvider modifySharedDocumentCommandExecutorProvider,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringType, typeDeclaration.getName().getFullyQualifiedName(), occurrence, compilationUnit, modifySharedDocumentCommandExecutorProvider, annotationEditFormatter);
	}

	/**
	 * constructor for testing
	 */
	public JDTType(Type declaringType, String name, int occurrence, ICompilationUnit compilationUnit) {
		super(declaringType, name, occurrence, compilationUnit, CommandExecutorProvider.Default.instance(), DefaultAnnotationEditFormatter.instance());
	}


	// ********** Member/Type implementation **********

	public ITypeBinding getBinding(CompilationUnit astRoot) {
		return this.getBodyDeclaration(astRoot).resolveBinding();
	}

	/**
	 * find the type's body declaration in the specified AST
	 */
	public TypeDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		Type declaringType = this.getDeclaringType();
		return (declaringType == null) ?
				this.getTopLevelTypeDeclaration(astRoot)
			:
				this.getNestedTypeDeclaration(declaringType.getBodyDeclaration(astRoot));
	}

	public boolean isPersistable(CompilationUnit astRoot) {
		ITypeBinding binding = this.getBinding(astRoot);
		return (binding == null) ? false : JPTTools.typeIsPersistable(binding);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return new ASTNodeTextRange(this.getBodyDeclaration(astRoot).getName());
	}

	public TypeDeclaration[] getTypes(CompilationUnit astRoot) {
		return this.getBodyDeclaration(astRoot).getTypes();
	}

	public FieldDeclaration[] getFields(CompilationUnit astRoot) {
		return this.getBodyDeclaration(astRoot).getFields();
	}

	public MethodDeclaration[] getMethods(CompilationUnit astRoot) {
		return this.getBodyDeclaration(astRoot).getMethods();
	}


	// ********** internal **********

	/**
	 * return the first top-level type in the specified AST with a matching name
	 */
	protected TypeDeclaration getTopLevelTypeDeclaration(CompilationUnit astRoot) {
		return this.getTypeDeclaration(types(astRoot));
	}

	protected TypeDeclaration getTypeDeclaration(List<TypeDeclaration> typeDeclarations) {
		return this.getTypeDeclaration(typeDeclarations.toArray(new TypeDeclaration[typeDeclarations.size()]));
	}

	/**
	 * return the nested type with a matching name and occurrence
	 */
	protected TypeDeclaration getNestedTypeDeclaration(TypeDeclaration declaringTypeDeclaration) {
		return this.getTypeDeclaration(declaringTypeDeclaration.getTypes());
	}

	/**
	 * return the type declaration corresponding to the type from the specified
	 * set of type declarations (match name and occurrence)
	 */
	protected TypeDeclaration getTypeDeclaration(TypeDeclaration[] typeDeclarations) {
		String name = this.getName_();
		int occurrence = this.getOccurrence();
		int count = 0;
		for (TypeDeclaration typeDeclaration : typeDeclarations) {
			if (typeDeclaration.getName().getFullyQualifiedName().equals(name)) {
				count++;
				if (count == occurrence) {
					return typeDeclaration;
				}
			}
		}
		// return null if the type is no longer in the source code;
		// this can happen when the context model has not yet
		// been synchronized with the resource model but is still
		// asking for an ASTNode (e.g. during a selection event)
		return null;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected static List<TypeDeclaration> types(CompilationUnit astRoot) {
		return astRoot.types();
	}

}
