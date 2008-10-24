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

import org.eclipse.jpt.core.context.java.JavaGeneratorHolder;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.utility.TextRange;

public class VirtualXmlSequenceGenerator extends AbstractJpaEObject implements XmlSequenceGenerator
{
	JavaGeneratorHolder javaGeneratorHolder;

	protected boolean metadataComplete;
	
		
	public VirtualXmlSequenceGenerator(JavaGeneratorHolder javaGeneratorHolder, boolean metadataComplete) {
		super();
		this.javaGeneratorHolder = javaGeneratorHolder;
		this.metadataComplete = metadataComplete;
	}

	protected JavaSequenceGenerator getJavaSequenceGenerator() {
		return this.javaGeneratorHolder.getSequenceGenerator();
	}

	public String getSequenceName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaSequenceGenerator().getSequenceName();
	}

	public void setSequenceName(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public Integer getAllocationSize() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaSequenceGenerator().getAllocationSize();
	}

	public void setAllocationSize(@SuppressWarnings("unused") Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public Integer getInitialValue() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaSequenceGenerator().getInitialValue();
	}

	public void setInitialValue(@SuppressWarnings("unused") Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaSequenceGenerator().getName();
	}

	public void setName(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public TextRange getNameTextRange() {
		return getValidationTextRange();
	}
	
	public boolean isVirtual() {
		return true;
	}
}
