/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.VirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaUniqueConstraint;

public class GenericJavaVirtualUniqueConstraint
	extends AbstractJavaUniqueConstraint<JpaContextModel>
	implements VirtualUniqueConstraint
{
	protected final UniqueConstraint overriddenUniqueConstraint;


	public GenericJavaVirtualUniqueConstraint(JpaContextModel parent, UniqueConstraint overriddenUniqueConstraint) {
		super(parent);
		this.overriddenUniqueConstraint = overriddenUniqueConstraint;
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.syncColumnNames();
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.parent.getValidationTextRange();
	}


	// ********** misc **********

	public UniqueConstraint getOverriddenUniqueConstraint() {
		return this.overriddenUniqueConstraint;
	}

	@Override
	protected Iterable<String> getResourceColumnNames() {
		return this.overriddenUniqueConstraint.getColumnNames();
	}
}
