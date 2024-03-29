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
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovalMapping2_0;

/**
 * Null Java orphan removal
 */
public class NullJavaOrphanRemoval2_0
	extends AbstractJavaContextModel<OrphanRemovalMapping2_0>
	implements OrphanRemovable2_0
{
	public NullJavaOrphanRemoval2_0(OrphanRemovalMapping2_0 parent) {
		super(parent);
	}


	// ********** orphan removal **********

	public boolean isOrphanRemoval() {
		return false;
	}

	public Boolean getSpecifiedOrphanRemoval() {
		return null;
	}

	public boolean isDefaultOrphanRemoval() {
		return false;
	}

	public void setSpecifiedOrphanRemoval(Boolean newSpecifiedOrphanRemoval) {
		throw new UnsupportedOperationException();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.parent.getValidationTextRange();
	}
}
