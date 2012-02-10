/*******************************************************************************
 * Copyright (c) 2005, 2012 Red Hat, Inc. and others. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 *     Oracle
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedPackage;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.command.CommandExecutor;

public class JDTPackage
	extends JDTAnnotatedElement 
	implements AnnotatedPackage
{
	protected JDTPackage(PackageDeclaration declaringPackage,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		super(declaringPackage.getName().getFullyQualifiedName(), 
				compilationUnit,
				modifySharedDocumentCommandExecutor);
	}

	public JDTPackage(
			PackageDeclaration declaringPackage,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringPackage.getName().getFullyQualifiedName(),
				compilationUnit, modifySharedDocumentCommandExecutor, annotationEditFormatter);
	}

	@Override
	public ModifiedDeclaration getModifiedDeclaration(CompilationUnit astRoot) {
		return new JDTModifiedDeclaration(this.getBodyDeclaration(astRoot));
	}

	public IPackageBinding getBinding(CompilationUnit astRoot) {
		PackageDeclaration pd = this.getBodyDeclaration(astRoot);
		return (pd == null) ? null : pd.resolveBinding();
	}

	public PackageDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		return astRoot.getPackage();
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return ASTTools.buildTextRange(this.getBodyDeclaration(astRoot).getName());
	}
}
