/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaQuery;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> named native query
 */
public class GenericJavaNamedNativeQuery
	extends AbstractJavaQuery<JavaQueryContainer, NamedNativeQueryAnnotation>
	implements JavaNamedNativeQuery
{
	protected String query;

	protected String resultClass;
	protected String fullyQualifiedResultClass;

	protected String resultSetMapping;


	public GenericJavaNamedNativeQuery(JavaQueryContainer parent, NamedNativeQueryAnnotation queryAnnotation) {
		super(parent, queryAnnotation);
		this.query = queryAnnotation.getQuery();
		this.resultClass = queryAnnotation.getResultClass();
		this.resultSetMapping = queryAnnotation.getResultSetMapping();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setQuery_(this.queryAnnotation.getQuery());
		this.setResultClass_(this.queryAnnotation.getResultClass());
		this.setResultSetMapping_(this.queryAnnotation.getResultSetMapping());
	}

	@Override
	public void update() {
		super.update();
		this.setFullyQualifiedResultClass(this.buildFullyQualifiedResultClass());
	}

	// ********** query **********

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.queryAnnotation.setQuery(query);
		this.setQuery_(query);
	}

	protected void setQuery_(String query) {
		String old = this.query;
		this.query = query;
		this.firePropertyChanged(QUERY_PROPERTY, old, query);
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

	// ********** metadata conversion *********
	
	public void convertTo(OrmQueryContainer queryContainer) {
		queryContainer.addNamedNativeQuery().convertFrom(this);
	}

	public void delete() {
		this.parent.removeNamedNativeQuery(this);
	}

	// ********** validation **********

	@Override
	public void validate(JpaJpqlQueryHelper queryHelper, List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateQuery(messages, reporter);
	}

	protected void validateQuery(List<IMessage> messages, IReporter reporter) {
		if (StringTools.isBlank(this.query)){
			messages.add(
				this.buildValidationMessage(
					this.getNameTextRange(),
					JptJpaCoreValidationMessages.QUERY_STATEMENT_UNDEFINED,
					this.name
				)
			);
		} 
	}

	@Override
	protected boolean isEquivalentTo(Query other) {
		return super.isEquivalentTo(other)
				&& this.isEquivalentTo((NamedNativeQuery) other);
	}
	
	protected boolean isEquivalentTo(NamedNativeQuery other) {
		return 	ObjectTools.equals(this.query, other.getQuery()) &&
				ObjectTools.equals(this.resultClass, other.getResultClass()) &&
				ObjectTools.equals(this.resultSetMapping, other.getResultSetMapping());
	}

	// ********** misc **********

	public Class<NamedNativeQuery> getType() {
		return NamedNativeQuery.class;
	}
}
