/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.binary;

import java.util.Vector;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryQueryAnnotation;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.NamedStoredProcedureQueryAnnotation2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.StoredProcedureParameterAnnotation2_1;

/**
 * javax.persistence.NamedStoredProcedureQuery
 */
public final class BinaryNamedStoredProcedureQuery2_1Annotation
	extends BinaryQueryAnnotation
	implements NamedStoredProcedureQueryAnnotation2_1
{
	private String procedureName;
	private final Vector<StoredProcedureParameterAnnotation2_1> parameters;
	private final Vector<String> resultClasses;
	private final Vector<String> resultSetMappings;


	public BinaryNamedStoredProcedureQuery2_1Annotation(JavaResourceModel parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.procedureName = this.buildProcedureName();
		this.parameters = this.buildParameters();
		this.resultClasses = this.buildResultClasses();
		this.resultSetMappings = this.buildResultSetMappings();
	}

	@Override
	public void update() {
		super.update();
		this.setProcedureName_(this.buildProcedureName());
		this.updateParameters();
		this.updateResultClasses();
		this.updateResultSetMappings();
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	// ********** BinaryNamedStoredProcedureQueryAnnotation implementation **********

	@Override
	public String getNameElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY__NAME;
	}
	
	@Override
	public String getHintsElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY__HINTS;
	}

	// ********** NamedStoredProcedureQueryAnnotation implementation **********
	
	//****** procedure name ******
	public String getProcedureName() {
		return this.procedureName;
	}

	public void setProcedureName(String procedureName) {
		throw new UnsupportedOperationException();
	}

	private void setProcedureName_(String procedureName) {
		String old = this.procedureName;
		this.procedureName = procedureName;
		this.firePropertyChanged(PROCEDURE_NAME_PROPERTY, old, procedureName);
	}

	private String buildProcedureName() {
		return (String) this.getJdtMemberValue(this.getProcedureNameElementName());
	}

	public String getProcedureNameElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME;
	}

	public TextRange getProcedureNameTextRange() {
		throw new UnsupportedOperationException();
	}

	// ********* parameters ***********
	public ListIterable<StoredProcedureParameterAnnotation2_1> getParameters() {
		return IterableTools.cloneLive(this.parameters);
	}

	public int getParametersSize() {
		return this.parameters.size();
	}

	public StoredProcedureParameterAnnotation2_1 parameterAt(int index) {
		return this.parameters.get(index);
	}

	public StoredProcedureParameterAnnotation2_1 addParameter(int index) {
		throw new UnsupportedOperationException();
	}

	public void moveParameter(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeParameter(int index) {
		throw new UnsupportedOperationException();
	}
	
	private Vector<StoredProcedureParameterAnnotation2_1> buildParameters() {
		Object[] jdtParameters = this.getJdtMemberValues(this.getParametersElementName());
		Vector<StoredProcedureParameterAnnotation2_1> result = new Vector<StoredProcedureParameterAnnotation2_1>(jdtParameters.length);
		for (Object jdtParameter : jdtParameters) {
			result.add(new BinaryStoredProcedureParameterAnnotation2_1(this, (IAnnotation) jdtParameter));
		}
		return result;
	}

	private String getParametersElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY__PARAMETERS;
	}

	// TODO
	private void updateParameters() {
		throw new UnsupportedOperationException();
	}

	// *********** result classes **********
	public ListIterable<String> getResultClasses() {
		return IterableTools.cloneLive(this.resultClasses);
	}

	public int getResultClassesSize() {
		return this.resultClasses.size();
	}

	public String resultClassAt(int index) {
		return this.resultClasses.elementAt(index);
	}

	public void addResultClass(String resultClass) {
		throw new UnsupportedOperationException();
	}

	public void addResultClass(int index, String resultClass) {
		throw new UnsupportedOperationException();
	}

	public void moveResultClass(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public boolean resultClassTouches(int pos) {
		throw new UnsupportedOperationException();
	}

	public void removeResultClass(String resultClass) {
		throw new UnsupportedOperationException();
	}

	public void removeResultClass(int index) {
		throw new UnsupportedOperationException();
	}

	private Vector<String> buildResultClasses() {
		Object[] jdtResultClasses = this.getJdtMemberValues(this.getResultClassesElementName());
		Vector<String> result = new Vector<String>(jdtResultClasses.length);
		for (Object resultClass : jdtResultClasses) {
			result.add((String) resultClass);
		}
		return result;
	}
	
	private String getResultClassesElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASSES;
	}
	
	//TODO
	private void updateResultClasses() {
		throw new UnsupportedOperationException();
	}

	// *********** result set mappings **********
	public ListIterable<String> getResultSetMappings() {
		return IterableTools.cloneLive(this.resultSetMappings);
	}

	public int getResultSetMappingsSize() {
		return this.resultSetMappings.size();
	}

	public String resultSetMappingAt(int index) {
		return this.resultSetMappings.elementAt(index);
	}

	public void addResultSetMapping(String resultSetMapping) {
		throw new UnsupportedOperationException();
	}

	public void addResultSetMapping(int index, String resultSetMapping) {
		throw new UnsupportedOperationException();
	}

	public void moveResultSetMapping(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public boolean resultSetMappingTouches(int pos) {
		throw new UnsupportedOperationException();
	}

	public void removeResultSetMapping(String resultSetMapping) {
		throw new UnsupportedOperationException();
	}

	public void removeResultSetMapping(int index) {
		throw new UnsupportedOperationException();
	}

	private Vector<String> buildResultSetMappings() {
		Object[] jdtResultSetMappings = this.getJdtMemberValues(this.getResultSetMappingsElementName());
		Vector<String> result = new Vector<String>(jdtResultSetMappings.length);
		for (Object resultSetMapping : jdtResultSetMappings) {
			result.add((String) resultSetMapping);
		}
		return result;
	}
	
	private String getResultSetMappingsElementName() {
		return JPA2_1.NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPINGS;
	}

	//TODO
	private void updateResultSetMappings() {
		throw new UnsupportedOperationException();
	}

}
