/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaSingleRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;

public class NullJavaDerivedIdentity2_0
	extends AbstractJavaJpaContextNode
	implements JavaDerivedIdentity2_0
{
	public NullJavaDerivedIdentity2_0(JavaSingleRelationshipMapping mapping) {
		super(mapping);
	}


	// ********** predominant derived identity strategy **********

	public DerivedIdentityStrategy2_0 getPredominantDerivedIdentityStrategy() {
		return null;
	}


	// ********** null derived identity strategy **********

	public boolean usesNullDerivedIdentityStrategy() {
		return true;
	}

	public void setNullDerivedIdentityStrategy() {
		// NOP
	}


	// ********** ID derived identity strategy **********

	public JavaIdDerivedIdentityStrategy2_0 getIdDerivedIdentityStrategy() {
		return null;
	}

	public boolean usesIdDerivedIdentityStrategy() {
		return false;
	}

	public void setIdDerivedIdentityStrategy() {
		// NOP
	}


	// ********** maps ID derived identity strategy **********

	public JavaMapsIdDerivedIdentityStrategy2_0 getMapsIdDerivedIdentityStrategy() {
		return null;
	}

	public boolean usesMapsIdDerivedIdentityStrategy() {
		return false;
	}

	public void setMapsIdDerivedIdentityStrategy() {
		// NOP
	}


	// ********** misc **********

	@Override
	public JavaSingleRelationshipMapping getParent() {
		return (JavaSingleRelationshipMapping) super.getParent();
	}

	public JavaSingleRelationshipMapping2_0 getMapping() {
		return null;
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getParent().getValidationTextRange(astRoot);
	}
}
