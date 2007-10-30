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
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


//TODO need a way to morph between mapping types to save any information that is
//comming to the different TypeMappings.  For java, we do not know the other types
//of mappings since they are defined in an extension point so we can do this the same
//way as xml.
public abstract class JavaTypeMapping extends JavaContextModel
	implements IJavaTypeMapping
{

	protected JavaTypeMapping(IJavaPersistentType parent) {
		super(parent);
	}

	public String getName() {
		return this.getPersistentType().getName();
	}

	public String getTableName() {
		return null;
	}

	public IPersistentType getPersistentType() {
		return (IPersistentType) parent();
	}


//	public ITextRange validationTextRange() {
//		ITextRange textRange = this.type.annotationTextRange(this.declarationAnnotationAdapter());
//		return (textRange != null) ? textRange : this.getPersistentType().validationTextRange();
//	}

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

//	public Iterator<ITable> associatedTables() {
//		return EmptyIterator.instance();
//	}

	public Iterator<String> associatedTableNamesIncludingInherited() {
		return EmptyIterator.instance();
	}

//	public Iterator<ITable> associatedTablesIncludingInherited() {
//		return EmptyIterator.instance();
//	}

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
	
	public void update(JavaPersistentTypeResource persistentTypeResource) {
		//do nothing
	}
}
