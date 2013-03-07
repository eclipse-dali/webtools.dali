/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.Enum;
import org.eclipse.jpt.common.core.utility.jdt.EnumConstant;
import org.eclipse.jpt.common.utility.command.CommandContext;

/**
 * Adapt and extend a JDT enum constant.
 */
public class JDTEnumConstant
	extends JDTMember
	implements EnumConstant
{

	// ********** constructors **********

	public JDTEnumConstant(
			Enum declaringEnum,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext) {
		this(declaringEnum, name, occurrence, compilationUnit, modifySharedDocumentCommandContext, DefaultAnnotationEditFormatter.instance());
	}
	
	public JDTEnumConstant(
			Enum declaringEnum,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringEnum, name, occurrence, compilationUnit, modifySharedDocumentCommandContext, annotationEditFormatter);
	}

	/**
	 * constructor for testing
	 */
	public JDTEnumConstant(Enum declaringEnum, String name, int occurrence, ICompilationUnit compilationUnit) {
		this(declaringEnum, name, occurrence, compilationUnit, CommandContext.Default.instance(), DefaultAnnotationEditFormatter.instance());
	}

	@Override
	protected Enum getDeclaringType() {
		return (Enum) super.getDeclaringType();
	}

	// ********** AnnotatedElement/EnumConstant implementation **********

	@Override
	public EnumConstantDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		return this.getSelectedDeclaration(astRoot);
	}

	// ********** internal **********

	protected EnumConstantDeclaration getSelectedDeclaration(CompilationUnit astRoot) {
		String name = this.getName();
		int occurrence = this.getOccurrence();
		int count = 0;
		for (EnumConstantDeclaration enumConstantDeclaration : this.getDeclaringTypeEnumConstantDeclarations(astRoot)) {
			if (enumConstantDeclaration.getName().getFullyQualifiedName().equals(name)) {
				count++;
				if (count == occurrence) {
					return enumConstantDeclaration;
				}
			}
		}
		// return null if the field is no longer in the source code;
		// this can happen when the context model has not yet
		// been synchronized with the resource model but is still
		// asking for an ASTNode (e.g. during a selection event)
		return null;
	}

	protected EnumConstantDeclaration[] getDeclaringTypeEnumConstantDeclarations(CompilationUnit astRoot) {
		List<EnumConstantDeclaration> enumConstants = enumConstants(this.getDeclaringTypeDeclaration(astRoot));
		return enumConstants.toArray(new EnumConstantDeclaration[enumConstants.size()]);
	}

	@SuppressWarnings("unchecked")
	private static List<EnumConstantDeclaration> enumConstants(EnumDeclaration ed) {
		return ed.enumConstants();
	}

	protected EnumDeclaration getDeclaringTypeDeclaration(CompilationUnit astRoot) {
		return this.getDeclaringType().getBodyDeclaration(astRoot);
	}
}
