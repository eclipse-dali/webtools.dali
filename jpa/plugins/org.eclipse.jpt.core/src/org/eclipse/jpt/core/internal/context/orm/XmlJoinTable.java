/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.core.internal.context.base.INonOwningMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.orm.JoinColumn;
import org.eclipse.jpt.core.internal.resource.orm.JoinTable;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;


public class XmlJoinTable extends AbstractXmlTable implements IJoinTable
{

	protected final List<XmlJoinColumn> specifiedJoinColumns;

	protected final List<XmlJoinColumn> defaultJoinColumns;

	protected final List<XmlJoinColumn> specifiedInverseJoinColumns;

	protected final List<XmlJoinColumn> defaultInverseJoinColumns;
	
	protected RelationshipMapping relationshipMappingResource;
	
	protected XmlJoinTable(XmlRelationshipMapping<? extends RelationshipMapping> parent) {
		super(parent);
		this.specifiedJoinColumns = new ArrayList<XmlJoinColumn>();
		this.defaultJoinColumns = new ArrayList<XmlJoinColumn>();
		this.specifiedInverseJoinColumns = new ArrayList<XmlJoinColumn>();
		this.defaultInverseJoinColumns = new ArrayList<XmlJoinColumn>();
	}
	
	//******************* AbstractXmlTable implementation *****************
	
	@Override
	protected String defaultCatalog() {
		return entityMappings().getCatalog();
	}
	
	@Override
	protected String defaultName() {
		String tableName = relationshipMapping().typeMapping().tableName();
		if (tableName == null) {
			return null;
		}
		IEntity targetEntity = targetEntity();
		if (targetEntity == null) {
			return null;
		}
		ITable targetTable = targetEntity.getTable();
		return (targetTable == null) ? null : tableName + "_" + targetTable.getName();
	}

	@Override
	protected String defaultSchema() {
		return entityMappings().getSchema();
	}


	protected IEntity targetEntity() {
//		String targetEntity = relationshipMapping().fullyQualifiedTargetEntity(defaultsContext.astRoot());
//		if (targetEntity == null) {
//			return null;
//		}
//		IPersistentType persistentType = defaultsContext.persistentType(targetEntity);
//		if (persistentType == null) {
//			return null;
//		}
//		ITypeMapping typeMapping = persistentType.getMapping();
//		if (typeMapping instanceof IEntity) {
//			return (IEntity) typeMapping;
//		}
		return null;
	}


	@Override
	protected void removeTableResource() {
		this.relationshipMappingResource.setJoinTable(null);
	}
	
	@Override
	protected void addTableResource() {
		this.relationshipMappingResource.setJoinTable(OrmFactory.eINSTANCE.createJoinTableImpl());
		
	}

	@Override
	protected JoinTable table() {
		return this.relationshipMappingResource.getJoinTable();
	}

	//******************* IJoinTable implementation *****************


	@SuppressWarnings("unchecked")
	public ListIterator<XmlJoinColumn> joinColumns() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumns() : this.specifiedJoinColumns();
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlJoinColumn> defaultJoinColumns() {
		return new CloneListIterator<XmlJoinColumn>(this.defaultJoinColumns);
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<XmlJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.specifiedJoinColumns.isEmpty();
	}

