/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaBaseColumn;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaMultiRelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaOrderable;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.context.CollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyClass2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyColumn2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java multi-relationship (m:m, 1:m) mapping
 */
public abstract class AbstractJavaMultiRelationshipMapping<T extends RelationshipMappingAnnotation>
	extends AbstractJavaRelationshipMapping<T> 
	implements JavaMultiRelationshipMapping, CollectionMapping2_0
{
	protected final JavaOrderable orderable;

	protected Embeddable resolvedTargetEmbeddable;

	protected Type valueType;
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

	protected final JavaColumn mapKeyColumn;

	protected AbstractJavaMultiRelationshipMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.orderable = getJpaFactory().buildJavaOrderable(this, buildOrderableOwner());
		this.mapKeyColumn = ((JpaFactory2_0) this.getJpaFactory()).buildJavaMapKeyColumn(parent, this.buildMapKeyColumnOwner());
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.orderable.initialize();
		this.resolvedTargetType = this.buildResolvedTargetType();
		this.resolvedTargetEmbeddable = this.buildResolvedTargetEmbeddable();
		this.initializeValueType();
		this.specifiedMapKeyClass = this.getResourceMapKeyClass();
		this.defaultMapKeyClass = this.buildDefaultMapKeyClass();
		this.resolvedMapKeyType = this.buildResolvedMapKeyType();//no need for change notification, use resolved target embeddable change notification instead?
		this.resolvedMapKeyEmbeddable = this.buildResolvedMapKeyEmbeddable();
		this.resolvedMapKeyEntity = this.buildResolvedMapKeyEntity();
		this.initializeKeyType();
		this.initializeMapKey();
		this.initializeMapKeyColumn();
	}

	@Override
	protected void update() {
		super.update();
		this.orderable.update();
		this.resolvedTargetType = this.buildResolvedTargetType();//no need for change notification, use resolved target embeddable change notification instead?
		this.resolvedTargetEmbeddable = this.buildResolvedTargetEmbeddable();
		this.updateValueType();
		this.setSpecifiedMapKeyClass_(this.getResourceMapKeyClass());
		this.setDefaultMapKeyClass(this.buildDefaultMapKeyClass());
		this.resolvedMapKeyType = this.buildResolvedMapKeyType();//no need for change notification, use resolved target embeddable change notification instead?
		this.setResolvedMapKeyEmbeddable(this.buildResolvedMapKeyEmbeddable());
		this.setResolvedMapKeyEntity(this.buildResolvedMapKeyEntity());
		this.updateKeyType();
		this.updateMapKey();
		this.updateMapKeyColumn();
	}

	// ********** AbstractJavaAttributeMapping implementation **********  

	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(JPA.JOIN_TABLE);
		names.add(JPA.MAP_KEY);
		names.add(JPA.ORDER_BY);
		if (this.isJpa2_0Compatible()) {
			names.add(JPA2_0.MAP_KEY_CLASS);
			names.add(JPA2_0.MAP_KEY_COLUMN);
			names.add(JPA2_0.MAP_KEY_ENUMERATED);
			names.add(JPA2_0.MAP_KEY_JOIN_COLUMN);
			names.add(JPA2_0.MAP_KEY_JOIN_COLUMNS);
			names.add(JPA2_0.MAP_KEY_TEMPORAL);
			names.add(JPA2_0.ORDER_COLUMN);
		}
	}

	// ********** AbstractJavaRelationshipMapping implementation **********  

	@Override
	protected String buildDefaultTargetEntity() {
		return this.getPersistentAttribute().getMultiReferenceTargetTypeName();
	}


	// ********** ordering **********  

	public JavaOrderable getOrderable() {
		return this.orderable;
	}

	protected Orderable2_0.Owner buildOrderableOwner() {
		return new Orderable2_0.Owner() {
			public String getTableName() {
				return getRelationshipReference().getPredominantJoiningStrategy().getTableName();
			}
			public Table getDbTable(String tableName) {
				return getRelationshipReference().getPredominantJoiningStrategy().getDbTable(tableName);
			}
		};
	}
	
	// ********** Fetchable implementation **********  

	public FetchType getDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}


	// ********** CollectionMapping implementation **********  

	protected Embeddable buildResolvedTargetEmbeddable() {
		if (this.resolvedTargetType == null) {
			return null;
		}
		TypeMapping typeMapping = this.resolvedTargetType.getMapping();
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
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
		if (this.getResolvedTargetEntity() != null) {
			return Type.ENTITY_TYPE;
		}
		else if (this.resolvedTargetEmbeddable != null) {
			return Type.EMBEDDABLE_TYPE; 
		}
		else if (getTargetEntity() == null) {
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

	protected void setDefaultMapKeyClass(String targetClass) {
		String old = this.defaultMapKeyClass;
		this.defaultMapKeyClass = targetClass;
		this.firePropertyChanged(DEFAULT_MAP_KEY_CLASS_PROPERTY, old, targetClass);
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

	// ********** map key column **********

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
		if (!isJpa2_0Compatible()) {
			return null;
		}
		return (MapKeyColumn2_0Annotation) this.getResourcePersistentAttribute().getNonNullAnnotation(MapKeyColumn2_0Annotation.ANNOTATION_NAME);
	}

	protected JavaBaseColumn.Owner buildMapKeyColumnOwner() {
		return new MapKeyColumnOwner();
	}

	// ********** Java completion proposals **********  

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.getOrderable().javaCompletionProposals(pos, filter, astRoot);
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
		return null;
	}

	protected Iterator<String> javaCandidateMapKeyNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateMapKeyNames(filter));
	}

	protected Iterator<String> candidateMapKeyNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateMapKeyNames(), filter);
	}

	public Iterator<String> candidateMapKeyNames() {
		return this.allTargetEntityAttributeNames();
	}


	// ********** metamodel **********  

	@Override
	protected String getMetamodelFieldTypeName() {
		return ((JavaPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldTypeName();
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
		this.orderable.validate(messages, reporter, astRoot);
		this.validateMapKey(messages, reporter, astRoot);
	}

	public void validateMapKey(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
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
			//validate map key attribute overrides
			//validate map key association overrides
		}
	}


	class MapKeyColumnOwner implements JavaBaseColumn.Owner {		
		public TypeMapping getTypeMapping() {
			return AbstractJavaMultiRelationshipMapping.this.getTypeMapping();
		}

		protected JoiningStrategy getPredominantJoiningStrategy() {
			return getRelationshipReference().getPredominantJoiningStrategy();
		}

		public String getDefaultTableName() {
			return getPredominantJoiningStrategy().getTableName();
		}

		public Table getDbTable(String tableName) {
			return getPredominantJoiningStrategy().getDbTable(tableName);
		}

		public String getDefaultColumnName() {
			return AbstractJavaMultiRelationshipMapping.this.getName() + "_KEY"; //$NON-NLS-1$
		}

		public boolean tableNameIsInvalid(String tableName) {
			return getPredominantJoiningStrategy().tableNameIsInvalid(tableName);
		}

		public java.util.Iterator<String> candidateTableNames() {
			return EmptyIterator.instance();
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return AbstractJavaMultiRelationshipMapping.this.getValidationTextRange(astRoot);
		}

		public IMessage buildTableNotValidMessage(BaseColumn column, TextRange textRange) {			
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.MAP_KEY_COLUMN_TABLE_NOT_VALID,
				new String[] {column.getTable(), column.getName(), getPredominantJoiningStrategy().getColumnTableNotValidDescription()}, 
				column,
				textRange
			);
		}

		public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
			return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.MAP_KEY_COLUMN_UNRESOLVED_NAME,
				new String[] {column.getName(), column.getDbTable().getName()}, 
				column, 
				textRange
			);
		}
	}
}
