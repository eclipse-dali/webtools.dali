/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.CascadeType;

public class VirtualCascadeType extends AbstractJpaEObject implements CascadeType
{
	Cascade javaCascade;

	protected boolean metadataComplete;
	
	public VirtualCascadeType(Cascade javaCascade, boolean metadataComplete) {
		super();
		this.javaCascade = javaCascade;
		this.metadataComplete = metadataComplete;
	}

	public void update(Cascade javaCascade) {
		this.javaCascade = javaCascade;
	}

	public boolean isCascadeAll() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isAll();
	}

	public void setCascadeAll(@SuppressWarnings("unused") boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public boolean isCascadeMerge() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isMerge();
	}

	public void setCascadeMerge(@SuppressWarnings("unused") boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public boolean isCascadePersist() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isPersist();
	}

	public void setCascadePersist(@SuppressWarnings("unused") boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public boolean isCascadeRefresh() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isRefresh();
	}

	public void setCascadeRefresh(@SuppressWarnings("unused") boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public boolean isCascadeRemove() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaCascade.isRemove();
	}

	public void setCascadeRemove(@SuppressWarnings("unused") boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
