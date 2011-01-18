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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.JpaFactory;
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
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaEnumeratedConverter;
import org.eclipse.jpt.core.context.java.JavaLobConverter;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaTemporalConverter;
import org.eclipse.jpt.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAttributeMapping;
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
import org.eclipse.jpt.core.internal.jpa1.context.java.NullJavaConverter;
import org.eclipse.jpt.core.internal.jpa2.context.CollectionTableValidator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyClass2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyColumn2_0Annotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.CompleteColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.Association;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.SimpleAssociation;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * JPA 2.0 Frankenstein mapping
 */
public abstract class AbstractJavaElementCollectionMapping2_0
	extends AbstractJavaAttributeMapping<ElementCollection2_0Annotation>
	implements JavaElementCollectionMapping2_0
{
	protected String specifiedTargetClass;
	protected String defaultTargetClass;
	protected String fullyQualifiedTargetClass;

	protected FetchType specifiedFetch;
	protected FetchType defaultFetch;

	protected final JavaOrderable2_0 orderable;

	protected final JavaCollectionTable2_0 collectionTable;

	protected Type valueType;
	protected final JavaColumn valueColumn;
	protected JavaConverter converter;  // value converter - never null
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
	protected String fullyQualifiedMapKeyClass;

	protected final JavaColumn mapKeyColumn;

	protected final JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer;


	protected static final JavaConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new JavaConverter.Adapter[] {
		JavaEnumeratedConverter.Adapter.instance(),
		JavaTemporalConverter.Adapter.instance(),
		JavaLobConverter.Adapter.instance()
	};
	protected static final Iterable<JavaConverter.Adapter> CONVERTER_ADAPTERS = new ArrayIterable<JavaConverter.Adapter>(CONVERTER_ADAPTER_ARRAY);


	protected AbstractJavaElementCollectionMapping2_0(JavaPersistentAttribute parent) {
		super(parent);
		this.specifiedTargetClass = this.buildSpecifiedTargetClass();
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
		this.setSpecifiedTargetClass_(this.buildSpecifiedTargetClass());
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
		if (this.valuesAreDifferent(targetClass, this.specifiedTargetClass)) {
			this.getAnnotationForUpdate().setTargetClass(targetClass);
			this.setSpecifiedTargetClass_(targetClass);
		}
	}

	protected void setSpecifiedTargetClass_(String targetClass) {
		String old = this.specifiedTargetClass;
		this.specifiedTargetClass = targetClass;
		this.firePropertyChanged(SPECIFIED_TARGET_CLASS_PROPERTY, old, targetClass);
	}

	protected String buildSpecifiedTargetClass() {
		ElementCollection2_0Annotation annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getTargetClass();
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
				this.getMappingAnnotation().getFullyQualifiedTargetClassName();
	}

	public char getTargetClassEnclosingTypeSeparator() {
		return '.';
	}


	// ********** resolved target type/embeddable/entity **********

	public PersistentType getResolvedTargetType() {
		return this.getPersistenceUnit().getPersistentType(this.fullyQualifiedTargetClass);
	}

	protected Embeddable getResolvedTargetEmbeddable() {
		return this.getPersistenceUnit().getEmbeddable(this.fullyQualifiedTargetClass);
	}

	protected Entity getResolvedTargetEntity() {
		return this.getPersistenceUnit().getEntity(this.fullyQualifiedTargetClass);
	}


	// ********** fetch **********

	public FetchType getFetch() {
		return (this.specifiedFetch != null) ? this.specifiedFetch : this.defaultFetch;
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}

	public void setSpecifiedFetch(FetchType fetch) {
		if (this.valuesAreDifferent(fetch, this.specifiedFetch)) {
			this.getAnnotationForUpdate().setFetch(FetchType.toJavaResourceModel(fetch));
			this.setSpecifiedFetch_(fetch);
		}
	}

	protected void setSpecifiedFetch_(FetchType fetch) {
		FetchType old = this.specifiedFetch;
		this.specifiedFetch = fetch;
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
	}

	protected FetchType buildSpecifiedFetch() {
		ElementCollection2_0Annotation annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : FetchType.fromJavaResourceModel(annotation.getFetch());
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

	public JavaOrderable2_0 getOrderable() {
		return this.orderable;
	}

	protected JavaOrderable2_0 buildOrderable() {
		return this.getJpaFactory().buildJavaOrderable(this, this.buildOrderableOwner());
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
		protected JavaCollectionTable2_0 getCollectionTable() {
			return AbstractJavaElementCollectionMapping2_0.this.getCollectionTable();
		}
	}


	// ********** collection table **********

	public JavaCollectionTable2_0 getCollectionTable() {
		return this.collectionTable;
	}

	protected JavaCollectionTable2_0 buildCollectionTable() {
		return this.getJpaFactory().buildJavaCollectionTable(this, this.buildCollectionTableOwner());
	}

	protected Table.Owner buildCollectionTableOwner() {
		return new CollectionTableOwner();
	}

	protected class CollectionTableOwner
		implements Table.Owner
	{
		public JptValidator buildTableValidator(Table table, TableTextRangeResolver textRangeResolver) {
			return new CollectionTableValidator((CollectionTable2_0) table, textRangeResolver);
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
		if (this.getResolvedTargetEntity() != null) {
			return Type.ENTITY_TYPE;
		}
		if (this.getTargetClass() == null) {
			return Type.NO_TYPE;
		}
		return Type.BASIC_TYPE;
	}


	// ********** value column **********

	public JavaColumn getValueColumn() {
		return this.valueColumn;
	}

	protected JavaColumn buildValueColumn() {
		return this.getJpaFactory().buildJavaColumn(this, this.buildValueColumnOwner());
	}

	protected JavaColumn.Owner buildValueColumnOwner() {
		return new ValueColumnOwner();
	}

	protected ColumnAnnotation getValueColumnAnnotation() {
		return (ColumnAnnotation) this.getResourcePersistentAttribute().getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	protected void removeValueColumnAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}


	// ********** converter **********

	public JavaConverter getConverter() {
		return this.converter;
	}

	public void setConverter(Class<? extends Converter> converterType) {
		if (this.converter.getType() != converterType) {
			this.converter.dispose();
			JavaConverter.Adapter converterAdapter = this.getConverterAdapter(converterType);
			this.retainConverterAnnotation(converterAdapter);
			this.setConverter_(this.buildConverter(converterAdapter));
		}
	}

	protected JavaConverter buildConverter(JavaConverter.Adapter converterAdapter) {
		 return (converterAdapter != null) ?
				converterAdapter.buildNewConverter(this, this.getJpaFactory()) :
				this.buildNullConverter();
	}

	protected void setConverter_(JavaConverter converter) {
		Converter old = this.converter;
		this.converter = converter;
		this.firePropertyChanged(CONVERTER_PROPERTY, old, converter);
	}

	/**
	 * Clear all the converter annotations <em>except</em> for the annotation
	 * corresponding to the specified adapter. If the specified adapter is
	 * <code>null</code>, remove <em>all</em> the converter annotations.
	 */
	protected void retainConverterAnnotation(JavaConverter.Adapter converterAdapter) {
		JavaResourcePersistentAttribute resourceAttribute = this.getResourcePersistentAttribute();
		for (JavaConverter.Adapter adapter : this.getConverterAdapters()) {
			if (adapter != converterAdapter) {
				adapter.removeConverterAnnotation(resourceAttribute);
			}
		}
	}

	protected JavaConverter buildConverter() {
		JpaFactory jpaFactory = this.getJpaFactory();
		for (JavaConverter.Adapter adapter : this.getConverterAdapters()) {
			JavaConverter javaConverter = adapter.buildConverter(this, jpaFactory);
			if (javaConverter != null) {
				return javaConverter;
			}
		}
		return this.buildNullConverter();
	}

	protected void syncConverter() {
		Association<JavaConverter.Adapter, Annotation> assoc = this.getConverterAnnotation();
		if (assoc == null) {
			if (this.converter.getType() != null) {
				this.setConverter_(this.buildNullConverter());
			}
		} else {
			JavaConverter.Adapter adapter = assoc.getKey();
			Annotation annotation = assoc.getValue();
			if ((this.converter.getType() == adapter.getConverterType()) &&
					(this.converter.getConverterAnnotation() == annotation)) {
				this.converter.synchronizeWithResourceModel();
			} else {
				this.setConverter_(adapter.buildConverter(annotation, this, this.getJpaFactory()));
			}
		}
	}

	/**
	 * Return the first converter annotation we find along with its corresponding
	 * adapter. Return <code>null</code> if there are no converter annotations.
	 */
	protected Association<JavaConverter.Adapter, Annotation> getConverterAnnotation() {
		JavaResourcePersistentAttribute resourceAttribute = this.getResourcePersistentAttribute();
		for (JavaConverter.Adapter adapter : this.getConverterAdapters()) {
			Annotation annotation = adapter.getConverterAnnotation(resourceAttribute);
			if (annotation != null) {
				return new SimpleAssociation<JavaConverter.Adapter, Annotation>(adapter, annotation);
			}
		}
		return null;
	}

	protected JavaConverter buildNullConverter() {
		return new NullJavaConverter(this);
	}


	// ********** converter adapters **********

	/**
	 * Return the converter adapter for the specified converter type.
	 */
	protected JavaConverter.Adapter getConverterAdapter(Class<? extends Converter> converterType) {
		for (JavaConverter.Adapter adapter : this.getConverterAdapters()) {
			if (adapter.getConverterType() == converterType) {
				return adapter;
			}
		}
		return null;
	}

	protected Iterable<JavaConverter.Adapter> getConverterAdapters() {
		return CONVERTER_ADAPTERS;
	}


	// ********** value attribute override container **********

	public JavaAttributeOverrideContainer getValueAttributeOverrideContainer() {
		return this.valueAttributeOverrideContainer;
	}

	protected JavaAttributeOverrideContainer buildValueAttributeOverrideContainer() {
		return this.getJpaFactory().buildJavaAttributeOverrideContainer(this, this.buildValueAttributeOverrideContainerOwner());
	}

	protected JavaAttributeOverrideContainer.Owner buildValueAttributeOverrideContainerOwner() {
		return new ValueAttributeOverrideContainerOwner();
	}


	// ********** value association override container **********

	public JavaAssociationOverrideContainer getValueAssociationOverrideContainer() {
		return this.valueAssociationOverrideContainer;
	}

	protected JavaAssociationOverrideContainer buildValueAssociationOverrideContainer() {
		return this.getJpaFactory().buildJavaAssociationOverrideContainer(this, this.buildValueAssociationOverrideContainerOwner());
	}

	protected JavaAssociationOverrideContainer.Owner buildValueAssociationOverrideContainerOwner() {
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
		return (MapKeyAnnotation) this.getResourcePersistentAttribute().getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected MapKeyAnnotation getMapKeyAnnotationForUpdate() {
		MapKeyAnnotation annotation = this.getMapKeyAnnotation();
		return (annotation != null ) ? annotation : this.addMapKeyAnnotation();
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
		return this.getPersistentAttribute().getMultiReferenceMapKeyTypeName();
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
		return (MapKeyClass2_0Annotation) this.getResourcePersistentAttribute().getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
	}

	protected MapKeyClass2_0Annotation addMapKeyClassAnnotation() {
		return (MapKeyClass2_0Annotation) this.getResourcePersistentAttribute().addAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyClassAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
	}


	// ********** map key column **********

	public JavaColumn getMapKeyColumn() {
		return this.mapKeyColumn;
	}

	protected JavaColumn buildMapKeyColumn() {
		return this.getJpaFactory().buildJavaMapKeyColumn(this, this.buildMapKeyColumnOwner());
	}

	protected JavaColumn.Owner buildMapKeyColumnOwner() {
		return new MapKeyColumnOwner();
	}

	protected MapKeyColumn2_0Annotation getMapKeyColumnAnnotation() {
		return (MapKeyColumn2_0Annotation) this.getResourcePersistentAttribute().getNonNullAnnotation(MapKeyColumn2_0Annotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyColumnAnnotation() {
		this.getResourcePersistentAttribute().removeAnnotation(MapKeyColumn2_0Annotation.ANNOTATION_NAME);
	}


	// ********** map key attribute override container **********

	public JavaAttributeOverrideContainer getMapKeyAttributeOverrideContainer() {
		return this.mapKeyAttributeOverrideContainer;
	}

	protected JavaAttributeOverrideContainer buildMapKeyAttributeOverrideContainer() {
		return this.getJpaFactory().buildJavaAttributeOverrideContainer(this, this.buildMapKeyAttributeOverrideContainerOwner());
	}

	protected JavaAttributeOverrideContainer.Owner buildMapKeyAttributeOverrideContainerOwner() {
		return new MapKeyAttributeOverrideContainerOwner();
	}


	// ********** embedded mappings **********

	public Iterator<String> candidateMapKeyNames() {
		return this.allTargetEmbeddableAttributeNames();
	}

	protected Iterator<String> allTargetEmbeddableAttributeNames() {
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
	public RelationshipReference resolveOverriddenRelationship(String attributeName) {
		attributeName = this.unqualify(attributeName);
		if (attributeName == null) {
			return null;
		}
		AssociationOverride override = this.valueAssociationOverrideContainer.getSpecifiedOverrideNamed(attributeName);
		// recurse into the target embeddable if necessary
		return (override != null) ? override.getRelationshipReference() :  this.resolveRelationshipInTargetEmbeddable(attributeName);
	}

	protected RelationshipReference resolveRelationshipInTargetEmbeddable(String attributeName) {
		Embeddable targetEmbeddable = this.getResolvedTargetEmbeddable();
		return (targetEmbeddable == null) ? null : targetEmbeddable.resolveOverriddenRelationship(attributeName);
	}


	// ********** misc **********

	@Override
	protected JpaFactory2_0 getJpaFactory() {
		return (JpaFactory2_0) super.getJpaFactory();
	}

	public String getKey() {
		return MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return ElementCollection2_0Annotation.ANNOTATION_NAME;
	}

	public Entity getEntity() {
		TypeMapping typeMapping = this.getTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.collectionTable.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.valueColumn.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.converter.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.orderable.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.valueAttributeOverrideContainer.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.valueAssociationOverrideContainer.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.mapKeyNameTouches(pos, astRoot)) {
			return this.javaCandidateMapKeyNames(filter);
		}
		result = this.mapKeyColumn.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.mapKeyAttributeOverrideContainer.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}

	protected Iterator<String> javaCandidateMapKeyNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateMapKeyNames(filter));
	}

	protected Iterator<String> candidateMapKeyNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateMapKeyNames(), filter);
	}


	// ********** metamodel **********

	@Override
	protected String getMetamodelFieldTypeName() {
		return ((JavaPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	public String getMetamodelTypeName() {
		return (this.fullyQualifiedTargetClass != null) ? this.fullyQualifiedTargetClass : MetamodelField.DEFAULT_TYPE_NAME;
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
		this.orderable.validate(messages, reporter, astRoot);
		this.collectionTable.validate(messages, reporter, astRoot);
		this.validateValue(messages, reporter, astRoot);
		this.validateMapKey(messages, reporter, astRoot);
	}

	protected void validateTargetClass(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getTargetClass() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED,
					EMPTY_STRING_ARRAY,
					this,
					this.getValidationTextRange(astRoot)
				)
			);
		}
		//TODO this does not give an error for unmapped, unlisted types that aren't basic - bug 310464
		if (this.getResolvedTargetType() != null) {
			if (this.getResolvedTargetEmbeddable() == null) {
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

	protected TextRange getTargetClassTextRange(CompilationUnit astRoot) {
		ElementCollection2_0Annotation annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : this.getTextRange(annotation.getTargetClassTextRange(astRoot), astRoot);
	}

	protected TextRange getTextRange(TextRange textRange, CompilationUnit astRoot) {
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

	protected void validateMapKeyClass(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getPersistentAttribute().getJpaContainerDefinition().isMap()) {
			this.validateMapKeyClass_(messages, astRoot);
		}
	}

	protected void validateMapKeyClass_(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getMapKeyClass() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.ELEMENT_COLLECTION_MAP_KEY_CLASS_NOT_DEFINED,
					EMPTY_STRING_ARRAY,
					this,
					this.getValidationTextRange(astRoot)
				)
			);
		}
	}

	protected void validateValue(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		//TODO should we handle validation when the type is embeddable,
		//but a value column is specified, or things like that if that is invalid?
		switch (this.valueType) {
			case BASIC_TYPE :
				this.valueColumn.validate(messages, reporter, astRoot);
				this.converter.validate(messages, reporter, astRoot);
				break;
			case EMBEDDABLE_TYPE :
				this.valueAttributeOverrideContainer.validate(messages, reporter, astRoot);
				this.valueAssociationOverrideContainer.validate(messages, reporter, astRoot);
				break;
			default :
				break;
		}
	}

	protected void validateMapKey(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if (this.getMapKey() == null) {
			this.validateMapKey_(messages, reporter, astRoot);
		} else {
			//TODO validate that the map key refers to an existing attribute
		}
	}

	protected void validateMapKey_(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		switch (this.keyType) {
			case BASIC_TYPE :
				this.mapKeyColumn.validate(messages, reporter, astRoot);
				//validate map key converter
				break;
			case ENTITY_TYPE :
				//validate map key join columns
				break;
			case EMBEDDABLE_TYPE :
				this.mapKeyAttributeOverrideContainer.validate(messages, reporter, astRoot);
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
		public JavaResourcePersistentMember getResourcePersistentMember() {
			return AbstractJavaElementCollectionMapping2_0.this.getResourcePersistentAttribute();
		}

		public TypeMapping getTypeMapping() {
			return AbstractJavaElementCollectionMapping2_0.this.getTypeMapping();
		}

		public String getDefaultTableName() {
			return this.getCollectionTable().getName();
		}

		public org.eclipse.jpt.db.Table resolveDbTable(String tableName) {
			JavaCollectionTable2_0 table = this.getCollectionTable();
			return Tools.valuesAreEqual(table.getName(), tableName) ? table.getDbTable() : null;
		}

		public java.util.Iterator<String> candidateTableNames() {
			return EmptyIterator.instance();
		}

		/**
		 * If there is a specified table name it needs to be the same
		 * the default table name.  the table is always the collection table
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return Tools.valuesAreDifferent(this.getDefaultTableName(), tableName);
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return AbstractJavaElementCollectionMapping2_0.this.getValidationTextRange(astRoot);
		}

		protected String getMappingName() {
			return AbstractJavaElementCollectionMapping2_0.this.getName();
		}

		protected JavaCollectionTable2_0 getCollectionTable() {
			return AbstractJavaElementCollectionMapping2_0.this.getCollectionTable();
		}
	}


	// ********** value column owner **********

	protected class ValueColumnOwner
		extends AbstractOwner
		implements JavaColumn.Owner
	{
		public CompleteColumnAnnotation getColumnAnnotation() {
			return AbstractJavaElementCollectionMapping2_0.this.getValueColumnAnnotation();
		}

		public void removeColumnAnnotation() {
			AbstractJavaElementCollectionMapping2_0.this.removeValueColumnAnnotation();
		}

		public String getDefaultColumnName() {
			return this.getMappingName();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new NamedColumnValidator((BaseColumn) column, (BaseColumnTextRangeResolver) textRangeResolver, new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** map key column owner **********

	protected class MapKeyColumnOwner
		extends AbstractOwner
		implements JavaColumn.Owner
	{
		public MapKeyColumn2_0Annotation getColumnAnnotation() {
			return AbstractJavaElementCollectionMapping2_0.this.getMapKeyColumnAnnotation();
		}

		public void removeColumnAnnotation() {
			AbstractJavaElementCollectionMapping2_0.this.removeMapKeyColumnAnnotation();
		}

		public String getDefaultColumnName() {
			return this.getMappingName() + "_KEY"; //$NON-NLS-1$
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new MapKeyColumnValidator((BaseColumn) column, (BaseColumnTextRangeResolver) textRangeResolver, new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** value override container owner **********

	protected abstract class ValueOverrideContainerOwner
		extends AbstractOwner
	{
		protected static final String POSSIBLE_PREFIX = "value"; //$NON-NLS-1$

		public TypeMapping getOverridableTypeMapping() {
			return AbstractJavaElementCollectionMapping2_0.this.getResolvedTargetEmbeddable();
		}

		public String getPossiblePrefix() {
			return POSSIBLE_PREFIX;
		}

		public String getWritePrefix() {
			return this.getPersistentAttribute().getJpaContainerDefinition().isMap() ? this.getPossiblePrefix() : null;
		}

		//return false if the override is prefixed with "key.", these will be part of the MapKeyAttributeOverrideContainer.
		//a prefix of "value." or no prefix at all is relevant. If the type is not a Map then return true since all attribute overrides
		//need to apply to the value.
		public boolean isRelevant(String overrideName) {
			if (AbstractJavaElementCollectionMapping2_0.this.getKeyType() != Type.EMBEDDABLE_TYPE) {
				return true;
			}
			return ! overrideName.startsWith(MapKeyAttributeOverrideContainerOwner.RELEVANT_PREFIX_);
		}

		protected JavaPersistentAttribute getPersistentAttribute() {
			return AbstractJavaElementCollectionMapping2_0.this.getPersistentAttribute();
		}

		public JptValidator buildValidator(Override_ override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new AssociationOverrideValidator((AssociationOverride) override, (AssociationOverrideContainer) container, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner columnOwner, BaseColumnTextRangeResolver textRangeResolver) {
			return new AssociationOverrideJoinColumnValidator((AssociationOverride) override, (JoinColumn) column, (JoinColumn.Owner) columnOwner, (JoinColumnTextRangeResolver) textRangeResolver, new CollectionTableTableDescriptionProvider());
		}

		public JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
			throw new UnsupportedOperationException("An element collection containing a nested relationship mapping using a JoinTable is not supported"); //$NON-NLS-1$
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
		extends ValueOverrideContainerOwner
		implements JavaAttributeOverrideContainer.Owner
	{
		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = this.getOverridableTypeMapping();
			return (typeMapping != null) ? typeMapping.allOverridableAttributeNames() : EmptyIterator.<String>instance();
		}

		public Column resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		@Override
		public JptValidator buildValidator(Override_ override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new AttributeOverrideValidator((AttributeOverride) override, (AttributeOverrideContainer) container, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}
		
		@Override
		public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner columnOwner, BaseColumnTextRangeResolver textRangeResolver) {
			return new AttributeOverrideColumnValidator((AttributeOverride) override, column, textRangeResolver, new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** value association override container owner **********

	protected class ValueAssociationOverrideContainerOwner
		extends ValueOverrideContainerOwner
		implements JavaAssociationOverrideContainer.Owner
	{
		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = this.getOverridableTypeMapping();
			return (typeMapping != null) ? typeMapping.allOverridableAssociationNames() : EmptyIterator.<String>instance();
		}

		public RelationshipReference resolveOverriddenRelationship(String attributeName) {
			return MappingTools.resolveOverriddenRelationship(this.getOverridableTypeMapping(), attributeName);
		}

		@Override
		public JptValidator buildValidator(Override_ override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new AssociationOverrideValidator((AssociationOverride) override, (AssociationOverrideContainer) container, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}

		@Override
		public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner columnOwner, BaseColumnTextRangeResolver textRangeResolver) {
			return new AssociationOverrideJoinColumnValidator((AssociationOverride) override, (JoinColumn) column, (JoinColumn.Owner) columnOwner, (JoinColumnTextRangeResolver) textRangeResolver, new CollectionTableTableDescriptionProvider());
		}

		@Override
		public JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
			throw new UnsupportedOperationException("An element collection containing a nested relationship mapping using a JoinTable is not supported"); //$NON-NLS-1$
		}

		@Override
		public JptValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
			throw new UnsupportedOperationException("An element collection containing a nested relationship mapping using a JoinTable is not supported"); //$NON-NLS-1$
		}

		@Override
		public JptValidator buildTableValidator(AssociationOverride override, Table table, TableTextRangeResolver textRangeResolver) {
			throw new UnsupportedOperationException("An element collection containing a nested relationship mapping using a JoinTable is not supported"); //$NON-NLS-1$
		}
	}


	// ********** map key attribute override container owner **********

	protected class MapKeyAttributeOverrideContainerOwner
		extends AbstractOwner
		implements JavaAttributeOverrideContainer.Owner
	{
		protected static final String POSSIBLE_PREFIX = "key"; //$NON-NLS-1$
		protected static final String RELEVANT_PREFIX_ = "key."; //$NON-NLS-1$

		public TypeMapping getOverridableTypeMapping() {
			return AbstractJavaElementCollectionMapping2_0.this.getResolvedMapKeyEmbeddable();
		}

		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = this.getOverridableTypeMapping();
			return (typeMapping != null) ? typeMapping.allOverridableAttributeNames() : EmptyIterator.<String>instance();
		}

		public String getPossiblePrefix() {
			return POSSIBLE_PREFIX;
		}

		public String getWritePrefix() {
			return this.getPossiblePrefix();
		}

		// the only relevant overrides are those that start with "key.";
		// no prefix will be a value attribute override
		public boolean isRelevant(String overrideName) {
			if (AbstractJavaElementCollectionMapping2_0.this.getValueType() != Type.EMBEDDABLE_TYPE) {
				return true;
			}
			return overrideName.startsWith(RELEVANT_PREFIX_);
		}

		public Column resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildValidator(Override_ override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new MapKeyAttributeOverrideValidator((AttributeOverride) override, (AttributeOverrideContainer) container, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}
		
		public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner columnOwner, BaseColumnTextRangeResolver textRangeResolver) {
			return new MapKeyAttributeOverrideColumnValidator((AttributeOverride) override, column, textRangeResolver, new CollectionTableTableDescriptionProvider());
		}
	}
}
