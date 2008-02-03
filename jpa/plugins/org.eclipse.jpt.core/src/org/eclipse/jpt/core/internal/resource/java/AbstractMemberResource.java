/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;

public abstract class AbstractMemberResource<E extends Member> extends AbstractResource
{
	private final E member;
	
	
	protected AbstractMemberResource(JavaResource parent, E member) {
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
	
	/**
	 * Convenience method. Return whether the specified position touches the element.
	 * Returns false if the element does not exist
	 */
	protected boolean elementTouches(DeclarationAnnotationElementAdapter<?> elementAdapter, int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.member.annotationElementTextRange(elementAdapter, astRoot), pos);
	}
	
	/**
	 * Convenience method. Return whether element's text range is not
	 * null (meaning the element exists) and the specified position touches it.
	 */
	protected boolean elementTouches(ITextRange elementTextRange, int pos) {
		return (elementTextRange != null) && elementTextRange.touches(pos);
	}
}
