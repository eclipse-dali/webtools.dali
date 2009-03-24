/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;


public class GenericOrmNamedNativeQuery extends AbstractOrmQuery<XmlNamedNativeQuery>
	implements OrmNamedNativeQuery
{

	protected String resultClass;

	protected String resultSetMapping;


	public GenericOrmNamedNativeQuery(XmlContextNode parent, XmlNamedNativeQuery resourceQuery) {
		super(parent, resourceQuery);
	}
	
	public char getResultClassEnclosingTypeSeparator() {
		return '$';
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

	public String getResultSetMapping() {
		return this.resultSetMapping;
	}

	public void setResultSetMapping(String newResultSetMapping) {
		String oldResultSetMapping = this.resultSetMapping;
		this.resultSetMapping = newResultSetMapping;
		getResourceQuery().setResultSetMapping(newResultSetMapping);
		firePropertyChanged(NamedNativeQuery.RESULT_SET_MAPPING_PROPERTY, oldResultSetMapping, newResultSetMapping);
	}


	@Override
	protected void initialize(XmlNamedNativeQuery resourceQuery) {
		super.initialize(resourceQuery);
		this.resultClass = resourceQuery.getResultClass();
		this.resultSetMapping = resourceQuery.getResultSetMapping();
	}
	
	@Override
	public void update(XmlNamedNativeQuery resourceQuery) {
		super.update(resourceQuery);
		this.setResultClass(resourceQuery.getResultClass());
		this.setResultSetMapping(resourceQuery.getResultSetMapping());
	}

}
