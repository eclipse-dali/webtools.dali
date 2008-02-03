/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator;


public class XmlSequenceGenerator extends XmlGenerator<SequenceGenerator>
	implements ISequenceGenerator
{

	protected String specifiedSequenceName;

	protected String defaultSequenceName;

	protected XmlSequenceGenerator(IJpaContextNode parent) {
		super(parent);
	}

	public String getSequenceName() {
		return (this.getSpecifiedSequenceName() == null) ? getDefaultSequenceName() : this.getSpecifiedSequenceName();
	}

	public String getSpecifiedSequenceName() {
		return this.specifiedSequenceName;
	}

	public void setSpecifiedSequenceName(String newSpecifiedSequenceName) {
		String oldSpecifiedSequenceName = this.specifiedSequenceName;
		this.specifiedSequenceName = newSpecifiedSequenceName;
		generatorResource().setSequenceName(newSpecifiedSequenceName);
		firePropertyChanged(SPECIFIED_SEQUENCE_NAME_PROPERTY, oldSpecifiedSequenceName, newSpecifiedSequenceName);
	}

	public String getDefaultSequenceName() {
		return this.defaultSequenceName;
	}
	
	protected void setDefaultSequenceName(String newDefaultSequenceName) {
		String oldSpecifiedSequenceName = this.defaultSequenceName;
		this.defaultSequenceName = newDefaultSequenceName;
		firePropertyChanged(DEFAULT_SEQUENCE_NAME_PROPERTY, oldSpecifiedSequenceName, newDefaultSequenceName);
	}

	@Override
	public void initialize(SequenceGenerator sequenceGenerator) {
		super.initialize(sequenceGenerator);
		this.specifiedSequenceName = this.specifiedSequenceName(sequenceGenerator);
		//TODO default sequence name
	}
	
	@Override
	public void update(SequenceGenerator sequenceGenerator) {
		super.update(sequenceGenerator);
		this.setSpecifiedSequenceName(this.specifiedSequenceName(sequenceGenerator));
		//TODO default sequence name
	}
	
	protected String specifiedSequenceName(SequenceGenerator generatorResource) {
		return generatorResource.getSequenceName();
	}

}
