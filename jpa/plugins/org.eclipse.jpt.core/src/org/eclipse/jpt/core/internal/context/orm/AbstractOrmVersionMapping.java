/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractOrmVersionMapping<T extends XmlVersion>
	extends AbstractOrmAttributeMapping<T>
	implements OrmVersionMapping
{
	protected final OrmColumn column;

	protected OrmConverter converter;
	
	protected final OrmConverter nullConverter;
	
	protected AbstractOrmVersionMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.column = getXmlContextNodeFactory().buildOrmColumn(this, this);
		this.column.initialize(this.getResourceColumn());//TODO pass in to constructor
		this.nullConverter = this.getXmlContextNodeFactory().buildOrmNullConverter(this);
		this.converter = this.buildConverter(this.getResourceConverterType());
	}

	public int getXmlSequence() {
		return 30;
	}

	public String getKey() {
		return MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmVersionMapping(this);
	}

	@Override
	public void initializeFromOrmColumnMapping(OrmColumnMapping oldMapping) {
		super.initializeFromOrmColumnMapping(oldMapping);
		getColumn().initializeFrom(oldMapping.getColumn());
	}

	public OrmColumn getColumn() {
		return this.column;
	}
	
	public OrmConverter getConverter() {
		return this.converter;
	}
	
	protected String getConverterType() {
		return this.converter.getType();
	}
	
	public void setConverter(String converterType) {
		if (this.valuesAreEqual(getConverterType(), converterType)) {
			return;
		}
		OrmConverter oldConverter = this.converter;
		OrmConverter newConverter = buildConverter(converterType);
		this.converter = this.nullConverter;
		if (oldConverter != null) {
			oldConverter.removeFromResourceModel();
		}
		this.converter = newConverter;
		if (newConverter != null) {
			newConverter.addToResourceModel();
		}
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void setConverter(OrmConverter newConverter) {
		OrmConverter oldConverter = this.converter;
		this.converter = newConverter;
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}	

	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getVersions().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getVersions().remove(this.resourceAttributeMapping);
	}

	//************** NamedColumn.Owner implementation ***************

	public String getDefaultColumnName() {		
		return getName();
	}

	public Table getDbTable(String tableName) {
		return getTypeMapping().getDbTable(tableName);
	}

	//************** BaseColumn.Owner implementation ***************

	public String getDefaultTableName() {
		return getTypeMapping().getPrimaryTableName();
	}
	
	public boolean tableNameIsInvalid(String tableName) {
		return getTypeMapping().tableNameIsInvalid(tableName);
	}

	public Iterator<String> candidateTableNames() {
		return getTypeMapping().associatedTableNamesIncludingInherited();
	}

	@Override
	public void update() {
		super.update();
		this.column.update(this.getResourceColumn());
		if (this.valuesAreEqual(getResourceConverterType(), getConverterType())) {
			getConverter().update();
		}
		else {
			setConverter(buildConverter(getResourceConverterType()));
		}
	}
	
	protected OrmConverter buildConverter(String converterType) {
		if (this.valuesAreEqual(converterType, Converter.NO_CONVERTER)) {
			return this.nullConverter;
		}
		if (this.valuesAreEqual(converterType, Converter.TEMPORAL_CONVERTER)) {
			return getXmlContextNodeFactory().buildOrmTemporalConverter(this, this.resourceAttributeMapping);
		}
		return null;
	}
	
	protected String getResourceConverterType() {
		if (this.resourceAttributeMapping.getTemporal() != null) {
			return Converter.TEMPORAL_CONVERTER;
		}
		return Converter.NO_CONVERTER;
	}

	//***************** XmlColumn.Owner implementation ****************
	
	public XmlColumn getResourceColumn() {
		return this.resourceAttributeMapping.getColumn();
	}
	
	public void addResourceColumn() {
		this.resourceAttributeMapping.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
	}
	
	public void removeResourceColumn() {
		this.resourceAttributeMapping.setColumn(null);
	}


	//************ refactoring ************

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createRenameTypeEdits(originalType, newName),
			this.createConverterRenameTypeEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createConverterRenameTypeEdits(IType originalType, String newName) {
		if (getConverter() != null) {
			return getConverter().createRenameTypeEdits(originalType, newName);
		}
		return EmptyIterable.instance();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			super.createMoveTypeEdits(originalType, newPackage),
			this.createConverterMoveTypeEdits(originalType, newPackage));
	}
	
	protected Iterable<ReplaceEdit> createConverterMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (getConverter() != null) {
			return getConverter().createMoveTypeEdits(originalType, newPackage);
		}
		return EmptyIterable.instance();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createRenamePackageEdits(originalPackage, newName),
			this.createConverterRenamePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createConverterRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		if (getConverter() != null) {
			return getConverter().createRenamePackageEdits(originalPackage, newName);
		}
		return EmptyIterable.instance();
	}


	// ****************** validation ****************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		this.getColumn().validate(messages, reporter);
	}
	
	public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
		return new NamedColumnValidator(getPersistentAttribute(), (BaseColumn) column, (BaseColumnTextRangeResolver) textRangeResolver, new EntityTableDescriptionProvider());
	}
}
