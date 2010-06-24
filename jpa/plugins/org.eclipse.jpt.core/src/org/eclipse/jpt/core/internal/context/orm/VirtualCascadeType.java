/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.resource.orm.CascadeType;

public class VirtualCascadeType extends CascadeType
{
	protected Cascade javaCascade;
	
	
	public VirtualCascadeType(Cascade javaCascade) {
		super();
		this.javaCascade = javaCascade;
	}

	@Override
	public boolean isCascadeAll() {
		return this.javaCascade.isAll();
	}

	@Override
	public void setCascadeAll(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public boolean isCascadeMerge() {
		return this.javaCascade.isMerge();
	}

	@Override
	public void setCascadeMerge(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public boolean isCascadePersist() {
		return this.javaCascade.isPersist();
	}

	@Override
	public void setCascadePersist(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public boolean isCascadeRefresh() {
		return this.javaCascade.isRefresh();
	}

	@Override
	public void setCascadeRefresh(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public boolean isCascadeRemove() {
		return this.javaCascade.isRemove();
	}
}
