/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.OverrideRelationship;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;

/**
 * Java attribute override container
 */
public class GenericJavaAssociationOverrideContainer
	extends AbstractJavaOverrideContainer<
			JavaAssociationOverrideContainer.ParentAdapter,
			AssociationOverride,
			JavaSpecifiedAssociationOverride,
			JavaVirtualAssociationOverride,
			AssociationOverrideAnnotation
		>
	implements JavaAssociationOverrideContainer2_0
{
	public GenericJavaAssociationOverrideContainer(JpaContextModel parent) {
		super(parent);
	}

	public GenericJavaAssociationOverrideContainer(JavaAssociationOverrideContainer.ParentAdapter parentAdapter) {
		super(parentAdapter);
	}


	public RelationshipMapping getRelationshipMapping(String attributeName) {
		return MappingTools.getRelationshipMapping(attributeName, this.parentAdapter.getOverridableTypeMapping());
	}

	public Relationship resolveOverriddenRelationship(String associationOverrideName) {
		return this.parentAdapter.resolveOverriddenRelationship(associationOverrideName);
	}

	public OverrideRelationship getOverrideRelationship(String overrideName) {
		return this.getOverrideNamed(overrideName).getRelationship();
	}

	@Override
	protected JavaAssociationOverrideContainer2_0.ParentAdapter getParentAdapter2_0() {
		return (JavaAssociationOverrideContainer2_0.ParentAdapter) super.getParentAdapter2_0();
	}

	public JpaValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.ParentAdapter o) {
		return this.getParentAdapter2_0().buildJoinTableJoinColumnValidator(override, column, o);
	}

	public JpaValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.ParentAdapter o) {
		return this.getParentAdapter2_0().buildJoinTableInverseJoinColumnValidator(override, column, o);
	}

	public JpaValidator buildJoinTableValidator(AssociationOverride override, Table table) {
		return this.getParentAdapter2_0().buildJoinTableValidator(override, table);
	}

	@Override
	protected String getOverrideAnnotationName() {
		return AssociationOverrideAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected JavaSpecifiedAssociationOverride buildSpecifiedOverride(AssociationOverrideAnnotation overrideAnnotation) {
		return this.getJpaFactory().buildJavaAssociationOverride(this, overrideAnnotation);
	}

	@Override
	protected void initializeSpecifiedOverride(JavaSpecifiedAssociationOverride specifiedOverride, JavaVirtualAssociationOverride virtualOverride) {
		specifiedOverride.initializeFrom(virtualOverride);
	}

	@Override
	protected JavaVirtualAssociationOverride buildVirtualOverride(String name) {
		return this.getJpaFactory().buildJavaVirtualAssociationOverride(this, name);
	}
}
