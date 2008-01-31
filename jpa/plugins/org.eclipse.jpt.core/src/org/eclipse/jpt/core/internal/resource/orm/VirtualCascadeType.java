/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.jpt.core.internal.context.base.ICascade;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;

public class VirtualCascadeType extends JpaEObject implements CascadeType
{
	ICascade javaCascade;

	protected boolean metadataComplete;
	
	public VirtualCascadeType(ICascade javaCascade, boolean metadataComplete) {
		super();
		this.javaCascade = javaCascade;
		this.metadataComplete = metadataComplete;
	}

	public void update(ICascade javaCascade) {
		this.javaCascade = javaCascade;
	}

	public boolean isCascadeAll() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isAll();
	}

	public void setCascadeAll(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public boolean isCascadeMerge() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isMerge();
	}

	public void setCascadeMerge(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public boolean isCascadePersist() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isPersist();
	}

	public void setCascadePersist(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public boolean isCascadeRefresh() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isRefresh();
	}

	public void setCascadeRefresh(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public boolean isCascadeRemove() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isRemove();
	}

	public void setCascadeRemove(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
}
