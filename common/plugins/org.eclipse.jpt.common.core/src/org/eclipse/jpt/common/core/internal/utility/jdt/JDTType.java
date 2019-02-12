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
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.common.utility.command.CommandContext;

/**
 * Adapt and extend a JDT type.
 */
public class JDTType
	extends AbstractJDTType
	implements Type
{

	/**
	 * constructor for the compilation unit's primary type
	 */
	public JDTType(
			TypeDeclaration typeDeclaration,  // exclude annotations and enums
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext) {
		this(typeDeclaration, compilationUnit, modifySharedDocumentCommandContext, DefaultAnnotationEditFormatter.instance());
	}

	/**
	 * constructor for the compilation unit's primary type
	 */
	public JDTType(
			TypeDeclaration typeDeclaration,  // exclude annotations and enums
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext,
			AnnotationEditFormatter annotationEditFormatter) {
		this(null, typeDeclaration, 1, compilationUnit, modifySharedDocumentCommandContext, annotationEditFormatter);
	}

	/**
	 * constructor for nested types
	 */
	public JDTType(
			Type declaringType,
			TypeDeclaration typeDeclaration,  // exclude annotations and enums
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext) {
		this(declaringType, typeDeclaration, occurrence, compilationUnit, modifySharedDocumentCommandContext, DefaultAnnotationEditFormatter.instance());
	}

	/**
	 * constructor for nested types
	 */
	public JDTType(
			Type declaringType,
			TypeDeclaration typeDeclaration,  // exclude annotations and enums
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringType, typeDeclaration, occurrence, compilationUnit, modifySharedDocumentCommandContext, annotationEditFormatter);
	}

	/**
	 * constructor for testing
	 */
	public JDTType(Type declaringType, String name, int occurrence, ICompilationUnit compilationUnit) {
		super(declaringType, name, occurrence, compilationUnit);
	}


	// ********** Member/Type implementation **********

	/**
	 * find the type's body declaration in the specified AST
	 */
	@Override
	public TypeDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		return (TypeDeclaration) super.getBodyDeclaration(astRoot);
	}


	// ********** internal **********

	/**
	 * return the first top-level type in the specified AST with a matching name
	 */
	@Override
	protected TypeDeclaration getTopLevelTypeDeclaration(CompilationUnit astRoot) {
		return (TypeDeclaration) super.getTopLevelTypeDeclaration(astRoot);
	}

	@Override
	protected TypeDeclaration getTypeDeclaration(List<AbstractTypeDeclaration> typeDeclarations) {
		return (TypeDeclaration) super.getTypeDeclaration(typeDeclarations);
	}

	/**
	 * return the nested type with a matching name and occurrence
	 */
	@Override
	protected TypeDeclaration getNestedTypeDeclaration(TypeDeclaration declaringTypeDeclaration) {
		return this.getTypeDeclaration(declaringTypeDeclaration.getTypes());
	}

	/**
	 * return the type declaration corresponding to the type from the specified
	 * set of type declarations (match name and occurrence)
	 */
	@Override
	protected TypeDeclaration getTypeDeclaration(AbstractTypeDeclaration[] typeDeclarations) {
		return (TypeDeclaration) super.getTypeDeclaration(typeDeclarations);
	}

	@Override
	protected int getASTNodeType() {
		return ASTNode.TYPE_DECLARATION;
	}
}
