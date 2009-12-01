/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.jpa2.context.SimpleMetamodelField;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.AttributeMapping2_0;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java attribute mapping
 */
public abstract class AbstractJavaAttributeMapping<T extends Annotation>
	extends AbstractJavaJpaContextNode
	implements JavaAttributeMapping, AttributeMapping2_0
{
	protected T mappingAnnotation;
	
	protected Vector<String> supportingAnnotationNames;
	

	protected AbstractJavaAttributeMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	@SuppressWarnings("unchecked")
	public void initialize(Annotation annotation) {
		this.mappingAnnotation = (T) annotation;
		this.initialize();
	}

	protected void initialize() {	
		// do nothing by default
	}

	@SuppressWarnings("unchecked")
	public void update(Annotation annotation) {
		this.mappingAnnotation = (T) annotation;
		this.update();
	}
	
	protected void update() {
		// do nothing by default
	}

	@Override
	public JavaPersistentAttribute getParent() {
		return (JavaPersistentAttribute) super.getParent();
	}
	
	public JavaPersistentAttribute getPersistentAttribute() {
		return this.getParent();
	}

	protected JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return this.getParent().getResourcePersistentAttribute();
	}
	
	public T getMappingAnnotation() {
		return this.mappingAnnotation;
	}
		
	/**
	 * the persistent attribute can tell whether there is a "specified" mapping
	 * or a "default" one
	 */
	public boolean isDefault() {
		return this.getPersistentAttribute().mappingIsDefault(this);
	}

	public boolean shouldValidateAgainstDatabase() {
		return this.getTypeMapping().shouldValidateAgainstDatabase();
	}
	
	public TypeMapping getTypeMapping() {
		return this.getPersistentAttribute().getTypeMapping();
	}

	public String getName() {
		return this.getPersistentAttribute().getName();
	}
	
	public Table getDbTable(String tableName) {
		return this.getTypeMapping().getDbTable(tableName);
	}

	public String getPrimaryKeyColumnName() {
		return null;
	}

	public boolean isOverridableAttributeMapping() {
		return false;
	}

	public boolean isOverridableAssociationMapping() {
		return false;
	}

	public boolean isIdMapping() {
		return false;
	}
	
	public boolean isOwnedBy(RelationshipMapping mapping) {
		return false;
	}
	
	public Iterator<String> allMappingNames() {
		return new SingleElementIterator<String>(getName());
	}
	
	public AttributeMapping resolveAttributeMapping(String name) {
		if (getName().equals(name)) {
			return this;
		}
		return null;
	}
	
	public Iterator<String> allOverrideableAttributeMappingNames() {
		if (isOverridableAttributeMapping()) {
			return allMappingNames();
		}
		return EmptyIterator.<String> instance();
	}
	
	public Iterator<String> allOverrideableAssociationMappingNames() {
		if (isOverridableAssociationMapping()) {
			return allMappingNames();
		}
		return EmptyIterator.<String> instance();
	}
	
	public Column resolveOverridenColumn(String attributeName) {
		ColumnMapping columnMapping = this.resolveColumnMapping(attributeName);
		return columnMapping == null ? null : columnMapping.getColumn();
	}
	
	protected ColumnMapping resolveColumnMapping(String name) {
		AttributeMapping attributeMapping = resolveAttributeMapping(name);
		if (attributeMapping != null && attributeMapping.isOverridableAttributeMapping()) {
			return (ColumnMapping) attributeMapping;
		}
		return null;
	}
	
	public RelationshipReference resolveRelationshipReference(String attributeName) {
		RelationshipMapping relationshipMapping = this.resolveRelationshipMapping(attributeName);
		return relationshipMapping == null ? null : relationshipMapping.getRelationshipReference();
	}
	
	protected RelationshipMapping resolveRelationshipMapping(String name) {
		AttributeMapping attributeMapping = resolveAttributeMapping(name);
		if (attributeMapping != null && attributeMapping.isOverridableAssociationMapping()) {
			return (RelationshipMapping) attributeMapping;
		}
		return null;
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}


	// ********** supporting annotation names **********

	public Iterable<String> getSupportingAnnotationNames() {
		if (this.supportingAnnotationNames == null) {
			this.supportingAnnotationNames = this.buildSupportingAnnotationNames();
		}
		return this.supportingAnnotationNames;
	}

	protected Vector<String> buildSupportingAnnotationNames() {
		Vector<String> names = new Vector<String>();
		this.addSupportingAnnotationNamesTo(names);
		return names;
	}

	protected void addSupportingAnnotationNamesTo(@SuppressWarnings("unused") Vector<String> names) {
		// the default is none
	}


	// ********** metamodel **********

	public MetamodelField getMetamodelField() {
		return new SimpleMetamodelField(
				this.getMetamodelFieldModifiers(),
				this.getMetamodelFieldTypeName(),
				this.getMetamodelFieldTypeArgumentNames(),
				this.getMetamodelFieldName()
			);
	}

	protected Iterable<String> getMetamodelFieldModifiers() {
		return STANDARD_METAMODEL_FIELD_MODIFIERS;
	}

	/**
	 * most mappings are "singular"
	 */
	protected String getMetamodelFieldTypeName() {
		return JPA2_0.SINGULAR_ATTRIBUTE;
	}

	protected final Iterable<String> getMetamodelFieldTypeArgumentNames() {
		ArrayList<String> typeArgumentNames = new ArrayList<String>(3);
		typeArgumentNames.add(this.getTypeMapping().getPersistentType().getName());
		this.addMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
		return typeArgumentNames;
	}

	/**
	 * by default, we add only the mapping's attribute type name;
	 * but collection relationship mappings will also need to add the key type
	 * name if the "collection" is of type java.util.Map
	 */
	protected void addMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames) {
		typeArgumentNames.add(this.getMetamodelTypeName());
	}

	public String getMetamodelTypeName() {
		return ((JavaPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelTypeName();
	}

	protected String getMetamodelFieldName() {
		return this.getName();
	}


	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateMappingType(messages, astRoot);
	}
	
	protected void validateMappingType(List<IMessage> messages, CompilationUnit astRoot) {
		if ( ! this.getTypeMapping().attributeMappingKeyAllowed(this.getKey())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING,
					new String[] {this.getName()},
					this,
					this.getValidationTextRange(astRoot)
				)
			);
		}
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getMappingAnnotationTextRange(astRoot);
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange(astRoot);
	}
	
	protected TextRange getMappingAnnotationTextRange(CompilationUnit astRoot) {
		return (this.mappingAnnotation == null) ? null : this.mappingAnnotation.getTextRange(astRoot);
	}
	
}
