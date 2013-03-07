/*******************************************************************************
 * Copyright (c) 2005, 2013 Red Hat, Inc. and others. All rights reserved.
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
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedPackage;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.command.CommandContext;

public class JDTPackage
	extends JDTAnnotatedElement 
	implements AnnotatedPackage
{
	protected JDTPackage(PackageDeclaration declaringPackage,
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext) {
		super(declaringPackage.getName().getFullyQualifiedName(), 
				compilationUnit,
				modifySharedDocumentCommandContext);
	}

	public JDTPackage(
			PackageDeclaration declaringPackage,
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringPackage.getName().getFullyQualifiedName(),
				compilationUnit, modifySharedDocumentCommandContext, annotationEditFormatter);
	}

	@Override
	public ModifiedDeclaration getModifiedDeclaration(CompilationUnit astRoot) {
		return new JDTModifiedDeclaration(this.getBodyDeclaration(astRoot));
	}

	public PackageDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		return astRoot.getPackage();
	}
}
