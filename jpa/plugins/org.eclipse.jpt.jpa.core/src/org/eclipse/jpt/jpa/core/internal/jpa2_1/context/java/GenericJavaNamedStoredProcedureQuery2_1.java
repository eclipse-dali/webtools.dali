/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java;

import java.util.ArrayList;
import java.util.Vector;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaQuery;
import org.eclipse.jpt.jpa.core.jpa2_1.context.NamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.StoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaQueryContainer2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmQueryContainer2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.NamedStoredProcedureQueryAnnotation2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.StoredProcedureParameterAnnotation2_1;

/**
 * <code>orm.xml</code> named stored procedure query
 */
public class GenericJavaNamedStoredProcedureQuery2_1
	extends AbstractJavaQuery<JavaQueryContainer2_1, NamedStoredProcedureQueryAnnotation2_1>
	implements JavaNamedStoredProcedureQuery2_1
{
	protected String procedureName;

	protected final ContextListContainer<JavaStoredProcedureParameter2_1, StoredProcedureParameterAnnotation2_1> parameterContainer;

	protected final Vector<String> resultClasses = new Vector<String>();

	protected final Vector<String> resultSetMappings = new Vector<String>();


	public GenericJavaNamedStoredProcedureQuery2_1(JavaQueryContainer2_1 parent, NamedStoredProcedureQueryAnnotation2_1 queryAnnotation) {
		super(parent, queryAnnotation);
		this.procedureName = queryAnnotation.getProcedureName();
		this.parameterContainer = this.buildParameterContainer();
		this.initializeResultClasses();
		this.initializeResultSetMappings();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setProcedureName_(this.queryAnnotation.getProcedureName());
		this.syncParameters();
		this.syncResultClasses();
		this.syncResultSetMappings();
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateModels(this.getParameters(), monitor);
	}


	// ********* procedure name ********

	public String getProcedureName() {
		return this.procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.queryAnnotation.setProcedureName(procedureName);
		this.setProcedureName_(procedureName);
	}
	
	protected void setProcedureName_(String procedureName) {
		String old = this.procedureName;
		this.procedureName = procedureName;
		this.firePropertyChanged(PROCEDURE_NAME_PROPERTY, old, procedureName);
	}

	// ************ parameters ***********

	public ListIterable<JavaStoredProcedureParameter2_1> getParameters() {
		return this.parameterContainer;
	}

	public int getParametersSize() {
		return this.parameterContainer.size();
	}
	
	public JavaStoredProcedureParameter2_1 addParameter() {
		return this.addParameter(this.getParametersSize());
	}

	public JavaStoredProcedureParameter2_1 addParameter(int index) {
		StoredProcedureParameterAnnotation2_1 annotation = this.queryAnnotation.addParameter(index);
		return this.parameterContainer.addContextElement(index, annotation);
	}

	public void removeParameter(StoredProcedureParameter2_1 parameter) {
		this.removeParameter(this.parameterContainer.indexOf((JavaStoredProcedureParameter2_1) parameter));
	}

	public void removeParameter(int index) {
		this.queryAnnotation.removeParameter(index);
		this.parameterContainer.remove(index);
	}

	public void moveParameter(int targetIndex, int sourceIndex) {
		this.queryAnnotation.moveParameter(targetIndex, sourceIndex);
		this.parameterContainer.move(targetIndex, sourceIndex);
	}

	public JavaStoredProcedureParameter2_1 getParameter(int index) {
		return this.parameterContainer.get(index);
	}

	protected JavaStoredProcedureParameter2_1 buildParameter(StoredProcedureParameterAnnotation2_1 parameterAnnotation) {
		return this.getJpaFactory2_1().buildJavaStoredProcedureParameter(this, parameterAnnotation);
	}

	protected void syncParameters() {
		this.parameterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<StoredProcedureParameterAnnotation2_1> getParameterAnnotations() {
		return this.queryAnnotation.getParameters();
	}

	protected ContextListContainer<JavaStoredProcedureParameter2_1, StoredProcedureParameterAnnotation2_1> buildParameterContainer() {
		return this.buildSpecifiedContextListContainer(PARAMETERS_LIST, new ParameterContainerAdapter());
	}

	/**
	 * parameter container adapter
	 */
	public class ParameterContainerAdapter
		extends AbstractContainerAdapter<JavaStoredProcedureParameter2_1, StoredProcedureParameterAnnotation2_1>
	{
		public JavaStoredProcedureParameter2_1 buildContextElement(StoredProcedureParameterAnnotation2_1 resourceElement) {
			return GenericJavaNamedStoredProcedureQuery2_1.this.buildParameter(resourceElement);
		}
		public ListIterable<StoredProcedureParameterAnnotation2_1> getResourceElements() {
			return GenericJavaNamedStoredProcedureQuery2_1.this.getParameterAnnotations();
		}
		public StoredProcedureParameterAnnotation2_1 extractResourceElement(JavaStoredProcedureParameter2_1 contextElement) {
			return contextElement.getStoredProcedureParameter2_1Annotation();
		}
	}


	// *********** result classes **********

	public ListIterable<String> getResultClasses() {
		return IterableTools.cloneLive(this.resultClasses);
	}

	public int getResultClassesSize() {
		return this.resultClasses.size();
	}
	
	public String getResultClass(int index) {
		return this.resultClasses.get(index);
	}
	
	public void addResultClass(String resultClass) {
		this.addResultClass(this.resultClasses.size(), resultClass);
	}
	
	public void addResultClass(int index, String resultClass) {
		this.queryAnnotation.addResultClass(index, resultClass);
		this.addItemToList(index, resultClass, this.resultClasses, RESULT_CLASSES_LIST);
	}

	public void removeResultClass(String resultClass) {
		this.removeResultClass(this.resultClasses.indexOf(resultClass));
	}

	public void removeResultClass(int index) {
		this.queryAnnotation.removeResultClass(index);
		this.removeItemFromList(index, this.resultClasses, RESULT_CLASSES_LIST);
	}

	public void moveResultClass(int targetIndex, int sourceIndex) {
		this.queryAnnotation.moveResultClass(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.resultClasses, RESULT_CLASSES_LIST);
	}
	
	protected void initializeResultClasses() {
		for (String resultClass : this.getResourceResultClasses()) {
			this.resultClasses.add(resultClass);
		}
	}

	protected void syncResultClasses() {
		this.synchronizeList(this.getResourceResultClasses(), this.resultClasses, RESULT_CLASSES_LIST);
	}

	protected Iterable<String> getResourceResultClasses() {
		return this.queryAnnotation.getResultClasses();
	}


	// *********** result set mappings *********

	public ListIterable<String> getResultSetMappings() {
		return IterableTools.cloneLive(this.resultSetMappings);
	}

	public int getResultSetMappingsSize() {
		return this.resultSetMappings.size();
	}
	
	public String getResultSetMapping(int index) {
		return this.resultSetMappings.get(index);
	}
	
	public void addResultSetMapping(String resultSetMapping) {
		this.addResultSetMapping(this.resultSetMappings.size(), resultSetMapping);
	}
	
	public void addResultSetMapping(int index, String resultSetMapping) {
		this.queryAnnotation.addResultSetMapping(index, resultSetMapping);
		this.addItemToList(index, resultSetMapping, this.resultSetMappings, RESULT_SET_MAPPINGS_LIST);
	}

	public void removeResultSetMapping(String resultSetMapping) {
		this.removeResultSetMapping(this.resultSetMappings.indexOf(resultSetMapping));
	}

	public void removeResultSetMapping(int index) {
		this.queryAnnotation.removeResultSetMapping(index);
		this.removeItemFromList(index, this.resultSetMappings, RESULT_SET_MAPPINGS_LIST);
	}

	public void moveResultSetMapping(int targetIndex, int sourceIndex) {
		this.queryAnnotation.moveResultSetMapping(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.resultSetMappings, RESULT_SET_MAPPINGS_LIST);
	}
	
	protected void initializeResultSetMappings() {
		for (String resultSetMapping : this.getResourceResultSetMappings()) {
			this.resultSetMappings.add(resultSetMapping);
		}
	}

	protected void syncResultSetMappings() {
		this.synchronizeList(this.getResourceResultSetMappings(), this.resultSetMappings, RESULT_SET_MAPPINGS_LIST);
	}

	protected Iterable<String> getResourceResultSetMappings() {
		return this.queryAnnotation.getResultSetMappings();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.resultClasses);
		sb.append(this.resultSetMappings);
	}


	// ********** metadata conversion *********
	
	public void convertTo(OrmQueryContainer queryContainer) {
		((OrmQueryContainer2_1) queryContainer).addNamedStoredProcedureQuery().convertFrom(this);
	}

	public void delete() {
		this.parent.removeNamedStoredProcedureQuery(this);
	}


	// ********** validation **********

	@Override
	protected boolean isEquivalentTo_(Query other) {
		return super.isEquivalentTo_(other)
				&& this.isEquivalentTo_((NamedStoredProcedureQuery2_1) other);
	}

	protected boolean isEquivalentTo_(NamedStoredProcedureQuery2_1 other) {
		return ObjectTools.equals(this.procedureName, other.getProcedureName()) &&
				this.parametersAreEquivalentTo(other) &&
				IterableTools.elementsAreEqual(this.getResultClasses(), other.getResultClasses()) &&
				IterableTools.elementsAreEqual(this.getResultSetMappings(), other.getResultSetMappings());
	}

	protected boolean parametersAreEquivalentTo(NamedStoredProcedureQuery2_1 other) {
		// get fixed lists of the stored procedure parameters
		ArrayList<JavaStoredProcedureParameter2_1> parameter1 = ListTools.arrayList(this.getParameters());
		ArrayList<? extends StoredProcedureParameter2_1> parameter2 = ListTools.arrayList(other.getParameters());
		if (parameter1.size() != parameter2.size()) {
			return false;
		}
		for (int i = 0; i < parameter1.size(); i++) {
			if ( ! parameter1.get(i).isEquivalentTo(parameter2.get(i))) {
				return false;
			}
		}
		return true;
	}

	// ********** misc **********

	public Class<NamedStoredProcedureQuery2_1> getQueryType() {
		return NamedStoredProcedureQuery2_1.class;
	}
}
