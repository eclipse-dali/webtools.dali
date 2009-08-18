/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;

public class GenericJavaJoinColumnJoiningStrategy 
	extends AbstractJavaJoinColumnJoiningStrategy
{
	
	public GenericJavaJoinColumnJoiningStrategy(JavaJoinColumnEnabledRelationshipReference parent) {
		super(parent);
	}
	
	@Override
	protected JavaJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}
	
	public TypeMapping getTypeMapping() {
		return getRelationshipMapping().getTypeMapping();
	}
	
	public boolean isOverridableAssociation() {
		return true;
	}
	
	@Override
	public JavaJoinColumnEnabledRelationshipReference getRelationshipReference() {
		return (JavaJoinColumnEnabledRelationshipReference) super.getRelationshipReference();
	}
	
	@Override
	public JavaRelationshipMapping getRelationshipMapping() {
		return getRelationshipReference().getRelationshipMapping();
	}
	
	protected JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return getRelationshipMapping().getPersistentAttribute().getResourcePersistentAttribute();
	}
	
	@Override
	protected ListIterator<JoinColumnAnnotation> joinColumnAnnotations() {
		return new TransformationListIterator<NestableAnnotation, JoinColumnAnnotation>(
			this.getResourcePersistentAttribute().supportingAnnotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME)) {
				@Override
				protected JoinColumnAnnotation transform(NestableAnnotation next) {
					return (JoinColumnAnnotation) next;
				}
			};
	}
	
	@Override
	protected JoinColumnAnnotation buildNullJoinColumnAnnotation() {
		return new NullJoinColumnAnnotation(this.getResourcePersistentAttribute());
	}
		
	@Override
	protected JoinColumnAnnotation addAnnotation(int index) {
		return (JoinColumnAnnotation) this.getResourcePersistentAttribute().
			addSupportingAnnotation(
				index, 
				JoinColumnAnnotation.ANNOTATION_NAME, 
				JoinColumnsAnnotation.ANNOTATION_NAME);
	}
	
	@Override
	protected void removeAnnotation(int index) {
		this.getResourcePersistentAttribute().
			removeSupportingAnnotation(
				index, 
				JoinColumnAnnotation.ANNOTATION_NAME, 
				JoinColumnsAnnotation.ANNOTATION_NAME);
	}
	
	@Override
	protected void moveAnnotation(int targetIndex, int sourceIndex) {
		this.getResourcePersistentAttribute().
			moveSupportingAnnotation(
				targetIndex, 
				sourceIndex, 
				JoinColumnsAnnotation.ANNOTATION_NAME);
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationshipReference().getValidationTextRange(astRoot);
	}
	protected class JoinColumnOwner 
	implements JavaJoinColumn.Owner 
{
	protected JoinColumnOwner() {
		super();
	}
	
	
	/**
	 * by default, the join column is in the type mapping's primary table
	 */
	public String getDefaultTableName() {		
		return getTypeMapping().getPrimaryTableName();
	}
	
	public String getDefaultColumnName() {
		//built in MappingTools.buildJoinColumnDefaultName()
		return null;
	}
	
	public Entity getTargetEntity() {
		return getRelationshipMapping().getResolvedTargetEntity();
	}
	
	public String getAttributeName() {
		return getRelationshipMapping().getPersistentAttribute().getName();
	}
	
	public RelationshipMapping getRelationshipMapping() {
		return GenericJavaJoinColumnJoiningStrategy.this.getRelationshipMapping();
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
	
	public TypeMapping getTypeMapping() {
		return getRelationshipMapping().getTypeMapping();
	}
	
	public Table getDbTable(String tableName) {
		return getTypeMapping().getDbTable(tableName);
	}
	
	public Table getReferencedColumnDbTable() {
		Entity targetEntity = getTargetEntity();
		return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
	}
	
	public boolean isVirtual(BaseJoinColumn joinColumn) {
		return GenericJavaJoinColumnJoiningStrategy.this.defaultJoinColumn == joinColumn;
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return GenericJavaJoinColumnJoiningStrategy.this.getValidationTextRange(astRoot);
	}
	
	public int joinColumnsSize() {
		return GenericJavaJoinColumnJoiningStrategy.this.joinColumnsSize();
	}
}
}
