/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.Association;
import org.eclipse.jpt.common.utility.internal.SimpleAssociation;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaLobConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaTemporalConverter;
import org.eclipse.jpt.jpa.core.internal.context.AttributeMappingTools;
import org.eclipse.jpt.jpa.core.internal.context.TableColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
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
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.NullMapKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAttributeOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaMapKeyEnumeratedConverter2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaMapKeyTemporalConverter2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyClass2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyColumn2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyJoinColumn2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.CompleteColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MapKeyAnnotation;
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
	protected JavaConverter mapKeyConverter;  // map key converter - never null

	protected final JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer;

	protected final ContextListContainer<JavaJoinColumn, MapKeyJoinColumn2_0Annotation> specifiedMapKeyJoinColumnContainer;
	protected final JavaReadOnlyJoinColumn.Owner mapKeyJoinColumnOwner;

	protected JavaJoinColumn defaultMapKeyJoinColumn;

	protected static final JavaConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new JavaConverter.Adapter[] {
		JavaEnumeratedConverter.Adapter.instance(),
		JavaTemporalConverter.ElementCollectionAdapter.instance(),
		JavaLobConverter.Adapter.instance()
	};
	protected static final Iterable<JavaConverter.Adapter> CONVERTER_ADAPTERS = new ArrayIterable<JavaConverter.Adapter>(CONVERTER_ADAPTER_ARRAY);


	protected static final JavaConverter.Adapter[] MAP_KEY_CONVERTER_ADAPTER_ARRAY = new JavaConverter.Adapter[] {
		JavaMapKeyEnumeratedConverter2_0.Adapter.instance(),
		JavaMapKeyTemporalConverter2_0.Adapter.instance()
	};
	protected static final Iterable<JavaConverter.Adapter> MAP_KEY_CONVERTER_ADAPTERS = new ArrayIterable<JavaConverter.Adapter>(MAP_KEY_CONVERTER_ADAPTER_ARRAY);


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
		this.mapKeyConverter = this.buildMapKeyConverter();
		this.mapKeyAttributeOverrideContainer = this.buildMapKeyAttributeOverrideContainer();
		this.mapKeyJoinColumnOwner = this.buildMapKeyJoinColumnOwner();
		this.specifiedMapKeyJoinColumnContainer = this.buildSpecifiedMapKeyJoinColumnContainer();
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
		return this.getJpaFactory().buildJavaCollectionTable(this, this.buildCollectionTableOwner());
	}

	protected Table.Owner buildCollectionTableOwner() {
		return new CollectionTableOwner();
	}

	protected class CollectionTableOwner
		implements ReadOnlyTable.Owner
	{
		public JptValidator buildTableValidator(ReadOnlyTable table, TableTextRangeResolver textRangeResolver) {
			return new CollectionTableValidator(AbstractJavaElementCollectionMapping2_0.this.getPersistentAttribute(), (CollectionTable2_0) table, textRangeResolver);
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

	protected JavaAssociationOverrideContainer2_0.Owner buildValueAssociationOverrideContainerOwner() {
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
		return (MapKeyClass2_0Annotation) this.getResourceAttribute().getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
	}

	protected MapKeyClass2_0Annotation addMapKeyClassAnnotation() {
		return (MapKeyClass2_0Annotation) this.getResourceAttribute().addAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyClassAnnotation() {
		this.getResourceAttribute().removeAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
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
		return (MapKeyColumn2_0Annotation) this.getResourceAttribute().getNonNullAnnotation(MapKeyColumn2_0Annotation.ANNOTATION_NAME);
	}

	protected void removeMapKeyColumnAnnotation() {
		this.getResourceAttribute().removeAnnotation(MapKeyColumn2_0Annotation.ANNOTATION_NAME);
	}

	// ********** map key converter **********

	public JavaConverter getMapKeyConverter() {
		return this.mapKeyConverter;
	}

	public void setMapKeyConverter(Class<? extends Converter> converterType) {
		if (this.mapKeyConverter.getType() != converterType) {
			this.mapKeyConverter.dispose();
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

	protected void syncMapKeyConverter() {
		Association<JavaConverter.Adapter, Annotation> assoc = this.getMapKeyConverterAnnotation();
		if (assoc == null) {
			if (this.mapKeyConverter.getType() != null) {
				this.setMapKeyConverter_(this.buildNullConverter());
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
		return this.getJpaFactory().buildJavaAttributeOverrideContainer(this, this.buildMapKeyAttributeOverrideContainerOwner());
	}

	protected JavaAttributeOverrideContainer.Owner buildMapKeyAttributeOverrideContainerOwner() {
		return new MapKeyAttributeOverrideContainerOwner();
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

	protected void updateSpecifiedMapKeyJoinColumns() {
		this.specifiedMapKeyJoinColumnContainer.update();
	}

	protected ContextListContainer<JavaJoinColumn, MapKeyJoinColumn2_0Annotation> buildSpecifiedMapKeyJoinColumnContainer() {
		return new SpecifiedMapKeyJoinColumnContainer();
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
			return AbstractJavaElementCollectionMapping2_0.this.buildMapKeyJoinColumn(resourceElement);
		}
		@Override
		protected ListIterable<MapKeyJoinColumn2_0Annotation> getResourceElements() {
			return AbstractJavaElementCollectionMapping2_0.this.getMapKeyJoinColumnAnnotations();
		}
		@Override
		protected MapKeyJoinColumn2_0Annotation getResourceElement(JavaJoinColumn contextElement) {
			return (MapKeyJoinColumn2_0Annotation) contextElement.getColumnAnnotation();
		}
	}

	protected JavaJoinColumn buildMapKeyJoinColumn(MapKeyJoinColumn2_0Annotation joinColumnAnnotation) {
		return this.getJpaFactory().buildJavaJoinColumn(this, this.mapKeyJoinColumnOwner, joinColumnAnnotation);
	}

	protected JavaReadOnlyJoinColumn.Owner buildMapKeyJoinColumnOwner() {
		return new MapKeyJoinColumnOwner();
	}


	// ********** default map key join column **********

	public JavaJoinColumn getDefaultMapKeyJoinColumn() {
		return this.defaultMapKeyJoinColumn;
	}

	protected void setDefaultMapKeyJoinColumn(JavaJoinColumn mapKeyJoinColumn) {
		JavaJoinColumn old = this.defaultMapKeyJoinColumn;
		this.defaultMapKeyJoinColumn = mapKeyJoinColumn;
		this.firePropertyChanged(DEFAULT_MAP_KEY_JOIN_COLUMN_PROPERTY, old, mapKeyJoinColumn);
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
		return ! this.hasSpecifiedMapKeyJoinColumns() &&
				getKeyType() == Type.ENTITY_TYPE;
	}

	// ********** map key join column annotations **********

	protected ListIterable<MapKeyJoinColumn2_0Annotation> getMapKeyJoinColumnAnnotations() {
		return new SubListIterableWrapper<NestableAnnotation, MapKeyJoinColumn2_0Annotation>(this.getNestableMapKeyJoinColumnAnnotations());
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


	// ********** embedded mappings **********

	public Iterable<String> getCandidateMapKeyNames() {
		return this.getAllTargetEmbeddableAttributeNames();
	}

	protected Iterable<String> getAllTargetEmbeddableAttributeNames() {
		return new CompositeIterable<String>(this.getAllTargetEmbeddableAttributeNamesLists());
	}

	/**
	 * Return a list of lists; each nested list holds the names for one of the
	 * embedded mapping's target embeddable type mapping's attribute mappings
	 * (attribute or association mappings, depending on the specified transformer).
	 */
	protected Iterable<Iterable<String>> getAllTargetEmbeddableAttributeNamesLists() {
		return new TransformationIterable<AttributeMapping, Iterable<String>>(this.getAllTargetEmbeddableAttributeMappings(), AttributeMappingTools.ALL_MAPPING_NAMES_TRANSFORMER);
	}

	protected Iterable<AttributeMapping> getAllTargetEmbeddableAttributeMappings() {
		Embeddable targetEmbeddable = this.getResolvedTargetEmbeddable();
		return (targetEmbeddable != null) ? targetEmbeddable.getAllAttributeMappings() : EmptyIterable.<AttributeMapping> instance();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<String> getAllMappingNames() {
		return new CompositeIterable<String>(super.getAllMappingNames(), this.getAllEmbeddableAttributeMappingNames());
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

	protected Iterable<String> getEmbeddableOverridableMappingNames(Transformer<AttributeMapping, Iterable<String>> transformer) {
		return new CompositeIterable<String>(this.getEmbeddableOverridableMappingNamesLists(transformer));
	}

	/**
	 * Return a list of lists; each nested list holds the names for one of the
	 * embedded mapping's target embeddable type mapping's attribute mappings
	 * (attribute or association mappings, depending on the specified transformer).
	 */
	protected Iterable<Iterable<String>> getEmbeddableOverridableMappingNamesLists(Transformer<AttributeMapping, Iterable<String>> transformer) {
		return new TransformationIterable<AttributeMapping, Iterable<String>>(this.getEmbeddableAttributeMappings(), transformer);
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
		return (override != null) ? override.getRelationship() :  this.resolveRelationshipInTargetEmbeddable(attributeName);
	}

	protected Relationship resolveRelationshipInTargetEmbeddable(String attributeName) {
		Embeddable targetEmbeddable = this.getResolvedTargetEmbeddable();
		return (targetEmbeddable == null) ? null : targetEmbeddable.resolveOverriddenRelationship(attributeName);
	}


	// ********** misc **********

	@Override
	public JavaPersistentAttribute2_0 getParent() {
		return (JavaPersistentAttribute2_0) super.getParent();
	}

	@Override
	public JavaPersistentAttribute2_0 getPersistentAttribute() {
		return (JavaPersistentAttribute2_0) super.getPersistentAttribute();
	}

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
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.collectionTable.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.valueColumn.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.converter.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.orderable.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.valueAttributeOverrideContainer.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.valueAssociationOverrideContainer.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.mapKeyNameTouches(pos, astRoot)) {
			return this.getJavaCandidateMapKeyNames(filter);
		}
		result = this.mapKeyColumn.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.mapKeyConverter.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.mapKeyAttributeOverrideContainer.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaJoinColumn joinColumn : this.getMapKeyJoinColumns()) {
			result = joinColumn.getJavaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	protected Iterable<String> getJavaCandidateMapKeyNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateMapKeyNames(filter));
	}

	protected Iterable<String> getCandidateMapKeyNames(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateMapKeyNames(), filter);
	}


	// ********** metamodel **********

	@Override
	protected String getMetamodelFieldTypeName() {
		return this.getPersistentAttribute().getMetamodelContainerFieldTypeName();
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
		String keyTypeName = this.getPersistentAttribute().getMetamodelContainerFieldMapKeyTypeName();
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
		String targetClass = this.getFullyQualifiedTargetClass();
		if (targetClass == null) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED,
						new String[] {this.getName()},
						this,
						this.getValidationTextRange(astRoot)
					)
				);
			} else {
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
			return;
		}

		IJavaProject javaProject = this.getJpaProject().getJavaProject();
		if (JDTTools.findType(javaProject, targetClass) != null) {
			if ( ! JDTTools.typeIsBasic(javaProject, targetClass) && (this.getResolvedTargetEmbeddable() == null)) {
				if (this.getPersistentAttribute().isVirtual()) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.VIRTUAL_ATTRIBUTE_ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE,
							new String[] {this.getName(), targetClass},
							this,
							this.getTargetClassTextRange(astRoot)
						)
					);
				} else {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_MUST_BE_EMBEDDABLE_OR_BASIC_TYPE,
							new String[] {targetClass},
							this,
							this.getTargetClassTextRange(astRoot)
						)
					);
				}
			}
		}
	}

	protected TextRange getTargetClassTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.getAnnotationTargetClassTextRange(astRoot), astRoot);
	}

	protected TextRange getAnnotationTargetClassTextRange(CompilationUnit astRoot) {
		ElementCollection2_0Annotation annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getTargetClassTextRange(astRoot);
	}

	protected void validateMapKeyClass(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getPersistentAttribute().getJpaContainerDefinition().isMap()) {
			this.validateMapKeyClass_(messages, astRoot);
		}
	}

	protected void validateMapKeyClass_(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getMapKeyClass() == null) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_MAP_KEY_CLASS_NOT_DEFINED,
						new String[] {this.getName()},
						this,
						this.getValidationTextRange(astRoot)
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.MAP_KEY_CLASS_NOT_DEFINED,
						EMPTY_STRING_ARRAY,
						this,
						this.getValidationTextRange(astRoot)
					)
				);
			}
		}
	}

	protected void validateValue(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		this.converter.validate(messages, reporter, astRoot);
		//TODO should we handle validation when the type is embeddable,
		//but a value column is specified, or things like that if that is invalid?
		switch (this.valueType) {
			case BASIC_TYPE :
				this.valueColumn.validate(messages, reporter, astRoot);
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
				this.mapKeyConverter.validate(messages, reporter, astRoot);
				break;
			case ENTITY_TYPE :
				for (JavaJoinColumn joinColumn : this.getMapKeyJoinColumns()) {
					joinColumn.validate(messages, reporter, astRoot);
				}
				break;
			case EMBEDDABLE_TYPE :
				this.mapKeyAttributeOverrideContainer.validate(messages, reporter, astRoot);
				//validate map key association overrides - for eclipselink
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
			return Tools.valuesAreEqual(table.getName(), tableName) ? table.getDbTable() : null;
		}

		public Iterable<String> getCandidateTableNames() {
			return EmptyIterable.instance();
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

		protected JavaPersistentAttribute2_0 getPersistentAttribute() {
			return AbstractJavaElementCollectionMapping2_0.this.getPersistentAttribute();
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

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return this.getMappingName();
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new NamedColumnValidator(this.getPersistentAttribute(), (ReadOnlyBaseColumn) column, (TableColumnTextRangeResolver) textRangeResolver, new CollectionTableTableDescriptionProvider());
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

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return this.getMappingName() + "_KEY"; //$NON-NLS-1$
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new MapKeyColumnValidator(this.getPersistentAttribute(), (ReadOnlyBaseColumn) column, (TableColumnTextRangeResolver) textRangeResolver, new CollectionTableTableDescriptionProvider());
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
	}


	// ********** value attribute override container owner **********

	protected class ValueAttributeOverrideContainerOwner
		extends ValueOverrideContainerOwner
		implements JavaAttributeOverrideContainer2_0.Owner
	{
		public Iterable<String> getAllOverridableNames() {
			TypeMapping overriddenTypeMapping = this.getOverridableTypeMapping();
			return (overriddenTypeMapping != null) ? overriddenTypeMapping.getAllOverridableAttributeNames() : EmptyIterable.<String>instance();
		}

		public Column resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new AttributeOverrideValidator(this.getPersistentAttribute(), (ReadOnlyAttributeOverride) override, (AttributeOverrideContainer) container, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner columnOwner, TableColumnTextRangeResolver textRangeResolver) {
			return new AttributeOverrideColumnValidator(this.getPersistentAttribute(), (ReadOnlyAttributeOverride) override, column, textRangeResolver, new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** value association override container owner **********

	protected class ValueAssociationOverrideContainerOwner
		extends ValueOverrideContainerOwner
		implements JavaAssociationOverrideContainer2_0.Owner
	{
		public Iterable<String> getAllOverridableNames() {
			TypeMapping typeMapping = this.getOverridableTypeMapping();
			return (typeMapping != null) ? typeMapping.getAllOverridableAssociationNames() : EmptyIterable.<String>instance();
		}

		public Relationship resolveOverriddenRelationship(String attributeName) {
			return MappingTools.resolveOverriddenRelationship(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new AssociationOverrideValidator(this.getPersistentAttribute(), (ReadOnlyAssociationOverride) override, (AssociationOverrideContainer) container, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner columnOwner, TableColumnTextRangeResolver textRangeResolver) {
			return new AssociationOverrideJoinColumnValidator(this.getPersistentAttribute(), (ReadOnlyAssociationOverride) override, (ReadOnlyJoinColumn) column, (ReadOnlyJoinColumn.Owner) columnOwner, (JoinColumnTextRangeResolver) textRangeResolver, new CollectionTableTableDescriptionProvider());
		}

		public JptValidator buildJoinTableJoinColumnValidator(ReadOnlyAssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
			throw this.buildJoinTableUnsupportedException();
		}

		public JptValidator buildJoinTableInverseJoinColumnValidator(ReadOnlyAssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
			throw this.buildJoinTableUnsupportedException();
		}

		public JptValidator buildJoinTableValidator(ReadOnlyAssociationOverride override, ReadOnlyTable table, TableTextRangeResolver textRangeResolver) {
			throw this.buildJoinTableUnsupportedException();
		}

		protected UnsupportedOperationException buildJoinTableUnsupportedException() {
			return new UnsupportedOperationException("An element collection containing a nested relationship mapping using a join table is not supported"); //$NON-NLS-1$
		}
	}

	// ********** map key join column owner **********

	protected class MapKeyJoinColumnOwner
		implements JavaReadOnlyJoinColumn.Owner
	{
		protected MapKeyJoinColumnOwner() {
			super();
		}

		public String getDefaultTableName() {
			return AbstractJavaElementCollectionMapping2_0.this.getCollectionTable().getName();
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return AbstractJavaElementCollectionMapping2_0.this.getName() + "_KEY"; //$NON-NLS-1$
		}

		public String getAttributeName() {
			return AbstractJavaElementCollectionMapping2_0.this.getName();
		}

		protected PersistentAttribute getPersistentAttribute() {
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
			return Tools.valuesAreDifferent(this.getDefaultTableName(), tableName);
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

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return AbstractJavaElementCollectionMapping2_0.this.defaultMapKeyJoinColumn == joinColumn;
		}

		public int getJoinColumnsSize() {
			return AbstractJavaElementCollectionMapping2_0.this.getMapKeyJoinColumnsSize();
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return AbstractJavaElementCollectionMapping2_0.this.getValidationTextRange(astRoot);
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new MapKeyJoinColumnValidator(
				this.getPersistentAttribute(),
				(ReadOnlyJoinColumn) column,
				this, 
				(JoinColumnTextRangeResolver) textRangeResolver,
				new CollectionTableTableDescriptionProvider());
		}
	}


	// ********** map key attribute override container owner **********

	protected class MapKeyAttributeOverrideContainerOwner
		extends AbstractOwner
		implements JavaAttributeOverrideContainer2_0.Owner
	{
		protected static final String POSSIBLE_PREFIX = "key"; //$NON-NLS-1$
		protected static final String RELEVANT_PREFIX_ = "key."; //$NON-NLS-1$

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

		public Column resolveOverriddenColumn(String attributeName) {
			return MappingTools.resolveOverriddenColumn(this.getOverridableTypeMapping(), attributeName);
		}

		public JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver) {
			return new MapKeyAttributeOverrideValidator(this.getPersistentAttribute(), (ReadOnlyAttributeOverride) override, (AttributeOverrideContainer) container, textRangeResolver, new EmbeddableOverrideDescriptionProvider());
		}

		public JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner columnOwner, TableColumnTextRangeResolver textRangeResolver) {
			return new MapKeyAttributeOverrideColumnValidator(this.getPersistentAttribute(), (ReadOnlyAttributeOverride) override, column, textRangeResolver, new CollectionTableTableDescriptionProvider());
		}
	}
}