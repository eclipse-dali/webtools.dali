/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaOverrideRelationship;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumn.Owner;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;

public class GenericJavaOverrideJoinColumnJoiningStrategy
	extends AbstractJavaJoinColumnJoiningStrategy
{
	public GenericJavaOverrideJoinColumnJoiningStrategy(JavaOverrideRelationship parent) {
		super(parent);
	}


	// ********** join column annotations **********

	@Override
	protected ListIterator<JoinColumnAnnotation> joinColumnAnnotations() {
		return this.getOverrideAnnotation().joinColumns();
	}

	@Override
	protected JoinColumnAnnotation addJoinColumnAnnotation(int index) {
		return this.getOverrideAnnotation().addJoinColumn(index);
	}

	@Override
	protected void removeJoinColumnAnnotation(int index) {
		this.getOverrideAnnotation().removeJoinColumn(index);
	}

	@Override
	protected void moveJoinColumnAnnotation(int targetIndex, int sourceIndex) {
		this.getOverrideAnnotation().moveJoinColumn(targetIndex, sourceIndex);
	}

	@Override
	protected JoinColumnAnnotation buildNullJoinColumnAnnotation() {
		return new NullJoinColumnAnnotation(this.getOverrideAnnotation());
	}

	protected AssociationOverrideAnnotation getOverrideAnnotation() {
		return this.getAssociationOverride().getOverrideAnnotation();
	}


	// ********** misc **********

	@Override
	protected Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
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

	@Override
	public RelationshipMapping getRelationshipMapping() {
		return this.getAssociationOverride().getMapping();
	}

	protected String getAttributeName() {
		return this.getAssociationOverride().getName();
	}

	@Override
	public String getTableName() {
		return this.isTargetForeignKey() ?
				super.getTableName() :
				this.getAssociationOverrideContainer().getDefaultTableName();
	}

	@Override
	public Table resolveDbTable(String tableName) {
		return this.isTargetForeignKey() ?
				super.resolveDbTable(tableName) :
				this.getAssociationOverrideContainer().resolveDbTable(tableName);
	}

	@Override
	public boolean tableNameIsInvalid(String tableName) {
		return this.isTargetForeignKey() ?
				super.tableNameIsInvalid(tableName) :
				this.getAssociationOverrideContainer().tableNameIsInvalid(tableName);
	}

	@Override
	public Iterator<String> candidateTableNames() {
		return this.isTargetForeignKey() ?
				super.candidateTableNames() :
				this.getAssociationOverrideContainer().candidateTableNames();
	}

	public String getColumnTableNotValidDescription() {
		return null;
	}

	public boolean isOverridable() {
		return false;
	}

	protected JavaAssociationOverride getAssociationOverride() {
		return this.getRelationship().getAssociationOverride();
	}

	protected JavaAssociationOverrideContainer getAssociationOverrideContainer() {
		return this.getAssociationOverride().getContainer();
	}

	@Override
	public JavaOverrideRelationship getRelationship() {
		return (JavaOverrideRelationship) super.getRelationship();
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationship().getValidationTextRange(astRoot);
	}


	// ********** join column owner adapter **********

	protected class JoinColumnOwner
		implements JavaJoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		public String getDefaultTableName() {
			return GenericJavaOverrideJoinColumnJoiningStrategy.this.getTableName();
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public String getAttributeName() {
			return GenericJavaOverrideJoinColumnJoiningStrategy.this.getAttributeName();
		}

		public PersistentAttribute getPersistentAttribute() {
			RelationshipMapping relationshipMapping = GenericJavaOverrideJoinColumnJoiningStrategy.this.getRelationshipMapping();
			return relationshipMapping == null ? null : relationshipMapping.getPersistentAttribute();
		}

		public TypeMapping getTypeMapping() {
			return GenericJavaOverrideJoinColumnJoiningStrategy.this.getRelationshipSource();
		}

		public Entity getRelationshipTarget() {
			return GenericJavaOverrideJoinColumnJoiningStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return GenericJavaOverrideJoinColumnJoiningStrategy.this.tableNameIsInvalid(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return GenericJavaOverrideJoinColumnJoiningStrategy.this.candidateTableNames();
		}

		public Table resolveDbTable(String tableName) {
			return GenericJavaOverrideJoinColumnJoiningStrategy.this.resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			return GenericJavaOverrideJoinColumnJoiningStrategy.this.getReferencedColumnDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return false;
		}

		public int joinColumnsSize() {
			return GenericJavaOverrideJoinColumnJoiningStrategy.this.joinColumnsSize();
		}

		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaOverrideJoinColumnJoiningStrategy.this.getValidationTextRange(astRoot);
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return this.getAssociationOverrideContainer().buildColumnValidator(this.getAssociationOverride(), (BaseColumn) column, this, (BaseColumnTextRangeResolver) textRangeResolver);
		}

		protected JavaAssociationOverride getAssociationOverride() {
			return GenericJavaOverrideJoinColumnJoiningStrategy.this.getAssociationOverride();
		}

		protected JavaAssociationOverrideContainer getAssociationOverrideContainer() {
			return GenericJavaOverrideJoinColumnJoiningStrategy.this.getAssociationOverrideContainer();
		}
	}
}
