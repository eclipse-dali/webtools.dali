/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmQuery;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java.JavaStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.ParameterMode2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.StoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.resource.orm.XmlStoredProcedureParameter;

public class GenericOrmStoredProcedureParameter2_1
	extends AbstractOrmXmlContextNode
	implements OrmStoredProcedureParameter2_1
{

	protected final XmlStoredProcedureParameter xmlStoredProcedureParameter;

	protected String name;
	
	protected ParameterMode2_1 specifiedMode;
	protected ParameterMode2_1 defaultMode;

	protected String typeName;
	protected String fullyQualifiedTypeName;


	public GenericOrmStoredProcedureParameter2_1(
			OrmQuery parent,
			XmlStoredProcedureParameter xmlStoredProcedureParameter) {
		super(parent);
		this.xmlStoredProcedureParameter = xmlStoredProcedureParameter;
		this.name = xmlStoredProcedureParameter.getName();
		this.specifiedMode = this.buildSpecifiedMode();
		this.typeName = xmlStoredProcedureParameter.getClassName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.xmlStoredProcedureParameter.getName());
		this.setSpecifiedMode_(this.buildSpecifiedMode());
		this.setTypeName_(this.xmlStoredProcedureParameter.getClassName());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultMode(this.buildDefaultMode());
		this.setFullyQualifiedTypeName(this.buildFullyQualifiedTypeName());
	}

	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.setName_(name);
		this.xmlStoredProcedureParameter.setName(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** mode **********

	public ParameterMode2_1 getMode() {
		return (this.specifiedMode != null) ? this.specifiedMode : this.defaultMode;
	}

	public ParameterMode2_1 getSpecifiedMode() {
		return this.specifiedMode;
	}

	public void setSpecifiedMode(ParameterMode2_1 mode) {
		this.xmlStoredProcedureParameter.setMode(ParameterMode2_1.toOrmResourceModel(mode));
		this.setSpecifiedMode_(mode);
	}

	public void setSpecifiedMode_(ParameterMode2_1 mode) {
		ParameterMode2_1 old = this.specifiedMode;
		this.specifiedMode = mode;
		this.firePropertyChanged(SPECIFIED_MODE_PROPERTY, old, mode);
	}

	protected ParameterMode2_1 buildSpecifiedMode() {
		return ParameterMode2_1.fromOrmResourceModel(this.xmlStoredProcedureParameter.getMode());
	}

	public ParameterMode2_1 getDefaultMode() {
		return this.defaultMode;
	}

	protected void setDefaultMode(ParameterMode2_1 mode) {
		ParameterMode2_1 old = this.defaultMode;
		this.defaultMode = mode;
		this.firePropertyChanged(DEFAULT_MODE_PROPERTY, old, mode);
	}

	protected ParameterMode2_1 buildDefaultMode() {
		return ParameterMode2_1.IN;
	}


	// ********** type **********

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.xmlStoredProcedureParameter.setClassName(typeName);
		this.setTypeName_(typeName);
	}

	protected void setTypeName_(String typeName) {
		String old = this.typeName;
		this.typeName = typeName;
		this.firePropertyChanged(TYPE_NAME_PROPERTY, old, typeName);
	}

	public String getFullyQualifiedTypeName() {
		return this.fullyQualifiedTypeName;
	}

	protected void setFullyQualifiedTypeName(String typeName) {
		String old = this.fullyQualifiedTypeName;
		this.fullyQualifiedTypeName = typeName;
		this.firePropertyChanged(FULLY_QUALIFIED_TYPE_NAME_PROPERTY, old, typeName);
	}

	protected String buildFullyQualifiedTypeName() {
		return this.getMappingFileRoot().qualify(this.typeName);
	}

	public char getTypeEnclosingTypeSeparator() {
		return '$';
	}

	// ********** metadata conversion **********
 
	public void convertFrom(JavaStoredProcedureParameter2_1 javaParameter) {
		this.setName(javaParameter.getName());
		this.setSpecifiedMode(javaParameter.getMode());
		this.setTypeName(javaParameter.getTypeName());
	}

	// ********** validation **********

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlStoredProcedureParameter.getValidationTextRange();
		return (textRange != null) ? textRange : this.getQuery().getValidationTextRange();
	}

	public boolean isEquivalentTo(StoredProcedureParameter2_1 parameter) {
		return ObjectTools.equals(this.name, parameter.getName()) &&
				ObjectTools.equals(this.specifiedMode, parameter.getSpecifiedMode()) &&
				ObjectTools.equals(this.typeName, parameter.getTypeName());
	}

	// ********** misc **********

	@Override
	public OrmQuery getParent() {
		return (OrmQuery) super.getParent();
	}

	protected OrmQuery getQuery() {
		return this.getParent();
	}

	public XmlStoredProcedureParameter getXmlStoredProcedureParameter() {
		return this.xmlStoredProcedureParameter;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
		sb.append(this.typeName);
	}
}
