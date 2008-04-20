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
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * A virtual secondary table is used to represent the XmlSecondaryTable resource object.
 * A virtual secondary table is one which is not specified in the orm.xml file, 
 * but is implied from the underlying java.  Virtual secondary table
 * is not used when the secondary table is specified in the orm.xml.
 * 
 * A virtual secondary table delegates to the underlying java secondary table for its state. 
 */
public class VirtualXmlSecondaryTable extends AbstractJpaEObject implements XmlSecondaryTable
{
	
	protected JavaSecondaryTable javaSecondaryTable;
	
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
	
	//VirtualXmlSecondaryTable is rebuilt everytime, so rebuilding the joinColumns list as well
	public EList<XmlPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		EList<XmlPrimaryKeyJoinColumn> primaryKeyJoinColumns = new EObjectContainmentEList<XmlPrimaryKeyJoinColumn>(XmlPrimaryKeyJoinColumn.class, this, OrmPackage.XML_SECONDARY_TABLE_IMPL__PRIMARY_KEY_JOIN_COLUMNS);
		
		for (JavaPrimaryKeyJoinColumn pkJoinColumn : CollectionTools.iterable(this.javaSecondaryTable.specifiedPrimaryKeyJoinColumns())) {
			XmlPrimaryKeyJoinColumn xmlPkJoinColumn = new VirtualXmlPrimaryKeyJoinColumn(pkJoinColumn);
			primaryKeyJoinColumns.add(xmlPkJoinColumn);
		}
		
		return primaryKeyJoinColumns;
	}
	
	public EList<XmlUniqueConstraint> getUniqueConstraints() {
		EList<XmlUniqueConstraint> xmlUniqueConstraints = new EObjectContainmentEList<XmlUniqueConstraint>(XmlUniqueConstraint.class, this, OrmPackage.XML_SECONDARY_TABLE__UNIQUE_CONSTRAINTS);

		for (JavaUniqueConstraint uniqueConstraint : CollectionTools.iterable(this.javaSecondaryTable.uniqueConstraints())) {
			XmlUniqueConstraint xmlUniqueConstraint = new VirtualXmlUniqueConstraint(uniqueConstraint, true);
			xmlUniqueConstraints.add(xmlUniqueConstraint);
		}

		return xmlUniqueConstraints;
	}
	
	public TextRange getNameTextRange() {
		return null;
	}
	
	public TextRange getCatalogTextRange() {
		return null;
	}
	
	public TextRange getSchemaTextRange() {
		return null;
	}
		
	public void update(JavaSecondaryTable javaSecondaryTable) {
		this.javaSecondaryTable = javaSecondaryTable;
	}

}
