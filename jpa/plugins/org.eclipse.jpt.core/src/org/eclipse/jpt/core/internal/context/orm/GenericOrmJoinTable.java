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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;


public class GenericOrmJoinTable extends AbstractOrmTable implements OrmJoinTable
{

	protected final List<OrmJoinColumn> specifiedJoinColumns;

	protected final List<OrmJoinColumn> defaultJoinColumns;

	protected final List<OrmJoinColumn> specifiedInverseJoinColumns;

	protected final List<OrmJoinColumn> defaultInverseJoinColumns;
	
	protected XmlRelationshipMapping relationshipMappingResource;
	
	public GenericOrmJoinTable(AbstractOrmRelationshipMapping<? extends XmlRelationshipMapping> parent) {
		super(parent);
		this.specifiedJoinColumns = new ArrayList<OrmJoinColumn>();
		this.defaultJoinColumns = new ArrayList<OrmJoinColumn>();
		this.specifiedInverseJoinColumns = new ArrayList<OrmJoinColumn>();
		this.defaultInverseJoinColumns = new ArrayList<OrmJoinColumn>();
	}
	
	@Override
	public AbstractOrmRelationshipMapping<? extends XmlRelationshipMapping> parent() {
		return (AbstractOrmRelationshipMapping<? extends XmlRelationshipMapping>) super.parent();
	}
	
	public void initializeFrom(JoinTable oldJoinTable) {
		super.initializeFrom(oldJoinTable);
		int index = 0;
		for (JoinColumn joinColumn : CollectionTools.iterable(oldJoinTable.specifiedJoinColumns())) {
			OrmJoinColumn newJoinColumn = addSpecifiedJoinColumn(index++);
			newJoinColumn.initializeFrom(joinColumn);
		}
		index = 0;
		for (JoinColumn joinColumn : CollectionTools.iterable(oldJoinTable.specifiedInverseJoinColumns())) {
			OrmJoinColumn newJoinColumn = addSpecifiedInverseJoinColumn(index++);
			newJoinColumn.initializeFrom(joinColumn);
		}
	}
	
	//******************* AbstractXmlTable implementation *****************
	
