/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.OverrideAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Common behavior for virtual versions of
 *     javax.persistence.AttributeOverride
 * and
 *     javax.persistence.AssociationOverride
 */
public abstract class VirtualOverrideAnnotation
	extends NullAnnotation
	implements OverrideAnnotation
{
	private final String name;

	public VirtualOverrideAnnotation(JavaResourcePersistentMember parent, String name) {
		super(parent);
		this.name = name;
	}

	@Override
	protected OverrideAnnotation buildSupportingAnnotation() {
		return (OverrideAnnotation) super.buildSupportingAnnotation();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}

	// ***** name
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		if (name != null) {
			this.buildSupportingAnnotation().setName(name);
		}		
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
	
}
