/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;

/**
 * Java sequence generator
 */
public abstract class AbstractJavaSequenceGenerator<A extends SequenceGeneratorAnnotation>
	extends AbstractJavaGenerator<A>
	implements JavaSequenceGenerator
{
	protected String specifiedSequenceName;
	protected String defaultSequenceName;


	protected AbstractJavaSequenceGenerator(JavaJpaContextNode parent, A generatorAnnotation) {
		super(parent, generatorAnnotation);
		this.specifiedSequenceName = generatorAnnotation.getSequenceName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedSequenceName_(this.generatorAnnotation.getSequenceName());
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
	public Iterator<String> connectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.sequenceNameTouches(pos, astRoot)) {
			return this.getJavaCandidateSequences(filter).iterator();
		}
		return null;
	}

	protected boolean sequenceNameTouches(int pos, CompilationUnit astRoot) {
		return this.generatorAnnotation.sequenceNameTouches(pos, astRoot);
	}

	protected Iterable<String> getJavaCandidateSequences(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateSequences(filter));
	}

	protected Iterable<String> getCandidateSequences(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateSequences(), filter);
	}

	protected Iterable<String> getCandidateSequences() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.getSortedSequenceIdentifiers() : EmptyIterable.<String> instance();
	}

}
