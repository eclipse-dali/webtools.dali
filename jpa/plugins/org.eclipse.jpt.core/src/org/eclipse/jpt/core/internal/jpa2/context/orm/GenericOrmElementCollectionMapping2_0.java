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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.BaseJoinColumn;
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
import org.eclipse.jpt.core.context.orm.OrmOrderable;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlAttributeOverrideColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.PersistentAttribute2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.core.resource.orm.XmlMapKeyClass;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericOrmElementCollectionMapping2_0
	extends AbstractOrmAttributeMapping<XmlElementCollection> 
	implements OrmElementCollectionMapping2_0
{
	
	protected String specifiedTargetClass;
	protected String defaultTargetClass;
	protected PersistentType resolvedTargetType;
	protected Embeddable resolvedTargetEmbeddable;

	protected FetchType specifiedFetch;
	
	protected final OrmOrderable orderable;

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
	
	public GenericOrmElementCollectionMapping2_0(OrmPersistentAttribute parent, XmlElementCollection resourceMapping) {
		super(parent, resourceMapping);
		this.specifiedFetch = this.getResourceFetch();
		this.orderable = getXmlContextNodeFactory().buildOrmOrderable(this, buildOrderableOwner());
		this.specifiedTargetClass = getResourceTargetClass();
		this.defaultTargetClass = buildDefaultTargetClass();
		this.resolvedTargetType = this.buildResolvedTargetType();
		this.resolvedTargetEmbeddable = buildResolvedTargetEmbeddable();
		this.collectionTable = getXmlContextNodeFactory().buildOrmCollectionTable(this, getResourceCollectionTable());
		this.valueType = this.buildValueType();
		this.valueColumn = getXmlContextNodeFactory().buildOrmColumn(this, new ValueColumnOwner());
		this.nullValueConverter = this.getXmlContextNodeFactory().buildOrmNullConverter(this);
		this.valueConverter = this.buildConverter(this.getResourceConverterType());
		this.valueAssociationOverrideContainer = buildValueAssociationOverrideContainer();
		this.valueAttributeOverrideContainer = buildValueAttributeOverrideContainer();
		this.initializeMapKey();
		this.defaultMapKeyClass = this.buildDefaultMapKeyClass();
		this.specifiedMapKeyClass = this.getResourceMapKeyClass();
	}
	
	@Override
	public void update() {
		super.update();
		this.setSpecifiedTargetClass_(this.getResourceTargetClass());
		this.setDefaultTargetClass(this.buildDefaultTargetClass());
		this.resolvedTargetType = this.buildResolvedTargetType();
		this.setResolvedTargetEmbeddable(this.buildResolvedTargetEmbeddable());
		this.setSpecifiedFetch_(this.getResourceFetch());
		this.orderable.update();
		this.collectionTable.update();
		this.setValueType(buildValueType()); 
		this.valueColumn.update(getResourceColumn());
		this.valueAttributeOverrideContainer.update();
		this.valueAssociationOverrideContainer.update();
		this.updateValueConverter();
		this.setKeyType(buildKeyType()); 
		this.updateMapKey();
		this.setDefaultMapKeyClass(this.buildDefaultMapKeyClass());
		this.setSpecifiedMapKeyClass_(this.getResourceMapKeyClass());
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		this.valueAssociationOverrideContainer.postUpdate();
	}
	
	@Override
	protected OrmXml2_0ContextNodeFactory getXmlContextNodeFactory() {
		return (OrmXml2_0ContextNodeFactory) super.getXmlContextNodeFactory();
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
		return getResolvedTargetEmbeddable() == null ? null : getResolvedTargetEmbeddable().getPersistentType();
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
	
	protected PersistentType buildResolvedTargetType() {
		String targetClassName = this.getTargetClass();
		if (targetClassName == null) {
			return null;
		}

		// first try to resolve using only the locally specified name...
		PersistentType targetPersistentType = this.getPersistentType(targetClassName);
		if (targetPersistentType != null) {
			return targetPersistentType;
		}

		// ...then try to resolve by prepending the global package name
		String defaultPackageName = this.getDefaultPackageName();
		if (defaultPackageName == null) {
			return null;
		}
		return this.getPersistentType(defaultPackageName + '.' + targetClassName);
	}

	protected Embeddable buildResolvedTargetEmbeddable() {
		if (this.resolvedTargetType == null) {
			return null;
		}
		TypeMapping typeMapping = this.resolvedTargetType.getMapping();
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
	}

	protected String getDefaultPackageName() {
		return this.getPersistentAttribute().getOwningPersistentType().getDefaultPackage();
	}

	protected PersistentType getPersistentType(String typeName) {
		return this.getPersistenceUnit().getPersistentType(typeName);
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
		return null;//TODO
	}
	
	// **************** ordering ***********************************************

	public OrmOrderable getOrderable() {
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
		return getXmlContextNodeFactory().buildOrmAttributeOverrideContainer(this, new AttributeOverrideContainerOwner(), this.getResourceAttributeMapping());
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
		return getXmlContextNodeFactory().buildOrmAssociationOverrideContainer(this, new AssociationOverrideContainerOwner(), this.getResourceAttributeMapping());
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

	protected Iterator<AttributeMapping> allTargetEmbeddableAttributeMappings() {
		return (this.resolvedTargetEmbeddable != null) ?
				this.resolvedTargetEmbeddable.allAttributeMappings() :
				EmptyIterator.<AttributeMapping> instance();
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
			XmlMapKeyClass xmlMapKeyClass = this.getXmlMapKeyClass();
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
	
	protected XmlMapKeyClass getXmlMapKeyClass() {
		return this.resourceAttributeMapping.getMapKeyClass();
	}
	
	protected XmlMapKeyClass addXmlMapKeyClass() {
		XmlMapKeyClass mapKeyClass = OrmFactory.eINSTANCE.createXmlMapKeyClass();
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
		XmlMapKeyClass mapKeyClass = this.resourceAttributeMapping.getMapKeyClass();
		return mapKeyClass == null ? null : mapKeyClass.getClassName();
	}
	
	protected String buildDefaultMapKeyClass() {
		if (this.getJavaPersistentAttribute() != null) {
			return this.getJavaPersistentAttribute().getMultiReferenceMapKeyTypeName();
		}
		return null;
	}
	

	// ********** metamodel **********  
	@Override
	protected String getMetamodelFieldTypeName() {
		return ((PersistentAttribute2_0) getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}
	
	@Override
	public String getMetamodelTypeName() {
		String targetClass = this.getTargetClass();
		return (targetClass != null) ? targetClass : MetamodelField.DEFAULT_TYPE_NAME;
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
		this.getOrderable().validate(messages, reporter);
		this.getCollectionTable().validate(messages, reporter);
		if (getValueType() == Type.BASIC_TYPE) {
			this.getValueColumn().validate(messages, reporter);
			this.getConverter().validate(messages, reporter);
		}
		if (getValueType() == Type.EMBEDDABLE_TYPE) {
			this.getValueAttributeOverrideContainer().validate(messages, reporter);
			this.getValueAssociationOverrideContainer().validate(messages, reporter);
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
						new String[] {this.getName()}, 
						this, 
						this.getValidationTextRange()
					)
				);
			}
		}
		//TODO this does not give an error for unmapped, unlisted types that aren't basic
		if (this.resolvedTargetType != null) {
			if (getResolvedTargetEmbeddable() == null) {
				if (getPersistentAttribute().isVirtual()) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE,
							new String[] {this.getName(), this.getTargetClass()}, 
							this, 
							this.getTargetClassTextRange()
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

	protected TextRange getTargetClassTextRange() {
		return this.resourceAttributeMapping.getTargetClassTextRange();
	}

	class ValueColumnOwner implements OrmColumn.Owner {
		
		public XmlColumn getResourceColumn() {
			return GenericOrmElementCollectionMapping2_0.this.resourceAttributeMapping.getColumn();
		}
		
		public void addResourceColumn() {
			GenericOrmElementCollectionMapping2_0.this.resourceAttributeMapping.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
		}
		
		public void removeResourceColumn() {
			GenericOrmElementCollectionMapping2_0.this.resourceAttributeMapping.setColumn(null);
		}
		
		public String getDefaultTableName() {
			return getCollectionTable().getName();
		}
		
		public TypeMapping getTypeMapping() {
			return GenericOrmElementCollectionMapping2_0.this.getTypeMapping();
		}
		
		public String getDefaultColumnName() {
			return GenericOrmElementCollectionMapping2_0.this.getName();
		}
		
		public Table getDbTable(String tableName) {
			if (getCollectionTable().getName().equals(tableName)) {
				return GenericOrmElementCollectionMapping2_0.this.getCollectionTable().getDbTable();
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
			return GenericOrmElementCollectionMapping2_0.this.getValidationTextRange();
		}

		public IMessage buildTableNotValidMessage(BaseColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualTableNotValidMessage(column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.ELEMENT_COLLECTION_VALUE_COLUMN_TABLE_DOES_NOT_MATCH_COLLECTION_TABLE,
				new String[] {column.getTable(), column.getName()}, 
				column, 
				textRange
			);
		}

		protected IMessage buildVirtualTableNotValidMessage(BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_VALUE_COLUMN_TABLE_DOES_NOT_MATCH_COLLECTION_TABLE,
				new String[] {getName(), column.getTable(), column.getName()},
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
				JpaValidationMessages.ELEMENT_COLLECTION_VALUE_COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column,
				textRange
			);
		}

		protected IMessage buildVirtualUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_VALUE_COLUMN_UNRESOLVED_NAME,
				new String[] {getName(), column.getName(), column.getDbTable().getName()},
				column, 
				textRange
			);
		}
	}
	
	class AssociationOverrideContainerOwner implements OrmAssociationOverrideContainer.Owner {
		public TypeMapping getOverridableTypeMapping() {
			return GenericOrmElementCollectionMapping2_0.this.getResolvedTargetEmbeddable();
		}

		public OrmTypeMapping getTypeMapping() {
			return GenericOrmElementCollectionMapping2_0.this.getTypeMapping();
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

		public String getDefaultTableName() {
			return GenericOrmElementCollectionMapping2_0.this.getCollectionTable().getName();
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return GenericOrmElementCollectionMapping2_0.this.getCollectionTable().getDbTable();
		}

		public IMessage buildColumnTableNotValidMessage(AssociationOverride override, BaseColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.ELEMENT_COLLECTION_COLUMN_TABLE_DOES_NOT_MATCH_COLLECTION_TABLE,
					new String[] {column.getTable(), column.getName()}, 
					column, 
					textRange
				);
		}

		protected IMessage buildVirtualAttributeColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_DOES_NOT_MATCH_COLLECTION_TABLE,
				new String[] {
					GenericOrmElementCollectionMapping2_0.this.getName(), 
					overrideName, 
					column.getTable(), 
					column.getName()},
				column,
				textRange
			);
		}

		protected IMessage buildVirtualOverrideColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_TABLE_DOES_NOT_MATCH_COLLECTION_TABLE,
				new String[] { 
					overrideName, 
					column.getTable(), 
					column.getName()},
				column,
				textRange
			);
		}

		public IMessage buildColumnUnresolvedNameMessage(AssociationOverride override, NamedColumn column, TextRange textRange) {
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
					GenericOrmElementCollectionMapping2_0.this.getName(), 
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
						GenericOrmElementCollectionMapping2_0.this.getName(),
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
					new String[] {GenericOrmElementCollectionMapping2_0.this.getName(), overrideName},
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
						new String[] {GenericOrmElementCollectionMapping2_0.this.getName(), overrideName},
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
			return GenericOrmElementCollectionMapping2_0.this.getValidationTextRange();
		}
	}
	
	//********** OrmAttributeOverrideContainer.Owner implementation *********	
	
	class AttributeOverrideContainerOwner implements OrmAttributeOverrideContainer.Owner {
		public TypeMapping getOverridableTypeMapping() {
			return GenericOrmElementCollectionMapping2_0.this.getResolvedTargetEmbeddable();
		}
		
		public OrmTypeMapping getTypeMapping() {
			return GenericOrmElementCollectionMapping2_0.this.getTypeMapping();
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
		
		public String getDefaultTableName() {
			return GenericOrmElementCollectionMapping2_0.this.getCollectionTable().getName();
		}
		
		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return GenericOrmElementCollectionMapping2_0.this.getCollectionTable().getDbTable();
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

		public IMessage buildColumnUnresolvedNameMessage(AttributeOverride override, NamedColumn column, TextRange textRange) {
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
					GenericOrmElementCollectionMapping2_0.this.getName(), 
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

		public IMessage buildColumnTableNotValidMessage(AttributeOverride override, BaseColumn column, TextRange textRange) {
			if (isVirtual()) {
				return this.buildVirtualAttributeColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			if (override.isVirtual()) {
				return this.buildVirtualOverrideColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.ELEMENT_COLLECTION_COLUMN_TABLE_DOES_NOT_MATCH_COLLECTION_TABLE,
					new String[] {column.getTable(), column.getName()}, 
					column, 
					textRange
				);
		}

		protected IMessage buildVirtualAttributeColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_DOES_NOT_MATCH_COLLECTION_TABLE,
				new String[] {
					GenericOrmElementCollectionMapping2_0.this.getName(), 
					overrideName, 
					column.getTable(), 
					column.getName()},
				column,
				textRange
			);
		}

		protected IMessage buildVirtualOverrideColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_TABLE_DOES_NOT_MATCH_COLLECTION_TABLE,
				new String[] { 
					overrideName, 
					column.getTable(), 
					column.getName()},
				column,
				textRange
			);
		}

		public TextRange getValidationTextRange() {
			return GenericOrmElementCollectionMapping2_0.this.getValidationTextRange();
		}
	}
}
