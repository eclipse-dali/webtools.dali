/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
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
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.BaseEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.SpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationship;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.TableColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaLobConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.NullJpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaAttributeMapping;
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
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.NullJavaConverter;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.CollectionTableValidator;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.MapKeyJoinColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.NullMapKeyJoinColumnAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAttributeOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollectionAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyClassAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyColumnAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyJoinColumnAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.CompleteColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * JPA 2.0 Frankenstein mapping
 */
public abstract class AbstractJavaElementCollectionMapping2_0
	extends AbstractJavaAttributeMapping<ElementCollectionAnnotation2_0>
	implements JavaElementCollectionMapping2_0
{
	protected String specifiedTargetClass;
	protected String defaultTargetClass;
	protected String fullyQualifiedTargetClass;

	protected FetchType specifiedFetch;
	protected FetchType defaultFetch = DEFAULT_FETCH_TYPE;

	protected final JavaOrderable2_0 orderable;

	protected final JavaCollectionTable2_0 collectionTable;

	protected Type valueType;
	protected final JavaSpecifiedColumn valueColumn;
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

	protected final JavaSpecifiedColumn mapKeyColumn;
	protected JavaConverter mapKeyConverter;  // map key converter - never null

	protected final JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer;

	protected final ContextListContainer<JavaSpecifiedJoinColumn, MapKeyJoinColumnAnnotation2_0> specifiedMapKeyJoinColumnContainer;
	protected final JoinColumn.ParentAdapter mapKeyJoinColumnParentAdapter;

	protected JavaSpecifiedJoinColumn defaultMapKeyJoinColumn;

	protected static final JavaConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new JavaConverter.Adapter[] {
		JavaBaseEnumeratedConverter.BasicAdapter.instance(),
		JavaBaseTemporalConverter.ElementCollectionAdapter.instance(),
		JavaLobConverter.Adapter.instance()
	};
	protected static final Iterable<JavaConverter.Adapter> CONVERTER_ADAPTERS = IterableTools.iterable(CONVERTER_ADAPTER_ARRAY);


	protected static final JavaConverter.Adapter[] MAP_KEY_CONVERTER_ADAPTER_ARRAY = new JavaConverter.Adapter[] {
		JavaBaseEnumeratedConverter.MapKeyAdapter.instance(),
		JavaBaseTemporalConverter.MapKeyAdapter.instance()
	};
	protected static final Iterable<JavaConverter.Adapter> MAP_KEY_CONVERTER_ADAPTERS = IterableTools.iterable(MAP_KEY_CONVERTER_ADAPTER_ARRAY);


	protected AbstractJavaElementCollectionMapping2_0(JavaSpecifiedPersistentAttribute parent) {
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
		this.mapKeyConverter = this.buildMapKeyConverter();
		this.mapKeyAttributeOverrideContainer = this.buildMapKeyAttributeOverrideContainer();
		this.mapKeyJoinColumnParentAdapter = this.buildMapKeyJoinColumnParentAdapter();
		this.specifiedMapKeyJoinColumnContainer = this.buildSpecifiedMapKeyJoinColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedTargetClass_(this.buildSpecifiedTargetClass());
		this.setSpecifiedFetch_(this.buildSpecifiedFetch());
		this.orderable.synchronizeWithResourceModel(monitor);
		this.collectionTable.synchronizeWithResourceModel(monitor);

		this.valueColumn.synchronizeWithResourceModel(monitor);
		this.syncConverter(monitor);
		this.valueAttributeOverrideContainer.synchronizeWithResourceModel(monitor);
		this.valueAssociationOverrideContainer.synchronizeWithResourceModel(monitor);

		this.setSpecifiedMapKey_(this.buildSpecifiedMapKey());
		this.setNoMapKey_(this.buildNoMapKey());
		this.setPkMapKey_(this.buildPkMapKey());
		this.setCustomMapKey_(this.buildCustomMapKey());
		this.setSpecifiedMapKeyClass_(this.buildSpecifiedMapKeyClass());

		this.mapKeyColumn.synchronizeWithResourceModel(monitor);
		this.syncMapKeyConverter(monitor);
		this.mapKeyAttributeOverrideContainer.synchronizeWithResourceModel(monitor);
		this.syncSpecifiedMapKeyJoinColumns(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);

		this.setDefaultTargetClass(this.buildDefaultTargetClass());
		this.setFullyQualifiedTargetClass(this.buildFullyQualifiedTargetClass());
		this.setDefaultFetch(this.buildDefaultFetch());

		this.orderable.update(monitor);
		this.collectionTable.update(monitor);

		this.setValueType(this.buildValueType());
		this.valueColumn.update(monitor);
		this.converter.update(monitor);
		this.valueAttributeOverrideContainer.update(monitor);
		this.valueAssociationOverrideContainer.update(monitor);

		this.setKeyType(this.buildKeyType());
		this.setDefaultMapKeyClass(this.buildDefaultMapKeyClass());
		this.setFullyQualifiedMapKeyClass(this.buildFullyQualifiedMapKeyClass());

		this.mapKeyColumn.update(monitor);
		this.mapKeyConverter.update(monitor);
		this.mapKeyAttributeOverrideContainer.update(monitor);
		this.updateModels(this.getSpecifiedMapKeyJoinColumns(), monitor);
		this.updateDefaultMapKeyJoinColumn(monitor);
	}


	// ********** target class **********

	public String getTargetClass() {
		return (this.specifiedTargetClass != null) ? this.specifiedTargetClass : this.defaultTargetClass;
	}

	public String getSpecifiedTargetClass() {
		return this.specifiedTargetClass;
	}

	public void setSpecifiedTargetClass(String targetClass) {
		if (ObjectTools.notEquals(targetClass, this.specifiedTargetClass)) {
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
		ElementCollectionAnnotation2_0 annotation = this.getMappingAnnotation();
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
		if (ObjectTools.notEquals(fetch, this.specifiedFetch)) {
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
		ElementCollectionAnnotation2_0 annotation = this.getMappingAnnotation();
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
		return this.getJpaFactory().buildJavaOrderable(this.buildOrderableParentAdapter());
	}

	protected JavaOrderable2_0.ParentAdapter buildOrderableParentAdapter() {
		return new OrderableParentAdapter();
	}

	public class OrderableParentAdapter
		implements JavaOrderable2_0.ParentAdapter
	{
		public JavaAttributeMapping getOrderableParent() {
			return AbstractJavaElementCollectionMapping2_0.this;
		}
		public String getTableName() {
			return this.getCollectionTable().getName();
		}
		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
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
		return this.getJpaFactory().buildJavaCollectionTable(this.buildCollectionTableParentAdapter());
	}

	protected JavaCollectionTable2_0.ParentAdapter buildCollectionTableParentAdapter() {
		return new CollectionTableParentAdapter();
	}

	public class CollectionTableParentAdapter
		implements JavaCollectionTable2_0.ParentAdapter
	{
		public JavaElementCollectionMapping2_0 getTableParent() {
			return AbstractJavaElementCollectionMapping2_0.this;
		}
		public JpaValidator buildTableValidator(Table table) {
			return new CollectionTableValidator(AbstractJavaElementCollectionMapping2_0.this.getPersistentAttribute(), (CollectionTable2_0) table);
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

	public JavaSpecifiedColumn getValueColumn() {
		return this.valueColumn;
	}

	protected JavaSpecifiedColumn buildValueColumn() {
		return this.getJpaFactory().buildJavaColumn(this.buildValueColumnParentAdapter());
	}

	protected JavaSpecifiedColumn.ParentAdapter buildValueColumnParentAdapter() {
		return new ValueColumnParentAdapter();
	}

	protected ColumnAnnotation getValueColumnAnnotation() {
		return (ColumnAnnotation) this.getResourceAttribute().getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	protected void removeValueColumnAnnotation() {
		this.getResourceAttribute().removeAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}


	// ********** converter **********

	public JavaConverter getConverter() {
		return this.converter;
	}

	public void setConverter(Class<? extends Converter> converterType) {
		if (this.converter.getConverterType() != converterType) {
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
		JavaResourceAttribute resourceAttribute = this.getResourceAttribute();
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

	protected void syncConverter(IProgressMonitor monitor) {
		Association<JavaConverter.Adapter, Annotation> assoc = this.getConverterAnnotation();
		if (assoc == null) {
			if (this.converter.getConverterType() != null) {
				this.setConverter_(this.buildNullConverter());
			}
		} else {
			JavaConverter.Adapter adapter = assoc.getKey();
			Annotation annotation = assoc.getValue();
			if ((this.converter.getConverterType() == adapter.getConverterType()) &&
					(this.converter.getConverterAnnotation() == annotation)) {
				this.converter.synchronizeWithResourceModel(monitor);
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
		JavaResourceAttribute resourceAttribute = this.getResourceAttribute();
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
		return this.getJpaFactory().buildJavaAttributeOverrideContainer(this.buildValueAttributeOverrideContainerParentAdapter());
	}

	protected JavaAttributeOverrideContainer.ParentAdapter buildValueAttributeOverrideContainerParentAdapter() {
		return new ValueAttributeOverrideContainerParentAdapter();
	}


	// ********** value association override container **********

	public JavaAssociationOverrideContainer getValueAssociationOverrideContainer() {
		return this.valueAssociationOverrideContainer;
	}

	protected JavaAssociationOverrideContainer buildValueAssociationOverrideContainer() {
		return this.getJpaFactory().buildJavaAssociationOverrideContainer(this.buildValueAssociationOverrideContainerParentAdapter());
	}

	protected JavaAssociationOverrideContainer2_0.ParentAdapter buildValueAssociationOverrideContainerParentAdapter() {
		return new ValueAssociationOverrideContainerParentAdapter();
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
		return (MapKeyAnnotation) this.getResourceAttribute().getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected MapKeyAnnotation getMapKeyAnnotationForUpdate() {
		MapKeyAnnotation annotation = this.getMapKeyAnnotation();
		return (annotation != null ) ? annotation : this.addMapKeyAnnotation();
	}

	protected MapKeyAnnotation addMapKeyAnnotation() {
		return (MapKeyAnnotation) this.getResourceAttribute().addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyAnnotation() {
		this.getResourceAttribute().removeAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}

	protected boolean mapKeyNameTouches(int pos) {
		MapKeyAnnotation mapKeyAnnotation = this.getMapKeyAnnotation();
		return (mapKeyAnnotation != null) && mapKeyAnnotation.nameTouches(pos);
	}


	// ********** map key class **********

	public String getMapKeyClass() {
		return (this.specifiedMapKeyClass != null) ? this.specifiedMapKeyClass : this.defaultMapKeyClass;
	}

	public String getSpecifiedMapKeyClass() {
		return this.specifiedMapKeyClass;
	}

	public void setSpecifiedMapKeyClass(String mapKeyClass) {
		if (ObjectTools.notEquals(mapKeyClass, this.specifiedMapKeyClass)) {
			MapKeyClassAnnotation2_0 annotation = this.getMapKeyClassAnnotation();
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
		MapKeyClassAnnotation2_0 annotation = this.getMapKeyClassAnnotation();
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

	protected MapKeyClassAnnotation2_0 getMapKeyClassAnnotation() {
		return (MapKeyClassAnnotation2_0) this.getResourceAttribute().getAnnotation(MapKeyClassAnnotation2_0.ANNOTATION_NAME);
	}

	protected MapKeyClassAnnotation2_0 addMapKeyClassAnnotation() {
		return (MapKeyClassAnnotation2_0) this.getResourceAttribute().addAnnotation(MapKeyClassAnnotation2_0.ANNOTATION_NAME);
	}

	protected void removeMapKeyClassAnnotation() {
		this.getResourceAttribute().removeAnnotation(MapKeyClassAnnotation2_0.ANNOTATION_NAME);
	}


	// ********** map key column **********

	public JavaSpecifiedColumn getMapKeyColumn() {
		return this.mapKeyColumn;
	}

	protected JavaSpecifiedColumn buildMapKeyColumn() {
		return this.getJpaFactory().buildJavaMapKeyColumn(this.buildMapKeyColumnParentAdapter());
	}

	protected JavaSpecifiedColumn.ParentAdapter buildMapKeyColumnParentAdapter() {
		return new MapKeyColumnParentAdapter();
	}

	protected MapKeyColumnAnnotation2_0 getMapKeyColumnAnnotation() {
		return (MapKeyColumnAnnotation2_0) this.getResourceAttribute().getNonNullAnnotation(MapKeyColumnAnnotation2_0.ANNOTATION_NAME);
	}

	protected void removeMapKeyColumnAnnotation() {
		this.getResourceAttribute().removeAnnotation(MapKeyColumnAnnotation2_0.ANNOTATION_NAME);
	}

	// ********** map key converter **********

	public JavaConverter getMapKeyConverter() {
		return this.mapKeyConverter;
	}

	public void setMapKeyConverter(Class<? extends Converter> converterType) {
		if (this.mapKeyConverter.getConverterType() != converterType) {
			JavaConverter.Adapter converterAdapter = this.getMapKeyConverterAdapter(converterType);
			this.retainMapKeyConverterAnnotation(converterAdapter);
			this.setMapKeyConverter_(this.buildKeyConverter(converterAdapter));
		}
	}

	protected JavaConverter buildKeyConverter(JavaConverter.Adapter converterAdapter) {
		 return (converterAdapter != null) ?
				converterAdapter.buildNewConverter(this, this.getJpaFactory()) :
				this.buildNullConverter();
	}

	protected void setMapKeyConverter_(JavaConverter keyConverter) {
		Converter old = this.mapKeyConverter;
		this.mapKeyConverter = keyConverter;
		this.firePropertyChanged(MAP_KEY_CONVERTER_PROPERTY, old, keyConverter);
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
		JpaFactory jpaFactory = this.getJpaFactory();
		for (JavaConverter.Adapter adapter : this.getMapKeyConverterAdapters()) {
			JavaConverter javaConverter = adapter.buildConverter(this, jpaFactory);
			if (javaConverter != null) {
				return javaConverter;
			}
		}
		return this.buildNullConverter();
	}

	protected void syncMapKeyConverter(IProgressMonitor monitor) {
		Association<JavaConverter.Adapter, Annotation> assoc = this.getMapKeyConverterAnnotation();
		if (assoc == null) {
			if (this.mapKeyConverter.getConverterType() != null) {
				this.setMapKeyConverter_(this.buildNullConverter());
			}
		} else {
			JavaConverter.Adapter adapter = assoc.getKey();
			Annotation annotation = assoc.getValue();
			if ((this.mapKeyConverter.getConverterType() == adapter.getConverterType()) &&
					(this.mapKeyConverter.getConverterAnnotation() == annotation)) {
				this.mapKeyConverter.synchronizeWithResourceModel(monitor);
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
		JavaResourceAttribute resourceAttribute = this.getResourceAttribute();
		for (JavaConverter.Adapter adapter : this.getMapKeyConverterAdapters()) {
			Annotation annotation = adapter.getConverterAnnotation(resourceAttribute);
			if (annotation != null) {
				return new SimpleAssociation<JavaConverter.Adapter, Annotation>(adapter, annotation);
			}
		}
		return null;
	}


	// ********** map key converter adapters **********

	/**
	 * Return the converter adapter for the specified converter type.
	 */
	protected JavaConverter.Adapter getMapKeyConverterAdapter(Class<? extends Converter> converterType) {
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

	// ********** map key attribute override container **********

	public JavaAttributeOverrideContainer getMapKeyAttributeOverrideContainer() {
		return this.mapKeyAttributeOverrideContainer;
	}

	protected JavaAttributeOverrideContainer buildMapKeyAttributeOverrideContainer() {
		return this.getJpaFactory().buildJavaAttributeOverrideContainer(this.buildMapKeyAttributeOverrideContainerParentAdapter());
	}

	protected JavaAttributeOverrideContainer.ParentAdapter buildMapKeyAttributeOverrideContainerParentAdapter() {
		return new MapKeyAttributeOverrideContainerParentAdapter();
	}

	// ********** map key join columns **********

	public ListIterable<JavaSpecifiedJoinColumn> getMapKeyJoinColumns() {
		return this.hasSpecifiedMapKeyJoinColumns() ? this.getSpecifiedMapKeyJoinColumns() : this.getDefaultMapKeyJoinColumns();
	}

	public int getMapKeyJoinColumnsSize() {
		return this.hasSpecifiedMapKeyJoinColumns() ? this.getSpecifiedMapKeyJoinColumnsSize() : this.getDefaultMapKeyJoinColumnsSize();
	}


	// ********** specified map key join columns **********

	public ListIterable<JavaSpecifiedJoinColumn> getSpecifiedMapKeyJoinColumns() {
		return this.specifiedMapKeyJoinColumnContainer;
	}

	public int getSpecifiedMapKeyJoinColumnsSize() {
		return this.specifiedMapKeyJoinColumnContainer.size();
	}

	public boolean hasSpecifiedMapKeyJoinColumns() {
		return this.getSpecifiedMapKeyJoinColumnsSize() != 0;
	}

	public JavaSpecifiedJoinColumn getSpecifiedMapKeyJoinColumn(int index) {
		return this.specifiedMapKeyJoinColumnContainer.get(index);
	}

	public JavaSpecifiedJoinColumn addSpecifiedMapKeyJoinColumn() {
		return this.addSpecifiedMapKeyJoinColumn(this.getSpecifiedMapKeyJoinColumnsSize());
	}

	public JavaSpecifiedJoinColumn addSpecifiedMapKeyJoinColumn(int index) {
		MapKeyJoinColumnAnnotation2_0 annotation = this.addMapKeyJoinColumnAnnotation(index);
		return this.specifiedMapKeyJoinColumnContainer.addContextElement(index, annotation);
	}

	public void removeSpecifiedMapKeyJoinColumn(SpecifiedJoinColumn joinColumn) {
		this.removeSpecifiedMapKeyJoinColumn(this.specifiedMapKeyJoinColumnContainer.indexOf((JavaSpecifiedJoinColumn) joinColumn));
	}

	public void removeSpecifiedMapKeyJoinColumn(int index) {
		this.removeMapKeyJoinColumnAnnotation(index);
		this.specifiedMapKeyJoinColumnContainer.remove(index);
	}

	public void moveSpecifiedMapKeyJoinColumn(int targetIndex, int sourceIndex) {
		this.moveMapKeyJoinColumnAnnotation(targetIndex, sourceIndex);
		this.specifiedMapKeyJoinColumnContainer.move(targetIndex, sourceIndex);
	}

	protected void syncSpecifiedMapKeyJoinColumns(IProgressMonitor monitor) {
		this.specifiedMapKeyJoinColumnContainer.synchronizeWithResourceModel(monitor);
	}

	protected ContextListContainer<JavaSpecifiedJoinColumn, MapKeyJoinColumnAnnotation2_0> buildSpecifiedMapKeyJoinColumnContainer() {
		return this.buildSpecifiedContextListContainer(SPECIFIED_MAP_KEY_JOIN_COLUMNS_LIST, new SpecifiedMapKeyJoinColumnContainerAdapter());
	}

	/**
	 * specified map key join column container adapter
	 */
	public class SpecifiedMapKeyJoinColumnContainerAdapter
		extends AbstractContainerAdapter<JavaSpecifiedJoinColumn, MapKeyJoinColumnAnnotation2_0>
	{
		public JavaSpecifiedJoinColumn buildContextElement(MapKeyJoinColumnAnnotation2_0 resourceElement) {
			return AbstractJavaElementCollectionMapping2_0.this.buildMapKeyJoinColumn(resourceElement);
		}
		public ListIterable<MapKeyJoinColumnAnnotation2_0> getResourceElements() {
			return AbstractJavaElementCollectionMapping2_0.this.getMapKeyJoinColumnAnnotations();
		}
		public MapKeyJoinColumnAnnotation2_0 extractResourceElement(JavaSpecifiedJoinColumn contextElement) {
			return (MapKeyJoinColumnAnnotation2_0) contextElement.getColumnAnnotation();
		}
	}

	protected JavaSpecifiedJoinColumn buildMapKeyJoinColumn(MapKeyJoinColumnAnnotation2_0 joinColumnAnnotation) {
		return this.getJpaFactory().buildJavaJoinColumn(this.mapKeyJoinColumnParentAdapter, joinColumnAnnotation);
	}

	protected JoinColumn.ParentAdapter buildMapKeyJoinColumnParentAdapter() {
		return new MapKeyJoinColumnParentAdapter();
	}


	// ********** default map key join column **********

	public JavaSpecifiedJoinColumn getDefaultMapKeyJoinColumn() {
		return this.defaultMapKeyJoinColumn;
	}

	protected void setDefaultMapKeyJoinColumn(JavaSpecifiedJoinColumn mapKeyJoinColumn) {
		JavaSpecifiedJoinColumn old = this.defaultMapKeyJoinColumn;
		this.defaultMapKeyJoinColumn = mapKeyJoinColumn;
		this.firePropertyChanged(DEFAULT_MAP_KEY_JOIN_COLUMN_PROPERTY, old, mapKeyJoinColumn);
	}

	protected ListIterable<JavaSpecifiedJoinColumn> getDefaultMapKeyJoinColumns() {
		return (this.defaultMapKeyJoinColumn != null) ?
				new SingleElementListIterable<JavaSpecifiedJoinColumn>(this.defaultMapKeyJoinColumn) :
				EmptyListIterable.<JavaSpecifiedJoinColumn>instance();
	}

	protected int getDefaultMapKeyJoinColumnsSize() {
		return (this.defaultMapKeyJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultMapKeyJoinColumn(IProgressMonitor monitor) {
		if (this.buildsDefaultMapKeyJoinColumn()) {
			if (this.defaultMapKeyJoinColumn == null) {
				this.setDefaultMapKeyJoinColumn(this.buildMapKeyJoinColumn(this.buildNullMapKeyJoinColumnAnnotation()));
			} else {
				this.defaultMapKeyJoinColumn.update(monitor);
			}
		} else {
			this.setDefaultMapKeyJoinColumn(null);
		}
	}

	protected boolean buildsDefaultMapKeyJoinColumn() {
		return ! this.hasSpecifiedMapKeyJoinColumns() &&
				getKeyType() == Type.ENTITY_TYPE;
	}

	// ********** map key join column annotations **********

	protected ListIterable<MapKeyJoinColumnAnnotation2_0> getMapKeyJoinColumnAnnotations() {
		return new SubListIterableWrapper<NestableAnnotation, MapKeyJoinColumnAnnotation2_0>(this.getNestableMapKeyJoinColumnAnnotations());
	}

	protected ListIterable<NestableAnnotation> getNestableMapKeyJoinColumnAnnotations() {
		return this.getResourceAttribute().getAnnotations(MapKeyJoinColumnAnnotation2_0.ANNOTATION_NAME);
	}

	protected MapKeyJoinColumnAnnotation2_0 addMapKeyJoinColumnAnnotation(int index) {
		return (MapKeyJoinColumnAnnotation2_0) this.getResourceAttribute().addAnnotation(index, MapKeyJoinColumnAnnotation2_0.ANNOTATION_NAME);
	}

	protected void removeMapKeyJoinColumnAnnotation(int index) {
		this.getResourceAttribute().removeAnnotation(index, MapKeyJoinColumnAnnotation2_0.ANNOTATION_NAME);
	}

	protected void moveMapKeyJoinColumnAnnotation(int targetIndex, int sourceIndex) {
		this.getResourceAttribute().moveAnnotation(targetIndex, sourceIndex, MapKeyJoinColumnAnnotation2_0.ANNOTATION_NAME);
	}

	protected MapKeyJoinColumnAnnotation2_0 buildNullMapKeyJoinColumnAnnotation() {
		return new NullMapKeyJoinColumnAnnotation2_0(this.getResourceAttribute());
	}


	// ********** embedded mappings **********

	public Iterable<String> getCandidateMapKeyNames() {
		return this.getAllTargetEmbeddableNonTransientAttributeNames();
	}

	/**
	 * Return a list of lists; each nested list holds the names for one of the
	 * embedded mapping's target embeddable type mapping's attribute mappings
	 * (non-transient attribute or association mappings, depending on the specified transformer).
	 */
	protected Iterable<String> getAllTargetEmbeddableNonTransientAttributeNames() {
		return IterableTools.children(this.getTargetEmbeddableNonTransientAttributeMappings(), ALL_MAPPING_NAMES_TRANSFORMER);
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
		return this.getQualifiedEmbeddableOverridableMappingNames(ALL_MAPPING_NAMES_TRANSFORMER);
	}

	@Override
	public Iterable<String> getAllOverridableAttributeMappingNames() {
		return this.getQualifiedEmbeddableOverridableMappingNames(ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER);
	}

	@Override
	public Iterable<String> getAllOverridableAssociationMappingNames() {
		return this.getQualifiedEmbeddableOverridableMappingNames(ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER);
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
	public SpecifiedColumn resolveOverriddenColumn(String attributeName) {
		attributeName = this.unqualify(attributeName);
		if (attributeName == null) {
			return null;
		}
		SpecifiedAttributeOverride override = this.valueAttributeOverrideContainer.getSpecifiedOverrideNamed(attributeName);
		// recurse into the target embeddable if necessary
		return (override != null) ? override.getColumn() : this.resolveOverriddenColumnInTargetEmbeddable(attributeName);
	}

	protected SpecifiedColumn resolveOverriddenColumnInTargetEmbeddable(String attributeName) {
		Embeddable targetEmbeddable = this.getResolvedTargetEmbeddable();
		return (targetEmbeddable == null) ? null : targetEmbeddable.resolveOverriddenColumn(attributeName);
	}

	@Override
	public SpecifiedRelationship resolveOverriddenRelationship(String attributeName) {
		attributeName = this.unqualify(attributeName);
		if (attributeName == null) {
			return null;
		}
		SpecifiedAssociationOverride override = this.valueAssociationOverrideContainer.getSpecifiedOverrideNamed(attributeName);
		// recurse into the target embeddable if necessary
		return (override != null) ? override.getRelationship() :  this.resolveRelationshipInTargetEmbeddable(attributeName);
	}

	protected SpecifiedRelationship resolveRelationshipInTargetEmbeddable(String attributeName) {
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
		return ElementCollectionAnnotation2_0.ANNOTATION_NAME;
	}

	public Entity getEntity() {
		TypeMapping typeMapping = this.getTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}


	// ********** Java completion proposals **********

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
		for (JavaSpecifiedJoinColumn joinColumn : this.getMapKeyJoinColumns()) {
			result = joinColumn.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	protected Iterable<String> getJavaCandidateMapKeyNames() {
		return new TransformationIterable<String, String>(this.getCandidateMapKeyNames(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}


	// ********** metamodel **********

	@Override
	protected String getMetamodelFieldTypeName() {
		return ((SpecifiedPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	public String getMetamodelTypeName() {
		return (this.fullyQualifiedTargetClass != null) ? this.fullyQualifiedTargetClass : MetamodelField2_0.DEFAULT_TYPE_NAME;
	}

	@Override
	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		this.addMetamodelFieldMapKeyTypeArgumentNameTo(typeArgumentNames);
		super.addMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
	}

	protected void addMetamodelFieldMapKeyTypeArgumentNameTo(ArrayList<String> typeArgumentNames) {
		String keyTypeName = ((SpecifiedPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldMapKeyTypeName();
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
		this.validateTargetClass(messages);
		this.validateMapKeyClass(messages);
		this.orderable.validate(messages, reporter);
		this.collectionTable.validate(messages, reporter);
		this.validateValue(messages, reporter);
		this.validateMapKey(messages, reporter);
		this.validateNoEmbeddableInMappingContainsProhibitedMapping(messages);
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
					|| ((ManyToOneRelationship2_0)relationshipMapping.getRelationship()).getJoinTableStrategy().getJoinTable() != null) {
				prohibitedMappingFound = true;
			}
		}
		Iterable<AttributeMapping> oneToOneMappings = embeddable.getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		if (oneToOneMappings.iterator().hasNext()) {
			relationshipMapping = (RelationshipMapping)oneToOneMappings.iterator().next();
			if (relationshipMapping.getRelationshipOwner() != null
					|| ((OneToOneRelationship2_0)relationshipMapping.getRelationship()).getJoinTableStrategy().getJoinTable() != null) {
				prohibitedMappingFound = true;
			}
		}
		if (prohibitedMappingFound) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
						this.buildValidationMessage(
								this.getVirtualPersistentAttributeTextRange(),
								JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_PROHIBITED_RELATIONSHIP_MAPPING,
								this.getName(),
								embeddable.getName(),
								relationshipMapping.getName()
						)
				);				
			} else {
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
	}

	private void embeddableContainsElementCollection(List<IMessage> messages, Embeddable embeddable) {
		Iterable<AttributeMapping> elementCollectionMappings = embeddable.getAllAttributeMappings(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		if (elementCollectionMappings.iterator().hasNext()) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
						this.buildValidationMessage(
								this.getVirtualPersistentAttributeTextRange(),
								JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_CONTAINS_EMBEDDABLE_WITH_ELEMENT_COLLECTION_MAPPING,
								this.getName(),
								embeddable.getName(),
								elementCollectionMappings.iterator().next().getName()
						)
				);				
			} else {
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
	}

	protected void validateAttributeType(List<IMessage> messages, IReporter reporter) {
		JavaSpecifiedPersistentAttribute javaAttribute = this.getJavaPersistentAttribute();
		if ((javaAttribute != null) && !javaAttribute.getJpaContainerDefinition().isContainer()) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					this.buildValidationMessage(
						this.getVirtualPersistentAttributeTextRange(),
						JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ATTRIBUTE_TYPE_IS_NOT_SUPPORTED_COLLECTION_TYPE,
						this.getName()
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

	protected void validateTargetClass(List<IMessage> messages) {
		String targetClassName = this.getFullyQualifiedTargetClass();
		if (targetClassName == null) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					this.buildValidationMessage(
						this.getVirtualPersistentAttributeTextRange(),
						JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED,
						this.getName()
					)
				);
			} else {
				messages.add(
					this.buildValidationMessage(
						this.getValidationTextRange(),
						JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED
					)
				);
			}
			return;
		}

		if (MappingTools.typeIsBasic(this.getJavaProject(), targetClassName)) {
			return;
		}
		if (this.getResolvedTargetEmbeddable() == null) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					this.buildValidationMessage(
						this.getVirtualPersistentAttributeTextRange(),
						JptJpaCoreValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE,
						this.getName(),
						targetClassName
					)
				);
			} else {
				messages.add(
					this.buildValidationMessage(
						this.getTargetClassTextRange(),
						JptJpaCoreValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE,
						targetClassName
					)
				);
			}
		}
	}

	protected TextRange getTargetClassTextRange() {
		return this.getValidationTextRange(this.getAnnotationTargetClassTextRange());
	}

	protected TextRange getAnnotationTargetClassTextRange() {
		ElementCollectionAnnotation2_0 annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getTargetClassTextRange();
	}

	protected TextRange getMapKeyClassTextRange() {
		MapKeyClassAnnotation2_0 annotation = this.getMapKeyClassAnnotation();
		return annotation == null ? getMappingAnnotationTextRange() : annotation.getTextRange();
	}

	protected void validateMapKeyClass(List<IMessage> messages) {
		if (this.getPersistentAttribute().getJpaContainerDefinition().isMap()) {
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
						this.getMapKeyClassTextRange(),
						JptJpaCoreValidationMessages.MAP_KEY_CLASS_NOT_DEFINED
					)
				);
			}
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
			} else {
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

	protected void validateValue(List<IMessage> messages, IReporter reporter) {
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
				for (JavaSpecifiedJoinColumn joinColumn : this.getMapKeyJoinColumns()) {
					joinColumn.validate(messages, reporter);
				}
				break;
			case EMBEDDABLE_TYPE :
				this.mapKeyAttributeOverrideContainer.validate(messages, reporter);
				//validate map key association overrides - for eclipselink
				break;
			default :
				break;
		}
	}


	// ********** abstract parent adapter **********

	/**
	 * the various (column and override) parent adapters have lots of common
	 * interactions with the mapping
	 */
	public abstract class AbstractParentAdapter {
		public JavaResourceMember getResourceMember() {
			return AbstractJavaElementCollectionMapping2_0.this.getResourceAttribute();
		}

		public TypeMapping getTypeMapping() {
			return AbstractJavaElementCollectionMapping2_0.this.getTypeMapping();
		}

		public String getDefaultTableName() {
			return this.getCollectionTable().getName();
		}

		public org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName) {
			JavaCollectionTable2_0 table = this.getCollectionTable();
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
			return AbstractJavaElementCollectionMapping2_0.this.getValidationTextRange();
		}

		protected String getMappingName() {
			return AbstractJavaElementCollectionMapping2_0.this.getName();
		}

		protected JavaCollectionTable2_0 getCollectionTable() {
			return AbstractJavaElementCollectionMapping2_0.this.getCollectionTable();
		}

		protected JavaSpecifiedPersistentAttribute getPersistentAttribute() {
			return AbstractJavaElementCollectionMapping2_0.this.getPersistentAttribute();
		}
	}


	// ********** value column parent adapter **********

	public class ValueColumnParentAdapter
		extends AbstractParentAdapter
		implements JavaSpecifiedColumn.ParentAdapter
	{
		public JpaContextModel getColumnParent() {
			return AbstractJavaElementCollectionMapping2_0.this;
		}

		public CompleteColumnAnnotation getColumnAnnotation() {
			return AbstractJavaElementCollectionMapping2_0.this.getValueColumnAnnotation();
		}

		public void removeColumnAnnotation() {
			AbstractJavaElementCollectionMapping2_0.this.removeValueColumnAnnotation();
		}

		public String getDefaultColumnName(NamedColumn column) {
			return this.getMappingName();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return new NamedColumnValidator(this.getPersistentAttribute(), (BaseColumn) column, new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** map key column parent adapter **********

	public class MapKeyColumnParentAdapter
		extends AbstractParentAdapter
		implements JavaSpecifiedColumn.ParentAdapter
	{
		public JpaContextModel getColumnParent() {
			return AbstractJavaElementCollectionMapping2_0.this;
		}

		public MapKeyColumnAnnotation2_0 getColumnAnnotation() {
			return AbstractJavaElementCollectionMapping2_0.this.getMapKeyColumnAnnotation();
		}

		public void removeColumnAnnotation() {
			AbstractJavaElementCollectionMapping2_0.this.removeMapKeyColumnAnnotation();
		}

		public String getDefaultColumnName(NamedColumn column) {
			return this.getMappingName() + "_KEY"; //$NON-NLS-1$
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return new MapKeyColumnValidator(this.getPersistentAttribute(), (BaseColumn) column, new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** value override container parent adapter **********

	public abstract class ValueOverrideContainerParentAdapter
		extends AbstractParentAdapter
	{
		protected static final String POSSIBLE_PREFIX = "value"; //$NON-NLS-1$

		public JpaContextModel getOverrideContainerParent() {
			return AbstractJavaElementCollectionMapping2_0.this;
		}

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
			return ! overrideName.startsWith(MapKeyAttributeOverrideContainerParentAdapter.RELEVANT_PREFIX_);
		}
	}


	// ********** value attribute override container parent adapter **********

	public class ValueAttributeOverrideContainerParentAdapter
		extends ValueOverrideContainerParentAdapter
		implements JavaAttributeOverrideContainer2_0.ParentAdapter
	{
		public Iterable<String> getAllOverridableNames() {
			TypeMapping overriddenTypeMapping = this.getOverridableTypeMapping();
			return (overriddenTypeMapping != null) ? overriddenTypeMapping.getAllOverridableAttributeNames() : EmptyIterable.<String>instance();
		}

		public SpecifiedColumn resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public JpaValidator buildOverrideValidator(Override_ override, OverrideContainer container) {
			return new AttributeOverrideValidator(this.getPersistentAttribute(), (AttributeOverride) override, (AttributeOverrideContainer) container, new EmbeddableOverrideDescriptionProvider());
		}

		public JpaValidator buildColumnValidator(Override_ override, BaseColumn column, TableColumn.ParentAdapter columnParentAdapter) {
			return new AttributeOverrideColumnValidator(this.getPersistentAttribute(), (AttributeOverride) override, column, new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** value association override container parent adapter **********

	public class ValueAssociationOverrideContainerParentAdapter
		extends ValueOverrideContainerParentAdapter
		implements JavaAssociationOverrideContainer2_0.ParentAdapter
	{
		public Iterable<String> getAllOverridableNames() {
			TypeMapping typeMapping = this.getOverridableTypeMapping();
			return (typeMapping != null) ? typeMapping.getAllOverridableAssociationNames() : EmptyIterable.<String>instance();
		}

		public SpecifiedRelationship resolveOverriddenRelationship(String attributeName) {
			return MappingTools.resolveOverriddenRelationship(this.getOverridableTypeMapping(), attributeName);
		}

		public JpaValidator buildOverrideValidator(Override_ override, OverrideContainer container) {
			return new AssociationOverrideValidator(this.getPersistentAttribute(), (AssociationOverride) override, (AssociationOverrideContainer) container, new EmbeddableOverrideDescriptionProvider());
		}

		public JpaValidator buildColumnValidator(Override_ override, BaseColumn column, TableColumn.ParentAdapter columnParentAdapter) {
			return new AssociationOverrideJoinColumnValidator(this.getPersistentAttribute(), (AssociationOverride) override, (JoinColumn) column, (JoinColumn.ParentAdapter) columnParentAdapter, new CollectionTableTableDescriptionProvider());
		}

		public JpaValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
			return NullJpaValidator.instance();
		}

		public JpaValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
			return NullJpaValidator.instance();
		}

		public JpaValidator buildJoinTableValidator(AssociationOverride override, Table table) {
			return NullJpaValidator.instance();
		}
	}

	// ********** map key join column parent adapter **********

	public class MapKeyJoinColumnParentAdapter
		implements JoinColumn.ParentAdapter
	{
		public JpaContextModel getColumnParent() {
			return AbstractJavaElementCollectionMapping2_0.this;
		}

		public String getDefaultTableName() {
			return AbstractJavaElementCollectionMapping2_0.this.getCollectionTable().getName();
		}

		public String getDefaultColumnName(NamedColumn column) {
			return AbstractJavaElementCollectionMapping2_0.this.getName() + "_KEY"; //$NON-NLS-1$
		}

		public String getAttributeName() {
			return AbstractJavaElementCollectionMapping2_0.this.getName();
		}

		protected SpecifiedPersistentAttribute getPersistentAttribute() {
			return AbstractJavaElementCollectionMapping2_0.this.getPersistentAttribute();
		}

		public Entity getRelationshipTarget() {
			return AbstractJavaElementCollectionMapping2_0.this.getResolvedMapKeyEntity();
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
			return AbstractJavaElementCollectionMapping2_0.this.getCollectionTable().getDbTable();
		}

		public org.eclipse.jpt.jpa.db.Table getReferencedColumnDbTable() {
			return AbstractJavaElementCollectionMapping2_0.this.getResolvedMapKeyEntity().getPrimaryDbTable();
		}

		public int getJoinColumnsSize() {
			return AbstractJavaElementCollectionMapping2_0.this.getMapKeyJoinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return AbstractJavaElementCollectionMapping2_0.this.getValidationTextRange();
		}

		public JpaValidator buildColumnValidator(NamedColumn column) {
			return new MapKeyJoinColumnValidator(
				this.getPersistentAttribute(),
				(JoinColumn) column,
				this, 
				new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** map key attribute override container parent adapter **********

	public class MapKeyAttributeOverrideContainerParentAdapter
		extends AbstractParentAdapter
		implements JavaAttributeOverrideContainer2_0.ParentAdapter
	{
		protected static final String POSSIBLE_PREFIX = "key"; //$NON-NLS-1$
		protected static final String RELEVANT_PREFIX_ = "key."; //$NON-NLS-1$

		public JpaContextModel getOverrideContainerParent() {
			return AbstractJavaElementCollectionMapping2_0.this;
		}

		public TypeMapping getOverridableTypeMapping() {
			return AbstractJavaElementCollectionMapping2_0.this.getResolvedMapKeyEmbeddable();
		}

		public Iterable<String> getAllOverridableNames() {
			TypeMapping overriddenTypeMapping = this.getOverridableTypeMapping();
			return (overriddenTypeMapping != null) ? overriddenTypeMapping.getAllOverridableAttributeNames() : EmptyIterable.<String>instance();
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

		public SpecifiedColumn resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public JpaValidator buildOverrideValidator(Override_ override, OverrideContainer container) {
			return new MapKeyAttributeOverrideValidator(this.getPersistentAttribute(), (AttributeOverride) override, (AttributeOverrideContainer) container, new EmbeddableOverrideDescriptionProvider());
		}

		public JpaValidator buildColumnValidator(Override_ override, BaseColumn column, TableColumn.ParentAdapter columnParentAdapter) {
			return new MapKeyAttributeOverrideColumnValidator(this.getPersistentAttribute(), (AttributeOverride) override, column, new CollectionTableTableDescriptionProvider());
		}
	}
}
