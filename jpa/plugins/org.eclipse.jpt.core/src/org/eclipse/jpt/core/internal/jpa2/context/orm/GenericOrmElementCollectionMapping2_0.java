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
import java.util.List;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Fetchable;
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
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
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
	
	public GenericOrmElementCollectionMapping2_0(OrmPersistentAttribute parent, XmlElementCollection resourceMapping) {
		super(parent, resourceMapping);
		this.specifiedFetch = this.getResourceFetch();
		this.orderable = getXmlContextNodeFactory().buildOrmOrderable(this);
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
	
	protected String getResourceTargetClass() {
		return this.resourceAttributeMapping.getTargetClass();
	}
	
	protected String buildDefaultTargetClass() {
		if (this.getJavaPersistentAttribute() != null) {
			return getResourceDefaultTargetClass();
		}
		return null;
	}
	
	protected String getResourceDefaultTargetClass() {
		return this.getJavaPersistentAttribute().getMultiReferenceTargetTypeName();		
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
		return this.getPersistentAttribute().getPersistentType().getDefaultPackage();
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
	


	// ********** metamodel **********  
	//TODO map metamodel
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
//		String keyTypeName = ((JavaPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelContainerFieldMapKeyTypeName();
//		if (keyTypeName != null) {
//			typeArgumentNames.add(keyTypeName);
//		}
	}

//	public String getMetamodelFieldMapKeyTypeName() {
//		return MappingTools.getMetamodelFieldMapKeyTypeName(this);
//	}
	

	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateTargetClass(messages);
		this.getOrderable().validate(messages, reporter);
		this.getCollectionTable().validate(messages, reporter);
		this.getValueColumn().validate(messages, reporter);
		this.getConverter().validate(messages, reporter);
		this.getValueAttributeOverrideContainer().validate(messages, reporter);
		this.getValueAssociationOverrideContainer().validate(messages, reporter);

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
		 * the default table name is always valid and a specified table name
		 * is prohibited (which will be handled elsewhere)
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return false;
		}
		
		public TextRange getValidationTextRange() {
			return GenericOrmElementCollectionMapping2_0.this.getValidationTextRange();
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
		 * the default table name is always valid and a specified table name
		 * is prohibited (which will be handled elsewhere)
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return false;
		}

		public String getDefaultTableName() {
			return GenericOrmElementCollectionMapping2_0.this.getCollectionTable().getName();
		}

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			return GenericOrmElementCollectionMapping2_0.this.getCollectionTable().getDbTable();
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
		 * the default table name is always valid and a specified table name
		 * is prohibited (which will be handled elsewhere)
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return false;
		}
	}
}
