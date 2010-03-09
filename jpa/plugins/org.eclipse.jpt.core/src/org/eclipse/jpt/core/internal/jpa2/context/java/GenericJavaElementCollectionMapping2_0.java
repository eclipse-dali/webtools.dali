/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
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
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaBaseColumn;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.core.jpa2.resource.java.CollectionTable2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyClass2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyColumn2_0Annotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.EnumeratedAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericJavaElementCollectionMapping2_0
	extends AbstractJavaAttributeMapping<ElementCollection2_0Annotation>
	implements JavaElementCollectionMapping2_0
{
	protected String specifiedTargetClass;
	protected String defaultTargetClass;
	protected PersistentType resolvedTargetType;
	protected Embeddable resolvedTargetEmbeddable;
	protected Entity resolvedTargetEntity;

	protected FetchType specifiedFetch;
	
	protected final JavaOrderable2_0 orderable;
	
	protected final JavaCollectionTable2_0 collectionTable;

	protected Type valueType;
	
	protected final JavaColumn valueColumn;
	
	protected JavaConverter valueConverter;

	protected final JavaConverter nullConverter;
	
	protected final JavaAttributeOverrideContainer valueAttributeOverrideContainer;
	
	protected final JavaAssociationOverrideContainer valueAssociationOverrideContainer;
	
	protected Type keyType;
	
	//MapKey is not supported by the spec, so this is only for EclipseLink
	//In the generic case we can handle this with validation and not showing the UI widgets
	protected String specifiedMapKey;
	protected boolean noMapKey = false;
	protected boolean pkMapKey = false;
	protected boolean customMapKey = false;

	protected String specifiedMapKeyClass;
	protected String defaultMapKeyClass;
	protected PersistentType resolvedMapKeyType;
	protected Embeddable resolvedMapKeyEmbeddable;
	protected Entity resolvedMapKeyEntity;

	protected final JavaColumn mapKeyColumn;

	protected final JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer;

	public GenericJavaElementCollectionMapping2_0(JavaPersistentAttribute parent) {
		super(parent);
		this.orderable = getJpaFactory().buildJavaOrderable(this, buildOrderableOwner());
		this.collectionTable = getJpaFactory().buildJavaCollectionTable(this);
		this.valueColumn = getJpaFactory().buildJavaColumn(parent, new ValueColumnOwner());
		this.nullConverter = getJpaFactory().buildJavaNullConverter(this);
		this.valueConverter = this.nullConverter;
		this.valueAttributeOverrideContainer = this.getJpaFactory().buildJavaAttributeOverrideContainer(this, new ValueAttributeOverrideContainerOwner());
		this.valueAssociationOverrideContainer = this.getJpaFactory().buildJavaAssociationOverrideContainer(this, new ValueAssociationOverrideContainerOwner());
		this.mapKeyColumn = getJpaFactory().buildJavaMapKeyColumn(parent, new MapKeyColumnOwner());
		this.mapKeyAttributeOverrideContainer = this.getJpaFactory().buildJavaAttributeOverrideContainer(this, new MapKeyAttributeOverrideContainerOwner());
	}

	@Override
	protected JpaFactory2_0 getJpaFactory() {
		return (JpaFactory2_0) super.getJpaFactory();
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.defaultTargetClass = this.buildDefaultTargetClass();
		this.specifiedFetch = this.getResourceFetch();
		this.orderable.initialize();
		this.specifiedTargetClass = this.getResourceTargetClass();
		this.resolvedTargetType = this.buildResolvedTargetType();
		this.resolvedTargetEmbeddable = this.buildResolvedTargetEmbeddable();
		this.resolvedTargetEntity = this.buildResolvedTargetEntity();
		this.initializeCollectionTable();
		this.initializeValueType();
		this.initializeValueColumn();
		this.initializeValueConverter();
		this.valueAttributeOverrideContainer.initialize(getResourcePersistentAttribute());
		this.valueAssociationOverrideContainer.initialize(getResourcePersistentAttribute());
		this.defaultMapKeyClass = this.buildDefaultMapKeyClass();
		this.specifiedMapKeyClass = this.getResourceMapKeyClass();
		this.resolvedMapKeyType = this.buildResolvedMapKeyType();
		this.resolvedMapKeyEmbeddable = this.buildResolvedMapKeyEmbeddable();
		this.resolvedMapKeyEntity = this.buildResolvedMapKeyEntity();
		this.initializeKeyType();
		this.initializeMapKey();
		this.initializeMapKeyColumn();
		this.mapKeyAttributeOverrideContainer.initialize(getResourcePersistentAttribute());
	}

	@Override
	protected void update() {
		super.update();
		this.setDefaultTargetClass(this.buildDefaultTargetClass());
		this.setSpecifiedFetch_(this.getResourceFetch());
		this.orderable.update();
		this.setSpecifiedTargetClass_(this.getResourceTargetClass());
		this.resolvedTargetType = this.buildResolvedTargetType();//no need for change notification, use resolved target embeddable change notification instead?
		this.setResolvedTargetEmbeddable(this.buildResolvedTargetEmbeddable());
		this.updateCollectionTable();
		this.updateValueType();
		this.updateValueColumn();
		this.updateValueConverter();
		this.valueAttributeOverrideContainer.update(getResourcePersistentAttribute());
		this.valueAssociationOverrideContainer.update(getResourcePersistentAttribute());
		this.setDefaultMapKeyClass(this.buildDefaultMapKeyClass());
		this.setSpecifiedMapKeyClass_(this.getResourceMapKeyClass());
		this.resolvedMapKeyType = this.buildResolvedMapKeyType();//no need for change notification, use resolved target embeddable change notification instead?
		this.setResolvedMapKeyEmbeddable(this.buildResolvedMapKeyEmbeddable());
		this.setResolvedMapKeyEntity(this.buildResolvedMapKeyEntity());
		this.updateKeyType();
		this.updateMapKey();
		this.updateMapKeyColumn();
		this.mapKeyAttributeOverrideContainer.update(getResourcePersistentAttribute());
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		this.valueAssociationOverrideContainer.postUpdate();
	}
	
	public Entity getEntity() {
		return getTypeMapping().getKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY ? (Entity) getTypeMapping() : null;
	}

	//************** JavaAttributeMapping implementation ***************

	public String getKey() {
		return MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return ElementCollection2_0Annotation.ANNOTATION_NAME;
	}
	
	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(JPA.ASSOCIATION_OVERRIDE);
		names.add(JPA.ASSOCIATION_OVERRIDES);
		names.add(JPA.ATTRIBUTE_OVERRIDE);
		names.add(JPA.ATTRIBUTE_OVERRIDES);
		names.add(JPA2_0.COLLECTION_TABLE);
		names.add(JPA.COLUMN);
		names.add(JPA.ENUMERATED);
		names.add(JPA.LOB);
		names.add(JPA.MAP_KEY);
		names.add(JPA2_0.MAP_KEY_CLASS);
		names.add(JPA2_0.MAP_KEY_COLUMN);
		names.add(JPA2_0.MAP_KEY_ENUMERATED);
		names.add(JPA2_0.MAP_KEY_JOIN_COLUMN);
		names.add(JPA2_0.MAP_KEY_JOIN_COLUMNS);
		names.add(JPA2_0.MAP_KEY_TEMPORAL);
		names.add(JPA.ORDER_BY);
		names.add(JPA2_0.ORDER_COLUMN);
		names.add(JPA.TEMPORAL);
	}
	
	// ********** target class **********

	public String getTargetClass() {
		return (this.specifiedTargetClass != null) ? this.specifiedTargetClass : this.defaultTargetClass;
	}

	public String getSpecifiedTargetClass() {
		return this.specifiedTargetClass;
	}

	public void setSpecifiedTargetClass(String targetClass) {
		String old = this.specifiedTargetClass;
		this.specifiedTargetClass = targetClass;
		this.mappingAnnotation.setTargetClass(targetClass);
		this.firePropertyChanged(SPECIFIED_TARGET_CLASS_PROPERTY, old, targetClass);
	}

	protected void setSpecifiedTargetClass_(String targetClass) {
		String old = this.specifiedTargetClass;
		this.specifiedTargetClass = targetClass;
		this.firePropertyChanged(SPECIFIED_TARGET_CLASS_PROPERTY, old, targetClass);
	}

	protected String getResourceTargetClass() {
		return this.mappingAnnotation.getTargetClass();
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
		return this.getPersistentAttribute().getMultiReferenceTargetTypeName();
	}

	public Embeddable getResolvedTargetEmbeddable() {
		return this.resolvedTargetEmbeddable;
	}

	protected void setResolvedTargetEmbeddable(Embeddable embeddable) {
		Embeddable old = this.resolvedTargetEmbeddable;
		this.resolvedTargetEmbeddable = embeddable;
		this.firePropertyChanged(RESOLVED_TARGET_EMBEDDABLE_PROPERTY, old, embeddable);
	}

	public PersistentType getResolvedTargetType() {
		return this.resolvedTargetType;
	}
	
	protected PersistentType buildResolvedTargetType() {
		String targetTypeClassName = (this.specifiedTargetClass == null) ?
						this.defaultTargetClass :
						this.mappingAnnotation.getFullyQualifiedTargetClassName();
		return (targetTypeClassName == null) ? null : this.getPersistenceUnit().getPersistentType(targetTypeClassName);
	}

	protected Embeddable buildResolvedTargetEmbeddable() {
		if (this.resolvedTargetType == null) {
			return null;
		}
		TypeMapping typeMapping = this.resolvedTargetType.getMapping();
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
	}

	protected Entity buildResolvedTargetEntity() {
		if (this.resolvedTargetType == null) {
			return null;
		}
		TypeMapping typeMapping = this.resolvedTargetType.getMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}


	public char getTargetClassEnclosingTypeSeparator() {
		return '.';
	}
	
	// *************** Fetch ***************
	
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
		this.mappingAnnotation.setFetch(FetchType.toJavaResourceModel(newSpecifiedFetch));
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedFetch_(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}
	
	protected FetchType getResourceFetch() {
		return FetchType.fromJavaResourceModel(this.mappingAnnotation.getFetch());
	}
	
	// ********** collection table **********
	
	public JavaCollectionTable2_0 getCollectionTable() {
		return this.collectionTable;
	}
	
	protected void initializeCollectionTable() {
		this.collectionTable.initialize(getCollectionTableAnnotation());
	}
	
	protected void updateCollectionTable() {
		this.collectionTable.update(getCollectionTableAnnotation());
	}
	
	public CollectionTable2_0Annotation getCollectionTableAnnotation() {
		return 	(CollectionTable2_0Annotation) this.getResourcePersistentAttribute().
				getNonNullAnnotation(CollectionTable2_0Annotation.ANNOTATION_NAME);
	}

	// ***************** value column **************

	public JavaColumn getValueColumn() {
		return this.valueColumn;
	}

	protected void initializeValueColumn() {
		this.valueColumn.initialize(getColumnAnnotation());
	}

	protected void updateValueColumn() {
		getValueColumn().update(getColumnAnnotation());
	}

	public ColumnAnnotation getColumnAnnotation() {
		return (ColumnAnnotation) this.getResourcePersistentAttribute().getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}


	// ************ value converter ************
	
	public JavaConverter getConverter() {
		return this.valueConverter;
	}
	
	protected String getValueConverterType() {
		return this.valueConverter.getType();
	}
	
	public void setConverter(String converterType) {
		if (this.valuesAreEqual(getValueConverterType(), converterType)) {
			return;
		}
		JavaConverter oldConverter = this.valueConverter;
		JavaConverter newConverter = buildConverter(converterType);
		this.valueConverter = this.nullConverter;
		if (oldConverter != null) {
			oldConverter.removeFromResourceModel();
		}
		this.valueConverter = newConverter;
		if (newConverter != null) {
			newConverter.addToResourceModel();
		}
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void setConverter(JavaConverter newConverter) {
		JavaConverter oldConverter = this.valueConverter;
		this.valueConverter = newConverter;
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	
	protected void initializeValueConverter() {
		this.valueConverter = this.buildConverter(this.getResourceConverterType());
	}
	
	protected void updateValueConverter() {
		if (this.valuesAreEqual(getResourceConverterType(), getValueConverterType())) {
			getConverter().update(this.getResourcePersistentAttribute());
		}
		else {
			JavaConverter javaConverter = buildConverter(getResourceConverterType());
			setConverter(javaConverter);
		}
	}
	
	protected JavaConverter buildConverter(String converterType) {
		if (this.valuesAreEqual(converterType, Converter.NO_CONVERTER)) {
			return this.nullConverter;			
		}
		if (this.valuesAreEqual(converterType, Converter.ENUMERATED_CONVERTER)) {
			return getJpaFactory().buildJavaEnumeratedConverter(this, this.getResourcePersistentAttribute());
		}
		if (this.valuesAreEqual(converterType, Converter.TEMPORAL_CONVERTER)) {
			return getJpaFactory().buildJavaTemporalConverter(this, this.getResourcePersistentAttribute());
		}
		return null;
	}
	
	protected String getResourceConverterType() {
		if (this.getResourcePersistentAttribute().getAnnotation(EnumeratedAnnotation.ANNOTATION_NAME) != null) {
			return Converter.ENUMERATED_CONVERTER;
		}
		if (this.getResourcePersistentAttribute().getAnnotation(TemporalAnnotation.ANNOTATION_NAME) != null) {
			return Converter.TEMPORAL_CONVERTER;
		}
		return Converter.NO_CONVERTER;
	}


	// ********** overrides **********
		
	public JavaAttributeOverrideContainer getValueAttributeOverrideContainer() {
		return this.valueAttributeOverrideContainer;
	}

	public JavaAssociationOverrideContainer getValueAssociationOverrideContainer() {
		return this.valueAssociationOverrideContainer;
	}


	// ***************** map key column **************

	public JavaColumn getMapKeyColumn() {
		return this.mapKeyColumn;
	}

	protected void initializeMapKeyColumn() {
		this.mapKeyColumn.initialize(getMapKeyColumnAnnotation());
	}

	protected void updateMapKeyColumn() {
		getMapKeyColumn().update(getMapKeyColumnAnnotation());
	}

	public MapKeyColumn2_0Annotation getMapKeyColumnAnnotation() {
		if (!this.isJpa2_0Compatible()) {
			return null;
		}
		return (MapKeyColumn2_0Annotation) this.getResourcePersistentAttribute().getNonNullAnnotation(MapKeyColumn2_0Annotation.ANNOTATION_NAME);
	}

	public JavaAttributeOverrideContainer getMapKeyAttributeOverrideContainer() {
		return this.mapKeyAttributeOverrideContainer;
	}

	// ********** ordering **********  
	
	public JavaOrderable2_0 getOrderable() {
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


	public Type getValueType() {
		return this.valueType;
	}

	protected void setValueType(Type newValueType) {
		Type old = this.valueType;
		this.valueType = newValueType;
		firePropertyChanged(VALUE_TYPE_PROPERTY, old, newValueType);
	}

	protected void initializeValueType() {
		this.valueType = this.buildValueType();
	}

	protected void updateValueType() {
		this.setValueType(this.buildValueType()); 
	}

	protected Type buildValueType() {
		if (getResolvedTargetEmbeddable() != null) {
			return Type.EMBEDDABLE_TYPE; 
		}
		else if (this.resolvedTargetEntity != null) {
			return Type.ENTITY_TYPE; 
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

	protected void initializeKeyType() {
		this.keyType = this.buildKeyType();
	}

	protected void updateKeyType() {
		this.setKeyType(this.buildKeyType());
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

	// ********** map key **********  

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
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		if (mapKey == null) {
			if (mapKeyAnnotation != null) {
				this.removeMapKeyAnnotation();
			}
		} else {
			if (mapKeyAnnotation == null) {
				mapKeyAnnotation = this.addMapKeyAnnotation();
			}
			mapKeyAnnotation.setName(mapKey);
		}
		this.firePropertyChanged(SPECIFIED_MAP_KEY_PROPERTY, old, mapKey);
	}

	protected void setSpecifiedMapKey_(String mapKey) {
		String old = this.specifiedMapKey;
		this.specifiedMapKey = mapKey;
		this.firePropertyChanged(SPECIFIED_MAP_KEY_PROPERTY, old, mapKey);
	}

	protected void initializeMapKey() {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		if (mapKeyAnnotation == null) {
			this.noMapKey = true;
		} else {
			this.specifiedMapKey = mapKeyAnnotation.getName();
			if (this.specifiedMapKey == null) {
				this.pkMapKey = true;
			} else {
				this.customMapKey = true;
			}
		}
	}

	protected void updateMapKey() {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		if (mapKeyAnnotation == null) {
			this.setSpecifiedMapKey_(null);
			this.setNoMapKey_(true);
			this.setPkMapKey_(false);
			this.setCustomMapKey_(false);
		} else {
			String mk = mapKeyAnnotation.getName();
			this.setSpecifiedMapKey_(mk);
			this.setNoMapKey_(false);
			this.setPkMapKey_(mk == null);
			this.setCustomMapKey_(mk != null);
		}
	}

	protected MapKeyAnnotation getMapKeyAnnotation() {
		return (MapKeyAnnotation) this.getResourcePersistentAttribute().getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected MapKeyAnnotation addMapKeyAnnotation() {
		return (MapKeyAnnotation) this.getResourcePersistentAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected boolean mapKeyNameTouches(int pos, CompilationUnit astRoot) {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		return (mapKeyAnnotation != null) && mapKeyAnnotation.nameTouches(pos, astRoot);
	}


	// ********** no map key **********  

	public boolean isNoMapKey() {
		return this.noMapKey;
	}

	public void setNoMapKey(boolean noMapKey) {
		boolean old = this.noMapKey;
		this.noMapKey = noMapKey;
		if (noMapKey) {
			if (this.getMapKeyAnnotation() != null) {
				this.removeMapKeyAnnotation();
			}
		} else {
			// the 'noMapKey' flag is cleared as a
			// side-effect of setting the other flags,
			// via a call to #setNoMapKey_(boolean)
		}
		this.firePropertyChanged(NO_MAP_KEY_PROPERTY, old, noMapKey);
	}

	protected void setNoMapKey_(boolean noMapKey) {
		boolean old = this.noMapKey;
		this.noMapKey = noMapKey;
		this.firePropertyChanged(NO_MAP_KEY_PROPERTY, old, noMapKey);	
	}


	// ********** pk map key **********  

	public boolean isPkMapKey() {
		return this.pkMapKey;
	}

	public void setPkMapKey(boolean pkMapKey) {
		boolean old = this.pkMapKey;
		this.pkMapKey = pkMapKey;
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		if (pkMapKey) {
			if (mapKeyAnnotation == null) {
				this.addMapKeyAnnotation();
			} else {
				mapKeyAnnotation.setName(null);
			}
		} else {
			// the 'pkMapKey' flag is cleared as a
			// side-effect of setting the other flags,
			// via a call to #setPkMapKey_(boolean)
		}
		this.firePropertyChanged(PK_MAP_KEY_PROPERTY, old, pkMapKey);
	}

	protected void setPkMapKey_(boolean pkMapKey) {
		boolean old = this.pkMapKey;
		this.pkMapKey = pkMapKey;
		this.firePropertyChanged(PK_MAP_KEY_PROPERTY, old, pkMapKey);
	}


	// ********** custom map key **********  

	public boolean isCustomMapKey() {
		return this.customMapKey;
	}

	public void setCustomMapKey(boolean customMapKey) {
		boolean old = this.customMapKey;
		this.customMapKey = customMapKey;
		if (customMapKey) {
			this.setSpecifiedMapKey(""); //$NON-NLS-1$
		} else {
			// the 'customMapKey' flag is cleared as a
			// side-effect of setting the other flags,
			// via a call to #setCustomMapKey_(boolean)
		}
		this.firePropertyChanged(CUSTOM_MAP_KEY_PROPERTY, old, customMapKey);
	}

	protected void setCustomMapKey_(boolean customMapKey) {
		boolean old = this.customMapKey;
		this.customMapKey = customMapKey;
		this.firePropertyChanged(CUSTOM_MAP_KEY_PROPERTY, old, customMapKey);
	}

	// *************** map key class *************

	public String getMapKeyClass() {
		return (this.specifiedMapKeyClass != null) ? this.specifiedMapKeyClass : this.defaultMapKeyClass;
	}

	public String getSpecifiedMapKeyClass() {
		return this.specifiedMapKeyClass;
	}

	public void setSpecifiedMapKeyClass(String mapKeyClass) {
		String old = this.specifiedMapKeyClass;
		this.specifiedMapKeyClass = mapKeyClass;
		MapKeyClass2_0Annotation mapKeyClassAnnotation = this.getMapKeyClassAnnotation();
		if (mapKeyClass == null) {
			if (mapKeyClassAnnotation != null) {
				this.removeMapKeyClassAnnotation();
			}
		} else {
			if (mapKeyClassAnnotation == null) {
				mapKeyClassAnnotation = this.addMapKeyClassAnnotation();
			}
			mapKeyClassAnnotation.setValue(mapKeyClass);
		}

		this.firePropertyChanged(SPECIFIED_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected void setSpecifiedMapKeyClass_(String mapKeyClass) {
		String old = this.specifiedMapKeyClass;
		this.specifiedMapKeyClass = mapKeyClass;
		this.firePropertyChanged(SPECIFIED_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected String getResourceMapKeyClass() {
		MapKeyClass2_0Annotation annotation = getMapKeyClassAnnotation();
		return annotation == null ? null : annotation.getValue();
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
		return this.getPersistentAttribute().getMultiReferenceMapKeyTypeName();
	}

	public char getMapKeyClassEnclosingTypeSeparator() {
		return '.';
	}

	protected MapKeyClass2_0Annotation getMapKeyClassAnnotation() {
		return (MapKeyClass2_0Annotation) this.getResourcePersistentAttribute().getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
	}

	protected MapKeyClass2_0Annotation addMapKeyClassAnnotation() {
		return (MapKeyClass2_0Annotation) this.getResourcePersistentAttribute().addAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyClassAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
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

	protected PersistentType buildResolvedMapKeyType() {
		String mapKeyClassName = (this.specifiedMapKeyClass == null) ?
						this.defaultMapKeyClass :
						this.getMapKeyClassAnnotation().getFullyQualifiedClassName();
		return (mapKeyClassName == null) ? null : this.getPersistenceUnit().getPersistentType(mapKeyClassName);
	}

	protected Embeddable buildResolvedMapKeyEmbeddable() {
		if (this.resolvedMapKeyType == null) {
			return null;
		}
		TypeMapping typeMapping = this.resolvedMapKeyType.getMapping();
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
	}

	protected Entity buildResolvedMapKeyEntity() {
		if (this.resolvedMapKeyType == null) {
			return null;
		}
		TypeMapping typeMapping = this.resolvedMapKeyType.getMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}



	// ********** Java completion proposals **********  

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getCollectionTable().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getValueColumn().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = getConverter().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getOrderable().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getValueAttributeOverrideContainer().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getValueAssociationOverrideContainer().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.mapKeyNameTouches(pos, astRoot)) {
			return this.javaCandidateMapKeyNames(filter);
		}
		result = this.getMapKeyColumn().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getMapKeyAttributeOverrideContainer().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
//		result = this.getMapKeyAssociationOverrideContainer().javaCompletionProposals(pos, filter, astRoot);
//		if (result != null) {
//			return result;
//		}
		return null;
	}

	protected Iterator<String> javaCandidateMapKeyNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateMapKeyNames(filter));
	}

	protected Iterator<String> candidateMapKeyNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateMapKeyNames(), filter);
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

	@Override
	public Iterator<String> allMappingNames() {
		return new CompositeIterator<String>(this.getName(), this.allEmbeddableAttributeMappingNames());
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

	protected Iterator<AttributeMapping> allTargetEmbeddableAttributeMappings() {
		return (this.resolvedTargetEmbeddable != null) ?
				this.resolvedTargetEmbeddable.allAttributeMappings() :
				EmptyIterator.<AttributeMapping> instance();
	}

	protected Iterator<AttributeMapping> embeddableAttributeMappings() {
		if (this.getResolvedTargetEmbeddable() == null) {
			return EmptyIterator.instance();
		}
		return this.getResolvedTargetEmbeddable().attributeMappings();
	}

	@Override
	public Iterator<String> allOverrideableAttributeMappingNames() {
		return this.isJpa2_0Compatible() ?
				this.embeddableOverrideableAttributeMappingNames() :
				super.allOverrideableAttributeMappingNames();
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
		return this.isJpa2_0Compatible() ?
				this.embeddableOverrideableAssociationMappingNames() :
				super.allOverrideableAssociationMappingNames();
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
	public AttributeMapping resolveAttributeMapping(String name) {
		AttributeMapping resolvedMapping = super.resolveAttributeMapping(name);
		if (resolvedMapping != null) {
			return resolvedMapping;
		}
		if (this.isJpa2_0Compatible()) {
			int dotIndex = name.indexOf('.');
			if (dotIndex != -1) {
				if (getName().equals(name.substring(0, dotIndex))) {
					for (AttributeMapping attributeMapping : CollectionTools.iterable(embeddableAttributeMappings())) {
						resolvedMapping = attributeMapping.resolveAttributeMapping(name.substring(dotIndex + 1));
						if (resolvedMapping != null) {
							return resolvedMapping;
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public Column resolveOverriddenColumn(String attributeName) {
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

	// ********** metamodel **********  
	@Override
	protected String getMetamodelFieldTypeName() {
		return ((JavaPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldTypeName();
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
		String keyTypeName = ((JavaPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldMapKeyTypeName();
		if (keyTypeName != null) {
			typeArgumentNames.add(keyTypeName);
		}
	}

	public String getMetamodelFieldMapKeyTypeName() {
		return MappingTools.getMetamodelFieldMapKeyTypeName(this);
	}
	
	
	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateTargetClass(messages, astRoot);
		this.validateMapKeyClass(messages, astRoot);
		this.getOrderable().validate(messages, reporter, astRoot);
		this.getCollectionTable().validate(messages, reporter, astRoot);
		this.validateValue(messages, reporter, astRoot);
		this.validateMapKey(messages, reporter, astRoot);
	}

	protected void validateValue(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		//TODO should we handle validation when the type is embeddable, but a value column is specified, or things like that if that is invalid?
		if (getValueType() == Type.BASIC_TYPE) {
			this.getValueColumn().validate(messages, reporter, astRoot);
			this.getConverter().validate(messages, reporter, astRoot);
		}
		else if (getValueType() == Type.EMBEDDABLE_TYPE) {
			this.getValueAttributeOverrideContainer().validate(messages, reporter, astRoot);
			this.getValueAssociationOverrideContainer().validate(messages, reporter, astRoot);
		}
	}

	protected void validateMapKey(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (getMapKey() != null) {
			//TODO validate that the map key refers to an existing attribute
			return;
		}
		if (getKeyType() == Type.BASIC_TYPE) {
			this.getMapKeyColumn().validate(messages, reporter, astRoot);
			//validate map key converter
		}
		else if (getKeyType() == Type.ENTITY_TYPE) {
			//validate map key join columns
		}
		else if (getKeyType() == Type.EMBEDDABLE_TYPE) {
			getMapKeyAttributeOverrideContainer().validate(messages, reporter, astRoot);
			//getMapKeyAssociationOverrideContainer().validate(messages, reporter, astRoot);
		}
	}

	protected void validateTargetClass(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getTargetClass() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED,
					new String[0], 
					this, 
					this.getValidationTextRange(astRoot)
				)
			);
		}
		//TODO this does not give an error for unmapped, unlisted types that aren't basic
		if (this.resolvedTargetType != null) {
			if (getResolvedTargetEmbeddable() == null) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE,
						new String[] {this.getTargetClass()}, 
						this, 
						this.getTargetClassTextRange(astRoot)
					)
				);
			}
		}
	}

	protected void validateMapKeyClass(List<IMessage> messages, CompilationUnit astRoot) {
		if (!getPersistentAttribute().getJpaContainer().isMap()) {
			return;
		}
		if (this.getMapKeyClass() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.ELEMENT_COLLECTION_MAP_KEY_CLASS_NOT_DEFINED,
					new String[0], 
					this, 
					this.getValidationTextRange(astRoot)
				)
			);
		}
	}

	protected TextRange getTargetClassTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.mappingAnnotation.getTargetClassTextRange(astRoot), astRoot);
	}

	protected TextRange getTextRange(TextRange textRange, CompilationUnit astRoot) {
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}


	abstract class ColumnOwner implements JavaBaseColumn.Owner {
		public String getDefaultTableName() {
			return getCollectionTable().getName();
		}
		
		public TypeMapping getTypeMapping() {
			return GenericJavaElementCollectionMapping2_0.this.getTypeMapping();
		}
		
		public Table getDbTable(String tableName) {
			if (getCollectionTable().getName().equals(tableName)) {
				return GenericJavaElementCollectionMapping2_0.this.getCollectionTable().getDbTable();
			}
			return null;
		}
		
		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name.  the table is always the collection table
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return !StringTools.stringsAreEqual(getDefaultTableName(), tableName);
		}

		public java.util.Iterator<String> candidateTableNames() {
			return EmptyIterator.instance();
		}
		
		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaElementCollectionMapping2_0.this.getValidationTextRange(astRoot);
		}
		
		public IMessage buildTableNotValidMessage(BaseColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				this.getColumnTableNotValidMessage(),
				new String[] {
					column.getTable(),
					column.getName(),
					JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE}, 
				column,
				textRange
			);
		}

		protected abstract String getColumnTableNotValidMessage();

		public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
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
		
		protected abstract String getColumnUnresolvedNameMessage();
	}

	class ValueColumnOwner extends ColumnOwner {		
		public String getDefaultColumnName() {
			return GenericJavaElementCollectionMapping2_0.this.getName();
		}

		@Override
		protected String getColumnTableNotValidMessage() {
			return JpaValidationMessages.COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected String getColumnUnresolvedNameMessage() {
			return JpaValidationMessages.COLUMN_UNRESOLVED_NAME;
		}
	}

	class MapKeyColumnOwner extends ColumnOwner {		
		public String getDefaultColumnName() {
			return GenericJavaElementCollectionMapping2_0.this.getName() + "_KEY"; //$NON-NLS-1$
		}

		@Override
		protected String getColumnTableNotValidMessage() {
			return JpaValidationMessages.MAP_KEY_COLUMN_TABLE_NOT_VALID;
		}

		@Override
		protected String getColumnUnresolvedNameMessage() {
			return JpaValidationMessages.MAP_KEY_COLUMN_UNRESOLVED_NAME;
		}
	}

	// ********** override container owners **********

	abstract class OverrideContainerOwner implements JavaOverrideContainer.Owner {
		
		public TypeMapping getTypeMapping() {
			return GenericJavaElementCollectionMapping2_0.this.getTypeMapping();
		}
		
		public String getDefaultTableName() {
			return GenericJavaElementCollectionMapping2_0.this.getCollectionTable().getName();
		}
		
		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return GenericJavaElementCollectionMapping2_0.this.getCollectionTable().getDbTable();
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
		
		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaElementCollectionMapping2_0.this.getValidationTextRange(astRoot);
		}
	}

	class ValueAssociationOverrideContainerOwner extends OverrideContainerOwner
		implements JavaAssociationOverrideContainer.Owner {

		public TypeMapping getOverridableTypeMapping() {
			return GenericJavaElementCollectionMapping2_0.this.getResolvedTargetEmbeddable();
		}

		public RelationshipReference resolveRelationshipReference(String associationOverrideName) {
			return MappingTools.resolveRelationshipReference(getOverridableTypeMapping(), associationOverrideName);
		}	

		public String getPossiblePrefix() {
			return "value."; //$NON-NLS-1$
		}

		public String getWritePrefix() {
			return getPersistentAttribute().getJpaContainer().isMap() ? this.getPossiblePrefix() : null;
		}

		//return false if the override is prefixed with "key.", these will be part of the MapKeyAttributeOverrideContainer.
		//a prefix of "value." or no prefix at all is relevant. If the type is not a Map then return true since all attribute overrides
		//need to apply to the value.
		public boolean isRelevant(String overrideName) {
			if (getKeyType() != Type.EMBEDDABLE_TYPE) {
				return true;
			}
			return !overrideName.startsWith("key."); //$NON-NLS-1$
		}

		public IMessage buildColumnTableNotValidMessage(BaseOverride override, BaseColumn column, TextRange textRange) {
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
			if (override.isVirtual()) {
				return this.buildVirtualColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME,
				new String[] {overrideName, column.getName(), column.getDbTable().getName()},
				column, 
				textRange
			);
		}

		public IMessage buildColumnUnresolvedReferencedColumnNameMessage(AssociationOverride override, BaseJoinColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualColumnUnresolvedReferencedColumnNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				new String[] {column.getReferencedColumnName(), column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}

		protected IMessage buildVirtualColumnUnresolvedReferencedColumnNameMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_REFERENCED_COLUMN_NAME,
				new String[] {overrideName, column.getReferencedColumnName(), column.getReferencedColumnDbTable().getName()},
				column, 
				textRange
			);
		}

		public IMessage buildUnspecifiedNameMultipleJoinColumnsMessage(AssociationOverride override, BaseJoinColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualUnspecifiedNameMultipleJoinColumnsMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[0],
				column, 
				textRange
			);
		}

		protected IMessage buildVirtualUnspecifiedNameMultipleJoinColumnsMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
					new String[] {overrideName},
				column, 
				textRange
			);
		}
		
		public IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(AssociationOverride override, BaseJoinColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
				new String[0],
				column, 
				textRange
			);
		}

		protected IMessage buildVirtualUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(String overrideName, BaseJoinColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_NAME_MUST_BE_SPECIFIED_MULTIPLE_JOIN_COLUMNS,
					new String[] {overrideName},
				column, 
				textRange
			);
		}
	}
	
	//********** AttributeOverrideContainer.Owner implementation *********	
	
	class ValueAttributeOverrideContainerOwner
		extends OverrideContainerOwner
		implements JavaAttributeOverrideContainer.Owner
	{
		public TypeMapping getOverridableTypeMapping() {
			return GenericJavaElementCollectionMapping2_0.this.getResolvedTargetEmbeddable();
		}
		
		public Column resolveOverriddenColumn(String attributeOverrideName) {
			return MappingTools.resolveOverridenColumn(getOverridableTypeMapping(), attributeOverrideName);
		}

		public String getPossiblePrefix() {
			return "value."; //$NON-NLS-1$
		}

		public String getWritePrefix() {
			return getPersistentAttribute().getJpaContainer().isMap() ? this.getPossiblePrefix() : null;
		}

		//return false if the override is prefixed with "key.", these will be part of the MapKeyAttributeOverrideContainer.
		//a prefix of "value." or no prefix at all is relevant. If the type is not a Map then return true since all attribute overrides
		//need to apply to the value.
		public boolean isRelevant(String overrideName) {
			if (getKeyType() != Type.EMBEDDABLE_TYPE) {
				return true;
			}
			return !overrideName.startsWith("key."); //$NON-NLS-1$
		}

		public IMessage buildColumnUnresolvedNameMessage(BaseOverride override, NamedColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
				new String[] {overrideName, column.getName(), column.getDbTable().getName()},
				column, 
				textRange
			);
		}

		public IMessage buildColumnTableNotValidMessage(BaseOverride override, BaseColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualColumnTableNotValidMessage(override.getName(), column, textRange);
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

		protected IMessage buildVirtualColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
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
	}
	
	class MapKeyAttributeOverrideContainerOwner
		extends OverrideContainerOwner
		implements JavaAttributeOverrideContainer.Owner
	{

		public String getPossiblePrefix() {
			return "key."; //$NON-NLS-1$
		}

		public String getWritePrefix() {
			return this.getPossiblePrefix();
		}

		//the only relevant overrides are those that start with "key.", no prefix will be a value attribute override
		public boolean isRelevant(String overrideName) {
			if (getValueType() != Type.EMBEDDABLE_TYPE) {
				return true;
			}
			return overrideName.startsWith("key."); //$NON-NLS-1$
		}

		public TypeMapping getOverridableTypeMapping() {
			return GenericJavaElementCollectionMapping2_0.this.getResolvedMapKeyEmbeddable();
		}
	
		public Column resolveOverriddenColumn(String attributeOverrideName) {
			return MappingTools.resolveOverridenColumn(getOverridableTypeMapping(), attributeOverrideName);
		}
	
		public IMessage buildColumnUnresolvedNameMessage(BaseOverride override, NamedColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualColumnUnresolvedNameMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column, 
				textRange
			);
		}
		
		protected IMessage buildVirtualColumnUnresolvedNameMessage(String overrideName, NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.VIRTUAL_MAP_KEY_ATTRIBUTE_OVERRIDE_COLUMN_UNRESOLVED_NAME,
				new String[] {overrideName, column.getName(), column.getDbTable().getName()},
				column, 
				textRange
			);
		}
	
		public IMessage buildColumnTableNotValidMessage(BaseOverride override, BaseColumn column, TextRange textRange) {
			if (override.isVirtual()) {
				return this.buildVirtualColumnTableNotValidMessage(override.getName(), column, textRange);
			}
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.COLUMN_TABLE_NOT_VALID,
				new String[] {
					column.getTable(),
					column.getName(),
					JpaValidationDescriptionMessages.DOES_NOT_MATCH_COLLECTION_TABLE }, 
				column, 
				textRange
			);
		}
	
		protected IMessage buildVirtualColumnTableNotValidMessage(String overrideName, BaseColumn column, TextRange textRange) {
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
	}
}
