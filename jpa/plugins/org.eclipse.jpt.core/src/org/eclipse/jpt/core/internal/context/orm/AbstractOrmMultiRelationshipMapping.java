/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.CollectionMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.Orderable;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmMultiRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmOrderable;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.JoiningStrategyTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.MapKeyAttributeOverrideColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.MapKeyColumnValidator;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmPersistentAttribute2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.core.resource.orm.AbstractXmlMultiRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * ORM multi-relationship (m:m, 1:m) mapping
 */
public abstract class AbstractOrmMultiRelationshipMapping<T extends AbstractXmlMultiRelationshipMapping>
	extends AbstractOrmRelationshipMapping<T>
	implements OrmMultiRelationshipMapping, OrmCollectionMapping2_0
{
	protected final OrmOrderable orderable;
		
	protected String specifiedMapKey;
	protected boolean noMapKey = false;
	protected boolean pkMapKey = false;
	protected boolean customMapKey = false;
	
	protected String specifiedMapKeyClass;
	protected String defaultMapKeyClass;
	protected PersistentType resolvedMapKeyType;
	protected Embeddable resolvedMapKeyEmbeddable;
	protected Entity resolvedMapKeyEntity;

	protected Embeddable resolvedTargetEmbeddable;

	protected Type valueType;
	protected Type keyType;

	protected final OrmColumn mapKeyColumn;

	protected final OrmAttributeOverrideContainer mapKeyAttributeOverrideContainer;

	protected AbstractOrmMultiRelationshipMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.orderable = this.buildOrderable();
		this.resolvedTargetEmbeddable = this.resolveTargetEmbeddable();
		this.valueType = this.buildValueType();
		this.resolvedMapKeyType = this.resolveMapKeyType();
		this.resolvedMapKeyEmbeddable = this.resolveMapKeyEmbeddable();
		this.resolvedMapKeyEntity = this.resolveMapKeyEntity();
		this.initializeMapKey();
		this.defaultMapKeyClass = this.buildDefaultMapKeyClass();
		this.specifiedMapKeyClass = this.getResourceMapKeyClass();
		this.mapKeyColumn = getXmlContextNodeFactory().buildOrmColumn(this, this.buildMapKeyColumnOwner());
		this.mapKeyAttributeOverrideContainer = buildMapKeyAttributeOverrideContainer();
	}
	
	@Override
	public void update() {
		super.update();
		this.orderable.update();
		this.resolvedTargetEmbeddable = this.resolveTargetEmbeddable();
		this.updateValueType();
		this.setSpecifiedMapKeyClass_(this.getResourceMapKeyClass());
		this.setDefaultMapKeyClass(this.buildDefaultMapKeyClass());
		this.resolvedMapKeyType = this.resolveMapKeyType();//no need for change notification, use resolved target embeddable change notification instead?
		this.setResolvedMapKeyEmbeddable(this.resolveMapKeyEmbeddable());
		this.setResolvedMapKeyEntity(this.resolveMapKeyEntity());
		this.updateKeyType();
		this.updateMapKey();
		this.setDefaultMapKeyClass(this.buildDefaultMapKeyClass());
		this.setSpecifiedMapKeyClass_(this.getResourceMapKeyClass());
		this.mapKeyColumn.update(getResourceMapKeyColumn());
		this.mapKeyColumn.update(getResourceMapKeyColumn());
		this.mapKeyAttributeOverrideContainer.update();
}
	
	@Override
	protected String getResourceDefaultTargetEntity() {
		return this.getJavaPersistentAttribute().getMultiReferenceTargetTypeName();
	}
	
	public FetchType getDefaultFetch() {
		return CollectionMapping.DEFAULT_FETCH_TYPE;
	}
	
	// **************** order by ***********************************************

	protected OrmOrderable buildOrderable() {
		return this.isJpa2_0Compatible() ? 
			this.getXmlContextNodeFactory().buildOrmOrderable(this, this.buildOrderableOwner()) : 
			this.getXmlContextNodeFactory().buildOrmOrderable(this, new Orderable.Owner() {/*nothing*/});
	}

	public OrmOrderable getOrderable() {
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

	// ********** CollectionMapping implementation **********  

	protected Embeddable resolveTargetEmbeddable() {
		if (this.resolvedTargetType == null) {
			return null;
		}
		TypeMapping typeMapping = this.resolvedTargetType.getMapping();
		return (typeMapping instanceof Embeddable) ? (Embeddable) typeMapping : null;
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

	// **************** value type ************************************************

	public Type getValueType() {
		return this.valueType;
	}

	protected void setValueType(Type newValueType) {
		Type old = this.valueType;
		this.valueType = newValueType;
		firePropertyChanged(VALUE_TYPE_PROPERTY, old, newValueType);
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

	protected void initializeValueType() {
		this.valueType = this.buildValueType();
	}

	protected void updateValueType() {
		this.setValueType(this.buildValueType()); 
	}

	// **************** key type ************************************************
	
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

	protected void initializeKeyType() {
		this.keyType = this.buildKeyType();
	}

	protected void updateKeyType() {
		this.setKeyType(this.buildKeyType());
	}

	// **************** map key ************************************************
	
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
		return this.allTargetEntityAttributeNames();
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

	// ************** value column ********************************************

	public OrmColumn getMapKeyColumn() {
		return this.mapKeyColumn;
	}

	protected XmlColumn getResourceMapKeyColumn() {
		return this.resourceAttributeMapping.getMapKeyColumn();
	}

	protected OrmColumn.Owner buildMapKeyColumnOwner() {
		return new MapKeyColumnOwner();
	}

	public OrmAttributeOverrideContainer getMapKeyAttributeOverrideContainer() {
		return this.mapKeyAttributeOverrideContainer;
	}

	protected OrmAttributeOverrideContainer buildMapKeyAttributeOverrideContainer() {
		return getXmlContextNodeFactory().buildOrmAttributeOverrideContainer(this, new MapKeyAttributeOverrideContainerOwner());
	}

	protected JavaAttributeOverride getJavaMapKeyAttributeOverrideNamed(String attributeName) {
		if (getJavaMultiRelationshipMapping() != null) {
			return getJavaMultiRelationshipMapping().getMapKeyAttributeOverrideContainer().getAttributeOverrideNamed(attributeName);
		}
		return null;
	}	

	protected JavaCollectionMapping2_0 getJavaMultiRelationshipMapping() {
		if (this.getJavaPersistentAttribute() == null) {
			return null;
		}
		AttributeMapping javaAttributeMapping = this.getJavaPersistentAttribute().getMapping();
		if (javaAttributeMapping.getKey() == this.getKey()) {
			return ((JavaCollectionMapping2_0) javaAttributeMapping);
		}
		return null;
	}

	// ********** metamodel **********  

	@Override
	protected String getMetamodelFieldTypeName() {
		return ((OrmPersistentAttribute2_0) getPersistentAttribute()).getMetamodelContainerFieldTypeName();
	}

	@Override
	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		this.addMetamodelFieldMapKeyTypeArgumentNameTo(typeArgumentNames);
		super.addMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
	}

	protected void addMetamodelFieldMapKeyTypeArgumentNameTo(ArrayList<String> typeArgumentNames) {
		String keyTypeName = ((OrmPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldMapKeyTypeName();
		if (keyTypeName != null) {
			typeArgumentNames.add(keyTypeName);
		}
	}

	public String getMetamodelFieldMapKeyTypeName() {
		return MappingTools.getMetamodelFieldMapKeyTypeName(this);
	}


	//************* refactoring *************

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createRenameTypeEdits(originalType, newName),
			this.createMapKeyClassRenameTypeEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createMapKeyClassRenameTypeEdits(IType originalType, String newName) {
		if (this.specifiedMapKeyClass != null) {
			String originalName = originalType.getFullyQualifiedName('.');
			if (this.resolvedMapKeyType != null && this.resolvedMapKeyType.isFor(originalName)) {
				return new SingleElementIterable<ReplaceEdit>(this.createMapKeyClassRenameTypeEdit(originalType, newName));
			}
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createMapKeyClassRenameTypeEdit(IType originalType, String newName) {
		return this.resourceAttributeMapping.createRenameMapKeyClassEdit(originalType, newName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			super.createMoveTypeEdits(originalType, newPackage),
			this.createMapKeyClassMoveTypeEdits(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createMapKeyClassMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (this.specifiedMapKeyClass != null) {
			String originalName = originalType.getFullyQualifiedName('.');
			if (this.resolvedMapKeyType != null && this.resolvedMapKeyType.isFor(originalName)) {
				return new SingleElementIterable<ReplaceEdit>(this.createMapKeyClassRenamePackageEdit(newPackage.getElementName()));
			}
		}
		return EmptyIterable.instance();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createRenamePackageEdits(originalPackage, newName),
			this.createMapKeyClassRenamePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createMapKeyClassRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		if (this.specifiedMapKeyClass != null) {
			if (this.resolvedMapKeyType != null && this.resolvedMapKeyType.isIn(originalPackage)) {
				return new SingleElementIterable<ReplaceEdit>(this.createMapKeyClassRenamePackageEdit(newName));
			}
		}
		return EmptyIterable.instance();
	}

	protected ReplaceEdit createMapKeyClassRenamePackageEdit(String newName) {
		return this.resourceAttributeMapping.createRenameMapKeyClassPackageEdit(newName);
	}


	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.orderable.validate(messages, reporter);
		this.validateMapKey(messages, reporter);
	}

	public void validateMapKey(List<IMessage> messages, IReporter reporter) {
		if (getMapKey() != null || getMapKeyAnnotation() != null) {
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
			getMapKeyAttributeOverrideContainer().validate(messages, reporter);
			//validate map key association overrides
		}
	}
	protected MapKeyAnnotation getMapKeyAnnotation() {
		if (!isVirtual()) {
			return null;
		}
		JavaResourcePersistentAttribute jrpa = getJavaResourcePersistentAttribute();
		return jrpa == null ? null : (MapKeyAnnotation) jrpa.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
	}


	protected class MapKeyColumnOwner implements OrmColumn.Owner {
		public TypeMapping getTypeMapping() {
			return AbstractOrmMultiRelationshipMapping.this.getTypeMapping();
		}

		protected OrmJoiningStrategy getPredominantJoiningStrategy() {
			return getRelationshipReference().getPredominantJoiningStrategy();
		}

		public String getDefaultTableName() {
			return getPredominantJoiningStrategy().getTableName();
		}

		public Table getDbTable(String tableName) {
			return getPredominantJoiningStrategy().getDbTable(tableName);
		}

		public String getDefaultColumnName() {
			return AbstractOrmMultiRelationshipMapping.this.getName() + "_KEY"; //$NON-NLS-1$
		}

		public boolean tableNameIsInvalid(String tableName) {
			return getRelationshipReference().getPredominantJoiningStrategy().tableNameIsInvalid(tableName);
		}

		public java.util.Iterator<String> candidateTableNames() {
			return EmptyIterator.instance();
		}

		public XmlColumn getResourceColumn() {
			return AbstractOrmMultiRelationshipMapping.this.resourceAttributeMapping.getMapKeyColumn();
		}

		public void addResourceColumn() {
			AbstractOrmMultiRelationshipMapping.this.resourceAttributeMapping.setMapKeyColumn(OrmFactory.eINSTANCE.createXmlColumn());
		}

		public void removeResourceColumn() {
			AbstractOrmMultiRelationshipMapping.this.resourceAttributeMapping.setMapKeyColumn(null);
		}

		public TextRange getValidationTextRange() {
			return AbstractOrmMultiRelationshipMapping.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new MapKeyColumnValidator(getPersistentAttribute(), (BaseColumn) column, (BaseColumnTextRangeResolver) textRangeResolver, new JoiningStrategyTableDescriptionProvider(getPredominantJoiningStrategy()));
		}
	}

	protected class MapKeyAttributeOverrideContainerOwner
		implements OrmAttributeOverrideContainer.Owner
	{
		public OrmTypeMapping getTypeMapping() {
			return AbstractOrmMultiRelationshipMapping.this.getTypeMapping();
		}
		
		public TypeMapping getOverridableTypeMapping() {
			return AbstractOrmMultiRelationshipMapping.this.getResolvedMapKeyEmbeddable();
		}
		
		public Iterator<String> allOverridableNames() {
			TypeMapping typeMapping = getOverridableTypeMapping();
			return (typeMapping == null) ? 
					EmptyIterator.<String>instance()
					: typeMapping.allOverridableAttributeNames();
		}
		
		protected JavaAttributeOverride getJavaAttributeOverrideNamed(String attributeName) {
			return AbstractOrmMultiRelationshipMapping.this.getJavaMapKeyAttributeOverrideNamed(attributeName);
		}
		
		public EList<XmlAttributeOverride> getResourceAttributeOverrides() {
			return AbstractOrmMultiRelationshipMapping.this.resourceAttributeMapping.getMapKeyAttributeOverrides();
		}
		
		public Column resolveOverriddenColumn(String attributeOverrideName) {
			if (getPersistentAttribute().isVirtual() && !getTypeMapping().isMetadataComplete()) {
				JavaAttributeOverride javaAttributeOverride = getJavaAttributeOverrideNamed(attributeOverrideName);
				if (javaAttributeOverride != null && !javaAttributeOverride.isVirtual()) {
					return javaAttributeOverride.getColumn();
				}
			}
			return MappingTools.resolveOverridenColumn(getOverridableTypeMapping(), attributeOverrideName);
		}
		
		
		public XmlColumn buildVirtualXmlColumn(Column overridableColumn, String attributeName, boolean isMetadataComplete) {
			return new VirtualXmlAttributeOverrideColumn(overridableColumn);
		}
		
		protected OrmJoiningStrategy getPredominantJoiningStrategy() {
			return getRelationshipReference().getPredominantJoiningStrategy();
		}
		
		public String getDefaultTableName() {
			return getPredominantJoiningStrategy().getTableName();
		}
		
		public Table getDbTable(String tableName) {
			return getPredominantJoiningStrategy().getDbTable(tableName);
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
		
		public JptValidator buildColumnValidator(BaseOverride override, BaseColumn column, BaseColumn.Owner owner, BaseColumnTextRangeResolver textRangeResolver) {
			return new MapKeyAttributeOverrideColumnValidator(getPersistentAttribute(), (AttributeOverride) override, column, textRangeResolver, new JoiningStrategyTableDescriptionProvider(getPredominantJoiningStrategy()));
		}
		
		public TextRange getValidationTextRange() {
			return AbstractOrmMultiRelationshipMapping.this.getValidationTextRange();
		}
	}
}
