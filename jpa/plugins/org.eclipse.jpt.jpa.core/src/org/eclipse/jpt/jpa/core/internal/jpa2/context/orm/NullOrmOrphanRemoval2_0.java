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
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovalMapping2_0;

/**
 * Null <code>orm.xml</code> orphan removal
 */
public class NullOrmOrphanRemoval2_0
	extends AbstractOrmXmlContextModel<OrphanRemovalMapping2_0>
	implements OrphanRemovable2_0
{
	public NullOrmOrphanRemoval2_0(OrphanRemovalMapping2_0 parent) {
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


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.parent.getValidationTextRange();
	}
}
