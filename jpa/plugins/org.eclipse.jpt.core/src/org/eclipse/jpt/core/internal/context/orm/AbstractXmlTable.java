/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.AbstractTable;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.NameTools;

public abstract class AbstractXmlTable extends JpaContextNode implements ITable
{
	protected String specifiedName;

	protected String defaultName;

	protected String specifiedCatalog;

	protected String defaultCatalog;

	protected String specifiedSchema;

	protected String defaultSchema;
	
//	protected EList<IUniqueConstraint> uniqueConstraints;

	protected AbstractXmlTable(IJpaContextNode parent) {
		super(parent);
	}

	public void initializeFrom(ITable oldTable) {
		setSpecifiedName(oldTable.getSpecifiedName());
		setSpecifiedCatalog(oldTable.getSpecifiedCatalog());
		setSpecifiedSchema(oldTable.getSpecifiedSchema());
	}
	
	
	public String getName() {
		return (this.getSpecifiedName() == null) ? this.getDefaultName() : this.getSpecifiedName();
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}
	
	protected abstract AbstractTable table();

	protected abstract void removeTableResource();
	
	protected abstract void addTableResource();
	
	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		if (oldSpecifiedName != newSpecifiedName) {
			if (this.table() != null) {
				this.table().setName(newSpecifiedName);						
				if (this.table().isAllFeaturesUnset()) {
					removeTableResource();
				}
			}
			else if (newSpecifiedName != null) {
				addTableResource();
				table().setName(newSpecifiedName);
			}
		}
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}
	
	protected void setSpecifiedName_(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}

	public String getDefaultName() {
		return this.defaultName;
	}

	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		firePropertyChanged(DEFAULT_NAME_PROPERTY, oldDefaultName, newDefaultName);
	}

	public String getCatalog() {
		return (this.getSpecifiedCatalog() == null) ? getDefaultCatalog() : this.getSpecifiedCatalog();
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newSpecifiedCatalog;
		if (oldSpecifiedCatalog != newSpecifiedCatalog) {
			if (this.table() != null) {
				this.table().setCatalog(newSpecifiedCatalog);						
				if (this.table().isAllFeaturesUnset()) {
					removeTableResource();
				}
			}
			else if (newSpecifiedCatalog != null) {
				addTableResource();
				table().setCatalog(newSpecifiedCatalog);
			}
		}
		firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, oldSpecifiedCatalog, newSpecifiedCatalog);
	}
	
	protected void setSpecifiedCatalog_(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newSpecifiedCatalog;
		firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, oldSpecifiedCatalog, newSpecifiedCatalog);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String newDefaultCatalog) {
		String oldDefaultCatalog = this.defaultCatalog;
		this.defaultCatalog = newDefaultCatalog;
		firePropertyChanged(DEFAULT_CATALOG_PROPERTY, oldDefaultCatalog, newDefaultCatalog);
	}

	public String getSchema() {
		return (this.getSpecifiedSchema() == null) ? getDefaultSchema() : this.getSpecifiedSchema();
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String newSpecifiedSchema) {
		String oldSpecifiedSchema = this.specifiedSchema;
		this.specifiedSchema = newSpecifiedSchema;
		if (oldSpecifiedSchema != newSpecifiedSchema) {
			if (this.table() != null) {
				this.table().setSchema(newSpecifiedSchema);						
				if (this.table().isAllFeaturesUnset()) {
					removeTableResource();
				}
			}
			else if (newSpecifiedSchema != null) {
				addTableResource();
				table().setSchema(newSpecifiedSchema);
			}
		}

		firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
	}
	
	protected void setSpecifiedSchema_(String newSpecifiedSchema) {
		String oldSpecifiedSchema = this.specifiedSchema;
		this.specifiedSchema = newSpecifiedSchema;
		firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}
	
	protected void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = this.defaultSchema;
		this.defaultSchema = newDefaultSchema;
		firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, oldDefaultSchema, newDefaultSchema);
	}

//	public EList<IUniqueConstraint> getUniqueConstraints() {
//		if (uniqueConstraints == null) {
//			uniqueConstraints = new EObjectContainmentEList<IUniqueConstraint>(IUniqueConstraint.class, this, OrmPackage.ABSTRACT_XML_TABLE__UNIQUE_CONSTRAINTS);
//		}
//		return uniqueConstraints;
//	}


	public ITextRange nameTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ITextRange catalogTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ITextRange schemaTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	public ITextRange nameTextRange() {
//		if (node == null) {
//			return owner.validationTextRange();
//		}
//		IDOMNode nameNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.NAME);
//		return (nameNode == null) ? validationTextRange() : buildTextRange(nameNode);
//	}
//
//	public ITextRange schemaTextRange() {
//		if (node == null) {
//			return owner.validationTextRange();
//		}
//		IDOMNode schemaNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.SCHEMA);
//		return (schemaNode == null) ? validationTextRange() : buildTextRange(schemaNode);
//	}
//
//	@Override
//	public ITextRange validationTextRange() {
//		return (node == null) ? owner.validationTextRange() : super.validationTextRange();
//	}
//
//	public Owner getOwner() {
//		return owner;
//	}

	public Table dbTable() {
		Schema schema = this.dbSchema();
		return (schema == null) ? null : schema.tableNamed(getName());
	}

	public Schema dbSchema() {
		return connectionProfile().getDatabase().schemaNamed(getSchema());
	}

	public boolean hasResolvedSchema() {
		return dbSchema() != null;
	}

	public boolean isResolved() {
		return dbTable() != null;
	}
//
//	public IUniqueConstraint createUniqueConstraint(int index) {
//		return createXmlJavaUniqueConstraint(index);
//	}
//
//	protected XmlUniqueConstraint createXmlJavaUniqueConstraint(int index) {
//		return OrmFactory.eINSTANCE.createXmlUniqueConstraint();
//	}
	
	protected void initialize(AbstractTable table) {
		this.specifiedName = this.specifiedName(table);
		this.specifiedSchema = this.specifiedSchema(table);
		this.specifiedCatalog = this.specifiedCatalog(table);
		this.defaultName = this.defaultName();
		this.defaultSchema = this.defaultSchema();
		this.defaultCatalog = this.defaultCatalog();
	}
	
	protected void update(AbstractTable table) {
		this.setSpecifiedName_(this.specifiedName(table));
		this.setSpecifiedSchema_(this.specifiedSchema(table));
		this.setSpecifiedCatalog_(this.specifiedCatalog(table));
		this.setDefaultName(this.defaultName());
		this.setDefaultSchema(this.defaultSchema());
		this.setDefaultCatalog(this.defaultCatalog());
	}

	protected String specifiedName(AbstractTable table) {
		return table == null ? null : table.getName();
	}
	
	protected String specifiedSchema(AbstractTable table) {
		return table == null ? null : table.getSchema();
	}
	
	protected String specifiedCatalog(AbstractTable table) {
		return table == null ? null : table.getCatalog();
	}
	
	protected abstract String defaultName();
	
	protected abstract String defaultSchema();
	
	protected abstract String defaultCatalog();
	
	public String qualifiedName() {
		return NameTools.buildQualifiedDatabaseObjectName(this.getCatalog(), this.getSchema(), this.getName());
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(qualifiedName());
	}

	@Override
	public String displayString() {
		return qualifiedName();
	}
}