/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

public class NullJavaAssociationOverrideContainer extends AbstractJavaJpaContextNode
	implements JavaAssociationOverrideContainer
{
	protected final JavaAssociationOverrideContainer.Owner owner;
	
	public NullJavaAssociationOverrideContainer(JavaJpaContextNode parent, JavaAssociationOverrideContainer.Owner owner) {
		super(parent);
		this.owner = owner;
	}

	public void initialize(JavaResourcePersistentMember jrpm) {
		// no-op
	}
	
	public void update(JavaResourcePersistentMember jrpm) {
		// no-op
	}
	
	protected Owner getOwner() {
		return this.owner;
	}

	public JavaAssociationOverride getAssociationOverrideNamed(String name) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public ListIterator<JavaAssociationOverride> associationOverrides() {
		return EmptyListIterator.instance();
	}
	
	public int associationOverridesSize() {
		return 0;
	}

	public  ListIterator<JavaAssociationOverride> virtualAssociationOverrides() {
		return EmptyListIterator.instance();
	}
	
	public int virtualAssociationOverridesSize() {
		return 0;
	}
	
	public ListIterator<JavaAssociationOverride> specifiedAssociationOverrides() {
		return EmptyListIterator.instance();
	}
	
	public int specifiedAssociationOverridesSize() {
		return 0;
	}
	
	public void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	
	//********** Validation ********************************************

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}
}
