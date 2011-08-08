/*******************************************************************************
 *  Copyright (c) 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryBaseDiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotation;

/**
 * org.eclipse.persistence.annotations.TenantDiscriminatorColumn
 */
public class BinaryEclipseLinkTenantDiscriminatorColumnAnnotation
	extends BinaryBaseDiscriminatorColumnAnnotation
	implements EclipseLinkTenantDiscriminatorColumnAnnotation
{
	private String contextProperty;
	private String table;
	private Boolean primaryKey;

	public BinaryEclipseLinkTenantDiscriminatorColumnAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.contextProperty = this.buildContextProperty();
		this.table = this.buildTable();
		this.primaryKey = this.buildPrimaryKey();
	}

	public String getAnnotationName() {
		return EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setContextProperty_(this.buildContextProperty());
		this.setTable_(this.buildTable());
		this.setPrimaryKey_(this.buildPrimaryKey());
	}

	// ********** BinaryBaseDiscriminatorColumnAnnotation implementation **********

	@Override
	protected String getDiscriminatorTypeElementName() {
		return EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE;
	}

	@Override
	protected String getLengthElementName() {
		return EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__LENGTH;
	}

	// ********** BinaryNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
	}


	//************* EclipseLinkTenantDiscriminatorColumnAnnotation implementation *************

	// ***** contextProperty
	public String getContextProperty() {
		return this.contextProperty;
	}

	public void setContextProperty(String contextProperty) {
		throw new UnsupportedOperationException();
	}

	private void setContextProperty_(String contextProperty) {
		String old = this.contextProperty;
		this.contextProperty = contextProperty;
		this.firePropertyChanged(CONTEXT_PROPERTY_PROPERTY, old, contextProperty);
	}

	private String buildContextProperty() {
		return (String) this.getJdtMemberValue(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__CONTEXT_PROPERTY);
	}

	public TextRange getContextPropertyTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** table
	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		throw new UnsupportedOperationException();
	}

	private void setTable_(String table) {
		String old = this.table;
		this.table = table;
		this.firePropertyChanged(TABLE_PROPERTY, old, table);
	}

	private String buildTable() {
		return (String) this.getJdtMemberValue(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__TABLE);
	}

	public TextRange getTableTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** primaryKey
	public Boolean getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(Boolean primaryKey) {
		throw new UnsupportedOperationException();
	}

	private void setPrimaryKey_(Boolean primaryKey) {
		Boolean old = this.primaryKey;
		this.primaryKey = primaryKey;
		this.firePropertyChanged(PRIMARY_KEY_PROPERTY, old, primaryKey);
	}

	private Boolean buildPrimaryKey() {
		return (Boolean) this.getJdtMemberValue(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN__PRIMARY_KEY);
	}

	public TextRange getPrimaryKeyTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
