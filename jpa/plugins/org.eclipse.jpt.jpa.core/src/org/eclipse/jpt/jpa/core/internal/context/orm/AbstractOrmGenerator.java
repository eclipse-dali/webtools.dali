/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmGenerator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.resource.orm.XmlGenerator;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> sequence or table generator
 */
public abstract class AbstractOrmGenerator<X extends XmlGenerator>
	extends AbstractOrmXmlContextNode
	implements OrmGenerator
{
	protected final X xmlGenerator;

	protected String name;

	protected Integer specifiedInitialValue;
	protected int defaultInitialValue;

	protected Integer specifiedAllocationSize;
	protected int defaultAllocationSize;


	protected AbstractOrmGenerator(XmlContextNode parent, X xmlGenerator) {
		super(parent);
		this.xmlGenerator = xmlGenerator;
		this.name = xmlGenerator.getName();
		this.specifiedInitialValue = xmlGenerator.getInitialValue();
		this.specifiedAllocationSize = xmlGenerator.getAllocationSize();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.xmlGenerator.getName());
		this.setSpecifiedInitialValue_(this.xmlGenerator.getInitialValue());
		this.setSpecifiedAllocationSize_(this.xmlGenerator.getAllocationSize());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultInitialValue(this.buildDefaultInitialValue());
		this.setDefaultAllocationSize(this.buildDefaultAllocationSize());
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.setName_(name);
		this.xmlGenerator.setName(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** initial value **********

	public int getInitialValue() {
		return (this.specifiedInitialValue != null) ? this.specifiedInitialValue.intValue() : this.defaultInitialValue;
	}

	public Integer getSpecifiedInitialValue() {
		return this.specifiedInitialValue;
	}

	public void setSpecifiedInitialValue(Integer specifiedInitialValue) {
		this.setSpecifiedInitialValue_(specifiedInitialValue);
		this.xmlGenerator.setInitialValue(specifiedInitialValue);
	}

	protected void setSpecifiedInitialValue_(Integer specifiedInitialValue) {
		Integer old = this.specifiedInitialValue;
		this.specifiedInitialValue = specifiedInitialValue;
		this.firePropertyChanged(SPECIFIED_INITIAL_VALUE_PROPERTY, old, specifiedInitialValue);
	}

	public int getDefaultInitialValue() {
		return this.defaultInitialValue;
	}

	protected void setDefaultInitialValue(int defaultInitialValue) {
		int old = this.defaultInitialValue;
		this.defaultInitialValue = defaultInitialValue;
		this.firePropertyChanged(DEFAULT_INITIAL_VALUE_PROPERTY, old, defaultInitialValue);
	}

	protected abstract int buildDefaultInitialValue();


	// ********** allocation size **********

	public int getAllocationSize() {
		return (this.specifiedAllocationSize != null) ? this.specifiedAllocationSize.intValue() : this.defaultAllocationSize;
	}

	public Integer getSpecifiedAllocationSize() {
		return this.specifiedAllocationSize;
	}

	public void setSpecifiedAllocationSize(Integer specifiedAllocationSize) {
		this.setSpecifiedAllocationSize_(specifiedAllocationSize);
		this.xmlGenerator.setAllocationSize(specifiedAllocationSize);
	}

	protected void setSpecifiedAllocationSize_(Integer specifiedAllocationSize) {
		Integer old = this.specifiedAllocationSize;
		this.specifiedAllocationSize = specifiedAllocationSize;
		this.firePropertyChanged(SPECIFIED_ALLOCATION_SIZE_PROPERTY, old, specifiedAllocationSize);
	}

	public int getDefaultAllocationSize() {
		return this.defaultAllocationSize;
	}

	protected void setDefaultAllocationSize(int defaultAllocationSize) {
		int old = this.defaultAllocationSize;
		this.defaultAllocationSize = defaultAllocationSize;
		this.firePropertyChanged(DEFAULT_ALLOCATION_SIZE_PROPERTY, old, defaultAllocationSize);
	}

	protected int buildDefaultAllocationSize() {
		return DEFAULT_ALLOCATION_SIZE;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		if (StringTools.stringIsEmpty(this.name)){
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.GENERATOR_NAME_UNDEFINED,
					EMPTY_STRING_ARRAY,
					this,
					this.getNameTextRange()
				)
			);
		}
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlGenerator.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.xmlGenerator.getNameTextRange());
	}


	// ********** database stuff **********

	public Schema getDbSchema() {
		SchemaContainer dbSchemaContainer = this.getDbSchemaContainer();
		return (dbSchemaContainer == null) ? null : dbSchemaContainer.getSchemaForIdentifier(this.getSchema());
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em> catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getDbSchemaContainer() {
		String catalog = this.getCatalog();
		return (catalog != null) ? this.resolveDbCatalog(catalog) : this.getDatabase();
	}

	protected abstract String getSchema();

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getDbCatalog() {
		String catalog = this.getCatalog();
		return (catalog == null) ? null : this.resolveDbCatalog(catalog);
	}

	protected abstract String getCatalog();


	// ********** misc **********

	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}

	public X getXmlGenerator() {
		return this.xmlGenerator;
	}

	public boolean overrides(Generator other) {
		return MappingTools.nodeOverrides(this, other, PRECEDENCE_TYPE_LIST);
	}

	public boolean duplicates(Generator other) {
		return MappingTools.nodesAreDuplicates(this, other);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}

}
