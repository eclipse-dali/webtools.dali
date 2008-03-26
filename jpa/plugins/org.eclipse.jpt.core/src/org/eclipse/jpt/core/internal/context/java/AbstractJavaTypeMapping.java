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

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public abstract class AbstractJavaTypeMapping extends AbstractJavaJpaContextNode
	implements JavaTypeMapping
{
	protected JavaResourcePersistentType javaResourcePersistentType;
	

	protected AbstractJavaTypeMapping(JavaPersistentType parent) {
		super(parent);
	}
	
	protected JavaResourceNode mappingResource() {
		return this.javaResourcePersistentType.mappingAnnotation(annotationName());
	}

	//***************** ITypeMapping implementation *****************
	
	public JavaPersistentType getPersistentType() {
		return (JavaPersistentType) getParent();
	}

	public String getTableName() {
		return null;
	}

	public org.eclipse.jpt.db.Table getPrimaryDbTable() {
		return null;
	}

	public org.eclipse.jpt.db.Table getDbTable(String tableName) {
		return null;
	}

	public Schema getDbSchema() {
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

	public Iterator<JavaPersistentAttribute> overridableAttributes() {
		return EmptyIterator.instance();
	}
	
	public Iterator<PersistentAttribute> allOverridableAttributes() {
		return EmptyIterator.instance();
	}
	
	public Iterator<String> overridableAttributeNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allOverridableAttributeNames() {
		return EmptyIterator.instance();
	}

	public Iterator<JavaPersistentAttribute> overridableAssociations() {
		return EmptyIterator.instance();
	}
	
	public Iterator<String> overridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public Iterator<PersistentAttribute> allOverridableAssociations() {
		return EmptyIterator.instance();
	}
	
	public Iterator<String> allOverridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}
	
	//******************** updatating *********************
	public void initializeFromResource(JavaResourcePersistentType javaResourcePersistentType) {
		this.javaResourcePersistentType = javaResourcePersistentType;
	}

	public void update(JavaResourcePersistentType javaResourcePersistentType) {
		this.javaResourcePersistentType = javaResourcePersistentType;
	}
	
	//******************** validation *********************

	public TextRange validationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.mappingResource().textRange(astRoot);
		return (textRange != null) ? textRange : this.getPersistentType().validationTextRange(astRoot);
	}
}
