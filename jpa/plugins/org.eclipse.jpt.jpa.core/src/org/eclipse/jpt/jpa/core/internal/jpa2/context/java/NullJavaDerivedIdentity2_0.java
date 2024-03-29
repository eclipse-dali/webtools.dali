/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaSingleRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.IdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;

public class NullJavaDerivedIdentity2_0
	extends AbstractJavaContextModel<JavaSingleRelationshipMapping>
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

	public IdDerivedIdentityStrategy2_0 getIdDerivedIdentityStrategy() {
		return null;
	}

	public boolean usesIdDerivedIdentityStrategy() {
		return false;
	}

	public void setIdDerivedIdentityStrategy() {
		// NOP
	}


	// ********** maps ID derived identity strategy **********

	public MapsIdDerivedIdentityStrategy2_0 getMapsIdDerivedIdentityStrategy() {
		return null;
	}

	public boolean usesMapsIdDerivedIdentityStrategy() {
		return false;
	}

	public void setMapsIdDerivedIdentityStrategy() {
		// NOP
	}


	// ********** misc **********

	public JavaSingleRelationshipMapping2_0 getMapping() {
		return null;
	}

	public TextRange getValidationTextRange() {
		return this.parent.getValidationTextRange();
	}
}
