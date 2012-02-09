/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaQuery;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> named native query
 */
public class GenericJavaNamedNativeQuery
	extends AbstractJavaQuery<NamedNativeQueryAnnotation>
	implements JavaNamedNativeQuery
{
	protected String resultClass;
	protected String fullyQualifiedResultClass;

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

	@Override
	public void update() {
		super.update();
		this.setFullyQualifiedResultClass(this.buildFullyQualifiedResultClass());
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

	public String getFullyQualifiedResultClass() {
		return this.fullyQualifiedResultClass;
	}

	protected void setFullyQualifiedResultClass(String resultClass) {
		String old = this.fullyQualifiedResultClass;
		this.fullyQualifiedResultClass = resultClass;
		this.firePropertyChanged(FULLY_QUALIFIED_RESULT_CLASS_PROPERTY, old, resultClass);
	}

	protected String buildFullyQualifiedResultClass() {
		return this.queryAnnotation.getFullyQualifiedResultClassName();
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


	// ********** validation **********

	@Override
	protected void validateQuery_(JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		// nothing yet
	}

	@Override
	public boolean isIdentical(Query query) {
		return super.isIdentical(query) &&
				StringTools.stringsAreEqual(this.getResultClass(), ((GenericJavaNamedNativeQuery)query).getResultClass()) &&
				StringTools.stringsAreEqual(this.getResultSetMapping(), ((GenericJavaNamedNativeQuery)query).getResultSetMapping());
	}

	// ********** misc **********

	public Class<NamedNativeQuery> getType() {
		return NamedNativeQuery.class;
	}
}
