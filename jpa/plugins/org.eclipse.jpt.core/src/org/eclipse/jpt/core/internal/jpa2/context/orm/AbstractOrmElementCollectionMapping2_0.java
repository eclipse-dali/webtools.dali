/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumn.Owner;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.OverrideContainer;
import org.eclipse.jpt.core.context.Override_;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.Relationship;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.context.orm.OrmEnumeratedConverter;
import org.eclipse.jpt.core.context.orm.OrmLobConverter;
import org.eclipse.jpt.core.context.orm.OrmOrderable;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTemporalConverter;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.core.internal.jpa1.context.AssociationOverrideJoinColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.AssociationOverrideValidator;
import org.eclipse.jpt.core.internal.jpa1.context.AttributeOverrideColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.AttributeOverrideValidator;
import org.eclipse.jpt.core.internal.jpa1.context.CollectionTableTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.EmbeddableOverrideDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.MapKeyAttributeOverrideColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.MapKeyAttributeOverrideValidator;
import org.eclipse.jpt.core.internal.jpa1.context.MapKeyColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.orm.NullOrmConverter;
import org.eclipse.jpt.core.internal.jpa2.context.CollectionTableValidator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrderable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmPersistentAttribute2_0;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
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

	protected FetchType specifiedFetch;
	protected FetchType defaultFetch;

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

	protected final OrmColumn mapKeyColumn;

	protected final OrmAttributeOverrideContainer mapKeyAttributeOverrideContainer;


	protected static final OrmConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new OrmConverter.Adapter[] {
		OrmEnumeratedConverter.Adapter.instance(),
		OrmTemporalConverter.Adapter.instance(),
		OrmLobConverter.Adapter.instance()
	};
	protected static final Iterable<OrmConverter.Adapter> CONVERTER_ADAPTERS = new ArrayIterable<OrmConverter.Adapter>(CONVERTER_ADAPTER_ARRAY);


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
		this.mapKeyAttributeOverrideContainer = this.buildMapKeyAttributeOverrideContainer();
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
		this.mapKeyAttributeOverrideContainer.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultTargetClass(this.buildDefaultTargetClass());
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

		this.mapKeyColumn.update();
		this.mapKeyAttributeOverrideContainer.update();
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
		return this.resolvePersistentType(this.getTargetClass());
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

	public OrmOrderable getOrderable() {
		return this.orderable;
	}

	protected OrmOrderable2_0 buildOrderable() {
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
		public org.eclipse.jpt.db.Table resolveDbTable(String tableName) {
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
		return this.getContextNodeFactory2_0().buildOrmCollectionTable(this, this.buildCollectionTableOwner());
	}

	protected Table.Owner buildCollectionTableOwner() {
		return new CollectionTableOwner();
	}

	public XmlCollectionTable getResourceCollectionTable() {
		return this.xmlAttributeMapping.getCollectionTable();
	}

	protected class CollectionTableOwner
		implements Table.Owner
	{
		public JptValidator buildTableValidator(Table table, TableTextRangeResolver textRangeResolver) {
			return new CollectionTableValidator(AbstractOrmElementCollectionMapping2_0.this.getPersistentAttribute(), (CollectionTable2_0) table, textRangeResolver);
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
			// note: we may also clear the XML value we want;
			// but if we leave it, the resulting sync will screw things up...
			this.clearXmlConverterValues();
			OrmConverter.Adapter converterAdapter = this.getConverterAdapter(converterType);
			this.setConverter_(this.buildConverter(converterAdapter));
			this.converter.initialize();
		}
	}

	protected OrmConverter buildConverter(OrmConverter.Adapter converterAdapter) {
		 return (converterAdapter != null) ?
				converterAdapter.buildNewConverter(this, this.getContextNodeFactory()) :
				this.buildNullConverter();
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
		return this.buildNullConverter();
	}

	protected void syncConverter() {
		OrmConverter.Adapter adapter = this.getXmlConverterAdapter();
		if (adapter == null) {
			if (this.converter.getType() != null) {
				this.setConverter_(this.buildNullConverter());
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

	protected OrmConverter buildNullConverter() {
		return new NullOrmConverter(this);
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
		return this.resolvePersistentType(this.getMapKeyClass());
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


	// ********** Java override lookups **********

	protected JavaAttributeOverride getSpecifiedJavaValueAttributeOverrideNamed(String attributeName) {
		JavaElementCollectionMapping2_0 javaMapping = this.getJavaElementCollectionMapping();
		return (javaMapping == null) ? null :
			javaMapping.getValueAttributeOverrideContainer().getSpecifiedOverrideNamed(attributeName);
	}

	protected JavaAssociationOverride getSpecifiedJavaValueAssociationOverrideNamed(String attributeName) {
		JavaElementCollectionMapping2_0 javaMapping = this.getJavaElementCollectionMapping();
		return (javaMapping == null) ? null :
			javaMapping.getValueAssociationOverrideContainer().getSpecifiedOverrideNamed(attributeName);
	}

	protected JavaAttributeOverride getSpecifiedJavaMapKeyAttributeOverrideNamed(String attributeName) {
		JavaElementCollectionMapping2_0 javaMapping = this.getJavaElementCollectionMapping();
		return (javaMapping == null) ? null :
			javaMapping.getMapKeyAttributeOverrideContainer().getSpecifiedOverrideNamed(attributeName);
	}

	protected JavaElementCollectionMapping2_0 getJavaElementCollectionMapping() {
		AttributeMapping javaAttributeMapping = this.getJavaAttributeMapping();
		return (javaAttributeMapping instanceof JavaElementCollectionMapping2_0) ?
			(JavaElementCollectionMapping2_0) javaAttributeMapping :
			null;
	}

	protected JavaAttributeMapping getJavaAttributeMapping() {
		JavaPersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		return (javaAttribute == null) ? null : javaAttribute.getMapping();
	}


	// ********** misc **********

	@Override
	public OrmPersistentAttribute2_0 getParent() {
		return (OrmPersistentAttribute2_0) super.getParent();
	}

	@Override
	public OrmPersistentAttribute2_0 getPersistentAttribute() {
		return (OrmPersistentAttribute2_0) super.getPersistentAttribute();
	}

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
		return this.getPersistentAttribute().getMetamodelContainerFieldTypeName();
	}

	@Override
	public String getMetamodelTypeName() {
		if (this.valueType == Type.BASIC_TYPE) {
			return this.getTargetClass();
		}
		PersistentType targetType = this.getResolvedTargetType();
		if (targetType == null) {
			return MetamodelField.DEFAULT_TYPE_NAME;
		}
		String targetTypeName = targetType.getName();
		return (targetTypeName != null) ? targetTypeName : MetamodelField.DEFAULT_TYPE_NAME;
	}

	@Override
	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		this.addMetamodelFieldMapKeyTypeArgumentNameTo(typeArgumentNames);
		super.addMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
	}

	protected void addMetamodelFieldMapKeyTypeArgumentNameTo(ArrayList<String> typeArgumentNames) {
		String keyTypeName = this.getPersistentAttribute().getMetamodelContainerFieldMapKeyTypeName();
		if (keyTypeName != null) {
			typeArgumentNames.add(keyTypeName);
		}
	}

	public String getMetamodelFieldMapKeyTypeName() {
		return MappingTools.getMetamodelFieldMapKeyTypeName(this);
	}


	// ********** embedded mappings **********

	public Iterator<String> candidateMapKeyNames() {
		return new CompositeIterator<String>(this.allTargetEmbeddableAttributeNamesLists());
	}

	/**
	 * Return a list of lists; each nested list holds the names for one of the
	 * embedded mapping's target embeddable type mapping's attribute mappings
	 * (attribute or association mappings, depending on the specified transformer).
	 */
	protected Iterator<Iterator<String>> allTargetEmbeddableAttributeNamesLists() {
		return new TransformationIterator<AttributeMapping, Iterator<String>>(this.allTargetEmbeddableAttributeMappings(), AttributeMappingTools.ALL_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterator<AttributeMapping> allTargetEmbeddableAttributeMappings() {
		Embeddable targetEmbeddable = this.getResolvedTargetEmbeddable();
		return (targetEmbeddable != null) ? targetEmbeddable.allAttributeMappings() : EmptyIterator.<AttributeMapping> instance();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterator<String> allMappingNames() {
		return new CompositeIterator<String>(super.allMappingNames(), this.allEmbeddableAttributeMappingNames());
	}

	protected Iterator<String> allEmbeddableAttributeMappingNames() {
		return this.qualifiedEmbeddableOverridableMappingNames(AttributeMappingTools.ALL_MAPPING_NAMES_TRANSFORMER);
	}

	@Override
	public Iterator<String> allOverridableAttributeMappingNames() {
		return this.qualifiedEmbeddableOverridableMappingNames(AttributeMappingTools.ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER);
	}

	@Override
	public Iterator<String> allOverridableAssociationMappingNames() {
		return this.qualifiedEmbeddableOverridableMappingNames(AttributeMappingTools.ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterator<String> qualifiedEmbeddableOverridableMappingNames(Transformer<AttributeMapping, Iterator<String>> transformer) {
		return new TransformationIterator<String, String>(this.embeddableOverridableMappingNames(transformer), this.buildQualifierTransformer());
	}

	protected Iterator<String> embeddableOverridableMappingNames(Transformer<AttributeMapping, Iterator<String>> transformer) {
		return new CompositeIterator<String>(this.embeddableOverridableMappingNamesLists(transformer));
	}

	/**
	 * Return a list of lists; each nested list holds the names for one of the
	 * embedded mapping's target embeddable type mapping's attribute mappings
	 * (attribute or association mappings, depending on the specified transformer).
	 */
	protected Iterator<Iterator<String>> embeddableOverridableMappingNamesLists(Transformer<AttributeMapping, Iterator<String>> transformer) {
		return new TransformationIterator<AttributeMapping, Iterator<String>>(this.embeddableAttributeMappings(), transformer);
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
		for (AttributeMapping mapping : CollectionTools.iterable(this.embeddableAttributeMappings())) {
			resolvedMapping = mapping.resolveAttributeMapping(attributeName);
			if (resolvedMapping != null) {
				return resolvedMapping;
			}
		}
		return null;
	}

	protected Iterator<AttributeMapping> embeddableAttributeMappings() {
		Embeddable targetEmbeddable = this.getResolvedTargetEmbeddable();
		return ((targetEmbeddable != null) && (targetEmbeddable != this.getTypeMapping())) ?
				targetEmbeddable.attributeMappings() :
				EmptyIterator.<AttributeMapping>instance();
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
		return new CompositeIterable<ReplaceEdit>(
				super.createRenameTypeEdits(originalType, newName),
				this.createMapKeyClassRenameTypeEdits(originalType, newName),
				this.createTargetClassRenameTypeEdits(originalType, newName),
				this.createConverterRenameTypeEdits(originalType, newName)
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

	protected Iterable<ReplaceEdit> createConverterRenameTypeEdits(IType originalType, String newName) {
		return (this.converter != null) ?
				this.converter.createRenameTypeEdits(originalType, newName) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
				super.createMoveTypeEdits(originalType, newPackage),
				this.createMapKeyClassMoveTypeEdits(originalType, newPackage),
				this.createTargetClassMoveTypeEdits(originalType, newPackage),
				this.createConverterMoveTypeEdits(originalType, newPackage)
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

	protected Iterable<ReplaceEdit> createConverterMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return (this.converter != null) ?
				this.converter.createMoveTypeEdits(originalType, newPackage) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				super.createRenamePackageEdits(originalPackage, newName),
				this.createMapKeyClassRenamePackageEdits(originalPackage, newName),
				this.createTargetClassRenamePackageEdits(originalPackage, newName),
				this.createConverterRenamePackageEdits(originalPackage, newName)
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

	protected Iterable<ReplaceEdit> createConverterRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return (this.converter != null) ?
				this.converter.createRenamePackageEdits(originalPackage, newName) :
				EmptyIterable.<ReplaceEdit>instance();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateTargetClass(messages);
		this.validateMapKeyClass(messages);
		this.orderable.validate(messages, reporter);
		this.collectionTable.validate(messages, reporter);
		this.validateValue(messages, reporter);
		this.validateMapKey(messages, reporter);
	}

	protected void validateTargetClass(List<IMessage> messages) {
		if (this.getTargetClass() == null) {
			if (this.isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED,
						new String[] {this.name},
						this,
						this.getValidationTextRange()
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED,
						EMPTY_STRING_ARRAY,
						this,
						this.getValidationTextRange()
					)
				);
			}
		}
		//TODO this does not give an error for unmapped, unlisted types that aren't basic - bug 310464
		if (this.getResolvedTargetType() != null) {
			if (this.getResolvedTargetEmbeddable() == null) {
				if (this.isVirtual()) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE,
							new String[] {this.name, this.getTargetClass()},
							this,
							this.getValidationTextRange()
						)
					);
				} else {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE,
							new String[] {this.getTargetClass(), this.name},
							this,
							this.getTargetClassTextRange()
						)
					);
				}
			}
		}
	}

	protected TextRange getTargetClassTextRange() {
		return this.xmlAttributeMapping.getTargetClassTextRange();
	}

	protected void validateMapKeyClass(List<IMessage> messages) {
		JavaPersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		if ((javaAttribute != null) && ! javaAttribute.getJpaContainerDefinition().isMap()) {
			return;
		}
		if (this.getMapKeyClass() == null) {
			if (this.isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_MAP_KEY_CLASS_NOT_DEFINED,
						new String[] {this.name},
						this,
						this.getValidationTextRange()
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ELEMENT_COLLECTION_MAP_KEY_CLASS_NOT_DEFINED,
						EMPTY_STRING_ARRAY,
						this,
						this.getValidationTextRange()
					)
				);
			}
		}
	}

	public void validateValue(List<IMessage> messages, IReporter reporter) {
		//TODO should we handle validation when the type is embeddable,
		//but a value column is specified, or things like that if that is invalid?
		switch (this.valueType) {
			case BASIC_TYPE :
				this.valueColumn.validate(messages, reporter);
				this.converter.validate(messages, reporter);
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
				//validate map key converter
				break;
			case ENTITY_TYPE :
				//validate map key join columns
				break;
			case EMBEDDABLE_TYPE :
				this.mapKeyAttributeOverrideContainer.validate(messages, reporter);
				//validate map key association overrides
				break;
			default :
				break;
		}
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

		public org.eclipse.jpt.db.Table resolveDbTable(String tableName) {
			OrmCollectionTable2_0 table = this.getCollectionTable();
			return table.getName().equals(tableName) ? table.getDbTable() : null;
		}

		public Iterator<String> candidateTableNames() {
			return EmptyIterator.instance();
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name.  the table is always the collection table
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return Tools.valuesAreDifferent(this.getDefaultTableName(), tableName);
		}

		public TextRange getValidationTextRange() {
			return AbstractOrmElementCollectionMapping2_0.this.getValidationTextRange();
		}

		protected String getMappingName() {
			return AbstractOrmElementCollectionMapping2_0.this.getName();
		}

		protected boolean mappingIsVirtual() {
			return AbstractOrmElementCollectionMapping2_0.this.isVirtual();
		}

		protected OrmCollectionTable2_0 getCollectionTable() {
			return AbstractOrmElementCollectionMapping2_0.this.getCollectionTable();
		}

		protected OrmPersistentAttribute2_0 getPersistentAttribute() {
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

		public String getDefaultColumnName() {
			return this.getMappingName();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new NamedColumnValidator(this.getPersistentAttribute(), (BaseColumn) column, (BaseColumnTextRangeResolver) textRangeResolver, new CollectionTableTableDescriptionProvider());
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

		public String getDefaultColumnName() {
			return this.getMappingName() + "_KEY"; //$NON-NLS-1$
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new MapKeyColumnValidator(this.getPersistentAttribute(), (BaseColumn) column, (BaseColumnTextRangeResolver) textRangeResolver, new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** value association override container owner **********

	protected class ValueAssociationOverrideContainerOwner
		extends AbstractOwner
		implements OrmAssociationOverrideContainer.Owner
	{
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmElementCollectionMapping2_0.this.getResolvedTargetEmbeddable();
		}

		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = this.getOverridableTypeMapping();
			return (typeMapping != null) ? typeMapping.allOverridableAssociationNames() : EmptyIterator.<String>instance();
		}

		public EList<XmlAssociationOverride> getXmlOverrides() {
			return this.getXmlMapping().getAssociationOverrides();
		}

		public Relationship resolveOverriddenRelationship(String attributeName) {
			if (this.mappingIsVirtual() && ! this.getTypeMapping().isMetadataComplete()) {
				JavaAssociationOverride override = AbstractOrmElementCollectionMapping2_0.this.getSpecifiedJavaValueAssociationOverrideNamed(attributeName);
				if (override != null) {
					return override.getRelationship();
				}
			}
			return MappingTools.resolveOverriddenRelationship(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner columnOwner, BaseColumnTextRangeResolver textRangeResolver) {
			return new AssociationOverrideJoinColumnValidator(this.getPersistentAttribute(), (AssociationOverride) override, (JoinColumn) column, (JoinColumn.Owner) columnOwner, (JoinColumnTextRangeResolver) textRangeResolver, new CollectionTableTableDescriptionProvider());
		}

		public JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
			throw new UnsupportedOperationException("An element collection containing a nested relationship mapping using a JoinTable is not supported"); //$NON-NLS-1$
		}

		public JptValidator buildValidator(Override_ override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new AssociationOverrideValidator(this.getPersistentAttribute(), (AssociationOverride) override, (AssociationOverrideContainer) container, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
			throw new UnsupportedOperationException("An element collection containing a nested relationship mapping using a JoinTable is not supported"); //$NON-NLS-1$
		}

		public JptValidator buildTableValidator(AssociationOverride override, Table table, TableTextRangeResolver textRangeResolver) {
			throw new UnsupportedOperationException("An element collection containing a nested relationship mapping using a JoinTable is not supported"); //$NON-NLS-1$
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

		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = this.getOverridableTypeMapping();
			return (typeMapping != null) ? typeMapping.allOverridableAttributeNames() : EmptyIterator.<String>instance();
		}

		public EList<XmlAttributeOverride> getXmlOverrides() {
			return this.getXmlMapping().getAttributeOverrides();
		}

		public Column resolveOverriddenColumn(String attributeName) {
			if (this.mappingIsVirtual() && ! this.getTypeMapping().isMetadataComplete()) {
				JavaAttributeOverride override = AbstractOrmElementCollectionMapping2_0.this.getSpecifiedJavaValueAttributeOverrideNamed(attributeName);
				if (override != null) {
					return override.getColumn();
				}
			}
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildValidator(Override_ override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new AttributeOverrideValidator(this.getPersistentAttribute(), (AttributeOverride) override, (AttributeOverrideContainer) container, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner columnOwner, BaseColumnTextRangeResolver textRangeResolver) {
			return new AttributeOverrideColumnValidator(this.getPersistentAttribute(), (AttributeOverride) override, column, textRangeResolver, new CollectionTableTableDescriptionProvider());
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

		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = this.getOverridableTypeMapping();
			return (typeMapping != null) ? typeMapping.allOverridableAttributeNames() : EmptyIterator.<String>instance();
		}

		public EList<XmlAttributeOverride> getXmlOverrides() {
			return this.getXmlMapping().getMapKeyAttributeOverrides();
		}

		public Column resolveOverriddenColumn(String attributeName) {
			if (this.mappingIsVirtual() && ! this.getTypeMapping().isMetadataComplete()) {
				JavaAttributeOverride override = AbstractOrmElementCollectionMapping2_0.this.getSpecifiedJavaMapKeyAttributeOverrideNamed(attributeName);
				if (override != null) {
					return override.getColumn();
				}
			}
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildValidator(Override_ override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new MapKeyAttributeOverrideValidator(this.getPersistentAttribute(), (AttributeOverride) override, (AttributeOverrideContainer) container, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner columnOwner, BaseColumnTextRangeResolver textRangeResolver) {
			return new MapKeyAttributeOverrideColumnValidator(this.getPersistentAttribute(), (AttributeOverride) override, column, textRangeResolver, new CollectionTableTableDescriptionProvider());
		}
	}
}
