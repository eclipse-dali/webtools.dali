/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.SingleRelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmSingleRelationshipMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSingleRelationshipMapping;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public abstract class AbstractOrmSingleRelationshipMapping<T extends XmlSingleRelationshipMapping>
	extends AbstractOrmRelationshipMapping<T> implements OrmSingleRelationshipMapping
{
	
	protected final List<OrmJoinColumn> specifiedJoinColumns;

	protected OrmJoinColumn defaultJoinColumn;

	protected Boolean specifiedOptional;

	protected AbstractOrmSingleRelationshipMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.specifiedJoinColumns = new ArrayList<OrmJoinColumn>();
	}
	
	@Override
	public void initializeFromXmlSingleRelationshipMapping(OrmSingleRelationshipMapping oldMapping) {
		super.initializeFromXmlSingleRelationshipMapping(oldMapping);
		int index = 0;
		for (JoinColumn joinColumn : CollectionTools.iterable(oldMapping.specifiedJoinColumns())) {
			OrmJoinColumn newJoinColumn = addSpecifiedJoinColumn(index++);
			newJoinColumn.initializeFrom(joinColumn);
		}
	}
	
	public FetchType getDefaultFetch() {
		return SingleRelationshipMapping.DEFAULT_FETCH_TYPE;
	}

	//***************** ISingleRelationshipMapping implementation *****************
	public ListIterator<OrmJoinColumn> joinColumns() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumns() : this.specifiedJoinColumns();
	}

	public int joinColumnsSize() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumnsSize() : this.specifiedJoinColumnsSize();
	}
	
	public OrmJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}
	
	protected void setDefaultJoinColumn(OrmJoinColumn newJoinColumn) {
		OrmJoinColumn oldJoinColumn = this.defaultJoinColumn;
		this.defaultJoinColumn = newJoinColumn;
		firePropertyChanged(SingleRelationshipMapping.DEFAULT_JOIN_COLUMN, oldJoinColumn, newJoinColumn);
	}

	protected ListIterator<OrmJoinColumn> defaultJoinColumns() {
		if (this.defaultJoinColumn != null) {
			return new SingleElementListIterator<OrmJoinColumn>(this.defaultJoinColumn);
		}
		return EmptyListIterator.instance();
	}
	
	protected int defaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
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
		OrmJoinColumn oldDefaultJoinColumn = this.getDefaultJoinColumn();
		if (oldDefaultJoinColumn != null) {
			//null the default join column now if one already exists.
			//if one does not exist, there is already a specified join column.
			//Remove it now so that it doesn't get removed during an update and
			//cause change notifications to be sent to the UI in the wrong order
			this.defaultJoinColumn = null;
		}
		XmlJoinColumn xmlJoinColumn = OrmFactory.eINSTANCE.createXmlJoinColumnImpl();
		OrmJoinColumn joinColumn = buildJoinColumn(xmlJoinColumn);
		this.specifiedJoinColumns.add(index, joinColumn);
		this.attributeMapping().getJoinColumns().add(index, xmlJoinColumn);
		this.fireItemAdded(SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		if (oldDefaultJoinColumn != null) {
			this.firePropertyChanged(SingleRelationshipMapping.DEFAULT_JOIN_COLUMN, oldDefaultJoinColumn, null);
		}
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, OrmJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		OrmJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		if (!containsSpecifiedJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultJoinColumn = buildJoinColumn(null);
		}
		this.attributeMapping().getJoinColumns().remove(index);
		fireItemRemoved(SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(SingleRelationshipMapping.DEFAULT_JOIN_COLUMN, null, this.defaultJoinColumn);		
		}
	}

	protected void removeSpecifiedJoinColumn_(OrmJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.attributeMapping().getJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}

	public Boolean getOptional() {
		return getSpecifiedOptional() == null ? getDefaultOptional() : getSpecifiedOptional();
	}
	
	public Boolean getDefaultOptional() {
		return Nullable.DEFAULT_OPTIONAL;
	}

	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		Boolean oldSpecifiedOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		attributeMapping().setOptional(newSpecifiedOptional);
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldSpecifiedOptional, newSpecifiedOptional);
	}
	
	protected void setSpecifiedOptional_(Boolean newSpecifiedOptional) {
		Boolean oldSpecifiedOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldSpecifiedOptional, newSpecifiedOptional);
	}
	
	@Override
	public void initialize(T singleRelationshipMapping) {
		super.initialize(singleRelationshipMapping);
		this.specifiedOptional = singleRelationshipMapping.getOptional();
		//TODO defaultOptional
		this.initializeSpecifiedJoinColumns(singleRelationshipMapping);
		this.initializeDefaultJoinColumn();
	}
	
	protected void initializeSpecifiedJoinColumns(T singleRelationshipMapping) {
		if (singleRelationshipMapping == null) {
			return;
		}
		for (XmlJoinColumn joinColumn : singleRelationshipMapping.getJoinColumns()) {
			this.specifiedJoinColumns.add(buildJoinColumn(joinColumn));
		}
	}
	
	protected boolean shouldBuildDefaultJoinColumn() {
		return !containsSpecifiedJoinColumns() && isRelationshipOwner();
	}
	
	protected void initializeDefaultJoinColumn() {
		if (!shouldBuildDefaultJoinColumn()) {
			return;
		}
		this.defaultJoinColumn = buildJoinColumn(null);
	}	
	
	protected OrmJoinColumn buildJoinColumn(XmlJoinColumn joinColumn) {
		OrmJoinColumn ormJoinColumn = jpaFactory().buildOrmJoinColumn(this, new JoinColumnOwner());
		ormJoinColumn.initialize(joinColumn);
		return ormJoinColumn;
	}	

	@Override
	public void update(T singleRelationshipMapping) {
		super.update(singleRelationshipMapping);
		this.setSpecifiedOptional_(singleRelationshipMapping.getOptional());
		this.updateSpecifiedJoinColumns(singleRelationshipMapping);
		this.updateDefaultJoinColumn();
	}
	
	protected void updateSpecifiedJoinColumns(T singleRelationshipMapping) {
		ListIterator<OrmJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<XmlJoinColumn> resourceJoinColumns = EmptyListIterator.instance();
		if (singleRelationshipMapping != null) {
			resourceJoinColumns = singleRelationshipMapping.getJoinColumns().listIterator();
		}
		
		while (joinColumns.hasNext()) {
			OrmJoinColumn joinColumn = joinColumns.next();
			if (resourceJoinColumns.hasNext()) {
				joinColumn.update(resourceJoinColumns.next());
			}
			else {
				removeSpecifiedJoinColumn_(joinColumn);
			}
		}
		
		while (resourceJoinColumns.hasNext()) {
			addSpecifiedJoinColumn(specifiedJoinColumnsSize(), buildJoinColumn(resourceJoinColumns.next()));
		}
	}
	
	protected void updateDefaultJoinColumn() {
		if (!shouldBuildDefaultJoinColumn()) {
			setDefaultJoinColumn(null);
			return;
		}
		if (getDefaultJoinColumn() == null) {
			this.setDefaultJoinColumn(buildJoinColumn(null));
		}
		else {
			this.defaultJoinColumn.update(null);
		}
	}	

	/**
	 * eliminate any "container" types
	 */
	@Override
	protected String defaultTargetEntity(JavaResourcePersistentAttribute persistentAttributeResource) {
		if (persistentAttributeResource.typeIsContainer()) {
			return null;
		}
		return persistentAttributeResource.getQualifiedReferenceEntityTypeName();
	}

	//********************* validation ******************
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		//bug 192287 - do not want joinColumn validation errors on the non-owning side
		//of a bidirectional relationship.  This is a low risk fix for RC3, but a better
		//solution would be to not have the default joinColumns on the non-owning side.
		//This would fix another bug that we show default joinColumns in this situation.
		if (entityOwned() && isRelationshipOwner()) {
			addJoinColumnMessages(messages);
		}
	}

	protected void addJoinColumnMessages(List<IMessage> messages) {
		boolean doContinue = this.connectionProfileIsActive();
		
		for (OrmJoinColumn joinColumn : CollectionTools.iterable(joinColumns())) {
			String table = joinColumn.getTable();
			
			if (doContinue && typeMapping().tableNameIsInvalid(table)) {
				if (persistentAttribute().isVirtual()) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_TABLE,
							new String[] {getName(), table, joinColumn.getName()},
							joinColumn, 
							joinColumn.tableTextRange())
					);
				}
				else {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_TABLE,
							new String[] {table, joinColumn.getName()}, 
							joinColumn, 
							joinColumn.tableTextRange())
					);
				}
				doContinue = false;
			}
			
			if (doContinue && ! joinColumn.isResolved()) {
				if (persistentAttribute().isVirtual()) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_UNRESOLVED_NAME,
							new String[] {getName(), joinColumn.getName()}, 
							joinColumn, 
							joinColumn.nameTextRange())
					);
				}
				else {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
							new String[] {joinColumn.getName()}, 
							joinColumn, 
							joinColumn.nameTextRange())
					);
				}
			}
			
			if (doContinue && ! joinColumn.isReferencedColumnResolved()) {
				if (persistentAttribute().isVirtual()) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.VIRTUAL_ATTRIBUTE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
							new String[] {getName(), joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
							joinColumn, 
							joinColumn.referencedColumnNameTextRange())
					);
				}
				else {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
							new String[] {joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
							joinColumn, 
							joinColumn.referencedColumnNameTextRange())
					);
				}
			}
		}
	}
	
	
	public class JoinColumnOwner implements OrmJoinColumn.Owner
	{

		public JoinColumnOwner() {
			super();
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String defaultTableName() {
			return AbstractOrmSingleRelationshipMapping.this.typeMapping().tableName();
		}

		public Entity targetEntity() {
			return AbstractOrmSingleRelationshipMapping.this.getResolvedTargetEntity();
		}

		public String attributeName() {
			return AbstractOrmSingleRelationshipMapping.this.getName();
		}

		public RelationshipMapping relationshipMapping() {
			return AbstractOrmSingleRelationshipMapping.this;
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractOrmSingleRelationshipMapping.this.typeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public TypeMapping typeMapping() {
			return AbstractOrmSingleRelationshipMapping.this.typeMapping();
		}

		public Table dbTable(String tableName) {
			return typeMapping().dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			Entity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.primaryDbTable();
		}
		
		public boolean isVirtual(AbstractJoinColumn joinColumn) {
			return AbstractOrmSingleRelationshipMapping.this.defaultJoinColumn == joinColumn;
		}
		
		public String defaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}

		public int joinColumnsSize() {
			return AbstractOrmSingleRelationshipMapping.this.joinColumnsSize();
		}
		
		public TextRange validationTextRange() {
			return AbstractOrmSingleRelationshipMapping.this.validationTextRange();
		}
	}
}
