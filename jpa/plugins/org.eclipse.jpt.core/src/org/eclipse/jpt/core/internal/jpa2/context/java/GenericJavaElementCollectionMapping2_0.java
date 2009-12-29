/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Fetchable;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaOrderable;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.CollectionTable2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
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

	protected FetchType specifiedFetch;
	
	protected final JavaOrderable orderable;
	
	protected final JavaCollectionTable2_0 collectionTable;

	public GenericJavaElementCollectionMapping2_0(JavaPersistentAttribute parent) {
		super(parent);
		this.orderable = getJpaFactory().buildJavaOrderable(this);
		this.collectionTable = getJpaFactory().buildJavaCollectionTable(this);
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
		this.initializeCollectionTable();
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

//	public Iterator<String> allTargetEntityAttributeNames() {
//		return new CompositeIterator<String>(
//			new TransformationIterator<AttributeMapping, Iterator<String>>(this.allTargetEntityAttributeMappings()) {
//				@Override
//				protected Iterator<String> transform(AttributeMapping mapping) {
//					return mapping.allMappingNames();
//				}
//		});
//	}
//
//	protected Iterator<AttributeMapping> allTargetEntityAttributeMappings() {
//		return (this.resolvedTargetEntity != null) ?
//				this.resolvedTargetEntity.allAttributeMappings() :
//				EmptyIterator.<AttributeMapping> instance();
//	}
//
//	protected String getTargetEntityIdAttributeName() {
//		PersistentAttribute attribute = this.getTargetEntityIdAttribute();
//		return (attribute == null) ? null : attribute.getName();
//	}
//
//	protected PersistentAttribute getTargetEntityIdAttribute() {
//		return (this.resolvedTargetEntity == null) ? null : this.resolvedTargetEntity.getIdAttribute();
//	}

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
	
	// ********** ordering **********  
	
	public JavaOrderable getOrderable() {
		return this.orderable;
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
		result = this.getOrderable().javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		
//		if (this.mapKeyNameTouches(pos, astRoot)) {
//			return this.javaCandidateMapKeyNames(filter);
//		}
		return null;
	}
	
	// ********** metamodel **********  
	//TODO metamodel support
	
	
	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateTargetClass(messages, astRoot);
		this.orderable.validate(messages, reporter, astRoot);
		this.collectionTable.validate(messages, reporter, astRoot);
	}

	protected void validateTargetClass(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getTargetClass() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.ELEMENT_COLLECTION_TARGET_CLASS_NOT_DEFINED,
					new String[] {this.getName()}, 
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
						new String[] {this.getTargetClass(), this.getName()}, 
						this, 
						this.getTargetClassTextRange(astRoot)
					)
				);
			}
		}
	}

	protected TextRange getTargetClassTextRange(CompilationUnit astRoot) {
		return this.getTextRange(this.mappingAnnotation.getTargetClassTextRange(astRoot), astRoot);
	}

	protected TextRange getTextRange(TextRange textRange, CompilationUnit astRoot) {
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}

}
