/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.VirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmReadOnlyUniqueConstraint;

public class GenericOrmVirtualUniqueConstraint
	extends AbstractOrmReadOnlyUniqueConstraint
	implements VirtualUniqueConstraint
{
	protected final ReadOnlyUniqueConstraint overriddenUniqueConstraint;


	public GenericOrmVirtualUniqueConstraint(JpaContextModel parent, ReadOnlyUniqueConstraint overriddenUniqueConstraint) {
		super(parent);
		this.overriddenUniqueConstraint = overriddenUniqueConstraint;
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.syncColumnNames();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.parent.getValidationTextRange();
	}


	// ********** misc **********

	public ReadOnlyUniqueConstraint getOverriddenUniqueConstraint() {
		return this.overriddenUniqueConstraint;
	}

	@Override
	protected Iterable<String> getResourceColumnNames() {
		return this.overriddenUniqueConstraint.getColumnNames();
	}
}
