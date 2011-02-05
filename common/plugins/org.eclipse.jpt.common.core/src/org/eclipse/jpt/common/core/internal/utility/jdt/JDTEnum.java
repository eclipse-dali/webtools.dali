/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.Enum;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.common.utility.CommandExecutor;

/**
 * Adapt and extend a JDT enum.
 */
public class JDTEnum
	extends AbstractJDTType
	implements Enum
{

	/**
	 * constructor for the compilation unit's primary type, an enum
	 */
	public JDTEnum(
			EnumDeclaration enumDeclaration,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		this(enumDeclaration, compilationUnit, modifySharedDocumentCommandExecutor, DefaultAnnotationEditFormatter.instance());
	}

	/**
	 * constructor for the compilation unit's primary type, an enum
	 */
	public JDTEnum(
			EnumDeclaration enumDeclaration,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		super(null, enumDeclaration, 1, compilationUnit, modifySharedDocumentCommandExecutor, annotationEditFormatter);
	}

	/**
	 * constructor for nested enums
	 */
	public JDTEnum(
			Type declaringType,
			EnumDeclaration enumDeclaration,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		this(declaringType, enumDeclaration, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, DefaultAnnotationEditFormatter.instance());
	}

	/**
	 * constructor for nested enums
	 */
	public JDTEnum(
			Type declaringType,
			EnumDeclaration enumDeclaration,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringType, enumDeclaration, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, annotationEditFormatter);
	}

	/**
	 * constructor for testing
	 */
	public JDTEnum(Type declaringType, String name, int occurrence, ICompilationUnit compilationUnit) {
		super(declaringType, name, occurrence, compilationUnit);
	}

	@Override
	public EnumDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		return (EnumDeclaration) super.getBodyDeclaration(astRoot);
	}

	public boolean isPersistable(CompilationUnit astRoot) {
		return true;
	}

	public EnumConstantDeclaration[] getEnumConstants(CompilationUnit astRoot) {
		List<EnumConstantDeclaration> enumConstants = enumConstants(getBodyDeclaration(astRoot));
		return enumConstants.toArray(new EnumConstantDeclaration[enumConstants.size()]);
	}

	@SuppressWarnings("unchecked")
	private static List<EnumConstantDeclaration> enumConstants(EnumDeclaration ed) {
		return ed.enumConstants();
	}

	
	// ********** internal **********

	@Override
	protected EnumDeclaration getTopLevelTypeDeclaration(CompilationUnit astRoot) {
		return (EnumDeclaration) super.getTopLevelTypeDeclaration(astRoot);
	}

	@Override
	protected EnumDeclaration getTypeDeclaration(List<AbstractTypeDeclaration> typeDeclarations) {
		return (EnumDeclaration) super.getTypeDeclaration(typeDeclarations);
	}

	@Override
	protected EnumDeclaration getNestedTypeDeclaration(TypeDeclaration declaringTypeDeclaration) {
		return this.getTypeDeclaration(getEnums(declaringTypeDeclaration));
	}

	@Override
	protected EnumDeclaration getTypeDeclaration(AbstractTypeDeclaration[] typeDeclarations) {
		return (EnumDeclaration) super.getTypeDeclaration(typeDeclarations);
	}

	@Override
	protected int getASTNodeType() {
		return ASTNode.ENUM_DECLARATION;
	}
}
