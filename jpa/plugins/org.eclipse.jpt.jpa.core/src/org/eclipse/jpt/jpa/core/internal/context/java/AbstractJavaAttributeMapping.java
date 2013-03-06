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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.ColumnMapping;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.SimpleMetamodelField;
import org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java attribute mapping
 * <p>
 * The mapping annotation is <code>null</code> for default mappings.
 * It will be faulted into existence whenever {@link #getAnnotationForUpdate()}
 * is called. It will <em>not</em> return to <code>null</code> automatically
 * when all its state is defaulted; it must be explicitly cleared via
 * {@link JavaSpecifiedPersistentAttribute#setMappingKey(String)}.
 */
public abstract class AbstractJavaAttributeMapping<A extends Annotation>
	extends AbstractJavaContextModel<JavaSpecifiedPersistentAttribute>
	implements JavaAttributeMapping, AttributeMapping2_0
{
	protected boolean default_;


	protected AbstractJavaAttributeMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
		this.default_ = this.buildDefault();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.updateDefault();
	}

	@Override
	public void update() {
		super.update();
	}


	// ********** name **********

	public String getName() {
		return this.getPersistentAttribute().getName();
	}


	// ********** annotation **********

	@SuppressWarnings("unchecked")
	public A getMappingAnnotation() {
		return (A) this.getAnnotation_();
	}

	protected Annotation getAnnotation_() {
		return this.getResourceAttribute().getAnnotation(this.getAnnotationName());
	}

	protected abstract String getAnnotationName();

	/**
	 * This method should only be called on mappings that can occur by default
	 * (e.g. <code>Basic</code>, <code>Embedded</code>, <code>OneToOne</code>,
	 * and <code>OneToMany</code>).
	 */
	public A getAnnotationForUpdate() {
		A annotation = this.getMappingAnnotation();
		if (annotation == null) {
			this.getPersistentAttribute().setMappingKey(this.getKey());
			annotation = this.getMappingAnnotation();
			if (annotation == null) {
				throw new IllegalStateException("missing annotation: " + this); //$NON-NLS-1$
			}
		}
		return annotation;
	}


	// ********** default **********

	public boolean isDefault() {
		return this.default_;
	}

	protected void setDefault(boolean default_) {
		boolean old = this.default_;
		this.default_ = default_;
		this.firePropertyChanged(DEFAULT_PROPERTY, old, default_);
	}

	public void updateDefault() {
		this.setDefault(this.buildDefault());
	}

	protected boolean buildDefault() {
		return this.getMappingAnnotation() == null;
	}


	// ********** misc **********

	public JavaSpecifiedPersistentAttribute getPersistentAttribute() {
		return this.parent;
	}

	public TypeMapping getTypeMapping() {
		return this.getPersistentAttribute().getDeclaringTypeMapping();
	}

	public JavaResourceAttribute getResourceAttribute() {
		return this.getPersistentAttribute().getResourceAttribute();
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

	public boolean isRelationshipOwner() {
		return false;
	}

	public boolean isOwnedBy(AttributeMapping mapping) {
		return false;
	}

	public boolean validatesAgainstDatabase() {
		return this.getTypeMapping().validatesAgainstDatabase();
	}

	public Table resolveDbTable(String tableName) {
		return this.getTypeMapping().resolveDbTable(tableName);
	}

	protected JavaSpecifiedPersistentAttribute getJavaPersistentAttribute() {
		return this.getPersistentAttribute().getJavaPersistentAttribute();
	}

	public Iterable<Generator> getGenerators() {
		return EmptyIterable.instance();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}


	// ********** embedded mappings **********

	public Iterable<String> getAllMappingNames() {
		return new SingleElementIterable<String>(this.getName());
	}

	public Iterable<String> getAllOverridableAttributeMappingNames() {
		return this.isOverridableAttributeMapping() ?
				new SingleElementIterable<String>(this.getName()) :
				EmptyIterable.<String>instance();
	}

	public Iterable<String> getAllOverridableAssociationMappingNames() {
		return this.isOverridableAssociationMapping() ?
				new SingleElementIterable<String>(this.getName()) :
				EmptyIterable.<String>instance();
	}

	public SpecifiedColumn resolveOverriddenColumn(String attributeName) {
		ColumnMapping mapping = this.resolveColumnMapping(attributeName);
		return (mapping == null) ? null : mapping.getColumn();
	}

	protected ColumnMapping resolveColumnMapping(String name) {
		AttributeMapping mapping = this.resolveAttributeMapping(name);
		return ((mapping != null) && mapping.isOverridableAttributeMapping()) ? (ColumnMapping) mapping : null;
	}

	public SpecifiedRelationship resolveOverriddenRelationship(String attributeName) {
		RelationshipMapping mapping = this.resolveRelationshipMapping(attributeName);
		return (mapping == null) ? null : mapping.getRelationship();
	}

	protected RelationshipMapping resolveRelationshipMapping(String name) {
		AttributeMapping mapping = this.resolveAttributeMapping(name);
		return ((mapping != null) && mapping.isOverridableAssociationMapping()) ? (RelationshipMapping) mapping : null;
	}

	public AttributeMapping resolveAttributeMapping(String attributeName) {
		return this.getName().equals(attributeName) ? this : null;
	}

	protected Transformer<String, String> buildQualifierTransformer() {
		return new MappingTools.QualifierTransformer(this.getName());
	}

	protected String unqualify(String attributeName) {
		return MappingTools.unqualify(this.getName(), attributeName);
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
		return ((SpecifiedPersistentAttribute2_0) this.getPersistentAttribute()).getMetamodelTypeName();
	}

	protected String getMetamodelFieldName() {
		return this.getName();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateMappingType(messages);
	}

	protected void validateMappingType(List<IMessage> messages) {
		if ( ! this.getTypeMapping().attributeMappingKeyAllowed(this.getKey())) {
			if (this.getPersistentAttribute().isVirtual()) {
				messages.add(
						this.buildValidationMessage(
							this.getVirtualPersistentAttributeTextRange(),
							JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING, //TODO KFB - different message for virtual attribute
							this.getName()
						)
					);
			}
			else {
				messages.add(
					this.buildValidationMessage(
						this.getValidationTextRange(),
						JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING,
						this.getName()
					)
				);
			}
		}
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getMappingAnnotationTextRange();
		return (textRange != null) ? textRange : this.getPersistentAttribute().getValidationTextRange();
	}

	protected TextRange getVirtualPersistentAttributeTextRange() {
		return this.getPersistentAttribute().getValidationTextRange();
	}

	protected TextRange getMappingAnnotationTextRange() {
		A annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getTextRange();
	}
}
