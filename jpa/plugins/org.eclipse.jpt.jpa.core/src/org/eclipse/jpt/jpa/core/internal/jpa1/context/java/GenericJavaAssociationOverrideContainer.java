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

import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;

/**
 * Java attribute override container
 */
public class GenericJavaAssociationOverrideContainer
	extends AbstractJavaOverrideContainer<
			JavaAssociationOverrideContainer.Owner,
			JavaReadOnlyAssociationOverride,
			JavaAssociationOverride,
			JavaVirtualAssociationOverride,
			AssociationOverrideAnnotation
		>
	implements JavaAssociationOverrideContainer2_0
{
	public GenericJavaAssociationOverrideContainer(JavaJpaContextNode parent, JavaAssociationOverrideContainer.Owner owner) {
		super(parent, owner);
	}


	public RelationshipMapping getRelationshipMapping(String attributeName) {
		return MappingTools.getRelationshipMapping(attributeName, this.owner.getOverridableTypeMapping());
	}

	public ReadOnlyRelationship resolveOverriddenRelationship(String associationOverrideName) {
		return this.owner.resolveOverriddenRelationship(associationOverrideName);
	}

	public ReadOnlyOverrideRelationship getOverrideRelationship(String overrideName) {
		return this.getOverrideNamed(overrideName).getRelationship();
	}

	@Override
	protected JavaAssociationOverrideContainer2_0.Owner getOwner2_0() {
		return (JavaAssociationOverrideContainer2_0.Owner) super.getOwner2_0();
	}

	public JptValidator buildJoinTableJoinColumnValidator(ReadOnlyAssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner o, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getOwner2_0().buildJoinTableJoinColumnValidator(override, column, o, textRangeResolver);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(ReadOnlyAssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner o, JoinColumnTextRangeResolver textRangeResolver) {
		return this.getOwner2_0().buildJoinTableInverseJoinColumnValidator(override, column, o, textRangeResolver);
	}

	public JptValidator buildJoinTableValidator(ReadOnlyAssociationOverride override, ReadOnlyTable table, TableTextRangeResolver textRangeResolver) {
		return this.getOwner2_0().buildJoinTableValidator(override, table, textRangeResolver);
	}

	@Override
	protected String getOverrideAnnotationName() {
		return AssociationOverrideAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected JavaAssociationOverride buildSpecifiedOverride(AssociationOverrideAnnotation overrideAnnotation) {
		return this.getJpaFactory().buildJavaAssociationOverride(this, overrideAnnotation);
	}

	@Override
	protected void initializeSpecifiedOverride(JavaAssociationOverride specifiedOverride, JavaVirtualAssociationOverride virtualOverride) {
		specifiedOverride.initializeFromVirtual(virtualOverride);
	}

	@Override
	protected JavaVirtualAssociationOverride buildVirtualOverride(String name) {
		return this.getJpaFactory().buildJavaVirtualAssociationOverride(this, name);
	}
}
