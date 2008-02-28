/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;


public class GenericOrmNamedNativeQuery extends AbstractOrmQuery<XmlNamedNativeQuery>
	implements OrmNamedNativeQuery
{

	protected String resultClass;

	protected String resultSetMapping;


	public GenericOrmNamedNativeQuery(OrmJpaContextNode parent) {
		super(parent);
	}
	
	public String getResultClass() {
		return this.resultClass;
	}

	public void setResultClass(String newResultClass) {
		String oldResultClass = this.resultClass;
		this.resultClass = newResultClass;
		queryResource().setResultClass(newResultClass);
		firePropertyChanged(NamedNativeQuery.RESULT_CLASS_PROPERTY, oldResultClass, newResultClass);
	}

	public String getResultSetMapping() {
		return this.resultSetMapping;
	}

	public void setResultSetMapping(String newResultSetMapping) {
		String oldResultSetMapping = this.resultSetMapping;
		this.resultSetMapping = newResultSetMapping;
		queryResource().setResultSetMapping(newResultSetMapping);
		firePropertyChanged(NamedNativeQuery.RESULT_SET_MAPPING_PROPERTY, oldResultSetMapping, newResultSetMapping);
	}


	@Override
	public void initialize(XmlNamedNativeQuery queryResource) {
		super.initialize(queryResource);
		this.resultClass = queryResource.getResultClass();
		this.resultSetMapping = queryResource.getResultSetMapping();
	}
	
	@Override
	public void update(XmlNamedNativeQuery queryResource) {
		super.update(queryResource);
		this.setResultClass(queryResource.getResultClass());
		this.setResultSetMapping(queryResource.getResultSetMapping());
	}

}
