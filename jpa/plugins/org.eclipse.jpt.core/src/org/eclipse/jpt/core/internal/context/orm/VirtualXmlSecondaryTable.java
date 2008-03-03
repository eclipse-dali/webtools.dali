/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.UniqueConstraint;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;

public class VirtualXmlSecondaryTable extends AbstractJpaEObject implements XmlSecondaryTable
{
	
	protected JavaSecondaryTable javaSecondaryTable;

	protected EList<XmlPrimaryKeyJoinColumn> primaryKeyJoinColumns;
	
	protected EList<UniqueConstraint> uniqueConstraints;
	
	protected VirtualXmlSecondaryTable(JavaSecondaryTable javaSecondaryTable) {
		super();
		this.javaSecondaryTable = javaSecondaryTable;
	}

	public String getName() {
		return this.javaSecondaryTable.getSpecifiedName();
	}

	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getCatalog() {
		return this.javaSecondaryTable.getSpecifiedCatalog();
	}
	
	public void setCatalog(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public String getSchema() {
		return this.javaSecondaryTable.getSpecifiedSchema();
	}
	
	public void setSchema(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public EList<XmlPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		if (primaryKeyJoinColumns == null)
		{
			primaryKeyJoinColumns = new EObjectContainmentEList<XmlPrimaryKeyJoinColumn>(XmlPrimaryKeyJoinColumn.class, this, OrmPackage.XML_SECONDARY_TABLE_IMPL__PRIMARY_KEY_JOIN_COLUMNS);
		}
		return primaryKeyJoinColumns;
	}
	
	public EList<UniqueConstraint> getUniqueConstraints() {
		if (uniqueConstraints == null)
		{
			uniqueConstraints = new EObjectContainmentEList<UniqueConstraint>(UniqueConstraint.class, this, OrmPackage.XML_SECONDARY_TABLE_IMPL__UNIQUE_CONSTRAINTS);
		}
		return uniqueConstraints;
	}
	
	public TextRange nameTextRange() {
		return null;
	}
	
	public TextRange catalogTextRange() {
		return null;
	}
	
	public TextRange schemaTextRange() {
		return null;
	}
		
	public void update(JavaSecondaryTable javaSecondaryTable) {
		this.javaSecondaryTable = javaSecondaryTable;
	}

}
