/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.JavaResourceTypeBinding;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;

/**
 * binary attribute (field or property)
 */
abstract class BinaryAttribute
		extends BinaryMember
		implements JavaResourceAttribute {
	
	private int modifiers;
	
	private TypeBinding typeBinding;
	
	
	protected BinaryAttribute(JavaResourceType parent, Adapter adapter) {
		super(parent, adapter);
		
		IMember member = adapter.getElement();
		this.modifiers = buildModifiers(member);
		this.typeBinding = buildTypeBinding(createJdtTypeBinding(member));
	}
	
	
	// ******** overrides ********
	
	@Override
	public void update() {
		super.update();
		
		// TODO - update type binding ?
	}
	
	@Override
	protected void update(IMember member) {
		super.update(member);
		this.setModifiers(this.buildModifiers(member));
		
		// TODO - update type binding ?
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
	
	
	// ********** BinaryPersistentMember implementation **********
	
	private Adapter getAdapter() {
		return (Adapter) this.adapter;
	}
	
	
	// ********** JavaResourceAttribute implementation **********
	
	@Override
	public JavaResourceType getParent() {
		return (JavaResourceType) super.getParent();
	}
	
	public JavaResourceType getResourceType() {
		return this.getParent();
	}
	
	public String getName() {
		return this.getAdapter().getAttributeName();
	}
	
	
	// ***** modifiers *****
	
	public int getModifiers() {
		return this.modifiers;
	}
	
	private void setModifiers(int modifiers) {
		int old = this.modifiers;
		this.modifiers = modifiers;
		this.firePropertyChanged(MODIFIERS_PROPERTY, old, modifiers);
	}
	
	/**
	 * zero seems like a reasonable default...
	 */
	private int buildModifiers(IMember member) {
		try {
			return member.getFlags();
		} catch (JavaModelException ex) {
			JptCommonCorePlugin.log(ex);
			return 0;
		}
	}
	
	
	// ***** type binding *****
	
	public TypeBinding getTypeBinding() {
		return this.typeBinding;
	}
	
	protected ITypeBinding createJdtTypeBinding(IMember member) {
		IBinding jdtBinding = ASTTools.createBinding(member);
		return getJdtTypeBinding(jdtBinding);
	}
	
	protected abstract ITypeBinding getJdtTypeBinding(IBinding jdtBinding);
	
	protected TypeBinding buildTypeBinding(ITypeBinding jdtTypeBinding) {
		return new JavaResourceTypeBinding(jdtTypeBinding);
	}
	
	
	// ***** adapters *****

	/**
	 * Adapt an IField or IMethod.
	 */
	interface Adapter
			extends BinaryMember.Adapter {
		
		/**
		 * Return the field or getter method's "attribute" name
		 * (e.g. field "foo" -> "foo"; method "getFoo" -> "foo").
		 */
		String getAttributeName();
		
		/**
		 * Return the attribute's type signature.
		 */
		String getTypeSignature() throws JavaModelException;
	}
}
