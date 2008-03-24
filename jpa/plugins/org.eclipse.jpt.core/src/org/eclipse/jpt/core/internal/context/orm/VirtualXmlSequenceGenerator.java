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

import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.utility.TextRange;

public class VirtualXmlSequenceGenerator extends AbstractJpaEObject implements XmlSequenceGenerator
{
	JavaSequenceGenerator javaSequenceGenerator;

	protected boolean metadataComplete;
	
		
	public VirtualXmlSequenceGenerator(JavaSequenceGenerator javaSequenceGenerator, boolean metadataComplete) {
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

	public void update(JavaSequenceGenerator javaSequenceGenerator) {
		this.javaSequenceGenerator = javaSequenceGenerator;
	}
	
	public TextRange nameTextRange() {
		return validationTextRange();
	}
}
