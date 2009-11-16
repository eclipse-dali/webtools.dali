/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.utility.TextRange;

/**
 *  NullOrmOrphanRemovable2_0
 */
public class NullOrmOrphanRemoval2_0
			extends AbstractOrmXmlContextNode
			implements OrmOrphanRemovable2_0
{
	public NullOrmOrphanRemoval2_0(OrmOrphanRemovalHolder2_0 parent) {
		super(parent);
	}

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

	public void update() {
		// do nothing
	}

	public TextRange getValidationTextRange() {
		return null;
	}

}