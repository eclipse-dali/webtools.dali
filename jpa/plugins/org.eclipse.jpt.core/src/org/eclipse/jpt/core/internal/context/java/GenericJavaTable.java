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
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.TableAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public class GenericJavaTable
	extends AbstractJavaTable
	implements JavaTable
{
	protected JavaResourcePersistentMember resourcePersistentMember;

	public GenericJavaTable(JavaEntity parent) {
		super(parent);
	}
	
	public void initialize(JavaResourcePersistentMember pr) {
		this.resourcePersistentMember = pr;
		initialize(getResourceTable());
	}

	//query for the table resource every time on setters.
	//call one setter and the tableResource could change. 
	//You could call more than one setter before this object has received any notification
	//from the java resource model
	@Override
	protected TableAnnotation getResourceTable() {
		//TODO get the NullTable from the resource model or build it here in the context model??
		return (TableAnnotation) this.resourcePersistentMember.getNonNullAnnotation(getAnnotationName());
	}

	@Override
	protected String getAnnotationName() {
		return TableAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	public JavaEntity getParent() {
		return (JavaEntity) super.getParent();
	}
	
	protected JavaEntity getJavaEntity() {
		return getParent();
	}
	
	@Override
	protected String buildDefaultName() {
		return this.getJavaEntity().getDefaultTableName();
	}

	/**
	 * Just to remember:
	 *   Entity.getDefaultSchema()
	 *     check inheritance - get default schema from root
	 *   EntityMappings.getSchema()
	 *     check for specified schema
	 *   PersistenceUnit.getDefaultSchema()
	 *     PersistenceUnitDefaults.getSchema()
	 *   JpaProject.getDefaultSchema()
	 *     check for user override project setting
	 *   Catalog.getDefaultSchema()
	 *     or
	 *   Database.getDefaultSchema()
	 */
	@Override
	protected String buildDefaultSchema() {
		return this.getJavaEntity().getDefaultSchema();
	}
	
	@Override
	protected String buildDefaultCatalog() {
		return this.getJavaEntity().getDefaultCatalog();
	}

	public void update(JavaResourcePersistentMember jrpm) {
		this.resourcePersistentMember = jrpm;
		this.update(getResourceTable());
	}


	//******************* validation **********************
	
	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		if (this.connectionProfileIsActive()) {
			this.validateAgainstDatabase(messages, astRoot);
		}
	}

	protected void validateAgainstDatabase(List<IMessage> messages, CompilationUnit astRoot) {
		if ( ! this.hasResolvedCatalog()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.TABLE_UNRESOLVED_CATALOG,
					new String[] {this.getCatalog(), this.getName()}, 
					this, 
					this.getCatalogTextRange(astRoot)
				)
			);
			return;
		}
		
		if ( ! this.hasResolvedSchema()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.TABLE_UNRESOLVED_SCHEMA,
					new String[] {this.getSchema(), this.getName()}, 
					this, 
					this.getSchemaTextRange(astRoot)
				)
			);
			return;
		}
		
		if ( ! this.isResolved()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.TABLE_UNRESOLVED_NAME,
					new String[] {this.getName()}, 
					this, 
					this.getNameTextRange(astRoot)
				)
			);
		}
	}

}