	@Override
	protected String defaultCatalog() {
		if (!relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		return entityMappings().getCatalog();
	}
	
	@Override
	protected String defaultName() {
		if (!relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		String tableName = relationshipMapping().typeMapping().tableName();
		if (tableName == null) {
			return null;
		}
		Entity targetEntity = targetEntity();
		if (targetEntity == null) {
			return null;
		}
		Table targetTable = targetEntity.getTable();
		return (targetTable == null) ? null : tableName + "_" + targetTable.getName();
	}

	@Override
	protected String defaultSchema() {
		if (!relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		return entityMappings().getSchema();
	}


	protected Entity targetEntity() {
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
	protected XmlJoinTable table() {
		return this.relationshipMappingResource.getJoinTable();
	}

	//******************* IJoinTable implementation *****************


	public ListIterator<OrmJoinColumn> joinColumns() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumns() : this.specifiedJoinColumns();
	}

	public int joinColumnsSize() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumnsSize() : this.specifiedJoinColumnsSize();
	}
	
	public JoinColumn getDefaultJoinColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ListIterator<OrmJoinColumn> defaultJoinColumns() {
		return new CloneListIterator<OrmJoinColumn>(this.defaultJoinColumns);
	}

	public int defaultJoinColumnsSize() {
		return this.defaultJoinColumns.size();
	}
	
	public ListIterator<OrmJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<OrmJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.specifiedJoinColumns.isEmpty();
	}

	public OrmJoinColumn addSpecifiedJoinColumn(int index) {
		if (table() == null) {
			addTableResource();
		}
		OrmJoinColumn joinColumn = jpaFactory().buildOrmJoinColumn(this, new JoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		this.table().getJoinColumns().add(index, OrmFactory.eINSTANCE.createJoinColumnImpl());
		this.fireItemAdded(JoinTable.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, OrmJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, JoinTable.SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void removeSpecifiedJoinColumn(int index) {
		OrmJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		this.table().getJoinColumns().remove(index);
		fireItemRemoved(JoinTable.SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		// TODO Auto-generated method stub
		
	}
	
	public void removeSpecifiedInverseJoinColumn(JoinColumn joinColumn) {
		// TODO Auto-generated method stub
		
	}
	protected void removeSpecifiedJoinColumn(OrmJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, JoinTable.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.table().getJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(JoinTable.SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}


	public ListIterator<OrmJoinColumn> inverseJoinColumns() {
		return this.specifiedInverseJoinColumns.isEmpty() ? this.defaultInverseJoinColumns() : this.specifiedInverseJoinColumns();
	}

	public int inverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumns.isEmpty() ? this.defaultInverseJoinColumnsSize() : this.specifiedInverseJoinColumnsSize();
	}
	
	public JoinColumn getDefaultInverseJoinColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ListIterator<OrmJoinColumn> defaultInverseJoinColumns() {
		return new CloneListIterator<OrmJoinColumn>(this.defaultInverseJoinColumns);
	}

	public int defaultInverseJoinColumnsSize() {
		return this.defaultInverseJoinColumns.size();
	}

	public ListIterator<OrmJoinColumn> specifiedInverseJoinColumns() {
		return new CloneListIterator<OrmJoinColumn>(this.specifiedInverseJoinColumns);
	}

	public int specifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumns.size();
	}

	public boolean containsSpecifiedInverseJoinColumns() {
		return !this.specifiedInverseJoinColumns.isEmpty();
	}

	public OrmJoinColumn addSpecifiedInverseJoinColumn(int index) {
		if (table() == null) {
			addTableResource();
		}
		OrmJoinColumn joinColumn = jpaFactory().buildOrmJoinColumn(this, new InverseJoinColumnOwner());
		this.specifiedInverseJoinColumns.add(index, joinColumn);
		this.table().getInverseJoinColumns().add(index, OrmFactory.eINSTANCE.createJoinColumnImpl());
		this.fireItemAdded(JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}
	
	protected void addSpecifiedInverseJoinColumn(int index, OrmJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedInverseJoinColumns, JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}


	public void removeSpecifiedInverseJoinColumn(int index) {
		OrmJoinColumn removedJoinColumn = this.specifiedInverseJoinColumns.remove(index);
		this.table().getInverseJoinColumns().remove(index);
		fireItemRemoved(JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedInverseJoinColumn(OrmJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedInverseJoinColumns, JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedInverseJoinColumns, targetIndex, sourceIndex);
		this.table().getInverseJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}

	public AbstractOrmRelationshipMapping<? extends XmlRelationshipMapping> relationshipMapping() {
		return parent();
	}

	public boolean isSpecified() {
		return this.table() != null;
	}	
	
	public void initialize(XmlRelationshipMapping relationshipMapping) {
		this.relationshipMappingResource = relationshipMapping;
		this.initialize(this.table());
	}
	
	public void update(XmlRelationshipMapping relationshipMapping) {
		this.relationshipMappingResource = relationshipMapping;
		this.update(this.table());
	}

	protected void initialize(XmlJoinTable joinTable) {
		super.initialize(joinTable);
		this.initializeSpecifiedJoinColumns(joinTable);
		this.initializeSpecifiedInverseJoinColumns(joinTable);
	}
	
	protected void initializeSpecifiedJoinColumns(XmlJoinTable joinTable) {
		if (joinTable == null) {
			return;
		}
		for (XmlJoinColumn joinColumn : joinTable.getJoinColumns()) {
			this.specifiedJoinColumns.add(createJoinColumn(joinColumn));
		}
	}
	
	protected void initializeSpecifiedInverseJoinColumns(XmlJoinTable joinTable) {
		if (joinTable == null) {
			return;
		}
		for (XmlJoinColumn joinColumn : joinTable.getInverseJoinColumns()) {
			this.specifiedInverseJoinColumns.add(createInverseJoinColumn(joinColumn));
		}
	}
	
	protected void update(XmlJoinTable joinTable) {
		super.update(joinTable);
		this.updateSpecifiedJoinColumns(joinTable);
		this.updateSpecifiedInverseJoinColumns(joinTable);
	}
		
	protected void updateSpecifiedJoinColumns(XmlJoinTable joinTable) {
		ListIterator<OrmJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<XmlJoinColumn> resourceJoinColumns = EmptyListIterator.instance();
		if (joinTable != null) {
			resourceJoinColumns = joinTable.getJoinColumns().listIterator();
		}
		
		while (joinColumns.hasNext()) {
			OrmJoinColumn joinColumn = joinColumns.next();
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
	
	protected void updateSpecifiedInverseJoinColumns(XmlJoinTable joinTable) {
		ListIterator<OrmJoinColumn> inverseJoinColumns = specifiedInverseJoinColumns();
		ListIterator<XmlJoinColumn> resourceInverseJoinColumns = EmptyListIterator.instance();
		if (joinTable != null) {
			resourceInverseJoinColumns = joinTable.getInverseJoinColumns().listIterator();
		}
		
		while (inverseJoinColumns.hasNext()) {
			OrmJoinColumn joinColumn = inverseJoinColumns.next();
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
	
	protected OrmJoinColumn createJoinColumn(XmlJoinColumn joinColumn) {
		OrmJoinColumn ormJoinColumn = jpaFactory().buildOrmJoinColumn(this, new JoinColumnOwner());
		ormJoinColumn.initialize(joinColumn);
		return ormJoinColumn;
	}
	
	protected OrmJoinColumn createInverseJoinColumn(XmlJoinColumn joinColumn) {
		OrmJoinColumn ormJoinColumn = jpaFactory().buildOrmJoinColumn(this, new InverseJoinColumnOwner());
		ormJoinColumn.initialize(joinColumn);
		return ormJoinColumn;
	}
	
	/**
	 * just a little common behavior
	 */
	abstract class AbstractJoinColumnOwner implements JoinColumn.Owner
	{
		AbstractJoinColumnOwner() {
			super();
		}

		public TypeMapping typeMapping() {
			return relationshipMapping().typeMapping();
		}
		public RelationshipMapping relationshipMapping() {
			return GenericOrmJoinTable.this.relationshipMapping();
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

		public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
			if (GenericOrmJoinTable.this.getName() == null) {
				return null;
			}
			return (GenericOrmJoinTable.this.getName().equals(tableName)) ? GenericOrmJoinTable.this.dbTable() : null;
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

		public Entity targetEntity() {
			return GenericOrmJoinTable.this.relationshipMapping().getResolvedTargetEntity();
		}

		public String attributeName() {
			return GenericOrmJoinTable.this.relationshipMapping().getName();
		}

		@Override
		public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
			org.eclipse.jpt.db.internal.Table dbTable = super.dbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			Entity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.dbTable(tableName);
		}

		public org.eclipse.jpt.db.internal.Table dbReferencedColumnTable() {
			Entity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.primaryDbTable();
		}
		
		public boolean isVirtual(AbstractJoinColumn joinColumn) {
			return GenericOrmJoinTable.this.defaultInverseJoinColumns.contains(joinColumn);
		}
		
		public String defaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public String defaultTableName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public TextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public int joinColumnsSize() {
			return GenericOrmJoinTable.this.inverseJoinColumnsSize();
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

		public Entity targetEntity() {
			return GenericOrmJoinTable.this.relationshipMapping().getEntity();
		}

		public String attributeName() {
			Entity targetEntity = GenericOrmJoinTable.this.relationshipMapping().getResolvedTargetEntity();
			if (targetEntity == null) {
				return null;
			}
			String attributeName = GenericOrmJoinTable.this.relationshipMapping().getName();
			for (Iterator<PersistentAttribute> stream = targetEntity.persistentType().allAttributes(); stream.hasNext();) {
				PersistentAttribute attribute = stream.next();
				AttributeMapping mapping = attribute.getMapping();
				if (mapping instanceof NonOwningMapping) {
					String mappedBy = ((NonOwningMapping) mapping).getMappedBy();
					if ((mappedBy != null) && mappedBy.equals(attributeName)) {
						return attribute.getName();
					}
				}
			}
			return null;
		}

		@Override
		public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
			org.eclipse.jpt.db.internal.Table dbTable = super.dbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			return typeMapping().dbTable(tableName);
		}

		public org.eclipse.jpt.db.internal.Table dbReferencedColumnTable() {
			return typeMapping().primaryDbTable();
		}
		
		public boolean isVirtual(AbstractJoinColumn joinColumn) {
			return GenericOrmJoinTable.this.defaultJoinColumns.contains(joinColumn);
		}
		
		public String defaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public String defaultTableName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public TextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public int joinColumnsSize() {
			return GenericOrmJoinTable.this.joinColumnsSize();
		}
	}
	
}
