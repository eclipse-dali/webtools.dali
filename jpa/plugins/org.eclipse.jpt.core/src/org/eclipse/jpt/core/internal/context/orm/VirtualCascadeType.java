/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
	Cascade javaCascade;

	protected boolean metadataComplete;
	
	public VirtualCascadeType(Cascade javaCascade, boolean metadataComplete) {
		super();
		this.javaCascade = javaCascade;
		this.metadataComplete = metadataComplete;
	}

	@Override
	public boolean isCascadeAll() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isAll();
	}

	@Override
	public void setCascadeAll(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public boolean isCascadeMerge() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isMerge();
	}

	@Override
	public void setCascadeMerge(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public boolean isCascadePersist() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isPersist();
	}

	@Override
	public void setCascadePersist(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public boolean isCascadeRefresh() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isRefresh();
	}

	@Override
	public void setCascadeRefresh(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public boolean isCascadeRemove() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isRemove();
	}

	@Override
	public void setCascadeRemove(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
