/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * 
 */
public class GenericJavaSequenceGenerator extends AbstractJavaGenerator
	implements JavaSequenceGenerator
{
	protected String specifiedSequenceName;


	public GenericJavaSequenceGenerator(JavaJpaContextNode parent) {
		super(parent);
	}


	// ********** sequence name **********

	public String getSequenceName() {
		return (this.specifiedSequenceName != null) ? this.specifiedSequenceName : this.getDefaultSequenceName();
	}

	public String getSpecifiedSequenceName() {
		return this.specifiedSequenceName;
	}

	public void setSpecifiedSequenceName(String specifiedSequenceName) {
		String old = this.specifiedSequenceName;
		this.specifiedSequenceName = specifiedSequenceName;
		this.getResourceGenerator().setSequenceName(specifiedSequenceName);
		this.firePropertyChanged(SPECIFIED_SEQUENCE_NAME_PROPERTY, old, specifiedSequenceName);
	}

	protected void setSpecifiedSequenceName_(String specifiedSequenceName) {
		String old = this.specifiedSequenceName;
		this.specifiedSequenceName = specifiedSequenceName;
		this.firePropertyChanged(SPECIFIED_SEQUENCE_NAME_PROPERTY, old, specifiedSequenceName);
	}

	public String getDefaultSequenceName() {
		return null;
	}


	// ********** resource => context **********

	public void initialize(SequenceGeneratorAnnotation resourceSequenceGenerator) {
		super.initialize(resourceSequenceGenerator);
		this.specifiedSequenceName = resourceSequenceGenerator.getSequenceName();
	}
	
	public void update(SequenceGeneratorAnnotation resourceSequenceGenerator) {
		super.update(resourceSequenceGenerator);
		this.setSpecifiedSequenceName_(resourceSequenceGenerator.getSequenceName()); 
	}
	

	// ********** database stuff **********

	/**
	 * The JPA spec does not allow a sequence to have a schema.
	 */
	@Override
	protected String getSchema() {
		return this.getContextDefaultSchema();
	}

	/**
	 * The JPA spec does not allow a sequence to have a catalog.
	 */
	@Override
	protected String getCatalog() {
		return this.getContextDefaultCatalog();
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
			return this.javaCandidateSequences(filter);
		}
		return null;
	}

	protected boolean sequenceNameTouches(int pos, CompilationUnit astRoot) {
		return this.getResourceGenerator().sequenceNameTouches(pos, astRoot);
	}

	protected Iterator<String> javaCandidateSequences(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateSequences(filter));
	}

	protected Iterator<String> candidateSequences(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateSequences(), filter);
	}

	protected Iterator<String> candidateSequences() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.sortedSequenceIdentifiers() : EmptyIterator.<String> instance();
	}


	// ********** misc **********

	public Integer getDefaultInitialValue() {
		return SequenceGenerator.DEFAULT_INITIAL_VALUE;
	}
	
	@Override
	protected SequenceGeneratorAnnotation getResourceGenerator() {
		return (SequenceGeneratorAnnotation) super.getResourceGenerator();
	}

}
