/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> named native query
 */
public class GenericOrmNamedNativeQuery
	extends AbstractOrmQuery<XmlNamedNativeQuery>
	implements OrmNamedNativeQuery
{
	protected String resultClass;

	protected String resultSetMapping;


	public GenericOrmNamedNativeQuery(XmlContextNode parent, XmlNamedNativeQuery xmlNamedNativeQuery) {
		super(parent, xmlNamedNativeQuery);
		this.resultClass = xmlNamedNativeQuery.getResultClass();
		this.resultSetMapping = xmlNamedNativeQuery.getResultSetMapping();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setResultClass_(this.xmlQuery.getResultClass());
		this.setResultSetMapping_(this.xmlQuery.getResultSetMapping());
	}


	// ********** result class **********

	public String getResultClass() {
		return this.resultClass;
	}

	public void setResultClass(String resultClass) {
		this.setResultClass_(resultClass);
		this.xmlQuery.setResultClass(resultClass);
	}

	protected void setResultClass_(String resultClass) {
		String old = this.resultClass;
		this.resultClass = resultClass;
		this.firePropertyChanged(RESULT_CLASS_PROPERTY, old, resultClass);
	}

	public char getResultClassEnclosingTypeSeparator() {
		return '$';
	}


	// ********** result set mapping **********

	public String getResultSetMapping() {
		return this.resultSetMapping;
	}

	public void setResultSetMapping(String resultSetMapping) {
		this.setResultSetMapping_(resultSetMapping);
		this.xmlQuery.setResultSetMapping(resultSetMapping);
	}

	protected void setResultSetMapping_(String resultSetMapping) {
		String old = this.resultSetMapping;
		this.resultSetMapping = resultSetMapping;
		this.firePropertyChanged(RESULT_SET_MAPPING_PROPERTY, old, resultSetMapping);
	}


	// ********** validation **********

	@Override
	protected void validateQuery_(List<IMessage> messages, IReporter reporter) {
		// nothing yet
	}
}
