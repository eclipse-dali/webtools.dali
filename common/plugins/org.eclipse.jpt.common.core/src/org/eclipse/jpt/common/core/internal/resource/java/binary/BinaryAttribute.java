/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.binary;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
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
	
	private String attributeName;
	
	private int modifiers;
	
	private TypeBinding typeBinding;
	
	
	protected BinaryAttribute(JavaResourceType parent, AttributeAdapter adapter) {
		super(parent, adapter);
		this.attributeName = adapter.getAttributeName();
		this.modifiers = buildModifiers();
		this.typeBinding = buildTypeBinding(adapter.getTypeBinding());
	}
	
	
	// ******** overrides ********
	
	@Override
	public void update() {
		super.update();
		updateAttributeName();
		updateModifiers();
		updateTypeBinding();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
	
	
	// ***** JavaResourceAttribute implementation *****
	
	@Override
	public JavaResourceType getParent() {
		return (JavaResourceType) super.getParent();
	}
	
	public JavaResourceType getResourceType() {
		return this.getParent();
	}
	
	public String getName() {
		return getAttributeName();
	}
	
	
	// ***** attribute name *****
	
	public String getAttributeName() {
		return this.attributeName;
	}
	
	protected void updateAttributeName() {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** modifiers *****
	
	public int getModifiers() {
		return this.modifiers;
	}
	
	
	/**
	 * zero seems like a reasonable default...
	 */
	private int buildModifiers() {
		try {
			return getElement().getFlags();
		}
		catch (JavaModelException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return 0;
		}
	}
	
	protected void updateModifiers() {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** type binding *****
	
	public TypeBinding getTypeBinding() {
		return this.typeBinding;
	}
	
	protected TypeBinding buildTypeBinding(ITypeBinding jdtTypeBinding) {
		return new JavaResourceTypeBinding(jdtTypeBinding);
	}
	
	protected void updateTypeBinding() {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** adapters *****

	/**
	 * Adapt an IField or IMethod.
	 */
	interface AttributeAdapter
			extends MemberAdapter {
		
		/**
		 * Return the field or getter method's "attribute" name
		 * (e.g. field "foo" -> "foo"; method "getFoo" -> "foo").
		 */
		String getAttributeName();
		
		/**
		 * Return the attribute's type binding.
		 */
		ITypeBinding getTypeBinding();
	}
}
