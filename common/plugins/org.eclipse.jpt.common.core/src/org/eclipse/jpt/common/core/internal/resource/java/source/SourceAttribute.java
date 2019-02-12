/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jpt.common.core.internal.utility.jdt.JavaResourceTypeBinding;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;

/**
 * Java source attribute (field or method)
 */
abstract class SourceAttribute<A extends Attribute>
		extends SourceMember<A>
		implements JavaResourceAttribute {
	
	private int modifiers;
	
	private JavaResourceTypeBinding typeBinding;
	
	
	protected SourceAttribute(JavaResourceType parent, A attribute){
		super(parent, attribute);
	}
	
	
	@Override
	protected void initialize(IBinding binding) {
		super.initialize(binding);
		this.modifiers = buildModifiers(binding);
		this.typeBinding = buildTypeBinding(getJdtTypeBinding(binding));
	}
	
	
	// ******** overrides ********

	protected void resolveTypes(IBinding binding) {
		syncTypeBinding(getJdtTypeBinding(binding));
	}
	
	@Override
	protected void synchronizeWith(IBinding binding) {
		super.synchronizeWith(binding);
		setModifiers(buildModifiers(binding));
		syncTypeBinding(getJdtTypeBinding(binding));
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
	
	
	// ******** JavaResourceAttribute implementation ********
	
	@Override
	public JavaResourceType getParent() {
		return (JavaResourceType) super.getParent();
	}
	
	public JavaResourceType getResourceType() {
		return this.getParent();
	}
	
	public String getName() {
		return this.annotatedElement.getAttributeName();
	}
	
	
	// ***** modifiers *****
	
	public int getModifiers() {
		return this.modifiers;
	}
	
	private void setModifiers(int astModifiers) {
		int old = this.modifiers;
		this.modifiers = astModifiers;
		this.firePropertyChanged(MODIFIERS_PROPERTY, old, astModifiers);
	}
	
	/**
	 * zero seems like a reasonable default...
	 */
	private int buildModifiers(IBinding binding) {
		return (binding == null) ? 0 : binding.getModifiers();
	}
	
	
	// ***** type binding *****
	
	public TypeBinding getTypeBinding() {
		return this.typeBinding;
	}
	
	protected void syncTypeBinding(ITypeBinding binding) {
		if (this.typeBinding.isEquivalentTo(binding)) {
			return;
		}
		TypeBinding old = this.typeBinding;
		this.typeBinding = buildTypeBinding(binding);
		firePropertyChanged(TYPE_BINDING_PROPERTY, old, this.typeBinding);
	}
	
	protected JavaResourceTypeBinding buildTypeBinding(ITypeBinding binding) {
		return new JavaResourceTypeBinding(binding);
	}
	
	protected abstract ITypeBinding getJdtTypeBinding(IBinding binding);
}
