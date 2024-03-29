/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.DatabaseGenerator;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.db.Schema;

/**
 * Java sequence generator
 */
public abstract class AbstractJavaSequenceGenerator<A extends SequenceGeneratorAnnotation>
	extends AbstractJavaDatabaseGenerator<A>
	implements JavaSequenceGenerator
{
	protected String specifiedSequenceName;
	protected String defaultSequenceName;


	protected AbstractJavaSequenceGenerator(JavaGeneratorContainer parent, A generatorAnnotation) {
		super(parent, generatorAnnotation);
		this.specifiedSequenceName = generatorAnnotation.getSequenceName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedSequenceName_(this.generatorAnnotation.getSequenceName());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
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

	public void setSpecifiedSequenceName(String name) {
		this.generatorAnnotation.setSequenceName(name);
		this.setSpecifiedSequenceName_(name);
	}

	protected void setSpecifiedSequenceName_(String name) {
		String old = this.specifiedSequenceName;
		this.specifiedSequenceName = name;
		this.firePropertyChanged(SPECIFIED_SEQUENCE_NAME_PROPERTY, old, name);
	}

	public String getDefaultSequenceName() {
		return this.defaultSequenceName;
	}
	
	protected void setDefaultSequenceName(String name) {
		String old = this.defaultSequenceName;
		this.defaultSequenceName = name;
		this.firePropertyChanged(DEFAULT_SEQUENCE_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultSequenceName() {
		return null; // TODO the default sequence name is determined by the runtime provider...
	}
	

	// ********** Java completion proposals **********

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
			return this.getJavaCandidateSequences();
		}
		return null;
	}

	protected boolean sequenceNameTouches(int pos) {
		return this.generatorAnnotation.sequenceNameTouches(pos);
	}

	protected Iterable<String> getJavaCandidateSequences() {
		return new TransformationIterable<String, String>(this.getCandidateSequences(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected Iterable<String> getCandidateSequences() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.getSortedSequenceIdentifiers() : EmptyIterable.<String> instance();
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

	public void convertTo(EntityMappings entityMappings) {
		entityMappings.addSequenceGenerator().convertFrom(this);
	}

	public void delete() {
		this.parent.removeSequenceGenerator();	
	}
}
