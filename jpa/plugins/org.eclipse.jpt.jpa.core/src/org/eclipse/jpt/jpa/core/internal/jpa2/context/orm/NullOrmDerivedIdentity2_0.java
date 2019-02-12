/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;

public class NullOrmDerivedIdentity2_0
	extends AbstractOrmXmlContextModel<OrmSingleRelationshipMapping2_0>
	implements OrmDerivedIdentity2_0
{
	public NullOrmDerivedIdentity2_0(OrmSingleRelationshipMapping2_0 parent) {
		super(parent);
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

	public OrmIdDerivedIdentityStrategy2_0 getIdDerivedIdentityStrategy() {
		return null;
	}

	public boolean usesIdDerivedIdentityStrategy() {
		return false;
	}

	public void setIdDerivedIdentityStrategy() {
		// NOP
	}


	// ********** maps ID derived identity strategy **********

	public OrmMapsIdDerivedIdentityStrategy2_0 getMapsIdDerivedIdentityStrategy() {
		return null;
	}

	public boolean usesMapsIdDerivedIdentityStrategy() {
		return false;
	}

	public void setMapsIdDerivedIdentityStrategy() {
		// NOP
	}


	// ********** misc **********

	public OrmSingleRelationshipMapping2_0 getMapping() {
		return this.parent;
	}

	public void initializeFrom(OrmDerivedIdentity2_0 oldDerivedIdentity) {
		// NOP
	}

	public TextRange getValidationTextRange() {
		return this.getMapping().getValidationTextRange();
	}
}
