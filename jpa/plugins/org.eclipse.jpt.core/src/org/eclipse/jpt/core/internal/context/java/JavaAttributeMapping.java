/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.List;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.validation.IJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public abstract class JavaAttributeMapping extends JavaContextModel
	implements IJavaAttributeMapping
{
	protected JavaPersistentAttributeResource persistentAttributeResource;
	

	protected JavaAttributeMapping(IJavaPersistentAttribute parent) {
		super(parent);
	}

	public void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource) {
		this.persistentAttributeResource = persistentAttributeResource;
	}

	protected JavaResource mappingResource() {
		return this.persistentAttributeResource.mappingAnnotation(annotationName());
	}
	
	public JavaPersistentAttribute persistentAttribute() {
		return (JavaPersistentAttribute) this.parent();
	}

	/**
	 * the persistent attribute can tell whether there is a "specified" mapping
	 * or a "default" one
	 */
	public boolean isDefault() {
		return this.persistentAttribute().mappingIsDefault();
	}

	protected boolean embeddableOwned() {
		return this.typeMapping().getKey() == IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}
	
	protected boolean entityOwned() {
		return this.typeMapping().getKey() == IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}
	
	public ITypeMapping typeMapping() {
		return this.persistentAttribute().typeMapping();
	}

	public String attributeName() {
		return this.persistentAttribute().getName();
	}
	
	public Table dbTable(String tableName) {
		return typeMapping().dbTable(tableName);
	}
	
	public ITextRange validationTextRange(CompilationUnit astRoot) {
		ITextRange textRange = this.mappingResource().textRange(astRoot);
		return (textRange != null) ? textRange : this.persistentAttribute().validationTextRange(astRoot);
	}

	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		this.persistentAttributeResource = persistentAttributeResource;
	}

	public String primaryKeyColumnName() {
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
	
	//************ Validation *************************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		addModifierMessages(messages, astRoot);
		addInvalidMappingMessage(messages, astRoot);
		
	}
	
	protected void addModifierMessages(List<IMessage> messages, CompilationUnit astRoot) {
		JavaPersistentAttribute attribute = this.persistentAttribute();
		if (attribute.getMapping().getKey() != IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY
				&& persistentAttributeResource.isForField()) {
			int flags;
			
			try {
				flags = persistentAttributeResource.getMember().getJdtMember().getFlags();
			} catch (JavaModelException jme) { 
				/* no error to log, in that case */ 
				return;
			}
			
			if (Flags.isFinal(flags)) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD,
						new String[] {attribute.getName()},
						attribute, attribute.validationTextRange(astRoot))
				);
			}
			
			if (Flags.isPublic(flags)) {
				messages.add(
					JpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						IJpaValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD,
						new String[] {attribute.getName()},
						attribute, attribute.validationTextRange(astRoot))
				);
				
			}
		}
	}
	
	protected void addInvalidMappingMessage(List<IMessage> messages, CompilationUnit astRoot) {
		if (! typeMapping().attributeMappingKeyAllowed(this.getKey())) {
			messages.add(
				JpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					IJpaValidationMessages.PERSISTENT_ATTRIBUTE_INVALID_MAPPING,
					new String[] {this.persistentAttribute().getName()},
					this, this.validationTextRange(astRoot))
			);
		}
	}
	
}