	public XmlJoinColumn addSpecifiedJoinColumn(int index) {
		if (table() == null) {
			addTableResource();
		}
		XmlJoinColumn joinColumn = new XmlJoinColumn(this, new JoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		this.table().getJoinColumns().add(index, OrmFactory.eINSTANCE.createJoinColumnImpl());
		this.fireItemAdded(IJoinTable.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, XmlJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, IJoinTable.SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void removeSpecifiedJoinColumn(int index) {
		XmlJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		this.table().getJoinColumns().remove(index);
		fireItemRemoved(IJoinTable.SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedJoinColumn(XmlJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, IJoinTable.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.table().getJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(IJoinTable.SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}


	@SuppressWarnings("unchecked")
	public ListIterator<XmlJoinColumn> inverseJoinColumns() {
		return this.specifiedInverseJoinColumns.isEmpty() ? this.defaultInverseJoinColumns() : this.specifiedInverseJoinColumns();
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlJoinColumn> defaultInverseJoinColumns() {
		return new CloneListIterator<XmlJoinColumn>(this.defaultInverseJoinColumns);
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlJoinColumn> specifiedInverseJoinColumns() {
		return new CloneListIterator<XmlJoinColumn>(this.specifiedInverseJoinColumns);
	}

	public int specifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumns.size();
	}

	public boolean containsSpecifiedInverseJoinColumns() {
		return !this.specifiedInverseJoinColumns.isEmpty();
	}

	public XmlJoinColumn addSpecifiedInverseJoinColumn(int index) {
		if (table() == null) {
			addTableResource();
		}
		XmlJoinColumn joinColumn = new XmlJoinColumn(this, new InverseJoinColumnOwner());
		this.specifiedInverseJoinColumns.add(index, joinColumn);
		this.table().getInverseJoinColumns().add(index, OrmFactory.eINSTANCE.createJoinColumnImpl());
		this.fireItemAdded(IJoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}
	
	protected void addSpecifiedInverseJoinColumn(int index, XmlJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedInverseJoinColumns, IJoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}


	public void removeSpecifiedInverseJoinColumn(int index) {
		XmlJoinColumn removedJoinColumn = this.specifiedInverseJoinColumns.remove(index);
		this.table().getInverseJoinColumns().remove(index);
		fireItemRemoved(IJoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedInverseJoinColumn(XmlJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedInverseJoinColumns, IJoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedInverseJoinColumns, targetIndex, sourceIndex);
		this.table().getInverseJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(IJoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}

	@SuppressWarnings("unchecked")
	public XmlRelationshipMapping<? extends RelationshipMapping> relationshipMapping() {
		return (XmlRelationshipMapping) parent();
	}

//	@Override
//	protected void addInsignificantXmlFeatureIdsTo(Set<Integer> insignificantXmlFeatureIds) {
//		super.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_JOIN_COLUMNS);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IJOIN_TABLE__DEFAULT_INVERSE_JOIN_COLUMNS);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IJOIN_TABLE__JOIN_COLUMNS);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IJOIN_TABLE__INVERSE_JOIN_COLUMNS);
//	}
//
//	public boolean containsSpecifiedJoinColumns() {
//		return !this.getSpecifiedJoinColumns().isEmpty();
//	}
//
//	public boolean containsSpecifiedInverseJoinColumns() {
//		return !this.getSpecifiedInverseJoinColumns().isEmpty();
//	}

	public boolean isSpecified() {
		return this.table() != null;
	}	
	
	
	public void initialize(RelationshipMapping relationshipMapping) {
		this.relationshipMappingResource = relationshipMapping;
		this.initialize(this.table());
	}
	
	public void update(RelationshipMapping relationshipMapping) {
		this.relationshipMappingResource = relationshipMapping;
		this.update(this.table());
	}

	protected void initialize(JoinTable joinTable) {
		super.initialize(joinTable);
		this.initializeSpecifiedJoinColumns(joinTable);
		this.initializeSpecifiedInverseJoinColumns(joinTable);
	}
	
	protected void initializeSpecifiedJoinColumns(JoinTable joinTable) {
		if (joinTable == null) {
			return;
		}
		for (JoinColumn joinColumn : joinTable.getJoinColumns()) {
			this.specifiedJoinColumns.add(createJoinColumn(joinColumn));
		}
	}
	
	protected void initializeSpecifiedInverseJoinColumns(JoinTable joinTable) {
		if (joinTable == null) {
			return;
		}
		for (JoinColumn joinColumn : joinTable.getInverseJoinColumns()) {
			this.specifiedInverseJoinColumns.add(createInverseJoinColumn(joinColumn));
		}
	}
	
	protected void update(JoinTable joinTable) {
		super.update(joinTable);
		this.updateSpecifiedJoinColumns(joinTable);
		this.updateSpecifiedInverseJoinColumns(joinTable);
	}
		
	protected void updateSpecifiedJoinColumns(JoinTable joinTable) {
		ListIterator<XmlJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<JoinColumn> resourceJoinColumns = EmptyListIterator.instance();
		if (joinTable != null) {
			resourceJoinColumns = joinTable.getJoinColumns().listIterator();
		}
		
		while (joinColumns.hasNext()) {
			XmlJoinColumn joinColumn = joinColumns.next();
			if (resourceJoinColumns.hasNext()) {
				joinColumn.update(resourceJoinColumns.next());
			}
			else {
				removeSpecifiedJoinColumn(joinColumn);
			}
		}
		
		while (resourceJoinColumns.hasNext()) {
			addSpecifiedJoinColumn(specifiedJoinColumnsSize(), createJoinColumn(resourceJoinColumns.next()));
		}
	}
	
	protected void updateSpecifiedInverseJoinColumns(JoinTable joinTable) {
		ListIterator<XmlJoinColumn> inverseJoinColumns = specifiedInverseJoinColumns();
		ListIterator<JoinColumn> resourceInverseJoinColumns = EmptyListIterator.instance();
		if (joinTable != null) {
			resourceInverseJoinColumns = joinTable.getInverseJoinColumns().listIterator();
		}
		
		while (inverseJoinColumns.hasNext()) {
			XmlJoinColumn joinColumn = inverseJoinColumns.next();
			if (resourceInverseJoinColumns.hasNext()) {
				joinColumn.update(resourceInverseJoinColumns.next());
			}
			else {
				removeSpecifiedInverseJoinColumn(joinColumn);
			}
		}
		
		while (resourceInverseJoinColumns.hasNext()) {
			addSpecifiedInverseJoinColumn(specifiedJoinColumnsSize(), createInverseJoinColumn(resourceInverseJoinColumns.next()));
		}
	}
	
	protected XmlJoinColumn createJoinColumn(JoinColumn joinColumn) {
		XmlJoinColumn xmlJoinColumn = new XmlJoinColumn(this, new JoinColumnOwner());
		xmlJoinColumn.initialize(joinColumn);
		return xmlJoinColumn;
	}
	
	protected XmlJoinColumn createInverseJoinColumn(JoinColumn joinColumn) {
		XmlJoinColumn xmlJoinColumn = new XmlJoinColumn(this, new InverseJoinColumnOwner());
		xmlJoinColumn.initialize(joinColumn);
		return xmlJoinColumn;
	}
	
	/**
	 * just a little common behavior
	 */
	abstract class AbstractJoinColumnOwner implements IJoinColumn.Owner
	{
		AbstractJoinColumnOwner() {
			super();
		}

		public ITypeMapping typeMapping() {
			return relationshipMapping().typeMapping();
		}
		public IRelationshipMapping relationshipMapping() {
			return XmlJoinTable.this.relationshipMapping();
		}

		/**
		 * the default table name is always valid and a specified table name
		 * is prohibited (which will be handled elsewhere)
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return false;
		}

		/**
		 * the join column can only be on the join table itself
		 */
		public boolean tableIsAllowed() {
			return false;
		}
//
//		public ITextRange validationTextRange() {
//			return this.joinTable.validationTextRange();
//		}

		public Table dbTable(String tableName) {
			if (XmlJoinTable.this.getName() == null) {
				return null;
			}
			return (XmlJoinTable.this.getName().equals(tableName)) ? XmlJoinTable.this.dbTable() : null;
		}
	}


	/**
	 * owner for "forward-pointer" JoinColumns;
	 * these point at the target/inverse entity
	 */
	class InverseJoinColumnOwner extends AbstractJoinColumnOwner
	{
		public InverseJoinColumnOwner() {
			super();
		}

		public IEntity targetEntity() {
			return XmlJoinTable.this.relationshipMapping().getResolvedTargetEntity();
		}

		public String attributeName() {
			return XmlJoinTable.this.relationshipMapping().getName();
		}

		@Override
		public Table dbTable(String tableName) {
			Table dbTable = super.dbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			IEntity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			IEntity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.primaryDbTable();
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return XmlJoinTable.this.defaultInverseJoinColumns.contains(joinColumn);
		}
		
		public String defaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public String defaultTableName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public ITextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
	}


	/**
	 * owner for "back-pointer" JoinColumns;
	 * these point at the source/owning entity
	 */
	class JoinColumnOwner extends AbstractJoinColumnOwner
	{
		public JoinColumnOwner() {
			super();
		}

		public IEntity targetEntity() {
			return XmlJoinTable.this.relationshipMapping().getEntity();
		}

		public String attributeName() {
			IEntity targetEntity = XmlJoinTable.this.relationshipMapping().getResolvedTargetEntity();
			if (targetEntity == null) {
				return null;
			}
			String attributeName = XmlJoinTable.this.relationshipMapping().getName();
			for (Iterator<IPersistentAttribute> stream = targetEntity.persistentType().allAttributes(); stream.hasNext();) {
				IPersistentAttribute attribute = stream.next();
				IAttributeMapping mapping = attribute.getMapping();
				if (mapping instanceof INonOwningMapping) {
					String mappedBy = ((INonOwningMapping) mapping).getMappedBy();
					if ((mappedBy != null) && mappedBy.equals(attributeName)) {
						return attribute.getName();
					}
				}
			}
			return null;
		}

		@Override
		public Table dbTable(String tableName) {
			Table dbTable = super.dbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			return typeMapping().dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			return typeMapping().primaryDbTable();
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return XmlJoinTable.this.defaultJoinColumns.contains(joinColumn);
		}
		
		public String defaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public String defaultTableName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public ITextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
}
