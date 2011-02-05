/*******************************************************************************
  * Copyright (c) 2010 Red Hat, Inc.
  * Distributed under license by Red Hat, Inc. All rights reserved.
  * This program is made available under the terms of the
  * Eclipse Public License v1.0 which accompanies this distribution,
  * and is available at http://www.eclipse.org/legal/epl-v10.html
  *
  * Contributor:
  *     Red Hat, Inc. - initial API and implementation
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
import org.eclipse.jpt.common.utility.CommandExecutor;

/**
 * @author Dmitry Geraskov
 *
 */

public class JDTPackage extends JDTAnnotatedElement implements AnnotatedPackage {
	
	
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
		return new ASTNodeTextRange(this.getBodyDeclaration(astRoot).getName());
	}
}
