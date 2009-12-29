/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import java.util.List;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Fetchable;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmOrderable;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.core.utility.TextRange;
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
	
	public GenericOrmElementCollectionMapping2_0(OrmPersistentAttribute parent, XmlElementCollection resourceMapping) {
		super(parent, resourceMapping);
		this.specifiedFetch = this.getResourceFetch();
		this.orderable = getXmlContextNodeFactory().buildOrmOrderable(this);
		this.specifiedTargetClass = getResourceTargetClass();
		this.defaultTargetClass = buildDefaultTargetClass();
		this.resolvedTargetType = this.buildResolvedTargetType();
		this.resolvedTargetEmbeddable = buildResolvedTargetEmbeddable();
		this.collectionTable = getXmlContextNodeFactory().buildOrmCollectionTable(this, getResourceCollectionTable());
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
		ElementCollectionMapping2_0 javaMapping = getJavaElementCollectionMapping();
		if (javaMapping != null) {
			if (getPersistentAttribute().isVirtual() && !getTypeMapping().isMetadataComplete()) {
				return javaMapping.getTargetClass();
			}
		}
		if (this.getJavaPersistentAttribute() != null) {
			return getResourceDefaultTargetClass();
		}
		return null;
	}
	
	protected ElementCollectionMapping2_0 getJavaElementCollectionMapping() {
		if (this.getJavaPersistentAttribute() == null) {
			return null;
		}
		AttributeMapping javaAttributeMapping = this.getJavaPersistentAttribute().getMapping();
		if (javaAttributeMapping instanceof ElementCollectionMapping2_0) {
			return ((ElementCollectionMapping2_0) javaAttributeMapping);
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

	// **************** ordering ***********************************************

	public OrmOrderable getOrderable() {
		return this.orderable;
	}
	
	
	// ********** metamodel **********  
	//TODO metamodel support
	

	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateTargetClass(messages);
		this.orderable.validate(messages, reporter);
		this.collectionTable.validate(messages, reporter);
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
}
