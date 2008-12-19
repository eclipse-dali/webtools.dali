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

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.Type;
import org.eclipse.jpt.utility.CommandExecutor;

/**
 * Combine behavior common to JDTFieldAttribute and JDTMethodAttribute.
 * Not so sure this is useful....
 */
public abstract class JDTAttribute
	extends JDTMember
	implements Attribute
{

	// ********** constructors **********

	protected JDTAttribute(
			Type declaringType,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		super(declaringType, name, occurrence, compilationUnit, modifySharedDocumentCommandExecutor);
	}

	protected JDTAttribute(
			Type declaringType,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringType, name, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, annotationEditFormatter);
	}


	// ********** Member/Attribute implementation **********

	public boolean isField() {
		return false;
	}

	public boolean isMethod() {
		return false;
	}


	// ********** internal **********

	protected TypeDeclaration getDeclaringTypeDeclaration(CompilationUnit astRoot) {
		// assume the declaring type is not an enum or annotation
		// since they do not have field or method declarations
		return this.getDeclaringType().getBodyDeclaration(astRoot);
	}

}
