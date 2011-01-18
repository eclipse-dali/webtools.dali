/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmVirtualAttributeOverride;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.TypeMappingTools;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Specified <code>orm.xml</code> attribute override
 */
public class GenericOrmAttributeOverride
	extends AbstractOrmOverride<OrmAttributeOverrideContainer, XmlAttributeOverride>
	implements OrmAttributeOverride, OrmColumn.Owner
{
	protected final OrmColumn column;


	public GenericOrmAttributeOverride(OrmAttributeOverrideContainer parent, XmlAttributeOverride xmlOverride) {
		super(parent, xmlOverride);
		this.column = this.buildColumn();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.column.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.column.update();
	}


	// ********** specified/virtual **********

	@Override
	public OrmVirtualAttributeOverride convertToVirtual() {
		return (OrmVirtualAttributeOverride) super.convertToVirtual();
	}


	// ********** column **********

	public OrmColumn getColumn() {
		return this.column;
	}

	protected OrmColumn buildColumn() {
		return this.getContextNodeFactory().buildOrmColumn(this, this);
	}


	// ********** misc **********

	public void initializeFrom(ReadOnlyAttributeOverride oldOverride) {
		super.initializeFrom(oldOverride);
		this.column.initializeFrom(oldOverride.getColumn());
	}

	public void initializeFromVirtual(ReadOnlyAttributeOverride oldOverride) {
		super.initializeFromVirtual(oldOverride);
		this.column.initializeFromVirtual(oldOverride.getColumn());
	}


	// ********** column owner implementation **********

	public TypeMapping getTypeMapping() {
		return this.getContainer().getTypeMapping();
	}

	public String getDefaultTableName() {
		return this.getContainer().getDefaultTableName();
	}

	public Table resolveDbTable(String tableName) {
		return this.getContainer().resolveDbTable(tableName);
	}

	public String getDefaultColumnName() {
		return this.name;
	}

	public JptValidator buildColumnValidator(NamedColumn col, NamedColumnTextRangeResolver textRangeResolver) {
		return this.getContainer().buildColumnValidator(this, (BaseColumn) col, this, (BaseColumnTextRangeResolver) textRangeResolver);
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getContainer().tableNameIsInvalid(tableName);
	}

	public Iterator<String> candidateTableNames() {
		return this.getContainer().candidateTableNames();
	}

	public XmlColumn getXmlColumn() {
		return this.getXmlOverride().getColumn();
	}

	public XmlColumn buildXmlColumn() {
		XmlColumn xmlColumn = OrmFactory.eINSTANCE.createXmlColumn();
		this.getXmlOverride().setColumn(xmlColumn);
		return xmlColumn;
	}

	public void removeXmlColumn() {
		this.getXmlOverride().setColumn(null);
	}


	// ********** mapped by relationship **********

	protected boolean isMappedByRelationship() {
		return CollectionTools.contains(this.getMappedByRelationshipAttributeNames(), this.buildQualifier());
	}

	protected Iterable<String> getMappedByRelationshipAttributeNames() {
		return TypeMappingTools.getMappedByRelationshipAttributeNames(this.getTypeMapping());
	}

	/**
	 * overridable names are (usually?) qualified with a container mapping,
	 * which may also be the one mapped by a relationship
	 */
	protected String buildQualifier() {
		if (this.name == null) {
			return null;
		}
		int index = this.name.indexOf('.');
		return (index == -1) ? this.name : this.name.substring(0, index);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		// [JPA 2.0] if the column is specified, or if the override is not mapped by a relationship,
		// then the column is validated.
		// (In JPA 1.0, the column will always be validated, since the override is never mapped by a
		//  relationship)
		if (this.xmlColumnIsSpecified() || ! this.isMappedByRelationship()) {
			this.column.validate(messages, reporter);
		}

		// [JPA 2.0] if the override is mapped by a relationship, then that actually is in itself
		// a validation error
		// (We prevent implied overrides that are mapped by a relationship ... hopefully)
		// (In JPA 1.0, this will never occur)
		if (this.isMappedByRelationship()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ATTRIBUTE_OVERRIDE_MAPPED_BY_RELATIONSHIP_AND_SPECIFIED,
						EMPTY_STRING_ARRAY,
						this,
						this.getValidationTextRange()
					)
				);
		}
	}

	protected boolean xmlColumnIsSpecified() {
		return this.getXmlColumn() != null;
	}
}
