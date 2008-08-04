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
	protected NamedNativeQueryAnnotation getQueryResource() {
		return (NamedNativeQueryAnnotation) super.getQueryResource();
	}
	
	public String getResultClass() {
		return this.resultClass;
	}

	public void setResultClass(String newResultClass) {
		String oldResultClass = this.resultClass;
		this.resultClass = newResultClass;
		getQueryResource().setResultClass(newResultClass);
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
		getQueryResource().setResultSetMapping(newResultSetMapping);
		firePropertyChanged(NamedNativeQuery.RESULT_SET_MAPPING_PROPERTY, oldResultSetMapping, newResultSetMapping);
	}

	protected void setResultSetMapping_(String newResultSetMapping) {
		String oldResultSetMapping = this.resultSetMapping;
		this.resultSetMapping = newResultSetMapping;
		firePropertyChanged(NamedNativeQuery.RESULT_SET_MAPPING_PROPERTY, oldResultSetMapping, newResultSetMapping);
	}

	public void initialize(NamedNativeQueryAnnotation queryResource) {
		super.initialize(queryResource);
		this.resultClass = queryResource.getResultClass();
		this.resultSetMapping = queryResource.getResultSetMapping();
	}
	
	public void update(NamedNativeQueryAnnotation queryResource) {
		super.update(queryResource);
		this.setResultClass_(queryResource.getResultClass());
		this.setResultSetMapping_(queryResource.getResultSetMapping());
	}

}
