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

import org.eclipse.jpt.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.utility.TextRange;

public class VirtualXmlSequenceGenerator extends XmlSequenceGenerator
{
	JavaGeneratorContainer javaGeneratorHolder;

	protected boolean metadataComplete;
	
		
	public VirtualXmlSequenceGenerator(JavaGeneratorContainer javaGeneratorHolder, boolean metadataComplete) {
		super();
		this.javaGeneratorHolder = javaGeneratorHolder;
		this.metadataComplete = metadataComplete;
	}

	protected JavaSequenceGenerator getJavaSequenceGenerator() {
		return this.javaGeneratorHolder.getSequenceGenerator();
	}

	@Override
	public String getSequenceName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaSequenceGenerator().getSequenceName();
	}

	@Override
	public void setSequenceName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public Integer getAllocationSize() {
		if (this.metadataComplete) {
			return null;
		}
		return Integer.valueOf(this.getJavaSequenceGenerator().getAllocationSize());
	}

	@Override
	public void setAllocationSize(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Integer getInitialValue() {
		if (this.metadataComplete) {
			return null;
		}
		return Integer.valueOf(this.getJavaSequenceGenerator().getInitialValue());
	}

	@Override
	public void setInitialValue(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public String getName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.getJavaSequenceGenerator().getName();
	}

	@Override
	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public TextRange getNameTextRange() {
		return getValidationTextRange();
	}
	
	@Override
	public boolean isVirtual() {
		return true;
	}
}
