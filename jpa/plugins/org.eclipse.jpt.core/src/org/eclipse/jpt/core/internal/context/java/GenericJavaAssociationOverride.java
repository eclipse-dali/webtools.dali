/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericJavaAssociationOverride extends AbstractJavaOverride
	implements JavaAssociationOverride
{

	protected final List<JavaJoinColumn> specifiedJoinColumns;

	protected final List<JavaJoinColumn> defaultJoinColumns;


	public GenericJavaAssociationOverride(JavaJpaContextNode parent, AssociationOverride.Owner owner) {
		super(parent, owner);
		this.specifiedJoinColumns = new ArrayList<JavaJoinColumn>();
		this.defaultJoinColumns = new ArrayList<JavaJoinColumn>();
	}

	@Override
	public JavaAssociationOverride setVirtual(boolean virtual) {
		return (JavaAssociationOverride) super.setVirtual(virtual);
	}

	@Override
	protected AssociationOverrideAnnotation getResourceOverride() {
		return (AssociationOverrideAnnotation) super.getResourceOverride();
	}
	
	@Override
	public AssociationOverride.Owner getOwner() {
		return (AssociationOverride.Owner) super.getOwner();
	}
	
	public ListIterator<JavaJoinColumn> joinColumns() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumns() : this.specifiedJoinColumns();
	}
	
	public int joinColumnsSize() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumnsSize() : this.specifiedJoinColumnsSize();
	}
	
	public ListIterator<JavaJoinColumn> defaultJoinColumns() {
		return new CloneListIterator<JavaJoinColumn>(this.defaultJoinColumns);
	}
	
	public int defaultJoinColumnsSize() {
		return this.defaultJoinColumns.size();
	}
	
	public ListIterator<JavaJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<JavaJoinColumn>(this.specifiedJoinColumns);
	}
	
	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}
	
	public JavaJoinColumn addSpecifiedJoinColumn(int index) {
		JavaJoinColumn joinColumn = getJpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		JoinColumnAnnotation joinColumnResource = getResourceOverride().addJoinColumn(index);
		joinColumn.initialize(joinColumnResource);
		this.fireItemAdded(AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}
	
	protected JavaJoinColumn.Owner createJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	protected void addSpecifiedJoinColumn(int index, JavaJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		JavaJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		getResourceOverride().removeJoinColumn(index);
		fireItemRemoved(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedJoinColumn(JavaJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		getResourceOverride().moveJoinColumn(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.specifiedJoinColumns, AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);		
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.specifiedJoinColumns.isEmpty();
	}


	@Override
	protected Iterator<String> candidateNames() {
		return this.getOwner().getTypeMapping().allOverridableAssociationNames();
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaJoinColumn column : CollectionTools.iterable(this.joinColumns())) {
			result = column.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public void initialize(AssociationOverrideAnnotation associationOverride) {
		super.initialize(associationOverride);
		this.name = associationOverride.getName();
		initializeSpecifiedJoinColumns(associationOverride);
	}
	
	protected void initializeSpecifiedJoinColumns(AssociationOverrideAnnotation associationOverride) {
		ListIterator<JoinColumnAnnotation> annotations = associationOverride.joinColumns();
		
		while(annotations.hasNext()) {
			this.specifiedJoinColumns.add(createJoinColumn(annotations.next()));
		}
	}

	public void update(AssociationOverrideAnnotation associationOverride) {
		super.update(associationOverride);
		updateSpecifiedJoinColumns(associationOverride);
	}

	protected void updateSpecifiedJoinColumns(AssociationOverrideAnnotation associationOverride) {
		ListIterator<JavaJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<JoinColumnAnnotation> resourceJoinColumns = associationOverride.joinColumns();
		
		while (joinColumns.hasNext()) {
			JavaJoinColumn joinColumn = joinColumns.next();
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
	
	
	protected JavaJoinColumn createJoinColumn(JoinColumnAnnotation joinColumnResource) {
		JavaJoinColumn joinColumn = getJpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
		joinColumn.initialize(joinColumnResource);
		return joinColumn;
	}

	
	//******************** validation ********************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		addJoinColumnMessages(messages, astRoot);
	}
	
	protected void addJoinColumnMessages(List<IMessage> messages, CompilationUnit astRoot) {
		for (JavaJoinColumn joinColumn : CollectionTools.iterable(joinColumns())) {
			String table = joinColumn.getTable();
			boolean doContinue = connectionProfileIsActive();
			
			if (doContinue && getOwner().getTypeMapping().tableNameIsInvalid(table)) {
				if (isVirtual()) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_TABLE,
							new String[] {getName(), table, joinColumn.getName()},
							joinColumn, 
							joinColumn.getTableTextRange(astRoot))
					);
				}
				else {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_TABLE,
							new String[] {table, joinColumn.getName()}, 
							joinColumn, 
							joinColumn.getTableTextRange(astRoot))
					);
				}
				doContinue = false;
			}
			
			if (doContinue && ! joinColumn.isResolved()) {
				if (isVirtual()) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_UNRESOLVED_NAME,
							new String[] {getName(), joinColumn.getName()}, 
							joinColumn, 
							joinColumn.getNameTextRange(astRoot))
					);
				}
				else {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
							new String[] {joinColumn.getName()}, 
							joinColumn, 
							joinColumn.getNameTextRange(astRoot))
					);
				}
			}
			
			if (doContinue && ! joinColumn.isReferencedColumnResolved()) {
				if (isVirtual()) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.VIRTUAL_ASSOCIATION_OVERRIDE_JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
							new String[] {getName(), joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
							joinColumn, 
							joinColumn.getReferencedColumnNameTextRange(astRoot))
					);
				}
				else {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
							new String[] {joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
							joinColumn,
							joinColumn.getReferencedColumnNameTextRange(astRoot))
					);
				}
			}
		}
	}
	public class JoinColumnOwner implements JavaJoinColumn.Owner
	{

		public JoinColumnOwner() {
			super();
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return GenericJavaAssociationOverride.this.owner.getTypeMapping().getTableName();
		}
		
		public String getDefaultColumnName() {
			return null;
		}
		
		public Entity getTargetEntity() {
			RelationshipMapping relationshipMapping = getRelationshipMapping();
			return relationshipMapping == null ? null : relationshipMapping.getResolvedTargetEntity();
		}

		public String getAttributeName() {
			return GenericJavaAssociationOverride.this.getName();
		}

		public RelationshipMapping getRelationshipMapping() {
			return GenericJavaAssociationOverride.this.getOwner().getRelationshipMapping(GenericJavaAssociationOverride.this.getName());
		}

		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}

		public TypeMapping getTypeMapping() {
			return GenericJavaAssociationOverride.this.owner.getTypeMapping();
		}

		public Table getDbTable(String tableName) {
			return getTypeMapping().getDbTable(tableName);
		}

		public Table getDbReferencedColumnTable() {
			Entity targetEntity = getTargetEntity();
			return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return GenericJavaAssociationOverride.this.defaultJoinColumns.contains(joinColumn);
		}

		public int joinColumnsSize() {
			return GenericJavaAssociationOverride.this.joinColumnsSize();
		}
	}
}
