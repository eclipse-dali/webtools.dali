/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public abstract class AbstractJavaAttributeMapping<T extends JavaResourceNode>
	extends AbstractJavaJpaContextNode
	implements JavaAttributeMapping
{
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	

	protected AbstractJavaAttributeMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	@SuppressWarnings("unchecked")
	protected T getResourceMapping() {
		if (this.isDefault()) {
			return (T) this.resourcePersistentAttribute.getNullMappingAnnotation(getAnnotationName());
		}
		return (T) this.resourcePersistentAttribute.getMappingAnnotation(getAnnotationName());
	}
	
	public GenericJavaPersistentAttribute getPersistentAttribute() {
		return (GenericJavaPersistentAttribute) this.getParent();
	}

	protected JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return this.resourcePersistentAttribute;
	}
	
	/**
	 * the persistent attribute can tell whether there is a "specified" mapping
	 * or a "default" one
	 */
	public boolean isDefault() {
		return this.getPersistentAttribute().mappingIsDefault(this);
	}

	protected boolean embeddableOwned() {
		return this.getTypeMapping().getKey() == MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}
	
	protected boolean ownerIsEntity() {
		return this.getTypeMapping().getKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}
	
	public TypeMapping getTypeMapping() {
		return this.getPersistentAttribute().getTypeMapping();
	}

	protected String getAttributeName() {
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

	public void initialize(JavaResourcePersistentAttribute javaResourcePersistentAttribute) {
		this.resourcePersistentAttribute = javaResourcePersistentAttribute;
		initialize(getResourceMapping());
	}

	protected void initialize(@SuppressWarnings("unused") T resourceMapping) {
		// do nothing by default
	}

	public void update(JavaResourcePersistentAttribute javaResourcePersistentAttribute) {
		this.resourcePersistentAttribute = javaResourcePersistentAttribute;
		this.update(getResourceMapping());
	}
	
	protected void update(@SuppressWarnings("unused") T resourceMapping) {
		// do nothing by default
	}

	
	//************ Validation *************************
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		this.validateModifiers(messages, astRoot);
		this.validateMappingType(messages, astRoot);
	}
	
	protected void validateModifiers(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getPersistentAttribute().getMappingKey() == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			return;
		}
		
		if (this.resourcePersistentAttribute.isForField()) {
			if (this.resourcePersistentAttribute.isFinal()) {
				messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD, astRoot));
			}
			
			if (this.resourcePersistentAttribute.isPublic()) {
				messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD, astRoot));
			}
		}
	}

	protected IMessage buildAttributeMessage(String msgID, CompilationUnit astRoot) {
		JavaPersistentAttribute attribute = this.getPersistentAttribute();
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				msgID,
				new String[] {attribute.getName()},
				attribute,
				attribute.getValidationTextRange(astRoot)
			);
	}
	
	protected void validateMappingType(List<IMessage> messages, CompilationUnit astRoot) {
		if ( ! this.getTypeMapping().attributeMappingKeyAllowed(this.getKey())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING,
					new String[] {this.getPersistentAttribute().getName()},
					this,
					this.getValidationTextRange(astRoot)
				)
			);
		}
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getResourceMappingTextRange(astRoot);
		return (textRange != null) ? textRange : this.getPersistentAttribute().getValidationTextRange(astRoot);
	}
	
	protected TextRange getResourceMappingTextRange(CompilationUnit astRoot) {
		T resourceMapping = this.getResourceMapping();
		return (resourceMapping == null) ? null : resourceMapping.getTextRange(astRoot);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getAttributeName());
	}

}
