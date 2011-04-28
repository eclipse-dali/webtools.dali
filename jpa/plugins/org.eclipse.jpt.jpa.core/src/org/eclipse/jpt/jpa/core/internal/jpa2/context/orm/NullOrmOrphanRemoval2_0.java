/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOrphanRemovalHolder2_0;

/**
 * Null <code>orm.xml</code> orphan removal
 */
public class NullOrmOrphanRemoval2_0
	extends AbstractOrmXmlContextNode
	implements OrmOrphanRemovable2_0
{
	public NullOrmOrphanRemoval2_0(OrmOrphanRemovalHolder2_0 parent) {
		super(parent);
	}


	// ********** orphan removal **********

	public boolean isDefaultOrphanRemoval() {
		return false;
	}

	public boolean isOrphanRemoval() {
		return false;
	}

	public Boolean getSpecifiedOrphanRemoval() {
		return null;
	}

	public void setSpecifiedOrphanRemoval(Boolean newSpecifiedOrphanRemoval) {
		throw new UnsupportedOperationException();
	}


	// ********** misc **********

	@Override
	public OrmOrphanRemovalHolder2_0 getParent() {
		return (OrmOrphanRemovalHolder2_0) super.getParent();
	}

	public TextRange getValidationTextRange() {
		return this.getParent().getValidationTextRange();
	}
}
