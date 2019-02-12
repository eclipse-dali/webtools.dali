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

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.jdt.AbstractType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.command.CommandContext;

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
			CommandContext modifySharedDocumentCommandContext) {
		this(declaringType, name, occurrence, compilationUnit, modifySharedDocumentCommandContext, DefaultAnnotationEditFormatter.instance());
	}

	protected JDTMember(
			AbstractType declaringType,
			String name,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandContext modifySharedDocumentCommandContext,
			AnnotationEditFormatter annotationEditFormatter) {
		super(name, compilationUnit, modifySharedDocumentCommandContext, annotationEditFormatter);
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
