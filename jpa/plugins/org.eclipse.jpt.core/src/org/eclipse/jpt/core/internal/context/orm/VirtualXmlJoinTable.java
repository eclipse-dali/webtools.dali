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
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.RelationshipMappingTools;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.UniqueConstraint;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class VirtualXmlJoinTable extends AbstractJpaEObject implements XmlJoinTable
{
	
	protected JavaJoinTable javaJoinTable;

	protected boolean metadataComplete;

	protected OrmPersistentAttribute ormPersistentAttribute;
	
	protected EList<XmlJoinColumn> inverseJoinColumns;
	
	protected EList<XmlJoinColumn> joinColumns;
	

	protected VirtualXmlJoinTable(OrmPersistentAttribute ormPersistentAttribute, JavaJoinTable javaJoinTable, boolean metadataComplete) {
		super();
		this.ormPersistentAttribute = ormPersistentAttribute;
		this.javaJoinTable = javaJoinTable;
		this.metadataComplete = metadataComplete;
	}

	protected OrmRelationshipMapping ormRelationshipMapping() {
		return (OrmRelationshipMapping) this.ormPersistentAttribute.getMapping();
	}
	
	public String getName() {
		if (!this.metadataComplete) {
			if (this.javaJoinTable.getSpecifiedName() != null) {
				return this.javaJoinTable.getSpecifiedName();
			}	
		}
		return RelationshipMappingTools.buildJoinTableDefaultName(ormRelationshipMapping());
	}

	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getCatalog() {
		if (this.metadataComplete) {
			return this.javaJoinTable.getDefaultCatalog();
		}
		return this.javaJoinTable.getCatalog();
	}

	public void setCatalog(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getSchema() {
		if (this.metadataComplete) {
			return this.javaJoinTable.getDefaultSchema();
		}
		return this.javaJoinTable.getSchema();
	}

	public void setSchema(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public EList<XmlJoinColumn> getJoinColumns() {
		if (this.joinColumns == null) {
			this.joinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_JOIN_TABLE__JOIN_COLUMNS);
		}
		for (JavaJoinColumn joinColumn : CollectionTools.iterable(this.javaJoinTable.specifiedJoinColumns())) {
			XmlJoinColumn xmlJoinColumn = new VirtualXmlJoinColumn(joinColumn, this.metadataComplete);
			this.joinColumns.add(xmlJoinColumn);
		}
		return this.joinColumns;
	}
	
	public EList<XmlJoinColumn> getInverseJoinColumns() {
		if (this.inverseJoinColumns == null) {
			this.inverseJoinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS);
		}
		for (JavaJoinColumn joinColumn : CollectionTools.iterable(this.javaJoinTable.specifiedInverseJoinColumns())) {
			XmlJoinColumn xmlJoinColumn = new VirtualXmlJoinColumn(joinColumn, this.metadataComplete);
			this.inverseJoinColumns.add(xmlJoinColumn);
		}

		return this.inverseJoinColumns;
	}

	public EList<UniqueConstraint> getUniqueConstraints() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update(JavaJoinTable javaJoinTable) {
		this.javaJoinTable = javaJoinTable;
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

}
