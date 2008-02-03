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

import org.eclipse.jpt.core.internal.context.java.IJavaSequenceGenerator;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;

public class VirtualSequenceGenerator extends JpaEObject implements SequenceGenerator
{
	IJavaSequenceGenerator javaSequenceGenerator;

	protected boolean metadataComplete;
	
		
	public VirtualSequenceGenerator(IJavaSequenceGenerator javaSequenceGenerator, boolean metadataComplete) {
		super();
		this.javaSequenceGenerator = javaSequenceGenerator;
		this.metadataComplete = metadataComplete;
	}

	public String getSequenceName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaSequenceGenerator.getSequenceName();
	}

	public void setSequenceName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public Integer getAllocationSize() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaSequenceGenerator.getAllocationSize();
	}

	public void setAllocationSize(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Integer getInitialValue() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaSequenceGenerator.getInitialValue();
	}

	public void setInitialValue(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaSequenceGenerator.getName();
	}

	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public void update(IJavaSequenceGenerator javaSequenceGenerator) {
		this.javaSequenceGenerator = javaSequenceGenerator;
	}
}
