/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.core.resource.java.NamedNativeQueryAnnotation;


public class GenericJavaNamedNativeQuery extends AbstractJavaQuery
	implements JavaNamedNativeQuery
{

	protected String resultClass;

	protected String resultSetMapping;

	public GenericJavaNamedNativeQuery(JavaJpaContextNode parent) {
		super(parent);
	}

	@Override
	protected NamedNativeQueryAnnotation getResourceQuery() {
		return (NamedNativeQueryAnnotation) super.getResourceQuery();
	}
	
	public char getResultClassEnclosingTypeSeparator() {
		return '.';
	}
	
	public String getResultClass() {
		return this.resultClass;
	}

	public void setResultClass(String newResultClass) {
		String oldResultClass = this.resultClass;
		this.resultClass = newResultClass;
		getResourceQuery().setResultClass(newResultClass);
		firePropertyChanged(NamedNativeQuery.RESULT_CLASS_PROPERTY, oldResultClass, newResultClass);
	}
	
	protected void setResultClass_(String newResultClass) {
		String oldResultClass = this.resultClass;
		this.resultClass = newResultClass;
		firePropertyChanged(NamedNativeQuery.RESULT_CLASS_PROPERTY, oldResultClass, newResultClass);
	}

	public String getResultSetMapping() {
		return this.resultSetMapping;
	}

	public void setResultSetMapping(String newResultSetMapping) {
		String oldResultSetMapping = this.resultSetMapping;
		this.resultSetMapping = newResultSetMapping;
		getResourceQuery().setResultSetMapping(newResultSetMapping);
		firePropertyChanged(NamedNativeQuery.RESULT_SET_MAPPING_PROPERTY, oldResultSetMapping, newResultSetMapping);
	}

	protected void setResultSetMapping_(String newResultSetMapping) {
		String oldResultSetMapping = this.resultSetMapping;
		this.resultSetMapping = newResultSetMapping;
		firePropertyChanged(NamedNativeQuery.RESULT_SET_MAPPING_PROPERTY, oldResultSetMapping, newResultSetMapping);
	}

	public void initialize(NamedNativeQueryAnnotation resourceQuery) {
		super.initialize(resourceQuery);
		this.resultClass = resourceQuery.getResultClass();
		this.resultSetMapping = resourceQuery.getResultSetMapping();
	}
	
	public void update(NamedNativeQueryAnnotation resourceQuery) {
		super.update(resourceQuery);
		this.setResultClass_(resourceQuery.getResultClass());
		this.setResultSetMapping_(resourceQuery.getResultSetMapping());
	}

}
