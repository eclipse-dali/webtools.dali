/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.SimpleAssociation;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.ModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyOverride;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EmbeddableOverrideDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.MapKeyAttributeOverrideColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.MapKeyAttributeOverrideValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.MapKeyColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.RelationshipStrategyTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.NullJavaConverter;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.MapKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.NullJavaMapKeyColumn2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.NullMapKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.context.MultiRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.PersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAttributeOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyClass2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyColumn2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyJoinColumn2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java multi-relationship (m:m, 1:m) mapping
 */
public abstract class AbstractJavaMultiRelationshipMapping<A extends RelationshipMappingAnnotation>
	extends AbstractJavaRelationshipMapping<A>
	implements MultiRelationshipMapping2_0, JavaCollectionMapping2_0
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

	protected final JavaColumn mapKeyColumn;
	protected JavaConverter mapKeyConverter;  // map key converter - never null

	protected final ContextListContainer<JavaJoinColumn, MapKeyJoinColumn2_0Annotation> specifiedMapKeyJoinColumnContainer;
	protected final ReadOnlyJoinColumn.Owner mapKeyJoinColumnOwner;

	protected JavaJoinColumn defaultMapKeyJoinColumn;

	protected final JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer;

	protected static final JavaConverter.Adapter[] MAP_KEY_CONVERTER_ADAPTER_ARRAY = new JavaConverter.Adapter[] {
		JavaBaseEnumeratedConverter.MapKeyAdapter.instance(),
		JavaBaseTemporalConverter.MapKeyAdapter.instance()
	};
	protected static final Iterable<JavaConverter.Adapter> MAP_KEY_CONVERTER_ADAPTERS = IterableTools.iterable(MAP_KEY_CONVERTER_ADAPTER_ARRAY);


	protected AbstractJavaMultiRelationshipMapping(JavaModifiablePersistentAttribute parent) {
		super(parent);
		this.orderable = this.buildOrderable();

		this.specifiedMapKey = this.buildSpecifiedMapKey();
		this.noMapKey = this.buildNoMapKey();
		this.pkMapKey = this.buildPkMapKey();
		this.customMapKey = this.buildCustomMapKey();

		this.specifiedMapKeyClass = this.buildSpecifiedMapKeyClass();

		this.mapKeyColumn = this.buildMapKeyColumn();
		this.mapKeyConverter = this.buildMapKeyConverter();
		this.mapKeyJoinColumnOwner = this.buildMapKeyJoinColumnOwner();
		this.specifiedMapKeyJoinColumnContainer = this.buildSpecifiedMapKeyJoinColumnContainer();
		this.mapKeyAttributeOverrideContainer = this.buildMapKeyAttributeOverrideContainer();
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
		this.syncSpecifiedMapKeyJoinColumns();

		this.mapKeyAttributeOverrideContainer.synchronizeWithResourceModel();
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
		this.updateModels(this.getSpecifiedMapKeyJoinColumns());
		this.updateDefaultMapKeyJoinColumn();

		this.mapKeyAttributeOverrideContainer.update();
	}


	// ********** orderable **********

	public Orderable getOrderable() {
		return this.orderable;
	}

	protected Orderable buildOrderable() {
		return this.isJpa2_0Compatible() ?
				this.getJpaFactory2_0().buildJavaOrderable(this, this.buildOrderableOwner()) :
				this.getJpaFactory().buildJavaOrderable(this);
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
			return AbstractJavaMultiRelationshipMapping.this.getRelationship().getStrategy();
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
			this.getMapKeyAnnotationForUpdate().setName(mapKey);

			this.setSpecifiedMapKey_(mapKey);
			this.setNoMapKey_(false);
			this.setPkMapKey_(false);
			this.setCustomMapKey_(true);
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
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		return (mapKeyAnnotation == null) ? null : mapKeyAnnotation.getName();
	}


	// ********** no map key **********

	public boolean isNoMapKey() {
		return this.noMapKey;
	}

	public void setNoMapKey(boolean noMapKey) {
		if (noMapKey) {
			if (this.getMapKeyAnnotation() != null) {
				this.removeMapKeyAnnotation();
			}

			this.setSpecifiedMapKey_(null);
			this.setNoMapKey_(true);
			this.setPkMapKey_(false);
			this.setCustomMapKey_(false);
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
		return this.getMapKeyAnnotation() == null;
	}


	// ********** pk map key **********

	public boolean isPkMapKey() {
		return this.pkMapKey;
	}

	public void setPkMapKey(boolean pkMapKey) {
		if (pkMapKey) {
			MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
			if (mapKeyAnnotation == null) {
				mapKeyAnnotation = this.addMapKeyAnnotation();
			} else {
				mapKeyAnnotation.setName(null);
			}

			this.setSpecifiedMapKey_(null);
			this.setNoMapKey_(false);
			this.setPkMapKey_(true);
			this.setCustomMapKey_(false);
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
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		return (mapKeyAnnotation != null) && (mapKeyAnnotation.getName() == null);
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
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		return (mapKeyAnnotation != null) && (mapKeyAnnotation.getName() != null);
	}


	// ********** map key annotation **********

	protected MapKeyAnnotation getMapKeyAnnotation() {
		return (MapKeyAnnotation) this.getResourceAttribute().getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected MapKeyAnnotation getMapKeyAnnotationForUpdate() {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		return (mapKeyAnnotation != null) ? mapKeyAnnotation : this.addMapKeyAnnotation();
	}

	protected MapKeyAnnotation addMapKeyAnnotation() {
		return (MapKeyAnnotation) this.getResourceAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyAnnotation() {
		this.getResourceAttribute().removeAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}


	// ********** map key class **********

	public String getMapKeyClass() {
		return (this.specifiedMapKeyClass != null) ? this.specifiedMapKeyClass : this.defaultMapKeyClass;
	}

	public String getSpecifiedMapKeyClass() {
		return this.specifiedMapKeyClass;
	}

	public void setSpecifiedMapKeyClass(String mapKeyClass) {
		if (this.valuesAreDifferent(mapKeyClass, this.specifiedMapKeyClass)) {
			MapKeyClass2_0Annotation annotation = this.getMapKeyClassAnnotation();
			if (mapKeyClass == null) {
				if (annotation != null) {
					this.removeMapKeyClassAnnotation();
				}
			} else {
				if (annotation == null) {
					annotation = this.addMapKeyClassAnnotation();
				}
				annotation.setValue(mapKeyClass);
			}

			this.setSpecifiedMapKeyClass_(mapKeyClass);
		}
	}

	protected void setSpecifiedMapKeyClass_(String mapKeyClass) {
		String old = this.specifiedMapKeyClass;
		this.specifiedMapKeyClass = mapKeyClass;
		this.firePropertyChanged(SPECIFIED_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected String buildSpecifiedMapKeyClass() {
		MapKeyClass2_0Annotation annotation = this.getMapKeyClassAnnotation();
		return (annotation == null) ? null : annotation.getValue();
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
		return this.isJpa2_0Compatible() ? this.getPersistentAttribute().getMultiReferenceMapKeyTypeName() : null;
	}

	public String getFullyQualifiedMapKeyClass() {
		return this.fullyQualifiedMapKeyClass;
	}

	protected void setFullyQualifiedMapKeyClass(String mapKeyClass) {
		String old = this.fullyQualifiedMapKeyClass;
		this.fullyQualifiedMapKeyClass = mapKeyClass;
		this.firePropertyChanged(FULLY_QUALIFIED_MAP_KEY_CLASS_PROPERTY, old, mapKeyClass);
	}

	protected String buildFullyQualifiedMapKeyClass() {
		return this.isJpa2_0Compatible() ? this.buildFullyQualifiedMapKeyClass_() : null;
	}

	protected String buildFullyQualifiedMapKeyClass_() {
		return (this.specifiedMapKeyClass == null) ?
				this.defaultMapKeyClass :
				this.getMapKeyClassAnnotation().getFullyQualifiedClassName();
	}

	public char getMapKeyClassEnclosingTypeSeparator() {
		return '.';
	}


	// ********** resolved map key embeddable/entity **********

	protected Embeddable getResolvedMapKeyEmbeddable() {
		return this.getPersistenceUnit().getEmbeddable(this.fullyQualifiedMapKeyClass);
	}

	protected Entity getResolvedMapKeyEntity() {
		return this.getPersistenceUnit().getEntity(this.fullyQualifiedMapKeyClass);
	}

	// ********** map key class annotation **********

	protected MapKeyClass2_0Annotation getMapKeyClassAnnotation() {
		return this.isJpa2_0Compatible() ? this.getMapKeyClassAnnotation_() : null;
	}

	protected MapKeyClass2_0Annotation getMapKeyClassAnnotation_() {
		return (MapKeyClass2_0Annotation) this.getResourceAttribute().getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
	}

	protected MapKeyClass2_0Annotation addMapKeyClassAnnotation() {
		return (MapKeyClass2_0Annotation) this.getResourceAttribute().addAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyClassAnnotation() {
		this.getResourceAttribute().removeAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
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
		return this.getPersistenceUnit().getEmbeddable(this.fullyQualifiedTargetEntity);
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

	public JavaColumn getMapKeyColumn() {
		return this.mapKeyColumn;
	}

	protected JavaColumn buildMapKeyColumn() {
		return this.isJpa2_0Compatible() ?
				this.getJpaFactory2_0().buildJavaMapKeyColumn(this, this.buildMapKeyColumnOwner()) :
				new NullJavaMapKeyColumn2_0(this);
	}

	protected JavaColumn.Owner buildMapKeyColumnOwner() {
		return new MapKeyColumnOwner();
	}

	protected MapKeyColumn2_0Annotation getMapKeyColumnAnnotation() {
		return this.isJpa2_0Compatible() ? this.getMapKeyColumnAnnotation_() : null;
	}

	protected MapKeyColumn2_0Annotation getMapKeyColumnAnnotation_() {
		return (MapKeyColumn2_0Annotation) this.getResourceAttribute().getNonNullAnnotation(MapKeyColumn2_0Annotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyColumnAnnotation() {
		if (this.isJpa2_0Compatible()) {
			this.removeMapKeyColumnAnnotation_();
		} else {
			throw new IllegalStateException();
		}
	}

	protected void removeMapKeyColumnAnnotation_() {
		this.getResourceAttribute().removeAnnotation(MapKeyColumn2_0Annotation.ANNOTATION_NAME);
	}


	// ********** map key converter **********

	public JavaConverter getMapKeyConverter() {
		return this.mapKeyConverter;
	}

	public void setMapKeyConverter(Class<? extends Converter> converterType) {
		if (this.mapKeyConverter.getType() != converterType) {
			this.mapKeyConverter.dispose();
			JavaConverter.Adapter converterAdapter = this.getKeyConverterAdapter(converterType);
			this.retainMapKeyConverterAnnotation(converterAdapter);
			this.setMapKeyConverter_(this.buildMapKeyConverter(converterAdapter));
		}
	}

	protected JavaConverter buildMapKeyConverter(JavaConverter.Adapter converterAdapter) {
		 return (converterAdapter != null) ?
				converterAdapter.buildNewConverter(this, this.getJpaFactory()) :
				this.buildNullMapKeyConverter();
	}

	protected void setMapKeyConverter_(JavaConverter mapKeyConverter) {
		Converter old = this.mapKeyConverter;
		this.mapKeyConverter = mapKeyConverter;
		this.firePropertyChanged(MAP_KEY_CONVERTER_PROPERTY, old, mapKeyConverter);
	}

	/**
	 * Clear all the converter annotations <em>except</em> for the annotation
	 * corresponding to the specified adapter. If the specified adapter is
	 * <code>null</code>, remove <em>all</em> the converter annotations.
	 */
	protected void retainMapKeyConverterAnnotation(JavaConverter.Adapter converterAdapter) {
		JavaResourceAttribute resourceAttribute = this.getResourceAttribute();
		for (JavaConverter.Adapter adapter : this.getMapKeyConverterAdapters()) {
			if (adapter != converterAdapter) {
				adapter.removeConverterAnnotation(resourceAttribute);
			}
		}
	}

	protected JavaConverter buildMapKeyConverter() {
		if (isJpa2_0Compatible()) {
			JpaFactory jpaFactory = this.getJpaFactory();
			for (JavaConverter.Adapter adapter : this.getMapKeyConverterAdapters()) {
				JavaConverter javaConverter = adapter.buildConverter(this, jpaFactory);
				if (javaConverter != null) {
					return javaConverter;
				}
			}
		}
		return this.buildNullMapKeyConverter();
	}

	protected void syncMapKeyConverter() {
		Association<JavaConverter.Adapter, Annotation> assoc = this.getMapKeyConverterAnnotation();
		if (assoc == null) {
			if (this.mapKeyConverter.getType() != null) {
				this.setMapKeyConverter_(this.buildNullMapKeyConverter());
			}
		} else {
			JavaConverter.Adapter adapter = assoc.getKey();
			Annotation annotation = assoc.getValue();
			if ((this.mapKeyConverter.getType() == adapter.getConverterType()) &&
					(this.mapKeyConverter.getConverterAnnotation() == annotation)) {
				this.mapKeyConverter.synchronizeWithResourceModel();
			} else {
				this.setMapKeyConverter_(adapter.buildConverter(annotation, this, this.getJpaFactory()));
			}
		}
	}

	/**
	 * Return the first converter annotation we find along with its corresponding
	 * adapter. Return <code>null</code> if there are no converter annotations.
	 */
	protected Association<JavaConverter.Adapter, Annotation> getMapKeyConverterAnnotation() {
		if (isJpa2_0Compatible()) {
			JavaResourceAttribute resourceAttribute = this.getResourceAttribute();
			for (JavaConverter.Adapter adapter : this.getMapKeyConverterAdapters()) {
				Annotation annotation = adapter.getConverterAnnotation(resourceAttribute);
				if (annotation != null) {
					return new SimpleAssociation<JavaConverter.Adapter, Annotation>(adapter, annotation);
				}
			}
		}
		return null;
	}

	protected JavaConverter buildNullMapKeyConverter() {
		return new NullJavaConverter(this);
	}


	// ********** map key converter adapters **********

	/**
	 * Return the converter adapter for the specified converter type.
	 */
	protected JavaConverter.Adapter getKeyConverterAdapter(Class<? extends Converter> converterType) {
		for (JavaConverter.Adapter adapter : this.getMapKeyConverterAdapters()) {
			if (adapter.getConverterType() == converterType) {
				return adapter;
			}
		}
		return null;
	}

	protected Iterable<JavaConverter.Adapter> getMapKeyConverterAdapters() {
		return MAP_KEY_CONVERTER_ADAPTERS;
	}


	// ********** map key join columns **********

	public ListIterable<JavaJoinColumn> getMapKeyJoinColumns() {
		return this.hasSpecifiedMapKeyJoinColumns() ? this.getSpecifiedMapKeyJoinColumns() : this.getDefaultMapKeyJoinColumns();
	}

	public int getMapKeyJoinColumnsSize() {
		return this.hasSpecifiedMapKeyJoinColumns() ? this.getSpecifiedMapKeyJoinColumnsSize() : this.getDefaultMapKeyJoinColumnsSize();
	}


	// ********** specified map key join columns **********

	public ListIterable<JavaJoinColumn> getSpecifiedMapKeyJoinColumns() {
		return this.specifiedMapKeyJoinColumnContainer.getContextElements();
	}

	public int getSpecifiedMapKeyJoinColumnsSize() {
		return this.specifiedMapKeyJoinColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedMapKeyJoinColumns() {
		return this.getSpecifiedMapKeyJoinColumnsSize() != 0;
	}

	public JavaJoinColumn getSpecifiedMapKeyJoinColumn(int index) {
		return this.specifiedMapKeyJoinColumnContainer.getContextElement(index);
	}

	public JavaJoinColumn addSpecifiedMapKeyJoinColumn() {
		return this.addSpecifiedMapKeyJoinColumn(this.getSpecifiedMapKeyJoinColumnsSize());
	}

	public JavaJoinColumn addSpecifiedMapKeyJoinColumn(int index) {
		MapKeyJoinColumn2_0Annotation annotation = this.addMapKeyJoinColumnAnnotation(index);
		return this.specifiedMapKeyJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedMapKeyJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedMapKeyJoinColumn(this.specifiedMapKeyJoinColumnContainer.indexOfContextElement((JavaJoinColumn) joinColumn));
	}

	public void removeSpecifiedMapKeyJoinColumn(int index) {
		this.removeMapKeyJoinColumnAnnotation(index);
		this.specifiedMapKeyJoinColumnContainer.removeContextElement(index);
	}

	public void moveSpecifiedMapKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.moveMapKeyJoinColumnAnnotation(targetIndex, sourceIndex);
		this.specifiedMapKeyJoinColumnContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected void syncSpecifiedMapKeyJoinColumns() {
		this.specifiedMapKeyJoinColumnContainer.synchronizeWithResourceModel();
	}

	protected ContextListContainer<JavaJoinColumn, MapKeyJoinColumn2_0Annotation> buildSpecifiedMapKeyJoinColumnContainer() {
		SpecifiedMapKeyJoinColumnContainer container = new SpecifiedMapKeyJoinColumnContainer();
		container.initialize();
		return container;
	}

	/**
	 * specified map key join column container
	 */
	protected class SpecifiedMapKeyJoinColumnContainer
		extends ContextListContainer<JavaJoinColumn, MapKeyJoinColumn2_0Annotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_MAP_KEY_JOIN_COLUMNS_LIST;
		}
		@Override
		protected JavaJoinColumn buildContextElement(MapKeyJoinColumn2_0Annotation resourceElement) {
			return AbstractJavaMultiRelationshipMapping.this.buildMapKeyJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<MapKeyJoinColumn2_0Annotation> getResourceElements() {
			return AbstractJavaMultiRelationshipMapping.this.getMapKeyJoinColumnAnnotations();
		}
		@Override
		protected MapKeyJoinColumn2_0Annotation getResourceElement(JavaJoinColumn contextElement) {
			return (MapKeyJoinColumn2_0Annotation) contextElement.getColumnAnnotation();
		}
	}

	protected ReadOnlyJoinColumn.Owner buildMapKeyJoinColumnOwner() {
		return new MapKeyJoinColumnOwner();
	}


	// ********** default map key join column **********

	public JavaJoinColumn getDefaultMapKeyJoinColumn() {
		return this.defaultMapKeyJoinColumn;
	}

	protected void setDefaultMapKeyJoinColumn(JavaJoinColumn joinColumn) {
		JavaJoinColumn old = this.defaultMapKeyJoinColumn;
		this.defaultMapKeyJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_MAP_KEY_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<JavaJoinColumn> getDefaultMapKeyJoinColumns() {
		return (this.defaultMapKeyJoinColumn != null) ?
				new SingleElementListIterable<JavaJoinColumn>(this.defaultMapKeyJoinColumn) :
				EmptyListIterable.<JavaJoinColumn>instance();
	}

	protected int getDefaultMapKeyJoinColumnsSize() {
		return (this.defaultMapKeyJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultMapKeyJoinColumn() {
		if (this.buildsDefaultMapKeyJoinColumn()) {
			if (this.defaultMapKeyJoinColumn == null) {
				this.setDefaultMapKeyJoinColumn(this.buildMapKeyJoinColumn(this.buildNullMapKeyJoinColumnAnnotation()));
			} else {
				this.defaultMapKeyJoinColumn.update();
			}
		} else {
			this.setDefaultMapKeyJoinColumn(null);
		}
	}

	protected boolean buildsDefaultMapKeyJoinColumn() {
		return 	isJpa2_0Compatible() &&
				!this.hasSpecifiedMapKeyJoinColumns() &&
				getKeyType() == Type.ENTITY_TYPE;
	}

	protected JavaJoinColumn buildMapKeyJoinColumn(MapKeyJoinColumn2_0Annotation joinColumnAnnotation) {
		return this.getJpaFactory().buildJavaJoinColumn(this, this.mapKeyJoinColumnOwner, joinColumnAnnotation);
	}

	// ********** map key join column annotations **********

	protected ListIterable<MapKeyJoinColumn2_0Annotation> getMapKeyJoinColumnAnnotations() {
		if (isJpa2_0Compatible()) {
			return new SubListIterableWrapper<NestableAnnotation, MapKeyJoinColumn2_0Annotation>(this.getNestableMapKeyJoinColumnAnnotations());
		}
		return EmptyListIterable.instance();
	}

	protected ListIterable<NestableAnnotation> getNestableMapKeyJoinColumnAnnotations() {
		return this.getResourceAttribute().getAnnotations(MapKeyJoinColumn2_0Annotation.ANNOTATION_NAME);
	}

	protected MapKeyJoinColumn2_0Annotation addMapKeyJoinColumnAnnotation(int index) {
		return (MapKeyJoinColumn2_0Annotation) this.getResourceAttribute().addAnnotation(index, MapKeyJoinColumn2_0Annotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyJoinColumnAnnotation(int index) {
		this.getResourceAttribute().removeAnnotation(index, MapKeyJoinColumn2_0Annotation.ANNOTATION_NAME);
	}

	protected void moveMapKeyJoinColumnAnnotation(int targetIndex, int sourceIndex) {
		this.getResourceAttribute().moveAnnotation(targetIndex, sourceIndex, MapKeyJoinColumn2_0Annotation.ANNOTATION_NAME);
	}

	protected MapKeyJoinColumn2_0Annotation buildNullMapKeyJoinColumnAnnotation() {
		return new NullMapKeyJoinColumnAnnotation(this.getResourceAttribute());
	}


	// ********** map key attribute override container **********

	public JavaAttributeOverrideContainer getMapKeyAttributeOverrideContainer() {
		return this.mapKeyAttributeOverrideContainer;
	}

	public JavaAttributeOverrideContainer buildMapKeyAttributeOverrideContainer() {
		return this.getJpaFactory().buildJavaAttributeOverrideContainer(this, this.buildMapKeyAttributeOverrideContainerOwner());
	}

	protected JavaAttributeOverrideContainer.Owner buildMapKeyAttributeOverrideContainerOwner() {
		return new MapKeyAttributeOverrideContainerOwner();
	}


	// ********** misc **********

	@Override
	protected String buildDefaultTargetEntity() {
		return this.getPersistentAttribute().getMultiReferenceTargetTypeName();
	}

	@Override
	protected FetchType buildDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}


	// ********** Java completion proposals **********

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
			return this.getJavaCandidateMapKeyNames();
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
		for (JavaJoinColumn joinColumn : this.getMapKeyJoinColumns()) {
			result = joinColumn.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	protected boolean mapKeyNameTouches(int pos) {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		return (mapKeyAnnotation != null) && mapKeyAnnotation.nameTouches(pos);
	}

	protected Iterable<String> getJavaCandidateMapKeyNames() {
		return new TransformationIterable<String, String>(this.getCandidateMapKeyNames(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	public Iterable<String> getCandidateMapKeyNames() {
		return this.getTargetEntityNonTransientAttributeNames();
	}


	// ********** metamodel **********

	@Override
	protected String getMetamodelFieldTypeName() {
		return ((PersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldTypeName();
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
		this.validateAttributeType(messages, reporter);
		this.validateMapKeyClass(messages);
		this.orderable.validate(messages, reporter);
		this.validateMapKey(messages, reporter);
	}

	protected void validateAttributeType(List<IMessage> messages, IReporter reporter) {
		JavaModifiablePersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		if ((javaAttribute != null) && !javaAttribute.getJpaContainerDefinition().isContainer()) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					this.buildValidationMessage(
						this.getVirtualPersistentAttributeTextRange(),
						JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_TYPE_IS_NOT_SUPPORTED_COLLECTION_TYPE,
						getName()
					)
				);
			}
			else {
				messages.add(
					this.buildValidationMessage(
						this.getValidationTextRange(),
						JptJpaCoreValidationMessages.ATTRIBUTE_TYPE_IS_NOT_SUPPORTED_COLLECTION_TYPE
					)
				);
			}
		}
	}

	protected TextRange getMapKeyClassTextRange() {
		return this.getValidationTextRange(this.getMapKeyClassAnnotationTextRange());
	}

	protected TextRange getMapKeyClassAnnotationTextRange() {
		MapKeyClass2_0Annotation annotation = this.getMapKeyClassAnnotation();
		return (annotation == null) ? null : annotation.getTextRange();
	}

	protected void validateMapKey(List<IMessage> messages, IReporter reporter) {
		if (this.getMapKeyAnnotation() != null) {//If MapKey annotation specified, no other MapKey* annotations can be specified, don't validate them
			//TODO validate that the map key refers to an existing attribute
			return;
		}
		if (this.getKeyType() == Type.BASIC_TYPE) {
			this.mapKeyColumn.validate(messages, reporter);
			this.mapKeyConverter.validate(messages, reporter);
		}
		else if (this.getKeyType() == Type.ENTITY_TYPE) {
			for (JavaJoinColumn joinColumn : this.getMapKeyJoinColumns()) {
				joinColumn.validate(messages, reporter);
			}
		}
		else if (this.getKeyType() == Type.EMBEDDABLE_TYPE) {
			this.mapKeyAttributeOverrideContainer.validate(messages, reporter);
			//validate map key association overrides - for eclipselink
		}
	}

	protected void validateMapKeyClass(List<IMessage> messages) {
		if (this.isJpa2_0Compatible() && this.getPersistentAttribute().getJpaContainerDefinition().isMap()) {
			this.validateMapKeyClass_(messages);
		}
	}

	protected void validateMapKeyClass_(List<IMessage> messages) {
		if (this.getMapKeyClass() == null) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					this.buildValidationMessage(
						this.getVirtualPersistentAttributeTextRange(),
						JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_CLASS_NOT_DEFINED,
						this.getName()
					)
				);
			} else {
				messages.add(
					this.buildValidationMessage(
						this.getValidationTextRange(),
						JptJpaCoreValidationMessages.MAP_KEY_CLASS_NOT_DEFINED
					)
				);
			}
			return;
		}

		if (MappingTools.typeIsBasic(this.getJavaProject(), this.getFullyQualifiedMapKeyClass())) {
			return;
		}

		if (this.getResolvedMapKeyEmbeddable() == null && this.getResolvedMapKeyEntity() == null) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					this.buildValidationMessage(
						this.getVirtualPersistentAttributeTextRange(),
						JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_CLASS_MUST_BE_ENTITY_EMBEDDABLE_OR_BASIC_TYPE,
						this.getName(),
						this.getFullyQualifiedMapKeyClass()
					)
				);
			}
			else {
				messages.add(
					this.buildValidationMessage(
						this.getMapKeyClassTextRange(),
						JptJpaCoreValidationMessages.MAP_KEY_CLASS_MUST_BE_ENTITY_EMBEDDABLE_OR_BASIC_TYPE,
						this.getFullyQualifiedMapKeyClass()
					)
				);
			}
		}
	}



	// ********** abstract owner **********

	/**
	 * some common behavior
	 */
	protected abstract class AbstractOwner
	{
		public TypeMapping getTypeMapping() {
			return AbstractJavaMultiRelationshipMapping.this.getTypeMapping();
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
			return AbstractJavaMultiRelationshipMapping.this.getValidationTextRange();
		}

		protected RelationshipStrategy getRelationshipStrategy() {
			return AbstractJavaMultiRelationshipMapping.this.getRelationship().getStrategy();
		}

		protected JavaModifiablePersistentAttribute getPersistentAttribute() {
			return AbstractJavaMultiRelationshipMapping.this.getPersistentAttribute();
		}
	}


	// ********** map key column owner **********

	protected class MapKeyColumnOwner
		extends AbstractOwner
		implements JavaColumn.Owner
	{
		public MapKeyColumn2_0Annotation getColumnAnnotation() {
			return AbstractJavaMultiRelationshipMapping.this.getMapKeyColumnAnnotation();
		}

		public void removeColumnAnnotation() {
			AbstractJavaMultiRelationshipMapping.this.removeMapKeyColumnAnnotation();
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return AbstractJavaMultiRelationshipMapping.this.getName() + "_KEY"; //$NON-NLS-1$
		}

		public boolean tableNameIsInvalid(String tableName) {
			return this.getRelationshipStrategy().tableNameIsInvalid(tableName);
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return new MapKeyColumnValidator(this.getPersistentAttribute(), (ReadOnlyBaseColumn) column, new RelationshipStrategyTableDescriptionProvider(this.getRelationshipStrategy()));
		}
	}


	// ********** map key attribute override owner **********

	protected class MapKeyAttributeOverrideContainerOwner
		extends AbstractOwner
		implements JavaAttributeOverrideContainer2_0.Owner
	{
		public JavaResourceAttribute getResourceMember() {
			return AbstractJavaMultiRelationshipMapping.this.getResourceAttribute();
		}

		public TypeMapping getOverridableTypeMapping() {
			return AbstractJavaMultiRelationshipMapping.this.getResolvedMapKeyEmbeddable();
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name.  the table is always the collection table
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return ObjectTools.notEquals(this.getDefaultTableName(), tableName);
		}

		public Iterable<String> getAllOverridableNames() {
			TypeMapping overriddenTypeMapping = this.getOverridableTypeMapping();
			return (overriddenTypeMapping != null) ? overriddenTypeMapping.getAllOverridableAttributeNames() : EmptyIterable.<String>instance();
		}

		protected static final String POSSIBLE_PREFIX = "key"; //$NON-NLS-1$
		public String getPossiblePrefix() {
			return POSSIBLE_PREFIX;
		}

		public String getWritePrefix() {
			return this.getPossiblePrefix();
		}

		// since only a map's key can be an embeddable on a 1-m or m-m;
		// all overrides are relevant
		public boolean isRelevant(String overrideName) {
			return true;
		}

		public Column resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideContainer container) {
			return new MapKeyAttributeOverrideValidator(this.getPersistentAttribute(), (ReadOnlyAttributeOverride) override, (AttributeOverrideContainer) container, new EmbeddableOverrideDescriptionProvider());
		}
		
		public JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner owner) {
			return new MapKeyAttributeOverrideColumnValidator(this.getPersistentAttribute(), (ReadOnlyAttributeOverride) override, column, new RelationshipStrategyTableDescriptionProvider(this.getRelationshipStrategy()));
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
			return AbstractJavaMultiRelationshipMapping.this.getRelationship().getStrategy().getTableName();
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return AbstractJavaMultiRelationshipMapping.this.getName() + "_KEY"; //$NON-NLS-1$
		}

		public String getAttributeName() {
			return AbstractJavaMultiRelationshipMapping.this.getName();
		}

		protected ModifiablePersistentAttribute getPersistentAttribute() {
			return AbstractJavaMultiRelationshipMapping.this.getPersistentAttribute();
		}

		public Entity getRelationshipTarget() {
			return AbstractJavaMultiRelationshipMapping.this.getResolvedMapKeyEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractJavaMultiRelationshipMapping.this.getRelationship().getStrategy().tableNameIsInvalid(tableName);
		}

		/**
		 * the map key join column can be on a secondary table
		 */
		public Iterable<String> getCandidateTableNames() {
			return AbstractJavaMultiRelationshipMapping.this.getTypeMapping().getAllAssociatedTableNames();
		}

		public Table resolveDbTable(String tableName) {
			return AbstractJavaMultiRelationshipMapping.this.getRelationship().getStrategy().resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			Entity entity = AbstractJavaMultiRelationshipMapping.this.getResolvedMapKeyEntity();
			return entity != null ? entity.getPrimaryDbTable() : null;
		}

		public int getJoinColumnsSize() {
			return AbstractJavaMultiRelationshipMapping.this.getMapKeyJoinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return AbstractJavaMultiRelationshipMapping.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return new MapKeyJoinColumnValidator(
				this.getPersistentAttribute(),
				(ReadOnlyJoinColumn) column,
				this,
				new RelationshipStrategyTableDescriptionProvider(getRelationship().getStrategy()));
		}
	}
}
