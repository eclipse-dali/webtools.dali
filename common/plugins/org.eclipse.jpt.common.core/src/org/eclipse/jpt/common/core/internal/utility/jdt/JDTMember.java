/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.jdt.AbstractType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.CommandExecutor;

/**
 * Adapt and extend a JDT member with simplified annotation handling.
 */
public abstract class JDTMember extends JDTAnnotatedElement
	implements Member
{

	/** this will be null for the primary type */
	private final AbstractType declaringType;

	/**
	 * members can occur more than once in non-compiling source;
	 * count starts at 1; the primary type will have occurrence 1
	 */
	private final int occurrence;


	// ********** constructors **********
	
	protected JDTMember(
			AbstractType declaringType,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		this(declaringType, name, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, DefaultAnnotationEditFormatter.instance());
	}

	protected JDTMember(
			AbstractType declaringType,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		super(name, compilationUnit, modifySharedDocumentCommandExecutor, annotationEditFormatter);
		this.declaringType = declaringType;
		this.occurrence = occurrence;
	}

	//covariant override
	public abstract BodyDeclaration getBodyDeclaration(CompilationUnit astRoot);

	
	// ********** Member implementation **********

	@Override
	public ModifiedDeclaration getModifiedDeclaration(CompilationUnit astRoot) {
		return new JDTModifiedDeclaration(this.getBodyDeclaration(astRoot));
	}

	public boolean matches(String memberName, int occur) {
		return memberName.equals(this.getName()) && (occur == this.occurrence);
	}


	// ********** internal **********

	protected int getOccurrence() {
		return this.occurrence;
	}

	/**
	 * this will return null for a top-level type
	 */
	protected AbstractType getDeclaringType() {
		return this.declaringType;
	}

}
