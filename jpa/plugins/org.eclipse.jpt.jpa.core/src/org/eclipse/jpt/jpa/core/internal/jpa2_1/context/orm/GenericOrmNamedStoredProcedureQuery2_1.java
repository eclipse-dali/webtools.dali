/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm;

import java.util.ArrayList;
import java.util.Vector;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmQuery;
import org.eclipse.jpt.jpa.core.jpa2_1.context.NamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.StoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlStoredProcedureParameter;

public class GenericOrmNamedStoredProcedureQuery2_1
	extends AbstractOrmQuery<XmlNamedStoredProcedureQuery>
	implements OrmNamedStoredProcedureQuery2_1
{
	protected String procedureName;
	
	protected final ContextListContainer<OrmStoredProcedureParameter2_1, XmlStoredProcedureParameter> parameterContainer;

	protected final Vector<String> resultClasses = new Vector<String>();

	protected final Vector<String> resultSetMappings = new Vector<String>();


	public GenericOrmNamedStoredProcedureQuery2_1(JpaContextModel parent, XmlNamedStoredProcedureQuery xmlNamedStoredProcedureQuery) {
		super(parent, xmlNamedStoredProcedureQuery);
		this.procedureName = this.xmlQuery.getProcedureName();
		this.parameterContainer = this.buildParameterContainer();
		this.initializeResultClasses();
		this.initializeResultSetMappings();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setProcedureName_(this.xmlQuery.getProcedureName());
		this.syncParameters();
		this.syncResultClasses();
		this.syncResultSetMappings();
	}

	@Override
	public void update() {
		super.update();
		this.updateModels(this.getParameters());
	}


	// ********* procedure name ********

	public String getProcedureName() {
		return this.procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.xmlQuery.setProcedureName(procedureName);
		this.setProcedureName_(procedureName);
	}

	protected void setProcedureName_(String procedureName) {
		String old = this.procedureName;
		this.procedureName = procedureName;
		this.firePropertyChanged(PROCEDURE_NAME_PROPERTY, old, procedureName);
	}

	// ********** parameters **********

	public ListIterable<OrmStoredProcedureParameter2_1> getParameters() {
		return this.parameterContainer;
	}

	public int getParametersSize() {
		return this.parameterContainer.size();
	}

	public OrmStoredProcedureParameter2_1 addParameter() {
		return this.addParameter(this.getParametersSize());
	}

	public OrmStoredProcedureParameter2_1 addParameter(int index) {
		XmlStoredProcedureParameter xmlParameter = this.buildXmlStoredProcedureParameter();
		OrmStoredProcedureParameter2_1 parameter = this.parameterContainer.addContextElement(index, xmlParameter);
		this.xmlQuery.getParameters().add(index, xmlParameter);
		return parameter;
	}

	protected XmlStoredProcedureParameter buildXmlStoredProcedureParameter() {
		return OrmFactory.eINSTANCE.createXmlStoredProcedureParameter();
	}

	public void removeParameter(StoredProcedureParameter2_1 parameter) {
		this.removeParameter(this.parameterContainer.indexOf((OrmStoredProcedureParameter2_1) parameter));
	}

	public void removeParameter(int index) {
		this.parameterContainer.remove(index);
		this.xmlQuery.getParameters().remove(index);
	}

	public void moveParameter(int targetIndex, int sourceIndex) {
		this.parameterContainer.move(targetIndex, sourceIndex);
		this.xmlQuery.getParameters().move(targetIndex, sourceIndex);
	}

	public OrmStoredProcedureParameter2_1 getParameter(int index) {
		return this.parameterContainer.get(index);
	}

	protected OrmStoredProcedureParameter2_1 buildParameter(XmlStoredProcedureParameter xmlParameter) {
		return this.isOrmXml2_1Compatible() ?
				this.getContextModelFactory2_1().buildOrmStoredProcedureParameter(this, xmlParameter) : 
				null;
	}

	protected void syncParameters() {
		this.parameterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlStoredProcedureParameter> getXmlParameters() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlQuery.getParameters());
	}

	protected ContextListContainer<OrmStoredProcedureParameter2_1, XmlStoredProcedureParameter> buildParameterContainer() {
		return this.buildSpecifiedContextListContainer(PARAMETERS_LIST, new ParameterContainerAdapter());
	}

	/**
	 * parameter container adapter
	 */
	public class ParameterContainerAdapter
		extends AbstractContainerAdapter<OrmStoredProcedureParameter2_1, XmlStoredProcedureParameter>
	{
		public OrmStoredProcedureParameter2_1 buildContextElement(XmlStoredProcedureParameter resourceElement) {
			return GenericOrmNamedStoredProcedureQuery2_1.this.buildParameter(resourceElement);
		}
		public ListIterable<XmlStoredProcedureParameter> getResourceElements() {
			return GenericOrmNamedStoredProcedureQuery2_1.this.getXmlParameters();
		}
		public XmlStoredProcedureParameter extractResourceElement(OrmStoredProcedureParameter2_1 contextElement) {
			return contextElement.getXmlStoredProcedureParameter();
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
		this.addItemToList(index, resultClass, this.resultClasses, RESULT_CLASSES_LIST);
		this.xmlQuery.getResultClasses().add(index, resultClass);
	}

	public void removeResultClass(String resultClass) {
		this.removeResultClass(this.resultClasses.indexOf(resultClass));
	}

	public void removeResultClass(int index) {
		this.removeItemFromList(index, this.resultClasses, RESULT_CLASSES_LIST);
		this.xmlQuery.getResultClasses().remove(index);
	}

	public void moveResultClass(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.resultClasses, RESULT_CLASSES_LIST);
		this.xmlQuery.getResultClasses().move(targetIndex, sourceIndex);
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
		return this.xmlQuery.getResultClasses();
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
		this.addItemToList(index, resultSetMapping, this.resultSetMappings, RESULT_SET_MAPPINGS_LIST);
		this.xmlQuery.getResultSetMappings().add(index, resultSetMapping);
	}

	public void removeResultSetMapping(String resultSetMapping) {
		this.removeResultSetMapping(this.resultSetMappings.indexOf(resultSetMapping));
	}

	public void removeResultSetMapping(int index) {
		this.removeItemFromList(index, this.resultSetMappings, RESULT_SET_MAPPINGS_LIST);
		this.xmlQuery.getResultSetMappings().remove(index);
	}

	public void moveResultSetMapping(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.resultSetMappings, RESULT_SET_MAPPINGS_LIST);
		this.xmlQuery.getResultSetMappings().move(targetIndex, sourceIndex);
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
		return this.xmlQuery.getResultSetMappings();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.resultClasses);
		sb.append(this.resultSetMappings);
	}


	// ********** metadata conversion *********
	
	public void convertFrom(JavaNamedStoredProcedureQuery2_1 javaQuery) {
		super.convertFrom(javaQuery);
		this.setProcedureName(javaQuery.getProcedureName());
		for (JavaStoredProcedureParameter2_1 javaParameter : javaQuery.getParameters()) {
			this.addParameter().convertFrom(javaParameter);
		}
		for (String resultClass : javaQuery.getResultClasses()) {
			this.addResultClass(resultClass);
		}
		for (String resultSetMapping : javaQuery.getResultSetMappings()) {
			this.addResultSetMapping(resultSetMapping);
		}
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
		ArrayList<OrmStoredProcedureParameter2_1> parameter1 = ListTools.arrayList(this.getParameters());
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
