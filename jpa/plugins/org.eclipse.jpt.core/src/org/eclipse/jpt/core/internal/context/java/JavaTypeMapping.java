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

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public abstract class JavaTypeMapping extends JavaContextModel
	implements IJavaTypeMapping
{
	protected JavaPersistentTypeResource persistentTypeResource;
	

	protected JavaTypeMapping(IJavaPersistentType parent) {
		super(parent);
	}
	
	protected JavaResource mappingResource() {
		return this.persistentTypeResource.mappingAnnotation(annotationName());
	}

	//***************** ITypeMapping implementation *****************
	
	public IJavaPersistentType persistentType() {
		return (IJavaPersistentType) parent();
	}

	public String tableName() {
		return null;
	}

	public Table primaryDbTable() {
		return null;
	}

	public Table dbTable(String tableName) {
		return null;
	}

	public Schema dbSchema() {
		return null;
	}

	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return true;
	}

	public Iterator<ITable> associatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<String> associatedTableNamesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Iterator<ITable> associatedTablesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Iterator<String> overridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> overridableAttributeNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allOverridableAttributeNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allOverridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}
	
	//******************** updatating *********************
	public void initializeFromResource(JavaPersistentTypeResource persistentTypeResource) {
		this.persistentTypeResource = persistentTypeResource;
	}

	public void update(JavaPersistentTypeResource persistentTypeResource) {
		this.persistentTypeResource = persistentTypeResource;
	}
	
	//******************** validation *********************

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		ITextRange textRange = this.mappingResource().textRange(astRoot);
		return (textRange != null) ? textRange : this.persistentType().validationTextRange(astRoot);
	}

}
