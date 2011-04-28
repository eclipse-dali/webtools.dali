/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualOverride;

/**
 * Virtual Java override
 */
public abstract class AbstractJavaVirtualOverride<C extends JavaOverrideContainer>
	extends AbstractJavaJpaContextNode
	implements JavaVirtualOverride
{
	protected final String name;  // never null


	protected AbstractJavaVirtualOverride(C parent, String name) {
		super(parent);
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public boolean isVirtual() {
		return true;
	}

	public JavaOverride convertToSpecified() {
		return this.getContainer().convertOverrideToSpecified(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public C getParent() {
		return (C) super.getParent();
	}

	public C getContainer() {
		return this.getParent();
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getContainer().getValidationTextRange(astRoot);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
