/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BaseEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmLobConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AssociationOverrideJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AssociationOverrideValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AttributeOverrideColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.AttributeOverrideValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.CollectionTableTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EmbeddableOverrideDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.MapKeyAttributeOverrideColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.MapKeyAttributeOverrideValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.MapKeyColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.NullOrmConverter;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.CollectionTableValidator;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.MapKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmOrderable2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.MapKey;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.jpa.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * JPA 2.0 Frankenstein mapping
 */
public abstract class AbstractOrmElementCollectionMapping2_0<X extends XmlElementCollection>
	extends AbstractOrmAttributeMapping<X>
	implements OrmElementCollectionMapping2_0
{
	protected String specifiedTargetClass;
	protected String defaultTargetClass;
	protected String fullyQualifiedTargetClass;

	protected FetchType specifiedFetch;
	protected FetchType defaultFetch = DEFAULT_FETCH_TYPE;

	protected final OrmOrderable2_0 orderable;

	protected final OrmCollectionTable2_0 collectionTable;

	protected Type valueType;
	protected final OrmColumn valueColumn;
	protected OrmConverter converter;  // value converter - never null
	protected final OrmAttributeOverrideContainer valueAttributeOverrideContainer;
	protected final OrmAssociationOverrideContainer valueAssociationOverrideContainer;

	protected Type keyType;

	protected String specifiedMapKey;
	protected boolean noMapKey = false;
	protected boolean pkMapKey = false;
	protected boolean customMapKey = false;

	protected String specifiedMapKeyClass;
	protected String defaultMapKeyClass;
	protected String fullyQualifiedMapKeyClass;

	protected final OrmColumn mapKeyColumn;
	protected OrmConverter mapKeyConverter;  // map key converter - never null

	protected final OrmAttributeOverrideContainer mapKeyAttributeOverrideContainer;

	protected final ContextListContainer<OrmJoinColumn, XmlJoinColumn> specifiedMapKeyJoinColumnContainer;
	protected final ReadOnlyJoinColumn.Owner mapKeyJoinColumnOwner;

	protected OrmJoinColumn defaultMapKeyJoinColumn;

	protected final OrmConverter nullConverter = new NullOrmConverter(this);

	protected static final OrmConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new OrmConverter.Adapter[] {
		OrmBaseEnumeratedConverter.BasicAdapter.instance(),
		OrmBaseTemporalConverter.ElementCollectionAdapter.instance(),
		OrmLobConverter.Adapter.instance()
	};
	protected static final Iterable<OrmConverter.Adapter> CONVERTER_ADAPTERS = IterableTools.iterable(CONVERTER_ADAPTER_ARRAY);

	protected static final OrmConverter.Adapter[] MAP_KEY_CONVERTER_ADAPTER_ARRAY = new OrmConverter.Adapter[] {
		OrmBaseEnumeratedConverter.MapKeyAdapter.instance(),
		OrmBaseTemporalConverter.MapKeyAdapter.instance()
	};
	protected static final Iterable<OrmConverter.Adapter> MAP_KEY_CONVERTER_ADAPTERS = IterableTools.iterable(MAP_KEY_CONVERTER_ADAPTER_ARRAY);


	protected AbstractOrmElementCollectionMapping2_0(OrmPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
		this.specifiedTargetClass = xmlMapping.getTargetClass();
		this.specifiedFetch = this.buildSpecifiedFetch();
		this.orderable = this.buildOrderable();
		this.collectionTable = this.buildCollectionTable();

		this.valueColumn = this.buildValueColumn();
		this.converter = this.buildConverter();
		this.valueAttributeOverrideContainer = this.buildValueAttributeOverrideContainer();
		this.valueAssociationOverrideContainer = this.buildValueAssociationOverrideContainer();

		this.specifiedMapKey = this.buildSpecifiedMapKey();
		this.noMapKey = this.buildNoMapKey();
		this.pkMapKey = this.buildPkMapKey();
		this.customMapKey = this.buildCustomMapKey();
		this.specifiedMapKeyClass = this.buildSpecifiedMapKeyClass();

		this.mapKeyColumn = this.buildMapKeyColumn();
		this.mapKeyConverter = this.buildMapKeyConverter();
		this.mapKeyAttributeOverrideContainer = this.buildMapKeyAttributeOverrideContainer();
		this.mapKeyJoinColumnOwner = this.buildMapKeyJoinColumnOwner();
		this.specifiedMapKeyJoinColumnContainer = this.buildSpecifiedMapKeyJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedTargetClass_(this.xmlAttributeMapping.getTargetClass());
		this.setSpecifiedFetch_(this.buildSpecifiedFetch());
		this.orderable.synchronizeWithResourceModel();
		this.collectionTable.synchronizeWithResourceModel();

		this.valueColumn.synchronizeWithResourceModel();
		this.syncConverter();
		this.valueAttributeOverrideContainer.synchronizeWithResourceModel();
		this.valueAssociationOverrideContainer.synchronizeWithResourceModel();

		this.setSpecifiedMapKey_(this.buildSpecifiedMapKey());
		this.setNoMapKey_(this.buildNoMapKey());
		this.setPkMapKey_(this.buildPkMapKey());
		this.setCustomMapKey_(this.buildCustomMapKey());
		this.setSpecifiedMapKeyClass_(this.buildSpecifiedMapKeyClass());

		this.mapKeyColumn.synchronizeWithResourceModel();
		this.syncMapKeyConverter();
		this.mapKeyAttributeOverrideContainer.synchronizeWithResourceModel();
		this.syncSpecifiedMapKeyJoinColumns();
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultTargetClass(this.buildDefaultTargetClass());
		this.setFullyQualifiedTargetClass(this.buildFullyQualifiedTargetClass());
		this.setDefaultFetch(this.buildDefaultFetch());
		this.orderable.update();
		this.collectionTable.update();

		this.setValueType(this.buildValueType());
		this.valueColumn.update();
		this.converter.update();
		this.valueAttributeOverrideContainer.update();
		this.valueAssociationOverrideContainer.update();

		this.setKeyType(this.buildKeyType());
		this.setDefaultMapKeyClass(this.buildDefaultMapKeyClass());
		this.setFullyQualifiedMapKeyClass(this.buildFullyQualifiedMapKeyClass());

		this.mapKeyColumn.update();
		this.mapKeyConverter.update();
		this.mapKeyAttributeOverrideContainer.update();
		this.updateNodes(this.getSpecifiedMapKeyJoinColumns());
		this.updateDefaultMapKeyJoinColumn();
	}


	// ********** fully-qualified target class **********

	public String getFullyQualifiedTargetClass() {
		return this.fullyQualifiedTargetClass;
	}

	protected void setFullyQualifiedTargetClass(String targetClass) {
		String old = this.fullyQualifiedTargetClass;
		this.fullyQualifiedTargetClass = targetClass;
		this.firePropertyChanged(FULLY_QUALIFIED_TARGET_CLASS_PROPERTY, old, targetClass);
	}

	protected String buildFullyQualifiedTargetClass() {
		return (this.specifiedTargetClass == null) ?
				this.defaultTargetClass :
				this.getEntityMappings().qualify(this.specifiedTargetClass);
	}

	// ********** target class **********

	public String getTargetClass() {
		return (this.specifiedTargetClass != null) ? this.specifiedTargetClass : this.defaultTargetClass;
	}

	public String getSpecifiedTargetClass() {
		return this.specifiedTargetClass;
	}

	public void setSpecifiedTargetClass(String targetClass) {
		this.setSpecifiedTargetClass_(targetClass);
		this.xmlAttributeMapping.setTargetClass(targetClass);
	}

	protected void setSpecifiedTargetClass_(String targetClass) {
		String old = this.specifiedTargetClass;
		this.specifiedTargetClass = targetClass;
		this.firePropertyChanged(SPECIFIED_TARGET_CLASS_PROPERTY, old, targetClass);
	}

	public String getDefaultTargetClass() {
		return this.defaultTargetClass;
	}

	protected void setDefaultTargetClass(String targetClass) {
		String old = this.defaultTargetClass;
		this.defaultTargetClass = targetClass;
		this.firePropertyChanged(DEFAULT_TARGET_CLASS_PROPERTY, old, targetClass);
	}

	protected String buildDefaultTargetClass() {
		JavaPersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		return (javaAttribute == null) ? null : javaAttribute.getMultiReferenceTargetTypeName();
	}

	public char getTargetClassEnclosingTypeSeparator() {
		return '$';
	}


	// ********** resolved target type/embeddable/entity **********

	public PersistentType getResolvedTargetType() {
		if (this.fullyQualifiedTargetClass == null) {
			return null;
		}
		return this.getPersistenceUnit().getPersistentType(this.fullyQualifiedTargetClass);
	}

	protected Embeddable getResolvedTargetEmbeddable() {
		TypeMapping typeMapping = this.getResolvedTargetTypeMapping();
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
	}

	protected Entity getResolvedTargetEntity() {
		TypeMapping typeMapping = this.getResolvedTargetTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}

	protected TypeMapping getResolvedTargetTypeMapping() {
		PersistentType resolvedTargetType = this.getResolvedTargetType();
		return (resolvedTargetType == null) ? null : resolvedTargetType.getMapping();
	}


	// ********** fetch **********

	public FetchType getFetch() {
		return (this.specifiedFetch != null) ? this.specifiedFetch : this.defaultFetch;
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}

	public void setSpecifiedFetch(FetchType fetch) {
		this.setSpecifiedFetch_(fetch);
		this.xmlAttributeMapping.setFetch(FetchType.toOrmResourceModel(fetch));
	}

	protected void setSpecifiedFetch_(FetchType fetch) {
		FetchType old = this.specifiedFetch;
		this.specifiedFetch = fetch;
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
	}

	protected FetchType buildSpecifiedFetch() {
		return FetchType.fromOrmResourceModel(this.xmlAttributeMapping.getFetch());
	}

	public FetchType getDefaultFetch() {
		return this.defaultFetch;
	}

	protected void setDefaultFetch(FetchType fetch) {
		FetchType old = this.defaultFetch;
		this.defaultFetch = fetch;
		this.firePropertyChanged(DEFAULT_FETCH_PROPERTY, old, fetch);
	}

	protected FetchType buildDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}


	// ********** orderable **********

	public Orderable getOrderable() {
		return this.orderable;
	}

	protected OrmOrderable2_0 buildOrderable() {
		// we wouldn't be here if we weren't orm.xml 2.0 compatible
		return this.getContextNodeFactory2_0().buildOrmOrderable(this, this.buildOrderableOwner());
	}

	protected Orderable2_0.Owner buildOrderableOwner() {
		return new OrderableOwner();
	}

	protected class OrderableOwner
		implements Orderable2_0.Owner
	{
		public String getTableName() {
			return this.getCollectionTable().getName();
		}
		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return this.getCollectionTable().getDbTable();
		}
		protected OrmCollectionTable2_0 getCollectionTable() {
			return AbstractOrmElementCollectionMapping2_0.this.getCollectionTable();
		}
	}


	// ********** collection table **********

	public OrmCollectionTable2_0 getCollectionTable() {
		return this.collectionTable;
	}

	protected OrmCollectionTable2_0 buildCollectionTable() {
		// we wouldn't be here if we weren't orm.xml 2.0 compatible
		return this.getContextNodeFactory2_0().buildOrmCollectionTable(this, this.buildCollectionTableOwner());
	}

	protected Table.Owner buildCollectionTableOwner() {
		return new CollectionTableOwner();
	}

	public XmlCollectionTable getResourceCollectionTable() {
		return this.xmlAttributeMapping.getCollectionTable();
	}

	protected class CollectionTableOwner
		implements ReadOnlyTable.Owner
	{
		public JptValidator buildTableValidator(ReadOnlyTable table) {
			return new CollectionTableValidator(AbstractOrmElementCollectionMapping2_0.this.getPersistentAttribute(), (CollectionTable2_0) table);
		}
	}


	// ********** value type **********

	public Type getValueType() {
		return this.valueType;
	}

	protected void setValueType(Type valueType) {
		Type old = this.valueType;
		this.valueType = valueType;
		this.firePropertyChanged(VALUE_TYPE_PROPERTY, old, valueType);
	}

	protected Type buildValueType() {
		if (this.getResolvedTargetEmbeddable() != null) {
			return Type.EMBEDDABLE_TYPE;
		}
		if (this.getTargetClass() == null) {
			return Type.NO_TYPE;
		}
		return Type.BASIC_TYPE;
	}


	// ********** value column **********

	public OrmColumn getValueColumn() {
		return this.valueColumn;
	}

	protected OrmColumn buildValueColumn() {
		return this.getContextNodeFactory().buildOrmColumn(this, this.buildValueColumnOwner());
	}

	protected OrmColumn.Owner buildValueColumnOwner() {
		return new ValueColumnOwner();
	}


	// ********** converter **********

	public OrmConverter getConverter() {
		return this.converter;
	}

	public void setConverter(Class<? extends Converter> converterType) {
		if (this.converter.getType() != converterType) {
			// Make the old value is the real old one when firing property changed event below
			OrmConverter old = this.converter;
			// Set the new value of the converter to a NullOrmConverter to prevent the following
			// step to synchronize through a separate thread when setting converters to null
			// Through this way timing issue between different thread may be eliminated.
			this.converter = this.nullConverter;
			// note: we may also clear the XML value we want;
			// but if we leave it, the resulting sync will screw things up...
			this.clearXmlConverterValues();
			OrmConverter.Adapter converterAdapter = this.getConverterAdapter(converterType);
			this.converter = this.buildConverter(converterAdapter);
			this.converter.initialize();
			this.firePropertyChanged(CONVERTER_PROPERTY, old, this.converter);
		}
	}

	protected OrmConverter buildConverter(OrmConverter.Adapter converterAdapter) {
		 return (converterAdapter != null) ?
				converterAdapter.buildNewConverter(this, this.getContextNodeFactory()) :
				this.nullConverter;
	}

	protected void setConverter_(OrmConverter converter) {
		Converter old = this.converter;
		this.converter = converter;
		this.firePropertyChanged(CONVERTER_PROPERTY, old, converter);
	}

	protected void clearXmlConverterValues() {
		for (OrmConverter.Adapter adapter : this.getConverterAdapters()) {
			adapter.clearXmlValue(this.xmlAttributeMapping);
		}
	}

	protected OrmConverter buildConverter() {
		OrmXmlContextNodeFactory factory = this.getContextNodeFactory();
		for (OrmConverter.Adapter adapter : this.getConverterAdapters()) {
			OrmConverter ormConverter = adapter.buildConverter(this, factory);
			if (ormConverter != null) {
				return ormConverter;
			}
		}
		return this.nullConverter;
	}

	protected void syncConverter() {
		OrmConverter.Adapter adapter = this.getXmlConverterAdapter();
		if (adapter == null) {
			if (this.converter.getType() != null) {
				this.setConverter_(this.nullConverter);
			}
		} else {
			if (this.converter.getType() == adapter.getConverterType()) {
				this.converter.synchronizeWithResourceModel();
			} else {
				this.setConverter_(adapter.buildNewConverter(this, this.getContextNodeFactory()));
			}
		}
	}

	/**
	 * Return the first adapter whose converter value is set in the XML mapping.
	 * Return <code>null</code> if there are no converter values in the XML.
	 */
	protected OrmConverter.Adapter getXmlConverterAdapter() {
		for (OrmConverter.Adapter adapter : this.getConverterAdapters()) {
			if (adapter.isActive(this.xmlAttributeMapping)) {
				return adapter;
			}
		}
		return null;
	}


	// ********** converter adapters **********

	/**
	 * Return the converter adapter for the specified converter type.
	 */
	protected OrmConverter.Adapter getConverterAdapter(Class<? extends Converter> converterType) {
		for (OrmConverter.Adapter adapter : this.getConverterAdapters()) {
			if (adapter.getConverterType() == converterType) {
				return adapter;
			}
		}
		return null;
	}

	protected Iterable<OrmConverter.Adapter> getConverterAdapters() {
		return CONVERTER_ADAPTERS;
	}


	// ********** value attribute override container **********

	public OrmAttributeOverrideContainer getValueAttributeOverrideContainer() {
		return this.valueAttributeOverrideContainer;
	}

	protected OrmAttributeOverrideContainer buildValueAttributeOverrideContainer() {
		return this.getContextNodeFactory().buildOrmAttributeOverrideContainer(this, this.buildValueAttributeOverrideContainerOwner());
	}

	protected OrmAttributeOverrideContainer.Owner buildValueAttributeOverrideContainerOwner() {
		return new ValueAttributeOverrideContainerOwner();
	}


	// ********** value association override container **********

	public OrmAssociationOverrideContainer getValueAssociationOverrideContainer() {
		return this.valueAssociationOverrideContainer;
	}

	protected OrmAssociationOverrideContainer buildValueAssociationOverrideContainer() {
		return this.getContextNodeFactory().buildOrmAssociationOverrideContainer(this, this.buildValueAssociationOverrideContainerOwner());
	}

	protected OrmAssociationOverrideContainer.Owner buildValueAssociationOverrideContainerOwner() {
		return new ValueAssociationOverrideContainerOwner();
	}


	// ********** key type **********

	public Type getKeyType() {
		return this.keyType;
	}

	protected void setKeyType(Type keyType) {
		Type old = this.keyType;
		this.keyType = keyType;
		this.firePropertyChanged(KEY_TYPE_PROPERTY, old, keyType);
	}

	protected Type buildKeyType() {
		if (this.getResolvedMapKeyEmbeddable() != null) {
			return Type.EMBEDDABLE_TYPE;
		}
		if (this.getResolvedMapKeyEntity() != null) {
			return Type.ENTITY_TYPE;
		}
		if (this.getMapKeyClass() == null) {
			return Type.NO_TYPE;
		}
		return Type.BASIC_TYPE;
	}


	// ********** map key **********

	public String getMapKey() {
		if (this.noMapKey) {
			return null;
		}
		if (this.pkMapKey) {
			// the target is either embeddable or basic, so a key will have to be specified
			return null;
		}
		if (this.customMapKey) {
			return this.specifiedMapKey;
		}
		throw new IllegalStateException("unknown map key"); //$NON-NLS-1$
	}


	// ********** specified map key **********

	public String getSpecifiedMapKey() {
		return this.specifiedMapKey;
	}

	public void setSpecifiedMapKey(String mapKey) {
		if (mapKey != null) {
			this.setSpecifiedMapKey_(mapKey);
			this.setNoMapKey_(false);
			this.setPkMapKey_(false);
			this.setCustomMapKey_(true);

			MapKey xmlMapKey = this.getXmlMapKey();
			if (xmlMapKey == null) {
				xmlMapKey = this.buildXmlMapKey();
			}
			xmlMapKey.setName(mapKey);
		} else {
			this.setPkMapKey(true);  // hmmm...
		}
	}

	protected void setSpecifiedMapKey_(String mapKey) {
		String old = this.specifiedMapKey;
		this.specifiedMapKey = mapKey;
		this.firePropertyChanged(SPECIFIED_MAP_KEY_PROPERTY, old, mapKey);
	}

	protected String buildSpecifiedMapKey() {
		MapKey xmlMapKey = this.getXmlMapKey();
		return (xmlMapKey == null) ? null : xmlMapKey.getName();
	}


	// ********** no map key **********

	public boolean isNoMapKey() {
		return this.noMapKey;
	}

	public void setNoMapKey(boolean noMapKey) {
		if (noMapKey) {
			this.setSpecifiedMapKey_(null);
			this.setNoMapKey_(true);
			this.setPkMapKey_(false);
			this.setCustomMapKey_(false);

			if (this.getXmlMapKey() != null) {
				this.removeXmlMapKey();
			}
		} else {
			this.setPkMapKey(true);  // hmmm...
		}
	}

	protected void setNoMapKey_(boolean noMapKey) {
		boolean old = this.noMapKey;
		this.noMapKey = noMapKey;
		this.firePropertyChanged(NO_MAP_KEY_PROPERTY, old, noMapKey);
	}

	protected boolean buildNoMapKey() {
		return this.getXmlMapKey() == null;
	}


	// ********** pk map key **********

	public boolean isPkMapKey() {
		return this.pkMapKey;
	}

	public void setPkMapKey(boolean pkMapKey) {
		if (pkMapKey) {
			this.setSpecifiedMapKey_(null);
			this.setNoMapKey_(false);
			this.setPkMapKey_(true);
			this.setCustomMapKey_(false);

			MapKey xmlMapKey = this.getXmlMapKey();
			if (xmlMapKey == null) {
				this.buildXmlMapKey();
			} else {
				xmlMapKey.setName(null);
			}
		} else {
			this.setNoMapKey(true);  // hmmm...
		}
	}

	protected void setPkMapKey_(boolean pkMapKey) {
		boolean old = this.pkMapKey;
		this.pkMapKey = pkMapKey;
		this.firePropertyChanged(PK_MAP_KEY_PROPERTY, old, pkMapKey);
	}

	protected boolean buildPkMapKey() {
		MapKey xmlMapKey = this.getXmlMapKey();
		return (xmlMapKey != null) && (xmlMapKey.getName() == null);
	}


	// ********** custom map key **********

	public boolean isCustomMapKey() {
		return this.customMapKey;
	}

	public void setCustomMapKey(boolean customMapKey) {
		if (customMapKey) {
			this.setSpecifiedMapKey(""); //$NON-NLS-1$
		} else {
			this.setNoMapKey(true);  // hmmm...
		}
	}

	protected void setCustomMapKey_(boolean customMapKey) {
		boolean old = this.customMapKey;
		this.customMapKey = customMapKey;
		this.firePropertyChanged(CUSTOM_MAP_KEY_PROPERTY, old, customMapKey);
	}

	protected boolean buildCustomMapKey() {
		MapKey xmlMapKey = this.getXmlMapKey();
		return (xmlMapKey != null) && (xmlMapKey.getName() != null);
	}


	// ********** xml map key **********

	protected MapKey getXmlMapKey() {
		return this.xmlAttributeMapping.getMapKey();
	}

	protected MapKey buildXmlMapKey() {
		MapKey mapKey = OrmFactory.eINSTANCE.createMapKey();
		this.xmlAttributeMapping.setMapKey(mapKey);
		return mapKey;
	}

	protected void removeXmlMapKey() {
		this.xmlAttributeMapping.setMapKey(null);
	}


	// ********** fully-qualified map key class **********

	public String getFullyQualifiedMapKeyClass() {
		return this.fullyQualifiedMapKeyClass;
	}

	protected void setFullyQualifiedMapKeyClass(String mapKeyClass) {
		String old = this.fullyQualifiedMapKeyClass;
		this.fullyQualifiedMapKeyClass = mapKeyClass;
		this.firePropertyChanged(FULLY_QUALIFIED_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected String buildFullyQualifiedMapKeyClass() {
		return (this.specifiedMapKeyClass == null) ?
				this.defaultMapKeyClass :
				this.getEntityMappings().qualify(this.specifiedMapKeyClass);
	}

	// ********** map key class **********

	public String getMapKeyClass() {
		return (this.specifiedMapKeyClass != null) ? this.specifiedMapKeyClass : this.defaultMapKeyClass;
	}

	public String getSpecifiedMapKeyClass() {
		return this.specifiedMapKeyClass;
	}

	public void setSpecifiedMapKeyClass(String mapKeyClass) {
		if (this.setSpecifiedMapKeyClass_(mapKeyClass)) {
			XmlClassReference xmlMapKeyClassRef = this.xmlAttributeMapping.getMapKeyClass();
			if (mapKeyClass == null) {
				if (xmlMapKeyClassRef != null) {
					this.xmlAttributeMapping.setMapKeyClass(null);
				}
			} else {
				if (xmlMapKeyClassRef == null) {
					xmlMapKeyClassRef = this.buildXmlMapKeyClassReference();
				}
				xmlMapKeyClassRef.setClassName(mapKeyClass);
			}
		}
	}

	protected boolean setSpecifiedMapKeyClass_(String mapKeyClass) {
		String old = this.specifiedMapKeyClass;
		this.specifiedMapKeyClass = mapKeyClass;
		return this.firePropertyChanged(SPECIFIED_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected XmlClassReference buildXmlMapKeyClassReference() {
		XmlClassReference mapKeyClass = OrmFactory.eINSTANCE.createXmlClassReference();
		this.xmlAttributeMapping.setMapKeyClass(mapKeyClass);
		return mapKeyClass;
	}

	protected String buildSpecifiedMapKeyClass() {
		XmlClassReference xmlClassRef = this.xmlAttributeMapping.getMapKeyClass();
		return (xmlClassRef == null) ? null : xmlClassRef.getClassName();
	}

	public String getDefaultMapKeyClass() {
		return this.defaultMapKeyClass;
	}

	protected void setDefaultMapKeyClass(String mapKeyClass) {
		String old = this.defaultMapKeyClass;
		this.defaultMapKeyClass = mapKeyClass;
		this.firePropertyChanged(DEFAULT_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected String buildDefaultMapKeyClass() {
		JavaPersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		return (javaAttribute == null) ? null : javaAttribute.getMultiReferenceMapKeyTypeName();
	}

	public char getMapKeyClassEnclosingTypeSeparator() {
		return '$';
	}


	// ********** resolved map key embeddable/entity **********

	protected Embeddable getResolvedMapKeyEmbeddable() {
		TypeMapping typeMapping = this.getResolvedMapKeyTypeMapping();
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
	}

	protected Entity getResolvedMapKeyEntity() {
		TypeMapping typeMapping = this.getResolvedMapKeyTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}

	protected TypeMapping getResolvedMapKeyTypeMapping() {
		PersistentType resolvedMapKeyType = this.getResolvedMapKeyType();
		return (resolvedMapKeyType == null) ? null : resolvedMapKeyType.getMapping();
	}

	protected PersistentType getResolvedMapKeyType() {
		if (this.fullyQualifiedMapKeyClass == null) {
			return null;
		}
		return this.getPersistenceUnit().getPersistentType(this.fullyQualifiedMapKeyClass);
	}


	// ********** map key column **********

	public OrmColumn getMapKeyColumn() {
		return this.mapKeyColumn;
	}

	protected OrmColumn buildMapKeyColumn() {
		return this.getContextNodeFactory().buildOrmColumn(this, this.buildMapKeyColumnOwner());
	}

	protected OrmColumn.Owner buildMapKeyColumnOwner() {
		return new MapKeyColumnOwner();
	}


	// ********** map key converter **********

	public OrmConverter getMapKeyConverter() {
		return this.mapKeyConverter;
	}

	public void setMapKeyConverter(Class<? extends Converter> converterType) {
		if (this.mapKeyConverter.getType() != converterType) {
			// Make the old value is the real old one when firing property changed event below
			OrmConverter old = this.mapKeyConverter;
			// Set the new value of the map key converter to a NullOrmConverter to prevent the following 
			// step from synchronizing through a separate thread when setting converters to null
			// Through this way timing issue between different thread may be eliminated.
			this.mapKeyConverter = this.nullConverter;
			// note: we may also clear the XML value we want;
			// but if we leave it, the resulting sync will screw things up...
			this.clearXmlMapKeyConverterValues();
			OrmConverter.Adapter converterAdapter = this.getMapKeyConverterAdapter(converterType);
			this.mapKeyConverter = this.buildMapKeyConverter(converterAdapter);
			this.mapKeyConverter.initialize();
			this.firePropertyChanged(MAP_KEY_CONVERTER_PROPERTY, old, this.mapKeyConverter);
		}
	}

	protected OrmConverter buildMapKeyConverter(OrmConverter.Adapter converterAdapter) {
		 return (converterAdapter != null) ?
				converterAdapter.buildNewConverter(this, this.getContextNodeFactory()) :
				this.nullConverter;
	}

	protected void setMapKeyConverter_(OrmConverter converter) {
		Converter old = this.mapKeyConverter;
		this.mapKeyConverter = converter;
		this.firePropertyChanged(MAP_KEY_CONVERTER_PROPERTY, old, converter);
	}

	protected void clearXmlMapKeyConverterValues() {
		for (OrmConverter.Adapter adapter : this.getMapKeyConverterAdapters()) {
			adapter.clearXmlValue(this.xmlAttributeMapping);
		}
	}

	protected OrmConverter buildMapKeyConverter() {
		OrmXmlContextNodeFactory factory = this.getContextNodeFactory();
		for (OrmConverter.Adapter adapter : this.getMapKeyConverterAdapters()) {
			OrmConverter ormConverter = adapter.buildConverter(this, factory);
			if (ormConverter != null) {
				return ormConverter;
			}
		}
		return this.nullConverter;
	}

	protected void syncMapKeyConverter() {
		OrmConverter.Adapter adapter = this.getXmlMapKeyConverterAdapter();
		if (adapter == null) {
			if (this.mapKeyConverter.getType() != null) {
				this.setMapKeyConverter_(this.nullConverter);
			}
		} else {
			if (this.mapKeyConverter.getType() == adapter.getConverterType()) {
				this.mapKeyConverter.synchronizeWithResourceModel();
			} else {
				this.setMapKeyConverter_(adapter.buildNewConverter(this, this.getContextNodeFactory()));
			}
		}
	}

	/**
	 * Return the first adapter whose converter value is set in the XML mapping.
	 * Return <code>null</code> if there are no converter values in the XML.
	 */
	protected OrmConverter.Adapter getXmlMapKeyConverterAdapter() {
		for (OrmConverter.Adapter adapter : this.getMapKeyConverterAdapters()) {
			if (adapter.isActive(this.xmlAttributeMapping)) {
				return adapter;
			}
		}
		return null;
	}

	// ********** map key converter adapters **********

	/**
	 * Return the converter adapter for the specified converter type.
	 */
	protected OrmConverter.Adapter getMapKeyConverterAdapter(Class<? extends Converter> converterType) {
		for (OrmConverter.Adapter adapter : this.getMapKeyConverterAdapters()) {
			if (adapter.getConverterType() == converterType) {
				return adapter;
			}
		}
		return null;
	}

	protected Iterable<OrmConverter.Adapter> getMapKeyConverterAdapters() {
		return MAP_KEY_CONVERTER_ADAPTERS;
	}

	// ********** map key attribute override container **********

	public OrmAttributeOverrideContainer getMapKeyAttributeOverrideContainer() {
		return this.mapKeyAttributeOverrideContainer;
	}

	protected OrmAttributeOverrideContainer buildMapKeyAttributeOverrideContainer() {
		return this.getContextNodeFactory().buildOrmAttributeOverrideContainer(this, this.buildMapKeyAttributeOverrideContainerOwner());
	}

	protected OrmAttributeOverrideContainer.Owner buildMapKeyAttributeOverrideContainerOwner() {
		return new MapKeyAttributeOverrideContainerOwner();
	}


	// ********** map key join columns **********

	public ListIterable<OrmJoinColumn> getMapKeyJoinColumns() {
		return this.hasSpecifiedMapKeyJoinColumns() ? this.getSpecifiedMapKeyJoinColumns() : this.getDefaultMapKeyJoinColumns();
	}

	public int getMapKeyJoinColumnsSize() {
		return this.hasSpecifiedMapKeyJoinColumns() ? this.getSpecifiedMapKeyJoinColumnsSize() : this.getDefaultMapKeyJoinColumnsSize();
	}


	// ********** specified map key join columns **********

	public ListIterable<OrmJoinColumn> getSpecifiedMapKeyJoinColumns() {
		return this.specifiedMapKeyJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedMapKeyJoinColumnsSize() {
		return this.specifiedMapKeyJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedMapKeyJoinColumns() {
		return this.getSpecifiedMapKeyJoinColumnsSize() != 0;
	}

	public OrmJoinColumn getSpecifiedMapKeyJoinColumn(int index) {
		return this.specifiedMapKeyJoinColumnContainer.getContextElement(index);
	}

	public OrmJoinColumn addSpecifiedMapKeyJoinColumn() {
		return this.addSpecifiedMapKeyJoinColumn(this.getSpecifiedMapKeyJoinColumnsSize());
	}

	public OrmJoinColumn addSpecifiedMapKeyJoinColumn(int index) {
		XmlJoinColumn xmlJoinColumn = this.buildXmlJoinColumn();
		OrmJoinColumn joinColumn = this.specifiedMapKeyJoinColumnContainer.addContextElement(index, xmlJoinColumn);
		this.getXmlAttributeMapping().getMapKeyJoinColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlJoinColumn buildXmlJoinColumn() {
		return OrmFactory.eINSTANCE.createXmlJoinColumn();
	}

	public void removeSpecifiedMapKeyJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedMapKeyJoinColumn(this.specifiedMapKeyJoinColumnContainer.indexOfContextElement((OrmJoinColumn) joinColumn));
	}

	public void removeSpecifiedMapKeyJoinColumn(int index) {
		this.specifiedMapKeyJoinColumnContainer.removeContextElement(index);
		this.getXmlAttributeMapping().getMapKeyJoinColumns().remove(index);
	}

	public void moveSpecifiedMapKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.specifiedMapKeyJoinColumnContainer.moveContextElement(targetIndex, sourceIndex);
		this.getXmlAttributeMapping().getMapKeyJoinColumns().move(targetIndex, sourceIndex);
	}

	protected void syncSpecifiedMapKeyJoinColumns() {
		this.specifiedMapKeyJoinColumnContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlJoinColumn> getXmlMapKeyJoinColumns() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.getXmlAttributeMapping().getMapKeyJoinColumns());
	}

	protected ContextListContainer<OrmJoinColumn, XmlJoinColumn> buildSpecifiedMapKeyJoinColumnContainer() {
		SpecifiedMapKeyJoinColumnContainer container = new SpecifiedMapKeyJoinColumnContainer();
		container.initialize();
		return container;
	}

	/**
	 * specified join column container
	 */
	protected class SpecifiedMapKeyJoinColumnContainer
		extends ContextListContainer<OrmJoinColumn, XmlJoinColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_MAP_KEY_JOIN_COLUMNS_LIST;
		}
		@Override
		protected OrmJoinColumn buildContextElement(XmlJoinColumn resourceElement) {
			return AbstractOrmElementCollectionMapping2_0.this.buildMapKeyJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<XmlJoinColumn> getResourceElements() {
			return AbstractOrmElementCollectionMapping2_0.this.getXmlMapKeyJoinColumns();
		}
		@Override
		protected XmlJoinColumn getResourceElement(OrmJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected OrmJoinColumn buildMapKeyJoinColumn(XmlJoinColumn xmlJoinColumn) {
		return this.getContextNodeFactory().buildOrmJoinColumn(this, this.mapKeyJoinColumnOwner, xmlJoinColumn);
	}

	protected ReadOnlyJoinColumn.Owner buildMapKeyJoinColumnOwner() {
		return new MapKeyJoinColumnOwner();
	}


	// ********** default map key join column **********

	public OrmJoinColumn getDefaultMapKeyJoinColumn() {
		return this.defaultMapKeyJoinColumn;
	}

	protected void setDefaultMapKeyJoinColumn(OrmJoinColumn mapKeyJoinColumn) {
		OrmJoinColumn old = this.defaultMapKeyJoinColumn;
		this.defaultMapKeyJoinColumn = mapKeyJoinColumn;
		this.firePropertyChanged(DEFAULT_MAP_KEY_JOIN_COLUMN_PROPERTY, old, mapKeyJoinColumn);
	}

	protected ListIterable<OrmJoinColumn> getDefaultMapKeyJoinColumns() {
		return (this.defaultMapKeyJoinColumn != null) ?
				new SingleElementListIterable<OrmJoinColumn>(this.defaultMapKeyJoinColumn) :
				EmptyListIterable.<OrmJoinColumn>instance();
	}

	protected int getDefaultMapKeyJoinColumnsSize() {
		return (this.defaultMapKeyJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultMapKeyJoinColumn() {
		if (this.buildsDefaultMapKeyJoinColumn()) {
			if (this.defaultMapKeyJoinColumn == null) {
				this.setDefaultMapKeyJoinColumn(this.buildMapKeyJoinColumn(null));
			} else {
				this.defaultMapKeyJoinColumn.update();
			}
		} else {
			this.setDefaultMapKeyJoinColumn(null);
		}
	}

	protected boolean buildsDefaultMapKeyJoinColumn() {
		return ! this.hasSpecifiedMapKeyJoinColumns() &&
				getKeyType() == Type.ENTITY_TYPE;
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 75;
	}

	public Entity getEntity() {
		OrmTypeMapping typeMapping = this.getTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
 		newMapping.initializeFromOrmAttributeMapping(this);
	}

	public void addXmlAttributeMappingTo(Attributes resourceAttributes) {
		resourceAttributes.getElementCollections().add(this.xmlAttributeMapping);
	}

	public void removeXmlAttributeMappingFrom(Attributes resourceAttributes) {
		resourceAttributes.getElementCollections().remove(this.xmlAttributeMapping);
	}


	// ********** metamodel **********

	@Override
	protected String getMetamodelFieldTypeName() {
		return ((PersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	public String getMetamodelTypeName() {
		PersistentType resolvedTargetType = this.getResolvedTargetType();
		if(((PersistentType2_0)resolvedTargetType).getMetamodelType() == null) { // dynamic type
			return null;
		}
		return resolvedTargetType.getName();
	}

	@Override
	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		this.addMetamodelFieldMapKeyTypeArgumentNameTo(typeArgumentNames);
		super.addMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
	}

	protected void addMetamodelFieldMapKeyTypeArgumentNameTo(ArrayList<String> typeArgumentNames) {
		String keyTypeName = ((PersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldMapKeyTypeName();
		if (keyTypeName != null) {
			typeArgumentNames.add(keyTypeName);
		}
	}

	public String getMetamodelFieldMapKeyTypeName() {
		return MappingTools.getMetamodelFieldMapKeyTypeName(this);
	}


	// ********** embedded mappings **********

	/**
	 * Return a list of lists; each nested list holds the names for one of the
	 * embedded mapping's target embeddable type mapping's attribute mappings
	 * (non-transient attribute or association mappings, depending on the specified transformer).
	 */
	public Iterable<String> getCandidateMapKeyNames() {
		return IterableTools.children(this.getTargetEmbeddableNonTransientAttributeMappings(), AttributeMappingTools.ALL_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterable<AttributeMapping> getTargetEmbeddableNonTransientAttributeMappings() {
		Embeddable targetEmbeddable = this.getResolvedTargetEmbeddable();
		return (targetEmbeddable != null) ? targetEmbeddable.getNonTransientAttributeMappings() : EmptyIterable.<AttributeMapping> instance();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<String> getAllMappingNames() {
		return IterableTools.concatenate(super.getAllMappingNames(), this.getAllEmbeddableAttributeMappingNames());
	}

	protected Iterable<String> getAllEmbeddableAttributeMappingNames() {
		return this.getQualifiedEmbeddableOverridableMappingNames(AttributeMappingTools.ALL_MAPPING_NAMES_TRANSFORMER);
	}

	@Override
	public Iterable<String> getAllOverridableAttributeMappingNames() {
		return this.getQualifiedEmbeddableOverridableMappingNames(AttributeMappingTools.ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER);
	}

	@Override
	public Iterable<String> getAllOverridableAssociationMappingNames() {
		return this.getQualifiedEmbeddableOverridableMappingNames(AttributeMappingTools.ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterable<String> getQualifiedEmbeddableOverridableMappingNames(Transformer<AttributeMapping, Iterable<String>> transformer) {
		return new TransformationIterable<String, String>(this.getEmbeddableOverridableMappingNames(transformer), this.buildQualifierTransformer());
	}

	/**
	 * Return a list of lists; each nested list holds the names for one of the
	 * embedded mapping's target embeddable type mapping's attribute mappings
	 * (attribute or association mappings, depending on the specified transformer).
	 */
	protected Iterable<String> getEmbeddableOverridableMappingNames(Transformer<AttributeMapping, Iterable<String>> transformer) {
		return IterableTools.children(this.getEmbeddableAttributeMappings(), transformer);
	}

	@Override
	public AttributeMapping resolveAttributeMapping(String attributeName) {
		AttributeMapping resolvedMapping = super.resolveAttributeMapping(attributeName);
		if (resolvedMapping != null) {
			return resolvedMapping;
		}
		attributeName = this.unqualify(attributeName);
		if (attributeName == null) {
			return null;
		}
		// recurse into the embeddable mappings
		for (AttributeMapping mapping : this.getEmbeddableAttributeMappings()) {
			resolvedMapping = mapping.resolveAttributeMapping(attributeName);
			if (resolvedMapping != null) {
				return resolvedMapping;
			}
		}
		return null;
	}

	protected Iterable<AttributeMapping> getEmbeddableAttributeMappings() {
		Embeddable targetEmbeddable = this.getResolvedTargetEmbeddable();
		return ((targetEmbeddable != null) && (targetEmbeddable != this.getTypeMapping())) ?
				targetEmbeddable.getAttributeMappings() :
				EmptyIterable.<AttributeMapping>instance();
	}

	@Override
	public Column resolveOverriddenColumn(String attributeName) {
		attributeName = this.unqualify(attributeName);
		if (attributeName == null) {
			return null;
		}
		AttributeOverride override = this.valueAttributeOverrideContainer.getSpecifiedOverrideNamed(attributeName);
		// recurse into the target embeddable if necessary
		return (override != null) ? override.getColumn() : this.resolveOverriddenColumnInTargetEmbeddable(attributeName);
	}

	protected Column resolveOverriddenColumnInTargetEmbeddable(String attributeName) {
		Embeddable targetEmbeddable = this.getResolvedTargetEmbeddable();
		return (targetEmbeddable == null) ? null : targetEmbeddable.resolveOverriddenColumn(attributeName);
	}

	@Override
	public Relationship resolveOverriddenRelationship(String attributeName) {
		attributeName = this.unqualify(attributeName);
		if (attributeName == null) {
			return null;
		}
		AssociationOverride override = this.valueAssociationOverrideContainer.getSpecifiedOverrideNamed(attributeName);
		// recurse into the target embeddable if necessary
		return (override != null) ? override.getRelationship() : this.resolveOverriddenRelationshipInTargetEmbeddable(attributeName);
	}

	protected Relationship resolveOverriddenRelationshipInTargetEmbeddable(String attributeName) {
		Embeddable targetEmbeddable = this.getResolvedTargetEmbeddable();
		return (targetEmbeddable == null) ? null : targetEmbeddable.resolveOverriddenRelationship(attributeName);
	}


	//*********** refactoring ***********

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
				super.createRenameTypeEdits(originalType, newName),
				this.createMapKeyClassRenameTypeEdits(originalType, newName),
				this.createTargetClassRenameTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createMapKeyClassRenameTypeEdits(IType originalType, String newName) {
		if (this.specifiedMapKeyClass != null) {
			PersistentType mapKeyType = this.getResolvedMapKeyType();
			if ((mapKeyType != null) && mapKeyType.isFor(originalType.getFullyQualifiedName('.'))) {
				return new SingleElementIterable<ReplaceEdit>(this.createRenameMapKeyClassEdit(originalType, newName));
			}
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createRenameMapKeyClassEdit(IType originalType, String newName) {
		return this.xmlAttributeMapping.createRenameMapKeyClassEdit(originalType, newName);
	}

	protected Iterable<ReplaceEdit> createTargetClassRenameTypeEdits(IType originalType, String newName) {
		if (this.specifiedTargetClass != null) {
			PersistentType targetType = this.getResolvedTargetType();
			if ((targetType != null) && targetType.isFor(originalType.getFullyQualifiedName('.'))) {
				return new SingleElementIterable<ReplaceEdit>(this.xmlAttributeMapping.createRenameTargetClassEdit(originalType, newName));
			}
		}
		return EmptyIterable.instance();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
				super.createMoveTypeEdits(originalType, newPackage),
				this.createMapKeyClassMoveTypeEdits(originalType, newPackage),
				this.createTargetClassMoveTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createMapKeyClassMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (this.specifiedMapKeyClass != null) {
			PersistentType mapKeyType = this.getResolvedMapKeyType();
			if ((mapKeyType != null) && mapKeyType.isFor(originalType.getFullyQualifiedName('.'))) {
				return new SingleElementIterable<ReplaceEdit>(this.createMapKeyClassRenamePackageEdit(newPackage.getElementName()));
			}
		}
		return EmptyIterable.instance();
	}

	protected Iterable<ReplaceEdit> createTargetClassMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (this.specifiedTargetClass != null) {
			PersistentType targetType = this.getResolvedTargetType();
			if ((targetType != null) && targetType.isFor(originalType.getFullyQualifiedName('.'))) {
				return new SingleElementIterable<ReplaceEdit>(this.xmlAttributeMapping.createRenameTargetClassPackageEdit(newPackage.getElementName()));
			}
		}
		return EmptyIterable.instance();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
				super.createRenamePackageEdits(originalPackage, newName),
				this.createMapKeyClassRenamePackageEdits(originalPackage, newName),
				this.createTargetClassRenamePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createMapKeyClassRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		if (this.specifiedMapKeyClass != null) {
			PersistentType mapKeyType = this.getResolvedMapKeyType();
			if ((mapKeyType != null) && mapKeyType.isIn(originalPackage)) {
				return new SingleElementIterable<ReplaceEdit>(this.createMapKeyClassRenamePackageEdit(newName));
			}
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createMapKeyClassRenamePackageEdit(String newName) {
		return this.xmlAttributeMapping.createRenameMapKeyClassPackageEdit(newName);
	}

	protected Iterable<ReplaceEdit> createTargetClassRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		if (this.specifiedTargetClass != null) {
			PersistentType targetType = this.getResolvedTargetType();
			if ((targetType != null) && targetType.isIn(originalPackage)) {
				return new SingleElementIterable<ReplaceEdit>(this.xmlAttributeMapping.createRenameTargetClassPackageEdit(newName));
			}
		}
		return EmptyIterable.instance();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateAttributeType(messages, reporter);
		this.validateTargetClass(messages);
		this.validateMapKeyClass(messages);
		this.orderable.validate(messages, reporter);
		this.collectionTable.validate(messages, reporter);
		this.validateValue(messages, reporter);
		this.validateMapKey(messages, reporter);
		this.validateNoEmbeddableInMappingContainsProhibitedMapping(messages);
	}

	protected void validateAttributeType(List<IMessage> messages, IReporter reporter) {
		JavaPersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		if ((javaAttribute != null) && !javaAttribute.getJpaContainerDefinition().isContainer()) {
			messages.add(
				this.buildValidationMessage(
					this.getAttributeTypeTextRange(),
					JptJpaCoreValidationMessages.ATTRIBUTE_TYPE_IS_NOT_SUPPORTED_COLLECTION_TYPE,
					this.getFullyQualifiedAttributeType()
				)
			);
		}
	}

	protected TextRange getAttributeTypeTextRange() {
		return this.getValidationTextRange();
	}

	protected void validateNoEmbeddableInMappingContainsProhibitedMapping(List<IMessage> messages) {
			Embeddable embeddableClass = getResolvedTargetEmbeddable();
			if (embeddableClass != null) {
				embeddableContainsElementCollection(messages, embeddableClass);
				embeddableContainsProhibitedRelationshipMapping(messages, embeddableClass);
				embeddableHierarchyContainsProhibitedMapping(messages, embeddableClass, new ArrayList<Embeddable>());
			}
			embeddableClass = getResolvedMapKeyEmbeddable();
			if (embeddableClass != null) {
				embeddableContainsElementCollection(messages, embeddableClass);
				embeddableContainsProhibitedRelationshipMapping(messages, embeddableClass);
				embeddableHierarchyContainsProhibitedMapping(messages, embeddableClass, new ArrayList<Embeddable>());
			}
		}
	
		private void embeddableHierarchyContainsProhibitedMapping(List<IMessage> messages, Embeddable parentEmbeddable, List<Embeddable> visited) {
			Iterable<AttributeMapping> embeddedIterable = parentEmbeddable.getAllAttributeMappings(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
			for(AttributeMapping mapping : embeddedIterable) {
				Embeddable embeddable = ((BaseEmbeddedMapping)mapping).getTargetEmbeddable();
				if (embeddable != null && embeddable != parentEmbeddable) {
					embeddableContainsElementCollection(messages, embeddable);
					embeddableContainsProhibitedRelationshipMapping(messages, embeddable);
					if (!visited.contains(embeddable)) {
						visited.add(embeddable);
						embeddableHierarchyContainsProhibitedMapping(messages, embeddable, visited);
					}
				}
			}
			Iterable<AttributeMapping> embeddedIdIterable = parentEmbeddable.getAllAttributeMappings(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
			for(AttributeMapping mapping : embeddedIdIterable) {
				Embeddable embeddable = ((BaseEmbeddedMapping)mapping).getTargetEmbeddable();
				if (embeddable != null && embeddable != parentEmbeddable) {
					embeddableContainsElementCollection(messages, embeddable);
					embeddableContainsProhibitedRelationshipMapping(messages, embeddable);
					if (!visited.contains(embeddable)) {
						visited.add(embeddable);
						embeddableHierarchyContainsProhibitedMapping(messages, embeddable, visited);
					}
				}
			}
		}
	
		private void embeddableContainsProhibitedRelationshipMapping(List<IMessage> messages, Embeddable embeddable) {
			boolean prohibitedMappingFound = false;
			RelationshipMapping relationshipMapping = null; 
			Iterable<AttributeMapping> manyToManyMappings = embeddable.getAllAttributeMappings(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			Iterable<AttributeMapping> oneToManyMappings = embeddable.getAllAttributeMappings(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
			if (oneToManyMappings.iterator().hasNext()) {
				relationshipMapping = (RelationshipMapping)oneToManyMappings.iterator().next();
				prohibitedMappingFound = true;
			}
			if (manyToManyMappings.iterator().hasNext()) {
				relationshipMapping = (RelationshipMapping)manyToManyMappings.iterator().next();
				prohibitedMappingFound = true;
			}
			Iterable<AttributeMapping> manyToOneMappings = embeddable.getAllAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			if (manyToOneMappings.iterator().hasNext()) {
				relationshipMapping = (RelationshipMapping)manyToOneMappings.iterator().next();
				if (((RelationshipMapping)manyToOneMappings.iterator().next()).getRelationshipOwner() != null 
						||((ManyToOneRelationship2_0)relationshipMapping.getRelationship()).getJoinTableStrategy().getJoinTable() != null) {
					prohibitedMappingFound = true;
				}
			}
			Iterable<AttributeMapping> oneToOneMappings = embeddable.getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
			if (oneToOneMappings.iterator().hasNext()) {
				relationshipMapping = (RelationshipMapping)oneToOneMappings.iterator().next();
				if (((RelationshipMapping)oneToOneMappings.iterator().next()).getRelationshipOwner() != null
						|| ((OneToOneRelationship2_0)relationshipMapping.getRelationship()).getJoinTableStrategy().getJoinTable() != null) {
					prohibitedMappingFound = true;
				}
			}
			if (prohibitedMappingFound) {
				messages.add(
					this.buildValidationMessage(
						this.getValidationTextRange(),
						JptJpaCoreValidationMessages.ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_PROHIBITED_RELATIONSHIP_MAPPING,
						embeddable.getName(),
						relationshipMapping.getName()
					)
				);
			}
		}
		
		private void embeddableContainsElementCollection(List<IMessage> messages, Embeddable embeddable) {
			Iterable<AttributeMapping> elementCollectionMappings = embeddable.getAllAttributeMappings(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
			if (elementCollectionMappings.iterator().hasNext()) {
				messages.add(
					this.buildValidationMessage(
						this.getValidationTextRange(),
						JptJpaCoreValidationMessages.ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_ELEMENT_COLLECTION_MAPPING,
						embeddable.getName(),
						elementCollectionMappings.iterator().next().getName()
					)
				);							
			}
	 	}
		
		protected void validateTargetClass(List<IMessage> messages) {
		if (StringTools.isBlank(this.getTargetClass())) {
			messages.add(
				this.buildValidationMessage(
					this.getTargetClassTextRange(),
					JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED
				)
			);
			return;
		}
		if (MappingTools.typeIsBasic(this.getJavaProject(), this.getFullyQualifiedTargetClass())) {
			return;
		}
		//If a persistent type exists, but no underlying java class, then 
		//you will get validation on that persistent type instead of here
		if (this.getResolvedTargetType() == null) {
			IType jdtType = JDTTools.findType(this.getJavaProject(), this.getFullyQualifiedTargetClass());
			if (jdtType == null) {
				messages.add(
					this.buildValidationMessage(
						this.getTargetClassTextRange(),
						JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_DOES_NOT_EXIST,
						this.getFullyQualifiedTargetClass()
					)
				);
			}
			return;
		}
		if (this.getResolvedTargetEmbeddable() == null) {
			messages.add(
				this.buildValidationMessage(
					this.getTargetClassTextRange(),
					JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE,
					this.getFullyQualifiedTargetClass()
				)
			);
		}
	}
	protected TextRange getTargetClassTextRange() {
		return this.getValidationTextRange(this.xmlAttributeMapping.getTargetClassTextRange());
	}

	protected void validateMapKeyClass(List<IMessage> messages) {
		JavaPersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		if ((javaAttribute != null) && javaAttribute.getJpaContainerDefinition().isMap()) {
			this.validateMapKeyClass_(messages);
		}
	}

	protected void validateMapKeyClass_(List<IMessage> messages) {
		if (StringTools.isBlank(this.getMapKeyClass())) {
			messages.add(
				this.buildValidationMessage(
					this.getMapKeyClassTextRange(),
					JptJpaCoreValidationMessages.MAP_KEY_CLASS_NOT_DEFINED
				)
			);
			return;
		}

		if (MappingTools.typeIsBasic(this.getJavaProject(), this.getFullyQualifiedMapKeyClass())) {
			return;
		}

		if (this.getResolvedMapKeyType() == null) {
			IType mapKeyJdtType = JDTTools.findType(this.getJavaProject(), this.getFullyQualifiedMapKeyClass());
			if (mapKeyJdtType == null) {
				messages.add(
					this.buildValidationMessage(
						this.getMapKeyClassTextRange(),
						JptJpaCoreValidationMessages.MAP_KEY_CLASS_NOT_EXIST,
						this.getFullyQualifiedMapKeyClass()
					)
				);
			}
			return;
		}
		if (this.getResolvedMapKeyEmbeddable() == null && this.getResolvedMapKeyEntity() == null) {
			messages.add(
				this.buildValidationMessage(
					this.getMapKeyClassTextRange(),
					JptJpaCoreValidationMessages.MAP_KEY_CLASS_MUST_BE_ENTITY_EMBEDDABLE_OR_BASIC_TYPE,
					this.getFullyQualifiedMapKeyClass()
				)
			);
		}
	}

	protected TextRange getMapKeyClassTextRange() {
		return this.getValidationTextRange(this.xmlAttributeMapping.getMapKeyClassTextRange());
	}

	public void validateValue(List<IMessage> messages, IReporter reporter) {
		this.converter.validate(messages, reporter);
		//TODO should we handle validation when the type is embeddable,
		//but a value column is specified, or things like that if that is invalid?
		switch (this.valueType) {
			case BASIC_TYPE :
				this.valueColumn.validate(messages, reporter);
				break;
			case EMBEDDABLE_TYPE :
				this.valueAttributeOverrideContainer.validate(messages, reporter);
				this.valueAssociationOverrideContainer.validate(messages, reporter);
				break;
			default :
				break;
		}
	}

	protected void validateMapKey(List<IMessage> messages, IReporter reporter) {
		if (this.getMapKey() == null) {
			this.validateMapKey_(messages, reporter);
		} else {
			//TODO validate that the map key refers to an existing attribute
		}
	}

	protected void validateMapKey_(List<IMessage> messages, IReporter reporter) {
		switch (this.keyType) {
			case BASIC_TYPE :
				this.mapKeyColumn.validate(messages, reporter);
				this.mapKeyConverter.validate(messages, reporter);
				break;
			case ENTITY_TYPE :
				for (OrmJoinColumn joinColumn : this.getMapKeyJoinColumns()) {
					joinColumn.validate(messages, reporter);
				}
				break;
			case EMBEDDABLE_TYPE :
				this.mapKeyAttributeOverrideContainer.validate(messages, reporter);
				//validate map key association overrides
				break;
			default :
				break;
		}
	}

	// ********** completion proposals **********
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.collectionTable.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.valueColumn.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.converter.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.orderable.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.valueAttributeOverrideContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.valueAssociationOverrideContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.targetClassTouches(pos)) {
			return this.getCandidateClassNames();
		}
		if (this.mapKeyClassTouches(pos)) {
			return this.getCandidateClassNames();
		}
		if (this.mapKeyNameTouches(pos)) {
			return this.getCandidateMapKeyNames();
		}
		result = this.mapKeyColumn.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.mapKeyConverter.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.mapKeyAttributeOverrideContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (OrmJoinColumn joinColumn : this.getMapKeyJoinColumns()) {
			result = joinColumn.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected Iterable<String> getCandidateClassNames() {
		return IterableTools.concatenate(
				MappingTools.getSortedJavaClassNames(getJavaProject()),
				MappingTools.getPrimaryBasicTypeNames()
			);
	}

	protected boolean targetClassTouches(int pos) {
		return this.xmlAttributeMapping.targetClassTouches(pos);
	}

	protected boolean mapKeyClassTouches(int pos) {
		return this.xmlAttributeMapping.getMapKeyClass() == null ? false :
			this.xmlAttributeMapping.getMapKeyClass().classNameTouches(pos);
	}

	protected boolean mapKeyNameTouches(int pos) {
		return  this.getXmlMapKey() == null ? false : this.getXmlMapKey().mapKeyNameTouches(pos);
	}

	// ********** abstract owner **********

	/**
	 * the various (column and override) owners have lots of common
	 * interactions with the mapping
	 */
	protected abstract class AbstractOwner
	{
		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmElementCollectionMapping2_0.this.getTypeMapping();
		}

		public String getDefaultTableName() {
			return this.getCollectionTable().getName();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			OrmCollectionTable2_0 table = this.getCollectionTable();
			return ObjectTools.equals(table.getName(), tableName) ? table.getDbTable() : null;
		}

		public Iterable<String> getCandidateTableNames() {
			return EmptyIterable.instance();
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name.  the table is always the collection table
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return ObjectTools.notEquals(this.getDefaultTableName(), tableName);
		}

		public TextRange getValidationTextRange() {
			return AbstractOrmElementCollectionMapping2_0.this.getValidationTextRange();
		}

		public Iterable<String> getJavaOverrideNames() {
			return null;
		}

		protected String getMappingName() {
			return AbstractOrmElementCollectionMapping2_0.this.getName();
		}

		protected OrmCollectionTable2_0 getCollectionTable() {
			return AbstractOrmElementCollectionMapping2_0.this.getCollectionTable();
		}

		protected PersistentAttribute getPersistentAttribute() {
			return AbstractOrmElementCollectionMapping2_0.this.getPersistentAttribute();
		}

		protected XmlElementCollection getXmlMapping() {
			return AbstractOrmElementCollectionMapping2_0.this.getXmlAttributeMapping();
		}
	}


	// ********** value column owner **********

	protected class ValueColumnOwner
		extends AbstractOwner
		implements OrmColumn.Owner
	{
		public XmlColumn getXmlColumn() {
			return this.getXmlMapping().getColumn();
		}

		public XmlColumn buildXmlColumn() {
			XmlColumn xmlColumn = OrmFactory.eINSTANCE.createXmlColumn();
			this.getXmlMapping().setColumn(xmlColumn);
			return xmlColumn;
		}

		public void removeXmlColumn() {
			this.getXmlMapping().setColumn(null);
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return this.getMappingName();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return new NamedColumnValidator(this.getPersistentAttribute(), (ReadOnlyBaseColumn) column, new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** map key column owner **********

	protected class MapKeyColumnOwner
		extends AbstractOwner
		implements OrmColumn.Owner
	{
		public XmlColumn getXmlColumn() {
			return this.getXmlMapping().getMapKeyColumn();
		}

		public XmlColumn buildXmlColumn() {
			XmlColumn xmlColumn = OrmFactory.eINSTANCE.createXmlColumn();
			this.getXmlMapping().setMapKeyColumn(xmlColumn);
			return xmlColumn;
		}

		public void removeXmlColumn() {
			this.getXmlMapping().setMapKeyColumn(null);
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return this.getMappingName() + "_KEY"; //$NON-NLS-1$
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return new MapKeyColumnValidator(this.getPersistentAttribute(), (ReadOnlyBaseColumn) column, new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** value association override container owner **********

	protected class ValueAssociationOverrideContainerOwner
		extends AbstractOwner
		implements OrmAssociationOverrideContainer2_0.Owner
	{
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmElementCollectionMapping2_0.this.getResolvedTargetEmbeddable();
		}

		public Iterable<String> getAllOverridableNames() {
			TypeMapping typeMapping = this.getOverridableTypeMapping();
			return (typeMapping != null) ? typeMapping.getAllOverridableAssociationNames() : EmptyIterable.<String>instance();
		}

		public EList<XmlAssociationOverride> getXmlOverrides() {
			return this.getXmlMapping().getAssociationOverrides();
		}

		public Relationship resolveOverriddenRelationship(String attributeName) {
			return MappingTools.resolveOverriddenRelationship(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner columnOwner) {
			return new AssociationOverrideJoinColumnValidator(this.getPersistentAttribute(), (ReadOnlyAssociationOverride) override, (ReadOnlyJoinColumn) column, (ReadOnlyJoinColumn.Owner) columnOwner, new CollectionTableTableDescriptionProvider());
		}

		public JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideContainer container) {
			return new AssociationOverrideValidator(this.getPersistentAttribute(), (ReadOnlyAssociationOverride) override, (AssociationOverrideContainer) container, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildJoinTableJoinColumnValidator(ReadOnlyAssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner) {
			return JptValidator.Null.instance();
		}

		public JptValidator buildJoinTableInverseJoinColumnValidator(ReadOnlyAssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner) {
			return JptValidator.Null.instance();
		}

		public JptValidator buildJoinTableValidator(ReadOnlyAssociationOverride override, ReadOnlyTable table) {
			return JptValidator.Null.instance();
		}
	}


	// ********** value attribute override container owner **********

	protected class ValueAttributeOverrideContainerOwner
		extends AbstractOwner
		implements OrmAttributeOverrideContainer.Owner
	{
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmElementCollectionMapping2_0.this.getResolvedTargetEmbeddable();
		}

		public Iterable<String> getAllOverridableNames() {
			TypeMapping overriddenTypeMapping = this.getOverridableTypeMapping();
			return (overriddenTypeMapping != null) ? overriddenTypeMapping.getAllOverridableAttributeNames() : EmptyIterable.<String>instance();
		}

		public EList<XmlAttributeOverride> getXmlOverrides() {
			return this.getXmlMapping().getAttributeOverrides();
		}

		public Column resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideContainer container) {
			return new AttributeOverrideValidator(this.getPersistentAttribute(), (ReadOnlyAttributeOverride) override, (AttributeOverrideContainer) container, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner columnOwner) {
			return new AttributeOverrideColumnValidator(this.getPersistentAttribute(), (ReadOnlyAttributeOverride) override, column, new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** map key attribute override container owner **********

	protected class MapKeyAttributeOverrideContainerOwner
		extends AbstractOwner
		implements OrmAttributeOverrideContainer.Owner
	{
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmElementCollectionMapping2_0.this.getResolvedMapKeyEmbeddable();
		}

		public Iterable<String> getAllOverridableNames() {
			TypeMapping overriddenTypeMapping = this.getOverridableTypeMapping();
			return (overriddenTypeMapping != null) ? overriddenTypeMapping.getAllOverridableAttributeNames() : EmptyIterable.<String>instance();
		}

		public EList<XmlAttributeOverride> getXmlOverrides() {
			return this.getXmlMapping().getMapKeyAttributeOverrides();
		}

		public Column resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideContainer container) {
			return new MapKeyAttributeOverrideValidator(this.getPersistentAttribute(), (ReadOnlyAttributeOverride) override, (AttributeOverrideContainer) container, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner columnOwner) {
			return new MapKeyAttributeOverrideColumnValidator(this.getPersistentAttribute(), (ReadOnlyAttributeOverride) override, column, new CollectionTableTableDescriptionProvider());
		}
	}

	// ********** map key join column owner **********

	protected class MapKeyJoinColumnOwner
		implements ReadOnlyJoinColumn.Owner
	{
		protected MapKeyJoinColumnOwner() {
			super();
		}

		public String getDefaultTableName() {
			return AbstractOrmElementCollectionMapping2_0.this.getCollectionTable().getName();
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return AbstractOrmElementCollectionMapping2_0.this.getName() + "_KEY"; //$NON-NLS-1$
		}

		public String getAttributeName() {
			return AbstractOrmElementCollectionMapping2_0.this.getName();
		}

		protected PersistentAttribute getPersistentAttribute() {
			return AbstractOrmElementCollectionMapping2_0.this.getPersistentAttribute();
		}

		public Entity getRelationshipTarget() {
			return AbstractOrmElementCollectionMapping2_0.this.getResolvedMapKeyEntity();
		}

		/**
		 * If there is a specified table name it needs to be the same as
		 * the default table name.  The table is always the collection table.
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return ObjectTools.notEquals(this.getDefaultTableName(), tableName);
		}

		public Iterable<String> getCandidateTableNames() {
			return EmptyIterable.instance();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			return AbstractOrmElementCollectionMapping2_0.this.getCollectionTable().getDbTable();
		}

		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			return AbstractOrmElementCollectionMapping2_0.this.getResolvedMapKeyEntity().getPrimaryDbTable();
		}

		public int getJoinColumnsSize() {
			return AbstractOrmElementCollectionMapping2_0.this.getMapKeyJoinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return AbstractOrmElementCollectionMapping2_0.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return new MapKeyJoinColumnValidator(
				this.getPersistentAttribute(),
				(ReadOnlyJoinColumn) column,
				this,
				new CollectionTableTableDescriptionProvider());
		}
	}
}
