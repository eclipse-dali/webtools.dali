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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public abstract class AbstractJavaTypeMapping extends JavaContextModel
	implements JavaTypeMapping
{
	protected JavaResourcePersistentType persistentTypeResource;
	

	protected AbstractJavaTypeMapping(JavaPersistentType parent) {
		super(parent);
	}
	
	protected JavaResourceNode mappingResource() {
		return this.persistentTypeResource.mappingAnnotation(annotationName());
	}

	//***************** ITypeMapping implementation *****************
	
	public JavaPersistentType persistentType() {
		return (JavaPersistentType) parent();
	}

	public String tableName() {
		return null;
	}

	public org.eclipse.jpt.db.internal.Table primaryDbTable() {
		return null;
	}

	public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
		return null;
	}

	public Schema dbSchema() {
		return null;
	}

	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return true;
	}

	public Iterator<Table> associatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<String> associatedTableNamesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Iterator<Table> associatedTablesIncludingInherited() {
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
	public void initializeFromResource(JavaResourcePersistentType persistentTypeResource) {
		this.persistentTypeResource = persistentTypeResource;
	}

	public void update(JavaResourcePersistentType persistentTypeResource) {
		this.persistentTypeResource = persistentTypeResource;
	}
	
	//******************** validation *********************

	public TextRange validationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.mappingResource().textRange(astRoot);
		return (textRange != null) ? textRange : this.persistentType().validationTextRange(astRoot);
	}

}
