/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlSequenceGenerator;
import org.eclipse.jpt.core.internal.jpa2.context.java.Generic2_0JavaSequenceGenerator;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.utility.TextRange;

/**
 *  Generic2_0VirtualXmlSequenceGenerator
 */
public class Generic2_0VirtualXmlSequenceGenerator extends XmlSequenceGenerator
{
	protected final JavaGeneratorContainer javaGeneratorHolder;

	protected final boolean metadataComplete;
	
	protected final VirtualXmlSequenceGenerator virtualXmlSequenceGenerator;

	public Generic2_0VirtualXmlSequenceGenerator(JavaGeneratorContainer javaGeneratorHolder, boolean metadataComplete) {
		super();
		this.javaGeneratorHolder = javaGeneratorHolder;
		this.metadataComplete = metadataComplete;
		this.virtualXmlSequenceGenerator = new VirtualXmlSequenceGenerator(javaGeneratorHolder, metadataComplete);
	}

	protected JavaSequenceGenerator getJavaSequenceGenerator() {
		return this.javaGeneratorHolder.getSequenceGenerator();
	}
	
	@Override
	public String getCatalog() {
		return ((Generic2_0JavaSequenceGenerator)this.getJavaSequenceGenerator()).getCatalog();
	}
	
	@Override
	public void setCatalog(String newCatalog) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public String getSchema() {
		return ((Generic2_0JavaSequenceGenerator)this.getJavaSequenceGenerator()).getSchema();
	}
	
	@Override
	public void setSchema(String newSchema) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public Integer getAllocationSize() {
		return this.virtualXmlSequenceGenerator.getAllocationSize();
	}
	
	@Override
	public void setAllocationSize(Integer newAllocationSize) {
		this.virtualXmlSequenceGenerator.setAllocationSize(newAllocationSize);
	}
	
	@Override
	public Integer getInitialValue() {
		return this.virtualXmlSequenceGenerator.getInitialValue();
	}
	
	@Override
	public void setInitialValue(Integer newInitialValue) {
		this.virtualXmlSequenceGenerator.setInitialValue(newInitialValue);
	}
	
	@Override
	public String getName() {
		return this.virtualXmlSequenceGenerator.getName();
	}
	
	@Override
	public void setName(String newName) {
		this.virtualXmlSequenceGenerator.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlSequenceGenerator.getNameTextRange();
	}
	
	@Override
	public String getSequenceName() {
		return this.virtualXmlSequenceGenerator.getSequenceName();
	}
	
	@Override
	public void setSequenceName(String newSequenceName) {
		this.virtualXmlSequenceGenerator.setSequenceName(newSequenceName);
	}

}
