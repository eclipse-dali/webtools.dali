/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.DatabaseGenerator;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.jpa.db.Schema;

/**
 * <code>orm.xml</code> sequence generator
 */
public abstract class AbstractOrmSequenceGenerator
	extends AbstractOrmDbGenerator<XmlSequenceGenerator>
	implements OrmSequenceGenerator
{
	protected String specifiedSequenceName;
	protected String defaultSequenceName;


	protected AbstractOrmSequenceGenerator(JpaContextModel parent, XmlSequenceGenerator xmlSequenceGenerator) {
		super(parent, xmlSequenceGenerator);
		this.specifiedSequenceName = xmlSequenceGenerator.getSequenceName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedSequenceName_(this.xmlGenerator.getSequenceName());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultSequenceName(this.buildDefaultSequenceName());
	}


	// ********** initial value **********

	@Override
	protected int buildDefaultInitialValue() {
		return DEFAULT_INITIAL_VALUE;
	}
	

	// ********** sequence name **********

	public String getSequenceName() {
		return (this.specifiedSequenceName != null) ? this.specifiedSequenceName : this.defaultSequenceName;
	}

	public String getSpecifiedSequenceName() {
		return this.specifiedSequenceName;
	}

	public void setSpecifiedSequenceName(String specifiedSequenceName) {
		this.setSpecifiedSequenceName_(specifiedSequenceName);
		this.xmlGenerator.setSequenceName(specifiedSequenceName);
	}
	
	protected void setSpecifiedSequenceName_(String specifiedSequenceName) {
		String old = this.specifiedSequenceName;
		this.specifiedSequenceName = specifiedSequenceName;
		this.firePropertyChanged(SPECIFIED_SEQUENCE_NAME_PROPERTY, old, specifiedSequenceName);
	}

	public String getDefaultSequenceName() {
		return this.defaultSequenceName;
	}
	
	protected void setDefaultSequenceName(String defaultSequenceName) {
		String old = this.defaultSequenceName;
		this.defaultSequenceName = defaultSequenceName;
		this.firePropertyChanged(DEFAULT_SEQUENCE_NAME_PROPERTY, old, defaultSequenceName);
	}

	protected String buildDefaultSequenceName() {
		return null; // TODO the default sequence name is determined by the runtime provider...
	}
	
	// ********** misc **********
	
	public Class<SequenceGenerator> getGeneratorType() {
		return SequenceGenerator.class;
	}
	
	// ********** validation **********
	
	@Override
	protected boolean isEquivalentTo_(DatabaseGenerator other) {
		return super.isEquivalentTo_(other)
				&& this.isEquivalentTo_((SequenceGenerator) other);
	}
	
	protected boolean isEquivalentTo_(SequenceGenerator other) {
		return ObjectTools.equals(this.specifiedSequenceName, other.getSpecifiedSequenceName());
	}
	
	// ********** metadata conversion **********
	
	public void convertFrom(JavaSequenceGenerator javaGenerator) {
		super.convertFrom(javaGenerator);
		this.setSpecifiedSequenceName(javaGenerator.getSpecifiedSequenceName());
	}
	
	// ********** completion proposals **********

	/**
	 * called if the database is connected:
	 * sequenceName
	 */
	@Override
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		Iterable<String> result = super.getConnectedCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.sequenceNameTouches(pos)) {
			return this.getCandidateSequences();
		}
		return null;
	}

	protected boolean sequenceNameTouches(int pos) {
		return this.xmlGenerator.sequenceNameTouches(pos);
	}

	protected Iterable<String> getCandidateSequences() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.getSortedSequenceIdentifiers() : EmptyIterable.<String> instance();
	}
}
