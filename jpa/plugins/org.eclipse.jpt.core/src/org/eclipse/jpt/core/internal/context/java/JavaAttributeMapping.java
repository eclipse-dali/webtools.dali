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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.db.internal.Table;


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
		ITextRange textRange = this.persistentAttributeResource.textRange(astRoot);
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
}
