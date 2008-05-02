/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;


public class GenericOrmSequenceGenerator extends AbstractOrmGenerator<XmlSequenceGenerator>
	implements OrmSequenceGenerator
{

	protected String specifiedSequenceName;

	protected String defaultSequenceName;

	public GenericOrmSequenceGenerator(OrmJpaContextNode parent) {
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
		getGeneratorResource().setSequenceName(newSpecifiedSequenceName);
		firePropertyChanged(SPECIFIED_SEQUENCE_NAME_PROPERTY, oldSpecifiedSequenceName, newSpecifiedSequenceName);
	}
	
	protected void setSpecifiedSequenceName_(String newSpecifiedSequenceName) {
		String oldSpecifiedSequenceName = this.specifiedSequenceName;
		this.specifiedSequenceName = newSpecifiedSequenceName;
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
	public void initialize(XmlSequenceGenerator sequenceGenerator) {
		super.initialize(sequenceGenerator);
		this.specifiedSequenceName = this.specifiedSequenceName(sequenceGenerator);
		//TODO default sequence name
	}
	
	@Override
	public void update(XmlSequenceGenerator sequenceGenerator) {
		super.update(sequenceGenerator);
		this.setSpecifiedSequenceName_(this.specifiedSequenceName(sequenceGenerator));
		//TODO default sequence name
	}
	
	protected String specifiedSequenceName(XmlSequenceGenerator generatorResource) {
		return generatorResource.getSequenceName();
	}

}
