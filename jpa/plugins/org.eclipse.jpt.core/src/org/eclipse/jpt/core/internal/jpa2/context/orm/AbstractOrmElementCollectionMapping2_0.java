/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Fetchable;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.context.orm.OrmOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlAttributeOverrideColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.PersistentAttribute2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrderable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractOrmElementCollectionMapping2_0<T extends XmlElementCollection>
	extends AbstractOrmAttributeMapping<T> 
	implements OrmElementCollectionMapping2_0
{
	
	protected String specifiedTargetClass;
	protected String defaultTargetClass;
	protected PersistentType resolvedTargetType;
	protected Embeddable resolvedTargetEmbeddable;

	protected FetchType specifiedFetch;
	
	protected final OrmOrderable2_0 orderable;

	protected final OrmCollectionTable2_0 collectionTable;

	protected Type valueType;
	
	protected final OrmColumn valueColumn;
	
	protected OrmConverter valueConverter;
	
	protected final OrmConverter nullValueConverter;

	protected final OrmAttributeOverrideContainer valueAttributeOverrideContainer;
	
	protected final OrmAssociationOverrideContainer valueAssociationOverrideContainer;
	
	protected Type keyType;
	
	protected String specifiedMapKey;
	protected boolean noMapKey = false;
	protected boolean pkMapKey = false;
	protected boolean customMapKey = false;
	
	protected String specifiedMapKeyClass;
	protected String defaultMapKeyClass;
	protected PersistentType resolvedMapKeyType;
	protected Embeddable resolvedMapKeyEmbeddable;
	protected Entity resolvedMapKeyEntity;
	
	protected final OrmColumn mapKeyColumn;

	protected final OrmAttributeOverrideContainer mapKeyAttributeOverrideContainer;

	protected AbstractOrmElementCollectionMapping2_0(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.specifiedFetch = this.getResourceFetch();
		this.orderable = getXmlContextNodeFactory().buildOrmOrderable(this, buildOrderableOwner());
		this.specifiedTargetClass = getResourceTargetClass();
		this.defaultTargetClass = buildDefaultTargetClass();
		this.resolvedTargetType = this.resolveTargetType();
		this.resolvedTargetEmbeddable = resolveTargetEmbeddable();
		this.collectionTable = getXmlContextNodeFactory().buildOrmCollectionTable(this, getResourceCollectionTable());
		this.valueType = this.buildValueType();
		this.valueColumn = getXmlContextNodeFactory().buildOrmColumn(this, new ValueColumnOwner());
		this.nullValueConverter = this.getXmlContextNodeFactory().buildOrmNullConverter(this);
		this.valueConverter = this.buildConverter(this.getResourceConverterType());
		this.valueAssociationOverrideContainer = buildValueAssociationOverrideContainer();
		this.valueAttributeOverrideContainer = buildValueAttributeOverrideContainer();
		this.resolvedMapKeyType = this.resolveMapKeyType();
		this.resolvedMapKeyEmbeddable = this.resolveMapKeyEmbeddable();
		this.resolvedMapKeyEntity = this.resolveMapKeyEntity();
		this.initializeMapKey();
		this.defaultMapKeyClass = this.buildDefaultMapKeyClass();
		this.specifiedMapKeyClass = this.getResourceMapKeyClass();
		this.mapKeyColumn = getXmlContextNodeFactory().buildOrmColumn(this, new MapKeyColumnOwner());
		this.mapKeyAttributeOverrideContainer = this.buildMapKeyAttributeOverrideContainer();
	}
	
	@Override
	public void update() {
		super.update();
		this.setSpecifiedTargetClass_(this.getResourceTargetClass());
		this.setDefaultTargetClass(this.buildDefaultTargetClass());
		this.resolvedTargetType = this.resolveTargetType();
		this.setResolvedTargetEmbeddable(this.resolveTargetEmbeddable());
		this.setSpecifiedFetch_(this.getResourceFetch());
		this.orderable.update();
		this.collectionTable.update();
		this.setValueType(buildValueType()); 
		this.valueColumn.update(getResourceColumn());
		this.valueAttributeOverrideContainer.update();
		this.valueAssociationOverrideContainer.update();
		this.updateValueConverter();
		this.resolvedMapKeyType = this.resolveMapKeyType();//no need for change notification, use resolved target embeddable change notification instead?
		this.setResolvedMapKeyEmbeddable(this.resolveMapKeyEmbeddable());
		this.setResolvedMapKeyEntity(this.resolveMapKeyEntity());
		this.setKeyType(buildKeyType()); 
		this.updateMapKey();
		this.setDefaultMapKeyClass(this.buildDefaultMapKeyClass());
		this.setSpecifiedMapKeyClass_(this.getResourceMapKeyClass());
		this.mapKeyColumn.update(getResourceMapKeyColumn());
		this.mapKeyAttributeOverrideContainer.update();
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		this.valueAssociationOverrideContainer.postUpdate();
	}
	
	@Override
	protected OrmXml2_0ContextNodeFactory getXmlContextNodeFactory() {
		return super.getXmlContextNodeFactory();
	}
	
	public Entity getEntity() {
		return getTypeMapping().getKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY ? (Entity) getTypeMapping() : null;
	}


	//************* AttributeMapping implementation ***************

	public String getKey() {
		return MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}

	//************* OrmAttributeMapping implementation ***************
	public void initializeOn(OrmAttributeMapping newMapping) {
 		newMapping.initializeFromOrmAttributeMapping(this);
	}

	public int getXmlSequence() {
		return 75;
	}

	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getElementCollections().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getElementCollections().remove(this.resourceAttributeMapping);
	}

	
	// **************** target class ******************************************

	public char getTargetClassEnclosingTypeSeparator() {
		return '$';
	}
	
	public String getTargetClass() {
		return (this.specifiedTargetClass != null) ? this.specifiedTargetClass : this.defaultTargetClass;
	}

	public String getSpecifiedTargetClass() {
		return this.specifiedTargetClass;
	}

	public void setSpecifiedTargetClass(String targetClass) {
		String old = this.specifiedTargetClass;
		this.specifiedTargetClass = targetClass;
		this.resourceAttributeMapping.setTargetClass(targetClass);
		this.firePropertyChanged(SPECIFIED_TARGET_CLASS_PROPERTY, old, targetClass);
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

	public Embeddable getResolvedTargetEmbeddable() {
		return this.resolvedTargetEmbeddable;
	}

	protected void setResolvedTargetEmbeddable(Embeddable targetEmbeddable) {
		Embeddable old = this.resolvedTargetEmbeddable;
		this.resolvedTargetEmbeddable = targetEmbeddable;
		this.firePropertyChanged(RESOLVED_TARGET_EMBEDDABLE_PROPERTY, old, targetEmbeddable);
	}

	public PersistentType getResolvedTargetType() {
		return this.resolvedTargetType;
	}

	protected String getResourceTargetClass() {
		return this.resourceAttributeMapping.getTargetClass();
	}
	
	protected String buildDefaultTargetClass() {
		if (this.getJavaPersistentAttribute() != null) {
			return this.getJavaPersistentAttribute().getMultiReferenceTargetTypeName();
		}
		return null;
	}
	
	protected PersistentType resolveTargetType() {
		return this.resolvePersistentType(this.getTargetClass());
	}

	protected Embeddable resolveTargetEmbeddable() {
		if (this.resolvedTargetType == null) {
			return null;
		}
		TypeMapping typeMapping = this.resolvedTargetType.getMapping();
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
	}
	
	//************* Fetchable *************
	
	public FetchType getFetch() {
		return (this.getSpecifiedFetch() == null) ? this.getDefaultFetch() : this.getSpecifiedFetch();
	}

	public FetchType getDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}
	
	public void setSpecifiedFetch(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		this.resourceAttributeMapping.setFetch(FetchType.toOrmResourceModel(newSpecifiedFetch));
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	protected void setSpecifiedFetch_(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}
	
	protected FetchType getResourceFetch() {
		return FetchType.fromOrmResourceModel(this.resourceAttributeMapping.getFetch());
	}
	
	// **************** collection table ***********************************************

	public OrmCollectionTable2_0 getCollectionTable() {
		return this.collectionTable;
	}
	
	public XmlCollectionTable getResourceCollectionTable() {
		return this.resourceAttributeMapping.getCollectionTable();
	}


	// ************** value column ********************************************

	public OrmColumn getValueColumn() {
		return this.valueColumn;
	}
	
	protected XmlColumn getResourceColumn() {
		return this.resourceAttributeMapping.getColumn();
	}


	// *********** value converter *************
	
	public OrmConverter getConverter() {
		return this.valueConverter;
	}
	
	protected String getConverterType() {
		return this.valueConverter.getType();
	}
	
	public void setConverter(String converterType) {
		if (this.valuesAreEqual(getConverterType(), converterType)) {
			return;
		}
		OrmConverter oldConverter = this.valueConverter;
		OrmConverter newConverter = buildConverter(converterType);
		this.valueConverter = this.nullValueConverter;
		if (oldConverter != null) {
			oldConverter.removeFromResourceModel();
		}
		this.valueConverter = newConverter;
		if (newConverter != null) {
			newConverter.addToResourceModel();
		}
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void setConverter(OrmConverter newConverter) {
		OrmConverter oldConverter = this.valueConverter;
		this.valueConverter = newConverter;
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}	
	
	protected OrmConverter buildConverter(String converterType) {
		if (this.valuesAreEqual(converterType, Converter.NO_CONVERTER)) {
			return this.nullValueConverter;
		}
		if (this.valuesAreEqual(converterType, Converter.ENUMERATED_CONVERTER)) {
			return getXmlContextNodeFactory().buildOrmEnumeratedConverter(this, this.resourceAttributeMapping);
		}
		if (this.valuesAreEqual(converterType, Converter.TEMPORAL_CONVERTER)) {
			return getXmlContextNodeFactory().buildOrmTemporalConverter(this, this.resourceAttributeMapping);
		}
		if (this.valuesAreEqual(converterType, Converter.LOB_CONVERTER)) {
			return getXmlContextNodeFactory().buildOrmLobConverter(this, this.resourceAttributeMapping);
		}
		return null;
	}
	
	protected String getResourceConverterType() {
		if (this.resourceAttributeMapping.getEnumerated() != null) {
			return Converter.ENUMERATED_CONVERTER;
		}
		else if (this.resourceAttributeMapping.getTemporal() != null) {
			return Converter.TEMPORAL_CONVERTER;
		}
		else if (this.resourceAttributeMapping.isLob()) {
			return Converter.LOB_CONVERTER;
		}
		
		return Converter.NO_CONVERTER;
	}

	protected void updateValueConverter() {
		if (this.valuesAreEqual(getResourceConverterType(), getConverterType())) {
			getConverter().update();
		}
		else {
			setConverter(buildConverter(getResourceConverterType()));
		}
	}


	public Type getValueType() {
		return this.valueType;
	}

	protected void setValueType(Type newValueType) {
		Type old = this.valueType;
		this.valueType = newValueType;
		firePropertyChanged(VALUE_TYPE_PROPERTY, old, newValueType);
	}

	protected Type buildValueType() {
		if (getResolvedTargetEmbeddable() != null) {
			return Type.EMBEDDABLE_TYPE; 
		}
		else if (getTargetClass() == null) {
			return Type.NO_TYPE;
		}
		return Type.BASIC_TYPE;
	}
	
	public Type getKeyType() {
		return this.keyType;
	}

	protected void setKeyType(Type newKeyType) {
		Type old = this.keyType;
		this.keyType = newKeyType;
		firePropertyChanged(KEY_TYPE_PROPERTY, old, newKeyType);
	}

	protected Type buildKeyType() {
		if (getResolvedMapKeyEmbeddable() != null) {
			return Type.EMBEDDABLE_TYPE; 
		}
		if (getResolvedMapKeyEntity() != null) {
			return Type.ENTITY_TYPE; 
		}
		else if (getMapKeyClass() == null) {
			return Type.NO_TYPE;
		}
		return Type.BASIC_TYPE;
	}

	// ************** value column ********************************************

	public OrmColumn getMapKeyColumn() {
		return this.mapKeyColumn;
	}
	
	protected XmlColumn getResourceMapKeyColumn() {
		return this.resourceAttributeMapping.getMapKeyColumn();
	}
	
	// **************** ordering ***********************************************

	public OrmOrderable2_0 getOrderable() {
		return this.orderable;
	}

	protected Orderable2_0.Owner buildOrderableOwner() {
		return new Orderable2_0.Owner() {
			public String getTableName() {
				return getCollectionTable().getName();
			}
			public Table getDbTable(String tableName) {
				return getCollectionTable().getDbTable();
			}
		};
	}


	// **************** overrides ***********************************************

	public OrmAttributeOverrideContainer getValueAttributeOverrideContainer() {
		return this.valueAttributeOverrideContainer;
	}

	protected OrmAttributeOverrideContainer buildValueAttributeOverrideContainer() {
		return getXmlContextNodeFactory().buildOrmAttributeOverrideContainer(this, new ValueAttributeOverrideContainerOwner());
	}

	protected JavaAttributeOverride getJavaValueAttributeOverrideNamed(String attributeName) {
		if (getJavaElementCollectionMapping() != null) {
			return getJavaElementCollectionMapping().getValueAttributeOverrideContainer().getAttributeOverrideNamed(attributeName);
		}
		return null;
	}	

	public OrmAssociationOverrideContainer getValueAssociationOverrideContainer() {
		return this.valueAssociationOverrideContainer;
	}

	protected OrmAssociationOverrideContainer buildValueAssociationOverrideContainer() {
		return getXmlContextNodeFactory().buildOrmAssociationOverrideContainer(this, new AssociationOverrideContainerOwner());
	}

	protected JavaAssociationOverride getJavaValueAssociationOverrideNamed(String attributeName) {
		if (getJavaElementCollectionMapping() != null) {
			return getJavaElementCollectionMapping().getValueAssociationOverrideContainer().getAssociationOverrideNamed(attributeName);
		}
		return null;
	}

	protected JavaElementCollectionMapping2_0 getJavaElementCollectionMapping() {
		if (this.getJavaPersistentAttribute() == null) {
			return null;
		}
		AttributeMapping javaAttributeMapping = this.getJavaPersistentAttribute().getMapping();
		if (javaAttributeMapping.getKey() == MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY) {
			return ((JavaElementCollectionMapping2_0) javaAttributeMapping);
		}
		return null;
	}
	
	public OrmAttributeOverrideContainer getMapKeyAttributeOverrideContainer() {
		return this.mapKeyAttributeOverrideContainer;
	}

	protected OrmAttributeOverrideContainer buildMapKeyAttributeOverrideContainer() {
		return getXmlContextNodeFactory().buildOrmAttributeOverrideContainer(this, new MapKeyAttributeOverrideContainerOwner());
	}

	protected JavaAttributeOverride getJavaMapKeyAttributeOverrideNamed(String attributeName) {
		if (getJavaElementCollectionMapping() != null) {
			return getJavaElementCollectionMapping().getMapKeyAttributeOverrideContainer().getAttributeOverrideNamed(attributeName);
		}
		return null;
	}

	// **************** map key ************************************************
	
	public String getMapKey() {
		if (this.noMapKey) {
			return null;
		}
		if (this.pkMapKey) {
			return null;//the target is either embeddable or basic, so a key will have to be specified
		}
		if (this.customMapKey) {
			return this.specifiedMapKey;
		}
		throw new IllegalStateException("unknown map key"); //$NON-NLS-1$
	}
	
	public String getSpecifiedMapKey() {
		return this.specifiedMapKey;
	}

	public void setSpecifiedMapKey(String mapKey) {
		String old = this.specifiedMapKey;
		this.specifiedMapKey = mapKey;
		if (this.attributeValueHasChanged(old, mapKey)) {
			MapKey xmlMapKey = this.getXmlMapKey();
			if (mapKey == null) {
				if (xmlMapKey != null) {
					this.removeXmlMapKey();
				}
			} else {
				if (xmlMapKey == null) {
					xmlMapKey = this.addXmlMapKey();
				}
				xmlMapKey.setName(mapKey);
			}
		}
		this.firePropertyChanged(SPECIFIED_MAP_KEY_PROPERTY, old, mapKey);
	}
	
	protected void setSpecifiedMapKey_(String mapKey) {
		String old = this.specifiedMapKey;
		this.specifiedMapKey = mapKey;
		this.firePropertyChanged(SPECIFIED_MAP_KEY_PROPERTY, old, mapKey);
	}
	
	protected void initializeMapKey() {
		MapKey xmlMapKey = this.getXmlMapKey();
		if (xmlMapKey == null) { 
			this.noMapKey = true;
		} else {
			this.specifiedMapKey = xmlMapKey.getName();
			if (this.specifiedMapKey == null) {
				this.pkMapKey = true;
			} else {
				this.customMapKey = true;
			}
		}
	}
	
	protected void updateMapKey() {
		MapKey xmlMapKey = this.getXmlMapKey();
		if (xmlMapKey == null) {
			this.setSpecifiedMapKey_(null);
			this.setNoMapKey_(true);
			this.setPkMapKey_(false);
			this.setCustomMapKey_(false);
		} else {
			String mk = xmlMapKey.getName();
			this.setSpecifiedMapKey_(mk);
			this.setNoMapKey_(false);
			this.setPkMapKey_(mk == null);
			this.setCustomMapKey_(mk != null);
		}
	}
	
	protected MapKey getXmlMapKey() {
		return this.resourceAttributeMapping.getMapKey();
	}
	
	protected MapKey addXmlMapKey() {
		MapKey mapKey = OrmFactory.eINSTANCE.createMapKey();
		this.resourceAttributeMapping.setMapKey(mapKey);
		return mapKey;
	}

	protected void removeXmlMapKey() {
		this.resourceAttributeMapping.setMapKey(null);
	}
	
	public Iterator<String> candidateMapKeyNames() {
		return this.allTargetEmbeddableAttributeNames();
	}

	public Iterator<String> allTargetEmbeddableAttributeNames() {
		return new CompositeIterator<String>(
			new TransformationIterator<AttributeMapping, Iterator<String>>(this.allTargetEmbeddableAttributeMappings()) {
				@Override
				protected Iterator<String> transform(AttributeMapping mapping) {
					return mapping.allMappingNames();
				}
		});
	}
	
	protected Iterator<String> allEmbeddableAttributeMappingNames() {
		return this.embeddableOverrideableMappingNames(
			new Transformer<AttributeMapping, Iterator<String>>() {
				public Iterator<String> transform(AttributeMapping mapping) {
					return mapping.allMappingNames();
				}
			}
		);
	}

	@Override
	public Iterator<String> allMappingNames() {
		return new CompositeIterator<String>(this.getName(), this.allEmbeddableAttributeMappingNames());
	}

	protected Iterator<AttributeMapping> allTargetEmbeddableAttributeMappings() {
		return (this.resolvedTargetEmbeddable != null) ?
				this.resolvedTargetEmbeddable.allAttributeMappings() :
				EmptyIterator.<AttributeMapping> instance();
	}

	protected Iterator<AttributeMapping> embeddableAttributeMappings() {
		Embeddable targetEmbeddable = getResolvedTargetEmbeddable();
		if (targetEmbeddable != null && targetEmbeddable != getPersistentAttribute().getOwningTypeMapping()) {
			return targetEmbeddable.attributeMappings();
		}
		return EmptyIterator.instance();
	}

	@Override
	public Iterator<String> allOverrideableAttributeMappingNames() {
		return this.embeddableOverrideableAttributeMappingNames();
	}
	
	protected Iterator<String> embeddableOverrideableAttributeMappingNames() {
		return this.embeddableOverrideableMappingNames(
			new Transformer<AttributeMapping, Iterator<String>>() {
				public Iterator<String> transform(AttributeMapping mapping) {
					return mapping.allOverrideableAttributeMappingNames();
				}
			}
		);
	}
	
	@Override
	public Iterator<String> allOverrideableAssociationMappingNames() {
		return this.embeddableOverrideableAssociationMappingNames();
	}
	
	protected Iterator<String> embeddableOverrideableAssociationMappingNames() {
		return this.embeddableOverrideableMappingNames(
			new Transformer<AttributeMapping, Iterator<String>>() {
				public Iterator<String> transform(AttributeMapping mapping) {
					return mapping.allOverrideableAssociationMappingNames();
				}
			}
		);
	}
	
	protected Iterator<String> embeddableOverrideableMappingNames(Transformer<AttributeMapping, Iterator<String>> transformer) {
		return new TransformationIterator<String, String>(
			new CompositeIterator<String>(
				new TransformationIterator<AttributeMapping, Iterator<String>>(this.embeddableAttributeMappings(), transformer))) 
		{
			@Override
			protected String transform(String next) {
				return getName() + '.' + next;
			}
		};
	}


	@Override
	public Column resolveOverriddenColumn(String attributeName) {
		if (getName() == null) {
			return null;
		}
		int dotIndex = attributeName.indexOf('.');
		if (dotIndex != -1) {
			if (getName().equals(attributeName.substring(0, dotIndex))) {
				attributeName = attributeName.substring(dotIndex + 1);
				AttributeOverride override = getValueAttributeOverrideContainer().getAttributeOverrideNamed(attributeName);
				if (override != null && !override.isVirtual()) {
					return override.getColumn();
				}
				if (this.getResolvedTargetEmbeddable() == null) {
					return null;
				}
				return this.getResolvedTargetEmbeddable().resolveOverriddenColumn(attributeName);
			}
		}
		return null;
	}

	protected Iterator<String> embeddableAttributeMappingNames() {
		return new TransformationIterator<String, String>(
			new CompositeIterator<String>(
				new TransformationIterator<AttributeMapping, Iterator<String>>(this.embeddableAttributeMappings()) {
					@Override
					protected Iterator<String> transform(AttributeMapping mapping) {
						return mapping.allMappingNames();
					}
				}
			)
		) {
			@Override
			protected String transform(String next) {
				return getName() + '.' + next;
			}
		};
	}

	@Override
	public AttributeMapping resolveAttributeMapping(String attributeName) {
		if (getName() == null) {
			return null;
		}
		AttributeMapping resolvedMapping = super.resolveAttributeMapping(attributeName);
		if (resolvedMapping != null) {
			return resolvedMapping;
		}
		int dotIndex = attributeName.indexOf('.');
		if (dotIndex != -1) {
			if (getName().equals(attributeName.substring(0, dotIndex))) {
				for (AttributeMapping attributeMapping : CollectionTools.iterable(embeddableAttributeMappings())) {
					resolvedMapping = attributeMapping.resolveAttributeMapping(attributeName.substring(dotIndex + 1));
					if (resolvedMapping != null) {
						return resolvedMapping;
					}
				}
			}
		}
		return null;
	}

	@Override
	public RelationshipReference resolveRelationshipReference(String attributeName) {
		if (getName() == null) {
			return null;
		}
		int dotIndex = attributeName.indexOf('.');
		if (dotIndex != -1) {
			if (getName().equals(attributeName.substring(0, dotIndex))) {
				attributeName = attributeName.substring(dotIndex + 1);
				AssociationOverride override = getValueAssociationOverrideContainer().getAssociationOverrideNamed(attributeName);
				if (override != null && !override.isVirtual()) {
					return override.getRelationshipReference();
				}
				if (this.getResolvedTargetEmbeddable() == null) {
					return null;
				}
				return this.getResolvedTargetEmbeddable().resolveRelationshipReference(attributeName);
			}
		}
		return null;
	}
	
	// **************** no map key ***********************************************
		
	public boolean isNoMapKey() {
		return this.noMapKey;
	}

	public void setNoMapKey(boolean noMapKey) {
		boolean old = this.noMapKey;
		this.noMapKey = noMapKey;
		if (noMapKey) {
			if (this.getXmlMapKey() != null) {
				this.removeXmlMapKey();
			}
		}
		this.firePropertyChanged(NO_MAP_KEY_PROPERTY, old, noMapKey);			
	}
	
	protected void setNoMapKey_(boolean noMapKey) {
		boolean old = this.noMapKey;
		this.noMapKey = noMapKey;
		this.firePropertyChanged(NO_MAP_KEY_PROPERTY, old, noMapKey);			
	}
	
	
	// **************** pk map key ***********************************************
		
	public boolean isPkMapKey() {
		return this.pkMapKey;
	}
	
	public void setPkMapKey(boolean pkMapKey) {
		boolean old = this.pkMapKey;
		this.pkMapKey = pkMapKey;
		MapKey xmlMapKey = this.getXmlMapKey();
		if (pkMapKey) {
			if (xmlMapKey == null) {
				this.addXmlMapKey();
			} else {
				xmlMapKey.setName(null);
			}
		}
		this.firePropertyChanged(PK_MAP_KEY_PROPERTY, old, pkMapKey);	
	}
	
	protected void setPkMapKey_(boolean pkMapKey) {
		boolean old = this.pkMapKey;
		this.pkMapKey = pkMapKey;
		this.firePropertyChanged(PK_MAP_KEY_PROPERTY, old, pkMapKey);	
	}
	
	
	// **************** custom map key ***********************************************
		
	public boolean isCustomMapKey() {
		return this.customMapKey;
	}

	public void setCustomMapKey(boolean customMapKey) {
		boolean old = this.customMapKey;
		this.customMapKey = customMapKey;
		if (customMapKey) {
			this.setSpecifiedMapKey(""); //$NON-NLS-1$
		}
		this.firePropertyChanged(CUSTOM_MAP_KEY_PROPERTY, old, customMapKey);
	}
	
	protected void setCustomMapKey_(boolean customMapKey) {
		boolean old = this.customMapKey;
		this.customMapKey = customMapKey;
		this.firePropertyChanged(CUSTOM_MAP_KEY_PROPERTY, old, customMapKey);
	}

	// **************** map key class ******************************************

	public char getMapKeyClassEnclosingTypeSeparator() {
		return '$';
	}
	
	public String getMapKeyClass() {
		return (this.specifiedMapKeyClass != null) ? this.specifiedMapKeyClass : this.defaultMapKeyClass;
	}

	public String getSpecifiedMapKeyClass() {
		return this.specifiedMapKeyClass;
	}

	public void setSpecifiedMapKeyClass(String mapKeyClass) {
		String old = this.specifiedMapKeyClass;
		this.specifiedMapKeyClass = mapKeyClass;
		if (this.attributeValueHasChanged(old, mapKeyClass)) {
			XmlClassReference xmlMapKeyClass = this.getXmlMapKeyClass();
			if (mapKeyClass == null) {
				if (xmlMapKeyClass != null) {
					this.removeXmlMapKeyClass();
				}
			} else {
				if (xmlMapKeyClass == null) {
					xmlMapKeyClass = this.addXmlMapKeyClass();
				}
				xmlMapKeyClass.setClassName(mapKeyClass);
			}
		}
		this.firePropertyChanged(SPECIFIED_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected void setSpecifiedMapKeyClass_(String mapKeyClass) {
		String old = this.specifiedMapKeyClass;
		this.specifiedMapKeyClass = mapKeyClass;
		this.firePropertyChanged(SPECIFIED_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}
	
	protected XmlClassReference getXmlMapKeyClass() {
		return this.resourceAttributeMapping.getMapKeyClass();
	}
	
	protected XmlClassReference addXmlMapKeyClass() {
		XmlClassReference mapKeyClass = OrmFactory.eINSTANCE.createXmlClassReference();
		this.resourceAttributeMapping.setMapKeyClass(mapKeyClass);
		return mapKeyClass;
	}

	protected void removeXmlMapKeyClass() {
		this.resourceAttributeMapping.setMapKeyClass(null);
	}

	public String getDefaultMapKeyClass() {
		return this.defaultMapKeyClass;
	}

	protected void setDefaultMapKeyClass(String mapKeyClass) {
		String old = this.defaultMapKeyClass;
		this.defaultMapKeyClass = mapKeyClass;
		this.firePropertyChanged(DEFAULT_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected String getResourceMapKeyClass() {
		XmlClassReference mapKeyClass = this.resourceAttributeMapping.getMapKeyClass();
		return mapKeyClass == null ? null : mapKeyClass.getClassName();
	}
	
	protected String buildDefaultMapKeyClass() {
		if (this.getJavaPersistentAttribute() != null) {
			return this.getJavaPersistentAttribute().getMultiReferenceMapKeyTypeName();
		}
		return null;
	}
	

	public Embeddable getResolvedMapKeyEmbeddable() {
		return this.resolvedMapKeyEmbeddable;
	}

	protected void setResolvedMapKeyEmbeddable(Embeddable embeddable) {
		Embeddable old = this.resolvedMapKeyEmbeddable;
		this.resolvedMapKeyEmbeddable = embeddable;
		this.firePropertyChanged(RESOLVED_MAP_KEY_EMBEDDABLE_PROPERTY, old, embeddable);
	}

	public Entity getResolvedMapKeyEntity() {
		return this.resolvedMapKeyEntity;
	}

	protected void setResolvedMapKeyEntity(Entity entity) {
		Entity old = this.resolvedMapKeyEntity;
		this.resolvedMapKeyEntity = entity;
		this.firePropertyChanged(RESOLVED_MAP_KEY_ENTITY_PROPERTY, old, entity);
	}

	public PersistentType getResolvedMapKeyType() {
		return getResolvedMapKeyEmbeddable() == null ? null : getResolvedMapKeyEmbeddable().getPersistentType();
	}
	
	protected PersistentType resolveMapKeyType() {
		return this.resolvePersistentType(this.getMapKeyClass());
	}

	protected Embeddable resolveMapKeyEmbeddable() {
		if (this.resolvedMapKeyType == null) {
			return null;
		}
		TypeMapping typeMapping = this.resolvedMapKeyType.getMapping();
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
	}

	protected Entity resolveMapKeyEntity() {
		if (this.resolvedMapKeyType == null) {
			return null;
		}
		TypeMapping typeMapping = this.resolvedMapKeyType.getMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}


	// ********** metamodel **********  
	@Override
	protected String getMetamodelFieldTypeName() {
		return ((PersistentAttribute2_0) getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}
	
	@Override
	public String getMetamodelTypeName() {
		if (this.resolvedTargetType == null) {
			return MetamodelField.DEFAULT_TYPE_NAME;
		}
		String targetTypeName = this.resolvedTargetType.getName();
		return (targetTypeName != null) ? targetTypeName : MetamodelField.DEFAULT_TYPE_NAME;
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
	

	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateTargetClass(messages);
		this.validateMapKeyClass(messages);
		this.getOrderable().validate(messages, reporter);
		this.getCollectionTable().validate(messages, reporter);
		this.validateValue(messages, reporter);
		this.validateMapKey(messages, reporter);
	}

	public void validateValue(List<IMessage> messages, IReporter reporter) {
		//TODO should we handle validation when the type is embeddable, but a value column is specified, or things like that if that is invalid?
		if (getValueType() == Type.BASIC_TYPE) {
			this.getValueColumn().validate(messages, reporter);
			this.getConverter().validate(messages, reporter);
		}
		if (getValueType() == Type.EMBEDDABLE_TYPE) {
			this.getValueAttributeOverrideContainer().validate(messages, reporter);
			this.getValueAssociationOverrideContainer().validate(messages, reporter);
		}
	}

	public void validateMapKey(List<IMessage> messages, IReporter reporter) {
		if (getMapKey() != null) {
			//TODO validate that the map key refers to an existing attribute
			return;
		}
		if (getKeyType() == Type.BASIC_TYPE) {
			this.getMapKeyColumn().validate(messages, reporter);
			//validate map key converter
		}
		else if (getKeyType() == Type.ENTITY_TYPE) {
			//validate map key join columns
		}
		else if (getKeyType() == Type.EMBEDDABLE_TYPE) {
			this.getMapKeyAttributeOverrideContainer().validate(messages, reporter);
			//validate map key association overrides
		}
	}

	protected void validateTargetClass(List<IMessage> messages) {
		if (getTargetClass() == null) {
			if (getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED,
						new String[] {this.getName()}, 
						this, 
						this.getValidationTextRange()
					)
				);
			}
			else { 
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED,
						new String[0], 
						this, 
						this.getValidationTextRange()
					)
				);
			}
		}
		//TODO this does not give an error for unmapped, unlisted types that aren't basic - bug 310464
		if (this.resolvedTargetType != null) {
			if (getResolvedTargetEmbeddable() == null) {
				if (getPersistentAttribute().isVirtual()) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE,
							new String[] {this.getName(), this.getTargetClass()}, 
							this, 
							this.getValidationTextRange()
						)
					);
				}
				else {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE,
							new String[] {this.getTargetClass(), this.getName()}, 
							this, 
							this.getTargetClassTextRange()
						)
					);				
				}
			}
		}
	}

	protected void validateMapKeyClass(List<IMessage> messages) {
		if (getJavaPersistentAttribute() != null && !getJavaPersistentAttribute().getJpaContainer().isMap()) {
			return;
		}
		if (getMapKeyClass() == null) {
			if (getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_MAP_KEY_CLASS_NOT_DEFINED,
						new String[] {this.getName()}, 
						this, 
						this.getValidationTextRange()
					)
				);
			}
			else { 
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ELEMENT_COLLECTION_MAP_KEY_CLASS_NOT_DEFINED,
						new String[0], 
						this, 
						this.getValidationTextRange()
					)
				);
			}
		}
	}	

	protected TextRange getTargetClassTextRange() {
		return this.resourceAttributeMapping.getTargetClassTextRange();
	}

	protected abstract class ColumnOwner implements OrmColumn.Owner {		
		public String getDefaultTableName() {
			return getCollectionTable().getName();
		}
		
		public TypeMapping getTypeMapping() {
			return AbstractOrmElementCollectionMapping2_0.this.getTypeMapping();
		}
		
		public Table getDbTable(String tableName) {
			if (getCollectionTable().getName().equals(tableName)) {
				return AbstractOrmElementCollectionMapping2_0.this.getCollectionTable().getDbTable();
			}
			return null;
		}

		public java.util.Iterator<String> candidateTableNames() {
			return EmptyIterator.instance();
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name.  the table is always the collection table
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return !StringTools.stringsAreEqual(getDefaultTableName(), tableName);
		}
		
		public TextRange getValidationTextRange() {
			return AbstractOrmElementCollectionMapping2_0.this.getValidationTextRange();
		}

		public IMessage buildTableNotValidMessage(BaseColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualTableNotValidMessage(column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				getColumnTableNotValidMessage(),
				new String[] {
					column.getTable(),
					column.getName(),
					JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE}, 
				column,
				textRange
			);
		}

		protected IMessage buildVirtualTableNotValidMessage(BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualAttributeColumnTableNotValidMessage(),
				new String[] {
					getName(),
					column.getTable(),
					column.getName(),
					JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE}, 
				column,
				textRange
			);
		}

		public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualUnresolvedNameMessage(column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getColumnUnresolvedNameMessage(),
				new String[] {
					column.getName(),
					column.getDbTable().getName()}, 
				column,
				textRange
			);
		}

		protected IMessage buildVirtualUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getVirtualAttributeColumnUnresolvedNameMessage(),
				new String[] {getName(), column.getName(), column.getDbTable().getName()},
				column, 
				textRange
			);
		}		
		protected abstract String getColumnTableNotValidMessage();

		protected abstract String getVirtualAttributeColumnTableNotValidMessage();
		
		protected abstract String getColumnUnresolvedNameMessage();
		
		protected abstract String getVirtualAttributeColumnUnresolvedNameMessage();
	}

	protected class ValueColumnOwner extends ColumnOwner {

		public XmlColumn getResourceColumn() {
			return AbstractOrmElementCollectionMapping2_0.this.resourceAttributeMapping.getColumn();
		}

		public void addResourceColumn() {
			AbstractOrmElementCollectionMapping2_0.this.resourceAttributeMapping.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
		}

		public void removeResourceColumn() {
			AbstractOrmElementCollectionMapping2_0.this.resourceAttributeMapping.setColumn(null);
		}

		public String getDefaultColumnName() {
			return AbstractOrmElementCollectionMapping2_0.this.getName();
		}

		@Override
		public String getColumnTableNotValidMessage() {
			return JpaValidationMessages.COLUMN_TABLE_NOT_VALID;
		}

		@Override
		public String getVirtualAttributeColumnTableNotValidMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		public String getColumnUnresolvedNameMessage() {
			return JpaValidationMessages.COLUMN_UNRESOLVED_NAME;
		}
		
		@Override
		public String getVirtualAttributeColumnUnresolvedNameMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME;
		}
	}

	protected class MapKeyColumnOwner extends ColumnOwner {

		public XmlColumn getResourceColumn() {
			return AbstractOrmElementCollectionMapping2_0.this.resourceAttributeMapping.getMapKeyColumn();
		}

		public void addResourceColumn() {
			AbstractOrmElementCollectionMapping2_0.this.resourceAttributeMapping.setMapKeyColumn(OrmFactory.eINSTANCE.createXmlColumn());
		}

		public void removeResourceColumn() {
			AbstractOrmElementCollectionMapping2_0.this.resourceAttributeMapping.setMapKeyColumn(null);
		}

		public String getDefaultColumnName() {
			return AbstractOrmElementCollectionMapping2_0.this.getName() + "_KEY"; //$NON-NLS-1$
		}

		@Override
		protected String getColumnTableNotValidMessage() {
			return JpaValidationMessages.MAP_KEY_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected String getVirtualAttributeColumnTableNotValidMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		public String getColumnUnresolvedNameMessage() {
			return JpaValidationMessages.MAP_KEY_COLUMN_UNRESOLVED_NAME;
		}
		
		@Override
		public String getVirtualAttributeColumnUnresolvedNameMessage() {
			return JpaValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_COLUMN_UNRESOLVED_NAME;
		}

	}

	protected abstract class OverrideContainerOwner implements OrmOverrideContainer.Owner {
		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmElementCollectionMapping2_0.this.getTypeMapping();
		}
		
		public String getDefaultTableName() {
			return AbstractOrmElementCollectionMapping2_0.this.getCollectionTable().getName();
		}
		
		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return AbstractOrmElementCollectionMapping2_0.this.getCollectionTable().getDbTable();
		}

		public java.util.Iterator<String> candidateTableNames() {
			return EmptyIterator.instance();
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name.  the table is always the collection table
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return !StringTools.stringsAreEqual(getDefaultTableName(), tableName);
		}
	}

	protected class AssociationOverrideContainerOwner
		extends OverrideContainerOwner 
		implements OrmAssociationOverrideContainer.Owner
	{
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmElementCollectionMapping2_0.this.getResolvedTargetEmbeddable();
		}
		
		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = getOverridableTypeMapping();
			return (typeMapping == null) ? 
					EmptyIterator.<String>instance()
					: typeMapping.allOverridableAssociationNames();
		}
		
		public EList<XmlAssociationOverride> getResourceAssociationOverrides() {
			return AbstractOrmElementCollectionMapping2_0.this.resourceAttributeMapping.getAssociationOverrides();
		}
		
		public RelationshipReference resolveRelationshipReference(String associationOverrideName) {
			if (getPersistentAttribute().isVirtual() && !getTypeMapping().isMetadataComplete()) {
				JavaAssociationOverride javaAssociationOverride = getJavaValueAssociationOverrideNamed(associationOverrideName);
				if (javaAssociationOverride != null && !javaAssociationOverride.isVirtual()) {
					return javaAssociationOverride.getRelationshipReference();
				}
			}
			return MappingTools.resolveRelationshipReference(getOverridableTypeMapping(), associationOverrideName);
		}
		
		public IMessage buildColumnTableNotValidMessage(BaseOverride override, BaseColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.JOIN_COLUMN_TABLE_NOT_VALID,
					new String[] {
						column.getTable(),
						column.getName(),
						JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE}, 
					column, 
					textRange
				);
		}
		
		protected IMessage buildVirtualAttributeColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID,
				new String[] {
					AbstractOrmElementCollectionMapping2_0.this.getName(), 
					overrideName, 
					column.getTable(), 
					column.getName(),
					JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE},
				column,
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_NOT_VALID,
				new String[] { 
					overrideName, 
					column.getTable(), 
					column.getName(),
					JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE},
				column,
				textRange
			);
		}
		
		public IMessage buildColumnUnresolvedNameMessage(BaseOverride override, NamedColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualAttributeColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {
					AbstractOrmElementCollectionMapping2_0.this.getName(), 
					overrideName, 
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {
					overrideName, 
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
			);
		}
		
		public IMessage buildColumnUnresolvedReferencedColumnNameMessage(AssociationOverride override, BaseJoinColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeColumnUnresolvedReferencedColumnNameMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnUnresolvedReferencedColumnNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				new String[] {
					column.getReferencedColumnName(),
					column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualAttributeColumnUnresolvedReferencedColumnNameMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
					new String[] {
						AbstractOrmElementCollectionMapping2_0.this.getName(),
						overrideName,
						column.getReferencedColumnName(),
						column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideColumnUnresolvedReferencedColumnNameMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
					new String[] {
						overrideName,
						column.getReferencedColumnName(),
						column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}
		
		public IMessage buildUnspecifiedNameMultipleJoinColumnsMessage(AssociationOverride override, BaseJoinColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideUnspecifiedNameMultipleJoinColumnsMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[] {
					column.getReferencedColumnName(),
					column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualAttributeUnspecifiedNameMultipleJoinColumnsMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
					new String[] {AbstractOrmElementCollectionMapping2_0.this.getName(), overrideName},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideUnspecifiedNameMultipleJoinColumnsMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
					new String[] {overrideName},
				column, 
				textRange
			);
		}
		
		public IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(AssociationOverride override, BaseJoinColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[] {
					column.getReferencedColumnName(),
					column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualAttributeUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
				return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
						new String[] {AbstractOrmElementCollectionMapping2_0.this.getName(), overrideName},
					column, 
					textRange
				);
		}
		
		protected IMessage buildVirtualOverrideUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
					new String[] {overrideName},
				column, 
				textRange
			);
		}
		
		public TextRange getValidationTextRange() {
			return AbstractOrmElementCollectionMapping2_0.this.getValidationTextRange();
		}
	}
	
	
	//********** OrmAttributeOverrideContainer.Owner implementation *********	

	protected class ValueAttributeOverrideContainerOwner
		extends OverrideContainerOwner
		implements OrmAttributeOverrideContainer.Owner
	{
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmElementCollectionMapping2_0.this.getResolvedTargetEmbeddable();
		}
		
		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = getOverridableTypeMapping();
			return (typeMapping == null) ? 
					EmptyIterator.<String>instance()
					: typeMapping.allOverridableAttributeNames();
		}
		
		public EList<XmlAttributeOverride> getResourceAttributeOverrides() {
			return AbstractOrmElementCollectionMapping2_0.this.resourceAttributeMapping.getAttributeOverrides();
		}
		
		public Column resolveOverriddenColumn(String attributeOverrideName) {
			if (getPersistentAttribute().isVirtual() && !getTypeMapping().isMetadataComplete()) {
				JavaAttributeOverride javaAttributeOverride = getJavaValueAttributeOverrideNamed(attributeOverrideName);
				if (javaAttributeOverride != null && !javaAttributeOverride.isVirtual()) {
					return javaAttributeOverride.getColumn();
				}
			}
			return MappingTools.resolveOverridenColumn(getOverridableTypeMapping(), attributeOverrideName);
		}
		
		public XmlColumn buildVirtualXmlColumn(Column overridableColumn, String attributeName, boolean isMetadataComplete) {
			return new VirtualXmlAttributeOverrideColumn(overridableColumn);
		}
		
		public IMessage buildColumnUnresolvedNameMessage(BaseOverride override, NamedColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
				new String[] {
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualAttributeColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
				new String[] {
					AbstractOrmElementCollectionMapping2_0.this.getName(), 
					overrideName, 
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
				new String[] {
					overrideName, 
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
			);
		}
		
		public IMessage buildColumnTableNotValidMessage(BaseOverride override, BaseColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.COLUMN_TABLE_NOT_VALID,
					new String[] {
						column.getTable(),
						column.getName(),
						JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE}, 
					column, 
					textRange
				);
		}
		
		protected IMessage buildVirtualAttributeColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID,
				new String[] {
					AbstractOrmElementCollectionMapping2_0.this.getName(), 
					overrideName, 
					column.getTable(), 
					column.getName(),
					JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE},
				column,
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID,
				new String[] { 
					overrideName, 
					column.getTable(), 
					column.getName(),
					JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE},
				column,
				textRange
			);
		}
		
		public TextRange getValidationTextRange() {
			return AbstractOrmElementCollectionMapping2_0.this.getValidationTextRange();
		}
	}
	
	
	protected class MapKeyAttributeOverrideContainerOwner
		extends OverrideContainerOwner
		implements OrmAttributeOverrideContainer.Owner
	{
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmElementCollectionMapping2_0.this.getResolvedMapKeyEmbeddable();
		}
		
		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = getOverridableTypeMapping();
			return (typeMapping == null) ? 
					EmptyIterator.<String>instance()
					: typeMapping.allOverridableAttributeNames();
		}
		
		public EList<XmlAttributeOverride> getResourceAttributeOverrides() {
			return AbstractOrmElementCollectionMapping2_0.this.resourceAttributeMapping.getMapKeyAttributeOverrides();
		}
		
		public Column resolveOverriddenColumn(String attributeOverrideName) {
			if (getPersistentAttribute().isVirtual() && !getTypeMapping().isMetadataComplete()) {
				JavaAttributeOverride javaAttributeOverride = getJavaMapKeyAttributeOverrideNamed(attributeOverrideName);
				if (javaAttributeOverride != null && !javaAttributeOverride.isVirtual()) {
					return javaAttributeOverride.getColumn();
				}
			}
			return MappingTools.resolveOverridenColumn(getOverridableTypeMapping(), attributeOverrideName);
		}
		
		public XmlColumn buildVirtualXmlColumn(Column overridableColumn, String attributeName, boolean isMetadataComplete) {
			return new VirtualXmlAttributeOverrideColumn(overridableColumn);
		}
		
		public IMessage buildColumnUnresolvedNameMessage(BaseOverride override, NamedColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
				new String[] {
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualAttributeColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
				new String[] {
					AbstractOrmElementCollectionMapping2_0.this.getName(), 
					overrideName, 
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
				new String[] {
					overrideName, 
					column.getName(), 
					column.getDbTable().getName()},
				column, 
				textRange
			);
		}
		
		public IMessage buildColumnTableNotValidMessage(BaseOverride override, BaseColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.COLUMN_TABLE_NOT_VALID,
					new String[] {
						column.getTable(),
						column.getName(),
						JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE}, 
					column, 
					textRange
				);
		}
		
		protected IMessage buildVirtualAttributeColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID,
				new String[] {
					AbstractOrmElementCollectionMapping2_0.this.getName(), 
					overrideName, 
					column.getTable(), 
					column.getName(),
					JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE},
				column,
				textRange
			);
		}
		
		protected IMessage buildVirtualOverrideColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_NOT_VALID,
				new String[] { 
					overrideName, 
					column.getTable(), 
					column.getName(),
					JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE},
				column,
				textRange
			);
		}
		
		public TextRange getValidationTextRange() {
			return AbstractOrmElementCollectionMapping2_0.this.getValidationTextRange();
		}
	}
}
