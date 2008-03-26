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
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.TableAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericJavaTable extends AbstractJavaTable implements JavaTable
{
	protected JavaResourcePersistentMember persistentResource;

	public GenericJavaTable(JavaEntity parent) {
		super(parent);
	}
	
	public void initializeFromResource(JavaResourcePersistentMember persistentResource) {
		this.persistentResource = persistentResource;
		initializeFromResource(tableResource());
	}

	
	//query for the table resource every time on setters.
	//call one setter and the tableResource could change. 
	//You could call more than one setter before this object has received any notification
	//from the java resource model
	@Override
	protected TableAnnotation tableResource() {
		//TODO get the NullTable from the resource model or build it here in the context model??
		return (TableAnnotation) this.persistentResource.getNonNullAnnotation(annotationName());
	}

	@Override
	protected String annotationName() {
		return TableAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	public JavaEntity getParent() {
		return (JavaEntity) super.getParent();
	}
	
	protected JavaEntity javaEntity() {
		return getParent();
	}
	
	protected Entity rootEntity() {
		return javaEntity().rootEntity();
	}
	
	@Override
	/**
	 * Table name default to the owning java entity name.
	 * If this entity is part of a single table inheritance hierarchy, table
	 * name defaults to the root entity's table name.
	 */
	protected String defaultName() {
		if (javaEntity().getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
			if (rootEntity() != javaEntity()) {
				return rootEntity().getTable().getName();
			}
		}
		return javaEntity().getName();
	}
	
	@Override
	protected String defaultSchema() {
		if (javaEntity().getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
			if (rootEntity() != javaEntity()) {
				return rootEntity().getTable().getSchema();
			}
		}
		return super.defaultSchema();
	}
	
	@Override
	protected String defaultCatalog() {
		if (javaEntity().getInheritanceStrategy() == InheritanceType.SINGLE_TABLE) {
			if (rootEntity() != javaEntity()) {
				return rootEntity().getTable().getCatalog();
			}
		}
		return super.defaultCatalog();
	}
	
	public void update(JavaResourcePersistentMember persistentResource) {
		this.persistentResource = persistentResource;
		this.update(tableResource());
	}

	//******************* validation **********************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		boolean doContinue = this.connectionProfileIsActive();
		String schema = getSchema();
		
		if (doContinue && ! hasResolvedSchema()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TABLE_UNRESOLVED_SCHEMA,
						new String[] {schema, getName()}, 
						this, 
						schemaTextRange(astRoot))
				);
			doContinue = false;
		}
		
		if (doContinue && ! isResolved()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TABLE_UNRESOLVED_NAME,
						new String[] {getName()}, 
						this, 
						nameTextRange(astRoot))
				);
		}
	}
}
