/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;

public class GenericJavaVirtualOverrideJoinColumnRelationshipStrategy
	extends AbstractJavaJpaContextNode
	implements JavaVirtualJoinColumnRelationshipStrategy
{
	protected final Vector<JavaVirtualJoinColumn> specifiedJoinColumns = new Vector<JavaVirtualJoinColumn>();
	protected final SpecifiedJoinColumnContainerAdapter specifiedJoinColumnContainerAdapter;
	protected final ReadOnlyJoinColumn.Owner joinColumnOwner;

	protected JavaVirtualJoinColumn defaultJoinColumn;


	public GenericJavaVirtualOverrideJoinColumnRelationshipStrategy(JavaVirtualJoinColumnRelationship parent) {
		super(parent);
		this.specifiedJoinColumnContainerAdapter = this.buildSpecifiedJoinColumnContainerAdapter();
		this.joinColumnOwner = this.buildJoinColumnOwner();
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.updateSpecifiedJoinColumns();
		this.updateDefaultJoinColumn();
	}


	// ********** join columns **********

	public ListIterator<JavaVirtualJoinColumn> joinColumns() {
		return this.getJoinColumns().iterator();
	}

	protected ListIterable<JavaVirtualJoinColumn> getJoinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.getSpecifiedJoinColumns() : this.getDefaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.getDefaultJoinColumnsSize();
	}


	// ********** specified join columns **********

	public ListIterator<JavaVirtualJoinColumn> specifiedJoinColumns() {
		return this.getSpecifiedJoinColumns().iterator();
	}

	protected ListIterable<JavaVirtualJoinColumn> getSpecifiedJoinColumns() {
		return new LiveCloneListIterable<JavaVirtualJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.specifiedJoinColumns.size() != 0;
	}

	public JavaVirtualJoinColumn getSpecifiedJoinColumn(int index) {
		return this.specifiedJoinColumns.get(index);
	}

	protected void updateSpecifiedJoinColumns() {
		ContextContainerTools.update(this.specifiedJoinColumnContainerAdapter);
	}

	protected Iterable<JoinColumn> getOverriddenSpecifiedJoinColumns() {
		JoinColumnRelationshipStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ?
				EmptyIterable.<JoinColumn>instance() :
				CollectionTools.iterable(overriddenStrategy.specifiedJoinColumns());
	}

	protected void moveSpecifiedJoinColumn(int index, JavaVirtualJoinColumn joinColumn) {
		this.moveItemInList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected JavaVirtualJoinColumn addSpecifiedJoinColumn(int index, JoinColumn joinColumn) {
		JavaVirtualJoinColumn virtualJoinColumn = this.buildJoinColumn(joinColumn);
		this.addItemToList(index, virtualJoinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
		return virtualJoinColumn;
	}

	protected void removeSpecifiedJoinColumn(JavaVirtualJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected SpecifiedJoinColumnContainerAdapter buildSpecifiedJoinColumnContainerAdapter() {
		return new SpecifiedJoinColumnContainerAdapter();
	}

	/**
	 * specified join column container adapter
	 */
	protected class SpecifiedJoinColumnContainerAdapter
		implements ContextContainerTools.Adapter<JavaVirtualJoinColumn, JoinColumn>
	{
		public Iterable<JavaVirtualJoinColumn> getContextElements() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getSpecifiedJoinColumns();
		}
		public Iterable<JoinColumn> getResourceElements() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getOverriddenSpecifiedJoinColumns();
		}
		public JoinColumn getResourceElement(JavaVirtualJoinColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
		public void moveContextElement(int index, JavaVirtualJoinColumn element) {
			GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.moveSpecifiedJoinColumn(index, element);
		}
		public void addContextElement(int index, JoinColumn resourceElement) {
			GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.addSpecifiedJoinColumn(index, resourceElement);
		}
		public void removeContextElement(JavaVirtualJoinColumn element) {
			GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.removeSpecifiedJoinColumn(element);
		}
	}

	protected ReadOnlyJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}


	// ********** default join column **********

	public JavaVirtualJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(JavaVirtualJoinColumn joinColumn) {
		JavaVirtualJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = joinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, joinColumn);
	}

	protected ListIterable<JavaVirtualJoinColumn> getDefaultJoinColumns() {
		return (this.defaultJoinColumn != null) ?
				new SingleElementListIterable<JavaVirtualJoinColumn>(this.defaultJoinColumn) :
				EmptyListIterable.<JavaVirtualJoinColumn>instance();
	}

	protected int getDefaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}

	protected void updateDefaultJoinColumn() {
		JoinColumn overriddenDefaultJoinColumn = this.getOverriddenDefaultJoinColumn();
		if (overriddenDefaultJoinColumn == null) {
			if (this.defaultJoinColumn != null) {
				this.setDefaultJoinColumn(null);
			}
		} else {
			if ((this.defaultJoinColumn != null) && (this.defaultJoinColumn.getOverriddenColumn() == overriddenDefaultJoinColumn)) {
				this.defaultJoinColumn.update();
			} else {
				this.setDefaultJoinColumn(this.buildJoinColumn(overriddenDefaultJoinColumn));
			}
		}
	}

	protected JoinColumn getOverriddenDefaultJoinColumn() {
		JoinColumnRelationshipStrategy overriddenStrategy = this.getOverriddenStrategy();
		return (overriddenStrategy == null) ? null : overriddenStrategy.getDefaultJoinColumn();
	}


	// ********** misc **********

	@Override
	public JavaVirtualJoinColumnRelationship getParent() {
		return (JavaVirtualJoinColumnRelationship) super.getParent();
	}

	public JavaVirtualJoinColumnRelationship getRelationship() {
		return this.getParent();
	}

	protected JoinColumnRelationshipStrategy getOverriddenStrategy() {
		JoinColumnRelationship relationship = this.getOverriddenJoinColumnRelationship();
		return (relationship == null) ? null : relationship.getJoinColumnStrategy();
	}

	protected JoinColumnRelationship getOverriddenJoinColumnRelationship() {
		Relationship relationship = this.resolveOverriddenRelationship();
		return (relationship instanceof JoinColumnRelationship) ? (JoinColumnRelationship) relationship : null;
	}

	protected Relationship resolveOverriddenRelationship() {
		return this.getRelationship().resolveOverriddenRelationship();
	}

	public boolean isTargetForeignKey() {
		RelationshipMapping relationshipMapping = this.getRelationshipMapping();
		return (relationshipMapping != null) &&
				relationshipMapping.getRelationship().isTargetForeignKey();
	}

	public TypeMapping getRelationshipSource() {
		return this.isTargetForeignKey() ?
				this.getRelationshipMapping().getResolvedTargetEntity() :
				this.getAssociationOverrideContainer().getTypeMapping();
	}

	public TypeMapping getRelationshipTarget() {
		return this.isTargetForeignKey() ?
				this.getAssociationOverrideContainer().getTypeMapping() :
				this.getRelationshipMappingTargetEntity();
	}

	protected TypeMapping getRelationshipMappingTargetEntity() {
		RelationshipMapping mapping = this.getRelationshipMapping();
		return (mapping == null) ? null : mapping.getResolvedTargetEntity();
	}

	protected Entity getRelationshipTargetEntity() {
		TypeMapping target = this.getRelationshipTarget();
		return (target instanceof Entity) ? (Entity) target : null;
	}

	protected RelationshipMapping getRelationshipMapping() {
		return this.getAssociationOverride().getMapping();
	}

	protected JavaReadOnlyAssociationOverride getAssociationOverride() {
		return ((JavaVirtualOverrideRelationship) this.getRelationship()).getAssociationOverride();
	}

	protected JavaAssociationOverrideContainer getAssociationOverrideContainer() {
		return this.getAssociationOverride().getContainer();
	}

	public String getTableName() {
		return this.isTargetForeignKey() ?
				this.getSourceTableName() :
				this.getAssociationOverrideContainer().getDefaultTableName();
	}

	protected String getSourceTableName() {
		TypeMapping typeMapping = this.getRelationshipSource();
		return (typeMapping == null) ? null : typeMapping.getPrimaryTableName();
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationship().getValidationTextRange(astRoot);
	}

	protected String getAttributeName() {
		return this.getAssociationOverride().getName();
	}

	protected JavaVirtualJoinColumn buildJoinColumn(JoinColumn overriddenJoinColumn) {
		return this.getJpaFactory().buildJavaVirtualJoinColumn(this, this.joinColumnOwner, overriddenJoinColumn);
	}


	// ********** join column owner **********

	protected class JoinColumnOwner
		implements ReadOnlyJoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		public String getDefaultTableName() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getTableName();
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public String getAttributeName() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getAttributeName();
		}

		public TypeMapping getTypeMapping() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getRelationshipSource();
		}

		public Entity getRelationshipTarget() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.getRelationshipTargetEntity();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return false;
		}

		public int joinColumnsSize() {
			return GenericJavaVirtualOverrideJoinColumnRelationshipStrategy.this.joinColumnsSize();
		}
	}
}
