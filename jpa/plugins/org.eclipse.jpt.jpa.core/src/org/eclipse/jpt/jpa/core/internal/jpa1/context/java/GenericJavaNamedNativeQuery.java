/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaQuery;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;

/**
 * <code>orm.xml</code> named native query
 */
public class GenericJavaNamedNativeQuery
	extends AbstractJavaQuery<NamedNativeQueryAnnotation>
	implements JavaNamedNativeQuery
{
	protected String resultClass;

	protected String resultSetMapping;


	public GenericJavaNamedNativeQuery(JavaJpaContextNode parent, NamedNativeQueryAnnotation queryAnnotation) {
		super(parent, queryAnnotation);
		this.resultClass = queryAnnotation.getResultClass();
		this.resultSetMapping = queryAnnotation.getResultSetMapping();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setResultClass_(this.queryAnnotation.getResultClass());
		this.setResultSetMapping_(this.queryAnnotation.getResultSetMapping());
	}


	// ********** result class **********

	public String getResultClass() {
		return this.resultClass;
	}

	public void setResultClass(String resultClass) {
		this.queryAnnotation.setResultClass(resultClass);
		this.setResultClass_(resultClass);
	}

	protected void setResultClass_(String resultClass) {
		String old = this.resultClass;
		this.resultClass = resultClass;
		this.firePropertyChanged(RESULT_CLASS_PROPERTY, old, resultClass);
	}

	public char getResultClassEnclosingTypeSeparator() {
		return '.';
	}


	// ********** result set mapping **********

	public String getResultSetMapping() {
		return this.resultSetMapping;
	}

	public void setResultSetMapping(String resultSetMapping) {
		this.queryAnnotation.setResultSetMapping(resultSetMapping);
		this.setResultSetMapping_(resultSetMapping);
	}

	protected void setResultSetMapping_(String resultSetMapping) {
		String old = this.resultSetMapping;
		this.resultSetMapping = resultSetMapping;
		this.firePropertyChanged(RESULT_SET_MAPPING_PROPERTY, old, resultSetMapping);
	}

}
