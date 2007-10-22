/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaNodeModel;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.JpaNodeModel;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;

public abstract class AbstractResource<E extends Member> extends JpaNodeModel implements JavaResource
{	
	private final E member;
	
	protected AbstractResource(IJpaNodeModel parent, E member) {
		super(parent);
		this.member = member;
	}
	
	public E getMember() {
		return this.member;
	}
	
	protected ITextRange elementTextRange(DeclarationAnnotationElementAdapter<?> elementAdapter, CompilationUnit astRoot) {
		return this.elementTextRange(this.member.annotationElementTextRange(elementAdapter, astRoot), astRoot);
	}
	
	/**
	 * Convenience method. If the specified element text range is null
	 * return the Java object's text range instead (which is usually the
	 * annotation's text range).
	 */
	protected ITextRange elementTextRange(ITextRange elementTextRange, CompilationUnit astRoot) {
		return (elementTextRange != null) ? elementTextRange : this.textRange(astRoot);
	}
	
}
