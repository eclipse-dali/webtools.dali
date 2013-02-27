/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

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
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.ModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmMultiRelationshipMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EmbeddableOverrideDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.MapKeyAttributeOverrideColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.MapKeyAttributeOverrideValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.MapKeyColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.RelationshipStrategyTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.NullOrmConverter;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.MapKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.MultiRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ModifiablePersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlMultiRelationshipMapping;
import org.eclipse.jpt.jpa.core.resource.orm.MapKey;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.jpa.core.resource.orm.XmlColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> multi-relationship (1:m, m:m) mapping
 */
public abstract class AbstractOrmMultiRelationshipMapping<X extends AbstractXmlMultiRelationshipMapping>
	extends AbstractOrmRelationshipMapping<X>
	implements OrmMultiRelationshipMapping, OrmCollectionMapping2_0, MultiRelationshipMapping2_0
{
	protected final Orderable orderable;

	protected String specifiedMapKey;
	protected boolean noMapKey = false;
	protected boolean pkMapKey = false;
	protected boolean customMapKey = false;

	protected String specifiedMapKeyClass;
	protected String defaultMapKeyClass;
	protected String fullyQualifiedMapKeyClass;

	protected Type valueType;
	protected Type keyType;

	protected final OrmSpecifiedColumn mapKeyColumn;
	protected OrmConverter mapKeyConverter;  // map key converter - never null

	protected final OrmAttributeOverrideContainer mapKeyAttributeOverrideContainer;

	protected final ContextListContainer<OrmSpecifiedJoinColumn, XmlJoinColumn> specifiedMapKeyJoinColumnContainer;
	protected final JoinColumn.Owner mapKeyJoinColumnOwner;

	protected OrmSpecifiedJoinColumn defaultMapKeyJoinColumn;

	protected final OrmConverter nullConverter = new NullOrmConverter(this);

	protected static final OrmConverter.Adapter[] MAP_KEY_CONVERTER_ADAPTER_ARRAY = new OrmConverter.Adapter[] {
		OrmBaseEnumeratedConverter.MapKeyAdapter.instance(),
		OrmBaseTemporalConverter.MapKeyAdapter.instance()
	};
	protected static final Iterable<OrmConverter.Adapter> MAP_KEY_CONVERTER_ADAPTERS = IterableTools.iterable(MAP_KEY_CONVERTER_ADAPTER_ARRAY);

	protected AbstractOrmMultiRelationshipMapping(OrmModifiablePersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
		this.orderable = this.buildOrderable();

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
		this.orderable.synchronizeWithResourceModel();

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

		this.orderable.update();

		this.setDefaultMapKeyClass(this.buildDefaultMapKeyClass());
		this.setFullyQualifiedMapKeyClass(this.buildFullyQualifiedMapKeyClass());

		this.setValueType(this.buildValueType());
		this.setKeyType(this.buildKeyType());

		this.mapKeyColumn.update();
		this.mapKeyConverter.update();

		this.mapKeyAttributeOverrideContainer.update();
		this.updateModels(this.getSpecifiedMapKeyJoinColumns());
		this.updateDefaultMapKeyJoinColumn();
	}


	// ********** orderable **********

	public Orderable getOrderable() {
		return this.orderable;
	}

	protected Orderable buildOrderable() {
		return this.isOrmXml2_0Compatible() ?
				this.getContextModelFactory2_0().buildOrmOrderable(this, this.buildOrderableOwner()) :
				this.getContextModelFactory().buildOrmOrderable(this);
	}

	protected Orderable2_0.Owner buildOrderableOwner() {
		return new OrderableOwner();
	}

	protected class OrderableOwner
		implements Orderable2_0.Owner
	{
		public String getTableName() {
			return this.getRelationshipStrategy().getTableName();
		}
		public Table resolveDbTable(String tableName) {
			return this.getRelationshipStrategy().resolveDbTable(tableName);
		}
		protected RelationshipStrategy getRelationshipStrategy() {
			return AbstractOrmMultiRelationshipMapping.this.getRelationship().getStrategy();
		}
	}


	// ********** map key **********

	public String getMapKey() {
		if (this.noMapKey) {
			return null;
		}
		if (this.pkMapKey) {
			return this.getTargetEntityIdAttributeName();
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
			XmlClassReference xmlClassRef = this.xmlAttributeMapping.getMapKeyClass();
			if (mapKeyClass == null) {
				if (xmlClassRef != null) {
					this.xmlAttributeMapping.setMapKeyClass(null);
				}
			} else {
				if (xmlClassRef == null) {
					xmlClassRef = this.buildXmlMapKeyClassReference();
				}
				xmlClassRef.setClassName(mapKeyClass);
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
		JavaModifiablePersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
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
		if (this.getResolvedTargetEntity() != null) {
			return Type.ENTITY_TYPE;
		}
		if (this.getResolvedTargetEmbeddable() != null) {
			return Type.EMBEDDABLE_TYPE;
		}
		if (this.getTargetEntity() == null) {
			return Type.NO_TYPE;
		}
		return Type.BASIC_TYPE;
	}

	protected Embeddable getResolvedTargetEmbeddable() {
		TypeMapping typeMapping = this.getResolvedTargetTypeMapping();
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
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


	// ********** map key column **********

	public OrmSpecifiedColumn getMapKeyColumn() {
		return this.mapKeyColumn;
	}

	protected OrmSpecifiedColumn buildMapKeyColumn() {
		return this.getContextModelFactory().buildOrmColumn(this, this.buildMapKeyColumnOwner());
	}

	protected OrmSpecifiedColumn.Owner buildMapKeyColumnOwner() {
		return new MapKeyColumnOwner();
	}

	protected XmlColumn getXmlMapKeyColumn() {
		return this.xmlAttributeMapping.getMapKeyColumn();
	}

	protected XmlColumn buildXmlMapKeyColumn() {
		XmlColumn xmlColumn = OrmFactory.eINSTANCE.createXmlColumn();
		this.xmlAttributeMapping.setMapKeyColumn(xmlColumn);
		return xmlColumn;
	}

	protected void removeXmlMapKeyColumn() {
		this.xmlAttributeMapping.setMapKeyColumn(null);
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
				converterAdapter.buildNewConverter(this, this.getContextModelFactory()) :
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
		OrmXmlContextModelFactory factory = this.getContextModelFactory();
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
				this.setMapKeyConverter_(adapter.buildNewConverter(this, this.getContextModelFactory()));
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
		return this.getContextModelFactory().buildOrmAttributeOverrideContainer(this, this.buildMapKeyAttributeOverrideContainerOwner());
	}

	protected OrmAttributeOverrideContainer.Owner buildMapKeyAttributeOverrideContainerOwner() {
		return new MapKeyAttributeOverrideContainerOwner();
	}

	protected JavaSpecifiedAttributeOverride getSpecifiedJavaMapKeyAttributeOverrideNamed(String attributeName) {
		JavaCollectionMapping2_0 javaCollectionMapping = this.getJavaCollectionMapping();
		return (javaCollectionMapping == null) ? null :
			javaCollectionMapping.getMapKeyAttributeOverrideContainer().getSpecifiedOverrideNamed(attributeName);
	}

	protected JavaCollectionMapping2_0 getJavaCollectionMapping() {
		AttributeMapping javaAttributeMapping = this.getJavaAttributeMapping();
		return (javaAttributeMapping.getKey() == this.getKey()) ?
				(JavaCollectionMapping2_0) javaAttributeMapping :
				null;
	}

	protected AttributeMapping getJavaAttributeMapping() {
		JavaModifiablePersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		return (javaAttribute == null) ? null : javaAttribute.getMapping();
	}
	// ********** map key join columns **********

	public ListIterable<OrmSpecifiedJoinColumn> getMapKeyJoinColumns() {
		return this.hasSpecifiedMapKeyJoinColumns() ? this.getSpecifiedMapKeyJoinColumns() : this.getDefaultMapKeyJoinColumns();
	}

	public int getMapKeyJoinColumnsSize() {
		return this.hasSpecifiedMapKeyJoinColumns() ? this.getSpecifiedMapKeyJoinColumnsSize() : this.getDefaultMapKeyJoinColumnsSize();
	}


	// ********** specified map key join columns **********

	public ListIterable<OrmSpecifiedJoinColumn> getSpecifiedMapKeyJoinColumns() {
		return this.specifiedMapKeyJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedMapKeyJoinColumnsSize() {
		return this.specifiedMapKeyJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedMapKeyJoinColumns() {
		return this.getSpecifiedMapKeyJoinColumnsSize() != 0;
	}

	public OrmSpecifiedJoinColumn getSpecifiedMapKeyJoinColumn(int index) {
		return this.specifiedMapKeyJoinColumnContainer.getContextElement(index);
	}

	public OrmSpecifiedJoinColumn addSpecifiedMapKeyJoinColumn() {
		return this.addSpecifiedMapKeyJoinColumn(this.getSpecifiedMapKeyJoinColumnsSize());
	}

	public OrmSpecifiedJoinColumn addSpecifiedMapKeyJoinColumn(int index) {
		XmlJoinColumn xmlJoinColumn = this.buildXmlJoinColumn();
		OrmSpecifiedJoinColumn joinColumn = this.specifiedMapKeyJoinColumnContainer.addContextElement(index, xmlJoinColumn);
		this.getXmlAttributeMapping().getMapKeyJoinColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlJoinColumn buildXmlJoinColumn() {
		return OrmFactory.eINSTANCE.createXmlJoinColumn();
	}

	public void removeSpecifiedMapKeyJoinColumn(SpecifiedJoinColumn joinColumn) {
		this.removeSpecifiedMapKeyJoinColumn(this.specifiedMapKeyJoinColumnContainer.indexOfContextElement((OrmSpecifiedJoinColumn) joinColumn));
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

	protected ContextListContainer<OrmSpecifiedJoinColumn, XmlJoinColumn> buildSpecifiedMapKeyJoinColumnContainer() {
		SpecifiedMapKeyJoinColumnContainer container = new SpecifiedMapKeyJoinColumnContainer();
		container.initialize();
		return container;
	}

	/**
	 * specified join column container
	 */
	protected class SpecifiedMapKeyJoinColumnContainer
		extends ContextListContainer<OrmSpecifiedJoinColumn, XmlJoinColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_MAP_KEY_JOIN_COLUMNS_LIST;
		}
		@Override
		protected OrmSpecifiedJoinColumn buildContextElement(XmlJoinColumn resourceElement) {
			return AbstractOrmMultiRelationshipMapping.this.buildMapKeyJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<XmlJoinColumn> getResourceElements() {
			return AbstractOrmMultiRelationshipMapping.this.getXmlMapKeyJoinColumns();
		}
		@Override
		protected XmlJoinColumn getResourceElement(OrmSpecifiedJoinColumn contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected OrmSpecifiedJoinColumn buildMapKeyJoinColumn(XmlJoinColumn xmlJoinColumn) {
		return this.getContextModelFactory().buildOrmJoinColumn(this, this.mapKeyJoinColumnOwner, xmlJoinColumn);
	}

	protected JoinColumn.Owner buildMapKeyJoinColumnOwner() {
		return new MapKeyJoinColumnOwner();
	}


	// ********** default map key join column **********

	public OrmSpecifiedJoinColumn getDefaultMapKeyJoinColumn() {
		return this.defaultMapKeyJoinColumn;
	}

	protected void setDefaultMapKeyJoinColumn(OrmSpecifiedJoinColumn mapKeyJoinColumn) {
		OrmSpecifiedJoinColumn old = this.defaultMapKeyJoinColumn;
		this.defaultMapKeyJoinColumn = mapKeyJoinColumn;
		this.firePropertyChanged(DEFAULT_MAP_KEY_JOIN_COLUMN_PROPERTY, old, mapKeyJoinColumn);
	}

	protected ListIterable<OrmSpecifiedJoinColumn> getDefaultMapKeyJoinColumns() {
		return (this.defaultMapKeyJoinColumn != null) ?
				new SingleElementListIterable<OrmSpecifiedJoinColumn>(this.defaultMapKeyJoinColumn) :
				EmptyListIterable.<OrmSpecifiedJoinColumn>instance();
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

	/**
	 * pre-condition: the mapping's Java persistent attribute is not
	 * <code>null</code>.
	 */
	@Override
	protected String getJavaTargetType() {
		return this.getJavaPersistentAttribute().getMultiReferenceTargetTypeName();
	}

	@Override
	protected FetchType buildDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}

	public Iterable<String> getCandidateMapKeyNames() {
		return this.getTargetEntityNonTransientAttributeNames();
	}

	@SuppressWarnings("unchecked")
	protected Iterable<String> getCandidateMapKeyClassNames() {
		return IterableTools.concatenate(
				MappingTools.getSortedJavaClassNames(getJavaProject()),
				MappingTools.getPrimaryBasicTypeNames()
				);
	}

	// ********** metamodel **********

	@Override
	protected String getMetamodelFieldTypeName() {
		return ((ModifiablePersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		this.addMetamodelFieldMapKeyTypeArgumentNameTo(typeArgumentNames);
		super.addMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
	}

	protected void addMetamodelFieldMapKeyTypeArgumentNameTo(ArrayList<String> typeArgumentNames) {
		String keyTypeName = ((ModifiablePersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldMapKeyTypeName();
		if (keyTypeName != null) {
			typeArgumentNames.add(keyTypeName);
		}
	}

	public String getMetamodelFieldMapKeyTypeName() {
		return MappingTools.getMetamodelFieldMapKeyTypeName(this);
	}


	//************* refactoring *************

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
				super.createRenameTypeEdits(originalType, newName),
				this.createMapKeyClassRenameTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createMapKeyClassRenameTypeEdits(IType originalType, String newName) {
		if (this.specifiedMapKeyClass != null) {
			PersistentType mapKeyType = this.getResolvedMapKeyType();
			if ((mapKeyType != null) && mapKeyType.isFor(originalType.getFullyQualifiedName('.'))) {
				return new SingleElementIterable<ReplaceEdit>(this.createMapKeyClassRenameTypeEdit(originalType, newName));
			}
		}
		return EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createMapKeyClassRenameTypeEdit(IType originalType, String newName) {
		return this.xmlAttributeMapping.createRenameMapKeyClassEdit(originalType, newName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
				super.createMoveTypeEdits(originalType, newPackage),
				this.createMapKeyClassMoveTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createMapKeyClassMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (this.specifiedMapKeyClass != null) {
			PersistentType mapKeyType = this.getResolvedMapKeyType();
			if ((mapKeyType != null) && mapKeyType.isFor(originalType.getFullyQualifiedName('.'))) {
				return new SingleElementIterable<ReplaceEdit>(this.createMapKeyClassRenamePackageEdit(newPackage.getElementName()));
			}
		}
		return EmptyIterable.<ReplaceEdit>instance();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
				super.createRenamePackageEdits(originalPackage, newName),
				this.createMapKeyClassRenamePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createMapKeyClassRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		if (this.specifiedMapKeyClass != null) {
			PersistentType mapKeyType = this.getResolvedMapKeyType();
			if ((mapKeyType != null) && mapKeyType.isIn(originalPackage)) {
				return new SingleElementIterable<ReplaceEdit>(this.createMapKeyClassRenamePackageEdit(newName));
			}
		}
		return EmptyIterable.<ReplaceEdit>instance();
	}

	protected ReplaceEdit createMapKeyClassRenamePackageEdit(String newName) {
		return this.xmlAttributeMapping.createRenameMapKeyClassPackageEdit(newName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateAttributeType(messages, reporter);
		this.orderable.validate(messages, reporter);
		this.validateMapKeyClass(messages);
		this.validateMapKey(messages, reporter);
	}

	protected void validateAttributeType(List<IMessage> messages, IReporter reporter) {
		JavaModifiablePersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
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

	public void validateMapKey(List<IMessage> messages, IReporter reporter) {
		if (this.getMapKey() != null) {
			//TODO validate that the map key refers to an existing attribute
			return;
		}
		if (this.keyType == Type.BASIC_TYPE) {
			this.mapKeyColumn.validate(messages, reporter);
			this.mapKeyConverter.validate(messages, reporter);
		}
		else if (this.keyType == Type.ENTITY_TYPE) {
			for (OrmSpecifiedJoinColumn joinColumn : this.getMapKeyJoinColumns()) {
				joinColumn.validate(messages, reporter);
			}
		}
		else if (this.keyType == Type.EMBEDDABLE_TYPE) {
			this.mapKeyAttributeOverrideContainer.validate(messages, reporter);
			//validate map key association overrides
		}
	}

	protected void validateMapKeyClass(List<IMessage> messages) {
		if (this.isJpa2_0Compatible()) {
			JavaModifiablePersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
			if ((javaAttribute != null) && javaAttribute.getJpaContainerDefinition().isMap()) {
				this.validateMapKeyClass_(messages);
			}
		}
	}

	protected void validateMapKeyClass_(List<IMessage> messages) {
		if (StringTools.isBlank(getMapKeyClass())) {
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

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.orderable.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.mapKeyNameTouches(pos)) {
			return this.getCandidateMapKeyNames();
		}
		if (this.mapKeyClassTouches(pos)) {
			return this.getCandidateMapKeyClassNames();
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
		for (OrmSpecifiedJoinColumn joinColumn : this.getMapKeyJoinColumns()) {
			result = joinColumn.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	protected boolean mapKeyNameTouches(int pos) {
		return this.getXmlMapKey() == null? false : this.getXmlMapKey().mapKeyNameTouches(pos);
	}

	protected boolean mapKeyClassTouches(int pos) {
		return this.xmlAttributeMapping.getMapKeyClass() == null ? false : 
					this.xmlAttributeMapping.getMapKeyClass().classNameTouches(pos);
	}

	// ********** abstract owner **********

	/**
	 * some common behavior
	 */
	protected class AbstractOwner
	{
		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmMultiRelationshipMapping.this.getTypeMapping();
		}

		public String getDefaultTableName() {
			return this.getRelationshipStrategy().getTableName();
		}

		public Table resolveDbTable(String tableName) {
			return this.getRelationshipStrategy().resolveDbTable(tableName);
		}

		public Iterable<String> getCandidateTableNames() {
			return EmptyIterable.instance();
		}

		public TextRange getValidationTextRange() {
			return AbstractOrmMultiRelationshipMapping.this.getValidationTextRange();
		}

		protected RelationshipStrategy getRelationshipStrategy() {
			return AbstractOrmMultiRelationshipMapping.this.getRelationship().getStrategy();
		}

		protected OrmModifiablePersistentAttribute getPersistentAttribute() {
			return AbstractOrmMultiRelationshipMapping.this.getPersistentAttribute();
		}
	}


	// ********** map key column owner **********

	protected class MapKeyColumnOwner
		extends AbstractOwner
		implements OrmSpecifiedColumn.Owner
	{
		public String getDefaultColumnName(NamedColumn column) {
			return AbstractOrmMultiRelationshipMapping.this.getName() + "_KEY"; //$NON-NLS-1$
		}

		public boolean tableNameIsInvalid(String tableName) {
			return this.getRelationshipStrategy().tableNameIsInvalid(tableName);
		}

		public XmlColumn getXmlColumn() {
			return AbstractOrmMultiRelationshipMapping.this.getXmlMapKeyColumn();
		}

		public XmlColumn buildXmlColumn() {
			return AbstractOrmMultiRelationshipMapping.this.buildXmlMapKeyColumn();
		}

		public void removeXmlColumn() {
			AbstractOrmMultiRelationshipMapping.this.removeXmlMapKeyColumn();
		}

		public JptValidator buildColumnValidator(NamedColumn column) {
			return new MapKeyColumnValidator(this.getPersistentAttribute(), (ReadOnlyBaseColumn) column, new RelationshipStrategyTableDescriptionProvider(this.getRelationshipStrategy()));
		}
	}


	// ********** map key attribute override container owner **********

	protected class MapKeyAttributeOverrideContainerOwner
		extends AbstractOwner
		implements OrmAttributeOverrideContainer.Owner
	{
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmMultiRelationshipMapping.this.getResolvedMapKeyEmbeddable();
		}

		public Iterable<String> getAllOverridableNames() {
			TypeMapping overriddenTypeMapping = this.getOverridableTypeMapping();
			return (overriddenTypeMapping != null) ? overriddenTypeMapping.getAllOverridableAttributeNames() : EmptyIterable.<String>instance();
		}

		protected JavaSpecifiedAttributeOverride getSpecifiedJavaAttributeOverrideNamed(String attributeName) {
			return AbstractOrmMultiRelationshipMapping.this.getSpecifiedJavaMapKeyAttributeOverrideNamed(attributeName);
		}

		public Iterable<String> getJavaOverrideNames() {
			return null;
		}

		public EList<XmlAttributeOverride> getXmlOverrides() {
			return AbstractOrmMultiRelationshipMapping.this.xmlAttributeMapping.getMapKeyAttributeOverrides();
		}

		public SpecifiedColumn resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name.  the table is always the collection table
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return ObjectTools.notEquals(this.getDefaultTableName(), tableName);
		}

		public JptValidator buildOverrideValidator(Override_ override, OverrideContainer container) {
			return new MapKeyAttributeOverrideValidator(this.getPersistentAttribute(), (AttributeOverride) override, (AttributeOverrideContainer) container, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(Override_ override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner owner) {
			return new MapKeyAttributeOverrideColumnValidator(this.getPersistentAttribute(), (AttributeOverride) override, column, new RelationshipStrategyTableDescriptionProvider(this.getRelationshipStrategy()));
		}
	}


	// ********** map key join column owner **********

	protected class MapKeyJoinColumnOwner
		implements JoinColumn.Owner
	{
		protected MapKeyJoinColumnOwner() {
			super();
		}

		public String getDefaultTableName() {
			return AbstractOrmMultiRelationshipMapping.this.getRelationship().getStrategy().getTableName();
		}

		public String getDefaultColumnName(NamedColumn column) {
			return AbstractOrmMultiRelationshipMapping.this.getName() + "_KEY"; //$NON-NLS-1$
		}

		public String getAttributeName() {
			return AbstractOrmMultiRelationshipMapping.this.getName();
		}

		protected ModifiablePersistentAttribute getPersistentAttribute() {
			return AbstractOrmMultiRelationshipMapping.this.getPersistentAttribute();
		}

		public Entity getRelationshipTarget() {
			return AbstractOrmMultiRelationshipMapping.this.getResolvedMapKeyEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractOrmMultiRelationshipMapping.this.getRelationship().getStrategy().tableNameIsInvalid(tableName);
		}

		/**
		 * the map key join column can be on a secondary table
		 */
		public Iterable<String> getCandidateTableNames() {
			return AbstractOrmMultiRelationshipMapping.this.getTypeMapping().getAllAssociatedTableNames();
		}

		public Table resolveDbTable(String tableName) {
			return AbstractOrmMultiRelationshipMapping.this.getRelationship().getStrategy().resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			Entity entity = AbstractOrmMultiRelationshipMapping.this.getResolvedMapKeyEntity();
			return entity != null ? entity.getPrimaryDbTable() : null;
		}

		public int getJoinColumnsSize() {
			return AbstractOrmMultiRelationshipMapping.this.getMapKeyJoinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return AbstractOrmMultiRelationshipMapping.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(NamedColumn column) {
			return new MapKeyJoinColumnValidator(
				this.getPersistentAttribute(),
				(JoinColumn) column,
				this,
				new RelationshipStrategyTableDescriptionProvider(getRelationship().getStrategy()));
		}
	}
}
