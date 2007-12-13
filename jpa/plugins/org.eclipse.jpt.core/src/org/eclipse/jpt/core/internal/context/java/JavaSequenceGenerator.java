/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;


public class JavaSequenceGenerator extends JavaGenerator<SequenceGenerator>
	implements IJavaSequenceGenerator
{
	protected String specifiedSequenceName;

	public JavaSequenceGenerator(IJavaJpaContextNode parent) {
		super(parent);
	}

	@Override
	public void initializeFromResource(SequenceGenerator sequenceGenerator) {
		super.initializeFromResource(sequenceGenerator);
		this.specifiedSequenceName = this.specifiedSequenceName(sequenceGenerator);
	}
	
	public Integer getDefaultInitialValue() {
		return ISequenceGenerator.DEFAULT_INITIAL_VALUE;
	}
	
	public String getSequenceName() {
		return (this.getSpecifiedSequenceName() == null) ? getDefaultSequenceName() : this.getSpecifiedSequenceName();
	}

	public String getSpecifiedSequenceName() {
		return this.specifiedSequenceName;
	}


	public void setSpecifiedSequenceName(String newSpecifiedSequenceName) {
		String oldSpecifiedSequenceName = newSpecifiedSequenceName;
		this.specifiedSequenceName = newSpecifiedSequenceName;
		generatorResource().setSequenceName(newSpecifiedSequenceName);
		firePropertyChanged(SPECIFIED_SEQUENCE_NAME_PROPERTY, oldSpecifiedSequenceName, newSpecifiedSequenceName);
	}

	public String getDefaultSequenceName() {
		return null;
	}

	@Override
	public void update(SequenceGenerator sequenceGenerator) {
		super.update(sequenceGenerator);
		this.setSpecifiedSequenceName(this.specifiedSequenceName(sequenceGenerator)); 
	}
	
	protected String specifiedSequenceName(SequenceGenerator generatorResource) {
		return generatorResource.getSequenceName();
	}

}
